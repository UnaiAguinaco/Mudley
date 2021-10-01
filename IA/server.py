# 1. Library imports
import uvicorn
import json
import gzip
import numpy as np
import itertools
import pandas as pd
from pydantic import BaseModel
from fastapi import FastAPI,Request
import requests
from csv import writer
class Search(BaseModel):
    name: str
    city: str
    date: str
    gender: str
    budget: str
# 2. Create the app object
app = FastAPI()
class Artista():
  name = ""
  budget = 0
  city = ""
  genre=""
  def __init__(self, name, city,budget,genre):
    self.name = name
    self.city = city
    self.budget = budget
    self.genre = genre
  def __str__(self):
    return self.name + "$" + self.city + "$" + self.budget + "$" + self.genre
def make_artist(name, city, budget,genre):
    artist = Artista(name, city, budget,genre)
    return artist
class Recomend(BaseModel):
    name: str
# 2. Create the app object
app = FastAPI()

def append_list_as_row(file_name, list_of_elem):
    with open(file_name, 'a+', newline='') as write_obj:
        csv_writer = writer(write_obj)
        csv_writer.writerow(list_of_elem)
def sample_recommendation(model, train, test, item_labels,data,item_labels_csv, user_ids):
    for user_id in user_ids:
        # artistas que ya les gustan
        ids_artistas = data['artistID'][data['userID'] == user_id]
        known_positives = item_labels_csv['name'][item_labels_csv['id'].isin(ids_artistas)]
        # artistas recomendados
        scores = model.predict(user_id, np.arange(item_labels.size))
        # artistas ordenados de mayor a menor
        top_items = item_labels[np.argsort(-scores)]
        # resultados
        print("User %s" % user_id)
        print("     Known positives:")
        for x in known_positives[:10]:
            print("        %s" % x)
    return top_items

def get_vistos(model, train, test, item_labels,data,item_labels_csv, user_ids):
    for user_id in user_ids:
        ids_artistas = data['artistID'][data['userID'] == user_id]
        known_positives = item_labels_csv['name'][item_labels_csv['id'].isin(ids_artistas)]
        scores = model.predict(user_id, np.arange(item_labels.size))
        top_items = item_labels[np.argsort(-scores)]
        print("User %s" % user_id)
        print("     Known positives:")
        for x in known_positives[:10]:
            print("        %s" % x)
    return known_positives


def train_test_split(ratings):
    test = np.zeros(ratings.shape)
    train = ratings.copy()
    for user in xrange(ratings.shape[0]):
        test_ratings = np.random.choice(ratings[user, :].nonzero()[0],
                                        size=10,
                                        replace=False)
        train[user, test_ratings] = 0.
        test[user, test_ratings] = ratings[user, test_ratings]

    # Test and training are truly disjoint
    assert (np.all((train * test) == 0))
    return train, test

def preprocesing():
    data = pd.read_csv('user_artists.dat', sep="\t", skiprows=1, names=['userID', 'artistID', 'weight'])
    item_labels_csv = pd.read_csv('final.dat', sep='\t', skiprows=1, names=['id', 'name', 'url', 'pictureURL', 'genre','budget','place'])
    user_labels_csv = pd.read_csv('user_friends.dat', sep='\t', skiprows=1,
                                  names=['id', 'friendId'])
    item_labels = item_labels_csv['name']
    item_lables_index = item_labels_csv['id']

    item_labels = item_labels.to_numpy()

    item_labels_csv.head()
    import csv
    item_labels_csv.to_csv(r'item_labels.csv', index=False)
    data.to_csv(r'data.csv', index=False)
    reader = csv.DictReader(open('item_labels.csv', 'r',encoding='utf8'))
    dict_list = []
    for line in reader:
        dict_list.append(line)

    reader = csv.DictReader(open('data.csv', 'r',encoding='utf8'))
    ratings = []
    for line in reader:
        ratings.append(line)

    user_labels_csv.to_csv(r'user_friends.csv', index=False)
    userList = csv.DictReader(open('user_friends.csv', 'r', encoding='utf8'))
    user_list = []
    for line in userList:
        user_list.append(line)
    from lightfm.data import Dataset
    from lightfm import LightFM
    from lightfm.evaluation import auc_score

    dataset = Dataset()
    dataset.fit((x['userID'] for x in ratings), (x['artistID'] for x in ratings))
    num_users, num_items = dataset.interactions_shape()
    print('Num users: {}, num_items {}.'.format(num_users, num_items))
    i1 = ((x['genre']) for x in dict_list)
    users = ((x['friendId']) for x in user_list)
    #i2 = ((x['budget']) for x in dict_list)
    #i3 = ((x['place']) for x in dict_list)
    #item_features=itertools.chain(i1,i2,i3)
    dataset.fit_partial(items=(x['id'] for x in dict_list),users=(x['id'] for x in user_list),item_features=i1,user_features=users)
    item_features = dataset.build_item_features(((x['id'], [x['genre']]) for x in dict_list))
    user_features = dataset.build_user_features(((x['id'], [x['friendId']]) for x in users))
    (interactions, weights) = dataset.build_interactions(((x['userID'], x['artistID'])
                                                          for x in ratings))

    from lightfm.cross_validation import random_train_test_split
    train, test = random_train_test_split(interactions, test_percentage=0.2, random_state=42)

    # Define a new model instance
    NUM_THREADS = 16
    NUM_COMPONENTS = 30
    NUM_EPOCHS = 70
    ITEM_ALPHA = 1e-6
    model = LightFM(loss='warp',
                    item_alpha=ITEM_ALPHA,learning_schedule='adagrad',
                    no_components=NUM_COMPONENTS)
    
    model = model.fit(train, epochs=NUM_EPOCHS, num_threads=NUM_THREADS)
    # Fit the hybrid model. Note that this time, we pass
    # in the item features matrix.
    model.fit(train, item_features=item_features,user_features=user_features)
    train_auc = auc_score(model,
                          train,
                          item_features=item_features,
                          num_threads=NUM_THREADS).mean()
    print('Hybrid training set AUC: %s' % train_auc)

    test_auc = auc_score(model,
                         test,
                         train_interactions=train,
                         item_features=item_features,
                         num_threads=NUM_THREADS).mean()
    print('Hybrid test set AUC: %s' % test_auc)
    from lightfm.evaluation import precision_at_k
    train_precision = precision_at_k(model, train, k=10, user_features=user_features, item_features=item_features).mean()
    test_precision = precision_at_k(model, test, k=10, user_features=user_features, item_features=item_features).mean()
    print('Precision: train %.2f, test %.2f.' % (train_precision, test_precision))
    return model, train, test, item_labels,data,item_labels_csv
model, train, test, item_labels,data,item_labels_csv=preprocesing()
# 3. Index route, opens automatically on http://127.0.0.1:8000
@app.post('/mostrar')
def index(search: Search):
    budget=int(search.budget)
    r = item_labels_csv.loc[(item_labels_csv['genre'] == search.gender) & (item_labels_csv['place'] == search.city) & (
                item_labels_csv['budget'] > budget-100) & (item_labels_csv['budget'] < budget+100)]
    re = item_labels_csv.loc[(item_labels_csv['place'] == search.city) & (item_labels_csv['budget'] > budget-100) & (
                item_labels_csv['budget'] < budget+100)]
    res = item_labels_csv.loc[(item_labels_csv['place'] == search.city) & (item_labels_csv['genre'] == search.gender)]
    resu = item_labels_csv.loc[
        (item_labels_csv['budget'] > budget-100) & (item_labels_csv['budget'] < budget+100) & (item_labels_csv['genre'] == search.gender)]
    resul = item_labels_csv.loc[item_labels_csv['place'] == search.city]
    result = item_labels_csv.loc[item_labels_csv['genre'] == search.gender]
    resultado = 0
    lista = list()
    ##################
    for index, row in r.iterrows():
        lista.append(make_artist(str(row.values[1]), str(row.values[6]), str(row.values[5]), str(row.values[4])))
    if len(lista) < 20:
        for index, row in re.iterrows():
            lista.append(make_artist(str(row.values[1]), str(row.values[6]), str(row.values[5]), str(row.values[4])))
    if len(lista) < 20:
        for index, row in res.iterrows():
            lista.append(make_artist(str(row.values[1]), str(row.values[6]), str(row.values[5]), str(row.values[4])))
    if len(lista) < 20:
        for index, row in resu.iterrows():
            lista.append(make_artist(str(row.values[1]), str(row.values[6]), str(row.values[5]), str(row.values[4])))
    if len(lista) < 20:
        for index, row in resul.iterrows():
            lista.append(make_artist(str(row.values[1]), str(row.values[6]), str(row.values[5]), str(row.values[4])))
    if len(lista) < 20:
        for index, row in result.iterrows():
            lista.append(make_artist(str(row.values[1]), str(row.values[6]), str(row.values[5]), str(row.values[4])))
    x = '{"name":"' + str(search.name) + '","results":["' + str(lista[0]) + '","' + str(lista[1]) + '","' + str(
        lista[2]) + '","' + str(lista[3]) + '","' + str(lista[4]) + '","' + str(lista[5]) + '","' + str(
        lista[6]) + '","' + str(lista[7]) + '","' + str(lista[8]) + '","' + str(lista[9]) + '","' + str(
        lista[10]) + '","' + str(lista[11]) + '","' + str(lista[12]) + '","' + str(lista[13]) + '","' + str(
        lista[14]) + '","' + str(lista[15]) + '","' + str(lista[16]) + '","' + str(lista[17]) + '","' + str(
        lista[18]) + '","' + str(lista[19]) + '"]}'
    y = json.loads(x)
    return y
@app.post('/mostrarRecomendaciones')
def recomendaciones1(recomend: Recomend):
    resultados=sample_recommendation(model, train, test, item_labels,data,item_labels_csv, [485])
    lista = list()
    for x in resultados[:12]:
        i = item_labels_csv[item_labels_csv['name'] == x]
        y = i.to_string(header=False,index=False).split('\n')
        lista.append(y)
    x =  '{"name":"'+str(recomend.name)+'","results":["'+str(lista[0])+'","'+str(lista[1])+'","'+str(lista[2])+'","'+str(lista[3])+'","'+str(lista[4])+'","'+str(lista[5])+'","'+str(lista[6])+'","'+str(lista[7])+'","'+str(lista[8])+'","'+str(lista[9])+'","'+str(lista[10])+'","'+str(lista[11])+'"]}'
    row_contents = [str(recomend.name), str(lista[0]), str(lista[1]), str(lista[2]), str(lista[3]), str(lista[4]),
                    str(lista[5]), str(lista[6]), str(lista[7]), str(lista[8]), str(lista[9]), str(lista[10]),
                    str(lista[11])]
    append_list_as_row('results.csv', row_contents)
    y = json.loads(x)
    return y
@app.post('/mostrarNuevos')
def recomendaciones2(recomend: Recomend):
    resultados = item_labels_csv.tail(12)
    resultados = resultados['name']
    lista = list()
    for x in resultados[:12]:
        lista.append(x)
    x = '{"name":"' + str(recomend.name) + '","results":["' + str(lista[0]) + '","' + str(lista[1]) + '","' + str(
        lista[2]) + '","' + str(lista[3]) + '","' + str(lista[4]) + '","' + str(lista[5]) + '","' + str(
        lista[6]) + '","' + str(lista[7]) + '","' + str(lista[8]) + '","' + str(lista[9]) + '","' + str(
        lista[10]) + '","' + str(lista[11]) + '"]}'
    y = json.loads(x)
    return y
@app.post('/mostrarVistos')
def recomendaciones3(recomend: Recomend):
    resultados = get_vistos(model, train, test, item_labels, data, item_labels_csv, [485])
    lista = list()
    for x in resultados[:12]:
        i = item_labels_csv[item_labels_csv['name'] == x]
        y = i.to_string(header=False, index=False).split('\n')
        lista.append(y)
    x = '{"name":"' + str(recomend.name) + '","results":["' + str(lista[0]) + '","' + str(lista[1]) + '","' + str(
        lista[2]) + '","' + str(lista[3]) + '","' + str(lista[4]) + '","' + str(lista[5]) + '","' + str(
        lista[6]) + '","' + str(lista[7]) + '","' + str(lista[8]) + '","' + str(lista[9]) + '","' + str(
        lista[10]) + '","' + str(lista[11]) + '"]}'
    y = json.loads(x)
    return y
@app.get('/actualizarAgente')
def recomendaciones4():
   preprocesing()
@app.post('/mostrarNuestros')
def recomendaciones5(recomend: Recomend):
    data.sort_values(by=['weight'])
    lista = data.head(12)
    lista = lista['artistID']
    r = list()
    for x in lista[:12]:
        i=item_labels_csv.loc[item_labels_csv['id'] == x]
        y = i.to_string(header=False, index=False).split('\n')
        r.append(y)
    x = '{"name":"' + str(recomend.name) + '","results":["' + str(r[0]) + '","' + str(r[1]) + '","' + str(
        r[2]) + '","' + str(r[3]) + '","' + str(r[4]) + '","' + str(r[5]) + '","' + str(
        r[6]) + '","' + str(r[7]) + '","' + str(r[8]) + '","' + str(r[9]) + '","' + str(
        r[10]) + '","' + str(r[11]) + '"]}'
    y = json.loads(x)
    return y

if __name__ == '__main__':
    uvicorn.run(app, host='0.0.0.0', port=8000)
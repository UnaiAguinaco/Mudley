
  var lat = 0;
  var lng = 0;
  geocode();
  var map = L.map('map').setView([lat, lng], 13);
  L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png').addTo(map);
  function randomGeo(center, radius) {
    var y0 = center.latitude;
    var x0 = center.longitude;
    var rd = radius / 111300;
    var u = Math.random();
    var v = Math.random();

    var w = rd * Math.sqrt(u);
    var t = 2 * Math.PI * v;
    var x = w * Math.cos(t);
    var y = w * Math.sin(t);
    var xp = x / Math.cos(y0);

    return {
      'latitude': y + y0,
      'longitude': x + x0
    };
  }



  function geocode(e) {
    //e.preventDefault();
    var lat = 0;
    var lng=0;
    //var location = document.getElementById('buscar').value;
    var location = document.getElementById('location');
    axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
      params: {
        address: location.className,
        key: 'AIzaSyBAvqd6iVi-Nq7KnAZfGsJbqpgzztKm_V8'
      }
    })
      .then(function (response) {

        lat = response.data.results[0].geometry.location.lat;
        lng = response.data.results[0].geometry.location.lng;
        map.setView(new L.LatLng(lat, lng), 13);

      })
      .catch(function (error) {
        console.log(error);
      });
  }


  var resultados = document.querySelectorAll(".perfil");

  resultados.forEach(element => {
    var locate=element.querySelectorAll(".perfilAtributos>a")[1].innerHTML.split(":")[1];
    console.log(locate);
    axios.get('https://maps.googleapis.com/maps/api/geocode/json', {
      params: {
        address: locate,
        key: 'AIzaSyBAvqd6iVi-Nq7KnAZfGsJbqpgzztKm_V8'
      }
    })
      .then(function (response) {
        console.log(response);
var puntos = randomGeo({
      'latitude': response.data.results[0].geometry.location.lat,
      'longitude': response.data.results[0].geometry.location.lng
    }, 1000)
        
L.marker([puntos.latitude, puntos.longitude]).addTo(map)
      .bindPopup(element.querySelectorAll(".perfilNombre>a")[0].innerHTML + ': ' + element.querySelectorAll(".presupuesto")[0].innerHTML.split(" ")[1]+ '</br><a href='+element.querySelectorAll(".perfilNombre>a")[0].href+'>Ver Perfil</a>');
      })
      .catch(function (error) {
        console.log(error);
      });
  });
var enviar = document.querySelector(".enviar");
var objDiv = document.querySelector(".chatAbiertoMensajes");
objDiv.scrollTop = objDiv.scrollHeight;
enviar.addEventListener("click", enviarMensaje);
var chatId;
var input = document.getElementById("escribir");
input.addEventListener("keyup", function(event) {
  if (event.keyCode === 13) {
    event.preventDefault();
    document.querySelector(".enviar").click();
  }
});
function enviarMensaje() {
  var txt = document.getElementById("escribir").value;
 document.getElementById("escribir").value="";
  if (txt.length>=1) {
    
    var newMsg = document.createElement("div");
    newMsg.classList.add("mensaje");
    newMsg.classList.add("enviado");
    
    var msg = document.createElement("p");
    msg.classList.add("txt");
    var node = document.createTextNode(txt);
    msg.appendChild(node);

    var date = document.createElement("p");
    date.classList.add("date");
    var currentdate =  new Date().toLocaleTimeString('en-US', { hour12: false,hour: "numeric", minute: "numeric"});;
    var node = document.createTextNode(currentdate);
    date.appendChild(node);

    newMsg.appendChild(msg);
    newMsg.appendChild(date);
    document.querySelector(".chatAbiertoMensajes").appendChild(newMsg);
    
    var objDiv = document.querySelector(".chatAbiertoMensajes");
    objDiv.scrollTop = objDiv.scrollHeight;
    txt= txt.replace(/ /g, '%20');
    txt= txt.replace('?', '%3F');
    var url = "/sendMsg"+ txt+"_"+chatId;
  $('.chatAbiertoMensajes').load(url);
  $( document ).ajaxComplete(function() {
  var objDiv = document.querySelector(".chatAbiertoMensajes>.chatAbiertoMensajes");
    objDiv.scrollTop = objDiv.scrollHeight;
    var clickable= document.querySelectorAll(".clickalble");
if (clickable.length>0) {
  clickable.forEach(element => {
    element.addEventListener("click",responderProposicion);
  });
}
});
  }
}

var intervalID;
var chats = document.querySelectorAll(".chat");
chats.forEach(element => {
  element.addEventListener("click",openChat);
});
function openChat(e) {
  var chats = document.querySelector(".chatAbierto");
  chats.classList.remove("hide");
  var chats = document.querySelector(".chatCerrado");
  chats.classList.add("hide");
  var name = document.querySelector(".nombreChatAbierto");
  
 
  var id=e.srcElement;
  while (!id.classList.contains("chat")) {
    id=id.parentElement;
  
  }
  chatId=id.classList[1];
  var url = "/chat"+ id.classList[1];
  $('.chatAbiertoMensajes').load(url);
  $( document ).ajaxComplete(function() {
  var objDiv = document.querySelector(".chatAbiertoMensajes>.chatAbiertoMensajes");
    objDiv.scrollTop = objDiv.scrollHeight;
});
  name.innerHTML=id.querySelector(".nombre").innerHTML;
  intervalID = window.setInterval(getMessages, 20000, id.classList[1]);
  id.querySelector(".msgSinLeer").classList.add("hide");
}
var cerrar = document.querySelector(".cerrar").addEventListener("click",cerrarChat);
function cerrarChat() {
  var chats = document.querySelector(".chatAbierto");
  chats.classList.add("hide");
  var chats = document.querySelector(".chatCerrado");
  chats.classList.remove("hide");
  clearInterval(intervalID);
}
function getMessages(id) {
  var url = "/chat"+ id;
  $('.chatAbiertoMensajes').load(url);
    $( document ).ajaxComplete(function() {
  var objDiv = document.querySelector(".chatAbiertoMensajes>.chatAbiertoMensajes");
    objDiv.scrollTop = objDiv.scrollHeight;
    var clickable= document.querySelectorAll(".clickalble");
if (clickable.length>0) {
  clickable.forEach(element => {
    element.addEventListener("click",responderProposicion);
  });
}
});
}


document.getElementById("sendProposition").addEventListener("click", enviarProposicion);
function enviarProposicion() {
  var proposition = document.getElementById("proposition").value;
  console.log(proposition.length);
  if (proposition.length>0) {

    var url = "/sendPrp"+ proposition+"_"+chatId;
  $('.chatAbiertoMensajes').load(url);
  $( document ).ajaxComplete(function() {
  var objDiv = document.querySelector(".chatAbiertoMensajes>.chatAbiertoMensajes");
    objDiv.scrollTop = objDiv.scrollHeight;
});
  }
}


function responderProposicion(e) {
 
  console.log(e.srcElement.classList[0]);
  var action = e.srcElement.classList[0];
  var id=e.srcElement;
  while (!id.classList.contains("pDiv")) {
    id=id.parentElement;
  
  }
   var date=id.querySelector(".prop").innerHTML.split(":")[1];
  date= date.replace(/\s/g, '');
  id=id.querySelector(".prop").classList[1];
  console.log(date);

  var url = "/respPrp"+ action+"_"+id+"_"+date;
  $('.chatAbiertoMensajes').load(url);
  $( document ).ajaxComplete(function() {
  var objDiv = document.querySelector(".chatAbiertoMensajes>.chatAbiertoMensajes");
  objDiv.scrollTop = objDiv.scrollHeight;
});
}

var enviar = document.querySelector(".enviar");
var objDiv = document.querySelector(".chatAbiertoMensajes");
objDiv.scrollTop = objDiv.scrollHeight;
enviar.addEventListener("click", enviarMensaje);

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
  
  }
}
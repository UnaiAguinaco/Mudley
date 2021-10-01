var checkedValue = document.getElementsByClassName('checkbox');
let root = document.documentElement;

let slider = document.querySelectorAll('.slider');
if (slider.length==2) {
  slider[0].addEventListener("click",change);
  slider[1].addEventListener("click",change);
  $("#checkbox1").change(function() {
    $("#checkbox2").prop("checked", this.checked);
});
$("#checkbox2").change(function() {
    $("#checkbox1").prop("checked", this.checked);
});
}else{
  slider[0].addEventListener("click",change);
}


function change() {
  if (checkedValue[0].checked) {
  var logo = document.querySelectorAll(".logo>img");
  logo.forEach(element => {
    element.src="img/logoClaro.png";
  });
  root.style.setProperty('--backImg', "url(../img/fondoClaro1.jpg)");
  root.style.setProperty('--mainColor', "white");
  root.style.setProperty('--textColor', "black");
  root.style.setProperty('--borderColor', "black");
  }else if (!checkedValue[0].checked) {
    var logo = document.querySelectorAll(".logo>img");
  logo.forEach(element => {
    element.src="img/logoOscuro.png";
  });
    root.style.setProperty('--backImg', "url(../img/fondoOscuro5.jpg)");
  root.style.setProperty('--mainColor', "#1C1B29");
  root.style.setProperty('--textColor', "white");
  root.style.setProperty('--borderColor', "white");
  }
}

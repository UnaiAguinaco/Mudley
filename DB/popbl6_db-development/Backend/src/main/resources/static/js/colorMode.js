var checkedValue = document.getElementsByClassName('checkbox');
let root = document.documentElement;

let slider = document.querySelectorAll('.slider');

  slider[0].addEventListener("click",change);



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

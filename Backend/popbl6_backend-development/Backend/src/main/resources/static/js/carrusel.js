var next = document.querySelectorAll("#next");
next.forEach(element => {
  element.addEventListener("click", nextElements);
});
var prev = document.querySelectorAll("#prev");
prev.forEach(element => {
  element.addEventListener("click", prevElements);
});

function nextElements(params) {
  var all=params.srcElement;
  while (!all.classList.contains("contentGroup")) {
    all=all.parentElement;
  
  }
  all=all.querySelector(".contents");
  var n = all.classList[1];
  if (n!=3) {
  var content = all.querySelectorAll(".content");
  for (let index = 0; index < 12; index++) {
    if (!content[index].classList.contains("hide")) {
      content[index].classList.add("hide");
    }
    
  }
  content[4*n].classList.remove("hide");
  content[4*n+1].classList.remove("hide");
  content[4*n+2].classList.remove("hide");
  content[4*n+3].classList.remove("hide");
  var a = all.classList[1];
  a++;
  all.classList.remove(all.classList[1]);
  all.classList.add(a);
  }
  
}
function prevElements(params) {
  var all=params.srcElement;
  while (!all.classList.contains("contentGroup")) {
    all=all.parentElement;
  
  }
  all=all.querySelector(".contents");
  var n = all.classList[1];
  if (n!=1) {

  var content = all.querySelectorAll(".content");
  for (let index = 0; index < 12; index++) {
    if (!content[index].classList.contains("hide")) {
      content[index].classList.add("hide");
    }
    
  }
  n-=2;
  console.log(n);
  content[4*n].classList.remove("hide");
  content[4*n+1].classList.remove("hide");
  content[4*n+2].classList.remove("hide");
  content[4*n+3].classList.remove("hide");
  var a = all.classList[1];
  a--;
  all.classList.remove(all.classList[1]);
  all.classList.add(a);
  console.log(a);
  }
}
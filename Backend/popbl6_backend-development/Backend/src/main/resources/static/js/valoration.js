var rating = document.querySelectorAll(".stars");
rating.forEach(element => {
  
  if(element.classList[1]=="null"){
    for (let index = 0; index < 5; index++) {
      var tag = document.createElement("i");
      tag.classList.add("far");
      tag.classList.add("fa-star");
      element.appendChild(tag);
      
    }
  }else{
    if (element.classList[1] % 1 == 0) {
      var kop = element.classList[1];
      var rest = 5 - element.classList[1];
      for (let index = 0; index < kop; index++) {
        var tag = document.createElement("i");
      tag.classList.add("fas");
      tag.classList.add("fa-star");
      element.appendChild(tag);
        
      }
      for (let index = 0; index < rest; index++) {
        var tag = document.createElement("i");
      tag.classList.add("far");
      tag.classList.add("fa-star");
      element.appendChild(tag);
        
      }
      
    }else{
      var kop = element.classList[1] - (element.classList[1]% 1);
      var decimal = 0;
      if (element.classList[1]% 1>=0.7) {
        kop = kop + 1;
        decimal=-1;
      }else if(element.classList[1]% 1<=0.4) {
        decimal=-1;
      }
      console.log(kop);
      var rest = 5 - kop;
      for (let index = 0; index < kop; index++) {
        var tag = document.createElement("i");
      tag.classList.add("fas");
      tag.classList.add("fa-star");
      element.appendChild(tag);
        
      }
      if (decimal==0) {
        var tag = document.createElement("i");
        tag.classList.add("fas");
      tag.classList.add("fa-star-half-alt");
      element.appendChild(tag);
      rest=rest-1;
      }
      for (let index = 0; index < rest; index++) {
        var tag = document.createElement("i");
      tag.classList.add("far");
      tag.classList.add("fa-star");
      element.appendChild(tag);
        
      }
    }
  }
});
/*
                  <i class="fas fa-star"></i>
                  <i class="fas fa-star-half-alt"></i>

*/
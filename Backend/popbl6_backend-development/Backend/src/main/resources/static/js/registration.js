$("input:checkbox").on('click', function () {
    var $box = $(this);
    if ($box.is(":checked")) {
      var group = "input:checkbox[class='" + $box.attr("class") + "']";
      $(group).prop("checked", false);
      $box.prop("checked", true);
    } else {
      $box.prop("checked", false);
    }
    
    if ($box.attr('id')=="grupoCheck") {
      document.querySelector(".groupAddData").classList.remove("hide");
    }else if ($box.attr('id')=="asociacionCheck") {
      document.querySelector(".groupAddData").classList.add("hide");
    }
  });


  $(document).ready(function ($) {
    $('#pass').passtrength({
      minChars: 4,
      passwordToggle: true,
      tooltip: true
    });
  });
  $("#presupuesto").val("");
  $("#cv").val("");
  function verificarPasswords() {

    // Ontenemos los valores de los campos de contraseñas 
    var pass1 = document.getElementById('pass');
    var pass2 = document.getElementById('rpass');
    if (pass1.value != pass2.value) {
      if (!pass2.classList.contains("notSame")) {
        pass2.classList.add("notSame");
      }

      var submit = document.getElementById('create').disabled = true;
    } else if (pass1.value == pass2.value) {
      {
        if (pass2.classList.contains("notSame")) {
          pass2.classList.remove("notSame");
        }
        var submit = document.getElementById('create').disabled = false;
      }


    }
  }
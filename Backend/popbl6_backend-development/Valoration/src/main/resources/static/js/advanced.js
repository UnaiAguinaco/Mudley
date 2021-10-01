var submit = document.getElementById('saveButton');
  function submitForm() {
    if (!submit.classList.contains("disabled")) {
      document.getElementById("advancedData").submit();
    }

  }
  function verificarPasswords() {

    // Ontenemos los valores de los campos de contraseñas 
    var pass1 = document.getElementById('cNueva');
    var pass2 = document.getElementById('cRepetida');

    if (pass1.value != pass2.value) {
      if (!pass2.classList.contains("notSame")) {
        pass2.classList.add("notSame");
      }
      if (!submit.classList.contains("disabled")) {
        submit.classList.add("disabled");
      }

    } else if (pass1.value == pass2.value) {
      {
        if (pass2.classList.contains("notSame")) {
          pass2.classList.remove("notSame");
        }
        if (submit.classList.contains("disabled")) {
          submit.classList.remove("disabled");
        }
      }


    }
  }
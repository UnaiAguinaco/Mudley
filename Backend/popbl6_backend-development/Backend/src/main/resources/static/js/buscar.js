function submitForm() {
    if (document.getElementById("pLocalidad").value.length > 0 && document.getElementById("pFecha").value.length > 0 && document.getElementById("pGenero").value != 0 && document.getElementById("pPersupuesto").value.length > 0) {
      document.getElementById("buscadorContainer").submit();

    } else {
      alert("Completa todos los valores para realizar una busqueda.");
    }
  }
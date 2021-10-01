var buscador = document.querySelector(".buscador");
buscador.addEventListener("click", abrirParametros);

var cerrar = document.querySelector(".parametrosExit");
cerrar.addEventListener("click", cerrarParametros);

function abrirParametros() {
  var buscarParametros = document.querySelector(".buscadorParametros");
  buscarParametros.classList.remove("hide");
}

function cerrarParametros() {
  var buscarParametros = document.querySelector(".buscadorParametros");
  buscarParametros.classList.add("hide");
}
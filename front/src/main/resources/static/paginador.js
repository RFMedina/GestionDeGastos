function refrescarBST() {
  $("table[data-toggle]").bootstrapTable("refresh");
}

/* Esto funciona pero con los ID predefinidos (Es una prueba) */
function mostrarFormularioNuevoMovimiento(idGrupo) {
  $.ajax({
    url: "/gestion/grupo/" + idGrupo + "/nuevoMovimiento",
    success: function (formularioEditar) {
      bootbox
        .dialog({
          title: "Añade nuevo movimiento",
          message: formularioEditar,
        })
        .find("div.modal-content")
        .addClass("largeWidth");
    },
    error: function (err) {
      alert("Si la URL esta mal. Codigo 404");
    },
  });
}

function mostrarMiembrosGrupo(idGrupo) {
  $.ajax({
    url: idGrupo + "/gestionar",
    success: function (formularioEditar) {
      bootbox
        .dialog({
          title: "Gestión de usuarios",
          message: formularioEditar,
        })
        .find("div.modal-content")
        .addClass("largeWidth");
    },
    error: function (err) {
      alert("Si la URL esta mal. Codigo 404");
    },
  });
}

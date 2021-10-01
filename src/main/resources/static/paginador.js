function Pager(tableName, itemsPerPage) {
  "use strict";

  this.tableName = tableName;
  this.itemsPerPage = itemsPerPage;
  this.currentPage = 1;
  this.pages = 0;
  this.inited = false;

  this.showRecords = function (from, to) {
    let rows = document.getElementById(tableName).rows;

    for (let i = 1; i < rows.length; i++) {
      if (i < from || i > to) {
        rows[i].style.display = "none";
      } else {
        rows[i].style.display = "";
      }
    }
  };

  this.showPage = function (pageNumber) {
    if (!this.inited) {
      return;
    }

    let oldPageAnchor = document.getElementById("pg" + this.currentPage);
    oldPageAnchor.className = "pg-normal";

    this.currentPage = pageNumber;
    let newPageAnchor = document.getElementById("pg" + this.currentPage);
    newPageAnchor.className = "pg-selected";

    let from = (pageNumber - 1) * itemsPerPage + 1;
    let to = from + itemsPerPage - 1;
    this.showRecords(from, to);
  };

  this.prev = function () {
    if (this.currentPage > 1) {
      this.showPage(this.currentPage - 1);
    }
  };

  this.next = function () {
    if (this.currentPage < this.pages) {
      this.showPage(this.currentPage + 1);
    }
  };

  this.init = function () {
    let rows = document.getElementById(tableName).rows;
    let records = rows.length - 1;

    this.pages = Math.ceil(records / itemsPerPage);
    this.inited = true;
  };

  this.showPageNav = function (pagerName, positionId) {
    if (!this.inited) {
      return;
    }

    let element = document.getElementById(positionId),
      pagerHtml =
        '<span onclick="' +
        pagerName +
        '.prev();" class="pg-normal pg-prev">Anterior</span>';

    for (let page = 1; page <= this.pages; page++) {
      pagerHtml +=
        '<span id="pg' +
        page +
        '" class="pg-normal pg-next" onclick="' +
        pagerName +
        ".showPage(" +
        page +
        ');">' +
        page +
        "</span>";
    }

    pagerHtml +=
      '<span onclick="' +
      pagerName +
      '.next();" class="pg-normal">Siguiente</span>';

    element.innerHTML = pagerHtml;
  };
}
let pager = new Pager("pager", 7);

pager.init();
pager.showPageNav("pager", "pageNavPosition");
pager.showPage(1);

//Esta función filtra los elementos en pantalla
document.querySelector("#buscar").onkeyup = function () {
  $TableFilter("#pager", this.value);
};

$TableFilter = function (id, value) {
  var rows = document.querySelectorAll(id + " tbody tr");

  for (var i = 0; i < rows.length; i++) {
    var showRow = false;

    var row = rows[i];
    row.style.display = "none";

    for (var x = 0; x < row.childElementCount; x++) {
      if (
        row.children[x].textContent
          .toLowerCase()
          .indexOf(value.toLowerCase().trim()) > -1
      ) {
        showRow = true;
        break;
      }
    }

    if (showRow) {
      row.style.display = null;
    }
  }
};

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

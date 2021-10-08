document.getElementById("id-sun").onclick = function () {
  document.getElementById("id-sun").classList.add("activo");
  document.getElementById("id-moon").classList.remove("activo");
  document.getElementById("id-thanos").classList.remove("activo");
  document.getElementById("id-batman").classList.remove("activo");
  document.getElementById("id-spiderman").classList.remove("activo");
  document.getElementById("id-hulk").classList.remove("activo");

  localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
  localStorage.setItem("capitanAmerica", capitanAmerica.getAttribute("class"));
  localStorage.setItem("batman", batman.getAttribute("class"));
  localStorage.setItem("hulk", hulk.getAttribute("class"));
  localStorage.setItem("spiderman", spiderman.getAttribute("class"));
  localStorage.setItem("thanos", thanos.getAttribute("class"));
};

document.getElementById("id-moon").onclick = function () {
  this.classList.add("activo");
  document.getElementById("id-sun").classList.remove("activo");
  document.getElementById("id-thanos").classList.remove("activo");
  document.getElementById("id-batman").classList.remove("activo");
  document.getElementById("id-spiderman").classList.remove("activo");
  document.getElementById("id-hulk").classList.remove("activo");

  localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
  localStorage.setItem("capitanAmerica", capitanAmerica.getAttribute("class"));
  localStorage.setItem("batman", batman.getAttribute("class"));
  localStorage.setItem("hulk", hulk.getAttribute("class"));
  localStorage.setItem("spiderman", spiderman.getAttribute("class"));
  localStorage.setItem("thanos", thanos.getAttribute("class"));
};

document.getElementById("id-thanos").onclick = function () {
  this.classList.add("activo");
  document.getElementById("id-sun").classList.remove("activo");
  document.getElementById("id-moon").classList.remove("activo");
  document.getElementById("id-batman").classList.remove("activo");
  document.getElementById("id-spiderman").classList.remove("activo");
  document.getElementById("id-hulk").classList.remove("activo");

  localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
  localStorage.setItem("capitanAmerica", capitanAmerica.getAttribute("class"));
  localStorage.setItem("batman", batman.getAttribute("class"));
  localStorage.setItem("hulk", hulk.getAttribute("class"));
  localStorage.setItem("spiderman", spiderman.getAttribute("class"));
  localStorage.setItem("thanos", thanos.getAttribute("class"));
};

document.getElementById("id-batman").onclick = function () {
  this.classList.add("activo");
  document.getElementById("id-sun").classList.remove("activo");
  document.getElementById("id-moon").classList.remove("activo");
  document.getElementById("id-thanos").classList.remove("activo");
  document.getElementById("id-spiderman").classList.remove("activo");
  document.getElementById("id-hulk").classList.remove("activo");

  localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
  localStorage.setItem("capitanAmerica", capitanAmerica.getAttribute("class"));
  localStorage.setItem("batman", batman.getAttribute("class"));
  localStorage.setItem("hulk", hulk.getAttribute("class"));
  localStorage.setItem("spiderman", spiderman.getAttribute("class"));
  localStorage.setItem("thanos", thanos.getAttribute("class"));
};

document.getElementById("id-spiderman").onclick = function () {
  this.classList.add("activo");
  document.getElementById("id-sun").classList.remove("activo");
  document.getElementById("id-moon").classList.remove("activo");
  document.getElementById("id-thanos").classList.remove("activo");
  document.getElementById("id-batman").classList.remove("activo");
  document.getElementById("id-hulk").classList.remove("activo");

  localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
  localStorage.setItem("capitanAmerica", capitanAmerica.getAttribute("class"));
  localStorage.setItem("batman", batman.getAttribute("class"));
  localStorage.setItem("hulk", hulk.getAttribute("class"));
  localStorage.setItem("spiderman", spiderman.getAttribute("class"));
  localStorage.setItem("thanos", thanos.getAttribute("class"));
};

document.getElementById("id-hulk").onclick = function () {
  this.classList.add("activo");
  document.getElementById("id-sun").classList.remove("activo");
  document.getElementById("id-moon").classList.remove("activo");
  document.getElementById("id-thanos").classList.remove("activo");
  document.getElementById("id-batman").classList.remove("activo");
  document.getElementById("id-spiderman").classList.remove("activo");

  localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
  localStorage.setItem("capitanAmerica", capitanAmerica.getAttribute("class"));
  localStorage.setItem("batman", batman.getAttribute("class"));
  localStorage.setItem("hulk", hulk.getAttribute("class"));
  localStorage.setItem("spiderman", spiderman.getAttribute("class"));
  localStorage.setItem("thanos", thanos.getAttribute("class"));
};

var cssStyle = document.getElementById("style");
var viudaNegra = document.getElementById("id-sun");
var capitanAmerica = document.getElementById("id-moon");
var batman = document.getElementById("id-batman");
var hulk = document.getElementById("id-hulk");
var spiderman = document.getElementById("id-spiderman");
var thanos = document.getElementById("id-thanos");

function setStyle(newStyle) {
  cssStyle.href = newStyle;

  if (localStorage) {
    localStorage.setItem("style", newStyle);
    localStorage.setItem("viudaNegra", viudaNegra.getAttribute("class"));
    localStorage.setItem(
      "capitanAmerica",
      capitanAmerica.getAttribute("class")
    );
    localStorage.setItem("batman", batman.getAttribute("class"));
    localStorage.setItem("hulk", hulk.getAttribute("class"));
    localStorage.setItem("spiderman", spiderman.getAttribute("class"));
    localStorage.setItem("thanos", thanos.getAttribute("class"));
  }
}

window.onload = function () {
  if (localStorage && localStorage.getItem("style")) {
    cssStyle.href = localStorage.getItem("style");
  }

  if (localStorage && localStorage.getItem("viudaNegra")) {
    $(viudaNegra).removeAttr("class");
    $(viudaNegra).addClass(localStorage.getItem("viudaNegra"));
  }

  if (localStorage && localStorage.getItem("capitanAmerica")) {
    $(capitanAmerica).removeAttr("class");
    $(capitanAmerica).addClass(localStorage.getItem("capitanAmerica"));
  }

  if (localStorage && localStorage.getItem("batman")) {
    $(batman).removeAttr("class");
    $(batman).addClass(localStorage.getItem("batman"));
  }

  if (localStorage && localStorage.getItem("hulk")) {
    $(hulk).removeAttr("class");
    $(hulk).addClass(localStorage.getItem("hulk"));
  }

  if (localStorage && localStorage.getItem("spiderman")) {
    $(spiderman).removeAttr("class");
    $(spiderman).addClass(localStorage.getItem("spiderman"));
  }

  if (localStorage && localStorage.getItem("thanos")) {
    $(thanos).removeAttr("class");
    $(thanos).addClass(localStorage.getItem("thanos"));
  }
};

const lista = document.querySelectorAll(".lista");

function inicio() {
  window.location("/gestion/inicio");
}

function activarSombra() {
  lista.forEach((item) => item.classList.remove("active"));
  this.classList.add("active");
}

lista.forEach((item) => item.addEventListener("mouseover", activarSombra));

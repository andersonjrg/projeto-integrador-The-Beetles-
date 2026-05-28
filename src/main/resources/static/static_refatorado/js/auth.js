(function () {
    const token = localStorage.getItem("token");
    if (!token) {
        alert("Você precisa estar logado para acessar esta página!");
        window.location.href = "/login.html";
    }
})();
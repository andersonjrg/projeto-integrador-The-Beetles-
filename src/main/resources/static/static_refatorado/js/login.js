const frm = document.querySelector("form");
const inEmail = document.querySelector("#inEmail");
const inSenha = document.querySelector("#inSenha")

frm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = inEmail.value;
    const senha = inSenha.value;
    try {
        const body = await Api.login({ email, senha });
        localStorage.setItem("token", body.token);

        window.location.href="adminDashboard.html";
    } catch (error) {
        alert(error.message);
    }
})
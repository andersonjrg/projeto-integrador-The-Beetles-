const inEmail = document.querySelector("#inEmail");
const inSenhaNova = document.querySelector("#inSenhaNova");
const frm = document.querySelector("form");

frm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = inEmail.value;
    const senha = inSenhaNova.value;
    try {
        const body = await Api.alterarSenha({ email, senha });
        alert("Senha alterada com sucesso");
    } catch (error) {
        alert(error?.message || "Erro ao alterar senha")
    }
})
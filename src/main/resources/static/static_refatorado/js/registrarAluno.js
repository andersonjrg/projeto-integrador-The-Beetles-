const inNome = document.querySelector("#inNome");
const inStatus = document.querySelector("#inStatus");
const selectPlanos = document.querySelector("#planos");
const inDataInicio = document.querySelector("#inDataInicio")

const frm = document.querySelector("form");

async function getPlanosNome() {
    try {
        const planos = await Api.getPlanos();
        const lista = planos.content ?? [];
        let options = ``;
        for (const plano of lista) {
            options += `
                <option value="${plano.planoId}">${plano.nome}
                </option>
                `
        }
        selectPlanos.innerHTML += options
    } catch (error) {
        alert(error?.message || "Erro ao carregar planos");
    }

}

frm.addEventListener("submit", async (e) => {
    e.preventDefault();

    const nome = inNome.value;
    const plano = selectPlanos.value || null;
    const status = inStatus.value;
    const dataInicioPlano = inDataInicio.value 
    ? new Date(inDataInicio.value).toISOString() 
    : null;

    try {
        await Api.registrarAluno({ nome, plano, status, dataInicioPlano });
        alert("Aluno registrado com sucesso!");
        frm.reset();
    } catch (error) {
        alert(error.message);
    }

})

getPlanosNome();
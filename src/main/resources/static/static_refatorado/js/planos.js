const inNome = document.querySelector("#inNome");
const inCat = document.querySelector("#inCat");
const inFreq = document.querySelector("#inFreq");
const inValor = document.querySelector("#inValor");
const inStatusPlano = document.querySelector("#inStatusPlano");
const tabelaPlanos = document.querySelector("#tabelaPlanos");
const frm = document.querySelector("form");

if (!frm || !tabelaPlanos) {
    console.error("Elementos do formulário ou da tabela de planos não foram encontrados.");
}

if (frm) {
    frm.addEventListener("submit", async (e) => {
        e.preventDefault();

        const nome = inNome.value;
        const categoria = inCat.value;
        const freq = inFreq.value;
        const valor = inValor.value;
        const statusPlano = inStatusPlano.value;

        try {
            await Api.registrarPlano({
                nome,
                categoria,
                frequenciaAulas: parseInt(freq),
                valor: parseFloat(valor),
                ativo: statusPlano === "Ativo"
            });
            frm.reset();
            getPlanos();
        } catch (error) {
            alert(error?.message || "Erro ao registrar plano");
        }
    });
}


let paginaPlanos = 0;
async function getPlanos(pagina = 0) {
    try {
        paginaPlanos = pagina;
        const planos = await Api.getPlanos(pagina);
        const lista = planos.content ?? [];
        const totalPaginas = planos.totalPages ?? 1;

        let tabHtml = `
            <thead>
                <tr>
                    <th scope="col">Nome</th>
                    <th scope="col">Categoria</th>
                    <th scope="col">Valor</th>
                    <th scope="col">Frequência</th>
                    <th scope="col">Status</th>
                    <th scope="col">Ações</th>

                </tr>
            </thead>
            <tbody>
        `;

        for (const plano of lista) {
            tabHtml += `
        <tr id="linha-${plano.planoId}">
            <td><input id="nome-${plano.planoId}" value="${plano.nome ?? ""}" disabled></td>
            <td><input id="cat-${plano.planoId}" value="${plano.categoria ?? ""}" disabled></td>
            <td><input id="valor-${plano.planoId}" type="number" value="${plano.valor ?? ""}" disabled></td>
            <td><input id="freq-${plano.planoId}" type="number" value="${plano.frequenciaAulas ?? 0}" disabled></td>
            <td>
                <span id="status-${plano.planoId}">${plano.ativo ? "Ativo" : "Inativo"}</span>
                    <select id="selectStatus-${plano.planoId}" style="display:none">
                        <option value="true">Ativo</option>
                        <option value="false">Inativo</option>
                    </select>
            </td>
            <td>
                <button onclick="habilitarEdicaoPlano('${plano.planoId}')">✏️</button>
                <button id="btnSalvarPlano-${plano.planoId}" style="display:none" onclick="salvarPlano('${plano.planoId}')">💾</button>
                <button onclick="deletarPlano('${plano.planoId}')">Excluir</button>
            </td>
        </tr>
    `;
        }

        tabHtml += `</tbody>`;
        tabelaPlanos.innerHTML = tabHtml;
        renderPaginacao("paginacaoPlanos", paginaPlanos, totalPaginas, getPlanos);
    } catch (error) {
        alert(error?.message || "Erro ao carregar planos");
    }
}

async function deletarPlano(id) {
    if (!confirm("Atenção: todos os alunos vinculados a este plano ficarão sem plano. Deseja continuar?")) return;
    try {
        await Api.deletarPlano(id);
        alert("Plano excluído com sucesso!");
        getPlanos();
    } catch (error) {
        alert(error?.message || "Erro ao excluir plano");
    }
}

function habilitarEdicaoPlano(id) {
    document.getElementById(`nome-${id}`).disabled = false;
    document.getElementById(`cat-${id}`).disabled = false;
    document.getElementById(`valor-${id}`).disabled = false;
    document.getElementById(`freq-${id}`).disabled = false;

    const statusAtual = document.getElementById(`status-${id}`).textContent.trim();
    const selectStatus = document.getElementById(`selectStatus-${id}`);
    selectStatus.value = statusAtual === "Ativo" ? "true" : "false";
    document.getElementById(`status-${id}`).style.display = "none";
    selectStatus.style.display = "inline";

    document.getElementById(`btnSalvarPlano-${id}`).style.display = "inline";
}
async function salvarPlano(id) {
    const dados = {
        nome: document.getElementById(`nome-${id}`).value,
        categoria: document.getElementById(`cat-${id}`).value,
        frequenciaAulas: parseInt(document.getElementById(`freq-${id}`).value),
        valor: parseFloat(document.getElementById(`valor-${id}`).value),
        ativo: document.getElementById(`selectStatus-${id}`).value === "true",
    };

    try {
        await Api.atualizarPlano(id, dados);
        alert("Plano atualizado com sucesso!");
        document.getElementById(`selectStatus-${id}`).style.display = "none";
        document.getElementById(`status-${id}`).style.display = "inline";
        getPlanos();
        
    } catch (error) {
        alert(error?.message || "Erro ao atualizar plano");
    }
}

getPlanos();
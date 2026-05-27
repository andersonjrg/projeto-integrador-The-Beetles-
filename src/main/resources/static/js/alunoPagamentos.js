const params = new URLSearchParams(window.location.search);
const alunoId = params.get("id");

if (!alunoId) window.location.href = "aluno.html";

const frm = document.querySelector("form");
const nomeAluno = document.querySelector("#nomeAluno");
const planoAluno = document.querySelector("#planoAluno");
const statusAluno = document.querySelector("#statusAluno");
const vencimentoAluno = document.querySelector("#vencimentoAluno");
const tabelaPagamentos = document.querySelector("#tabelaPagamentos");
const inValor = document.querySelector("#inValor");
let planoAtualId = null;

const camposEditados = {};

function habilitarEdicao(campoId) {
    const input = document.querySelector(`#${campoId}`);
    input.disabled = false;
    input.focus();
    camposEditados[campoId] = true;
    document.querySelector("#btnSalvar").style.display = "inline-block";
}

async function habilitarEdicaoPlano() {
    const resultado = await Api.getPlanos();
    const planos = resultado.content ?? resultado;
    const select = document.querySelector("#selectPlano");
    select.innerHTML = `<option value="">Nenhum</option>`;
    for (const p of planos) {
        select.innerHTML += `<option value="${p.planoId}">${p.nome}</option>`;
    }
    select.value = planoAtualId ?? "";

    document.querySelector("#planoAluno").style.display = "none";
    select.style.display = "inline";
    camposEditados["plano"] = true;
    document.querySelector("#btnSalvar").style.display = "inline-block";
}

function habilitarEdicaoStatus() {
    const statusAtual = document.querySelector("#statusAluno").textContent.trim();
    const select = document.querySelector("#selectStatus");
    select.value = statusAtual;
    document.querySelector("#statusAluno").style.display = "none";
    select.style.display = "inline";
    camposEditados["status"] = true;
    document.querySelector("#btnSalvar").style.display = "inline-block";
}

async function salvarAluno() {
    const dados = {
        nome: document.querySelector("#nomeAluno").value,
        status: camposEditados["status"]
            ? document.querySelector("#selectStatus").value
            : document.querySelector("#statusAluno").textContent.trim(),
        plano: camposEditados["plano"]
            ? (document.querySelector("#selectPlano").value || null)
            : planoAtualId,
        dataInicioPlano: null
    };

    try {
        await Api.atualizarAluno(alunoId, dados);
        alert("Aluno atualizado com sucesso!");

        document.querySelector("#selectPlano").style.display = "none";
        document.querySelector("#planoAluno").style.display = "inline";
        document.querySelector("#selectStatus").style.display = "none";
        document.querySelector("#statusAluno").style.display = "inline";
        document.querySelector("#nomeAluno").disabled = true;
        document.querySelector("#btnSalvar").style.display = "none";

        Object.keys(camposEditados).forEach(k => delete camposEditados[k]);

        carregarAluno();
    } catch (error) {
        alert(error?.message || "Erro ao atualizar aluno");
    }
}

async function carregarAluno() {
    try {
        const aluno = await Api.getAlunoPorId(alunoId);

        nomeAluno.value = aluno.nome ?? "";
        document.querySelector("#planoAluno").textContent = aluno.planoNome ?? "Nenhum";
        document.querySelector("#statusAluno").textContent = aluno.status ?? "";
        document.querySelector("#vencimentoAluno").textContent = formatarData(aluno.diaVencimento);

        planoAtualId = aluno.planoId ?? null;
    } catch (error) {
        alert(error?.message || "Erro ao carregar aluno");
    }
}

async function carregarPagamentos() {
    try {
        const pagamentos = await Api.getPagamentosAluno(alunoId);
        const lista = pagamentos.content ?? pagamentos ?? [];

        let tabHtml = `
            <thead>
                <tr>
                    <th>Status</th>
                    <th>Valor</th>
                    <th>Data Solicitação</th>
                    <th>Data Confirmação</th>
                    <th>Ações</th>
                </tr>
            </thead>
            <tbody>
        `;

        for (const p of lista) {
            tabHtml += `
                <tr>
                    <td>${p.statusPagamento ?? ""}</td>
                    <td>${p.valorCobrado ?? ""}</td>
                    <td>${formatarData(p.dataSolicitacao) ?? ""}</td>
                    <td>${formatarData(p.dataConfirmacao) ?? ""}</td>
                    <td>
                        <button onclick="confirmarPagamento('${p.id}')">Confirmar</button>
                        <button onclick="deletarPagamento('${p.id}')">Excluir</button>
                    </td>
                </tr>
            `;
        }

        tabHtml += `</tbody>`;
        tabelaPagamentos.innerHTML = tabHtml;
    } catch (error) {
        alert(error?.message || "Erro ao carregar pagamentos");
    }
}

async function confirmarPagamento(id) {
    try {
        await Api.confirmarPagamento(id);
        alert("Pagamento confirmado!");
        carregarPagamentos();
    } catch (error) {
        alert(error?.message || "Erro ao confirmar pagamento");
    }
}

frm.addEventListener("submit", async (e) => {
    e.preventDefault();
    const valor = inValor.value;
    try {
        await Api.registrarPagamento({ alunoId, valor });
        alert("Pagamento registrado");
        carregarPagamentos();
    } catch (error) {
        alert(error?.message || "Erro ao carregar Pagamentos");
    }
});

async function deletarPagamento(id) {
    if (!confirm("Tem certeza que deseja excluir este pagamento?")) return;
    try {
        await Api.deletarPagamento(id);
        alert("Pagamento excluído com sucesso!");
        carregarPagamentos();
    } catch (error) {
        alert(error?.message || "Erro ao excluir pagamento");
    }
}

carregarAluno();
carregarPagamentos();
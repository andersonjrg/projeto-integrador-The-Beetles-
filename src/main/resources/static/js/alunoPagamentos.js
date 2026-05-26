const params = new URLSearchParams(window.location.search);
const alunoId = params.get("id");

if (!alunoId) window.location.href = "aluno.html";

const frm = document.querySelector("form");
const nomeAluno = document.querySelector("#nomeAluno");
const planoAluno = document.querySelector("#planoAluno");
const statusAluno = document.querySelector("#statusAluno");
const vencimentoAluno = document.querySelector("#vencimentoAluno");
const tabelaPagamentos = document.querySelector("#tabelaPagamentos");
const inValor = document.querySelector("#inValor")

async function carregarAluno() {
    try {
        const aluno = await Api.getAlunoPorId(alunoId);

        nomeAluno.textContent = aluno.nome ?? "";
        planoAluno.textContent = aluno.planoNome ?? "Nenhum";
        statusAluno.textContent = aluno.status ?? "";
        vencimentoAluno.textContent = formatarData(aluno.diaVencimento) ?? "";
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
                    <th>Ação</th>
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
                        <button onclick="confirmarPagamento('${p.id}')">
                            Confirmar
                        </button>
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
        await Api.registrarPagamento({alunoId, valor});
        alert("Pagamento registrado");
        carregarPagamentos();

    } catch (error) {
        alert(error?.message || "Erro ao carregar Pagamentos");
    }
})


carregarAluno();
carregarPagamentos();
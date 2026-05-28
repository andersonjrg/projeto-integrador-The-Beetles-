const BASE_URL = "http://localhost:8080";


async function request(endpoint, options = {}) {
    const token = localStorage.getItem("token");
    const headers = { "Content-type": "application/json" };
    if (token) {
        headers["Authorization"] = "Bearer " + token;
    }
    const response = await fetch(`${BASE_URL}/${endpoint}`, {
        ...options,
        headers: { ...headers, ...options.headers }
    });
    const text = await response.text();
    const data = text ? JSON.parse(text): {};
    if (!response.ok) {
        throw new Error((data.detail) || "Erro no sistema")
    }
    return data;
}
const Api = {
    //Autenticação
    login: (dados) => request("auth/login", { method: "POST", body: JSON.stringify(dados) }),
    alterarSenha: (dados) => request("auth/admin/alterarsenha", { method: "POST", body: JSON.stringify(dados) }),
    
    //Aluno
    registrarAluno: (dados) => request("aluno/register", { method: "POST", body: JSON.stringify(dados) }),
    getAlunos: (pagina = 0) => request(`aluno/all?page=${pagina}&size=10`),
    getAlunoPorId: (id) => request(`aluno/getId/${id}`),
    atualizarAluno: (id, dados) => request(`aluno/update/${id}`, { method: "PUT", body: JSON.stringify(dados) }),
    deletarAluno: (id) => request(`aluno/delete/${id}`, { method: "DELETE" }),

    //Planos
    registrarPlano: (dados) => request("plano/salvar", { method: "POST", body: JSON.stringify(dados)}),
    getPlanos: (pagina = 0) => request(`plano/buscar?page=${pagina}&size=10`),
    getPlanoPorId: (id) => request(`plano/buscar/${id}`),
    atualizarPlano: (id, dados) => request(`plano/atualizar/${id}`, { method: "PUT", body: JSON.stringify(dados) }),
    deletarPlano: (id) => request(`plano/deletar/${id}`, { method: "DELETE" }),

    //Historico (Pagamentos)
    registrarPagamento: (dados) => request("historico/salvar", {method: "POST", body: JSON.stringify(dados)}),
    getPagamentos: (pagina = 0) => request(`historico/findAll?page=${pagina}&size=10`),
    getPagamentosAluno: (id) => request(`historico/getAllByAluno/${id}`),
    getPagamentoPorId: (id) => request(`historico/${id}`), 
    confirmarPagamento: (id) => request(`historico/confirmar/${id}`, { method: "POST", body: JSON.stringify(id)}),
    deletarPagamento: (id) => request(`historico/deletar/${id}`, { method: "DELETE" })
}
function formatarData(dataISO) {
    if (!dataISO) return "—";
    return new Date(dataISO).toLocaleDateString("pt-BR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric"
    });
}

function renderPaginacao(containerId, paginaAtual, totalPaginas, callback) {
    const container = document.getElementById(containerId);
    if (!container) return;

    container.className = "paginacao";

    let html = "";
    if (paginaAtual > 0)
        html += `<button onclick="${callback.name}(${paginaAtual - 1})">← Anterior</button>`;

    html += `<span>Página ${paginaAtual + 1} de ${totalPaginas}</span>`;

    if (paginaAtual < totalPaginas - 1)
        html += `<button onclick="${callback.name}(${paginaAtual + 1})">Próxima →</button>`;

    container.innerHTML = html;
}
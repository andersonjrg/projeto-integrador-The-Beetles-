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
    const data = await response.json()
    if (!response.ok) {
        throw new Error((data.message) || "Erro no sistema")
    }
    return data;
}
const Api = {
    //Autenticação
    login: (dados) => request("auth/login", { method: "POST", body: JSON.stringify(dados) }),
    alterarSenha: (dados) => request("auth/admin/alterarsenha", { method: "POST", body: JSON.stringify(dados) }),
    
    //Aluno
    registrarAluno: (dados) => request("aluno/register", { method: "POST", body: JSON.stringify(dados) }),
    getAlunos: () => request("aluno/all"),
    getAlunoPorId: (id) => request(`aluno/getId/${id}`),
    atualizarAluno: (id, dados) => request(`aluno/update/${id}`, { method: "PUT", body: JSON.stringify(dados) }),
    deletarAluno: (id) => request(`aluno/delete/${id}`, { method: "DELETE" }),

    //Planos
    registrarPlano: (dados) => request("plano/salvar", { method: "POST", body: JSON.stringify(dados)}),
    getPlanos: () => request("plano/buscar"),
    getPlanoPorId: (id) => request(`plano/buscar/${id}`),
    atualizarPlano: (id, dados) => request(`plano/atualizar/${id}`, { method: "PUT", body: JSON.stringify(dados) }),
    deletarPlano: (id) => request(`plano/deletar/${id}`, { method: "DELETE" }),

    //Historico (Pagamentos)
    registrarPagamento: (dados) => request("historico/salvar", {method: "POST", body: JSON.stringify(dados)}),
    getPagamentos: () => request("historico/findAll"),
    getPagamentosAluno: (id) => request(`historico/getAllByAluno/${id}`),
    getPagamentoPorId: (id) => request(`historico/${id}`), 
    confirmarPagamento: (id) => request(`historico/confirmar/${id}`, { method: "POST", body: JSON.stringify(id)})
}
function formatarData(dataISO) {
    if (!dataISO) return "—";
    return new Date(dataISO).toLocaleDateString("pt-BR", {
        day: "2-digit",
        month: "2-digit",
        year: "numeric"
    });
}
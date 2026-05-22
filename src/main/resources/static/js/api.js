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
    alterarSenha: (dados) => request("auth/?", { method: "POST", body: JSON.stringify(dados) }),
    registrarAluno: (dados) => request("auth/aluno/register", { method: "POST", body: JSON.stringify(dados) }),

    //Aluno
    getAlunos: () => request("aluno/all"),
    getAlunoPorId: (id) => request(`aluno/getId/${id}`),
    atualizarAluno: (id, dados) => request(`aluno/update/${id}`, { method: "PUT", body: JSON.stringify(dados) }),
    deletarAluno: (id) => request(`aluno/delete/${id}`, { method: "DELETE" }),

    //Planos

    //Historico


}
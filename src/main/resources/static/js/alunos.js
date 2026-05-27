const tabelaAlunos = document.querySelector("#tabelaAlunos")
let paginaAlunos = 0;
async function getAlunos(pagina = 0) {
    try {
        paginaAlunos = pagina;
        const alunos = await Api.getAlunos(pagina);
        const lista = alunos.content ?? [];
        const totalPaginas = alunos.totalPages ?? 1;
        let tabHtml =
            `<thead>
                <th scope="col">Nome</th>
                <th scope="col">Plano Escolhido</th>
                <th scope="col">Status</th>
                <th scope="col">Vencimento</th>
                <th scope="col">Ações</th>
            </thead>
            <tbody>`
            ;

        for (const aluno of lista) {
            tabHtml += `
                <tr>
                    <td>
                    <a href="aluno.html?id=${aluno.id}" style="cursor:pointer">
                    ${aluno.nome ?? ""}
                    </a>
                    </td>
                    <td>${aluno.planoNome ?? "Nenhum"}</td>
                    <td>${aluno.status ?? ""}</td>
                    <td>${formatarData(aluno.diaVencimento) ?? ""}</td>
                    <td><button onclick="deletarAluno('${aluno.id}')">Excluir</button></td>
                </tr>
            `;
        }

        tabHtml += `</tbody>`;
        tabelaAlunos.innerHTML = tabHtml;
        renderPaginacao("paginacaoAlunos", paginaAlunos, totalPaginas, getAlunos);
    } catch (error) {
        alert(error?.message || "Erro ao carregar alunos");
    }
}
async function deletarAluno(id) {
    if (!confirm("Ao excluir um aluno, você também estará excluindo todos seus pagamentos.\nVocê tem certeza que deseja excluir o aluno?")) return;
    try {
        await Api.deletarAluno(id);
        alert("Aluno excluído com sucesso!");
        getAlunos();
    } catch (error) {
        alert(error?.message || "Erro ao excluir aluno");
    }
}
getAlunos();
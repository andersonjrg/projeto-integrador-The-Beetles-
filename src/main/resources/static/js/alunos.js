const tabelaAlunos = document.querySelector("#tabelaAlunos")

async function getAlunos() {
    try {
        const alunos = await Api.getAlunos();
        const lista = alunos.content ?? [];

        let tabHtml =
            `<thead>
                <th scope="col">Nome</th>
                <th scope="col">Plano Escolhido</th>
                <th scope="col">Status</th>
                <th scope="col">Vencimento</th>
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
                </tr>
            `;
        }

        tabHtml += `</tbody>`;
        tabelaAlunos.innerHTML = tabHtml;
    } catch (error) {
        alert(error?.message || "Erro ao carregar alunos");
    }
}
getAlunos();
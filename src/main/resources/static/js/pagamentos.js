const tabelaPagamentos = document.querySelector("#tabelaPagamentos")
let paginaPagamentos = 0;
async function getPagamentos(pagina = 0) {
    try {
        paginaPagamentos = pagina;
        const pagamentos = await Api.getPagamentos(pagina);
        const lista = pagamentos.content ?? [];
        const totalPaginas = pagamentos.totalPages ?? 1;

        let tabHtml =
            `<thead>
                <th scope="col">Nome</th>
                <th scope="col">Valor(R$)</th>
                <th scope="col">Status</th>
                <th scope="col">Solicitação</th>
                <th scope="col">Confirmação</th>
            </thead>
            <tbody>`
            ;

        for (const pagamento of lista) {
            tabHtml += `
                <tr>
                    <td>${pagamento.nomeAluno ?? ""}</td>
                    <td>${pagamento.valorCobrado ?? "Nenhum"}</td>
                    <td>${pagamento.statusPagamento ?? ""}</td>
                    <td>${formatarData(pagamento.dataSolicitacao) ?? ""}</td>
                    <td>${formatarData(pagamento.dataConfirmacao) ?? ""}</td>
                </tr>
            `;
        }

        tabHtml += `</tbody>`;
        tabelaPagamentos.innerHTML = tabHtml;
         renderPaginacao("paginacaoPagamentos", paginaPagamentos, totalPaginas, getPagamentos);
    } catch (error) {
        alert(error?.message || "Erro ao carregar Pagamentos");
    }
}
getPagamentos()
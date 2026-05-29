CREATE TABLE administrador(
    admin_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    role VARCHAR(255),
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL
);

CREATE TABLE planos(
    plano_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    valor NUMERIC NOT NULL,
    ativo BOOLEAN,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
   frequencia_aulas INT NOT NULL DEFAULT 0
);

CREATE TABLE alunos(
    aluno_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    nome VARCHAR(255) NOT NULL,
    plano_escolhido_id UUID REFERENCES planos(plano_id) NULL,
    status VARCHAR(255),
    dia_vencimento TIMESTAMP,
    data_inicio_plano TIMESTAMP,
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE historico_pagamento(
    historico_id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    aluno_id UUID REFERENCES alunos(aluno_id),
    valor_cobrado NUMERIC NOT NULL,
    status_pagamento VARCHAR(255) NOT NULL,
    data_solicitacao TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    data_confirmacao TIMESTAMP
);
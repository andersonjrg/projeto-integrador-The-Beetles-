# 🥊 System Payout — The Beetles

Sistema de Controle de alunos para academia de Muay Thai, desenvolvido como projeto integrador pelo grupo **The Beetles**.

---

## 📋 Sobre o Projeto

O **System Payout** é uma aplicação web full-stack para controle financeiro e de alunos de uma academia de Muay Thai. O sistema permite que administradores gerenciem alunos, planos de treino e histórico de pagamentos por meio de uma interface web integrada com uma API REST segura.

---

## 🚀 Tecnologias Utilizadas

**Back-end**
- Java 17
- Spring Boot 3.4.5
- Spring Security (JWT com Auth0 `java-jwt 4.5.2`)
- Spring Data JPA + Hibernate
- Flyway (migrações de banco de dados)
- Resilience4j (rate limiting)
- Lombok
- Maven

**Banco de Dados**
- PostgreSQL (produção)
- H2 (testes)

**Front-end**
- HTML, CSS e JavaScript puros (servidos como recursos estáticos pelo Spring Boot)

---

## 🏗️ Arquitetura

O projeto segue uma arquitetura em camadas por domínio de negócio:

```
src/main/java/com/Beetles/systempayout/
├── backend/
│   ├── admin/          # Gestão de administradores
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   └── service/
│   ├── aluno/          # Gestão de alunos
│   ├── historico/      # Histórico de pagamentos
│   ├── plano/          # Planos disponíveis
│   └── security/       # Autenticação JWT e configuração de segurança
└── SystemPayoutApplication.java

src/main/resources/static/
├── index.html
├── login.html
├── aluno.html
├── planos.html
├── adminDashboard.html
├── registrarAluno.html
├── alterarSenha.html
├── css/                # Estilos por página
└── js/                 # Scripts por funcionalidade
```

---

## 🔐 Segurança

- Autenticação **stateless** via **JWT**
- Controle de acesso por roles (`ROLE_ADMIN`)
- Todos os endpoints administrativos exigem autenticação
- Rotas públicas: `/auth/login`, `/auth/admin/register`, `/auth/admin/alterarsenha` e os recursos estáticos (HTML, CSS, JS, imagens)
- Proteção contra abuso com **rate limiting** via Resilience4j por domínio:

| Endpoint       | Requisições por período | Período   |
|----------------|------------------------|-----------|
| `/admin/**`    | 20                     | 10 segundos |
| `/aluno/**`    | 10                     | 5 segundos  |
| `/historico/**`| 10                     | 5 segundos  |
| `/plano/**`    | 10                     | 5 segundos  |
| `/auth/**`     | 10                     | 5 segundos  |

---

## 📡 Endpoints da API

### Autenticação — `/auth`
| Método | Rota                        | Acesso  | Descrição                  |
|--------|-----------------------------|---------|----------------------------|
| POST   | `/auth/login`               | Público | Login de administrador     |
| POST   | `/auth/admin/register`      | Admin | Cadastro de administrador  |

### Alunos — `/aluno`
| Método | Rota                   | Acesso | Descrição                  |
|--------|------------------------|--------|----------------------------|
| POST   | `/aluno/register`      | Admin  | Cadastrar novo aluno       |
| GET    | `/aluno/all`           | Admin  | Listar todos os alunos (paginado) |
| GET    | `/aluno/getId/{id}`    | Admin  | Buscar aluno por ID        |
| PUT    | `/aluno/update/{id}`   | Admin  | Atualizar dados do aluno   |
| DELETE | `/aluno/delete/{id}`   | Admin  | Remover aluno              |

### Planos — `/plano`
| Método | Rota                    | Acesso | Descrição                    |
|--------|-------------------------|--------|------------------------------|
| POST   | `/plano/salvar`         | Admin  | Criar novo plano             |
| GET    | `/plano/buscar`         | Admin  | Listar todos os planos (paginado) |
| GET    | `/plano/buscar/{id}`    | Admin  | Buscar plano por ID          |
| PUT    | `/plano/atualizar/{id}` | Admin  | Atualizar plano              |
| DELETE | `/plano/deletar/{id}`   | Admin  | Remover plano                |

### Histórico de Pagamentos — `/historico`
| Método | Rota                               | Acesso | Descrição                              |
|--------|------------------------------------|--------|----------------------------------------|
| POST   | `/historico/salvar`                | Admin  | Registrar novo pagamento               |
| GET    | `/historico/{id}`                  | Admin  | Buscar pagamento por ID                |
| GET    | `/historico/findAll`               | Admin  | Listar todos os pagamentos (paginado)  |
| GET    | `/historico/getAllByAluno/{id}`     | Admin  | Listar pagamentos de um aluno          |
| POST   | `/historico/confirmar/{id}`        | Admin  | Confirmar pagamento pendente           |
| DELETE | `/historico/deletar/{id}`          | Admin  | Remover registro de pagamento          |

### Administradores — `/admin`
| Método | Rota                        | Acesso | Descrição               |
|--------|-----------------------------|--------|-------------------------|
| POST   | `/admin/findEmail/{email}`  | Admin  | Buscar admin por e-mail |
| DELETE | `/admin/delete/{id}`        | Admin  | Remover administrador   |

---

## ⚙️ Configuração e Execução

### Pré-requisitos

- Java 17+
- Maven 3.8+
- PostgreSQL

### Variáveis de Ambiente

Crie as seguintes variáveis de ambiente antes de executar a aplicação:

```bash
DB_URL_POSTGRES=jdbc:postgresql://localhost:5432/systempayout
DB_USER_POSTGRES=seu_usuario
DB_PASSWORD_POSTGRES=sua_senha
JWT_SECRET=sua_chave_secreta_jwt
PORT=8080  # opcional, padrão 8080
```

### Executando com Maven

```bash
# Clonar o repositório
git clone https://github.com/seu-usuario/projeto-integrador-The-Beetles.git
cd projeto-integrador-The-Beetles

# Executar a aplicação
./mvnw spring:boot:run
```

A aplicação estará disponível em `http://localhost:8080`.

### Executando os Testes

```bash
./mvnw test
```

Os testes utilizam o banco H2 em memória (perfil `test`).

---

## 🗄️ Banco de Dados

As migrações são gerenciadas automaticamente pelo **Flyway**. Ao iniciar a aplicação, o schema é criado/atualizado automaticamente no schema `public`.

**Tabelas principais:**
- `alunos` — dados dos alunos (nome, plano, status, datas de vencimento)
- `planos` — planos disponíveis (nome, categoria, frequência de aulas, valor)
- `historico_pagamento` — registros de cobranças (valor, status: `PENDENTE`/`PAGO`, datas)
- `admins` — contas de administradores

---

## 🖥️ Interface Web

O front-end é composto por páginas HTML estáticas servidas pelo próprio Spring Boot:

| Página                  | Descrição                                  |
|-------------------------|--------------------------------------------|
| `index.html`            | Página inicial / landing page              |
| `login.html`            | Login do administrador                     |
| `adminDashboard.html`   | Painel principal do administrador          |
| `aluno.html`            | Listagem e gerenciamento de alunos         |
| `registrarAluno.html`   | Formulário de cadastro de aluno            |
| `planos.html`           | Gerenciamento de planos                    |
| `alterarSenha.html`     | Alteração de senha do administrador        |

---

## 👥 Equipe

Desenvolvido pelo grupo **The Beetles** como projeto integrador.

---

## 📄 Licença

Este projeto está licenciado sob os termos descritos no arquivo [LICENSE](LICENSE).

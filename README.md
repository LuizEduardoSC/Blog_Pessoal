# Projeto Blog Pessoal 📝

O **Blog Pessoal** é uma API REST desenvolvida com **Spring Boot**, projetada para gerenciar postagens, temas e usuários em um ambiente de blog. O projeto foca em boas práticas de desenvolvimento, segurança com JWT e documentação interativa.

---

## 🚀 Funcionalidades

- **Gerenciamento de Usuários:** Cadastro com confirmação por e-mail, login e atualização de perfis (incluindo campo `sobre`).
- **Postagens:** CRUD completo com suporte a **Paginação**.
- **Comentários:** Sistema de comentários em postagens.
- **Temas:** Organização de postagens por categorias/temas.
- **Segurança:** Autenticação via Token JWT e criptografia de senhas com BCrypt.
- **CORS:** Configuração global para permitir requisições do frontend.
- **Documentação:** Interface interativa via Swagger UI para teste de endpoints.

---

## 🛠️ Tecnologias Utilizadas

- **Java 21**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (Persistência de dados)
- **Spring Security** (Autenticação e Autorização)
- **JSON Web Token (JWT)** (Segurança de rotas)
- **Java Mail Sender / Mailtrap** (Confirmação de cadastro por e-mail)
- **MySQL / PostgreSQL** (Bancos de dados)
- **Render / Neon** (Hospedagem e Banco Nuvem)
- **Swagger (OpenAPI 3)** (Documentação da API)

---

## 🌐 Deploy em Produção

A API está hospedada no **Render** e utiliza o **Neon PostgreSQL** como banco de dados serverless.

- **URL Base:** `https://blogpessoal-vms9.onrender.com`
- **Documentação:** `https://blogpessoal-vms9.onrender.com/swagger-ui/index.html`

---

## 🔒 Variáveis de Ambiente (Produção)

Para rodar este projeto em um ambiente como o Render, as seguintes variáveis devem estar configuradas:

| Variável | Descrição |
| :--- | :--- |
| `POSTGRESHOST` | Hostname do banco (ex: `ep-xxx-pooler.neon.tech`) |
| `POSTGRESPORT` | Porta do banco (Padrão: `5432`) |
| `POSTGRESDATABASE` | Nome do banco |
| `POSTGRESUSER` | Usuário do banco |
| `POSTGRESPASSWORD` | Senha do banco |
| `PORT` | Porta que o Render fornece (Normalmente `10000`) |

---

## ⚙️ Como Rodar Localmente

### Pré-requisitos
- **Java 21** ou superior instalado.
- **MySQL** instalado e rodando.

### 1. Configurar o Banco de Dados
Acesse seu terminal MySQL e execute:
```sql
CREATE DATABASE IF NOT EXISTS db_blogpessoal;
```

### 2. Configurar Variáveis de E-mail
No arquivo `src/main/resources/application-dev.properties`, configure suas credenciais do **Mailtrap**:
```properties
spring.mail.username=SEU_USUARIO_MAILTRAP
spring.mail.password=SUA_SENHA_MAILTRAP
```

### 2. Clonar e Iniciar
```bash
# Clone o repositório
git clone https://github.com/LuizEduardoSC/Blog_Pessoal.git
cd Blog_Pessoal

# Execute o projeto
./mvnw spring-boot:run
```

> **Importante:** O arquivo `src/main/resources/application.properties` deve estar com `spring.profiles.active=dev` para rodar localmente com MySQL.

---

## 🔄 Alternando entre Ambientes

| Arquivo | Local | Produção (Render) |
| :--- | :--- | :--- |
| `application.properties` | `spring.profiles.active=dev` | `spring.profiles.active=prod` |
| Frontend `.env` | `VITE_API_URL=http://localhost:8080` | `VITE_API_URL=https://blogpessoal-vms9.onrender.com` |

---

## 🧪 Como Testar e Acessar

1.  **Swagger UI:** Acesse http://localhost:8080/swagger-ui/index.html para ver e testar os endpoints.
2.  **Primeiro Acesso:**
    - Cadastre um usuário via `POST /usuarios/cadastrar`.
    - Use as credenciais para fazer login e obter o token JWT.
    - Insira o token no Swagger (botão **Authorize**) para acessar os endpoints protegidos.

---

## 📁 Estrutura de Endpoints Principais

| Método | Endpoint | Auth | Descrição |
| :--- | :--- | :--- | :--- |
| POST | `/usuarios/cadastrar` | ❌ Público | Cadastra um novo usuário |
| POST | `/usuarios/logar` | ❌ Público | Autentica e retorna token JWT |
| GET | `/postagens` | ✅ Token | Lista postagens (**Paginado**) |
| POST | `/postagens` | ✅ Token | Cria uma nova postagem |
| GET | `/comentarios/postagem/{id}` | ✅ Token | Lista comentários de uma postagem |
| POST | `/comentarios` | ✅ Token | Adiciona um comentário |
| GET | `/temas` | ✅ Token | Lista temas (**Paginado**) |
| DELETE | `/temas/{id}` | ✅ Token | Remove um tema |

---

## 👤 Autor
Desenvolvido por **Luiz Eduardo Silva Costa**.
[GitHub](https://github.com/LuizEduardoSC) | [LinkedIn](https://www.linkedin.com/in/luiz-eduardosc/)
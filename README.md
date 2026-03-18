# Projeto Blog Pessoal 📝

O **Blog Pessoal** é uma API REST desenvolvida com **Spring Boot**, projetada para gerenciar postagens, temas e usuários em um ambiente de blog. O projeto foca em boas práticas de desenvolvimento, segurança com JWT e documentação interativa.

---

## 🚀 Funcionalidades

- **Gerenciamento de Usuários:** Cadastro, login e atualização de perfis.
- **Postagens:** Criação, leitura, atualização e exclusão (CRUD) de postagens.
- **Temas:** Organização de postagens por categorias/temas.
- **Segurança:** Autenticação via Token JWT e criptografia de senhas com BCrypt.
- **Documentação:** Interface interativa via Swagger UI para teste de endpoints.

---

## 🛠️ Tecnologias Utilizadas

- **Java 17**
- **Spring Boot 3.2.0**
- **Spring Data JPA** (Persistência de dados)
- **Spring Security** (Autenticação e Autorização)
- **JSON Web Token (JWT)** (Segurança de rotas)
- **MySQL** (Banco de dados em ambiente de desenvolvimento)
- **PostgreSQL** (Configurado para o ambiente de produção)
- **Maven** (Gerenciador de dependências)
- **Swagger (OpenAPI 3)** (Documentação da API)

---

## ⚙️ Como Rodar Localmente

### Pré-requisitos
- **Java 17** ou superior instalado.
- **MySQL** instalado e rodando.
- **Postman** ou **Insomnia** para testes iniciais.

### 1. Configurar o Banco de Dados
Acesse seu terminal MySQL e execute os comandos abaixo para garantir o acesso correto do Spring:
```sql
CREATE DATABASE IF NOT EXISTS db_blogpessoal;
ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'root';
FLUSH PRIVILEGES;
```

### 2. Clonar e Iniciar
```bash
# Clone o repositório (substitua pela sua URL)
git clone https://github.com/LuizEduardoSC/Blog_Pessoal.git

# Entre na pasta
cd Blog_Pessoal

# Execute o projeto via Maven Wrapper
./mvnw spring-boot:run
```

---

## 🧪 Como Testar e Acessar

1.  **Swagger UI:** Acesse http://localhost:8080/swagger-ui/index.html para ver e testar os endpoints.
2.  **Primeiro Acesso:** 
    - O banco inicia vazio. Você deve cadastrar um usuário via **Postman/Insomnia** enviando um `POST` para `/usuarios/cadastrar`.
    - Use as credenciais criadas para fazer login na janela do Swagger.

---

## 📁 Estrutura de Endpoints Principais

| Método | Endpoint | Descrição |
| :--- | :--- | :--- |
| POST | `/usuarios/cadastrar` | Cadastra um novo usuário (Público) |
| POST | `/usuarios/logar` | Autentica e gera token JWT (Público) |
| GET | `/postagens` | Lista todas as postagens (Autenticado) |
| GET | `/temas` | Lista todos os temas (Autenticado) |

---

## 👤 Autor
Desenvolvido por **Luiz Eduardo Silva Costa**.
[GitHub](https://github.com/LuizEduardoSC) | [LinkedIn](https://www.linkedin.com/in/luiz-eduardo-sc/)

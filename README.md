  # Authentication API
  
  ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
  ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
  ![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
  ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
  
  Esse projeto é uma API construída usando **Java, Java Spring, Flyway Migrations, PostgresSQL como banco de dados, e Spring Security com JWT para controle de autenticação.**
  
  ## Instalação
  
  1. Clone o repositório:
  
  ```bash
  git clone https://github.com/daniel-sd03/Authentication-API.git
  ```

  2. Instale as dependências do Maven
  
  3. Instale o [PostgresSQL](https://www.postgresql.org/)

  4. Configure o arquivo `application.properties` com as configurações do seu banco de dados 
  
  ````java 
  spring.datasource.url=Url-Do-Banco-De-Dados
  spring.datasource.username=User-Do-Banco-De-Dados
  spring.datasource.password=Senha-Do-Banco-De-Dados
  
  api.security.token.secret=${JWT_SECRET:my-secret-key}
  
  ````
  
  ## Uso
  
  1. Inicie a aplicação com Maven
  2. A API estará acessível em http://localhost:8080
  
  
  ## Endpoints da API 
  A API fornece os seguintes endpoints
  
  ```markdown
  
  POST /auth/login - Realiza o login na aplicação
  
  POST /auth/register - Registra um novo usuário na aplicação

  POST /home - Endpoint apenas para teste  de autenticação, apenas usuário com role ADMIN devem ter acesso 
  ```
  
  ## Autenticação
  A API utiliza Spring Security para controle de autenticação. Os seguintes roles estão disponíveis: 
  ```
  USER ->  Role padrão para usuários logados.
  ADMIN -> Role de administrador para gerenciar parceiros.
  ```
  
  ## Banco de Dados
  O projeto utiliza [PostgresSQL](https://www.postgresql.org/) como banco de dados. As migrações necessárias são gerenciadas usando Flyway.

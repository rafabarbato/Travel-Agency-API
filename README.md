# API para Agência de Viagem

Esta é uma API RESTful desenvolvida com Java e Spring Boot para gerenciar viagens de uma agência de turismo. A aplicação utiliza um banco de dados em memória (H2), possui endpoints protegidos com Spring Security e vem com dados iniciais para facilitar os testes.

## Funcionalidades

  - ✅ Listar todas as viagens ou apenas as ativas
  - ✅ Buscar viagens por ID, destino, categoria ou faixa de preço
  - ✅ Criar novas viagens (requer autenticação)
  - ✅ Atualizar viagens (requer autenticação)
  - ✅ Deletar viagens (requer autenticação)
  - ✅ **Registrar e consultar avaliações de viagens (novo)**
  - ✅ Reservar vagas (requer autenticação)
  - ✅ Validação de dados de entrada
  - ✅ Tratamento de exceções
  - ✅ Segurança de endpoints com autenticação baseada em usuário e senha

## Imagens

- Banco de Dados working

![Banco de Dados](https://github.com/user-attachments/assets/ec25884b-c778-4e20-be95-ed44329356dc)

- curl working

![curl funcionando](https://github.com/user-attachments/assets/0fe557ff-d33d-4ded-84df-92d24a3643a9)

## Tecnologias Utilizadas

  - Java 17
  - Spring Boot 3.2.0
  - Spring Web
  - Spring Data JPA
  - Spring Security
  - Spring Validation
  - H2 Database (banco de dados em memória)
  - Maven

## Como Executar

### 1\. Pré-requisitos

  - Java 17 (ou superior)
  - Apache Maven

### 2\. Configuração e Execução

1.  Clone o repositório para sua máquina local.
2.  Abra um terminal e navegue até a pasta raiz do projeto (onde se encontra o arquivo `pom.xml`).
3.  Execute o seguinte comando para iniciar a aplicação:
    ```bash
    mvn spring-boot:run
    ```
4.  A API estará disponível em `http://localhost:8080`.

### 3\. Acessando o Banco de Dados (H2 Console)

A aplicação é configurada para usar um banco de dados em memória H2, que pode ser acessado enquanto a aplicação está rodando.

1.  Com a aplicação em execução, abra seu navegador e acesse: `http://localhost:8080/h2-console`
2.  Na tela de login, utilize as seguintes configurações:
      - **JDBC URL**: `jdbc:h2:mem:travelagencydb`
      - **User Name**: `sa`
      - **Password**: (deixe em branco)
3.  Clique em **Connect**. Agora você pode visualizar as tabelas `VIAGEM`, `USUARIO` e `AVALIACAO` e executar consultas SQL.

## Segurança e Usuário Padrão

A API utiliza Spring Security para proteger endpoints de escrita (POST, PUT, PATCH, DELETE). Para realizar essas operações, é necessário se autenticar.

Ao iniciar a aplicação, um usuário padrão é criado para facilitar os testes:

  - **Usuário**: `admin`
  - **Senha**: `admin`

## Endpoints da API

**Observação:** Endpoints que modificam dados (POST, PUT, PATCH, DELETE) requerem autenticação.

#### Seção de Viagens

  - **`GET /api/viagens`**: Lista viagens com filtros.
  - **`GET /api/viagens/{id}`**: Busca uma viagem por ID.
  - **`POST /api/viagens`**: Cria uma nova viagem.
  - **`PUT /api/viagens/{id}`**: Atualiza uma viagem.
  - **`DELETE /api/viagens/{id}`**: Deleta uma viagem.
  - **`POST /api/viagens/{id}/reservar`**: Reserva vagas em uma viagem.

#### Seção de Avaliações

  - **`GET /api/viagens/{id}/avaliacoes`**: Lista as avaliações de uma viagem.
  - **`POST /api/viagens/{id}/avaliacoes`**: Adiciona uma nova avaliação a uma viagem.

## Exemplos de Uso com `curl`

#### Criar uma nova viagem

1.  Crie um arquivo `viagem.json`:
    ```json
    {
      "destino": "Kyoto",
      "dataPartida": "2025-11-10",
      "dataRetorno": "2025-11-20",
      "preco": 5200.00,
      "descricao": "Outono no Japão",
      "vagasDisponiveis": 10,
      "categoria": "EXECUTIVA"
    }
    ```
2.  Execute o comando:
    ```bash
    curl -X POST http://localhost:8080/api/viagens -u admin:admin -H "Content-Type: application/json" -d @viagem.json
    ```

#### Adicionar uma avaliação à viagem de ID 1

1.  Crie um arquivo `avaliacao.json`:
    ```json
    {
      "nota": 5,
      "comentario": "Inesquecível! A melhor viagem da minha vida."
    }
    ```
2.  Execute o comando:
    ```bash
    curl -X POST http://localhost:8080/api/viagens/1/avaliacoes -u admin:admin -H "Content-Type: application/json" -d @avaliacao.json
    ```

## Dados Iniciais

A API vem com dados iniciais que são carregados no banco de dados em memória toda vez que a aplicação é iniciada:

  - **Usuário**: `admin` com senha `admin` e permissão `ADMIN`.
  - **Viagens**: Uma viagem para Paris e outra para Tokyo.

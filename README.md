# API para Agência de Viagem

Esta é uma API RESTful desenvolvida com Java e Spring Boot para gerenciar viagens de uma agência de turismo. A aplicação utiliza um banco de dados em memória (H2), possui endpoints protegidos com Spring Security e vem com dados iniciais para facilitar os testes.

## Funcionalidades

  - ✅ Listar todas as viagens ou apenas as ativas
  - ✅ Buscar viagens por ID, destino, categoria ou faixa de preço
  - ✅ Criar novas viagens (requer autenticação)
  - ✅ Atualizar viagens (requer autenticação)
  - ✅ Deletar viagens (requer autenticação)
  - ✅ Registrar e consultar avaliações de viagens
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
3.  Clique em **Connect**. Agora você pode visualizar as tabelas `VIAGEM`, `USUARIO` e **`AVALIACAO`** e executar consultas SQL.

## Segurança e Usuário Padrão

A API utiliza Spring Security para proteger endpoints de escrita (POST, PUT, PATCH, DELETE). Para realizar essas operações, é necessário se autenticar.

Ao iniciar a aplicação, um usuário padrão é criado para facilitar os testes:

  - **Usuário**: `admin`
  - **Senha**: `admin`

## Endpoints da API

**Observação:** Endpoints que modificam dados (POST, PUT, PATCH, DELETE) requerem autenticação.

1.  **Listar Viagens (Público)**
      - **GET** `/api/viagens`
      - **Parâmetros opcionais:**
          - `destino`, `categoria`, `precoMin`, `precoMax`, `apenasAtivas`
2.  **Buscar Viagem por ID (Público)**
      - **GET** `/api/viagens/{id}`
3.  **Criar Nova Viagem (Requer Autenticação)**
      - **POST** `/api/viagens`
4.  **Atualizar Viagem Completa (Requer Autenticação)**
      - **PUT** `/api/viagens/{id}`
5.  **Atualizar Viagem Parcialmente (Requer Autenticação)**
      - **PATCH** `/api/viagens/{id}`
6.  **Deletar Viagem (Requer Autenticação)**
      - **DELETE** `/api/viagens/{id}`
7.  **Desativar Viagem (Requer Autenticação)**
      - **PATCH** `/api/viagens/{id}/desativar`
8.  **Reservar Vagas (Requer Autenticação)**
      - **POST** `/api/viagens/{id}/reservar`
9.  **Status da API (Público)**
      - **GET** `/api/viagens/status`
10. **Listar Avaliações de uma Viagem (Público)**
      - **GET** `/api/viagens/{id}/avaliacoes`
11. **Criar Nova Avaliação (Requer Autenticação)**
      - **POST** `/api/viagens/{id}/avaliacoes`

## Exemplos de Uso com `curl`

### Buscar viagens (público)

```bash
curl http://localhost:8080/api/viagens?destino=Paris
```

### Criar uma viagem (autenticado)

```bash
curl -X POST http://localhost:8080/api/viagens \
  -u admin:admin \
  -H "Content-Type: application/json" \
  -d '{
    "destino": "Rio de Janeiro",
    "dataPartida": "2025-12-15",
    "dataRetorno": "2025-12-22",
    "preco": 1800.00,
    "descricao": "Fim de ano no Rio de Janeiro",
    "vagasDisponiveis": 40,
    "categoria": "ECONOMICA"
  }'
```

### Reservar vagas (autenticado)

```bash
curl -X POST http://localhost:8080/api/viagens/1/reservar \
  -u admin:admin \
  -H "Content-Type: application/json" \
  -d '{"quantidade": 2}'
```

### Adicionar uma avaliação à viagem de ID 1 (autenticado)

1. Crie um arquivo `avaliacao.json` com o seguinte conteúdo:

```json
{
  "nota": 5,
  "comentario": "Viagem fantástica!"
}

2. Execute o comando curl, lendo os dados do novo arquivo:
```

```bash
curl -X POST http://localhost:8080/api/viagens/1/avaliacoes -u admin:admin -H "Content-Type: application/json" -d @avaliacao.json
```

## Dados Iniciais

A API vem com dados iniciais que são carregados no banco de dados em memória toda vez que a aplicação é iniciada:

  - **Usuário**: `admin` com senha `admin` e permissão `ADMIN`.
  - **Viagens**: Uma viagem para Paris e outra para Tokyo.
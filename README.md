# API para Agência de Viagem

Esta é uma API RESTful desenvolvida com Java e Spring Boot para gerenciar viagens de uma agência de turismo.

## Funcionalidades

- ✅ Listar todas as viagens ou apenas as ativas
- ✅ Buscar viagens por ID, destino, categoria ou faixa de preço
- ✅ Criar novas viagens
- ✅ Atualizar viagens (completa ou parcialmente)
- ✅ Deletar viagens
- ✅ Desativar viagens (soft delete)
- ✅ Reservar vagas
- ✅ Validação de dados
- ✅ Tratamento de exceções

## Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.0
- Spring Web
- Spring Validation
- Maven

## Estrutura do Projeto

```
src/main/java/com/agencia/travelagencyapi/
├── TravelAgencyApiApplication.java      # Classe principal
├── controller/
│   └── ViagemController.java           # Controlador REST
├── model/
│   └── Viagem.java                     # Modelo de dados
├── service/
│   └── ViagemService.java              # Camada de serviço
└── exception/
    └── GlobalExceptionHandler.java     # Tratamento global de exceções
```

## Como Executar

1. Clone o projeto
2. Navegue até a pasta do projeto
3. Execute: `mvn spring-boot:run`
4. A API estará disponível em: `http://localhost:8080`

## Endpoints da API

### 1. Listar Viagens
- **GET** `/api/viagens`
- **Parâmetros opcionais:**
  - `destino`: filtrar por destino
  - `categoria`: filtrar por categoria (ECONOMICA, EXECUTIVA, PRIMEIRA_CLASSE)
  - `precoMin` e `precoMax`: filtrar por faixa de preço
  - `apenasAtivas`: mostrar apenas viagens ativas (padrão: true)

**Exemplo:**
```bash
GET /api/viagens?destino=Paris
GET /api/viagens?categoria=ECONOMICA
GET /api/viagens?precoMin=1000&precoMax=3000
```

### 2. Buscar Viagem por ID
- **GET** `/api/viagens/{id}`

**Exemplo:**
```bash
GET /api/viagens/1
```

### 3. Criar Nova Viagem
- **POST** `/api/viagens`
- **Body (JSON):**
```json
{
  "destino": "Barcelona",
  "dataPartida": "2025-10-15",
  "dataRetorno": "2025-10-25",
  "preco": 2800.00,
  "descricao": "Aventura pela Catalunha",
  "vagasDisponiveis": 30,
  "categoria": "ECONOMICA"
}
```

### 4. Atualizar Viagem Completa
- **PUT** `/api/viagens/{id}`
- **Body:** Mesma estrutura do POST

### 5. Atualizar Viagem Parcialmente
- **PATCH** `/api/viagens/{id}`
- **Body (JSON):**
```json
{
  "preco": 2500.00,
  "vagasDisponiveis": 25
}
```

### 6. Deletar Viagem
- **DELETE** `/api/viagens/{id}`

### 7. Desativar Viagem
- **PATCH** `/api/viagens/{id}/desativar`

### 8. Reservar Vagas
- **POST** `/api/viagens/{id}/reservar`
- **Body (JSON):**
```json
{
  "quantidade": 2
}
```

### 9. Status da API
- **GET** `/api/viagens/status`

## Modelo de Dados

### Viagem
```json
{
  "id": 1,
  "destino": "Paris",
  "dataPartida": "2025-08-15",
  "dataRetorno": "2025-08-25",
  "preco": 2500.00,
  "descricao": "Viagem romântica para Paris",
  "vagasDisponiveis": 18,
  "categoria": "ECONOMICA",
  "ativa": true
}
```

## Validações

- **Destino**: obrigatório, não pode estar vazio
- **Data de partida**: obrigatória, não pode ser no passado
- **Data de retorno**: obrigatória, deve ser posterior à data de partida
- **Preço**: obrigatório, deve ser positivo
- **Descrição**: ob
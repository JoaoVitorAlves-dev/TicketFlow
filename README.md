# TicketFlow

API REST para gerenciamento de eventos e venda/controle de ingressos, desenvolvida com Java 21 e Spring Boot. O projeto reúne cadastro de usuários e organizadores, eventos, sessões, locais, setores, assentos, pedidos, pagamentos, ingressos com QR Code e check-in.

## Tecnologias

- Java 21
- Spring Boot 4.1.2
- Spring Web MVC
- Spring Data JPA / Hibernate
- Spring Security
- JWT com JJWT 0.12.6
- MySQL 8.4
- ZXing 3.5.3 para geração de QR Code
- Maven
- Docker e Docker Compose
- Bean Validation
- Lombok
- Spring Boot Actuator

## Funcionalidades

- Cadastro, autenticação e atualização de usuários.
- Autenticação baseada em JWT e renovação por refresh token.
- Controle de acesso por perfis: `CUSTOMER`, `ORGANIZER` e `ADMIN`.
- Cadastro e gerenciamento de organizadores.
- Criação e gerenciamento de eventos.
- Organização de sessões, locais, setores e assentos.
- Criação e consulta de pedidos.
- Registro e gerenciamento de pagamentos.
- Emissão e consulta de ingressos.
- Geração de QR Code em PNG para um ingresso.
- Registro e consulta de check-ins.
- Rotinas agendadas para limpeza de pedidos expirados, sessões passadas e pagamentos inválidos/pendentes.

## Perfis de acesso

Os perfis definidos no enum `Role` são:

| Perfil | Responsabilidade prevista |
|---|---|
| `CUSTOMER` | Usuário/cliente que consulta eventos e sessões, cria pedidos e acompanha seus ingressos e pagamentos. |
| `ORGANIZER` | Organizador que gerencia seus próprios eventos e sessões e realiza operações de check-in. |
| `ADMIN` | Administração geral dos cadastros e operações da plataforma. |

O cadastro público cria usuários com o perfil `CUSTOMER`. A atribuição dos demais perfis depende da administração da aplicação.

## Endpoints da API

A API não declara um prefixo global como `/api`. As rotas abaixo são relativas à raiz do servidor. Os endpoints que recebem dados usam JSON e os identificadores são informados no caminho quando indicado.

### Usuários — `/users`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/users/register` | Cadastra um usuário com perfil `CUSTOMER`. | Público |
| `POST` | `/users/login` | Autentica com e-mail e senha e retorna tokens. | Público |
| `POST` | `/users/refresh` | Renova os tokens usando um refresh token. | Público |
| `GET` | `/users/view` | Consulta os dados do usuário autenticado. | Autenticado |
| `PATCH` | `/users/update/{id}` | Atualiza os dados da própria conta. | Autenticado |
| `DELETE` | `/users/deactivate` | Desativa a própria conta. | Autenticado |

O identificador de usuário é um UUID. O corpo de cadastro/atualização é baseado em `RegisterRequest`:

```json
{
  "nome": "João",
  "cpf": "00000000000",
  "email": "joao@email.com",
  "password": "senha"
}
```

A resposta de autenticação contém `accessToken` e `refreshToken`.

### Organizadores — `/organizer`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/organizer/create` | Cadastra um organizador associado a um usuário. | `ADMIN` |
| `GET` | `/organizer/view` | Consulta os registros de organizador associados ao usuário autenticado. | `ORGANIZER`, `ADMIN` |
| `PATCH` | `/organizer/updaterecord/{id}` | Atualiza os dados do próprio registro de organizador. | `ORGANIZER` |
| `PATCH` | `/organizer/update/{id}` | Atualiza um registro de organizador. | `ADMIN` |
| `DELETE` | `/organizer/deactivate/{id}` | Desativa um organizador. | `ADMIN` |

### Eventos — `/event`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/event/create` | Cria um evento vinculado a um organizador. | `ORGANIZER`, `ADMIN` |
| `GET` | `/event/view` | Lista eventos com paginação. | Público |
| `PATCH` | `/event/updaterecords/{id}` | Atualiza um evento do próprio organizador. | `ORGANIZER` |
| `PATCH` | `/event/update/{id}` | Atualiza um evento. | `ADMIN` |
| `DELETE` | `/event/deleterecords/{id}` | Exclui um evento pertencente ao organizador autenticado. | `ORGANIZER` |
| `DELETE` | `/event/deactivate/{id}` | Desativa um evento. | `ADMIN` |

### Sessões — `/session`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/session/createsession` | Cria uma sessão para um evento do organizador autenticado. | `ORGANIZER` |
| `POST` | `/session/create` | Cria uma sessão administrativamente. | `ADMIN` |
| `GET` | `/session/list` | Lista sessões com paginação. | Público |
| `PATCH` | `/session/updateownsession/{id}` | Atualiza uma sessão do próprio organizador. | `ORGANIZER` |
| `PATCH` | `/session/update/{id}` | Atualiza uma sessão. | `ADMIN` |
| `DELETE` | `/session/deletesession/{id}` | Exclui uma sessão do próprio organizador. | `ORGANIZER` |
| `DELETE` | `/session/deletebyid/{id}` | Exclui uma sessão pelo identificador. | `ADMIN` |

### Locais — `/location`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/location/create` | Cadastra um local. | `ADMIN` |
| `GET` | `/location/view` | Lista locais com paginação. | Público |
| `PATCH` | `/location/update/{id}` | Atualiza um local. | `ADMIN` |
| `DELETE` | `/location/delete/{id}` | Exclui um local. | `ADMIN` |

### Setores — `/sector`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/sector/create` | Cadastra um setor. | `ADMIN` |
| `GET` | `/sector/list` | Lista setores com paginação. | Público |
| `PATCH` | `/sector/update/{id}` | Atualiza um setor. | `ADMIN` |
| `DELETE` | `/sector/delete/{id}` | Exclui um setor. | `ADMIN` |

### Assentos — `/seat`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/seat/create` | Cadastra um assento. | `ADMIN` |
| `GET` | `/seat/view` | Lista assentos com paginação. | Público |
| `PATCH` | `/seat/update/{id}` | Atualiza um assento. | `ADMIN` |
| `DELETE` | `/seat/deactivate/{id}` | Desativa um assento. | `ADMIN` |

### Pedidos — `/order`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/order/createorder` | Cria um pedido. O status inicial é `PENDENTE`. | `CUSTOMER`, `ORGANIZER` |
| `GET` | `/order/listown` | Lista os pedidos do usuário autenticado. | `CUSTOMER`, `ORGANIZER` |
| `GET` | `/order/list` | Lista todos os pedidos com paginação. | `ADMIN` |
| `PATCH` | `/order/update/{id}` | Atualiza dados de um pedido. | `ADMIN` |
| `DELETE` | `/order/deleterecords/{id}` | Exclui um pedido pertencente ao usuário autenticado. | `CUSTOMER`, `ORGANIZER` |
| `DELETE` | `/order/delete/{id}` | Exclui um pedido pelo identificador. | Autenticado |

### Ingressos — `/ticket`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/ticket/create` | Emite um ingresso a partir de pedido, sessão e assento. | `ADMIN` |
| `GET` | `/ticket/view` | Lista ingressos com paginação. | `ADMIN` |
| `GET` | `/ticket/viewrecords` | Consulta um ingresso relacionado ao usuário autenticado. | `CUSTOMER`, `ORGANIZER` |
| `GET` | `/ticket/{id}/qrcode` | Gera a imagem PNG do QR Code do ingresso. | Autenticado (regra específica não declarada) |
| `PATCH` | `/ticket/update/{id}` | Atualiza um ingresso. | `ADMIN` |
| `DELETE` | `/ticket/delete/{id}` | Exclui um ingresso. | `ADMIN` |

### Pagamentos — `/payment`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/payment/createcustompayment` | Cria pagamento para um pedido do usuário autenticado. | `CUSTOMER`, `ORGANIZER` |
| `POST` | `/payment/create` | Cria um pagamento administrativamente. | `ADMIN` |
| `GET` | `/payment/viewrecords` | Lista pagamentos relacionados ao usuário autenticado. | `CUSTOMER`, `ORGANIZER` |
| `GET` | `/payment/listall` | Lista todos os pagamentos com paginação. | `ADMIN` |
| `PATCH` | `/payment/update/{id}` | Atualiza um pagamento. | `ADMIN` |
| `DELETE` | `/payment/delete/{id}` | Exclui um pagamento. | `ADMIN` |

### Check-in — `/checkin`

| Método | Rota | Descrição | Acesso configurado |
|---|---|---|---|
| `POST` | `/checkin/createrecords` | Registra check-in para ingresso associado ao usuário autenticado. | `ORGANIZER` |
| `POST` | `/checkin/create` | Registra check-in administrativamente. | `ADMIN` |
| `GET` | `/checkin/viewrecords` | Lista registros relacionados ao usuário autenticado. | `ORGANIZER` (conforme configuração) |
| `GET` | `/checkin/view` | Lista todos os check-ins com paginação. | `ADMIN` |
| `PATCH` | `/checkin/update/{id}` | Atualiza um registro de check-in. | `ADMIN` |
| `DELETE` | `/checkin/delete/{id}` | Exclui um registro de check-in. | `ADMIN` |

### Paginação

Os endpoints de listagem recebem os parâmetros padrão do Spring Data:

```http
GET /event/view?page=0&size=10&sort=titulo,asc
```

A resposta é paginada pelo Spring Data, com conteúdo em `content` e metadados de paginação.

## Autenticação e tokens

- O login gera um JWT de acesso com validade configurada em **1 hora** (`api.token.expiration-ms=3600000`).
- O refresh token tem validade configurada em **15 dias** (`api.token.expiration-in-days=15`).
- O token de acesso deve ser enviado no cabeçalho:

```http
Authorization: Bearer SEU_ACCESS_TOKEN
```

- As senhas são armazenadas com BCrypt.
- O refresh token é armazenado no banco e rotacionado quando utilizado.

## Configuração do ambiente

A aplicação lê variáveis de ambiente e também importa, opcionalmente, um arquivo `.env`.

Crie um arquivo `.env` na raiz do projeto:

```env
DB_USER=ticketflow
DB_PASSWORD=uma_senha_local
DB_ROOT_PASSWORD=uma_senha_root_local
JWT_SECRET=uma_chave_secreta_com_pelo_menos_32_bytes
```

Não compartilhe o arquivo `.env` nem envie segredos para o Git.

### Executar com Docker Compose

Com Docker e Docker Compose instalados:

```bash
docker compose up --build
```

O Compose inicia:
- MySQL 8.4, com banco `ticketflow` e volume persistente `mysql-data`.
- API Spring Boot, publicada na porta `8080`.

Para encerrar:

```bash
docker compose down
```

Para remover também os dados persistidos do banco:

```bash
docker compose down -v
```

### Executar localmente

1. Inicie um servidor MySQL e crie o banco `ticketflow`.
2. Configure `DB_USER`, `DB_PASSWORD` e `JWT_SECRET` no ambiente ou no arquivo `.env`.
3. Execute a aplicação:

Linux/macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

A API utiliza a porta `8080` por padrão.

## Testes

Execute:

Linux/macOS:

```bash
./mvnw test
```

Windows:

```powershell
.\mvnw.cmd test
```

## Estrutura do projeto

```text
src/
├── main/
│   ├── java/com/ticketflow/romeuzxg/
│   │   ├── config/       # Configuração de segurança e filtro JWT
│   │   ├── controller/   # Endpoints REST
│   │   ├── domain/       # Entidades e enums
│   │   ├── dto/          # Objetos de entrada e saída
│   │   ├── mapper/       # Conversão entre entidades e DTOs
│   │   ├── repository/   # Repositórios Spring Data JPA
│   │   ├── scheduler/    # Rotinas agendadas
│   │   └── service/      # Regras de negócio e serviços
│   └── resources/
│       └── application.properties
└── test/
    └── java/             # Testes automatizados
```

## Autor
**João Vitor de Jesus Alves** · [GitHub](https://github.com/JoaoVitorAlves-dev).

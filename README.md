# API de banco

## Descrição

Projeto final do bootcamp Afrocódigos, realizado pela [Olabi](https://www.olabi.org.br/) em parceria com a [J.P. Morgan](https://www.jpmorgan.com.br/pt/about-us), Trilha Back-End. O projeto representa a API de uma instituição financeira, implementada em Java com Spring Boot e utilizando a arquitetura MSC.

<img src="/API Banco Fictício.gif">

## Ferramentas

- [Java 17](https://www.oracle.com/br/java/technologies/downloads/#java17)
- [Spring Boot 3](https://spring.io/projects/spring-boot)
- [Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL 16](https://www.postgresql.org/download/)
- [Insomnia](https://insomnia.rest/download)
- [IDE](https://code.visualstudio.com/download)

## Como testar

### Testando a API online

Para testar a API, acesse [aqui](http://ec2-44-202-244-96.compute-1.amazonaws.com:8080/swagger-ui/index.html#/).

Talvez o servidor não esteja conectado no momento de acesso. Nesse caso, por favor, abra uma issue.

### Testando a API localmente

Os passos necessários para testar localmente são:

- Instalar as ferramentas necessárias
- Clonar o projeto
- Executar o arquivo [BancoFicticioApplication.java](/src/main/java/com/ficticio/bancoficticio/BancoFicticioApplication.java) na IDE
- Testar as rotas da API (rotas listadas [aqui](#rotas-da-api) e json com requisições disponível [aqui](/rotas_insomnia/))

## Entidades

### Cliente

Representa o cliente do banco.

#### Construtor

```plaintext
public Cliente(String cpf, String nome, String dataNascimento, String telefone, String endereco, double rendaMensal, String email, String senha)
```

#### Atributos

- **id** (UUID) - ID do Cliente
- **cpf** (String) - CPF do Cliente
- **dataNascimento** (String) - Data de nascimento do Cliente
- **telefone** (String) - Número de telefone do Cliente
- **endereco** (String) - Endereço do Cliente
- **rendaMensal** (double) - Valor da renda mensal do Cliente
- **email** (String) - E-mail do Cliente
- **senha** (String) - Senha da conta do Cliente
- **logado** (boolean) - Status de login do Cliente
- **dataCriacao** (LocalDateTime) - Data e horário da criação da instância de Cliente
- **dataAtualizacao** (LocalDateTime) - Data e horário da última modificação feita na instância de Cliente

#### Métodos

- Getters e setters

### Conta

Representa a conta bancária do cliente.

#### Construtor

```plaintext
public Conta(Cliente cliente)
```

#### Atributos

- **id** (UUID) - ID da Conta (seria o equivalente ao nº da agência + nº da conta)
- **cliente** (Cliente) - Entidade cliente associada à Conta
- **tipo** (String) - Tipo da Conta
- **saldo** (double) - Saldo da Conta
- **limite** (double) - Limite da Conta
- **transferenciaMaxima** (double) - Valor de transferência máxima permitida para esta Conta
- **chavePix** (String) - Valor da chave pix da Conta
- **dataCriacaoConta** (LocalDateTime) - Data e horário da criação da instância de Conta
- **dataAtualizacaoConta** (LocalDateTime) - Data e horário da última modificação feita na instância de Conta

#### Métodos

- Getters e setters
- **atualizaLimite()** - Método utilizado para atualizar o valor do atributo limite após cada transação bancária

#### Subclasses

- ContaPagamento
  - Tipo "Conta Pagamento"
  - Limite = saldo
  - Transferência máxima de R$ 4.999,99
- ContaCorrente
  - Tipo "Conta Corrente"
  - Limite = saldo + 10% da renda mensal
  - Sem limite de transferência

### Transacao

Representa cada transação bancária realizada por uma conta.

#### Construtor

```plaintext
public Transacao(UUID idConta, String tipo, double quantia)
```

ou

```plaintext
public Transacao(UUID idConta, String tipo, double quantia, UUID idContaDestino)
```

#### Atributos

- **idTransacao** (UUID) - ID da Transação
- **idConta** (UUID) - ID da Conta realizando a Transação
- **tipo** (String) - Tipo da Transação
  - "depósito"
  - "saque"
  - "pagamento de conta"
  - "transferência bancária"
  - "pix"
- **quantia** (double) - Valor da quantia sendo movimentada na Transação
- **idContaDestino** (UUID) - ID da Conta envolvida na Transação
- **dataTransacao** (LocalDateTime) - Data e horário da criação da instância de Transação

#### Métodos

- Getters e setters

## Regras de negócio

A aplicação do banco foi criada seguindo as seguintes regras de negócio:

- O cliente deverá realizar seu cadastro fornecendo as seguintes informações:
  - CPF
  - Nome
  - Data de nascimento
  - Telefone
  - Endereço
  - Renda mensal
  - E-mail
  - Senha para acessar a conta
- A partir do valor de renda mensal, o cliente será associado a um tipo de conta
  - Conta Pagamento liberada para pessoas com renda mensal abaixo de R$ 2.900,00
  - Conta Corrente liberada para pessoas com renda mensal a partir de R$ 2.900,00
- O cliente só poderá realizar qualquer operação de transação bancária ou atualização cadastral se tiver realizado o login no sistema
- O login do cliente é feito através do fornecimento do CPF e da senha do cliente
- Caso o cliente atualize o valor de renda mensal a partir de R$ 2.900,00, ele poderá realizar o upgrade de conta (de Conta Pagamento para Conta Corrente)
- Características comuns a todos os tipos de conta:
  - Acesso a todas as transações bancárias
  - 4 saques mensais gratuitos. A partir do 5° saque é cobrada uma taxa de R$ 6,50 por operação
- Características específicas da Conta Pagamento:
  - Transferências bancárias limitadas até R$ 4.999,99
  - Sem acesso ao cheque especial
  - Limite será igual ao saldo atual
- Características específicas da Conta Corrente:
  - Sem limite de transferências bancárias
  - Acesso ao cheque especial equivalente a 10% da renda mensal
  - Limite será igual ao saldo atual acrescido do cheque especial
- Transações disponíveis:
  - Depósito
  - Saque
  - Pagamento de conta
  - Transferência bancária
  - Pix
- As transações de saque, transferência, pagamento e pix serão validadas conforme o limite atual da conta
- Demais funções disponíveis aos clientes:
  - Ver saldo atual
  - Acessar extrato mensal
  - Cadastrar e descadastrar chave pix
  - Atualizar dados cadastrais (telefone, endereço, renda mensal, e-mail, senha)
  - Encerrar conta

## Estrutura do projeto

```plaintext
📂src
└──📂 main
    ├──📂 java
    │   └──📂 com
    │       └──📂 ficticio
    │           └──📂 bancoficticio
    │               ├──📁 config
    │                   └──📄 DataInitializer.java
    │               ├──📁 controller
    │                   ├──📄 ClienteController.java
    │                   ├──📄 ContaController.java
    │                   └──📄 TransacaoController.java
    │               ├──📁 exception
    │                   ├──📄 ClienteException.java
    │                   └──📄 ContaException.java
    │               └──📂 model
    │                   └──📁 entity
    │                       ├──📄 Cliente.java
    │                       ├──📄 Conta.java
    │                       ├──📄 ContaCorrente.java
    │                       ├──📄 ContaPagamento.java
    │                       └──📄 Transacao.java
    │               ├──📁 repository
    │                   ├──📄 ClienteRepository.java
    │                   ├──📄 ContaRepository.java
    │                   └──📄 TransacaoRepository.java
    │               ├──📁 service
    │                   ├──📄 ClienteService.java
    │                   └──📄 ContaService.java
    │               └──📄 BancoFicticioApplication.java
    └──📂 resources
        └──📄 application.properties
```

- [**DataInitializer.java**](/src/main/java/com/ficticio/bancoficticio/config/DataInitializer.java) - Inicializa e conecta ao banco de dados
- [**ClienteController.java**](/src/main/java/com/ficticio/bancoficticio/controller/ClienteController.java) - Controller da classe Cliente. Relaciona as rotas de requisições HTTP relacionadas à classe Cliente.
- [**ContaController.java**](/src/main/java/com/ficticio/bancoficticio/controller/ContaController.java) - Controller da classe Conta. Relaciona as rotas de requisições HTTP relacionadas à classe Conta.
- [**TransacaoController.java**](/src/main/java/com/ficticio/bancoficticio/controller/TransacaoController.java) - Controller da classe Transacao. Relaciona as rotas de requisições HTTP relacionadas à classe Transacao.
- [**ClienteException.java**](/src/main/java/com/ficticio/bancoficticio/exception/ClienteException.java) - Tratamento de exceções da classe Cliente.
- [**ContaException.java**](/src/main/java/com/ficticio/bancoficticio/exception/ContaException.java) - Tratamento de exceções da classe Conta.
- [**Cliente.java**](/src/main/java/com/ficticio/bancoficticio/model/entity/Cliente.java) - Classe da entidade Cliente.
- [**Conta.java**](/src/main/java/com/ficticio/bancoficticio/model/entity/Conta.java) - Classe da entidade Conta.
- [**ContaCorrente.java**](/src/main/java/com/ficticio/bancoficticio/model/entity/ContaCorrente.java) - Classe da entidade ContaCorrente. Subclasse da classe Conta.
- [**ContaPagamento.java**](/src/main/java/com/ficticio/bancoficticio/model/entity/ContaPagamento.java) - Classe da entidade ContaPagamento. Subclasse da classe Conta.
- [**Transacao.java**](/src/main/java/com/ficticio/bancoficticio/model/entity/Transacao.java) - Classe da entidade Transacao.
- [**ClienteRepository.java**](/src/main/java/com/ficticio/bancoficticio/repository/ClienteRepository.java) - Repositório de dados de instâncias da classe Cliente.
- [**ContaRepository.java**](/src/main/java/com/ficticio/bancoficticio/repository/ContaRepository.java) - Repositório de dados de instâncias da classe Conta.
- [**TransacaoRepository.java**](/src/main/java/com/ficticio/bancoficticio/repository/TransacaoRepository.java) - Repositório de dados de instâncias da classe Transacao.
- [**ClienteService.java**](/src/main/java/com/ficticio/bancoficticio/service/ClienteService.java) - Implementação das regras de negócio relacionadas a entidade Cliente.
- [**ContaService.java**](/src/main/java/com/ficticio/bancoficticio/service/ContaService.java) - Implementação das regras de negócio relacionadas a entidade Conta.
- [**BancoFicticioApplication.java**](/src/main/java/com/ficticio/bancoficticio/BancoFicticioApplication.java) - Aplicação Spring Boot.

## Rotas da API

### Rotas da área do cliente (/cliente)

| Método | Rota                                | Função                                            |
| ------ | ----------------------------------- | ------------------------------------------------- |
| POST   | /cliente/cadastro                   | Operação de cadastro de novo cliente              |
| DELETE | /cliente/{id}/encerramento-de-conta | Operação de encerrar a conta bancária             |
| PATCH  | /cliente/login                      | Operação de login                                 |
| PATCH  | /cliente/redefinir-senha            | Operação de redefinir de senha                    |
| PATCH  | /cliente/{id}/logoff                | Operação de logoff                                |
| PATCH  | /cliente/{id}/upgrade-de-conta      | Operação de atualização do tipo de conta bancária |
| PUT    | /cliente/{id}/atualizar-cadastro    | Operação de atualização dos dados cadastrais      |

#### [POST] /cadastro

- **Descrição**: Operação de cadastro de novo cliente. Requer o envio dos dados do cliente pelo body.

- **Exemplo de body**:

```json
{
  "cpf": "123.456.789-00",
  "nome": "José Maria",
  "dataNascimento": "01/01/2000",
  "telefone": "1234-5678",
  "endereco": "Endereço do José, 001",
  "rendaMensal": "3600.00",
  "email": "josemaria@email.com",
  "senha": "senha123"
}
```

#### [DELETE] /{id}/encerramento-de-conta

- **Descrição**: Operação de encerrar a conta bancária. Requer no path param o id do cliente que deseja exlcuir a conta.

#### [PATCH] /login

- **Descrição**: Operação de login. Requer o envio do CPF e da senha do cliente pelo body.

- **Exemplo de body**:

```json
{
  "cpf": "123.456.789-00",
  "senha": "senha456"
}
```

#### [PATCH] /redefinir-senha

- **Descrição**: Operação de redefinir de senha. Requer o envio do CPF, do e-mail e da nova senha do cliente pelo body.

- **Exemplo de body**:

```json
{
  "cpf": "123.456.789-00",
  "email": "josemaria@email.com",
  "senha": "senha123"
}
```

#### [PATCH] /{id}/logoff

- **Descrição**: Operação de logoff. Requer no path param o id do cliente que deseja fazer o logoff.

#### [PATCH] /{id}/upgrade-de-conta

- **Descrição**: Operação de atualização do tipo de conta bancária. Requer no path param o id do cliente que deseja fazer o upgrade de conta.

#### [PUT] /{id}/atualizar-cadastro

- **Descrição**: Operação de atualização dos dados cadastrais. Requer o envio dos dados atualizados do cliente pelo body.

- **Exemplo de body**:

```json
{
  "id": "d3ea0477-6b4e-4b65-942a-4e81bae99f78",
  "cpf": "123.456.789-00",
  "nome": "José Maria",
  "dataNascimento": "01/01/2000",
  "telefone": "1234-5678",
  "endereco": "Endereço do José, 001",
  "rendaMensal": "3600.00",
  "email": "josemaria@email.com",
  "senha": "senha123",
  "logado": true,
  "dataCriacao": "20-06-2024 20:26:17",
  "dataAtualizacao": "20-06-2024 20:26:17"
}
```

### Rotas da área de conta bancária (/conta)

| Método | Rota                                 | Função                                                          |
| ------ | ------------------------------------ | --------------------------------------------------------------- |
| GET    | /conta/idCliente                     | Operação de verificação dos dados da conta atrelada ao usuário. |
| GET    | /conta/{idConta}/saldo               | Operação de verificação de saldo.                               |
| GET    | /conta/{idConta}/extrato             | Operação de verificação de extrato mensal.                      |
| PATCH  | /conta/{idConta}/deposito            | Operação de depósito bancário.                                  |
| PATCH  | /conta/{idConta}/saque               | Operação de saque.                                              |
| PATCH  | /conta/{idConta}/pagamento-de-conta  | Operação de pagamento de conta.                                 |
| PATCH  | /conta/{idContaOrigem}/transferencia | Operação de transferência bancária.                             |
| PATCH  | /conta/{idConta}/pix                 | Operação de transferência por pix.                              |
| POST   | /conta/{idConta}/pix/cadastro        | Operação de cadastro da chave pix.                              |
| POST   | /conta/{idConta}/pix/descadastro     | Operação de descadastro da chave pix.                           |

#### [GET] /{idCliente}

- **Descrição**: Operação de verificação dos dados da conta atrelada ao usuário. Requer no path param o id do cliente que deseja verificar as informações da conta.

#### [GET] /{idConta}/saldo

- **Descrição**: Operação de verificação de saldo. Requer no path param o id da conta que deseja verificar o saldo.

#### [GET] /{idConta}/extrato

- **Descrição**: Operação de verificação de extrato mensal. Requer no path param o id da conta que deseja verificar o extrato, e os query param de "mes" e "ano" que se deseja verificar o extrato.

- **Exemplo de request com query param**:
  `/conta/4cfdd0ef-3d00-4197-956c-c3afde664b61/extrato?mes=6&ano=2024`

#### [PATCH] /{idConta}/deposito

- **Descrição**: Operação de depósito bancário. Requer no path param o id da conta e, no body, o envio da quantia a ser depositada.

- **Exemplo de body**:

```json
{
  "quantia": "5000.00"
}
```

#### [PATCH] /{idConta}/saque

- **Descrição**: Operação de saque. Requer no path param o id da conta e, no body, o envio da quantia a ser sacada.

- **Exemplo de body**:

```json
{
  "quantia": "100.00"
}
```

#### [PATCH] /{idConta}/pagamento-de-conta

- **Descrição**: Operação de pagamento de conta. Requer no path param o id da conta e, no body, o envio da quantia a ser paga. Idealmente o body também deveria receber o código de barras da conta, mas o mesmo não será utilizado nesta aplicação.

- **Exemplo de body**:

```json
{
  "boletoBancario": "código-do-boleto-bancário",
  "quantia": "20.00"
}
```

#### [PATCH] /{idContaOrigem}/transferencia

- **Descrição**: Operação de transferência bancária. Requer no path param o id da conta realizandoo a transferência e, no body, o envio do id da conta recebendo a transferência e da quantia a ser transferida.

- **Exemplo de body**:

```json
{
  "idContaDestino": "40b43186-93d9-4a1c-90de-382edc02e30b",
  "quantia": "100.00"
}
```

#### [PATCH] /{idConta}/pix

- **Descrição**: Operação de transferência por pix. Requer no path param o id da conta e, no body, o envio da chave pix de destino e a quantia a ser transferida.

- **Exemplo de body**:

```json
{
  "chavePix": "batatafrita",
  "quantia": "50.00"
}
```

#### [POST] /{idConta}/pix/cadastro

- **Descrição**: Operação de cadastro da chave pix. Requer no path param o id da conta e, no body, o envio da chave pix a ser cadastrada.

- **Exemplo de body**:

```json
{
  "chavePix": "bananafrita"
}
```

#### [POST] /{idConta}/pix/descadastro

- **Descrição**: Operação de descadastro da chave pix. Requer no path param o id da conta e, no body, o envio da chave pix a ser cadastrada.

### Rotas da área de transações (/transacao)(Espaço restrito aos operários do banco)

| Método | Rota                      | Função                                                            |
| ------ | ------------------------- | ----------------------------------------------------------------- |
| GET    | /transacao/transacoes     | Operação de listar todas as operações realizadas pelo banco.      |
| GET    | /transacao/depositos      | Operação de listar todos os depósitos realizados pelo banco.      |
| GET    | /transacao/saques         | Operação de listar todos os saques realizados pelo banco.         |
| GET    | /transacao/transferencias | Operação de listar todas as transferências realizadas pelo banco. |
| GET    | /transacao/pagamentos     | Operação de listar todos os pagamentos realizados pelo banco.     |
| GET    | /transacao/pix            | Operação de listar todos os pix realizados pelo banco.            |

#### [GET] /transacoes

- **Descrição**: Operação de listar todas as operações realizadas pelo banco. Pode ser adicionado o query param com a id da conta para filtrar o resultado.

- **Exemplo de request com query param**:
  `/transacao/transacoes?id=40b43186-93d9-4a1c-90de-382edc02e30b`

#### [GET] /depositos

- **Descrição**: Operação de listar todos os depósitos realizados. Pode ser adicionado o query param com a id da conta para filtrar o resultado.

- **Exemplo de request com query param**:
  `/transacao/depositos?id=40b43186-93d9-4a1c-90de-382edc02e30b`

#### [GET] /saques

- **Descrição**: Operação de listar todos os saques realizados. Pode ser adicionado o query param com a id da conta para filtrar o resultado.

- **Exemplo de request com query param**:
  `/transacao/saques?id=40b43186-93d9-4a1c-90de-382edc02e30b`

#### [GET] /transferencias

- **Descrição**: Operação de listar todas as transferências realizadas. Pode ser adicionado o query param com a id da conta para filtrar o resultado.

- **Exemplo de request com query param**:
  `/transacao/transferencias?id=40b43186-93d9-4a1c-90de-382edc02e30b`

#### [GET] /pagamentos

- **Descrição**: Operação de listar todos os pagamentos realizados. Pode ser adicionado o query param com a id da conta para filtrar o resultado.

- **Exemplo de request com query param**:
  `/transacao/pagamentos?id=40b43186-93d9-4a1c-90de-382edc02e30b`

#### [GET] /pix

- **Descrição**: Operação de listar todos os pix realizados. Pode ser adicionado o query param com a id da conta para filtrar o resultado.

- **Exemplo de request com query param**:
  `/transacao/pix?id=40b43186-93d9-4a1c-90de-382edc02e30b`

### Demais rotas restritas aos operários do banco

| Método | Rota              | Função                                            |
| ------ | ----------------- | ------------------------------------------------- |
| GET    |                   | Página inicial da API                             |
| GET    | /cliente/clientes | Operação de listar todos os clientes cadastrados. |
| GET    | /conta/contas     | Operação de listar todas as contas cadastradas.   |

#### [GET]

- **Descrição**: Página inicial da API .

#### [GET] /cliente/clientes

- **Descrição**: Operação de listar todos os clientes cadastrados.

#### [GET] /conta/contas

- **Descrição**: Operação de listar todas as contas cadastradas.

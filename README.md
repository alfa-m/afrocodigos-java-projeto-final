# API de banco

## Descrição

Projeto final do bootcamp Afrocódigos - Trilha Back-End. O projeto representa a API de uma instituição financeira, implementada em Java com Spring Boot e utilizando a arquitetura MSC.

## Ferramentas

- Java 17
- Spring Boot 3
- Maven
- PostgreSQL 16
- Insomnia
- IDE

## Como testar

### Testando a API online

Para testar a API, acesse [aqui](ec2-34-230-37-196.compute-1.amazonaws.com).

Talvez o servidor não esteja conectado no momento de acesso. Nesse caso, por favor, abra uma issue.

### Testando a API localmente

Os passos necessários para testar localmente são:

- Instalar as ferramentas necessárias
- Clonar o projeto
- Executar o arquivo "BancoFicticioApplication.java"
- Testar as rotas da API (rotas listadas [aqui](#rotas-da-api))

## Entidades

### Cliente

Representa o cliente do banco.

#### Construtor

```plaintext
public Cliente(String cpf, String nome, String dataNascimento, String telefone, String endereco, double rendaMensal, String email, String senha)
```

#### Atributos

- id (UUID) - ID do Cliente
- cpf (String) - CPF do Cliente
- dataNascimento (String) - Data de nascimento do Cliente
- telefone (String) - Número de telefone do Cliente
- endereco (String) - Endereço do Cliente
- rendaMensal (double) - Valor da renda mensal do Cliente
- email (String) - E-mail do Cliente
- senha (String) - Senha da conta do Cliente
- logado (boolean) - Status de login do Cliente
- dataCriacao (LocalDateTime) - Data e horário da criação da instância de Cliente
- dataAtualizacao (LocalDateTime) - Data e horário da última modificação feita na instância de Cliente

#### Métodos

- Getters e setters

### Conta

Representa a conta bancária do cliente.

#### Construtor

```plaintext
public Conta(Cliente cliente)
```

#### Atributos

- id (UUID) - ID da Conta (seria o equivalente ao nº da agência + nº da conta)
- cliente (Cliente) - Entidade cliente associada à Conta
- tipo (String) - Tipo da Conta
- saldo (double) - Saldo da Conta
- limite (double) - Limite da Conta
- transferenciaMaxima (double) - Valor de transferência máxima permitida para esta Conta
- chavePix (String) - Valor da chave pix da Conta
- dataCriacaoConta (LocalDateTime) - Data e horário da criação da instância de Conta
- dataAtualizacaoConta (LocalDateTime) - Data e horário da última modificação feita na instância de Conta

#### Métodos

- Getters e setters
- atualizaLimite() - Método utilizado para atualizar o valor do atributo limite após cada transação bancária

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

- idTransacao (UUID) - ID da Transação
- idConta (UUID) - ID da Conta realizando a Transação
- tipo (String) - Tipo da Transação
  - "depósito"
  - "saque"
  - "pagamento de conta"
  - "transferência bancária"
  - "pix"
- quantia (double) - Valor da quantia sendo movimentada na Transação
- idContaDestino (UUID) - ID da Conta envolvida na Transação
- dataTransacao (LocalDateTime) - Data e horário da criação da instância de Transação

#### Métodos

- Getters e setters

## Regras de negócio

## Estrutura do projeto

```plaintext
📂src
└──📂 main
    ├──📂 java
    │   └──📂 com
    │       └──📂 ficticio
    │           └──📂 bancoficticio
    │               ├──📁 config
    |                   └──📄 DataInitializer.java
    │               ├──📁 controller
    |                   ├──📄 ClienteController.java
    |                   ├──📄 ContaController.java
    |                   └──📄 TransacaoController.java
    │               ├──📁 exception
    |                   ├──📄 ClienteException.java
    |                   └──📄 ContaException.java
    │               └──📂 model
    │                   └──📁 entity
    |                       ├──📄 Cliente.java
    |                       ├──📄 Conta.java
    |                       ├──📄 ContaCorrente.java
    |                       ├──📄 ContaPagamento.java
    |                       └──📄 Transacao.java
    │               ├──📁 repository
    |                   ├──📄 ClienteRepository.java
    |                   ├──📄 ContaRepository.java
    |                   └──📄 TransacaoRepository.java
    │               ├──📁 service
    |                   ├──📄 ClienteService.java
    |                   └──📄 ContaService.java
    │               └──📄 BancoFicticioApplication.java
    └──📂 resources
        └──📄 application.properties
```

- DataInitializer.java - Inicializa e conecta ao banco de dados
- ClienteController.java - Controller da classe Cliente. Relaciona as rotas de requisições HTTP relacionadas à classe Cliente.
- ContaController.java - Controller da classe Conta. Relaciona as rotas de requisições HTTP relacionadas à classe Conta.
- TransacaoController.java - Controller da classe Transacao. Relaciona as rotas de requisições HTTP relacionadas à classe Transacao.
- ClienteException.java - Tratamento de exceções da classe Cliente.
- ContaException.java - Tratamento de exceções da classe Conta.
- Cliente.java - Classe da entidade Cliente.
- Conta.java - Classe da entidade Conta.
- ContaCorrente.java - Classe da entidade ContaCorrente. Subclasse da classe Conta.
- ContaPagamento.java - Classe da entidade ContaPagamento. Subclasse da classe Conta.
- Transacao.java - Classe da entidade Transacao.
- ClienteRepository.java - Repositório de dados de instâncias da classe Cliente.
- ContaRepository.java - Repositório de dados de instâncias da classe Conta.
- TransacaoRepository.java - Repositório de dados de instâncias da classe Transacao.
- ClienteService.java - Implementação das regras de negócio relacionadas a entidade Cliente.
- ContaService.java - Implementação das regras de negócio relacionadas a entidade Conta.
- BancoFicticioApplication.java - Aplicação Spring Boot.

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

- **Descrição**: Operação de verificação de extrato mensal. Requer no path param o id da conta que deseja verificar o extrato.

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

Operação de cadastro da chave pix.

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

- **Descrição**: Operação de listar todas as operações realizadas pelo banco.

#### [GET] /depositos

- **Descrição**: Operação de listar todos os depositos realizados pelo banco.

#### [GET] /saques

- **Descrição**: Operação de listar todos os saques realizados pelo banco.

#### [GET] /transferencias

- **Descrição**: Operação de listar todas as transferencias realizadas pelo banco.

#### [GET] /pagamentos

- **Descrição**: Operação de listar todos os pagamentos realizados pelo banco.

#### [GET] /pix

- **Descrição**: Operação de listar todos os pix realizados pelo banco.

### Demais rotas restritas aos operários do banco

| Método | Rota | Função |
| ------ | ---- | ------ |

#### [GET] /cliente/clientes

Operação de listar todos os clientes cadastrados.

#### [GET] /conta/contas

Operação de listar todas as contas cadastradas.

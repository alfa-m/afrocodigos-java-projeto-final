# API de banco

## Descrição

Projeto final do bootcamp Afrocódigos - Trilha Back-End. O projeto representa a API de uma instituição financeira, implementada em Java com Spring Boot.

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

```
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

```
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

```
public Transacao(UUID idConta, String tipo, double quantia)
```

ou

```
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

## Rotas da API

### Rotas da área do cliente (/cliente)

#### [POST] /cadastro

Operação de cadastro de novo cliente.

#### [DELETE] /{id}/encerramento-de-conta

Operação de encerrar a conta bancária.

#### [PATCH] /login

Operação de login.

#### [PATCH] /redefinir-senha

Operação de redefinir de senha.

#### [PATCH] /{id}/logoff

Operação de logoff.

#### [PATCH] /{id}/upgrade-de-conta

Operação de atualização do tipo de conta bancária.

#### [PUT] /{id}/atualizar-cadastro

Operação de atualização dos dados cadastrais.

### Rotas da área de conta bancária (/conta)

#### [GET] /{idCliente}

Operação de verificação dos dados da conta atrelada ao usuário.

#### [GET] /{idConta}/saldo

Operação de verificação de saldo.

#### [GET] /{idConta}/extrato

Operação de verificação de extrato mensal.

#### [PATCH] /{idConta}/deposito

Operação de depósito bancário.

#### [PATCH] /{idConta}/saque

Operação de saque.

#### [PATCH] /{idConta}/pagamento-de-conta

Operação de pagamento de conta.

#### [PATCH] /{idContaOrigem}/transferencia

Operação de transferência bancária.

#### [PATCH] /{idConta}/pix

Operação de transferência por pix.

#### [POST] /{idConta}/pix/cadastro

Operação de cadastro da chave pix.

#### [POST] /{idConta}/pix/descadastro

Operação de descadastro da chave pix.

### Rotas da área de transações (/transacao)(Espaço restrito aos operários do banco)

#### [GET] /transacoes

Operação de listar todas as operações realizadas pelo banco.

#### [GET] /depositos

Operação de listar todos os depositos realizados pelo banco.

#### [GET] /saques

Operação de listar todos os saques realizados pelo banco.

#### [GET] /transferencias

Operação de listar todas as transferencias realizadas pelo banco.

#### [GET] /pagamentos

Operação de listar todos os pagamentos realizados pelo banco.

#### [GET] /pix

Operação de listar todos os pix realizados pelo banco.

### Demais rotas restritas aos operários do banco

#### [GET] /cliente/clientes

Operação de listar todos os clientes cadastrados.

#### [GET] /conta/contas

Operação de listar todas as contas cadastradas.

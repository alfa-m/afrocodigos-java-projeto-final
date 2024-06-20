# API de banco
## Descrição
 Projeto final do bootcamp Afrocódigos - Trilha Back-End. Mock-up de API de instituição financeira.

## Como testar

## Entidades


## Regras de negócio


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

# API de banco
## Descrição
 Projeto final do bootcamp Afrocódigos - Trilha Back-End. Mock-up de API de instituição financeira.

## Rotas da API
### Rotas da área do cliente
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

### Rotas da área de conta bancária (PENDENTE)




#### /GET/{id}/saldo
Operação de obtenção de saldo.

#### /GET/{id}/extrato
Operação de obtenção de extrato.

### 

#### /POST/{id}/pix/cadastrar
Operação de cadastro de nova chave pix.

### Rotas PUT

### Rotas PATCH
#### /PATCH/{idOrigem}/transferencia/{idDestino}
Operação de transferência entre contas bancárias.

#### /PATCH/{id}/deposito
Operação de depósito bancário.

#### /PATCH/{id}/saque
Operação de saque bancário.

#### /PATCH/{id}/pagamento
Operação de pagamentos de contas.


### Rotas DELETE

#### /DELETE/{id}/pix/deletar
Operação de apagar chave pix.

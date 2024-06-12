# API de banco
## Descrição
 Projeto final do bootcamp Afrocódigos - Trilha Back-End. Mock-up de API de instituição financeira.

## Rotas da API
### Rotas GET
#### /GET/login/{id}
Operação de login.

#### /GET/{id}/saldo
Operação de obtenção de saldo.

#### /GET/{id}/extrato
Operação de obtenção de extrato.

### Rotas POST
#### /POST/cadastrar
Operação de cadastro de novo cliente.

#### /POST/{id}/pix/cadastrar
Operação de cadastro de nova chave pix.

### Rotas PUT
#### /PUT/{id}/atualizar
Operação de atualização dos dados cadastrais.

### Rotas PATCH
#### /PATCH/{idOrigem}/transferencia/{idDestino}
Operação de transferência entre contas bancárias.

#### /PATCH/{id}/deposito
Operação de depósito bancário.

#### /PATCH/{id}/saque
Operação de saque bancário.

#### /PATCH/{id}/pagamento
Operação de pagamentos de contas.

#### /PATCH/{id}/redefinir-senha
Operação de redefinir de senha.

### Rotas DELETE
#### /DELETE/{id}/deletar
Operação de encerrar a conta bancária.

#### /DELETE/{id}/pix/deletar
Operação de apagar chave pix.

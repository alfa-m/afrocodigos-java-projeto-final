# API de banco
## Descrição
 Projeto final do bootcamp Afrocódigos - Trilha Back-End. Mock-up de API de instituição financeira.

## Rotas da API
### Rotas GET
#### /GET/login/{CPF}
Operação de login.

#### /GET/{CPF}/saldo
Operação de obtenção de saldo.

#### /GET/{CPF}/extrato
Operação de obtenção de extrato.

### Rotas POST
#### /POST/cadastrar
Operação de cadastro de novo cliente.

### Rotas PUT
#### /PUT/{CPF}/atualizar
Operação de atualização dos dados cadastrais.

### Rotas PATCH
#### /PATCH/{CPF}/transferir/{outro-CPF}
Operação de transferência entre contas bancárias.

#### /PATCH/{CPF}/depositar
Operação de depósito bancário.

#### /PATCH/{CPF}/saque
Operação de saque bancário.

#### /PATCH/{CPF}/pagamento-de-contas
Operação de pagamentos de contas.

#### /PATCH/{CPF}/recuperar-senha
Operação de recuperação de senha.

### Rotas DELETE
#### /DELETE/{CPF}/deletar
Operação de encerrar a conta bancária.

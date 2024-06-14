package com.ficticio.bancoficticio.exception;

public class ClienteException extends RuntimeException {
    public ClienteException(String message){
        super(message);
    }

    public static class ClienteNaoCadastradoException extends ClienteException{
        public ClienteNaoCadastradoException(){
            super("Cliente não cadastrado");
        }
    }

    public static class ClienteJaCadastradoException extends ClienteException{
        public ClienteJaCadastradoException(){
            super("Cliente informado já consta na nossa base de dados");
        }
    }

    public static class LoginIncorretoException extends ClienteException{
        public LoginIncorretoException(){
            super("Informações de login (cpf ou senha) incorretas. Tente novamente");
        }
    }

    public static class ContaSemPermissaoException extends ClienteException{
        public ContaSemPermissaoException(){
            super("Nível de conta do cliente não está apta a exercer tal transação");
        }
    }

    public static class SemLimiteException extends ClienteException{
        public SemLimiteException(){
            super("Cliente não possui fundos suficientes para executar tal transação");
        }
    }

    public static class SemChavePixException extends ClienteException{
        public SemChavePixException(){
            super("Cliente ainda não possui uma chave pix cadastrada");
        }
    }

}

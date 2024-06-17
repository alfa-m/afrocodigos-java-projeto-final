package com.ficticio.bancoficticio.exception;

public class ClienteException extends RuntimeException {
    public ClienteException(String message){
        super(message);
    }

    public static class ClienteNaoCadastradoException extends ClienteException{
        public ClienteNaoCadastradoException(){
            super("Cliente não cadastrado. Verifique as informações enviadas e tente novamente");
        }
    }

    public static class ClienteJaCadastradoException extends ClienteException{
        public ClienteJaCadastradoException(){
            super("Cliente informado já consta na nossa base de dados. Tente realizar o login");
        }
    }

    public static class LoginIncorretoException extends ClienteException{
        public LoginIncorretoException(){
            super("Informações de login não compatíveis com a base de dados. Por favor, corrija suas informações ou realize um cadastro");
        }
    }

    public static class ClienteNaoLogado extends ClienteException{
        public ClienteNaoLogado(){
            super("O cliente precisa estar logado para realizar tal operação. Por favor, realize o login");
        }
    }
}

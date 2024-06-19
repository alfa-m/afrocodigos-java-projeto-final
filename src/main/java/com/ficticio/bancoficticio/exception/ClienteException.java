package com.ficticio.bancoficticio.exception;

public class ClienteException extends RuntimeException {
    public ClienteException(String message){
        super(message);
    }

    public static class ClienteNaoCadastradoException extends ClienteException{
        public ClienteNaoCadastradoException(){
            super("Cliente não cadastrado. Peça para verificar as informações enviadas e tentar novamente");
        }
    }

    public static class ClienteJaCadastradoException extends ClienteException{
        public ClienteJaCadastradoException(){
            super("Cliente informado já consta na nossa base de dados. Peça para realizar o login");
        }
    }

    public static class LoginIncorretoException extends ClienteException{
        public LoginIncorretoException(){
            super("Informações de login não compatíveis com a base de dados. Peça a correção das informações ou a realização de um cadastro");
        }
    }

    public static class ClienteNaoLogadoException extends ClienteException{
        public ClienteNaoLogadoException(){
            super("O cliente precisa estar logado para realizar tal operação. Peça para realizar o login");
        }
    }
    public static class RendaBaixaException extends ClienteException{
        public RendaBaixaException(){
            super("O cliente não possui a renda mínima para fazer o upgrade de conta");
        }
    }
}

package com.ficticio.bancoficticio.exception;

public class ContaException extends RuntimeException {
    public ContaException(String message){
        super(message);
    }

    public static class ContaNaoExisteException extends ContaException{
        public ContaNaoExisteException(){
            super("Conta não cadastrada. Peça para o cliente verificar as informações");
        }
    }

    public static class ContaSemPermissaoException extends ContaException{
        public ContaSemPermissaoException(){
            super("Nível de conta do cliente não está apta a exercer tal transação");
        }
    }

    public static class SemLimiteException extends ContaException{
        public SemLimiteException(){
            super("Cliente não possui fundos suficientes para executar tal transação");
        }
    }

    public static class SemChavePixException extends ContaException{
        public SemChavePixException(){
            super("Cliente ainda não possui uma chave pix cadastrada");
        }
    }
}

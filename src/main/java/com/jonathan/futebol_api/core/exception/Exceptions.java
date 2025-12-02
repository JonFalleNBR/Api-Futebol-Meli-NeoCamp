package com.jonathan.futebol_api.core.exception;


public class Exceptions {

    public static class ClubeInvalidoeException extends RuntimeException {

        public ClubeInvalidoeException(com.jonathan.futebol_api.utils.Exceptions.mensagensException mensagem) {
            super(mensagem.getMensagem());
        }


    }


    public static class EstadioInexistenteException extends RuntimeException{
            public EstadioInexistenteException(com.jonathan.futebol_api.utils.Exceptions.mensagensException mensagem){
                super(mensagem.getMensagem());

            }

    }

    public static class PartidaInvalidaException extends RuntimeException{
        public PartidaInvalidaException(com.jonathan.futebol_api.utils.Exceptions.mensagensException mensagem){
            super(mensagem.getMensagem());
        }

    }

}





package com.jonathan.futebol_api.core.exception;


import com.jonathan.futebol_api.utils;

public class Exceptions {

    public static class ClubeInvalidoeException extends RuntimeException {

        public ClubeInvalidoeException(utils.MensagensException mensagem) {
            super(mensagem.getMensagem());
        }


    }


    public static class EstadioInexistenteException extends RuntimeException{
            public EstadioInexistenteException(utils.MensagensException mensagem){
                super(mensagem.getMensagem());

            }

    }

    public static class PartidaInvalidaException extends RuntimeException{
        public PartidaInvalidaException(utils.MensagensException mensagem){
            super(mensagem.getMensagem());
        }

    }

}





package com.jonathan.futebol_api;

public class utils {

    public enum MensagensException{
        CLUBE_INEXISTENTE("Clube nao encontrado na nossa base de Dados"),

        ESTADIO_INEXISTENTE("Estadio nao encontrado na base de Dados");

        private final String mensagem;


        MensagensException(String mensagem){
            this.mensagem = mensagem;

        }

        public String getMensagem() {
            return mensagem;

        }
    }


}

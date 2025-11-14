package com.jonathan.futebol_api;

public class utils {

    public enum MensagensException{
        CLUBE_INEXISTENTE("CLube nao encontrado na noosa base"),

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

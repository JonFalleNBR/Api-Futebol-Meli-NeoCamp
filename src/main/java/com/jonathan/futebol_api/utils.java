package com.jonathan.futebol_api;

public class utils {

    public enum MensagensException{
        CLUBE_INEXISTENTE("Clube nao encontrado na nossa base de Dados"),

        ESTADIO_INEXISTENTE("Estadio nao encontrado na base de Dados"),

        PARTIDA_INVALIDA("Partida nao encontrada em nossa base de Dados"),

        CLUBE_INVALIDO("Clube invalido, por favor informar um que seja valido"),

        CLUBE_JA_EXISTE("Clube ja existe, por favor de outro nome"),

        PARTIDA_JA_CRIADA("Partida ja criada no nosso historico");

        private final String mensagem;


        MensagensException(String mensagem){
            this.mensagem = mensagem;

        }

        public String getMensagem() {
            return mensagem;

        }
    }


}

package com.jonathan.futebol_api.helper;

import com.jonathan.futebol_api.core.entity.Clube;
import org.springframework.data.jpa.domain.Specification;

public class ClubeSpecifications {

    public static Specification<Clube> nomeContains(String nome){
        return(root, query, cb) ->
                    cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase());
    }

    public static Specification<Clube> estadoEquals(String estado){
        return((root, query, cb) ->
                      cb.like(cb.lower(root.get("estado")), estado.toLowerCase()));

    }


    public static Specification<Clube> ativoEquals(Boolean ativo){
        return (root, query, cb) ->
                    cb.equal(root.get("ativp"), ativo);
    }

}

package com.jonathan.futebol_api.core.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**

 Converte Boolean <-> Integer para persistência.

 convertToDatabaseColumn: true -> 1, false -> 0, null -> null
 convertToEntityAttribute: 0 -> false, qualquer outro número != 0 -> true, null -> null
 Não usa autoApply: aplique explicitamente em cada campo com @Convert(converter = BooleanIntegerConverter.class)
 */
@Converter
public class BooleanIntegerConverter implements AttributeConverter<Boolean, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
        if (attribute == null) {
            return null;
        }
        return attribute ? 1 : 0;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
        if (dbData == null) {
            return null;
        }
        return dbData != 0;
    }
}
package com.scaler.bookmyshow.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.util.Objects;

public class ObjectMapperUtils {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    @SneakyThrows
    public static String convertObjectToJson(Object object) {
        if (Objects.isNull(object)) return null;
        return OBJECT_MAPPER.writeValueAsString(object);
    }
}

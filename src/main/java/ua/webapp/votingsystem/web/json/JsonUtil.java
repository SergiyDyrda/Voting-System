package ua.webapp.votingsystem.web.json;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectReader;

import java.io.IOException;
import java.util.List;

import static ua.webapp.votingsystem.web.json.JacksonObjectMapper.getMapper;

public class JsonUtil {

    private JsonUtil(){
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        try {
            return getMapper().readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read object from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> List<T> readValues(String json, Class<T> clazz) {
        ObjectReader reader = getMapper().readerFor(clazz);
        try {
            return reader.<T>readValues(json).readAll();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid read array from JSON:\n'" + json + "'", e);
        }
    }

    public static <T> String writeValue(T value) {
        try {
            return getMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Invalid write to JSON:\n'" + value + "'", e);
        }
    }
}

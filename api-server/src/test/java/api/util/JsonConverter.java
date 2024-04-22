package api.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static String toJsonString(Object o) throws JsonProcessingException {
        return objectMapper.writeValueAsString(o);
    }

    public static <T> T parseJsonString(String jsonString, Class<T> targetType) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, targetType);
    }


}

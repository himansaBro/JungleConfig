package core.dataTypes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectConfig implements JConfigConverter<Object> {
    @Override
    public Class<Object> getType() {
        return Object.class;
    }

    @Override
    public String Encode(Object data) {
        try {
            return JacksonModule.getMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object Decode(String data) {
        return data;
    }
}

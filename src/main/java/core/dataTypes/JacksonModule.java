package core.dataTypes;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class JacksonModule {
    public static ObjectMapper mapper=new ObjectMapper();
    private static boolean setuped;
    public static ObjectMapper getMapper(){
        if (!setuped){
            mapper.registerModule(new JavaTimeModule());
            mapper.registerModule(new Jdk8Module());
        }
        return mapper;
    }
}

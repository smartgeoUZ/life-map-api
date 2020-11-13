package uz.smartgeo.lifemap.api.utils;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class ConverterJsonToObject<T> {
    ObjectMapper mapper;

    private ObjectMapper getInstance() {
        if (mapper == null)
            mapper = new ObjectMapper();
        return mapper;
    }
    public T convert(String jsonString, Class<T> type) {
        mapper = getInstance();
        T obj  = null;

        try {
            obj  = mapper.readValue(jsonString, type);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return obj;
    }
}

package core;

import Exceptions.ConverterNotFoundException;
import core.DataModel.TripleMap;
import core.dataTypes.*;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TranslationManager {
    private IOHandler handler;
    private Map<Class<?>, JConfigConverter<?>> converters=new HashMap<>();
    private String Encode(String data){
        return URLEncoder.encode(data, StandardCharsets.UTF_8);
    }
    private String Decode(String data){
        return URLDecoder.decode(data,StandardCharsets.UTF_8);
    }

    public TranslationManager(File file) {
        handler = new IOHandler(file);
        this.registerConverters(new BooleanConfig(),new StringConfig(),new IntagerConfig(),new ObjectConfig());
    }
    public TranslationManager(File file,JConfigConverter<?>... converters) {
        handler = new IOHandler(file);
        this.registerConverters(converters);
    }

    private String ConvertForSave(Object data){
        Class<?> dataType = data.getClass();
        if (!converters.containsKey(dataType)) throw new ConverterNotFoundException(dataType);
        return converters.get(dataType).Encode(data);
    }
    private Object ConvertForGive(String data,Class<?> type){
        if (!converters.containsKey(type)) throw new ConverterNotFoundException(type);
        return converters.get(type).Decode(data);
    }
    public void registerConverters(JConfigConverter<?>... Converters){
        for (JConfigConverter<?> converter : Converters) {
            converters.put(converter.getType(),converter);
        }
    }
    public void removeConvertor(Class<?> type){
        converters.remove(type);
    }
    public void removeAllConvertors(){
        converters.clear();
    }
    public TripleMap<String,Class<?>,Object> getDataMap(){
        String data ="";
        TripleMap<String,Class<?>,Object> dataMap = new TripleMap<>();
        try {
            data = handler.ReadFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String[] dats = data.split("\n");
        for (String dat:dats){
            if (!dat.strip().isBlank()){
                int keySepIndex,ValSepIndex;
                keySepIndex = dat.indexOf(":");
                ValSepIndex = dat.indexOf("=");
                String key = dat.substring(0,keySepIndex);
                String type= dat.substring(keySepIndex+1,ValSepIndex);
                String value= dat.substring(ValSepIndex).replaceFirst("=","");
                dataMap.put(key,getType(type),ConvertForGive(Decode(value),getType(type)));
            }
        }
        return dataMap;
    }
    public void setDataFromMap(Map<String,Object> data){
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String,Object> d:data.entrySet()){
            StringBuilder sb1 = new StringBuilder();
            sb1.append(d.getKey())
                    .append(":")
                    .append(getType(d.getValue().getClass().getSimpleName()).getSimpleName())
                    .append("=")
                    .append(Encode(ConvertForSave(d.getValue())));
            builder.append(sb1.toString()).append("\n");
        }
        try {
            handler.WriteFile(builder.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private Class<?> getType(String type){
        Set<Class<?>> types = converters.keySet();
        for (Class<?> t:types){
            if (t.getSimpleName().equalsIgnoreCase(type)) {
                return t;
            }
        }
        System.err.println("No Compatible Converter for "+type+" not found, Falling Back To Object");
        return Object.class;
    }
}

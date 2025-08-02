package core;

import Exceptions.InvalidTypeCastExeption;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import core.DataModel.TripleMap;
import core.dataTypes.JConfigConverter;
import core.dataTypes.JacksonModule;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ConfigHandler {
    private TranslationManager manager;
    private Map<String,Object> saveQuery=new HashMap<>();
    private boolean useCache;
    private boolean isCacheDirty;
    private int cacheIterations=0;
    public int MAX_CACHE_ITERATIONS=10;
    private TripleMap<String,Class<?>,Object> cache;
    private Boolean isInTransaction=false;
    public ConfigHandler(File file,boolean useCache) {
        this.manager = new TranslationManager(file);
        this.useCache= useCache;
        if (useCache){
            cache = manager.getDataMap();
        }
    }
    public void addAdapters(JConfigConverter<?>... converters){
        manager.registerConverters(converters);
    }
    public void removeAdapter(Class<?> dataType){
        manager.removeConvertor(dataType);
    }
    public void removeAllAdapters(){
        manager.removeAllConvertors();
    }
    private void updateCache(){
        if (isCacheDirty || cacheIterations>MAX_CACHE_ITERATIONS){
            cache = manager.getDataMap();
            cacheIterations=0;
        }
        isCacheDirty=false;
    }
    public void invalidateCache(){
        isCacheDirty=true;
    }
    public ConfigHandler BeginTransaction(){
        isInTransaction=true;
        return this;
    }
    public void Commit(){
        Map<String,Object> newData = migrate(saveQuery);
        manager.setDataFromMap(newData);
        if (useCache){
            constructCache(newData);
        }
    }
    public void Rollback(){
        saveQuery.clear();
    }
    public ConfigHandler set(String key,Object data){
        System.out.println("--setting "+key);
        validateKeys(key);
        saveQuery.put(key,data);
        if (!isInTransaction){
            Map<String,Object> newData = migrate(saveQuery);
            manager.setDataFromMap(newData);
            if (useCache){
                constructCache(newData);
            }
        }

        return this;
    }
    private void constructCache(Map<String,Object> data){
        TripleMap<String,Class<?>,Object> map = new TripleMap<>();
        for (Map.Entry<String,Object> d:data.entrySet()){
            map.put(d.getKey(),d.getValue().getClass(),d.getValue());
        }
        cache = map;
        cacheIterations++;
    }
    private void validateKeys(String key){
        if (key.contains("\n")||key.contains(":")||key.contains("=")) throw new IllegalArgumentException("Key's can't contain ':' '=' '\\n' ");
    }
    public <T> Optional<T> get(String key,Class<T> type){
        System.out.println("--Getting "+key);
        TripleMap<String,Class<?>,Object> d;
        if (useCache){
            updateCache();
            d = cache;
        }else {
            d = manager.getDataMap();
        }
        if (!d.containsKey(key)) return Optional.empty();
        if (d.getValue1(key) != type) throw new InvalidTypeCastExeption(d.getValue1(key).getName()+" cannot be casted to "+type.getName());
        return Optional.of((T) d.getValue2(key));
    }
    private Map<String,Object> migrate(Map<String,Object> newData){
        Map<String,Object> outData = new HashMap<>();
        TripleMap<String,Class<?>,Object> currentData;
        if (useCache){
            updateCache();
            currentData = cache;
        }else {
            currentData = manager.getDataMap();
        }
        currentData.getEntryList().forEach(stringClassObjectEntry -> {
            outData.put(stringClassObjectEntry.getKey(),stringClassObjectEntry.getValue2());
        });
        outData.putAll(newData);
        return outData;
    }
    public TripleMap<String,Class<?>,Object> findSimilar(String key){
        TripleMap<String,Class<?>,Object> outputMap=new TripleMap<>();
        TripleMap<String,Class<?>,Object> d;
        if (useCache){
            updateCache();
            d=cache;
        }else {
            d = manager.getDataMap();
        }
        for (TripleMap.Entry<String,Class<?>,Object> dataEntry:d.getEntryList()){
            if (dataEntry.getKey().startsWith(key)){
                outputMap.put(dataEntry);
            }
        }
        return outputMap;
    }
    public List<String> getPaths(String mainPath){
        return findSimilar(mainPath).getKeyList();
    }
    public void SerializedSet(String path,Object data) throws JsonProcessingException {
        String json = JacksonModule.getMapper().writeValueAsString(data);
        set(path,json);
    }
    public <T> Optional<T> SerializedGet(String path,Class<T> type) throws JsonProcessingException {
        Optional<String> jsonData = get(path,String.class);
        if (jsonData.isEmpty()) return Optional.empty();
        return Optional.of(JacksonModule.getMapper().readValue(jsonData.get(),type));
    }
}

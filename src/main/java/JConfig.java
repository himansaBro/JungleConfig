import com.fasterxml.jackson.core.JsonProcessingException;
import core.ConfigHandler;
import core.DataModel.TripleMap;
import core.dataTypes.JConfigConverter;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class JConfig {
    private static final ConfigHandler manager=new ConfigHandler(new File(".\\JConfig.config"),true);

    public static void AddAdapters(JConfigConverter<?>... Adapters){
        manager.addAdapters(Adapters);
    }
    public static void changeCacheRefresh(int newRefresh){
        manager.MAX_CACHE_ITERATIONS = newRefresh;
    }
    public static void RemoveAdapter(Class<?> DataType){
        manager.removeAdapter(DataType);
    }
    public static void RemoveAllAdapters(){
        manager.removeAllAdapters();
    }
    public static void InvalidateCache(){
        manager.invalidateCache();
    }
    public static void BeginTransaction(){
        manager.BeginTransaction();
    }
    public static void Commit(){
        manager.Commit();
    }
    public static void Rollback(){
        manager.Rollback();
    }
    public static void Set(String key,Object value){
        manager.set(key,value);
    }
    public static <T> Optional<T> SafeGet(String key, Class<T> dataType){
        return manager.get(key,dataType);
    }
    public static <T> T Get(String key, Class<T> dataType){
        return manager.get(key,dataType).get();
    }
    public static TripleMap<String,Class<?>,Object> KeysStartsWith(String key){
        return manager.findSimilar(key);
    }
    public static List<String> getSubPaths(String rootPath){
        return manager.getPaths(rootPath);
    }
    public static void SerializedSet(String path,Object data) throws JsonProcessingException {
        manager.SerializedSet(path,data);
    }
    public static<T> Optional<T> SerializedGet(String path,Class<T> dataType) throws JsonProcessingException {
        return manager.SerializedGet(path,dataType);
    }
}

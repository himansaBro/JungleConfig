package core.dataTypes;

public interface JConfigConverter <T>{
    public Class<T> getType();
    public String Encode(Object data);
    public Object Decode(String data);
}

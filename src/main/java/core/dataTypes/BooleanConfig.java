package core.dataTypes;

public class BooleanConfig implements JConfigConverter<Boolean> {

    @Override
    public Class<Boolean> getType() {
        return Boolean.class;
    }

    @Override
    public String Encode(Object data) {
        if (!(data instanceof Boolean)) throw new IllegalArgumentException("Invalid Boolean:"+data.toString());
        return ((Boolean) data)?"true":"false";
    }

    @Override
    public Boolean Decode(String data) {
        return data.equalsIgnoreCase("true");
    }
}

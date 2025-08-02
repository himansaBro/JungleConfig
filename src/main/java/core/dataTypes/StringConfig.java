package core.dataTypes;

public class StringConfig implements JConfigConverter<String> {
    @Override
    public Class<String> getType() {
        return String.class;
    }

    @Override
    public String Encode(Object data) {
        return data.toString();
    }

    @Override
    public String Decode(String data) {
        return data;
    }
}

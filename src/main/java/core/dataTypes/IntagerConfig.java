package core.dataTypes;

public class IntagerConfig implements JConfigConverter<Integer>{
    @Override
    public Class<Integer> getType() {
        return Integer.class;
    }

    @Override
    public String Encode(Object data) {
        if (!(data instanceof Integer)) throw new IllegalArgumentException("Invalid Integer :"+data.toString());
        return Integer.toString((Integer) data);
    }

    @Override
    public Integer Decode(String data) {
        return Integer.parseInt(data);
    }
}

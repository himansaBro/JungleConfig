package Exceptions;

public class ConverterNotFoundException extends RuntimeException{
    private String massage = "Converters Failed to parse Value. Data Type:";
    private Class<?> type;

    public ConverterNotFoundException(Class<?> type) {
        this.type = type;
    }

    @Override
    public String getMessage() {
        return massage+type.getName();
    }
}

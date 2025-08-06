package com.codehack.JungleConfig.Core;

public interface TypeConverterAdapter {
    String getType();
    String ConvertToSave(Object object);
    Object CastToUse(String data);
}

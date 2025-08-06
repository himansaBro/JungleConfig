package com.codehack.JungleConfig.Core.Adapters;

import com.codehack.JungleConfig.Core.TypeConverterAdapter;

public class NativeBooleanAdapter implements TypeConverterAdapter {
    @Override
    public String getType() {
        return Boolean.class.getSimpleName();
    }

    @Override
    public String ConvertToSave(Object object) {
        return (object.toString().equals("true"))?"true":"false";
    }

    @Override
    public Object CastToUse(String data) {
        return (data.equals("true"));
    }
}

package com.codehack.JungleConfig.Core.Adapters;

import com.codehack.JungleConfig.Core.TypeConverterAdapter;

public class NativeStringAdapter implements TypeConverterAdapter {
    @Override
    public String getType() {
        return String.class.getSimpleName();
    }

    @Override
    public String ConvertToSave(Object object) {
        return object.toString();
    }

    @Override
    public Object CastToUse(String data) {
        return data;
    }
}

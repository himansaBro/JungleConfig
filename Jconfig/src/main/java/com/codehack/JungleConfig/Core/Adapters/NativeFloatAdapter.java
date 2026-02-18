package com.codehack.JungleConfig.Core.Adapters;

import com.codehack.JungleConfig.Core.TypeConverterAdapter;

public class NativeFloatAdapter implements TypeConverterAdapter {
    @Override
    public String getType() {
        return Float.class.getSimpleName();
    }

    @Override
    public String ConvertToSave(Object object) {
        return object.toString();
    }

    @Override
    public Object CastToUse(String data) {
        return Float.valueOf(data);
    }
}

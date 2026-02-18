package com.codehack.JungleConfig.Core.Adapters;

import com.codehack.JungleConfig.Core.TypeConverterAdapter;

public class NativeDoubleAdapter implements TypeConverterAdapter {
    @Override
    public String getType() {
        return Double.class.getSimpleName();
    }

    @Override
    public String ConvertToSave(Object object) {
        return object.toString();
    }

    @Override
    public Object CastToUse(String data) {
        return Double.valueOf(data);
    }
}

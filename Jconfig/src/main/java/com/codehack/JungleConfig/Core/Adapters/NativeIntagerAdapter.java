package com.codehack.JungleConfig.Core.Adapters;

import com.codehack.JungleConfig.Core.TypeConverterAdapter;

public class NativeIntagerAdapter implements TypeConverterAdapter {
    @Override
    public String getType() {
        return Integer.class.getSimpleName();
    }

    @Override
    public String ConvertToSave(Object object) {
        return Integer.toString((Integer)object);
    }

    @Override
    public Object CastToUse(String data) {
        return Integer.valueOf(data);
    }
}

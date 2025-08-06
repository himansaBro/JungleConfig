package com.codehack.JungleConfig.Core.Implim;

import com.codehack.JungleConfig.Core.ConverterInterface;
import com.codehack.JungleConfig.Core.IOHandlerInterface;
import com.codehack.JungleConfig.DataModel.TypeMap;

public class NativeEncryptedConverter extends NativeConverter implements ConverterInterface {

    private final String pass;
    public NativeEncryptedConverter(IOHandlerInterface ioHander,String pass) {
        super(ioHander);
        this.pass = pass;
    }

    @Override
    protected String preProcess(String data) {
        System.out.println("Decrypting Data");
        return super.preProcess(data);
    }

    @Override
    protected String preWrite(String data) {
        System.out.println("Encrypting Data");
        return super.preWrite(data);
    }
}

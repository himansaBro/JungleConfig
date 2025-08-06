package com.codehack.JungleConfig.Core;

import com.codehack.JungleConfig.DataModel.TypeMap;

import java.io.File;

/*
Responsibility, Do Low level Encoding that Related To file Store and KeyConflicts.
---String--->[split to KeyMap,URLDecode,Decryption]---KeyMap<String,String,String>--->
 */
public interface ConverterInterface {
    TypeMap<String,String,String> decode();
    void encode(TypeMap<String,String,String> data);

    boolean Backup(File path, boolean override);
}

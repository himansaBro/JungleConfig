package com.codehack.JungleConfig.Core;

import com.codehack.JungleConfig.DataModel.TypeMap;

import java.io.File;
import java.util.List;

public interface CacheInterface {
    void BeginTransaction();
    void EndTransaction();
    void rollback();
    void Commit();

    TypeMap.Entry<String,String,String> Get(String key);
    void Set(TypeMap.Entry<String,String,String> data);
    boolean Remove(String key);
    void InvalidateCache();

    TypeMap<String,String,String> query(boolean iKey,String keyReg,boolean iType,String typeReg,boolean iVal,String valReg);

    boolean Backup(File path, boolean override);

    void RemoveAll();

    boolean Exists(String key);

    List<String> getAllKeys();

    List<String> getAllKeys(String regx);

    String getTypeSimpleName(String key);
}

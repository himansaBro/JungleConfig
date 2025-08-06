/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.codehack.JungleConfig.Core;

import com.codehack.JungleConfig.DataModel.TypeMap;
import java.io.File;
import java.util.List;

/**
 *
 * @author Himansa
 */
public interface InternalCacheInterface {
    void Set(TypeMap<String,String,String> saveQuery,List<String> remList);
    TypeMap<String,String,String> Get(TypeMap<String,String,String> saveQuery,List<String> remList);
    void Invalidate();
    boolean Backup(File file,boolean override);
}

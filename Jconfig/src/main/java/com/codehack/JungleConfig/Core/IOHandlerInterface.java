package com.codehack.JungleConfig.Core;

import java.io.File;

public interface IOHandlerInterface {
    String Read();
    void Write(String data);
    boolean Backup(File path, boolean override);
}

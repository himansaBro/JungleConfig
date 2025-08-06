package com.codehack.JungleConfig.Utils;

public class JLogger {
    public static void LogMsg(String message){
        System.out.println("[Logger Message] "+message);
    }
    public static void LogWarn(String message){
        System.out.println("[Logger Warning] "+message);
    }
    public static void LogError(String message){
        System.err.println("[Logger Error  ] "+message);
    }

}

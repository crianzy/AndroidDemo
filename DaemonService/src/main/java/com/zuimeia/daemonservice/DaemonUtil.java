package com.zuimeia.daemonservice;

public class DaemonUtil {

    static {
        System.loadLibrary("dm");
    }

    public static boolean RunCommand(String command, String packageName) {
        try {
            Runtime.getRuntime().exec(command);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

package com.xrlj.utils;

public final class OSsysUtils {

    /**
     * 获取操作系统类型。
     * @return
     */
    public static OsType getOsSysType() {
        if(System.getProperty("os.name").indexOf("Windows") == -1) {
           return OsType.LINUX;
        }else {
            return OsType.WIN;
        }
    }

    /**
     * 系统类型.
     */
    public enum  OsType {
        LINUX,
        WIN
    }
}

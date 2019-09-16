package com.xrlj.utils;

import org.apache.commons.io.FileUtils;

import java.io.InputStream;

public final class FileUtil extends FileUtils {

    /**
     * spring-boot打成jar包后，要这样调用resource下面资源。
     * @param fileName
     * @return
     */
    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }
}

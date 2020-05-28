package com.xrlj.utils;

import org.apache.commons.io.FileUtils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public final class FileUtil extends FileUtils {

    /**
     * spring-boot打成jar包后，要这样调用resource下面资源。
     * @param fileName
     * @return
     */
    public static InputStream getResourcesFileInputStream(String fileName) {
        return Thread.currentThread().getContextClassLoader().getResourceAsStream("" + fileName);
    }

    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        String s = "http://jnyl-20200522.oss-cn-shenzhen.aliyuncs.com/MyObjectKey/abc.txt?Expires=1905911503&OSSAccessKeyId=LTAI4GC4smf9i14i5H1d5DUV&Signature=jK0mcbY5Yynk1wLF%2BgDSW%2FBK7PI%3D";

        URL url = new URL(s);
        System.out.println(url.getHost());
        System.out.println(url.getPath().substring(1));
        String[] fs = url.getPath().substring(1).split("/");
        System.out.println(fs[fs.length -1]);
        System.out.println(fs[0]);
    }
}

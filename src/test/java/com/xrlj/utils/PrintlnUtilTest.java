package com.xrlj.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public final class PrintlnUtilTest {

    @Test
    public void printlnJsonFormatTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aa", "aaaaa");
        jsonObject.put("bb", 333);
        jsonObject.put("dd", "ddddd");

        PrintUtil.printlnJsonFormat(jsonObject);
    }

}

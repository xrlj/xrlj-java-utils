package com.xrlj.utils;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

public final class FormatUtilTest {

    @Test
    public void printJSONTest() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("aa", "aaaaa");
        jsonObject.put("bb", 333);
        jsonObject.put("dd", "ddddd");

        FormatUtil.printJSON(jsonObject.toJSONString());
    }

}

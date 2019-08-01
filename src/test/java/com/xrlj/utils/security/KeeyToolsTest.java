package com.xrlj.utils.security;

import com.xrlj.utils.PrintUtil;
import com.xrlj.utils.security.KeyTools;
import org.junit.Test;

public class KeeyToolsTest {

    @Test
    public void generateSaltTest() {
        PrintUtil.println(KeyTools.generateSalt(16)); //32位
    }
}

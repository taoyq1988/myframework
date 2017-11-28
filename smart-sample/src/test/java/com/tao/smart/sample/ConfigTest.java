package com.tao.smart.sample;

import com.tao.smart.framework.helper.ConfigHelper;
import org.junit.Test;

/**
 * @author tyq
 * @version 1.0, 2017/11/28
 */
public class ConfigTest {

    @Test
    public void configRead() {
        String packageName = ConfigHelper.getAppBasePackage();
        System.out.println(packageName);
    }
}

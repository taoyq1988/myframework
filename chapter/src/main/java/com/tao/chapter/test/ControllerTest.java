package com.tao.chapter.test;

import com.tao.smart.framework.annotation.Action;
import com.tao.smart.framework.annotation.Controller;
import com.tao.smart.framework.bean.Data;

/**
 * @author tyq
 * @version 1.0, 2017/11/28
 */
@Controller
public class ControllerTest {

    @Action("get:test")
    public Data test() {
        String ss = "test";
        Data data = new Data(ss);
        return data;
    }
}

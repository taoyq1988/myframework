package com.tao.smart.sample;

import com.tao.smart.framework.annotation.Action;
import com.tao.smart.framework.annotation.Controller;
import com.tao.smart.framework.bean.Data;
import com.tao.smart.framework.bean.Param;

/**
 * @author tyq
 * @version 1.0, 2017/11/28
 */
@Controller
public class test {

    @Action("get:/test")
    public Data testData(Param param) {
        String s = "------------------success------------------------";
        System.out.println(s);
        Data data = new Data(s);
        return data;
    }
}

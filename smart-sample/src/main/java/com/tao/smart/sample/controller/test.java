package com.tao.smart.sample.controller;

import com.tao.smart.framework.annotation.Action;
import com.tao.smart.framework.annotation.Controller;
import com.tao.smart.framework.annotation.Inject;
import com.tao.smart.framework.bean.Data;
import com.tao.smart.framework.bean.Param;
import com.tao.smart.sample.service.TestService;
import com.tao.smart.sample.service.impl.TestServiceImpl;

/**
 * @author tyq
 * @version 1.0, 2017/11/28
 */
@Controller
public class test {

    @Inject
    private TestServiceImpl testService;

    @Action("get:/test")
    public Data testData(Param param) {
        String s = "------------------success------------------------";
        System.out.println(s);
        System.out.println(testService);
        testService.hello("tom");
        String str = (String) param.getParamMap().get("name");
        System.out.println(str);
        Data data = new Data(str);
        return data;
    }
}

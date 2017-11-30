package com.tao.smart.sample.service.impl;

import com.tao.smart.framework.annotation.Service;
import com.tao.smart.framework.annotation.Transaction;
import com.tao.smart.sample.service.TestService;

/**
 * @author tyq
 * @version 1.0, 2017/11/30
 */
@Service
public class TestServiceImpl implements TestService {

    @Override
    @Transaction
    public void hello(String name) {
        System.out.println("hello " + name);
    }
}

package com.tao.smart.sample.service.impl;

import com.tao.smart.framework.annotation.Service;
import com.tao.smart.framework.annotation.Transaction;
import com.tao.smart.sample.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tyq
 * @version 1.0, 2017/11/30
 */
@Service
public class TestServiceImpl implements TestService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestServiceImpl.class);

    @Override
    @Transaction
    public void hello(String name) {
        LOGGER.info("log info test");
        LOGGER.debug("log debug test");
        System.out.println("hello " + name);
    }
}

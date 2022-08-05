package com.thrift.demo.service;

import com.thrift.demo.ThriftHelloService;
import org.apache.thrift.TException;

/**
 * @author fit2cloudzhao
 * @date 2022/8/1 18:03
 * @description:
 */
public class ThriftHelloServiceImpl implements ThriftHelloService.Iface {
    @Override
    public String sayHello(String username) throws TException {
        return "Hello Thrift :" + username;
    }
}

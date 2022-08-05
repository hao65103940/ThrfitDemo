package com.thrift.demo.TestMain;

import com.thrift.demo.ThriftHelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.layered.TFramedTransport;

/**
 * 客户端
 *
 * @author fit2cloudzhao
 * @date 2022/8/1 18:06
 * @description:
 */
public class ClientMain {

    public static void main(String[] args) {

        TTransport transport = null;
        try {
            transport = new TFramedTransport(new TSocket("127.0.0.1", 9099, 6000));
            TProtocol protocol = new TBinaryProtocol(transport);
            ThriftHelloService.Client client = new ThriftHelloService.Client(protocol);
            transport.open();
            String result = client.sayHello("thrift-1");
            System.out.println(result);
        } catch (TException e) {
            e.printStackTrace();
        } finally {
            if (null != transport) {
                transport.close();
            }
        }
    }
}

package com.thrift.demo.client;

import com.thrift.demo.ThriftHelloService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.apache.thrift.transport.layered.TFramedTransport;

/**
 * @author fit2cloudzhao
 * @date 2022/8/2 11:20
 * @description:
 */
public class ThriftClient {

    ThriftHelloService.Client client = null;

    private TTransport tTransport = null;


    public ThriftClient(String ip, int port, int timeout) {
        try {
            //
            tTransport = new TFramedTransport(new TSocket(ip, port, timeout));
            TProtocol tProtocol = new TBinaryProtocol(tTransport);
            client = new ThriftHelloService.Client(tProtocol);
            tTransport.open();
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public String getResponse(String str) {
        try {
            return client.sayHello(str);
        } catch (TException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void close() {
        if (tTransport != null && tTransport.isOpen()) {
            tTransport.close();
        }
    }


}

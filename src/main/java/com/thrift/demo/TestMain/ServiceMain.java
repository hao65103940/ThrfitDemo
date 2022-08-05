package com.thrift.demo.TestMain;

import com.thrift.demo.ThriftHelloService;
import com.thrift.demo.service.ThriftHelloServiceImpl;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.transport.layered.TFramedTransport;

/**
 * 服务端
 *
 * @author fit2cloudzhao
 * @date 2022/8/1 18:05
 * @description:
 */
public class ServiceMain {


    public static void main(String[] args) throws Exception {
        try {
            // 实现处理接口impl
            ThriftHelloServiceImpl thriftHelloService = new ThriftHelloServiceImpl();
            // 创建TProcessor
            TProcessor processor = new ThriftHelloService.Processor<>(thriftHelloService);
            // 创建TServerTransport,非阻塞式I/O,服务端和客户端需要TFramedTransport 数据传输方式
            TNonblockingServerSocket tNonblockingServerSocket = new TNonblockingServerSocket(9099);
            // 创建TProtocol
            TThreadedSelectorServer.Args args1 = new TThreadedSelectorServer.Args(tNonblockingServerSocket);
            args1.transportFactory(new TFramedTransport.Factory());
            // 二进制格式反序列化
            args1.protocolFactory(new TBinaryProtocol.Factory());
            args1.processor(processor);
            args1.selectorThreads(16);
            args1.workerThreads(32);
            System.out.println("computer service server on port:" + 9099);
            TThreadedSelectorServer tThreadedSelectorServer = new TThreadedSelectorServer(args1);
            System.out.println("启动 Thrift 服务端");
            tThreadedSelectorServer.serve();
        } catch (Exception e) {
            System.out.println("启动 Thrift 服务端失败" + e.getMessage());
        }
    }
}

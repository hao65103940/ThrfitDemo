package com.thrift.demo.jmeterSampler;

import com.thrift.demo.client.ThriftClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.jmeter.config.Arguments;
import org.apache.jmeter.protocol.java.sampler.AbstractJavaSamplerClient;
import org.apache.jmeter.protocol.java.sampler.JavaSamplerContext;
import org.apache.jmeter.samplers.SampleResult;

/**
 * @author fit2cloudzhao
 * @date 2022/8/2 11:30
 * @description:
 */
public class TestThriftByJmeter extends AbstractJavaSamplerClient {

    private ThriftClient thriftClient;

    /**
     * 方法为性能测试时的线程运行体；
     *
     * @param javaSamplerContext
     * @return
     */
    @Override
    public SampleResult runTest(JavaSamplerContext javaSamplerContext) {

        SampleResult sampleResult = new SampleResult();
        // 开始统计响应时间标记
        sampleResult.sampleStart();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String response = thriftClient.getResponse("哈哈我是性能调试");
            stopWatch.stop();
            System.out.println(response + "总计花费:" + stopWatch.getTime());
            if (StringUtils.isNotBlank(response)) {
                sampleResult.setSuccessful(true);
                sampleResult.setResponseMessage(response);
            } else {
                sampleResult.setSuccessful(false);
                sampleResult.setResponseMessage("请求失败....");
            }
        } catch (Exception e) {
            sampleResult.setSuccessful(false);
            sampleResult.setResponseMessage("请求失败....");
        } finally {
            // 结束统计响应时间标记
            sampleResult.sampleEnd();
        }
        return sampleResult;
    }


    /**
     * 方法为初始化方法，用于初始化性能测试时的每个线程；
     *
     * @param context
     */
    @Override
    public void setupTest(JavaSamplerContext context) {
        String ip = context.getParameter("ip");
        String port = context.getParameter("port");
        String timeout = context.getParameter("timeout");
        // 初始化客户端
        thriftClient = new ThriftClient(ip, Integer.valueOf(port), Integer.valueOf(timeout));
        super.setupTest(context);
    }


    /**
     * 方法为测试结束方法，用于结束性能测试中的每个线程。
     *
     * @param context
     */
    @Override
    public void teardownTest(JavaSamplerContext context) {
        if (thriftClient != null) {
            thriftClient.close();
        }
        super.teardownTest(context);
    }

    /**
     * 方法主要用于设置传入界面的参数
     *
     * @return
     */
    @Override
    public Arguments getDefaultParameters() {
        Arguments jMeterProperties = new Arguments();
        jMeterProperties.addArgument("ip", "127.0.0.1");
        jMeterProperties.addArgument("port", "9099");
        jMeterProperties.addArgument("timeout", "6000");
        return jMeterProperties;
    }

}

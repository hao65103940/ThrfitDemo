package com.thrift.demo.jmeterSampler;

import com.thrift.demo.client.ThriftClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.jmeter.samplers.AbstractSampler;
import org.apache.jmeter.samplers.Entry;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jmeter.testelement.TestStateListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author fit2cloudzhao
 * @date 2022/8/2 21:45
 * @description:
 */
public class ThriftSampler extends AbstractSampler implements TestStateListener {

    Logger log = LoggerFactory.getLogger(ThriftSampler.class);
    private ThriftClient thriftClient;

    private static final String SERVER_IP = "server_ip";
    private static final String PORT = "port";

    private static final String PARAM = "request_param";


    public ThriftSampler() {
        setName("Thrift sampler");
    }

    @Override
    public SampleResult sample(Entry entry) {

        SampleResult sampleResult = new SampleResult();
        // 开始统计响应时间标记
        sampleResult.sampleStart();
        try {
            StopWatch stopWatch = new StopWatch();
            stopWatch.start();
            String param = getParam();
            String response = "";
            System.out.println("入参：" + param);
            log.info("入参：" + param);
            thriftClient = getThriftClient();
            if (StringUtils.isNotBlank(param)) {
                response = thriftClient.getResponse(param);
            } else {
                response = thriftClient.getResponse("我是空的");
            }
            System.out.println("response==>" + response);
            log.info("response==>" + response);
            stopWatch.stop();
            System.out.println(response + "总计花费:" + stopWatch.getTime());
            log.info(response + "总计花费:" + stopWatch.getTime());
            if (StringUtils.isNotBlank(response)) {
                sampleResult.setSuccessful(true);
                sampleResult.setResponseMessage(response);
                sampleResult.setResponseData(("请求成功:"+response).getBytes());
                sampleResult.setResponseCode("200");
            } else {
                sampleResult.setSuccessful(false);
                sampleResult.setResponseMessage("请求失败....请求参数：" + param);
                sampleResult.setResponseCode("500");
                sampleResult.setResponseData("请求失败".getBytes());
            }
        } catch (Exception e) {
            sampleResult.setSuccessful(false);
            sampleResult.setResponseMessage("请求失败...." + e.getMessage());
            sampleResult.setResponseCode("500");
            sampleResult.setResponseData(("请求失败...." + e.getMessage()).getBytes());
        } finally {
            // 结束统计响应时间标记
            sampleResult.sampleEnd();
        }
        return sampleResult;
    }


    public ThriftClient getThriftClient() {
        if (thriftClient == null) {
            thriftClient = new ThriftClient(getServerIp(), getPort(), 10000);
        }
        return this.thriftClient;
    }

    public String getServerIp() {
        return getPropertyAsString(SERVER_IP);
    }

    public Integer getPort() {
        return getPropertyAsInt(PORT);
    }


    public void setServerIp(String serverIp) {
        setProperty(SERVER_IP, serverIp);
    }

    public void setPort(Integer port) {
        setProperty(PORT, port);
    }

    public void setParam(String param) {
        setProperty(PARAM, param);
    }


    public String getParam() {
        return getPropertyAsString(PARAM);
    }

    @Override
    public void testStarted() {

    }

    @Override
    public void testStarted(String s) {

    }

    @Override
    public void testEnded() {
        this.testEnded("local");
    }

    @Override
    public void testEnded(String s) {

    }
}

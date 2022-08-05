package com.thrift.demo.gui;

import com.thrift.demo.jmeterSampler.ThriftSampler;
import org.apache.jmeter.gui.util.VerticalPanel;
import org.apache.jmeter.samplers.gui.AbstractSamplerGui;
import org.apache.jmeter.testelement.TestElement;
import org.apache.jorphan.gui.JLabeledTextField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;


/**
 * @author fit2cloudzhao
 * @date 2022/8/2 18:58
 * @description:
 */
public class ThriftSamplerUI extends AbstractSamplerGui {


    Logger log = LoggerFactory.getLogger(ThriftSamplerUI.class);
    private final JLabeledTextField serverIp = new JLabeledTextField("ServerIp");
    private final JLabeledTextField port = new JLabeledTextField("Port");
    private final JLabeledTextField param = new JLabeledTextField("Param");
    private void init() {
        log.info("Initializing the UI.");
        setLayout(new BorderLayout());
        setBorder(makeBorder());

        add(makeTitlePanel(), BorderLayout.NORTH);
        JPanel mainPanel = new VerticalPanel();
        add(mainPanel, BorderLayout.CENTER);

        JPanel DPanel = new JPanel();
        DPanel.setLayout(new GridLayout(3, 2));
        DPanel.add(serverIp);
        DPanel.add(port);
        DPanel.add(param);

        JPanel ControlPanel = new VerticalPanel();
        ControlPanel.add(DPanel);
        ControlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.gray), "Parameters"));
        mainPanel.add(ControlPanel);
    }

    public ThriftSamplerUI() {
        super();
        this.init();
    }

    @Override
    public String getStaticLabel() {
        return "Thrift Sampler";
    }
    @Override
    public String getLabelResource() {
        throw new IllegalStateException("This shouldn't be called");
    }

    /**
     * 该方法创建一个新的Sampler，然后将界面中的数据设置到这个新的Sampler实例中。
     * @return
     */
    @Override
    public TestElement createTestElement() {
        ThriftSampler sampler = new ThriftSampler();
        this.setupSamplerProperties(sampler);
        return sampler;
    }


    private void setupSamplerProperties(ThriftSampler sampler) {
        this.configureTestElement(sampler);
        sampler.setServerIp(serverIp.getText());
        sampler.setPort(Integer.valueOf(port.getText()));
        sampler.setParam(param.getText());
    }


    /**
     * 这个方法用于把界面的数据移到Sampler中。
     * @param testElement
     */
    @Override
    public void modifyTestElement(TestElement testElement) {
        ThriftSampler sampler = (ThriftSampler) testElement;
        this.setupSamplerProperties(sampler);
    }


    /**
     * 界面与Sampler之间的数据交换
     * @param element
     */
    @Override
    public void configure(TestElement element) {
        super.configure(element);
        ThriftSampler sampler = (ThriftSampler) element;
        this.serverIp.setText(sampler.getServerIp());
        this.port.setText(sampler.getPort().toString());
        this.param.setText(sampler.getParam());
    }


    /**
     * 该方法会在reset新界面的时候调用，这里可以填入界面控件中需要显示的一些缺省的值(就是默认显示值)
     */
    @Override
    public void clearGui() {
        super.clearGui();
        this.serverIp.setText("服务端ip");
        this.port.setText("9099");
        this.param.setText("参数");
    }
}

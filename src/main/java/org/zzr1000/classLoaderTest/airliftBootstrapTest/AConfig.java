package org.zzr1000.classLoaderTest.airliftBootstrapTest;

import io.airlift.configuration.Config;

//如果该类中，没有任何值，会有如下报错：
//Error: Configuration class [org.zzr1000.airliftTest.AConfig]
//does not have any @Config annotations
//设置NotNull之后，要保障有对应配置信息传入
public class AConfig {

    private String configA;
    private String configB;
    private String configC;
    private String configD;

    //@NotNull
    public String getConfigA() {
        return configA;
    }

    @Config("configA")
    //将形参中的configA替换为c做测试验证：
    //测试验证证明：注解的名称、形参的名称：可以不同：..
    public void setConfigA(String c) {
        this.configA = c;
    }

    //@NotNull
    public String getConfigB() {
        return configB;
    }

    @Config("configB")
    public void setConfigB(String configB) {
        this.configB = configB;
    }

    //@NotNull
    public String getConfigC() {
        return configC;
    }

    @Config("configC")
    public void setConfigC(String configC) {
        this.configC = configC;
    }

    //@NotNull
    public String getConfigD() {
        return configD;
    }

    @Config("configD")
    public void setConfigD(String configD) {
        this.configD = configD;
    }
}

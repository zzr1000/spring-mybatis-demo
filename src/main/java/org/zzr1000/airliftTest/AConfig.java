package org.zzr1000.airliftTest;

import io.airlift.configuration.Config;

import javax.validation.constraints.NotNull;

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
    public void setConfigA(String configA) {
        this.configA = configA;
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

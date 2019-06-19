package org.zzr1000.airliftTest;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import io.airlift.bootstrap.Bootstrap;
import io.airlift.configuration.ConfigBinder;
import io.airlift.json.JsonModule;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;


// 总结使用方法：
// 1、使用Module数组初始化Bootstrap：(Module数组中的Module，可以将一些想要Class bind进来)
// 2、通过实例化的Bootstrap：设置一些属性：
// 例如：严格参数(配置文件中有，但代码中没有对应接收参数的情况)、初始化日志信息、设置参数信息等
// 来初始化得到 Injector
// 3、通过拿到的Inject就可以直接使用初始化Bootstrap时使用的Module数组中Module bind的Class实例
// 4、总结下来：io.airlift.bootstrap.Bootstrap的作用就是这个：. .
// 5、这样的好处是什么：批量注入：将一个类中，需要使用到的其他类，统一注册、处理
// 不需要对需要使用到的其他类：单独分批new操作：应该就是这个好处 ..
public class AirliftTest {

    public static void main(String[] args) throws Exception {

        Map<String,String> requiredConfig = new HashMap<>();
        requiredConfig.put("configA","a");
        requiredConfig.put("configB","b");
        requiredConfig.put("configC","c");
        requiredConfig.put("configD","d");

        Bootstrap e = new Bootstrap(
                new Module[]{new JsonModule(),new AModule()});

        Injector injector = e.strictConfig()
                .doNotInitializeLogging()
                .setRequiredConfigurationProperties(requiredConfig)
                .initialize();

        AClass aClass = injector.getInstance(AClass.class);
        aClass.printInfo();
        injector.getInstance(BClass.class).printInfo();
        injector.getInstance(CClass.class).printInfo();
    }
}


@Data
class AClass{
    private String name;

    public void printInfo(){
        System.out.println("AClass : Hi Inject!!");
    }
}

@Data
class BClass{
    private String name;

    public void printInfo(){
        System.out.println("BClass : Hi Inject!!");
    }
}

@Data
class CClass{
    private String name;

    public void printInfo(){
        System.out.println("CClass : Hi Inject!!");
    }
}

class AModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(AClass.class).in(Scopes.SINGLETON);
        binder.bind(BClass.class).in(Scopes.SINGLETON);
        binder.bind(CClass.class).in(Scopes.SINGLETON);
        ConfigBinder.configBinder(binder).bindConfig(AConfig.class);
    }
}
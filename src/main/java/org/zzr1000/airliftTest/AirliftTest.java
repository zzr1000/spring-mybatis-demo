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

public class AirliftTest {

    public static void main(String[] args) throws Exception {

        Map<String,String> requiredConfig = new HashMap<>();

        Bootstrap e = new Bootstrap(
                new Module[]{new JsonModule(),new AModule()});

        Injector injector = e.strictConfig()
                .doNotInitializeLogging()
                .setRequiredConfigurationProperties(requiredConfig)
                .initialize();

        AClass aClass = injector.getInstance(AClass.class);

        System.out.println(aClass.getName());

    }
}


@Data
class AClass{
    private String name;
}

class AModule implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(AClass.class).in(Scopes.SINGLETON);
        ConfigBinder.configBinder(binder).bindConfig(AConfig.class);
    }
}
package org.zzr1000.springbootTest.componentScanTest;


/*
Refer to :
https://blog.51cto.com/4247649/2118342

一句话总结：
@ComponentScan主要就是定义扫描的路径从中找出标识了需要装配的类自动装配到spring的bean容器中

@Controller，@Service，@Repository注解，
查看其源码你会发现，他们中有一个共同的注解@Component，
@ComponentScan注解默认就会装配标识了@Controller，@Service，@Repository，@Component注解的类到spring容器中

这种包扫描的方式比以通过@Bean注解的方式  方便很多


总结：
@ComponentScan的常用方式如下

自定义扫描路径下边带有@Controller，@Service，@Repository，@Component注解加入spring容器
通过includeFilters加入扫描路径下没有以上注解的类加入spring容器
通过excludeFilters过滤出不用加入spring容器的类
自定义增加了@Component注解的注解方式

 */


import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan(value="org.zzr1000.springbootTest.componentScanTest")
@Configuration
public class ComponentScanTest {

    /*
    会打印如下信息：
    org.springframework.context.annotation.internalConfigurationAnnotationProcessor
    org.springframework.context.annotation.internalAutowiredAnnotationProcessor
    org.springframework.context.annotation.internalRequiredAnnotationProcessor
    org.springframework.context.annotation.internalCommonAnnotationProcessor
    org.springframework.context.event.internalEventListenerProcessor
    org.springframework.context.event.internalEventListenerFactory
    componentScanTest
    userController
    userDao
    userService
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext
                = new AnnotationConfigApplicationContext(ComponentScanTest.class);
        String[] definitionNames = applicationContext.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

}

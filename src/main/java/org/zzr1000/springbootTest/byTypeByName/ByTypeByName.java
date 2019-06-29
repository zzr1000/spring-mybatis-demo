package org.zzr1000.springbootTest.byTypeByName;


/*

<bean id="userServiceImpl"
    class="cn.com.bochy.service.impl.UserServiceImpl"
    autowire="byName">
</bean>

<bean id="userDao"
    class="cn.com.bochy.dao.impl.UserDaoImpl">
</bean>

byName就是通过Bean的id或者name，byType就是按Bean的Class的类型。

若autowire="byType"意思是通过 class="cn.com.bochy.dao.impl.UserDaoImpl"来查找UserDaoImpl下所有的对象。
代码autowire="byName"意思是通过id="userDao"来查找Bean中的userDao对象

 */

/*
refer to:https://www.imooc.com/qadetail/220078?t=344574
byName：通过属性名称自动装配。
spring会检查set方法，比如你有一个setMaster(),这个方法提供注入，
那相应的Bean name或者id 为master的Bean 就会被注入到协作者（就是类里面你所定义的引用型成员）中
如：
pubic class Test{
    private Master master；
    public void setMaster（Master master）{
        this.master = master;
    }
}
<bean id="master" class="">

byType：如果容器中存在一个与指定属性类型相同的bean，那么将与该属性自动装配。
就是查找构造器或者方法中的参数，参数的类型匹配了就注入。

如：
pubic class Test{
    private Master master；
    public Test(Master master){
    }
    public void setMaster（Master master）{
        this.master = master;
    }
}

<bean id="master" class="xxx.xxx.Master">
*/

/*
spring 中：
自动装配有两种方式：
1.byName
在spring配置文件的<beans>标签中加入代码：dafault-autowire="byName"。
被装配类中的setter方法还是需要的，因为是byName，所以setter方法名要与bean的id对应，比如bean的id为cd，那么setter方法名为setCd。

2.byType
在spring配置文件的<beans>标签中加入代码：dafault-autowire="byType"。
被装配类中的setter方法的参数类型要与bean的class的类型一样，才能自动装配到。

注意：以上两种自动装配方法本质上都是通过反射，构造出对应的setter方法，然后执行setter方法。
 */

public class ByTypeByName {


}

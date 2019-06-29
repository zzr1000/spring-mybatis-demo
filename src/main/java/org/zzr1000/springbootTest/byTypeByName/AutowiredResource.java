package org.zzr1000.springbootTest.byTypeByName;


//refer to :
//https://blog.csdn.net/weixin_40423597/article/details/80643990
public class AutowiredResource {


    /**
    @Resource的作用相当于@Autowired，
    只不过@Autowired按byType自动注入，
    而@Resource默认按 byName自动注入罢了。
    @Resource有两个属性是比较重要的，分是name和type，
    Spring将@Resource注解的name属性解析为bean的名字，而type属性则解析为bean的类型。
    所以如果使用name属性，则使用byName的自动注入策略，而使用type属性时则使用byType自动注入策略。
    如果既不指定name也不指定type属性，这时将通过反射机制使用byName自动注入策略。

　　@Resource装配顺序
　　1. 如果同时指定了name和type，则从Spring上下文中找到唯一匹配的bean进行装配，找不到则抛出异常
　　2. 如果指定了name，则从上下文中查找名称（id）匹配的bean进行装配，找不到则抛出异常
　　3. 如果指定了type，则从上下文中找到类型匹配的唯一bean进行装配，找不到或者找到多个，都会抛出异常
　　4. 如果既没有指定name，又没有指定type，则自动按照byName方式进行装配；如果没有匹配，则回退为一个原始类型进行匹配，如果匹配则自动装配；
     */

    ////////////////////

    /**@Autowired与@Resource的区别：
    1、 @Autowired与@Resource都可以用来装配bean. 都可以写在字段上,或写在setter方法上。
    2、 @Autowired默认按类型装配（这个注解是属业spring的），默认情况下必须要求依赖对象必须存在，
        如果要允许null值，可以设置它的required属性为false，如：@Autowired(required=false) ，
        如果我们想使用名称装配可以结合@Qualifier注解进行使用，如下：
            @Autowired()@Qualifier("baseDao")
            privateBaseDao baseDao;
    3、@Resource（这个注解属于J2EE的），默认按照名称进行装配，名称可以通过name属性进行指定，
            如果没有指定name属性，当注解写在字段上时，默认取字段名进行安装名称查找，
            如果注解写在setter方法上默认取属性名进行装配。
            当找不到与名称匹配的bean时才按照类型进行装配。
            但是需要注意的是，如果name属性一旦指定，就只会按照名称进行装配。

            @Resource(name="baseDao")
            privateBaseDao baseDao;

            推荐使用：@Resource注解在字段上，这样就不用写setter方法了，并且这个注解是属于J2EE的，减少了与spring的耦合。这样代码看起就比较优雅。

    4、@Autowired是根据类型进行自动装配的。
            如果当Spring上下文中存在不止一个UserDao类型的bean时，就会抛出BeanCreationException异常;
            如果Spring上下文中不存在UserDao类型的bean，也会抛出BeanCreationException异常。
            我们可以使用@Qualifier配合@Autowired来解决这些问题。如下：

            ①可能存在多个UserDao实例
                @Autowired
                @Qualifier("userServiceImpl")
                public IUserService userService;
                或者
                @Autowired
                public void setUserDao(@Qualifier("userDao") UserDao userDao) {
                this.userDao = userDao;
                }
                这样Spring会找到id为userServiceImpl和userDao的bean进行装配。
            ②可能不存在UserDao实例

                 @Autowired(required = false)
                 public IUserService userService

     5、个人总结：
             @Autowired//默认按type注入
             @Qualifier("cusInfoService")//一般作为@Autowired()的修饰用
             @Resource(name="cusInfoService")//默认按name注入，可以通过name和type属性进行选择性注入

             一般@Autowired和@Qualifier一起用，@Resource单独用。
             当然没有冲突的话@Autowired也可以单独用

     6、简单理解：
             @Autowired 根据类型注入，
             @Resource 默认根据名字注入，其次按照类型搜索
             @Autowired @Qualifie("userService") 两个结合起来可以根据名字和类型注入

     */




    /**
     复杂理解：

     比如你有这么一个Bean
     @Service(“UserService”)
     public Class UserServiceImpl implements UserService｛｝;

     现在你想在UserController 里面使用这个UserServiceImpl
     public Class UserController ｛
     @AutoWire   //当使用这个注入的时候上面的 UserServiceImpl 只需要这样写 @Service，
                    这样就会自动找到UserService这个类型以及他的子类型。
                    UserServiceImpl 实现了UserService，所以能够找到它。
                    不过这样有一个缺点，就是当UserService实现类有两个以上的时候，这个时候会找哪一个呢，这就造成了冲突，
                    所以要用@AutoWire注入的时候要确保UserService只有一个实现类。
     @Resource   //默认情况下是按照名称进行匹配，如果没有找到相同名称的Bean，则会按照类型进行匹配，
                    有人可能会想了，这下好了，用这个是万能的了，不用管名字了，也不用管类型了，
                    但这里还是有缺点。首先，根据这个注解的匹配效果可以看出，它进行了两次匹配，
                    也就是说，如果你在UserService这个类上面这样写注解：@Service,它会怎么找呢，
                    首先是找相同名字的，如果没有找到，再找相同类型的，而这里的@Service没有写名字，
                    这个时候就进行了两次搜索，显然，速度就下降了许多。也许你还会问，这里的@Service本来就没有名字，
                    肯定是直接进行类型搜索啊。其实不是这样的，UserServiceImpl 上面如果有@Service,默认的名字
                    是这个userServiceImpl，注意看，就是把类名前面的大写变成小写，就是默认的Bean的名字了。
                    @Resource根据名字搜索是这样写@Resource("userService")，
                    如果你写了这个名字叫userService，那么UserServiceImpl上面必须也是这个名字，不然还是会报错。
     @Autowired @Qualifie("userService") //是直接按照名字进行搜索，也就是说，
                     对于UserServiceImpl 上面@Service注解必须写名字，不写就会报错，
                     而且名字必须是@Autowired @Qualifie("userService") 保持一致。
                     如果@Service上面写了名字，而@Autowired @Qualifie() ，一样会报错。

     private UserService userService;

     ｝

     说了这么多，可能你有些说晕了，那么怎么用这三个呢，要实际的工作是根据实际情况来使用的，
     通常使用AutoWire和@Resource多一些，bean的名字不用写，
     而UserServiceImpl上面能会这样写 @Service("userService")。
     这里的实际工作情况，到底是什么情况呢？说白了就是整个项目设计时候考虑的情况，
     如果你的架构设计师考虑的比较精细，要求比较严格，要求项目上线后的访问速度比较好，通常是考虑速度了。
     这个时候@AutoWire没有@Resource好用，因为@Resource可以根据名字来搜索，
     是这样写的@Resource("userService")。这个@Autowired @Qualifie("userService") 也可以用名字啊，
     为什么不用呢，原因很简单，这个有点长，不喜欢，增加工作量。
     因为根据名字搜索是最快的，就好像查数据库一样，根据Id查找最快。因为这里的名字与数据库里面的ID是一样的作用。
     这个时候，就要求你多写几个名字，工作量自然就增加了。而如果你不用注解，
     用xml文件的时候，对于注入Bean的时候要求写一个Id，xml文件时候的id就相当于这里的名字。


     说了那么多没用，你能做的就是简单直接，什么最方便就用什么，

     你就直接用@Resource得了，如果你喜欢用@AutoWire也行，不用写名字。
     */


}

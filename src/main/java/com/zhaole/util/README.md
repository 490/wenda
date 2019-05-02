# InitializingBean
InitializingBean接口为bean提供了初始化方法的方式，它只包括afterPropertiesSet方法，凡是继承该接口的类，在初始化bean的时候都会执行该方法。

1、Spring为bean提供了两种初始化bean的方式，实现InitializingBean接口，实现afterPropertiesSet方法，或者在配置文件中通过init-method指定，两种方式可以同时使用。

2、实现InitializingBean接口是直接调用afterPropertiesSet方法，比通过反射调用init-method指定的方法效率要高一点，但是init-method方式消除了对spring的依赖。

3、如果调用afterPropertiesSet方法时出错，则不调用init-method指定的方法。


# md5

MD5(MD5(pass明文+固定salt)+随机salt) 
第一次固定salt写死在前端 
第二次加密采用随机的salt 并将每次生成的salt保存在数据库中

登录流程：

前端对用户输入的密码进行md5加密（固定的salt） 
将加密后的密码传递到后端 
后端使用用户id取出用户信息 
后端对加密后的密码在进行md5加密（取出盐），然后与数据库中存储的密码进行对比， 
ok登录成功，否则登录失败

注册流程:

前端对用户输入的密码进行md5加密（固定的salt） 
将加密后的密码传递到后端 
后端随机生成一个salt， 
使用生成salt对前端传过来的密码进行加密，然后将加密后密码和salt一起保存到db中

# WebMvcConfigurer

自己定义一些Handler，Interceptor，ViewResolver，MessageConverter

# @Inherited注解

   @Inherited 元注解是一个标记注解，@Inherited阐述了某个被标注的类型是被继承的。 
如果一个使用了@Inherited修饰的annotation类型被用于一个class，则这个annotation将被用于该class的子类。 

# @Documented注解

Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声明被注释了文档化，它的注释成为公共API的一部分。


# Retention({RetentionPolicy.Runtime}) 注解

RetentionPolicy这个枚举类型的常量描述保留注释的各种策略，它们与元注释(@Retention)一起指定注释要保留多长时间

# @Target({ElementType.TYPE}) 注解

ElementType 这个枚举类型的常量提供了一个简单的分类：注释可能出现在Java程序中的语法位置（这些常量与元注释类型(@Target)一起指定在何处写入注释的合法位置）








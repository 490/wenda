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
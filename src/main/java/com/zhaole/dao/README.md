# Repository
当你的一个类被@Component所注解，那么就意味着同样可以用@Repository, @Service, @Controller来替代它，同时这些注解会具备有更多的功能，而且功能各异。

这两个不能互换
@Controller 注解的bean会被spring-mvc框架所使用。 
@Repository 会被作为持久层操作（数据库）的bean来使用 

# Mapper
@Mapper是mybatis里的
Mybatis MapperScannerConfigurer 自动扫描 将Mapper接口生成代理注入到Spring

使用 @Mapper，最终 Mybatis 会有一个拦截器，会自动的把 @Mapper 注解的接口生成动态代理类。这点可以在 MapperRegistry 类中的源代码中查看。

@Mapper 注解针对的是一个一个的类，相当于是一个一个 Mapper.xml 文件。而一个接口一个接口的使用 @Mapper，太麻烦了，于是 @MapperScan 就应用而生了。@MapperScan 配置一个或多个包路径，自动的扫描这些包路径下的类，自动的为它们生成代理类。
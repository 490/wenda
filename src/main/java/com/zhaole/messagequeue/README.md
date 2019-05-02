# Configuration注解
从Spring3.0，@Configuration用于定义配置类，可替换xml配置文件，被注解的类内部包含有一个或多个被@Bean注解的方法，这些方法将会被AnnotationConfigApplicationContext或AnnotationConfigWebApplicationContext类进行扫描，并用于构建bean定义，初始化Spring容器。

# @RabbitListener注解
@RabbitListener注解指定目标方法来作为消费消息的方法，通过注解参数指定所监听的队列或者Binding。使用@RabbitListener可以设置一个自己明确默认值的RabbitListenerContainerFactory对象。
















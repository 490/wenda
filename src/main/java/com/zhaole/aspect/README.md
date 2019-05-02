@Aspect 注解
@Before("execution(* com.zhaole.controller.HomeController.*(..))")注解
public void beforeMethod(JoinPoint joinPoint)参数类型



RateLimiter
首先通过RateLimiter.create(1);创建一个限流器，参数代表每秒生成的令牌数，通过limiter.acquire(i);来以阻塞的方式获取令牌，当然也可以通过tryAcquire(int permits, long timeout, TimeUnit unit)来设置等待超时时间的方式获取令牌，如果超timeout为0，则代表非阻塞，获取不到立即返回。

aspectJ切面通过ProceedingJoinPoint想要获取当前执行的方法：

AspectJ使用org.aspectj.lang.JoinPoint接口表示目标类连接点对象，如果是环绕增强时，使用org.aspectj.lang.ProceedingJoinPoint表示连接点对象，该类是JoinPoint的子接口。任何一个增强方法都可以通过将第一个入参声明为JoinPoint访问到连接点上下文的信息。我们先来了解一下这两个接口的主要方法： 
1)JoinPoint 
   java.lang.Object[] getArgs()：获取连接点方法运行时的入参列表； 
   Signature getSignature() ：获取连接点的方法签名对象； 
   java.lang.Object getTarget() ：获取连接点所在的目标对象； 
   java.lang.Object getThis() ：获取代理对象本身； 
2)ProceedingJoinPoint 
ProceedingJoinPoint继承JoinPoint子接口，它新增了两个用于执行连接点方法的方法： 
   java.lang.Object proceed() throws java.lang.Throwable：通过反射执行目标对象的连接点处的方法； 
   java.lang.Object proceed(java.lang.Object[] args) throws java.lang.Throwable：通过反射执行目标对象连接点处的方法，不过使用新的入参替换原来的入参。 
























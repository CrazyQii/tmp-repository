/*
 Navicat Premium Data Transfer

 Source Server         : 121.41.169.208
 Source Server Type    : MySQL
 Source Server Version : 80027
 Source Host           : 121.41.169.208:3306
 Source Schema         : forum

 Target Server Type    : MySQL
 Target Server Version : 80027
 File Encoding         : 65001

 Date: 07/03/2022 16:54:14
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for hibernate_sequence
-- ----------------------------
DROP TABLE IF EXISTS `hibernate_sequence`;
CREATE TABLE `hibernate_sequence`  (
  `next_val` bigint NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of hibernate_sequence
-- ----------------------------
INSERT INTO `hibernate_sequence` VALUES (30);

-- ----------------------------
-- Table structure for t_article
-- ----------------------------
DROP TABLE IF EXISTS `t_article`;
CREATE TABLE `t_article`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `appreciation` bit(1) NOT NULL,
  `commentabled` bit(1) NOT NULL,
  `content` longtext CHARACTER SET utf8 COLLATE utf8_bin NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `first_picture` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `flag` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `published` bit(1) NOT NULL,
  `recommend` bit(1) NOT NULL,
  `share_statement` bit(1) NOT NULL,
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `views` int NULL DEFAULT 1,
  `type_id` bigint NULL DEFAULT NULL,
  `user_id` bigint NULL DEFAULT 1,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKrerqcw9tswkbq0973yo5a163t`(`type_id`) USING BTREE,
  INDEX `FKsgqpqfl0p7olcr7694a3pjl0q`(`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 26 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article
-- ----------------------------
INSERT INTO `t_article` VALUES (18, b'1', b'1', '众所周知，Spring拥有两大特性：IoC和AOP。IoC，英文全称Inversion of Control，意为控制反转。AOP，英文全称Aspect-Oriented Programming，意为面向切面编程。\r\n\r\nSpring核心容器的主要组件是Bean工厂（BeanFactory），Bean工厂使用控制反转（IoC）模式来降低程序代码之间的耦合度，并提供了面向切面编程（AOP）的实现。\r\n\r\n简单来说，Spring是一个轻量级的控制反转（IoC）和面向切面编程（AOP）的容器框架。\r\n\r\n下面，我们简要说明下这两大特性。\r\n\r\n \r\n\r\n##  1. Spring常用注解\r\n在具体介绍IoC和AOP之前，我们先简要说明下Spring常用注解\r\n\r\n1、@Controller：用于标注控制器层组件\r\n\r\n2、@Service：用于标注业务层组件\r\n\r\n3、@Component : 用于标注这是一个受 Spring 管理的组件，组件引用名称是类名，第一个字母小写。可以使用@Component(“beanID”) 指定组件的名称\r\n\r\n4、@Repository：用于标注数据访问组件，即DAO组件\r\n\r\n5、@Bean：方法级别的注解，主要用在@Configuration和@Component注解的类里，@Bean注解的方法会产生一个Bean对象，该对象由Spring管理并放到IoC容器中。引用名称是方法名，也可以用@Bean(name = \"beanID\")指定组件名\r\n\r\n6、@Scope(\"prototype\")：将组件的范围设置为原型的（即多例）。保证每一个请求有一个单独的action来处理，避免action的线程问题。\r\n\r\n由于Spring默认是单例的，只会创建一个action对象，每次访问都是同一个对象，容易产生并发问题，数据不安全。\r\n\r\n7、@Autowired：默认按类型进行自动装配。在容器查找匹配的Bean，当有且仅有一个匹配的Bean时，Spring将其注入@Autowired标注的变量中。\r\n\r\n8、@Resource：默认按名称进行自动装配，当找不到与名称匹配的Bean时会按类型装配。\r\n\r\n \r\n\r\n简单点说，就是，能够明确该类是一个控制器类组件的，就用@Controller；能够明确是一个服务类组件的，就用@Service；能够明确该类是一个数据访问组件的，就用@Repository；不知道他是啥或者不好区分他是啥，但是就是想让他动态装配的就用@Component。\r\n\r\n@Controller、@Service、@Component、@Repository都是类级别的注解，如果一个方法也想动态装配，就用@Bean。\r\n\r\n当我们想按类型进行自动装配时，就用@Autowired；当我们想按名称（beanID）进行自动装配时，就用@Resource；当我们需要根据比如配置信息等来动态装配不同的组件时，可以用getBean(\"beanID\")。\r\n\r\n到这里，如果对这些注解，或是自动装配不太理解，可以继续往下，看完 控制反转(IoC) 内容后再回来理解这里的内容。\r\n\r\n \r\n\r\n##  2. 控制反转(IoC)\r\n控制反转，简单点说，就是创建对象的控制权，被反转到了Spring框架上。\r\n\r\n通常，我们实例化一个对象时，都是使用类的构造方法来new一个对象，这个过程是由我们自己来控制的，而控制反转就把new对象的工交给了Spring容器。\r\n\r\n《expert ONE-ON-ONE J2EE Development without EJB》第6章中指出\r\n\r\nP128\r\n\r\nIoC Implementation Strategies\r\n\r\nIoC is a broad concept that can be implemented in different ways. There are two main types:\r\n\r\nDependency Lookup: The container provides callbacks to components, and a lookup context.This is the EJB and Apache Avalon approach. It leaves the onus on each component to use container APIs to look up resources and collaborators. The Inversion of Control is limited to the container invoking callback methods that application code can use to obtain resources.\r\n\r\nDependency Injection: Components do no look up; they provide plain Java methods enabling the container to resolve dependencies. The container is wholly responsible for wiring up components, passing resolved objects in to JavaBean properties or constructors. Use of JavaBean properties is called Setter Injection; use of constructor arguments is called Constructor Injection.\r\n\r\nP130\r\n\r\nThe second IoC strategy-Dependency Injection-is usually preferable.\r\n\r\n主要意思为：\r\n\r\nIoC的主要实现方式有两种：依赖查找、依赖注入。\r\n\r\n依赖注入是一种更可取的方式。\r\n\r\n那么依赖查找和依赖注入有什么区别呢？\r\n\r\n依赖查找，主要是容器为组件提供一个回调接口和上下文环境。这样一来，组件就必须自己使用容器提供的API来查找资源和协作对象，控制反转仅体现在那些回调方法上，容器调用这些回调方法，从而应用代码获取到资源。\r\n\r\n依赖注入，组件不做定位查询，只提供标准的Java方法让容器去决定依赖关系。容器全权负责组件的装配，把符合依赖关系的对象通过Java Bean属性或构造方法传递给需要的对象。\r\n\r\n### 2.1 IoC容器\r\nIoC容器：具有依赖注入功能的容器，可以创建对象的容器。IoC容器负责实例化、定位、配置应用程序中的对象并建立这些对象之间的依赖。\r\n\r\n### 2.2 依赖注入\r\nDI，英文全称，Dependency Injection，意为依赖注入。\r\n\r\n依赖注入：由IoC容器动态地将某个对象所需要的外部资源（包括对象、资源、常量数据）注入到组件(Controller, Service等）之中。简单点说，就是IoC容器会把当前对象所需要的外部资源动态的注入给我们。\r\n\r\nSpring依赖注入的方式主要有四个，基于注解注入方式、set注入方式、构造器注入方式、静态工厂注入方式。推荐使用基于注解注入方式，配置较少，比较方便。\r\n\r\n基于注解注入方式\r\n\r\n服务层代码\r\n\r\n```java\r\n@Service\r\npublic class AdminService {\r\n    //code\r\n}\r\n```\r\n控制层代码\r\n\r\n```java\r\n@Controller\r\n@Scope(\"prototype\")\r\npublic class AdminController {\r\n \r\n    @Autowired\r\n    private AdminService adminService;\r\n \r\n    //code\r\n}\r\n```\r\n@Autowired与@Resource都可以用来装配Bean，都可以写在字段、setter方法上。他们的区别是：\r\n\r\n@Autowired默认按类型进行自动装配（该注解属于Spring），默认情况下要求依赖对象必须存在，如果要允许为null，需设置required属性为false，例：@Autowired(required=false)。如果要使用名称进行装配，可以与@Qualifier注解一起使用。\r\n\r\n    @Autowired\r\n    @Qualifier(\"adminService\")\r\n    private AdminService adminService;\r\n@Resource默认按照名称进行装配（该注解属于J2EE），名称可以通过name属性来指定。如果没有指定name属性，当注解写在字段上时，默认取字段名进行装配；如果注解写在setter方法上，默认取属性名进行装配。当找不到与名称相匹配的Bean时，会按照类型进行装配。但是，name属性一旦指定，就只会按照名称进行装配。\r\n\r\n    @Resource(name = \"adminService\")\r\n    private AdminService adminService;\r\n \r\n\r\n除此之外，对于一些复杂的装载Bean的时机，比如我们需要根据配置装载不同的Bean，以完成不同的操作，可以使用getBean(“beanID”)的方式来加载Bean。\r\n\r\n通过BeanID加载Bean方法如下：\r\n\r\n```java\r\n@Component\r\npublic class BeanUtils implements ApplicationContextAware {\r\n \r\n    private static ApplicationContext applicationContext;\r\n \r\n    @Override\r\n    public void setApplicationContext(ApplicationContext applicationContext) {\r\n        if (BeanUtils.applicationContext == null) {\r\n            BeanUtils.applicationContext = applicationContext;\r\n        }\r\n    }\r\n \r\n    public static ApplicationContext getApplicationContext() {\r\n        return applicationContext;\r\n    }\r\n \r\n    public static Object getBean(String id) throws Exception {\r\n        try {\r\n            return applicationContext.containsBean(id) ? applicationContext.getBean(id) : null;\r\n        } catch (BeansException e) {\r\n            e.printStackTrace();\r\n            throw new Exception(\"not found bean id: \" + id);\r\n        }\r\n    }\r\n}\r\n```\r\n我们在需要装载Bean的地方调用该方法即可\r\n\r\n```java\r\npublic class BaseController {\r\n \r\n    protected IService loadService(String id) throws Exception {\r\n        IService iService = (IService) BeanUtils.getBean(id);\r\n        if (iService != null) {\r\n            return iService;\r\n        } else {\r\n            throw new Exception(\"加载Bean错误\");\r\n        }\r\n    }\r\n}\r\n```\r\n## 3. 面向切面编程(AOP)\r\n面向切面编程（AOP）就是纵向的编程。比如业务A和业务B现在需要一个相同的操作，传统方法我们可能需要在A、B中都加入相关操作代码，而应用AOP就可以只写一遍代码，A、B共用这段代码。并且，当A、B需要增加新的操作时，可以在不改动原代码的情况下，灵活添加新的业务逻辑实现。\r\n\r\n在实际开发中，比如商品查询、促销查询等业务，都需要记录日志、异常处理等操作，AOP把所有共用代码都剥离出来，单独放置到某个类中进行集中管理，在具体运行时，由容器进行动态织入这些公共代码。\r\n\r\nAOP主要一般应用于签名验签、参数校验、日志记录、事务控制、权限控制、性能统计、异常处理等。\r\n\r\n###  3.1 AOP涉及名词\r\n切面（Aspect）：共有功能的实现。如日志切面、权限切面、验签切面等。在实际开发中通常是一个存放共有功能实现的标准Java类。当Java类使用了@Aspect注解修饰时，就能被AOP容器识别为切面。\r\n\r\n通知（Advice）：切面的具体实现。就是要给目标对象织入的事情。以目标方法为参照点，根据放置的地方不同，可分为前置通知（Before）、后置通知（AfterReturning）、异常通知（AfterThrowing）、最终通知（After）与环绕通知（Around）5种。在实际开发中通常是切面类中的一个方法，具体属于哪类通知，通过方法上的注解区分。\r\n\r\n连接点（JoinPoint）：程序在运行过程中能够插入切面的地点。例如，方法调用、异常抛出等。Spring只支持方法级的连接点。一个类的所有方法前、后、抛出异常时等都是连接点。\r\n\r\n切入点（Pointcut）：用于定义通知应该切入到哪些连接点上。不同的通知通常需要切入到不同的连接点上，这种精准的匹配是由切入点的正则表达式来定义的。\r\n\r\n比如，在上面所说的连接点的基础上，来定义切入点。我们有一个类，类里有10个方法，那就产生了几十个连接点。但是我们并不想在所有方法上都织入通知，我们只想让其中的几个方法，在调用之前检验下入参是否合法，那么就用切点来定义这几个方法，让切点来筛选连接点，选中我们想要的方法。切入点就是来定义哪些类里面的哪些方法会得到通知。\r\n\r\n目标对象（Target）：那些即将切入切面的对象，也就是那些被通知的对象。这些对象专注业务本身的逻辑，所有的共有功能等待AOP容器的切入。\r\n\r\n代理对象（Proxy）：将通知应用到目标对象之后被动态创建的对象。可以简单地理解为，代理对象的功能等于目标对象本身业务逻辑加上共有功能。代理对象对于使用者而言是透明的，是程序运行过程中的产物。目标对象被织入共有功能后产生的对象。\r\n\r\n织入（Weaving）：将切面应用到目标对象从而创建一个新的代理对象的过程。这个过程可以发生在编译时、类加载时、运行时。Spring是在运行时完成织入，运行时织入通过Java语言的反射机制与动态代理机制来动态实现。\r\n\r\n### 3.2 Pointcut用法\r\nPointcut格式为：\r\n\r\nexecution(modifier-pattern? ret-type-pattern declaring-type-pattern? name-pattern(param-pattern) throws-pattern?)\r\n修饰符匹配 modifier-pattern? 例：public private\r\n\r\n返回值匹配 ret-type-pattern 可以用 * 表示任意返回值\r\n\r\n类路径匹配 declaring-type-pattern? 全路径的类名\r\n\r\n方法名匹配 name-pattern 可以指定方法名或者用 * 表示所有方法；set* 表示所有以set开头的方法\r\n\r\n参数匹配 (param-pattern) 可以指定具体的参数类型，多个参数用“,”分隔；可以用 * 表示匹配任意类型的参数；可以用 (..) 表示零个或多个任意参数\r\n\r\n异常类型匹配throws-pattern? 例：throws Exception\r\n\r\n其中后面跟着 ? 表示可选项\r\n\r\n例：\r\n\r\n```java\r\n@Pointcut(\"execution(public * cn.wbnull. springbootdemo.controller.*.*(..))\")\r\nprivate void sign() {\r\n \r\n}\r\n```\r\n \r\n\r\n### 3.3 一个例子\r\n以 Spring Boot入门：使用AOP实现拦截器 中的AOP为例\r\n\r\n```java\r\n@Aspect\r\n@Component\r\npublic class SignAop {\r\n \r\n}\r\n```\r\nSignAop类使用了@Aspect注解，则该类可以被AOP容器识别为切面。\r\n\r\n \r\n\r\n```java\r\n@Aspect\r\n@Component\r\npublic class SignAop {\r\n \r\n    @Pointcut(\"execution(public * cn.wbnull.springbootdemo.controller.*.*(..))\")\r\n    private void signAop() {\r\n \r\n    }\r\n}\r\n```\r\n@Pointcut声明一个切入点，范围为controller包下所有的类的所有方法\r\n\r\n注：作为切入点签名的方法必须返回void类型\r\n\r\n \r\n```java\r\n\r\n@Aspect\r\n@Component\r\npublic class SignAop {\r\n \r\n    @Pointcut(\"execution(public * cn.wbnull.springbootdemo.controller.*.*(..))\")\r\n    private void signAop() {\r\n \r\n    }\r\n \r\n    @Before(\"signAop()\")\r\n    public void doBefore(JoinPoint joinPoint) throws Exception {\r\n        //code\r\n       }\r\n \r\n    @AfterReturning(value = \"signAop()\", returning = \"params\")\r\n    public JSONObject doAfterReturning(JoinPoint joinPoint, JSONObject params) {\r\n        //code\r\n        }\r\n}\r\n```\r\ndoBefore()方法使用@Before(\"signAop()\")注解，表示前置通知（在某连接点之前执行的通知），但这个通知不能阻止连接点之前的执行流程，除非它抛出一个异常。\r\n\r\ndoAfterReturning()方法使用@AfterReturning(value = \"signAop()\", returning = \"params\")注解，表示后置通知（在某连接点正常完成后执行的通知），通常在一个匹配的方法返回的时候执行。\r\n\r\n实际运行时，在进入controller包下所有方法前，都会进入doBefore()方法，在controller包下方法执行完成后，都会进入doAfterReturning()方法。\r\n\r\n \r\n\r\nAOP具体使用可以参考 Spring Boot入门：使用AOP实现拦截器\r\n\r\n \r\n\r\n到这里，Spring两大特性IoC和AOP基本讲述完成，后续若想起其他再进行补充。', '2022-02-21 13:24:41', 'https://picsum.photos/id/1/800/450', '原创', b'1', b'1', b'1', '深入理解Spring两大特性：IoC和AOP', '2022-02-22 13:22:06', 61, 1, 1, '众所周知，Spring拥有两大特性：IoC和AOP。IoC，英文全称Inversion of Control，意为控制反转。AOP，英文全称Aspect-Oriented Programming，意为面向切面编程。');
INSERT INTO `t_article` VALUES (19, b'1', b'1', 'Spring Boot最常用的3种读取properties配置文件中数据的方法：\r\n1、使用@Value注解读取\r\n读取properties配置文件时，默认读取的是application.properties。\r\n\r\napplication.properties：\r\n\r\ndemo.name=Name\r\ndemo.age=18\r\nJava代码：\r\n\r\nimport org.springframework.beans.factory.annotation.Value;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n \r\n@RestController\r\npublic class GatewayController {\r\n \r\n    @Value(\"${demo.name}\")\r\n    private String name;\r\n \r\n    @Value(\"${demo.age}\")\r\n    private String age;\r\n \r\n    @RequestMapping(value = \"/gateway\")\r\n    public String gateway() {\r\n        return \"get properties value by \'\'@Value\'\' :\" +\r\n                //1、使用@Value注解读取\r\n                \" name=\" + name +\r\n                \" , age=\" + age;\r\n    }\r\n}\r\n运行结果如下：\r\n\r\n\r\n\r\n这里，如果要把\r\n\r\n @Value(\"${demo.name}\")\r\n            private String name;\r\n            @Value(\"${demo.age}\")\r\n            private String age;\r\n\r\n部分放到一个单独的类A中进行读取，然后在类B中调用，则要把类A增加@Component注解，并在类B中使用@Autowired自动装配类A，代码如下。\r\n\r\n类A：\r\n\r\nimport org.springframework.beans.factory.annotation.Value;\r\nimport org.springframework.stereotype.Component;\r\n \r\n@Component\r\npublic class ConfigBeanValue {\r\n \r\n    @Value(\"${demo.name}\")\r\n    public String name;\r\n \r\n    @Value(\"${demo.age}\")\r\n    public String age;\r\n}\r\n类B：\r\n\r\nimport cn.wbnull.springbootdemo.config.ConfigBeanValue;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n \r\n@RestController\r\npublic class GatewayController {\r\n \r\n    @Autowired\r\n    private ConfigBeanValue configBeanValue;\r\n \r\n    @RequestMapping(value = \"/gateway\")\r\n    public String gateway() {\r\n        return \"get properties value by \'\'@Value\'\' :\" +\r\n                //1、使用@Value注解读取\r\n                \" name=\" + configBeanValue.name +\r\n                \" , age=\" + configBeanValue.age;\r\n    }\r\n}\r\n运行结果如下：\r\n\r\n\r\n\r\n注意：如果@Value${}所包含的键名在application.properties配置文件中不存在的话，会抛出异常：\r\n\r\norg.springframework.beans.factory.BeanCreationException: Error creating bean with name \'configBeanValue\': Injection of autowired dependencies failed; nested exception is java.lang.IllegalArgumentException: Could not resolve placeholder \'demo.name\' in value \"${demo.name}\"\r\n\r\n \r\n\r\n2、使用Environment读取\r\napplication.properties：\r\n\r\ndemo.sex=男\r\ndemo.address=山东\r\nJava代码：\r\n\r\nimport cn.wbnull.springbootdemo.config.ConfigBeanValue;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport org.springframework.core.env.Environment;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n \r\n@RestController\r\npublic class GatewayController {\r\n \r\n    @Autowired\r\n    private ConfigBeanValue configBeanValue;\r\n \r\n    @Autowired\r\n    private Environment environment;\r\n \r\n    @RequestMapping(value = \"/gateway\")\r\n    public String gateway() {\r\n        return \"get properties value by \'\'@Value\'\' :\" +\r\n                //1、使用@Value注解读取\r\n                \" name=\" + configBeanValue.name +\r\n                \" , age=\" + configBeanValue.age +\r\n                \"<p>get properties value by \'\'Environment\'\' :\" +\r\n                //2、使用Environment读取\r\n                \" , sex=\" + environment.getProperty(\"demo.sex\") +\r\n                \" , address=\" + environment.getProperty(\"demo.address\");\r\n    }\r\n}\r\n运行，发现中文乱码：\r\n\r\n\r\n\r\n这里，我们在application.properties做如下配置：\r\n\r\nserver.tomcat.uri-encoding=UTF-8\r\nspring.http.encoding.charset=UTF-8\r\nspring.http.encoding.enabled=true\r\nspring.http.encoding.force=true\r\nspring.messages.encoding=UTF-8\r\n然后修改IntelliJ IDEA，File --> Settings --> Editor --> File Encodings ，将最下方Default encoding for properties files设置为UTF-8，并勾选Transparent native-to-ascii conversion。\r\n\r\n\r\n\r\n重新运行结果如下：\r\n\r\n\r\n\r\n \r\n\r\n3、使用@ConfigurationProperties注解读取\r\n在实际项目中，当项目需要注入的变量值很多时，上述所述的两种方法工作量会变得比较大，这时候我们通常使用基于类型安全的配置方式，将properties属性和一个Bean关联在一起，即使用注解@ConfigurationProperties读取配置文件数据。\r\n\r\n在src\\main\\resources下新建config.properties配置文件：\r\n\r\ndemo.phone=10086\r\ndemo.wife=self\r\n创建ConfigBeanProp并注入config.properties中的值：\r\n\r\nimport org.springframework.boot.context.properties.ConfigurationProperties;\r\nimport org.springframework.context.annotation.PropertySource;\r\nimport org.springframework.stereotype.Component;\r\n \r\n@Component\r\n@ConfigurationProperties(prefix = \"demo\")\r\n@PropertySource(value = \"config.properties\")\r\npublic class ConfigBeanProp {\r\n \r\n    private String phone;\r\n \r\n    private String wife;\r\n \r\n    public String getPhone() {\r\n        return phone;\r\n    }\r\n \r\n    public void setPhone(String phone) {\r\n        this.phone = phone;\r\n    }\r\n \r\n    public String getWife() {\r\n        return wife;\r\n    }\r\n \r\n    public void setWife(String wife) {\r\n        this.wife = wife;\r\n    }\r\n}\r\n\r\n@Component 表示将该类标识为Bean\r\n\r\n@ConfigurationProperties(prefix = \"demo\")用于绑定属性，其中prefix表示所绑定的属性的前缀。\r\n\r\n@PropertySource(value = \"config.properties\")表示配置文件路径。\r\n\r\n \r\n\r\n使用时，先使用@Autowired自动装载ConfigBeanProp，然后再进行取值，示例如下：\r\n\r\nimport cn.wbnull.springbootdemo.config.ConfigBeanProp;\r\nimport cn.wbnull.springbootdemo.config.ConfigBeanValue;\r\nimport org.springframework.beans.factory.annotation.Autowired;\r\nimport org.springframework.core.env.Environment;\r\nimport org.springframework.web.bind.annotation.RequestMapping;\r\nimport org.springframework.web.bind.annotation.RestController;\r\n \r\n@RestController\r\npublic class GatewayController {\r\n \r\n    @Autowired\r\n    private ConfigBeanValue configBeanValue;\r\n \r\n    @Autowired\r\n    private Environment environment;\r\n \r\n    @Autowired\r\n    private ConfigBeanProp configBeanProp;\r\n \r\n    @RequestMapping(value = \"/gateway\")\r\n    public String gateway() {\r\n        return \"get properties value by \'\'@Value\'\' :\" +\r\n                //1、使用@Value注解读取\r\n                \" name=\" + configBeanValue.name +\r\n                \" , age=\" + configBeanValue.age +\r\n                \"<p>get properties value by \'\'Environment\'\' :\" +\r\n                //2、使用Environment读取\r\n                \" sex=\" + environment.getProperty(\"demo.sex\") +\r\n                \" , address=\" + environment.getProperty(\"demo.address\") +\r\n                \"<p>get properties value by \'\'@ConfigurationProperties\'\' :\" +\r\n                //3、使用@ConfigurationProperties注解读取\r\n                \" phone=\" + configBeanProp.getPhone() +\r\n                \" , wife=\" + configBeanProp.getWife();\r\n    }\r\n}\r\n\r\n运行结果如下：\r\n\r\n', '2021-01-21 12:19:08', 'https://img0.baidu.com/it/u=3711645934,2253971284&fm=253&fmt=auto&app=138&f=GIF?w=800&h=450', '转载', b'1', b'1', b'1', 'Spring Boot读取properties配置文件中的数据', '2022-02-22 10:53:16', 6, 1, 1, 'Spring Boot最常用的3种读取properties配置文件中数据的方法：\r\n1、使用@Value注解读取\r\n读取properties配置文件时，默认读取的是application.properties。');
INSERT INTO `t_article` VALUES (20, b'1', b'1', '1、Spring能做什么\r\n1.1、Spring的能力\r\nWhatSpringcandoMicroservicesWedappsCloudReactiveFrameworksforfast,secure.QuicklydeliverYourcode,anycloud-weveSpring\'sasynchronous,andresponsivewebnonblockingarchitectureproduction-gradefeaturesgotyoucovered.connectmeansyoucanggermoreapplicationsconnectedtoandscaleyourserviceswithindependentlywhateveryourplatform.fromyourcomputingevolvablemicroservicesanydatastore.resourcesBatchEventDrivenServerlessTheultiateflexibility.ScalelntegratewithyourAutomatedtasks.Offlineupondemandandscaletoenterprise.Reacttoprocessingofdataatatimebusinessevents.Actonyourtosuityou.zerowhenthere\'snodemand.streamingdatainrealtime\r\nimage.png\r\n\r\n\r\n1.2、Spring的生态\r\nhttps://spring.io/projects/spring-boot\r\n\r\n覆盖了：\r\nweb开发\r\n数据访问\r\n安全控制\r\n分布式\r\n消息服务\r\n移动开发\r\n批处理\r\n......\r\n1.3、Spring5重大升级\r\n1.3.1、响应式编程\r\nSpringBoot2ReactorOptionalDependencyServletstackReactiveStackSpringMvcisbuiltontheServletASpringWebfluxisanon-blockingwebandusesasynchronousblocking/oframeworkbuiltfromthegrounduptotakearchitecturewithaone-request-peradvantageofmulti-core,next-generationthreadmodel.processorsandhandlemassivenumbersofconcurrentconnectionsServletContainersNetty.Servlet3.1+ContaiesServletAPlReactiveStreamsAdaptersSpringSecurityReactiveSpringSecuritySpringMvCSpringWebFluxSpringDataRepositoriesSpringDataReacitiveRepositoriesMongo,Cassandra,Redis,Couchbase,R2DBCJDBCJPANOSQL\r\nimage.png\r\n\r\n1.3.2、内部源码设计\r\n基于Java8的一些新特性，如：接口默认实现。重新设计源码架构。\r\n\r\n2、为什么用SpringBoot\r\n\r\nSpring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can \"just run\".\r\n\r\n能快速创建出生产级别的Spring应用\r\n\r\n2.1、SpringBoot优点\r\n●\r\nCreate stand-alone Spring applications\r\n○\r\n创建独立Spring应用\r\n●\r\nEmbed Tomcat, Jetty or Undertow directly (no need to deploy WAR files)\r\n○\r\n内嵌web服务器\r\n●\r\nProvide opinionated \'starter\' dependencies to simplify your build configuration\r\n○\r\n自动starter依赖，简化构建配置\r\n●\r\nAutomatically configure Spring and 3rd party libraries whenever possible\r\n○\r\n自动配置Spring以及第三方功能\r\n●\r\nProvide production-ready features such as metrics, health checks, and externalized configuration\r\n○\r\n提供生产级别的监控、健康检查及外部化配置\r\n●\r\nAbsolutely no code generation and no requirement for XML configuration\r\n○\r\n无代码生成、无需编写XML\r\n\r\nSpringBoot是整合Spring技术栈的一站式框架\r\nSpringBoot是简化Spring技术栈的快速开发脚手架\r\n\r\n2.2、SpringBoot缺点\r\n●\r\n人称版本帝，迭代快，需要时刻关注变化\r\n●\r\n封装太深，内部原理复杂，不容易精通\r\n3、时代背景\r\n3.1、微服务\r\nJames Lewis and Martin Fowler (2014)  提出微服务完整概念。https://martinfowler.com/microservices/\r\nIn short, the microservice architectural style is an approach to developing a single application as a suite of small services, each running in its own process and communicating with lightweight mechanisms, often an HTTP resource API. These services are built around business capabilities and independently deployable by fully automated deployment machinery. There is a bare minimum of centralized management of these services, which may be written in different programming languages and use different data storage technologies.-- James Lewis and Martin Fowler (2014)\r\n●\r\n微服务是一种架构风格\r\n●\r\n一个应用拆分为一组小型服务\r\n●\r\n每个服务运行在自己的进程内，也就是可独立部署和升级\r\n●\r\n服务之间使用轻量级HTTP交互\r\n●\r\n服务围绕业务功能拆分\r\n●\r\n可以由全自动部署机制独立部署\r\n●\r\n去中心化，服务自治。服务可以使用不同的语言、不同的存储技术\r\n\r\n3.2、分布式\r\n\r\n\r\n\r\n分布式的困难\r\n●\r\n远程调用\r\n●\r\n服务发现\r\n●\r\n负载均衡\r\n●\r\n服务容错\r\n●\r\n配置管理\r\n●\r\n服务监控\r\n●\r\n链路追踪\r\n●\r\n日志管理\r\n●\r\n任务调度\r\n●\r\n......\r\n\r\n分布式的解决\r\n●\r\nSpringBoot + SpringCloud\r\n\r\n\r\n\r\n\r\n3.3、云原生\r\n原生应用如何上云。 Cloud Native\r\n上云的困难\r\n●\r\n服务自愈\r\n●\r\n弹性伸缩\r\n●\r\n服务隔离\r\n●\r\n自动化部署\r\n●\r\n灰度发布\r\n●\r\n流量治理\r\n●\r\n......\r\n上云的解决\r\n\r\n\r\n\r\n4、如何学习SpringBoot\r\n4.1、官网文档架构\r\nSpringBootReferenceDocumentationPhipwewb:DwsyruhogStb3tienDdlaeMmMTherererencedocumentationconsistsofthefollowingsectionsLegalinformation.LegalDocumentationOverviewAbouttheDocumentationGetingHelpFrststepsandmoreMATYSBOSRGettingStarted入门AALDSJHEmSSTUUIBYURO.HUIPBSHDPNEODOMUsingSpringBoot进价ProfilesLoingsecurity.cingpingrtiiSpringBootFeatures高送特仨MonitoringMetricsAudiigadmoreSpringBootActuatorDeployingSpringBootappllcatlonsDeployingtotheCloud,nstallingasaunixapplicationSprlngBootcLiinstallingthecLlusingthecLiconfguinghBuilDToOLPluginsMavenPlugin.GradlePlugin,Antlib.andmorc\"How-to\'GuidesApplLcaLonDevelopmenconurdd小技巧\r\nimage.png\r\n\r\n\r\nThereferencedocumentationhasthefollowingappendices:commonapplitationproperiesthatcanbusdcongureupiaioApplicationProperties所有配置低览ComnMetadatausedtodescribeconfigurationpropertiesConfigurationMetadataAuto-configurationClasses所有自动配置Auto-configurationclassesprovidedbyspringBoot.TestAuto-configurationAnnotationsTestautoconfigurationannotationsusedtotestsicesoyourapplication常见测试汪解ExecutableJarsSpringBootsexecutablejars,heiluchesherfrmat可执行jarDependencyVersions所有场景体版本ealsofthedependenciesthataremanagedbyspringBoot\r\nimage.png\r\n\r\n\r\n\r\n\r\n查看版本新特性；\r\nhttps://github.com/spring-projects/spring-boot/wiki#release-notes\r\nSpringBoot2.3.4.RELEASESAMPLESLEARNOVERVIEWSpingBootmakesiteasytocyjustrun\"weakeangpmoniecvwrthepPaomyminimumfus.MostSpringBootappicaonedinimingconiiHYOUReLOkingorinoinusnicinudmutheprojectreleasenotessectiononourwiki.earlierrelease,checkouthep\r\nimage.png\r\n', '2022-02-21 12:19:11', 'https://img0.baidu.com/it/u=3711645934,2253971284&fm=253&fmt=auto&app=138&f=GIF?w=800&h=450', '原创', b'1', b'1', b'1', 'Spring与SpringBoot', '2022-02-22 10:52:34', 2, 1, 1, 'Spring能做什么、Spring的能力、Spring的生态');
INSERT INTO `t_article` VALUES (22, b'1', b'1', '这是一篇测试文章。。。。。。。。。。。。。。。\r\n这是一篇测试文章。。。。。。。。。。。。。。。\r\n这是一篇测试文章。。。。。。。。。。。。。。。\r\n这是一篇测试文章。。。。。。。。。。。。。。。\r\n这是一篇测试文章。。。。。。。。。。。。。。。\r\n这是一篇测试文章。。。。。。。。。。。。。。。\r\n这是一篇测试文章。。。。。。。。。。。。。。。1', '2022-02-21 12:13:53', 'https://img1.baidu.com/it/u=2893615075,2492346271&fm=253&fmt=auto&app=138&f=GIF?w=800&h=450', '原创', b'1', b'1', b'1', '测试文章', '2022-02-24 15:23:58', 263, 1, 1, '这是一篇测试文章。。。。。。。。。。。。。。。');

-- ----------------------------
-- Table structure for t_article_tags
-- ----------------------------
DROP TABLE IF EXISTS `t_article_tags`;
CREATE TABLE `t_article_tags`  (
  `articles_id` bigint NOT NULL,
  `tags_id` bigint NOT NULL,
  INDEX `FK1amq6fug05o4tlj1f96surfll`(`tags_id`) USING BTREE,
  INDEX `FK87nyce3wq0nohfo9mybde6x5v`(`articles_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_article_tags
-- ----------------------------
INSERT INTO `t_article_tags` VALUES (20, 15);
INSERT INTO `t_article_tags` VALUES (19, 15);
INSERT INTO `t_article_tags` VALUES (18, 14);
INSERT INTO `t_article_tags` VALUES (18, 15);
INSERT INTO `t_article_tags` VALUES (18, 16);
INSERT INTO `t_article_tags` VALUES (22, 16);
INSERT INTO `t_article_tags` VALUES (23, 15);
INSERT INTO `t_article_tags` VALUES (24, 16);

-- ----------------------------
-- Table structure for t_comment
-- ----------------------------
DROP TABLE IF EXISTS `t_comment`;
CREATE TABLE `t_comment`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `content` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `article_id` bigint NULL DEFAULT NULL,
  `parent_comment_id` bigint NULL DEFAULT NULL,
  `admin_comment` bit(1) NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `FKlsvvc2ob8lxg2m9qqry15ru0y`(`article_id`) USING BTREE,
  INDEX `FK4jj284r3pb7japogvo6h72q95`(`parent_comment_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 97 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_comment
-- ----------------------------
INSERT INTO `t_comment` VALUES (90, '/images/avatar.jpg', '123213213', '2022-03-06 13:50:19', '953867626@qq.com', 'Yodayuki', 22, NULL, b'0');
INSERT INTO `t_comment` VALUES (95, '/images/avatar.jpg', 'test', '2022-03-07 00:08:01', '953867626@qq.com', 'Yodayuki', 22, NULL, b'0');
INSERT INTO `t_comment` VALUES (96, '/images/avatar.jpg', '测试评论', '2022-03-07 00:10:19', '949525977@qq.com', '林圣浩', 22, NULL, b'0');

-- ----------------------------
-- Table structure for t_discuss
-- ----------------------------
DROP TABLE IF EXISTS `t_discuss`;
CREATE TABLE `t_discuss`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '  ',
  `content` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `parent_id` int NULL DEFAULT NULL,
  `user_id` int NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of t_discuss
-- ----------------------------
INSERT INTO `t_discuss` VALUES (1, '我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论我要参与讨论', '2022-03-05 13:46:53', '测试题目', -1, 1);
INSERT INTO `t_discuss` VALUES (3, '测试回复', '2022-03-06 14:59:47', NULL, NULL, 6);
INSERT INTO `t_discuss` VALUES (5, '测试', '2022-03-06 15:08:45', NULL, 1, 6);
INSERT INTO `t_discuss` VALUES (8, '我发布了一个讨论', '2022-03-07 07:27:09', NULL, 1, 6);
INSERT INTO `t_discuss` VALUES (9, '我也发布了一个讨论\r\n', '2022-03-07 07:28:07', NULL, 1, 1);
INSERT INTO `t_discuss` VALUES (13, '发布讨论', '2022-03-07 08:29:15', NULL, 11, 6);
INSERT INTO `t_discuss` VALUES (14, '详细说明', '2022-03-07 08:30:22', '股票数据采集模拟交易及价格监控系统', -1, 6);
INSERT INTO `t_discuss` VALUES (15, '参与讨论', '2022-03-07 08:41:06', NULL, 14, 6);

-- ----------------------------
-- Table structure for t_tag
-- ----------------------------
DROP TABLE IF EXISTS `t_tag`;
CREATE TABLE `t_tag`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 19 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_tag
-- ----------------------------
INSERT INTO `t_tag` VALUES (14, 'Java');
INSERT INTO `t_tag` VALUES (15, 'Spring Boot4');
INSERT INTO `t_tag` VALUES (16, 'Aop');
INSERT INTO `t_tag` VALUES (17, 'jsp');

-- ----------------------------
-- Table structure for t_type
-- ----------------------------
DROP TABLE IF EXISTS `t_type`;
CREATE TABLE `t_type`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 13 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_type
-- ----------------------------
INSERT INTO `t_type` VALUES (1, '计算机6');
INSERT INTO `t_type` VALUES (2, '文学');
INSERT INTO `t_type` VALUES (3, '生物');
INSERT INTO `t_type` VALUES (4, '物理');
INSERT INTO `t_type` VALUES (5, '化学');
INSERT INTO `t_type` VALUES (6, '建筑');
INSERT INTO `t_type` VALUES (7, '科研');
INSERT INTO `t_type` VALUES (8, '数学');
INSERT INTO `t_type` VALUES (9, '地理');
INSERT INTO `t_type` VALUES (10, '历史');
INSERT INTO `t_type` VALUES (11, '法律');

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `create_time` datetime NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `nickname` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `type` int NULL DEFAULT NULL,
  `update_time` datetime NULL DEFAULT NULL,
  `username` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `role` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL COMMENT '用户角色',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `hobby` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  `tech` varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'https://picsum.photos/id/1005/100/100', '2022-02-16 16:02:13', '949525977@qq.com', '林圣浩', '123456', 1, '2022-02-16 16:02:53', 'felix', 'user', '平平无奇大四毕业党，分享自己的学习心得。\r\n\r\n喜欢折腾，正在探索高效学习编程技术的方法....', '编程,学习,运动,游戏', 'JavaWeb,Spring,MySql,Mybatis');
INSERT INTO `t_user` VALUES (6, 'https://tse1-mm.cn.bing.net/th/id/OIP-C.3dEvA4aEcT2P79gRTAftnwHaHa?pid=ImgDet&rs=1', '2022-03-01 13:36:58', '953867626@qq.com', 'Yodayuki', '123', NULL, '2022-03-01 13:36:58', 'user', 'user', NULL, NULL, NULL);
INSERT INTO `t_user` VALUES (7, 'https://tse1-mm.cn.bing.net/th/id/R-C.34bfc89571de5dda317179add19618bd?rik=Ry9URN37NGJqFw&riu=http%3a%2f%2fwww.qubiaoqing.cn%2fpic%2f2020%2f11%2f25%2fxw5bz1s1yyp.jpg&ehk=RD2rIOmemzj5P3T5gTByxO0fJfzSgjDfucufyhl8N%2bY%3d&risl=&pid=ImgRaw&r=0', '2022-03-05 17:30:10', '11111111@qq.com', 'admin', '123456', 1, '2022-03-05 17:30:34', 'root', 'admin', NULL, NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;

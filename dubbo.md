#dubbo的使用
增加dubbo的依赖
```
<dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>dubbo</artifactId>
                <version>2.5.2</version>
                <exclusions>
                    <exclusion>
                        <groupId>org.springframework</groupId>
                        <artifactId>spring</artifactId>
                    </exclusion>
                </exclusions>
            </dependency>
            
             <dependency>
                 <groupId>org.springframework.boot</groupId>
                 <artifactId>spring-boot-starter-dubbo</artifactId>
                 <version>1.3.6.RELEASE</version>
           </dependency>
```
dubbo的配置文件
```
spring.dubbo.application.name=provider
spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
spring.dubbo.protocol.name=dubbo
spring.dubbo.protocol.port=20880
spring.dubbo.scan=com.grb.indonesia
```
使用dubbo，在实现类上增加`@Service`

dubbo的测试方法：

- 启动zookeeper的应用
    `sudo sh zkServer.sh start（启动命令zookeeper-3.4.9/bin/zkServer.sh）`
    `sudo sh zkServer.sh status （查看启动状态）`
- 用tomcat启动dubbo-admin的应用（需要下载dubbo源码，然后自己打包dubbo-admin模块，部署的时候以root的方式部署。启动完成之后直接访问 http://localhost:8080/dubbo-admin（注意端口号和用户名密码）用户名密码 默认 root root
- 将springboot_jooq_zipkin_test项目打包并将api包发布出去。相关的应用引用并调用即可。

    ####方式一:
    ######bean配置
    ```
        <!-- 消费方应用名，用于计算依赖关系，不是匹配条件，不要与提供方一样 -->
        <dubbo:application name="hehe_consumer" />

        <!-- 使用zookeeper注册中心暴露服务地址 -->
        <!-- <dubbo:registry address="multicast://224.5.6.7:1234" /> -->
        <dubbo:registry address="zookeeper://127.0.0.1:2181" />

        <!-- 生成远程服务代理，可以像使用本地bean一样使用demoService -->
        <dubbo:reference id="demoService" interface="com.grb.zk.TestZookeeper.provider.DemoService" />
        
        <dubbo:reference id="userService" interface="com.grb.indonesia.api.UserExporterService" />

    ```
    ######调用方式
    ```
    public class UserConsumer {
        public static void main(String[] args) throws IOException {
            ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(  
                    new String[] { "applicationContext.xml" });  
            context.start();  
      
            UserExporterService demoService = (UserExporterService) context.getBean("userService");
            demoService.echo();
        }
    }
    ```
    ####方式二（使用spring-boot-starter-dubbo）:
    ######在Spring Boot项目的pom.xml中添加以下依赖:
    ```
    <dependency>
             <groupId>org.springframework.boot</groupId>
             <artifactId>spring-boot-starter-dubbo</artifactId>
             <version>1.3.1.RELEASE</version>
     </dependency>
    ```
    ######在application.properties添加Dubbo的版本信息和客户端超时信息,如下:
    ```
    spring.dubbo.application.name=test-consumer
    spring.dubbo.registry.address=zookeeper://127.0.0.1:2181
    spring.dubbo.scan=com.grb.indonesia
    ```
    ######引用Dubbo服务,只需要添加要发布的服务实现上添加 @Reference ,如下:
    ```
    @Component
    public class UserAction {

        @Reference(version = "1.0.0")
        private UserExporterService fooService;
    }
    ```














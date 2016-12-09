#netflix中eureka（服务注册与发现）使用方式
运行eureka-server项目，访问`http://localhost:8761/`查看是否成功启动。

POM配置
```
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-netflix</artifactId>
    <version>1.2.3.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
    <version>1.2.3.RELEASE</version>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka-server</artifactId>
    <version>1.2.3.RELEASE</version>
</dependency>
```

与springboot集成
```
spring.application.name=pay-indonesia
eureka.client.serviceUrl.defaultZone=http://127.0.0.1:8761/eureka

@EnableEurekaClient
@SpringBootApplication
@EnableSwagger2
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}
```


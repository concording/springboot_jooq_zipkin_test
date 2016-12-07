#rabbitMQ使用文档

配置文件
```
rabbitMQ.ip=10.23.1.132
rabbitMQ.username=guest
rabbitMQ.password=123456
```

与springboot集成
```
@Configuration
@EnableConfigurationProperties
public class RabbitMQConfig {
    
    @Value("${rabbitMQ.ip}")
    private String ip;
    
    @Value("${rabbitMQ.username}")
    private String username;
    
    @Value("${rabbitMQ.password}")
    private String password;
    
    @Primary
    @Bean(name = "rabbitMQConnection")
    public Connection getRabbitMQConnection(){
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(ip);
        factory.setUsername(username);
        factory.setPassword(password);
        Connection connection = null;
        try {
            connection = factory.newConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return connection;
    }

}
```

使用方式(下面的是最基本的使用方式)
```
@Resource(name="rabbitMQConnection")
Connection connection;

private void testMQ() {
    try {
        Channel channel = connection.createChannel();
        channel.queueDeclare("test-rabbitMQ", false, false, false, null);
        String message = "Hello World!";
        for(int i=0;i<10;i++){
            channel.basicPublish("", "test-rabbitMQ", null, message.getBytes("UTF-8"));
        }
        System.out.println(" [x] Sent '" + message + "'");
        channel.close();
        connection.close();
    } catch (IOException e) {
    } catch (TimeoutException e) {
    }

}
```

[rabbitMQ使用demo](https://www.rabbitmq.com/tutorials/tutorial-six-java.html)



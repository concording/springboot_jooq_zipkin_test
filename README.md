#测试项目

*目前已经集成springboot、jooq、zipkin+brave、dubbo、swaggerUI、redis、rabbitMQ、druid等，之后会持续添加其他组件，具体的集成方式参考各个模块相关的文档。*

###测试步骤
1. 启动远程服务redis、rabbitMQ。本人的redis和rabbitMQ在10.23.1.132虚拟机上。
   ```
   /usr/local/redis/bin/redis-server /usr/local/redis/etc/redis.conf
   ```
测试redis服务是否已经启动`redis-cli -h 10.23.1.132 ping`
   ```
   /usr/sbin/rabbitmq-server
   ```
查看MQ是否启动成功`http://10.23.1.132:15672/mgmt`
2. 启动本机的mysql和zookeeper服务。`mysql_start` `zk_start`
3. 运行APP.java
4. 访问http://localhost:8090/swagger-ui.html,点击选择要运行的方法。











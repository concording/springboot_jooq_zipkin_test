#redis使用方法

配置文件
```
redis.ip=10.23.1.132
redis.port=6379
```

与springboot集成（jedis）
```
@Configuration
@EnableConfigurationProperties
public class RedisConfig {

    @Value("${redis.ip}")
    private String ip;
    
    @Value("${redis.port}")
    private String port;
    
    @Primary
    @Bean(name = "redis")
    public Jedis getJedis() {
        Jedis jedis = new Jedis(ip);
        return jedis;
    }
}
```

使用方法
```
@Autowired Jedis redis;

redis.set("key", "value");
redis.get("key");
```

[redis使用文档](https://www.tutorialspoint.com/redis/redis_java.htm)
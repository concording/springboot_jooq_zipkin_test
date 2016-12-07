package com.grb.indonesia;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import redis.clients.jedis.Jedis;

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

package com.grb.indonesia;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

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

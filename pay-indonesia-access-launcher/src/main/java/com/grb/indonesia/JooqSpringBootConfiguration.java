package com.grb.indonesia;

import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.impl.DataSourceConnectionProvider;
import org.jooq.impl.DefaultConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.vendor.MySqlValidConnectionChecker;

/**
 * jooQ的配置文件，包括druid数据库连接池以及spring的事务
 * @author wangyinbin
 *
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties
public class JooqSpringBootConfiguration {
	
	/**
	 * 另一种使用properties文件的方式
	 */
	@Value("${spring.datasource.url}")
	private String url;
	
	@Autowired
	private MasterDataSource masterDataSource;
	
	
	@Primary
    @Bean(name = "mDataSource")
    public DataSource masterDataSource() {
		DruidDataSource dataSource = new DruidDataSource();
	    dataSource.setDriverClassName(masterDataSource.getDriverClassName());
	    dataSource.setUrl(masterDataSource.getUrl());
	    dataSource.setUsername(masterDataSource.getUsername());
	    dataSource.setPassword(masterDataSource.getPassword());
	    dataSource.setMaxActive(20);
	    dataSource.setMaxWait(2000);
	    dataSource.setMinIdle(0);
	    dataSource.setTestOnBorrow(true);
	    dataSource.setTestWhileIdle(true);
	    dataSource.setInitialSize(1);
	    dataSource.setMinEvictableIdleTimeMillis(1000*60*10);
	    dataSource.setTimeBetweenEvictionRunsMillis(60*1000);
	    dataSource.setPoolPreparedStatements(true);
	    dataSource.setMaxPoolPreparedStatementPerConnectionSize(20);
	    dataSource.setValidConnectionChecker(new MySqlValidConnectionChecker());
	    return dataSource;
    }

    @Primary
    @Bean(name = "txManager")
    @DependsOn("mDataSource")
    public PlatformTransactionManager txManager(@Qualifier("mDataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Primary
    @Bean
    @DependsOn("txManager")
    public DSLContext getDSLContext(@Qualifier("mDataSource") DataSource dataSource,@Qualifier("txManager") DataSourceTransactionManager txMgr) throws Exception {
    	TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(dataSource);
	    org.jooq.Configuration configuration = new DefaultConfiguration()
	    	.set(new DataSourceConnectionProvider(proxy))
	    	.set(new SpringTransactionProvider(txMgr))
	    	.set(SQLDialect.MYSQL);
	    
	    return DSL.using(configuration);
    }
    
	
}
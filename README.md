##测试项目

*目前已经集成springboot、jooq、zipkin、swagger、druid等，之后会持续添加其他组件*
##使用方法
###jooq的使用
配置MYSQL的连接地址 

```
spring.datasource.master.driverClassName=com.mysql.jdbc.Driver
spring.datasource.master.url=jdbc:mysql://127.0.0.1:3306/pinpoint?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&zeroDateTimeBehavior=convertToNull&statementInterceptors=com.github.kristofa.brave.mysql.MySQLStatementInterceptor&zipkinServiceName=myDatabaseService
spring.datasource.master.username=root
spring.datasource.master.password=123456
```

使用jooq生成java代码
配置POM文件，并运行`mvn clean install -Djooq`
```
<profiles>
   <profile>
      <id>jooq</id>
      <properties />
      <activation>
         <property>
            <name>jooq</name>
         </property>
      </activation>
      <build>
         <plugins>
            <plugin>
               <groupId>org.jooq</groupId>
               <artifactId>jooq-codegen-maven</artifactId>
               <version>3.8.5</version>
               <executions>
                  <execution>
                     <goals>
                        <goal>generate</goal>
                     </goals>
                  </execution>
               </executions>
               <dependencies>
                  <dependency>
                     <groupId>mysql</groupId>
                     <artifactId>mysql-connector-java</artifactId>
                     <version>5.1.21</version>
                  </dependency>
               </dependencies>
               <configuration>
                  <jdbc>
                     <driver>com.mysql.jdbc.Driver</driver>
                     <url>jdbc:mysql://127.0.0.1:3306/pinpoint?useUnicode=true</url>
                     <user>root</user>
                     <password>123456</password>
                  </jdbc>
                  <generator>
                     <database>
                        <name>org.jooq.util.mysql.MySQLDatabase</name>
                        <includes>.*</includes>
                        <excludes />
                        <inputSchema>pinpoint</inputSchema>
                        <forcedTypes>
                           <forcedType>
                              <name>BOOLEAN</name>
                              <types>(?i:TINYINT(\s*\(\d+\))?(\s*UNSIGNED)?)</types>
                           </forcedType>
                        </forcedTypes>
                     </database>
                     <generate>
                        <deprecated>false</deprecated>
                     </generate>
                     <target>
                        <packageName>com.grb.indonesia.jooq.data.jooq</packageName>
                        <directory>/Users/wangyinbin/Documents/git_goopal/pay-indonesia/pay-indonesia-dao/src/main/java</directory>
                     </target>
                     <generate>
                        <pojos>false</pojos>
                        <daos>false</daos>
                     </generate>
                  </generator>
               </configuration>
            </plugin>
         </plugins>
      </build>
   </profile>
</profiles>
```

springboot与jooq集成

```
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
```
使用jooq操作数据库

```
@Override
    public void testDeclareTransaction() throws Exception{
        
        DefaultTransactionDefinition def = new DefaultTransactionDefinition(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        TransactionStatus transactionStatus = null;
        transactionStatus = txManager.getTransaction(def);
        try {
            Timestamp time = new Timestamp(new Date().getTime());
            dsl.insertInto(Tables.TEST_DATE,TestDate.TEST_DATE.ID,TestDate.TEST_DATE.DT).values(11L,time).execute();
            txManager.commit(transactionStatus);
        } catch (Exception e) {
            txManager.rollback(transactionStatus);
            System.out.println(e);
            throw new Exception("除0异常");
        }
    }
```
###swagger的使用
###zipkin的使用

下载openzipkin项目，打包运行
`java -jar zipkin.jar`
访问http://localhost:9411/查看是否成功

集成brave与zipkin
```
@Configuration
public class ZipkinConfig {

    @Autowired
    private ZipkinProperties properties;


    @Bean(name = "spanCollector")
    public SpanCollector spanCollector() {
        HttpSpanCollector.Config config = HttpSpanCollector.Config.builder().connectTimeout(properties.getConnectTimeout()).readTimeout(properties.getReadTimeout())
                .compressionEnabled(properties.isCompressionEnabled()).flushInterval(properties.getFlushInterval()).build();
        return HttpSpanCollector.create(properties.getUrl(), config, new EmptySpanCollectorMetricsHandler());
    }


    @Bean(name = "brave")
    @DependsOn("spanCollector")
    public Brave brave(@Qualifier("spanCollector") SpanCollector spanCollector){
        Brave.Builder builder = new Brave.Builder(properties.getServiceName());  //指定state
        builder.spanCollector(spanCollector);
        builder.traceSampler(Sampler.ALWAYS_SAMPLE);
        Brave brave = builder.build();
        return brave;
    }

    @Bean(name = "filter")
    @DependsOn("brave")
    public BraveServletFilter braveServletFilter(@Qualifier("brave") Brave brave){
        BraveServletFilter filter = new BraveServletFilter(brave.serverRequestInterceptor(),brave.serverResponseInterceptor(),new DefaultSpanNameProvider());
        return filter;
    }

    @Bean
    @DependsOn("brave")
    public OkHttpClient okHttpClient(@Qualifier("brave") Brave brave){
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new BraveOkHttpRequestResponseInterceptor(brave.clientRequestInterceptor(), brave.clientResponseInterceptor(), new DefaultSpanNameProvider()))
                .build();
        return client;
    }
    
    @Bean
    @DependsOn("brave")
    public MySQLStatementInterceptorManagementBean getBean(@Qualifier("brave") Brave brave){
        
        ClientTracer clientTrace;
        MySQLStatementInterceptorManagementBean bean = null;
        try {
            clientTrace = brave.clientTracer();
            bean = new MySQLStatementInterceptorManagementBean(clientTrace);
        } catch (Exception e) {
        }
        return bean;
    }
}
```





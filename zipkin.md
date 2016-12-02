#zipkin的使用

下载openzipkin项目，打包运行`java -jar zipkin.jar`

访问http://localhost:9411/查看是否成功

集成brave与zipkin
- POM依赖，按照需求选取合适的jar
    
    ```
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-core-spring</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-mysql</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-http</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-core</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-spancollector-http</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-web-servlet-filter</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-spring-web-servlet-interceptor</artifactId>
        <version>3.15.0</version>
    </dependency>
    <dependency>
        <groupId>io.zipkin.brave</groupId>
        <artifactId>brave-okhttp</artifactId>
        <version>3.15.0</version>
    </dependency>
    ```

- 集成springboot与brave

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
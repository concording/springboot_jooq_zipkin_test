#zipkin的使用

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

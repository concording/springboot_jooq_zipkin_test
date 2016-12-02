#swaggerUI的使用
引入相关的jar包
```
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
</dependency>
```
swagger的配置，包括swagger扫描的package
```
@Configuration
public class SwaggerDocumentationConfig {

    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("测试接口")
                .description("")
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .termsOfServiceUrl("")
                .version("1.0.0")
                .contact(new Contact("GoopalPay","zhangjing@goopal.com", "zhangjing@goopal.com"))
                .build();
    }

    @Bean
    public Docket customImplementation(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.grb.indonesia.access"))
                .build()
                .directModelSubstitute(org.joda.time.LocalDate.class, java.sql.Date.class)
                .directModelSubstitute(org.joda.time.DateTime.class, java.util.Date.class)
                .apiInfo(apiInfo());
    }

}
```
配置完成之后在APP.java里面增加注解`@EnableSwagger2`

配置完成之后就可以在controller里面使用了，使用方式大致如下
```
@RestController
@Api(value = "Demo", description = "测试 API", tags = {"Demo"})
public class DemoCtl extends AbstractCtl{

    @ApiOperation(value = "zipkin测试", notes = "", response = EchoRsp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = EchoRsp.class)})
    @RequestMapping(value="/start", method = RequestMethod.POST)
    public String start() throws InterruptedException, IOException {
        System.out.println("access start method");
        int sleep= random.nextInt(100);
        TimeUnit.MILLISECONDS.sleep(sleep);
        Request request = new Request.Builder().url("http://localhost:9090/echo").get().build();
        Response response = client.newCall(request).execute();
        return " [service1 sleep " + sleep+" ms]" + response.body().toString();
    }
}
```

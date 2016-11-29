package com.grb.indonesia.access.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.grb.indonesia.access.params.EchoReq;
import com.grb.indonesia.access.params.EchoRsp;
import com.grb.indonesia.service.UserService;

@RestController
@Api(value = "Demo", description = "测试 API", tags = {"Demo"})
public class DemoCtl extends AbstractCtl{

	@Autowired
	private UserService userService;
	
	private  Random random = new Random();
	
	@Autowired
    private OkHttpClient client;
	
    @ApiOperation(value = "回音测试", notes = "", response = EchoRsp.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = EchoRsp.class)})
    @RequestMapping(value="/echo", method = RequestMethod.POST, produces = "application/json")
    public EchoRsp echo(@ApiParam(value = "回音服务请求参数",required = true) @RequestBody EchoReq echoReq){
    	
        this.getLogger().info("{}", echoReq);
        try {
        	
        	userService.testAnnotationTransaction();
		} catch (Exception e) {
		}
        return EchoRsp.DEFAULT();
    }
    
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
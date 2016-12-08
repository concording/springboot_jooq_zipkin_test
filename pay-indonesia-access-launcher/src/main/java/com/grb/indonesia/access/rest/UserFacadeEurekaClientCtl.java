package com.grb.indonesia.access.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grb.indonesia.access.params.EchoRsp;
import com.grb.indonesia.service.UserService;

@EnableEurekaClient
@RestController
@Api(value = "eureka", description = "测试eureka API", tags = {"eureka"})
public class UserFacadeEurekaClientCtl{

	@Autowired UserService uservice;
	
	@ApiOperation(value = "测试eureka", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = EchoRsp.class)})
    @RequestMapping(value="/testEureka")
	public String echo() {
		
			//uservice.testAnnotationTransaction();
			//System.out.println("test Eureka");
			return "hello world";
	}
}

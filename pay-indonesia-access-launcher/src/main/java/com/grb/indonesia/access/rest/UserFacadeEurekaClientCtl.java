package com.grb.indonesia.access.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grb.indonesia.access.params.EchoRsp;
import com.grb.indonesia.service.UserService;

@RestController
@Api(value = "eureka", description = "测试eureka API", tags = {"eureka"})
public class UserFacadeEurekaClientCtl extends AbstractCtl{

	@Autowired UserService uservice;
	
	@ApiOperation(value = "测试eureka", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success", response = EchoRsp.class)})
    @RequestMapping(value="/testEureka")
	public String echo() {
		
		System.out.println("hello world ! welcome to pay-indonesia");
		try {
			uservice.testDeclareTransaction();
		} catch (Exception e) {
			this.getLogger().error("来自客户端的调用失败了",e);
		}
		//System.out.println("test Eureka");
		return "hello world ! welcome to pay-indonesia";
	}
}

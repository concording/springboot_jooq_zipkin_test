package com.grb.indonesia.access.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.grb.indonesia.service.UserService;

@Service(version="1.0.0")
public class UserExporter {

	@Autowired UserService userService;
	
}

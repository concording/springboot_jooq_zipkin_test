package com.grb.indonesia.access.rest;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.grb.indonesia.api.UserExporterService;
import com.grb.indonesia.service.UserService;

@Service
public class UserFacade implements UserExporterService{

	@Autowired UserService uservice;
	
	@Override
	public void echo() {
		
		try {
			uservice.testAnnotationTransaction();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

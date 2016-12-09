package com.grb.indonesia.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface UserService {

	@Transactional
	public List<?> queryAll() throws Exception ;
	
	@Transactional(rollbackFor=Exception.class)
	public void testAnnotationTransaction() throws Exception;
	
	public boolean testDeclareTransaction() throws Exception;
}

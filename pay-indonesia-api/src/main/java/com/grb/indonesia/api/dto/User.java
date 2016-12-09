package com.grb.indonesia.api.dto;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = -1858738192569642284L;
	private String username;
	private int age;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}

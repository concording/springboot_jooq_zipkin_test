package com.grb.indonesia.entity;

import java.io.Serializable;
import java.util.Date;


public class DateTest implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -27099228030732651L;
	private int id;
	private Date date;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}

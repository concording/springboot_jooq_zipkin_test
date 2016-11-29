package com.grb.indonesia.test;


public class TestJdbcDriveManager {

	public static void main(String[] args) {
		new TestJdbcDriveManager().r1.run();
	    new TestJdbcDriveManager().r2.run();
		
	}
	Runnable r1 = () -> { System.out.println(this); };
	Runnable r2 = () -> { System.out.println(toString()); };

	public String toString() { return "Hello, world!"; }
}

package com.wj.base.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionFactory {
	private static String driver;
	private static String dburl;
	private static String user;
	private static String password;
	
	private static final ConnectionFactory factory = new ConnectionFactory();
	
	private Connection conn;
	
	static{
		Properties prop = new Properties();
		try {
			InputStream in = ConnectionFactory.class.getClassLoader().getResourceAsStream("dbconfig.properties");
			prop.load(in);
		}catch(Exception e){
			
			System.out.println("======配置信息读取错误=====");
			
		}
		driver = prop.getProperty("driver");
		dburl = prop.getProperty("dburl");
		user = prop.getProperty("user");
		password = prop.getProperty("password");
		
		System.out.println("======配置信息读取正确=====");
		
	}
	private ConnectionFactory(){
		
	}
	
	public static ConnectionFactory getInstance(){
		System.out.println("======getInstance=====");
		return factory;
	}
	
	public Connection makeConnection(){
		try {
    		Class.forName(driver);
//			conn = DriverManager.getConnection("jdbc:mysql://127.3.65.130:3306/good","adminXWhfEjP","xiK1_8lvsT6G");
    		conn = DriverManager.getConnection(dburl,user,password);

    	}catch(Exception e){
		//	aa = e.toString();
    		e.printStackTrace();
		}finally {
			
		}
		return conn;
	}

}

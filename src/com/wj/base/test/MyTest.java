package com.wj.base.test;

import java.sql.Connection;

import com.wj.base.util.ConnectionFactory;

public class MyTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("======³ÌÐò¿ªÊ¼=====");
		ConnectionFactory cf =ConnectionFactory.getInstance();
		Connection conn = cf.makeConnection();
		System.out.println(conn.getAutoCommit());
	}

}

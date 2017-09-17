package com.wj.base.test;

import java.sql.Connection;

import com.wj.base.dao.UserDao;
import com.wj.base.entity.User;
import com.wj.base.impl.UserDaoImpl;
import com.wj.base.util.ConnectionFactory;

public class UserDaoTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
			conn.setAutoCommit(false);
			
			UserDao userDao = new UserDaoImpl();
			User tom = new User();
			tom.setName("Tom");
			tom.setPassword("1234");
			tom.setEmail("eee@sina.com");
			
			userDao.save(conn, tom);
			conn.commit();
			
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
	
		
		

	}

}

package com.wj.base.service;

import java.sql.Connection;
import java.sql.ResultSet;

import com.wj.base.dao.UserDao;
import com.wj.base.impl.UserDaoImpl;
import com.wj.base.jdbc.JDBClink;
import com.wj.base.entity.User;;

public class CheckUserService {

	private UserDao userDao = new UserDaoImpl();
	
	public boolean check(User user){
		Connection conn = null;
		try {
			conn = JDBClink.getConnection();
			conn.setAutoCommit(false);
			ResultSet resultSet = userDao.get(conn, user);
			while (resultSet.next()){
				return true;
			}
			
		} catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2){
				e2.printStackTrace();
				
			}
			
		} finally {
			try {
				conn.close();
			} catch (Exception e3){
				e3.printStackTrace();
			}
		}
		return false;
	}
}

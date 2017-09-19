package com.wj.db.dao.service;

import java.sql.Connection;
import java.sql.ResultSet;

import com.wj.db.dao.entity.User;
import com.wj.db.dao.impl.UserDaoImpl;
import com.wj.db.dao.inface.UserDaoInface;
import com.wj.db.dao.util.ConnectionFactory;

public class UserService {

	private UserDaoInface userDao = new UserDaoImpl();
	
	public boolean check(User user){
		Connection conn = null;
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
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
	
	
	public boolean save(User user){
		Connection conn = null;
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
			conn.setAutoCommit(false);

			UserDaoInface userDao = new UserDaoImpl();
			userDao.save(conn, user);
			conn.commit();

			
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

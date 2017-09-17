package com.wj.base.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wj.base.dao.UserDao;
import com.wj.base.entity.User;;

public class UserDaoImpl implements UserDao {

	/**
	 * ������Ϣ
	 */
	@Override
	public void save(Connection conn,User user) throws SQLException{
		// TODO Auto-generated method stub
		String SQLstr = "INSERT INTO user(name,passwd,email) values (?,?,?)";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(SQLstr);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * ������Ϣ
	 */
	@Override
	public void update(Connection conn,User user) throws SQLException {
		// TODO Auto-generated method stub
		String SQLstr = "UPDATE user SET name=?,passwd=?,email=? WHERE id=?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(SQLstr);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmail());
			ps.setLong(4, user.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * ɾ����Ϣ
	 */
	@Override
	public void delete(Connection conn,User user) throws SQLException {
		// TODO Auto-generated method stub
		String SQLstr = "DELETE FROM user WHERE id=?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(SQLstr);
			ps.setLong(1, user.getId());
			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/**
	 * ��ȡ��¼
	 */
	@Override
	public ResultSet get(Connection conn,User user) throws SQLException {
		// TODO Auto-generated method stub
		String SQLstr = "SELECT * FROM user WHERE name=? AND passwd=?";
		PreparedStatement ps;
		try {
			ps = conn.prepareStatement(SQLstr);
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			return ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}

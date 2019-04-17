package com.wj.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.wj.db.dao.entity.Jsapi_ticket;
import com.wj.db.dao.entity.User;
import com.wj.db.dao.intface.DaoIntface;

public class ticketDaoImpl implements DaoIntface {

	/**
	 * 保存信息
	 */
	@Override
	public void save(Connection conn,Object obj) throws SQLException{
		// TODO Auto-generated method stub
		Jsapi_ticket jsapi_ticket = (Jsapi_ticket ) obj;
		String SQLstr = "INSERT INTO jsapi_ticket(ticket,start_time,end_time) values (?,?,?)";
		PreparedStatement ps;
		try {
			
			ps = conn.prepareStatement(SQLstr);
			ps.setString(1, jsapi_ticket.getTicket());
			ps.setTimestamp(2, jsapi_ticket.getStart_time());
			ps.setTimestamp(3, jsapi_ticket.getEnd_time());

			ps.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	/**
	 * 更新信息
	 */
	@Override
	public void update(Connection conn,Object obj) throws SQLException {
		// TODO Auto-generated method stub
		User user = (User ) obj;
		String SQLstr = "UPDATE the_user SET name=?,passwd=?,email=? WHERE id=?";
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
	 * 删除信息
	 */
	@Override
	public void delete(Connection conn,Object obj) throws SQLException {
		// TODO Auto-generated method stub
		User user = (User ) obj;
		String SQLstr = "DELETE FROM the_user WHERE id=?";
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
	 * 获取记录
	 */
	@Override
	public ResultSet get(Connection conn,Object obj) throws SQLException {
		// TODO Auto-generated method stub
		User user = (User ) obj;
		String SQLstr = "SELECT * FROM jsapi_ticket WHERE name=? AND passwd=?";
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

	/**
	 * 查询记录
	 */
	@Override
	public ArrayList get_where(Connection conn, String where) throws SQLException {
		// TODO Auto-generated method stub
		ArrayList<Jsapi_ticket> list = new ArrayList<Jsapi_ticket>();
		
		String SQLstr = "SELECT ticket,start_time,end_time FROM jsapi_ticket order by start_time desc ";
		PreparedStatement ps;
		ResultSet rs;
		
		try {
			ps = conn.prepareStatement(SQLstr);
			rs = ps.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			rs = null;
		}
			
		if ( rs != null )
		{
			while (rs.next())
			{
				Jsapi_ticket a_ticket = new Jsapi_ticket();
				a_ticket.setTicket( (String) rs.getObject("ticket") );
				a_ticket.setStart_time( rs.getTimestamp("start_time") );
				a_ticket.setEnd_time( rs.getTimestamp("end_time") );
				list.add( a_ticket);
				// 取第一个就立刻return ，否则不return
				return list;
			}
		}
		
		return  list;
	}

}

package com.wj.db.dao.inface;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.wj.db.dao.entity.User;;

public interface UserDaoInface {
	
	public void save(Connection conn ,User user) throws SQLException  ;
	public void update(Connection conn ,User user) throws SQLException  ;
	public void delete(Connection conn ,User user) throws SQLException  ;
	public ResultSet get(Connection conn ,User user) throws SQLException  ;

}

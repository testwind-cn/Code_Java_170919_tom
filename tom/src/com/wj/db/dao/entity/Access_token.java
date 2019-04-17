package com.wj.db.dao.entity;

import java.sql.Timestamp;

public class Access_token extends IdEntity {

	private String token;
	private Timestamp start_time,end_time;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getStart_time() {
		return start_time;
	}
	
	public Timestamp getEnd_time() {
		return end_time;
	}

	public void setStart_time(Timestamp start_time) {
		if ( start_time == null)
		{
			java.util.Date date=new java.util.Date();
			start_time = new Timestamp(date.getTime());
		};
		this.start_time = start_time;
	}
	
	public void setEnd_time(Timestamp end_time) {
		if ( end_time == null)
		{
			java.util.Date date=new java.util.Date();
			end_time = new Timestamp(date.getTime());
		};
		this.end_time = end_time;
	}
	

	@Override
	public String toString() {
		return "access_token [token=" + token + ", start_time=" + start_time + ", end_time=" + end_time + ", id=" + id + "]";
	}

}

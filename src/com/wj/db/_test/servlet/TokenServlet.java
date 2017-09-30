package com.wj.db._test.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wj.db.dao.service.TokenService;
import com.wj.wechat.service.JSSDK;
import com.wj.wechat.service.JSSDK_data;


public class TokenServlet extends HttpServlet {
	


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=wx66dded684c14bfd1&secret=e5f1a2b154a810183f1e3032eb289c98
		
//		TokenService ts = new TokenService();
		
//		String aa = ts.RefreshToken();
		
		


		JSSDK_data dat;
		JSSDK dd = new JSSDK();
		dat = dd.getSignPackage(req);
		if ( dat != null )
		{
			System.out.println(dat.getSignature());
		}
		
		PrintWriter out = resp.getWriter();  
	    out.println("<html>");  
	    out.println("<head>");  
	    out.println("<title>Servlet Pattern</title>");  
	    out.println("</head>");  
	    out.println("<body>");  
	    out.println("get URI = " + req.getRequestURI() + "<br>");  
	    out.println("Context Path = " + req.getContextPath() + "<br>");  
	    out.println("Servlet Path = " + req.getServletPath() + "<br>");  
	    out.println("Path Info = " + req.getPathInfo() + "<br>");
	    out.println("URL = " + req.getRequestURL() + "<br>");  
	    out.println("query = " + req.getQueryString() + "<br>");
	    out.println("Host = " + req.getServerName() + "<br>");
	    out.println("Port = " + req.getServerPort() + "<br>");
	    out.println("Protocol = " + req.getProtocol() + "<br>");
	    
	    
	    
	    	    
	    
	    
	    out.println("</body>");  
	    out.println("</html>");  
	    out.close();
	}
	
	


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		super.doGet(req, resp);
		doPost(req,resp);
		
		
	}

}

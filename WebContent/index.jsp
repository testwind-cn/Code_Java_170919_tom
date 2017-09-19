<%@ page language="java"  import="java.sql.*,com.wj.fin.calValue.*,java.net.InetAddress" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>这里是标题</title>
</head>
<body>


OK 第一个程序
<%  TotalScedule s=new TotalScedule(); 

s.calPeriodAmount(12000, 0.18, 24, 0);
String ss = s.echoTable();
out.println(ss);

String addr = InetAddress.getLocalHost().getHostName();//获得本机IP  
String name = InetAddress.getLocalHost().getHostAddress();
out.println( name+" is "+addr);

%>


		
</body>
</html>
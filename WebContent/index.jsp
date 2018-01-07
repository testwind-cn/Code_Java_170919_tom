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

// s.calPeriodAmount(10000, 0.18, 6, 0);

// s.calPeriodAmount(10000, 0.18, 6, 0);

// calPeriodAmount( double all_loan ,double real_rate, int total_Period, int period_days, String start_date, int t_period_days_array[])


int t_period_days_array[] = { 25,31,28,31,30,31};
//s.calPeriodAmount( 10000 ,0.213, 6, -1, "2017-12-16", t_period_days_array);
s.calPeriodAmount( 10000 ,0.213, 120, 0, "2017-12-16", null);



//int t_period_days_array[] = { 22,31,28,31,30,31};
//s.calPeriodAmount( 100000 ,0.5, 120, 0, "2017-12-16", null );//t_period_days_array);
//s.calPeriodAmount( 10000 ,0.2197, 6, -1, "2017-12-16", t_period_days_array);


String ss = s.echoTable();
out.println(ss);

String addr = InetAddress.getLocalHost().getHostName();//获得本机IP  
String name = InetAddress.getLocalHost().getHostAddress();
out.println( name+" is "+addr);

%>


		
</body>
</html>
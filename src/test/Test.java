/**
 * 
 */
package test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import cal.TotalScedule;
/**
 * @author DEV
 *
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		TotalScedule s = new TotalScedule();
		s.calPeriodAmount(12000, 0.18, 24, 0);
		String ss = s.echoTable();
		System.out.print(ss);
		
		
		double datediff =  27.9833;
    	datediff = wjutil.Tools.round(datediff, 0);
    	System.out.println(datediff);
    	System.out.println((int) datediff);
    	
    	
    	
		  SimpleDateFormat f1 = new SimpleDateFormat("yyyy-MM-dd");
		  f1.setTimeZone(java.util.TimeZone.getTimeZone("Africa/Addis_Ababa"));
		  try {
			 Date dd = f1.parse("2015-12-12");
		    System.out.println(dd);
		    f1.setTimeZone(java.util.TimeZone.getTimeZone("Asia/Shanghai"));
		    Date dd2 = f1.parse("2015-12-12");
//		    dd2.setTime(dd.getTime());
		    
		    System.out.println(dd2);

		  } catch (ParseException e) {
		    e.printStackTrace();
		  }

		
	}

}

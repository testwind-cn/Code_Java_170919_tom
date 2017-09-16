/**
 * 
 */
package cal;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DEV
 *
 */
public class PeriodAmount 
{
    private int data_period_num = 0;
    private Date data_start_date;// = date_create();       // 贷款首期借款日期
    private Date data_period_date;// = date_create();      // 贷款本期还款日期
    private double data_period_principal = 0;              // 本期总欠本金
    private int data_due_days = 0;                         // 本期借款天数
    private double data_due_principal = 0;                 // 本期应还本金
    private double data_due_interest_real = 0.0;           // 本期应还利息_原始小数
    private double data_due_interest = 0.0;                // 本期应还利息_取整
    private double data_due_amount = 0;                    // 本期应还本息
    private double data_z_1_B = 1;                         // 本期本息率 = 1 + 年率 /360* 本期天数
    private double data_z_pai = 1;                         // <本息率>连乘积
    
    public PeriodAmount()
    {
        data_start_date = new Date();   
        data_period_date = new Date();    
    }
    
    public void setPeriodPrincipal(double principal)
    { // 设置本期总欠款本金
        data_period_principal = principal;
    }
    
    public double getNextPeriodPrincipal()
    { // 计算下期总欠款本金
        return data_period_principal - data_due_principal;
    }
    
    public void cal_principal_interest(double new_principal, double rate, double per_amount_round)
    { // 计算本期应还本金、利息、剩余本金。
    	new_principal = wjutil.Tools.round(new_principal,4); // 每期剩余本金不可能为长小数，把末尾小数去除

    	data_period_principal = new_principal;                          // 本期总欠本金
        data_due_interest_real = new_principal * data_due_days * rate / 360.0;  // 本期精确应还利息取整

        
        // PHP BEGIN
        // double new_interest_round = round( new_interest, 2, PHP_ROUND_HALF_UP );     // 本期应还利息取整
        // PHP END
        
        // JAVA BEGIN
        //BigDecimal bg = new BigDecimal(data_due_interest_real);
        //double new_interest_round = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // Java END
        
        data_due_interest = wjutil.Tools.round(data_due_interest_real,2);  // 本期应还利息取整
        data_due_principal = wjutil.Tools.round( per_amount_round - data_due_interest,2 );
        
        if ( data_due_principal > data_period_principal ) // 修正本期应还本金，如果应还本金，大于剩余本金，就是错误，改为剩余本金。
        {
            data_due_principal = data_period_principal;
        }
        data_due_amount = wjutil.Tools.round( data_due_principal + data_due_interest, 2);
    }
    
    public void cal_last_period_due_principal()
    { // 修正最后一期应还本金，如果没还完本金，全部归还。
        if ( data_due_principal < data_period_principal )
        {
            data_due_principal = data_period_principal;
        }

        data_due_amount = wjutil.Tools.round(data_due_principal + data_due_interest,2);
    }
    
    public void setPeriodDate(Date start_date, int x)
    {
    	setPeriodDate( start_date, x, 0);
    }
    public void setPeriodDate(Date start_date, int x, int period_days)
    {
    	setPeriodDate( start_date, x, period_days, null);    	
    }
    public void setPeriodDate(Date start_date, int x, int period_days, Object period_days_array)
    {
    	Date period_date=new Date();
    	
        data_period_num = x;
        
        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Calendar newCal = java.util.Calendar.getInstance();
        newCal.setTime(start_date);//使用给定的 Date 设置此 Calendar 的时间。 
        
        if ( period_days == 0 ) // 如果是0，则是按月还，间隔为x月.
        {
            newCal.add(java.util.Calendar.MONTH, x);// 日期加x个月
            period_date = newCal.getTime();//返回一个表示此 Calendar 时间值的 Date 对象。
            
           // i = new DateInterval("P".$x."M");
        } 
        else if ( period_days < 0 ) // 需要采用后面的实际天数数组
        {
            
        } else
        {
        	newCal.add(java.util.Calendar.DATE, x * period_days);// 日期加x个天
            period_date = newCal.getTime();//返回一个表示此 Calendar 时间值的 Date 对象。
           // i = new DateInterval("P".$xd."D");
        }
        
        data_start_date  = start_date;//date_create_from_format("Y-m-d H:i:s",date_format($start_date,"Y-m-d 00:00:00"));
        data_period_date = period_date; //date_create_from_format("Y-m-d H:i:s",date_format($start_date,"Y-m-d 00:00:00"));
        

    //    echo date_format($date,"Y/m/d")."<br>";
        data_due_days = (int) ( ( data_period_date.getTime() - data_start_date.getTime() ) / (1000*3600*24) );  //这个没用了，后面需要重新 FixDueDays
    }
    
    public Date getPeriodDate()
    { // 获取本期还款日的一个副本
        Date the_date= new Date();
        the_date.setTime(data_period_date.getTime());
        return the_date;
    }
    
    public void fixDueDays(Date the_date)
    { // 计算本期还款日和某日期（上期还款日）间隔的天数
    	double datediff =  ( data_period_date.getTime() - the_date.getTime() );
    	datediff = datediff / (1000*3600*24);
    	datediff = wjutil.Tools.round(datediff, 0);
    	data_due_days = (int) datediff;
    }
    
    public void fix_z_1_B(double real_rate)
    {
    	data_z_1_B = 1 + data_due_days * real_rate / 360.0;
    }
    
    public double get_z_pai()
    {
    	return data_z_pai; 
    }  
    public void set_z_pai(double mult_pai)
    {
    	data_z_pai = data_z_1_B * mult_pai;
    }
    
    public String echoData()
    {
        //echo date_default_timezone_get();
    	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
    	
    	String s =
        "        <td>"+data_period_num+"</td>\n"+
        "        <td>"+fmt.format(data_start_date)+"</td>\n"+
        "        <td>"+fmt.format(data_period_date)+"</td>\n"+
        "        <td>"+data_period_principal+"</td>\n"+
        "        <td>"+data_due_days+"</td>\n"+
        "        <td>"+data_due_principal+"</td>\n"+
        "        <td>"+data_due_interest+"</td>\n"+
        "        <td>"+data_due_amount+"</td>\n"+
        "        <td>"+data_z_1_B+"</td>\n"+
        "        <td>"+data_z_pai+"</td>\n"+
        "\n";
    	
    	return s;
    }
}
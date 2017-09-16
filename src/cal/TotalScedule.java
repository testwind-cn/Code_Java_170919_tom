/**
 * 
 */
package cal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author DEV
 *
 */
public class TotalScedule
{
    private double d1_all_loan = 12000;
    private double d2_real_rate = 0.18;
    private int d3_total_Period = 36;
    private int d4_period_days = 0;  // 0=按自然月还， 1-365=按天数周期还，-1=按后面的还款天表
    private double d6_period_amount = 0.0;
    private double d6_period_amount_round = 0;
    private Date d5_start_date;
    private int period_days_array[] = null;
    private PeriodAmount[] periodAmounts = null;//array();

    /**
     * 获取现在时间
     *
     * @return返回短时间格式 yyyy-MM-dd
     */
    private static Date getDateShort() 
    {
    	return getDateShort(null);    	
    }

	private static Date getDateShort(String s)
	{
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = null;

		if (s == null || s.length() <= 0) {
			dateString = formatter.format(currentTime);
		} else
			dateString = s;
		
		// ParsePosition pos = new ParsePosition(8);
		Date resultDate = null;
		try {
			resultDate = formatter.parse(dateString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			dateString = formatter.format(currentTime);
			try {
				resultDate = formatter.parse(dateString);
			}
			catch (ParseException e2) {
				resultDate = currentTime;
			}
		}
		
		return resultDate;
	}
    
    public void calPeriodAmount( double all_loan ,double real_rate, int total_Period)
    {
    	calPeriodAmount( all_loan ,real_rate, total_Period,0,null,null);    	
    }
    public void calPeriodAmount( double all_loan ,double real_rate, int total_Period, int period_days)
    {
    	calPeriodAmount( all_loan ,real_rate, total_Period,period_days,null,null);
    }
    public void calPeriodAmount( double all_loan ,double real_rate, int total_Period, int period_days, String start_date)
    {
    	calPeriodAmount( all_loan ,real_rate, total_Period,period_days,start_date,null);	
    }
    public void calPeriodAmount( double all_loan ,double real_rate, int total_Period, int period_days, String start_date, int t_period_days_array[])
    {
        if ( all_loan < 0 ) { all_loan = 0; }
        if ( real_rate< 0 ) { real_rate = 0; }
        if ( total_Period < 1 ) { total_Period = 1; }
        
        d1_all_loan = all_loan;
        d2_real_rate = real_rate;
        d3_total_Period = total_Period; // 不能小于1
        d4_period_days = period_days; // 0=按自然月还， 1-365=按天数周期还，-1=按后面的还款天表
        period_days_array = t_period_days_array;
        
        // date_default_timezone_set("Asia/Shanghai");
        
        // $date=date_create(date("Y-m-d")); // "Y-m-d 2017-01-09 Y n j 2017-1-9" // date_date_set($date,2020,10,15);
        Date a_date = getDateShort(start_date);  
        
        if ( a_date == null )
        { // 如果没有传递开始日期，或者错误的开始日期，则设置当前日期为开始日期
        	a_date=getDateShort(null); // "Y-m-d 2017-01-09 Y n j 2017-1-9" // date_date_set($date,2020,10,15);
        }
        
        d5_start_date = a_date;
        
        periodAmounts = null;
        periodAmounts = new PeriodAmount[d3_total_Period+2]; // 其实 d3_total_Period+1就足够了
        
        periodAmounts[0] = new PeriodAmount();
        periodAmounts[0].setPeriodDate(d5_start_date,0);
        periodAmounts[0].setPeriodPrincipal(d1_all_loan);
        
        for (int x=1; x <= d3_total_Period; x++) 
        {
            periodAmounts[x] = new PeriodAmount();
            periodAmounts[x].setPeriodDate(d5_start_date, x, d4_period_days, period_days_array);       // 赋值借款日和本期还款日、本期期数
            periodAmounts[x].fixDueDays(periodAmounts[x-1].getPeriodDate()); // 修正本期天数
            periodAmounts[x].fix_z_1_B(d2_real_rate);
        }
        
        periodAmounts[d3_total_Period+1] = new PeriodAmount(); // 多生成一个，data_due_z_1_B = 1；
        
        double sum_z_pai = 0; // 从 2 到 第 25 个 z_pai 求和
        
        for (int x=d3_total_Period; x >= 1; x--) 
        {
        	double mult_pai = periodAmounts[x+1].get_z_pai();
            periodAmounts[x].set_z_pai( mult_pai );
            sum_z_pai = sum_z_pai + mult_pai; // 从 2 到 第 25 个 z_pai 求和
        }
        
        double first_z_pai = this.periodAmounts[1].get_z_pai(); //  第1 个 z_pai
        d6_period_amount = d1_all_loan * first_z_pai / sum_z_pai; // 求精确月供
//        d6_period_amount_round = round( d6_period_amount, 2, PHP_ROUND_HALF_UP ); // 求四舍五入到分月供
        
       // JAVA BEGIN
        //BigDecimal bg = new BigDecimal(d6_period_amount);
        //d6_period_amount_round = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        d6_period_amount_round = wjutil.Tools.round(d6_period_amount,2);
        // Java END
        
        
        for (int x=1; x <= this.d3_total_Period; x++) {
            periodAmounts[x].cal_principal_interest(periodAmounts[x-1].getNextPeriodPrincipal(),d2_real_rate,d6_period_amount_round);
        }
        
        periodAmounts[d3_total_Period].cal_last_period_due_principal();

    }

    public String echoTable()
    {
        String s = "<table border=1 cellspacing=0 cellpadding=0>\n";
        for (int x=0; x <= d3_total_Period+1; x++) {
            s = s + "    <tr>\n";
            if ( periodAmounts != null && x < periodAmounts.length && periodAmounts[x] != null )
            {
                s = s + periodAmounts[x].echoData();
            }
            else
            {
                s = s + "    <td>no data</td>\n";
            }
            s = s + "    </tr>\n";
        }
        s = s + "</table>\n";
        return s;
    }
}

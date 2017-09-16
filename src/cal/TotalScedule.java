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
    private int d4_period_days = 0;  // 0=����Ȼ�»��� 1-365=���������ڻ���-1=������Ļ������
    private double d6_period_amount = 0.0;
    private double d6_period_amount_round = 0;
    private Date d5_start_date;
    private int period_days_array[] = null;
    private PeriodAmount[] periodAmounts = null;//array();

    /**
     * ��ȡ����ʱ��
     *
     * @return���ض�ʱ���ʽ yyyy-MM-dd
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
        d3_total_Period = total_Period; // ����С��1
        d4_period_days = period_days; // 0=����Ȼ�»��� 1-365=���������ڻ���-1=������Ļ������
        period_days_array = t_period_days_array;
        
        // date_default_timezone_set("Asia/Shanghai");
        
        // $date=date_create(date("Y-m-d")); // "Y-m-d 2017-01-09 Y n j 2017-1-9" // date_date_set($date,2020,10,15);
        Date a_date = getDateShort(start_date);  
        
        if ( a_date == null )
        { // ���û�д��ݿ�ʼ���ڣ����ߴ���Ŀ�ʼ���ڣ������õ�ǰ����Ϊ��ʼ����
        	a_date=getDateShort(null); // "Y-m-d 2017-01-09 Y n j 2017-1-9" // date_date_set($date,2020,10,15);
        }
        
        d5_start_date = a_date;
        
        periodAmounts = null;
        periodAmounts = new PeriodAmount[d3_total_Period+2]; // ��ʵ d3_total_Period+1���㹻��
        
        periodAmounts[0] = new PeriodAmount();
        periodAmounts[0].setPeriodDate(d5_start_date,0);
        periodAmounts[0].setPeriodPrincipal(d1_all_loan);
        
        for (int x=1; x <= d3_total_Period; x++) 
        {
            periodAmounts[x] = new PeriodAmount();
            periodAmounts[x].setPeriodDate(d5_start_date, x, d4_period_days, period_days_array);       // ��ֵ����պͱ��ڻ����ա���������
            periodAmounts[x].fixDueDays(periodAmounts[x-1].getPeriodDate()); // ������������
            periodAmounts[x].fix_z_1_B(d2_real_rate);
        }
        
        periodAmounts[d3_total_Period+1] = new PeriodAmount(); // ������һ����data_due_z_1_B = 1��
        
        double sum_z_pai = 0; // �� 2 �� �� 25 �� z_pai ���
        
        for (int x=d3_total_Period; x >= 1; x--) 
        {
        	double mult_pai = periodAmounts[x+1].get_z_pai();
            periodAmounts[x].set_z_pai( mult_pai );
            sum_z_pai = sum_z_pai + mult_pai; // �� 2 �� �� 25 �� z_pai ���
        }
        
        double first_z_pai = this.periodAmounts[1].get_z_pai(); //  ��1 �� z_pai
        d6_period_amount = d1_all_loan * first_z_pai / sum_z_pai; // ��ȷ�¹�
//        d6_period_amount_round = round( d6_period_amount, 2, PHP_ROUND_HALF_UP ); // ���������뵽���¹�
        
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

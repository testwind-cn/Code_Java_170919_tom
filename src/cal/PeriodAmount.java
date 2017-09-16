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
    private Date data_start_date;// = date_create();       // �������ڽ������
    private Date data_period_date;// = date_create();      // ����ڻ�������
    private double data_period_principal = 0;              // ������Ƿ����
    private int data_due_days = 0;                         // ���ڽ������
    private double data_due_principal = 0;                 // ����Ӧ������
    private double data_due_interest_real = 0.0;           // ����Ӧ����Ϣ_ԭʼС��
    private double data_due_interest = 0.0;                // ����Ӧ����Ϣ_ȡ��
    private double data_due_amount = 0;                    // ����Ӧ����Ϣ
    private double data_z_1_B = 1;                         // ���ڱ�Ϣ�� = 1 + ���� /360* ��������
    private double data_z_pai = 1;                         // <��Ϣ��>���˻�
    
    public PeriodAmount()
    {
        data_start_date = new Date();   
        data_period_date = new Date();    
    }
    
    public void setPeriodPrincipal(double principal)
    { // ���ñ�����Ƿ���
        data_period_principal = principal;
    }
    
    public double getNextPeriodPrincipal()
    { // ����������Ƿ���
        return data_period_principal - data_due_principal;
    }
    
    public void cal_principal_interest(double new_principal, double rate, double per_amount_round)
    { // ���㱾��Ӧ��������Ϣ��ʣ�౾��
    	new_principal = wjutil.Tools.round(new_principal,4); // ÿ��ʣ�౾�𲻿���Ϊ��С������ĩβС��ȥ��

    	data_period_principal = new_principal;                          // ������Ƿ����
        data_due_interest_real = new_principal * data_due_days * rate / 360.0;  // ���ھ�ȷӦ����Ϣȡ��

        
        // PHP BEGIN
        // double new_interest_round = round( new_interest, 2, PHP_ROUND_HALF_UP );     // ����Ӧ����Ϣȡ��
        // PHP END
        
        // JAVA BEGIN
        //BigDecimal bg = new BigDecimal(data_due_interest_real);
        //double new_interest_round = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        // Java END
        
        data_due_interest = wjutil.Tools.round(data_due_interest_real,2);  // ����Ӧ����Ϣȡ��
        data_due_principal = wjutil.Tools.round( per_amount_round - data_due_interest,2 );
        
        if ( data_due_principal > data_period_principal ) // ��������Ӧ���������Ӧ�����𣬴���ʣ�౾�𣬾��Ǵ��󣬸�Ϊʣ�౾��
        {
            data_due_principal = data_period_principal;
        }
        data_due_amount = wjutil.Tools.round( data_due_principal + data_due_interest, 2);
    }
    
    public void cal_last_period_due_principal()
    { // �������һ��Ӧ���������û���걾��ȫ���黹��
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
        newCal.setTime(start_date);//ʹ�ø����� Date ���ô� Calendar ��ʱ�䡣 
        
        if ( period_days == 0 ) // �����0�����ǰ��»������Ϊx��.
        {
            newCal.add(java.util.Calendar.MONTH, x);// ���ڼ�x����
            period_date = newCal.getTime();//����һ����ʾ�� Calendar ʱ��ֵ�� Date ����
            
           // i = new DateInterval("P".$x."M");
        } 
        else if ( period_days < 0 ) // ��Ҫ���ú����ʵ����������
        {
            
        } else
        {
        	newCal.add(java.util.Calendar.DATE, x * period_days);// ���ڼ�x����
            period_date = newCal.getTime();//����һ����ʾ�� Calendar ʱ��ֵ�� Date ����
           // i = new DateInterval("P".$xd."D");
        }
        
        data_start_date  = start_date;//date_create_from_format("Y-m-d H:i:s",date_format($start_date,"Y-m-d 00:00:00"));
        data_period_date = period_date; //date_create_from_format("Y-m-d H:i:s",date_format($start_date,"Y-m-d 00:00:00"));
        

    //    echo date_format($date,"Y/m/d")."<br>";
        data_due_days = (int) ( ( data_period_date.getTime() - data_start_date.getTime() ) / (1000*3600*24) );  //���û���ˣ�������Ҫ���� FixDueDays
    }
    
    public Date getPeriodDate()
    { // ��ȡ���ڻ����յ�һ������
        Date the_date= new Date();
        the_date.setTime(data_period_date.getTime());
        return the_date;
    }
    
    public void fixDueDays(Date the_date)
    { // ���㱾�ڻ����պ�ĳ���ڣ����ڻ����գ����������
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
package com.wj.db.dao.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Timestamp;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wj.db.dao.entity.Jsapi_ticket;
import com.wj.db.dao.impl.ticketDaoImpl;
import com.wj.db.dao.intface.DaoIntface;
import com.wj.db.dao.util.ConnectionFactory;

public class TicketService {

	private DaoIntface userDao;
	
	private String appid;
	private String secret;
	
	   
    /**
     * 
     */
    public TicketService() {
        super();
        // TODO Auto-generated constructor stub
        appid = "wx66dded684c14bfd1";
        secret = "e5f1a2b154a810183f1e3032eb289c98";
    }
    
    public TicketService(String appid,String secret) {
        super();
        // TODO Auto-generated constructor stub
        this.appid = appid;
        this.secret = secret;
    }
	
    
	/** 
     * 获取URL指定的资源 
     * 
     * @throws IOException 
     */ 
    private String getTicketStr(String appid,String secret ) { 
            URL url;
            TokenService sss = new TokenService( appid, secret );
    		String accessToken = sss.RefreshToken();
    		
			try {
				url = new URL("https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token="+ accessToken );
				// 如果是企业号用以下 URL 获取 ticket
                // $url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=$accessToken";
                // $url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=$accessToken";
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			InputStream ins;
			try {
				ins = url.openStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

          //打开到此 URL 的连接并返回一个用于从该连接读入的 InputStream。 
            BufferedReader reader=new BufferedReader(new InputStreamReader(ins)); 
            String s="";
            try {
				while((s=reader.readLine())!=null)
				{
				    System.out.println(s);
				    reader.close(); 
				    return s;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            return "";
            
            
            
            /*    
            private String getJsApiTicket() {
                // jsapi_ticket 应该全局存储与更新，以下代码以写入到文件中做示例
                $data = json_decode(file_get_contents("jsapi_ticket.json"));
                if ($data->expire_time < time()) {
                  $accessToken = $this->getAccessToken();
                  // 如果是企业号用以下 URL 获取 ticket
                  // $url = "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=$accessToken";
                  $url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?type=jsapi&access_token=$accessToken";
                  $res = json_decode($this->httpGet($url));
                  $ticket = $res->ticket;
                  if ($ticket) {
                    $data->expire_time = time() + 7000;
                    $data->jsapi_ticket = $ticket;
                    $fp = fopen("jsapi_ticket.json", "w");
                    fwrite($fp, json_encode($data));
                    fclose($fp);
                  }
                } else {
                  $ticket = $data->jsapi_ticket;
                }

                return $ticket;
              }
        */
            
            
    }
    
    private String getTicketStr()
    {
    	return getTicketStr( appid, secret );
    }
    
    
    public String RefreshTicket(boolean force) { // true = 强制刷新
    	
		if ( force == false )
		{
			String s = getCurrentTicket();
			if  ( s !=null && s.length() > 0  )
				return s;
		}
		return GenAndSaveTicket();
    }
    
    public String RefreshTicket() {
    	return RefreshTicket(false);
    }
    
    private String getCurrentTicket(){
    	
    	Jsapi_ticket jsapi_ticket;
    	
    	Connection conn = null;
    	ArrayList alist =null;
    	userDao = new ticketDaoImpl(); // = DaoFactory.getInstance().createDao(UserDaoImpl.class);
		
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
			alist = userDao.get_where(conn, null);
		} catch (Exception e){
		} finally {
			try {
				conn.close();
			} catch (Exception e3){
				e3.printStackTrace();
			}
		}
		
		if ( alist !=null && alist.size() > 0  )
		{
			System.out.println( alist.get(0).getClass() );
			System.out.println( Jsapi_ticket.class );
			
			if ( Jsapi_ticket.class.isInstance( alist.get(0) ) )
			{
				java.util.Date date=new java.util.Date();
				
				jsapi_ticket = ( Jsapi_ticket ) alist.get(0);
//				long diff = date.getTime() - access_token.getStart_time().getTime();
//				if (  diff < 60*1000*90 ) // 90 分钟刷新一次
				if ( jsapi_ticket.getEnd_time() != null )
					if ( date.getTime() + 60000 < jsapi_ticket.getEnd_time().getTime() ) // 至少要早于截止时间1分钟
						return jsapi_ticket.getTicket();
			}
		}
    	return null;
    }
    
    //construct json from String and resolve it.  
    private String jsonData(String jsonString)
    {  
        JSONObject json;
        String token = "";
		try {
			json = new JSONObject(jsonString);
			JSONArray jsonArray=json.getJSONArray("data"); 
	        JSONObject data=(JSONObject) jsonArray.get(0);
	        token =(String) data.get("ticket");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
        return token;

/*        
        String loginNames="loginname list:";  
        for(int i=0;i<jsonArray.length();i++){  
            JSONObject user=(JSONObject) jsonArray.get(i);  
            String userName=(String) user.get("loginname");  
            if(i==jsonArray.length()-1){  
                loginNames+=userName;  
            }else{  
                loginNames+=userName+",";  
            }  
        }  
        return loginNames;
*/
    }  
    
    
    private String GenAndSaveTicket(){
    	String s;
    	Jsapi_ticket jsapi_ticket = new Jsapi_ticket();
    	
    	s = getTicketStr();	  
    	
    	if  ( s == null ||  s.length() <= 0  )
    		return s;
    	
    	s = jsonData("{\"data\":[" +s + "]}");
    	
    	
    	if  ( s == null ||  s.length() <= 0  )
    		return s;    	
    	
    	java.util.Date date=new java.util.Date();
    	Timestamp end_time = new Timestamp(date.getTime() + 60*1000*110 ); // 110分钟		
    	
    	jsapi_ticket.setTicket(s);
    	jsapi_ticket.setStart_time(null);
    	jsapi_ticket.setEnd_time(end_time);
    	save(jsapi_ticket);

    	return s;
    }
	
    private boolean save(Jsapi_ticket jsapi_ticket){
		Connection conn = null;

		userDao = new ticketDaoImpl(); // = DaoFactory.getInstance().createDao(UserDaoImpl.class);
		
		try {
			conn = ConnectionFactory.getInstance().makeConnection();
			conn.setAutoCommit(false);
			userDao.save(conn, jsapi_ticket);
			conn.commit();
		} catch (Exception e){
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (Exception e2){
				e2.printStackTrace();
			}
		} finally {
			try {
				conn.close();
			} catch (Exception e3){
				e3.printStackTrace();
			}
		}
		return false;
	}

}

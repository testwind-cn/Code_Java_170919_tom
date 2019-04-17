/**
 * 
 */
package com.wj.wechat.service;

import java.security.MessageDigest;
import java.sql.Timestamp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wj.db.dao.service.TicketService;

/**
 * @author DEV
 *
 */
public class JSSDK {
	
	private String appid;
	private String secret;
	
	/**
     * 
     */
    public JSSDK() {
        super();
        // TODO Auto-generated constructor stub
        appid = "wx66dded684c14bfd1";
        secret = "e5f1a2b154a810183f1e3032eb289c98";
    }
    
    public JSSDK(String appid,String secret) {
        super();
        // TODO Auto-generated constructor stub
        this.appid = appid;
        this.secret = secret;
    }
    
    
    public static String getSha1(String str){
        if(str==null||str.length()==0){
            return null;
        }
        char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9',
                'a','b','c','d','e','f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes("UTF-8"));

            byte[] md = mdTemp.digest();
            int j = md.length;
            char buf[] = new char[j*2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];      
            }
            return new String(buf);
        } catch (Exception e) {
            // TODO: handle exception
            return null;
        }
    }
    
    public JSSDK_data getSignPackage(HttpServletRequest req) {
        String jsapiTicket;
        JSSDK_data signPackage;
        TicketService tik = new TicketService();
        jsapiTicket = tik.RefreshTicket();
        
        // servlet中获取服务器信息 // http://blog.sina.com.cn/s/blog_4cb39e1a0100imsp.html

        // 注意 URL 一定要动态获取，不能 hardcode.
       // $protocol = (!empty($_SERVER['HTTPS']) && $_SERVER['HTTPS'] !== 'off' || $_SERVER['SERVER_PORT'] == 443) ? "https://" : "http://";
       // $url = "$protocol$_SERVER[HTTP_HOST]$_SERVER[REQUEST_URI]";
        
        // out.println("URL = " + req.getRequestURL() + "<br>");  
	    // out.println("query = " + req.getQueryString() + "<br>");
        String url = "";
        String query = "";
        if ( req != null )
        {
        	if ( req.getRequestURL() != null )
        		url = req.getRequestURL().toString();
        	if ( req.getQueryString() != null )
        	{
        		query = req.getQueryString().toString();
        		if (query!=null && query.length()>0)
            		url = url + "?" + query;
        	}
        } 
        
        
        if ( jsapiTicket == null || url == null || url.length() <=0 || jsapiTicket.length()<= 0 )
        	return null;
		
        long the_time;
        java.util.Date date=new java.util.Date();
        the_time = date.getTime() / 1000;
        String nonceStr = createNonceStr();

        // 这里参数的顺序要按照 key 值 ASCII 码升序排序
        String code_str = "jsapi_ticket="+jsapiTicket+"&noncestr="+ nonceStr+ "&timestamp="+the_time+"&url="+url;

        String signature = getSha1(code_str);
        
        signPackage = new JSSDK_data( appid,nonceStr,the_time,url,signature,code_str);

        /*
        signPackage = array(
          "appId"     => $this->appId,
          "nonceStr"  => $nonceStr,
          "timestamp" => $timestamp,
          "url"       => $url,
          "signature" => $signature,
          "rawString" => code_str
        ); */
        return signPackage; 
      }

    private String createNonceStr() {
    	int length = 16;
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        String str = "";
        for (int i = 0; i < length; i++) {
          str = str + chars.charAt((int)(Math.random()*chars.length()));
        }
        str  = "1234567890123456";
        return str;
      }
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

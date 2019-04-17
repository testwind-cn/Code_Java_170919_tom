/**
 * 
 */
package com.wj.wechat.service;

/**
 * @author DEV
 *
 */
public class JSSDK_data {
	private String appId;
	private String nonceStr;
	private long timestamp;
	private String url;
	private String signature;
	private String rawString;
	
	public JSSDK_data() {
		
	}

	public JSSDK_data(String appId,String nonceStr, long timestamp, String url, String signature, String rawString ) {
		this.appId = appId;
		this.nonceStr = nonceStr;
		this.timestamp = timestamp;
		this.url = url;
		this.signature = signature;
		this.rawString = rawString;
	}

	public String getAppId() {
		return appId;
	}

	public String getNonceStr() {
		return nonceStr;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getUrl() {
		return url;
	}

	public String getSignature() {
		return signature;
	}

	public String getRawString() {
		return rawString;
	}

}

package com.eyun.pay.utils;

import java.security.MessageDigest;

public class WxPayUtil {
	
	public static String getSign(String appid, String attach, String body, String mch_id, String nonce_str, String notify_url, String out_trade_no, String total_fee, String trade_type, String key) throws Exception {
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer
			.append("appid=").append(appid)
			.append("&attach=").append(attach)
			.append("&body=").append(body)
			.append("&mch_id=").append(mch_id)
			.append("&nonce_str=").append(nonce_str)
			.append("&notify_url").append(notify_url)
			.append("&out_trade_no=").append(out_trade_no)
			.append("&total_fee=").append(total_fee)
			.append("&trade_type=").append(trade_type)
			.append("&key=").append(key);
//		String str1 = "appid=wxf177c6755716fa32"
//				+"&attach=支付测试"
//				+"&body=APP支付测试"
//				+"&mch_id=1500998061"
//				+"&nonce_str=5K8264ILTKCH16CQ2502SI8ZNMTM67VS"
//				+"&notify_url=http://www.baidu.com"
//				+"&out_trade_no=1"
//				+"&total_fee=1"
//				+"&trade_type=APP"
//				+"&key=6H7vSZjhOQEhjsCVA9b2XKqjooTWBVZr";
		byte[] bytes = stringBuffer.toString().getBytes();
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(bytes);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
		return new String(str).toUpperCase();
	}
	
	
}

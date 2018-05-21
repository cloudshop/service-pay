package com.eyun.pay.test;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.Jaxb2RootElementHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCancelRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCancelResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.thoughtworks.xstream.XStream;

public class PayTest {

	@Test
	public void fun1() {
		try {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					"2018032102418344",
					"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA3PtcpUmi0AEQx618Arjgu/ohCS4MrEBwiCWxW93uFah7pQdlENiRhNbAy+w1iu0gSyCy4sFJkScXU8soYiV5Lc5gv7lM1j+e/EDCEFhqSUq8kPDymyNkQBZCPUXy+PnZFpSfkZ5z87asPua1aLbsRM5xqzDO4vvXZ17OH4OvInhH7PLCqZglwcS6iyi8LW7U5dO18qxKYD5BCY5brU+p5uvEhRK8JuT+FhP06tQInhpDDxRsXtehSvy/zvYeOcEV8AvzJEt84VqHKecYkCZrSBn/CIQeR/lePFZLiqnUZWpkQVnRViWbcxxjsJk2rJyCdpDW+34a1kF+g6TesC+BAgMBAAECggEASrTOwUJdVa3Q29kdAE5lotftueI+bjZC99QlFaCKRPEqxEpWpPVzjlqwfRoAs3TPCZVQYzqmuIJ7a/PPXCM4dMojiSJ6+qJ7HnCD/Sgrt3AQfR5/1tn1SZ3xgVMOx2FeFpNCmtTjVfLvraTn5Rlc0gSNG0a+r0UJXJT0Ck/2yF2bX023GDk8omNoEqSZ1DPhs7gn5cym9NckOIx8LSYLi7J1GmhALWr0jNSybZl21mh1zpO9EG+2uF07VS1bSnUedMGtY9Ohf+B2V9MX3mLpE/S+IvPYPjr4bXE8ZXxornawJWP71MYX3yaC0qxCfDaBCo5/2pvZrrf1pL0ju2uz4QKBgQDrdjXEj11fDaJLHww2oZypQY0tLPnxbpgZM0N1lYkKDHgMfixIOe1U0AduY4YLtcbet3R3X1CcIF0tMs/9vgV90nq7hrH1AiPxGXnLMnoAqNkKSivIVjd5o4+usQSYlCjAL0IrQqMj9kGSoBTO/nL7EXrU3Rp9P4cJ/0FPdZzxpQKBgQDRr48kTFgDVBqcGF9/hJMXlcnCh43jpQC/Tm1eKFxLFVCxHs2ANf8N7uq75D9TNLIexkJCK2bH9PrDFRtc5eCicLxEC4vgx83ypxfreq5OBwdvJbnUbUDQ9tIRruLH3mfMok8tqZCIIxopur5eDPlCjryVD0fhY09VipDYYTHnrQKBgQC6SXncmzhaHaVLHbNB/ba+Sjhxh2Xv13nKZj525unW42qPJ7vNINdeBH/8nAzPcu92AVrJnsVd4FUXj13y+MXLeBzWBIkCuMDK3Ub4tmTD5NJiS7A7/cpCGF9y7GYgeQeMlVcadswvhYL3iGMuKS266WduxcdRVFN4W1TGktyqaQKBgQClHD11kpv3OwGdCmAgVC9S8gKmKAElUOdOvJ3H97X89XLNlXixzVLiENBLVUY76ZGcQ/cmjXBoYVIXzQX315dTsbu9RO5G+G4F9WcTaE3aaRSEg8tZJxJTuRbfD7vJMCrB6si4Jv4FBMb0NvTxr/uSWjzKjvbF1mJA9FRr3dTjHQKBgEP/OyVq77Ws0Hs3Q1MkrueRISD8sNjqj0IIPBilqcIWAfDhFN39rFSF0V1V8cSmbOicBex2o8sgr8J379ZUtS/Dtrq8GRTrkEkc5tZW5nI25SytKIG8M35JxV2ZjCdzUDw8iFbfHgruVC8AOa8k2jjfuaQ/OEni1KGshilhSkC6",
					"json", "GBK",
					"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxGgcgzQSPV6Td1brra08f8qjYvciXLlHA9XJQ9fQur2XJxsjAivvaUluXSi0I+kf+RsNUm0CrHHfSwX8uzmBHzLNWXyJ1kVUqhMyFwAIlfx75K011FeNmsgJ8sEv0JI0aG5QP+3cFIPsIsPY5fGmPhPvPYR4ysRzzZeqzSZ5kf+20g2VLXlwG0lELfJ/hgxR0i4pBPBjH82sviGP7ffUKB0tk06Qv79+zVYXdl4hrxEblB0t4Kg4xYOlSK2XBrw30gY85vCsmI6hW4Zy3xXIQodYU75YYi1FFg5utjZtc4O76CV44jUXBe8F4nk/nX4/ezJHf7VsbZO/jBRfUEin/wIDAQAB",
					"RSA2");
			AlipayDataDataserviceBillDownloadurlQueryRequest request = new AlipayDataDataserviceBillDownloadurlQueryRequest();
			request.setBizContent("{" + "\"bill_type\":\"trade\"," + "\"bill_date\":\"2018-04-05\"" + "  }");
			AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("调用成功");
				System.out.println(response.getBillDownloadUrl());
				System.out.println(response.getBody());
			} else {
				System.out.println("调用失败");
			}
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void fun2() {
		try {
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do",
					"2018032102418344",
					"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA3PtcpUmi0AEQx618Arjgu/ohCS4MrEBwiCWxW93uFah7pQdlENiRhNbAy+w1iu0gSyCy4sFJkScXU8soYiV5Lc5gv7lM1j+e/EDCEFhqSUq8kPDymyNkQBZCPUXy+PnZFpSfkZ5z87asPua1aLbsRM5xqzDO4vvXZ17OH4OvInhH7PLCqZglwcS6iyi8LW7U5dO18qxKYD5BCY5brU+p5uvEhRK8JuT+FhP06tQInhpDDxRsXtehSvy/zvYeOcEV8AvzJEt84VqHKecYkCZrSBn/CIQeR/lePFZLiqnUZWpkQVnRViWbcxxjsJk2rJyCdpDW+34a1kF+g6TesC+BAgMBAAECggEASrTOwUJdVa3Q29kdAE5lotftueI+bjZC99QlFaCKRPEqxEpWpPVzjlqwfRoAs3TPCZVQYzqmuIJ7a/PPXCM4dMojiSJ6+qJ7HnCD/Sgrt3AQfR5/1tn1SZ3xgVMOx2FeFpNCmtTjVfLvraTn5Rlc0gSNG0a+r0UJXJT0Ck/2yF2bX023GDk8omNoEqSZ1DPhs7gn5cym9NckOIx8LSYLi7J1GmhALWr0jNSybZl21mh1zpO9EG+2uF07VS1bSnUedMGtY9Ohf+B2V9MX3mLpE/S+IvPYPjr4bXE8ZXxornawJWP71MYX3yaC0qxCfDaBCo5/2pvZrrf1pL0ju2uz4QKBgQDrdjXEj11fDaJLHww2oZypQY0tLPnxbpgZM0N1lYkKDHgMfixIOe1U0AduY4YLtcbet3R3X1CcIF0tMs/9vgV90nq7hrH1AiPxGXnLMnoAqNkKSivIVjd5o4+usQSYlCjAL0IrQqMj9kGSoBTO/nL7EXrU3Rp9P4cJ/0FPdZzxpQKBgQDRr48kTFgDVBqcGF9/hJMXlcnCh43jpQC/Tm1eKFxLFVCxHs2ANf8N7uq75D9TNLIexkJCK2bH9PrDFRtc5eCicLxEC4vgx83ypxfreq5OBwdvJbnUbUDQ9tIRruLH3mfMok8tqZCIIxopur5eDPlCjryVD0fhY09VipDYYTHnrQKBgQC6SXncmzhaHaVLHbNB/ba+Sjhxh2Xv13nKZj525unW42qPJ7vNINdeBH/8nAzPcu92AVrJnsVd4FUXj13y+MXLeBzWBIkCuMDK3Ub4tmTD5NJiS7A7/cpCGF9y7GYgeQeMlVcadswvhYL3iGMuKS266WduxcdRVFN4W1TGktyqaQKBgQClHD11kpv3OwGdCmAgVC9S8gKmKAElUOdOvJ3H97X89XLNlXixzVLiENBLVUY76ZGcQ/cmjXBoYVIXzQX315dTsbu9RO5G+G4F9WcTaE3aaRSEg8tZJxJTuRbfD7vJMCrB6si4Jv4FBMb0NvTxr/uSWjzKjvbF1mJA9FRr3dTjHQKBgEP/OyVq77Ws0Hs3Q1MkrueRISD8sNjqj0IIPBilqcIWAfDhFN39rFSF0V1V8cSmbOicBex2o8sgr8J379ZUtS/Dtrq8GRTrkEkc5tZW5nI25SytKIG8M35JxV2ZjCdzUDw8iFbfHgruVC8AOa8k2jjfuaQ/OEni1KGshilhSkC6",
					"json", "UTF-8",
					"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxGgcgzQSPV6Td1brra08f8qjYvciXLlHA9XJQ9fQur2XJxsjAivvaUluXSi0I+kf+RsNUm0CrHHfSwX8uzmBHzLNWXyJ1kVUqhMyFwAIlfx75K011FeNmsgJ8sEv0JI0aG5QP+3cFIPsIsPY5fGmPhPvPYR4ysRzzZeqzSZ5kf+20g2VLXlwG0lELfJ/hgxR0i4pBPBjH82sviGP7ffUKB0tk06Qv79+zVYXdl4hrxEblB0t4Kg4xYOlSK2XBrw30gY85vCsmI6hW4Zy3xXIQodYU75YYi1FFg5utjZtc4O76CV44jUXBe8F4nk/nX4/ezJHf7VsbZO/jBRfUEin/wIDAQAB",
					"RSA2");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
			// request.setBizContent("{" +
			// "\"out_trade_no\":\"20150320010101001\","
			// + "\"trade_no\":\"2014112611001004680 073956707\"" + " }");
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("out_trade_no", "123460");
			request.setBizContent(jsonObject.toString());
			AlipayTradeQueryResponse response = alipayClient.execute(request);
			if (response.isSuccess()) {
				System.out.println("调用成功");
				System.out.println(response.getBody());
			} else {
				System.out.println("调用失败");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void fun4() {
		System.out.println(System.currentTimeMillis());
	}

	class wxReturn {
		private String return_code;
		private String return_msg;
		private String appid;
		private String mch_id;
		private String device_info;
		private String nonce_str;
		private String sign;
		private String result_code;
		private String err_code;
		private String err_code_des;
		private String trade_type;
		private String prepay_id;

		@Override
		public String toString() {
			return "wxReturn [return_code=" + return_code + ", return_msg=" + return_msg + ", appid=" + appid
					+ ", mch_id=" + mch_id + ", device_info=" + device_info + ", nonce_str=" + nonce_str + ", sign="
					+ sign + ", result_code=" + result_code + ", err_code=" + err_code + ", err_code_des="
					+ err_code_des + ", trade_type=" + trade_type + ", prepay_id=" + prepay_id + "]";
		}

		public String getReturn_code() {
			return return_code;
		}

		public void setReturn_code(String return_code) {
			this.return_code = return_code;
		}

		public String getReturn_msg() {
			return return_msg;
		}

		public void setReturn_msg(String return_msg) {
			this.return_msg = return_msg;
		}

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getMch_id() {
			return mch_id;
		}

		public void setMch_id(String mch_id) {
			this.mch_id = mch_id;
		}

		public String getDevice_info() {
			return device_info;
		}

		public void setDevice_info(String device_info) {
			this.device_info = device_info;
		}

		public String getNonce_str() {
			return nonce_str;
		}

		public void setNonce_str(String nonce_str) {
			this.nonce_str = nonce_str;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}

		public String getResult_code() {
			return result_code;
		}

		public void setResult_code(String result_code) {
			this.result_code = result_code;
		}

		public String getErr_code() {
			return err_code;
		}

		public void setErr_code(String err_code) {
			this.err_code = err_code;
		}

		public String getErr_code_des() {
			return err_code_des;
		}

		public void setErr_code_des(String err_code_des) {
			this.err_code_des = err_code_des;
		}

		public String getTrade_type() {
			return trade_type;
		}

		public void setTrade_type(String trade_type) {
			this.trade_type = trade_type;
		}

		public String getPrepay_id() {
			return prepay_id;
		}

		public void setPrepay_id(String prepay_id) {
			this.prepay_id = prepay_id;
		}
	}

	class wxRequest {
		private String appid;
		private String mch_id;
		private String nonce_str;
		private String attach;
		private String body;
		private String out_trade_no;
		private String total_fee;
		private String trade_type;
		private String notify_url;
		private String sign;

		public String getAppid() {
			return appid;
		}

		public void setAppid(String appid) {
			this.appid = appid;
		}

		public String getMch_id() {
			return mch_id;
		}

		public void setMch_id(String mch_id) {
			this.mch_id = mch_id;
		}

		public String getNonce_str() {
			return nonce_str;
		}

		public void setNonce_str(String nonce_str) {
			this.nonce_str = nonce_str;
		}

		public String getAttach() {
			return attach;
		}

		public void setAttach(String attach) {
			this.attach = attach;
		}

		public String getBody() {
			return body;
		}

		public void setBody(String body) {
			this.body = body;
		}

		public String getOut_trade_no() {
			return out_trade_no;
		}

		public void setOut_trade_no(String out_trade_no) {
			this.out_trade_no = out_trade_no;
		}

		public String getTotal_fee() {
			return total_fee;
		}

		public void setTotal_fee(String total_fee) {
			this.total_fee = total_fee;
		}

		public String getTrade_type() {
			return trade_type;
		}

		public void setTrade_type(String trade_type) {
			this.trade_type = trade_type;
		}

		public String getNotify_url() {
			return notify_url;
		}

		public void setNotify_url(String notify_url) {
			this.notify_url = notify_url;
		}

		public String getSign() {
			return sign;
		}

		public void setSign(String sign) {
			this.sign = sign;
		}
	}

	// 微信支付测试
	@Test
	public void fun3() {
		try {
//			String str1 = "appid=wxf177c6755716fa32"
//					+ "&attach=支付测试"
//					+ "&body=APP支付测试"
//					+ "&mch_id=1500998061"
//					+ "&nonce_str=5K8264ILTKCH16CQ2502SI8ZNMTM67VS"
//					+ "&notify_url=www.baidu.com"
//					+ "&out_trade_no=123459"
//					+ "&total_fee=1"
//					+ "&trade_type=APP"
//					+ "&key=6H7vSZjhOQEhjsCVA9b2XKqjooTWBVZr";
			String str1 = "appid=wxf177c6755716fa32"
					+ "&nonce_str=5K8264ILTKCH16CQ2502SI8ZNMTM67VS"
					+ "&package=Sign=WXPay"
					+ "&partnerid=1500998061"
					+ "&prepayid=wx211510508158933b899a979b3676397794"
					+ "&timestamp="+System.currentTimeMillis()
					+ "&key=6H7vSZjhOQEhjsCVA9b2XKqjooTWBVZr";
			System.out.println(str1);
			byte[] bytes = str1.getBytes();
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
			String upperCase = new String(str).toUpperCase();
			System.out.println(upperCase);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void fun5() {
		RestTemplate restTemplate = new RestTemplate();
		String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";	
		String xml = "<xml>"
		   +"<appid>wxf177c6755716fa32</appid>"
		   +"<mch_id>1500998061</mch_id>"
		   +"<nonce_str>5K8264ILTKCH16CQ2502SI8ZNMTM67VS</nonce_str>"
		   +"<attach>支付测试</attach>"
		   +"<body>APP支付测试</body>"
		   +"<out_trade_no>123457</out_trade_no>"
		   +"<total_fee>1</total_fee>"
		   +"<trade_type>APP</trade_type>"
		   +"<notify_url>www.baidu.com</notify_url>"
		   +"<sign>3D14A397EE992746F35F802ABED4BF4C</sign>"
		   +"</xml>";
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("Content-Type", "application/xml;charset=UTF-8");
		HttpEntity<String> httpEntity = new HttpEntity<>(xml,httpHeaders);
		ResponseEntity<String> resp = restTemplate.postForEntity(url, httpEntity, String.class);
		XStream xStream = new XStream();
		xStream.alias("xml", wxReturn.class);
		wxReturn wr = (wxReturn)xStream.fromXML(resp.getBody());
		System.out.println(wr.toString());
	}
	
	@Test
	public void fun() {
		Instant instant = Instant.now();
		OffsetDateTime atOffset = instant.atOffset(ZoneOffset.ofHours(1));

		//返回偏移的时间戳 !!!!!!!!!
		//!!!!!注意这里的时间戳是原来的instant 
		//!!!!  atOffset.toInstant()==instant
		Instant instant2 = atOffset.toInstant();
		System.out.println(instant2);
	}
	
	@Test
	public void fun11() throws AlipayApiException {
		String APP_ID = "2018032102418344";
		String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA3PtcpUmi0AEQx618Arjgu/ohCS4MrEBwiCWxW93uFah7pQdlENiRhNbAy+w1iu0gSyCy4sFJkScXU8soYiV5Lc5gv7lM1j+e/EDCEFhqSUq8kPDymyNkQBZCPUXy+PnZFpSfkZ5z87asPua1aLbsRM5xqzDO4vvXZ17OH4OvInhH7PLCqZglwcS6iyi8LW7U5dO18qxKYD5BCY5brU+p5uvEhRK8JuT+FhP06tQInhpDDxRsXtehSvy/zvYeOcEV8AvzJEt84VqHKecYkCZrSBn/CIQeR/lePFZLiqnUZWpkQVnRViWbcxxjsJk2rJyCdpDW+34a1kF+g6TesC+BAgMBAAECggEASrTOwUJdVa3Q29kdAE5lotftueI+bjZC99QlFaCKRPEqxEpWpPVzjlqwfRoAs3TPCZVQYzqmuIJ7a/PPXCM4dMojiSJ6+qJ7HnCD/Sgrt3AQfR5/1tn1SZ3xgVMOx2FeFpNCmtTjVfLvraTn5Rlc0gSNG0a+r0UJXJT0Ck/2yF2bX023GDk8omNoEqSZ1DPhs7gn5cym9NckOIx8LSYLi7J1GmhALWr0jNSybZl21mh1zpO9EG+2uF07VS1bSnUedMGtY9Ohf+B2V9MX3mLpE/S+IvPYPjr4bXE8ZXxornawJWP71MYX3yaC0qxCfDaBCo5/2pvZrrf1pL0ju2uz4QKBgQDrdjXEj11fDaJLHww2oZypQY0tLPnxbpgZM0N1lYkKDHgMfixIOe1U0AduY4YLtcbet3R3X1CcIF0tMs/9vgV90nq7hrH1AiPxGXnLMnoAqNkKSivIVjd5o4+usQSYlCjAL0IrQqMj9kGSoBTO/nL7EXrU3Rp9P4cJ/0FPdZzxpQKBgQDRr48kTFgDVBqcGF9/hJMXlcnCh43jpQC/Tm1eKFxLFVCxHs2ANf8N7uq75D9TNLIexkJCK2bH9PrDFRtc5eCicLxEC4vgx83ypxfreq5OBwdvJbnUbUDQ9tIRruLH3mfMok8tqZCIIxopur5eDPlCjryVD0fhY09VipDYYTHnrQKBgQC6SXncmzhaHaVLHbNB/ba+Sjhxh2Xv13nKZj525unW42qPJ7vNINdeBH/8nAzPcu92AVrJnsVd4FUXj13y+MXLeBzWBIkCuMDK3Ub4tmTD5NJiS7A7/cpCGF9y7GYgeQeMlVcadswvhYL3iGMuKS266WduxcdRVFN4W1TGktyqaQKBgQClHD11kpv3OwGdCmAgVC9S8gKmKAElUOdOvJ3H97X89XLNlXixzVLiENBLVUY76ZGcQ/cmjXBoYVIXzQX315dTsbu9RO5G+G4F9WcTaE3aaRSEg8tZJxJTuRbfD7vJMCrB6si4Jv4FBMb0NvTxr/uSWjzKjvbF1mJA9FRr3dTjHQKBgEP/OyVq77Ws0Hs3Q1MkrueRISD8sNjqj0IIPBilqcIWAfDhFN39rFSF0V1V8cSmbOicBex2o8sgr8J379ZUtS/Dtrq8GRTrkEkc5tZW5nI25SytKIG8M35JxV2ZjCdzUDw8iFbfHgruVC8AOa8k2jjfuaQ/OEni1KGshilhSkC6";
		String CHARSET = "utf-8";
		String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxGgcgzQSPV6Td1brra08f8qjYvciXLlHA9XJQ9fQur2XJxsjAivvaUluXSi0I+kf+RsNUm0CrHHfSwX8uzmBHzLNWXyJ1kVUqhMyFwAIlfx75K011FeNmsgJ8sEv0JI0aG5QP+3cFIPsIsPY5fGmPhPvPYR4ysRzzZeqzSZ5kf+20g2VLXlwG0lELfJ/hgxR0i4pBPBjH82sviGP7ffUKB0tk06Qv79+zVYXdl4hrxEblB0t4Kg4xYOlSK2XBrw30gY85vCsmI6hW4Zy3xXIQodYU75YYi1FFg5utjZtc4O76CV44jUXBe8F4nk/nX4/ezJHf7VsbZO/jBRfUEin/wIDAQAB";
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();//创建API对应的request类
		request.setBizContent("{" +
		"    \"out_trade_no\":\"12018050718318305\"," +
		"    \"total_amount\":\"0.01\"," +
		"    \"subject\":\"Iphone6 16G\"," +
		"    \"store_id\":\"NJ_001\"," +
		"    \"timeout_express\":\"90m\"}");//设置业务参数
		AlipayTradePrecreateResponse response = alipayClient.execute(request);
		System.out.print(response.getBody());
	}
	
	@Test
	public void cancelOrder() throws AlipayApiException {
		String APP_ID = "2018032102418344";
		String APP_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA3PtcpUmi0AEQx618Arjgu/ohCS4MrEBwiCWxW93uFah7pQdlENiRhNbAy+w1iu0gSyCy4sFJkScXU8soYiV5Lc5gv7lM1j+e/EDCEFhqSUq8kPDymyNkQBZCPUXy+PnZFpSfkZ5z87asPua1aLbsRM5xqzDO4vvXZ17OH4OvInhH7PLCqZglwcS6iyi8LW7U5dO18qxKYD5BCY5brU+p5uvEhRK8JuT+FhP06tQInhpDDxRsXtehSvy/zvYeOcEV8AvzJEt84VqHKecYkCZrSBn/CIQeR/lePFZLiqnUZWpkQVnRViWbcxxjsJk2rJyCdpDW+34a1kF+g6TesC+BAgMBAAECggEASrTOwUJdVa3Q29kdAE5lotftueI+bjZC99QlFaCKRPEqxEpWpPVzjlqwfRoAs3TPCZVQYzqmuIJ7a/PPXCM4dMojiSJ6+qJ7HnCD/Sgrt3AQfR5/1tn1SZ3xgVMOx2FeFpNCmtTjVfLvraTn5Rlc0gSNG0a+r0UJXJT0Ck/2yF2bX023GDk8omNoEqSZ1DPhs7gn5cym9NckOIx8LSYLi7J1GmhALWr0jNSybZl21mh1zpO9EG+2uF07VS1bSnUedMGtY9Ohf+B2V9MX3mLpE/S+IvPYPjr4bXE8ZXxornawJWP71MYX3yaC0qxCfDaBCo5/2pvZrrf1pL0ju2uz4QKBgQDrdjXEj11fDaJLHww2oZypQY0tLPnxbpgZM0N1lYkKDHgMfixIOe1U0AduY4YLtcbet3R3X1CcIF0tMs/9vgV90nq7hrH1AiPxGXnLMnoAqNkKSivIVjd5o4+usQSYlCjAL0IrQqMj9kGSoBTO/nL7EXrU3Rp9P4cJ/0FPdZzxpQKBgQDRr48kTFgDVBqcGF9/hJMXlcnCh43jpQC/Tm1eKFxLFVCxHs2ANf8N7uq75D9TNLIexkJCK2bH9PrDFRtc5eCicLxEC4vgx83ypxfreq5OBwdvJbnUbUDQ9tIRruLH3mfMok8tqZCIIxopur5eDPlCjryVD0fhY09VipDYYTHnrQKBgQC6SXncmzhaHaVLHbNB/ba+Sjhxh2Xv13nKZj525unW42qPJ7vNINdeBH/8nAzPcu92AVrJnsVd4FUXj13y+MXLeBzWBIkCuMDK3Ub4tmTD5NJiS7A7/cpCGF9y7GYgeQeMlVcadswvhYL3iGMuKS266WduxcdRVFN4W1TGktyqaQKBgQClHD11kpv3OwGdCmAgVC9S8gKmKAElUOdOvJ3H97X89XLNlXixzVLiENBLVUY76ZGcQ/cmjXBoYVIXzQX315dTsbu9RO5G+G4F9WcTaE3aaRSEg8tZJxJTuRbfD7vJMCrB6si4Jv4FBMb0NvTxr/uSWjzKjvbF1mJA9FRr3dTjHQKBgEP/OyVq77Ws0Hs3Q1MkrueRISD8sNjqj0IIPBilqcIWAfDhFN39rFSF0V1V8cSmbOicBex2o8sgr8J379ZUtS/Dtrq8GRTrkEkc5tZW5nI25SytKIG8M35JxV2ZjCdzUDw8iFbfHgruVC8AOa8k2jjfuaQ/OEni1KGshilhSkC6";
		String CHARSET = "utf-8";
		String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxGgcgzQSPV6Td1brra08f8qjYvciXLlHA9XJQ9fQur2XJxsjAivvaUluXSi0I+kf+RsNUm0CrHHfSwX8uzmBHzLNWXyJ1kVUqhMyFwAIlfx75K011FeNmsgJ8sEv0JI0aG5QP+3cFIPsIsPY5fGmPhPvPYR4ysRzzZeqzSZ5kf+20g2VLXlwG0lELfJ/hgxR0i4pBPBjH82sviGP7ffUKB0tk06Qv79+zVYXdl4hrxEblB0t4Kg4xYOlSK2XBrw30gY85vCsmI6hW4Zy3xXIQodYU75YYi1FFg5utjZtc4O76CV44jUXBe8F4nk/nX4/ezJHf7VsbZO/jBRfUEin/wIDAQAB";
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2"); //获得初始化的AlipayClient
		AlipayTradeCancelRequest request = new AlipayTradeCancelRequest();//创建API对应的request类
		request.setBizContent("{" +
		"    \"out_trade_no\":\"12018050718318305\"," +
		"    \"trade_no\":\"\"}"); //设置业务参数
		//request.setNotifyUrl("");
		AlipayTradeCancelResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
		System.out.print(response.getBody());
	}

}

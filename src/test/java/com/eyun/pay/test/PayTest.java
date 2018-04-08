package com.eyun.pay.test;

import java.util.Map;

import org.json.JSONObject;
import org.junit.Test;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;

public class PayTest {

	@Test
	public void fun1() {
		try {
			AlipayClient alipayClient = new DefaultAlipayClient(
					"https://openapi.alipay.com/gateway.do", "2018032102418344",
					"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA3PtcpUmi0AEQx618Arjgu/ohCS4MrEBwiCWxW93uFah7pQdlENiRhNbAy+w1iu0gSyCy4sFJkScXU8soYiV5Lc5gv7lM1j+e/EDCEFhqSUq8kPDymyNkQBZCPUXy+PnZFpSfkZ5z87asPua1aLbsRM5xqzDO4vvXZ17OH4OvInhH7PLCqZglwcS6iyi8LW7U5dO18qxKYD5BCY5brU+p5uvEhRK8JuT+FhP06tQInhpDDxRsXtehSvy/zvYeOcEV8AvzJEt84VqHKecYkCZrSBn/CIQeR/lePFZLiqnUZWpkQVnRViWbcxxjsJk2rJyCdpDW+34a1kF+g6TesC+BAgMBAAECggEASrTOwUJdVa3Q29kdAE5lotftueI+bjZC99QlFaCKRPEqxEpWpPVzjlqwfRoAs3TPCZVQYzqmuIJ7a/PPXCM4dMojiSJ6+qJ7HnCD/Sgrt3AQfR5/1tn1SZ3xgVMOx2FeFpNCmtTjVfLvraTn5Rlc0gSNG0a+r0UJXJT0Ck/2yF2bX023GDk8omNoEqSZ1DPhs7gn5cym9NckOIx8LSYLi7J1GmhALWr0jNSybZl21mh1zpO9EG+2uF07VS1bSnUedMGtY9Ohf+B2V9MX3mLpE/S+IvPYPjr4bXE8ZXxornawJWP71MYX3yaC0qxCfDaBCo5/2pvZrrf1pL0ju2uz4QKBgQDrdjXEj11fDaJLHww2oZypQY0tLPnxbpgZM0N1lYkKDHgMfixIOe1U0AduY4YLtcbet3R3X1CcIF0tMs/9vgV90nq7hrH1AiPxGXnLMnoAqNkKSivIVjd5o4+usQSYlCjAL0IrQqMj9kGSoBTO/nL7EXrU3Rp9P4cJ/0FPdZzxpQKBgQDRr48kTFgDVBqcGF9/hJMXlcnCh43jpQC/Tm1eKFxLFVCxHs2ANf8N7uq75D9TNLIexkJCK2bH9PrDFRtc5eCicLxEC4vgx83ypxfreq5OBwdvJbnUbUDQ9tIRruLH3mfMok8tqZCIIxopur5eDPlCjryVD0fhY09VipDYYTHnrQKBgQC6SXncmzhaHaVLHbNB/ba+Sjhxh2Xv13nKZj525unW42qPJ7vNINdeBH/8nAzPcu92AVrJnsVd4FUXj13y+MXLeBzWBIkCuMDK3Ub4tmTD5NJiS7A7/cpCGF9y7GYgeQeMlVcadswvhYL3iGMuKS266WduxcdRVFN4W1TGktyqaQKBgQClHD11kpv3OwGdCmAgVC9S8gKmKAElUOdOvJ3H97X89XLNlXixzVLiENBLVUY76ZGcQ/cmjXBoYVIXzQX315dTsbu9RO5G+G4F9WcTaE3aaRSEg8tZJxJTuRbfD7vJMCrB6si4Jv4FBMb0NvTxr/uSWjzKjvbF1mJA9FRr3dTjHQKBgEP/OyVq77Ws0Hs3Q1MkrueRISD8sNjqj0IIPBilqcIWAfDhFN39rFSF0V1V8cSmbOicBex2o8sgr8J379ZUtS/Dtrq8GRTrkEkc5tZW5nI25SytKIG8M35JxV2ZjCdzUDw8iFbfHgruVC8AOa8k2jjfuaQ/OEni1KGshilhSkC6", 
					"json", 
					"GBK", 
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
			AlipayClient alipayClient = new DefaultAlipayClient(
					"https://openapi.alipay.com/gateway.do", "2018032102418344",
					"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA3PtcpUmi0AEQx618Arjgu/ohCS4MrEBwiCWxW93uFah7pQdlENiRhNbAy+w1iu0gSyCy4sFJkScXU8soYiV5Lc5gv7lM1j+e/EDCEFhqSUq8kPDymyNkQBZCPUXy+PnZFpSfkZ5z87asPua1aLbsRM5xqzDO4vvXZ17OH4OvInhH7PLCqZglwcS6iyi8LW7U5dO18qxKYD5BCY5brU+p5uvEhRK8JuT+FhP06tQInhpDDxRsXtehSvy/zvYeOcEV8AvzJEt84VqHKecYkCZrSBn/CIQeR/lePFZLiqnUZWpkQVnRViWbcxxjsJk2rJyCdpDW+34a1kF+g6TesC+BAgMBAAECggEASrTOwUJdVa3Q29kdAE5lotftueI+bjZC99QlFaCKRPEqxEpWpPVzjlqwfRoAs3TPCZVQYzqmuIJ7a/PPXCM4dMojiSJ6+qJ7HnCD/Sgrt3AQfR5/1tn1SZ3xgVMOx2FeFpNCmtTjVfLvraTn5Rlc0gSNG0a+r0UJXJT0Ck/2yF2bX023GDk8omNoEqSZ1DPhs7gn5cym9NckOIx8LSYLi7J1GmhALWr0jNSybZl21mh1zpO9EG+2uF07VS1bSnUedMGtY9Ohf+B2V9MX3mLpE/S+IvPYPjr4bXE8ZXxornawJWP71MYX3yaC0qxCfDaBCo5/2pvZrrf1pL0ju2uz4QKBgQDrdjXEj11fDaJLHww2oZypQY0tLPnxbpgZM0N1lYkKDHgMfixIOe1U0AduY4YLtcbet3R3X1CcIF0tMs/9vgV90nq7hrH1AiPxGXnLMnoAqNkKSivIVjd5o4+usQSYlCjAL0IrQqMj9kGSoBTO/nL7EXrU3Rp9P4cJ/0FPdZzxpQKBgQDRr48kTFgDVBqcGF9/hJMXlcnCh43jpQC/Tm1eKFxLFVCxHs2ANf8N7uq75D9TNLIexkJCK2bH9PrDFRtc5eCicLxEC4vgx83ypxfreq5OBwdvJbnUbUDQ9tIRruLH3mfMok8tqZCIIxopur5eDPlCjryVD0fhY09VipDYYTHnrQKBgQC6SXncmzhaHaVLHbNB/ba+Sjhxh2Xv13nKZj525unW42qPJ7vNINdeBH/8nAzPcu92AVrJnsVd4FUXj13y+MXLeBzWBIkCuMDK3Ub4tmTD5NJiS7A7/cpCGF9y7GYgeQeMlVcadswvhYL3iGMuKS266WduxcdRVFN4W1TGktyqaQKBgQClHD11kpv3OwGdCmAgVC9S8gKmKAElUOdOvJ3H97X89XLNlXixzVLiENBLVUY76ZGcQ/cmjXBoYVIXzQX315dTsbu9RO5G+G4F9WcTaE3aaRSEg8tZJxJTuRbfD7vJMCrB6si4Jv4FBMb0NvTxr/uSWjzKjvbF1mJA9FRr3dTjHQKBgEP/OyVq77Ws0Hs3Q1MkrueRISD8sNjqj0IIPBilqcIWAfDhFN39rFSF0V1V8cSmbOicBex2o8sgr8J379ZUtS/Dtrq8GRTrkEkc5tZW5nI25SytKIG8M35JxV2ZjCdzUDw8iFbfHgruVC8AOa8k2jjfuaQ/OEni1KGshilhSkC6", 
					"json", 
					"UTF-8", 
					"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxGgcgzQSPV6Td1brra08f8qjYvciXLlHA9XJQ9fQur2XJxsjAivvaUluXSi0I+kf+RsNUm0CrHHfSwX8uzmBHzLNWXyJ1kVUqhMyFwAIlfx75K011FeNmsgJ8sEv0JI0aG5QP+3cFIPsIsPY5fGmPhPvPYR4ysRzzZeqzSZ5kf+20g2VLXlwG0lELfJ/hgxR0i4pBPBjH82sviGP7ffUKB0tk06Qv79+zVYXdl4hrxEblB0t4Kg4xYOlSK2XBrw30gY85vCsmI6hW4Zy3xXIQodYU75YYi1FFg5utjZtc4O76CV44jUXBe8F4nk/nX4/ezJHf7VsbZO/jBRfUEin/wIDAQAB", 
					"RSA2");
			AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
//			request.setBizContent("{" + "\"out_trade_no\":\"20150320010101001\","
//					+ "\"trade_no\":\"2014112611001004680 073956707\"" + "  }");
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
}

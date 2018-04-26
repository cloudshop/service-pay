package com.eyun.pay.web.rest;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.eyun.pay.domain.AlipayVM;
import com.eyun.pay.service.OrderService;

import io.swagger.annotations.ApiOperation;

/**
 * mvn install:install-file -Dfile=C:\development\repository_git\alipay\javademo\alipay-sdk-java-3.0.0.jar -DgroupId=com.alipay.api -DartifactId=alipay-sdk-java -Dversion=3.0.0 -Dpackaging=jar
 * @author 逍遥子
 * 支付宝支付
 */
@RestController
@RequestMapping("/api")
public class AlipayResource {
	
	private final Logger log = LoggerFactory.getLogger(AlipayResource.class);
	
	@Value("${application.pay.alipay.domainName}")
	private String DOMAIN_NAME;
	
	@Value("${application.pay.alipay.appid}")
	private String APP_ID;
	
	@Value("${application.pay.alipay.appPrivateKey}")
	private String APP_PRIVATE_KEY;
	
	private String CHARSET = "utf-8";
	
	@Value("${application.pay.alipay.appPublicKey}")
	private String ALIPAY_PUBLIC_KEY; 
	
	@Autowired
	private OrderService orderService;
	
	@Value("${application.pay.alipay.aesKey}")
	private String ENCRYPT_KEY;
	
	private String NCRYPT_TYPE = "AES";
	
	/**
	 * @author 逍遥子
	 * @email 756898059@qq.com
	 * @date 2018年4月5日
	 * @version 1.0
	 * @param alipayVM
	 * @return
	 * @throws AlipayApiException
	 */
	@ApiOperation(value="生成支付宝OrderString")
	@PostMapping("/alipay/app/orderString")
	public String createAlipayAppOrder (@RequestBody AlipayVM alipayVM) throws AlipayApiException {
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2", ENCRYPT_KEY, NCRYPT_TYPE);
		//实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
		AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
		//SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
		AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
		model.setBody(alipayVM.getBody());
		model.setSubject(alipayVM.getSubject());
		model.setOutTradeNo(alipayVM.getOutTradeNo());
		model.setTimeoutExpress(alipayVM.getTimeoutExpress());
		model.setTotalAmount(alipayVM.getTotalAmount());
		model.setPassbackParams(alipayVM.getPassbackParams());
		request.setBizModel(model);
		request.setNotifyUrl(DOMAIN_NAME+"/pay/api/alipay/app/notify");
		//request.setNeedEncrypt(true);
        //这里和普通的接口调用不同，使用的是sdkExecute
        AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
		return response.getBody();
	}
	
	/**
	 * @author 逍遥子
	 * @email 756898059@qq.com
	 * @date 2018年4月5日
	 * @version 1.0
	 * @param request
	 * @return
	 * @throws AlipayApiException 
	 */
	@PostMapping("/alipay/app/notify")
	public String alipayNotify (HttpServletRequest request) throws AlipayApiException {
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
		    String name = (String) iter.next();
		    String[] values = (String[]) requestParams.get(name);
		    String valueStr = "";
		    for (int i = 0; i < values.length; i++) {
		        valueStr = (i == values.length - 1) ? valueStr + values[i]
		                    : valueStr + values[i] + ",";
		  	}
		    //乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
		    System.out.println(name+":"+valueStr);
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean flag = AlipaySignature.rsaCheckV1(params, ALIPAY_PUBLIC_KEY, CHARSET,"RSA2");
		if (flag) {//验签成功 执行回调
			String passbackParams = params.get("passback_params");
			switch (passbackParams) {
			case "deposit": //充值订单
				return orderService.depositNotify(params.get("out_trade_no"));
			default:
				return "failure";
			}
		} else {
			return "failure";
		}
	}
	
	/**
	 * 查询支付宝订单
	 * @author 逍遥子
	 * @email 756898059@qq.com
	 * @date 2018年4月8日
	 * @version 1.0
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/alipay/order/{orderNo}")
	public ResponseEntity<String> queryOrder (@PathVariable String orderNo) throws Exception {
		//实例化客户端
		AlipayClient alipayClient = new DefaultAlipayClient("https://openapi.alipay.com/gateway.do", APP_ID, APP_PRIVATE_KEY, "json", CHARSET, ALIPAY_PUBLIC_KEY, "RSA2", ENCRYPT_KEY, NCRYPT_TYPE);
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("out_trade_no", orderNo);
		request.setBizContent(jsonObject.toString());
		AlipayTradeQueryResponse response = alipayClient.execute(request);
		if (response.isSuccess()) {
			return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>("", HttpStatus.OK);
		}
	}
	
}

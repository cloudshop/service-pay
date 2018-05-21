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
import org.springframework.web.client.RestTemplate;

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
 * @author 逍遥子
 * 微信支付
 */
@RestController
@RequestMapping("/api")
public class WxpayResource {
	
	private final Logger log = LoggerFactory.getLogger(WxpayResource.class);
	
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
	
	@ApiOperation(value="创建微信支付预支付订单")
	@PostMapping("/wxpay/app/preorder")
	public String createWxpayAppOrder () {
		return "";
	}
	
}

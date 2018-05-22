package com.eyun.pay.web.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.eyun.pay.service.OrderService;
import com.eyun.pay.service.WxPayService;
import com.eyun.pay.utils.WXMyConfigUtil;
import com.eyun.pay.utils.WxPayUtil;

import io.swagger.annotations.ApiOperation;

/**
 * @author 逍遥子 微信支付
 */
@RestController
@RequestMapping("/api")
public class WxpayResource {

	private final Logger logger = LoggerFactory.getLogger(WxpayResource.class);

	@Autowired
	private OrderService orderService;

	@Autowired
	private WxPayService wxPayService;

	@ApiOperation(value = "创建微信支付预支付订单")
	@PostMapping("/wxpay/preorder")
	public String prePay(
			/*
			 * @RequestParam(required = true,value = "user_id")String user_id,
			 * 
			 * @RequestParam(required = true,value = "coupon_id")String coupon_id,
			 * 
			 * @RequestParam(required = true,value = "out_trade_no")String out_trade_no,
			 * 
			 * @RequestParam(required = true,value = "total_fee")String total_fee,
			 */
			HttpServletRequest req, HttpServletResponse response) throws Exception {
		logger.debug("进入微信支付申请");
		Date now = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");// 可以方便地修改日期格式
		String hehe = dateFormat.format(now);

		String out_trade_no = hehe + "wxpay";
		String total_fee = "1";
		String user_id = "1";
		String coupon_id = "7";

		String attach = user_id + "," + coupon_id;
		WXMyConfigUtil config = new WXMyConfigUtil();
		// String spbill_create_ip = GetIPAddrUtil.getIpAddr(req);
		String spbill_create_ip = "47.106.157.29";
		Map<String, String> result = wxPayService.dounifiedOrder(attach, out_trade_no, total_fee, spbill_create_ip, 1);
		String nonce_str = (String) result.get("nonce_str");
		String prepay_id = (String) result.get("prepay_id");
		Long time = System.currentTimeMillis() / 1000;
		String timestamp = time.toString();

		// 签名生成算法
		WxPayUtil md5Util = new WxPayUtil();
		Map<String, String> map = new HashMap<>();
		map.put("appid", config.getAppID());
		map.put("partnerid", config.getMchID());
		map.put("package", "Sign=WXPay");
		map.put("noncestr", nonce_str);
		map.put("timestamp", timestamp);
		map.put("prepayid", prepay_id);
		String sign = md5Util.getSign(map);

		String resultString = "{\"appid\":\"" + config.getAppID() + "\",\"partnerid\":\"" + config.getMchID()
				+ "\",\"package\":\"Sign=WXPay\"," + "\"noncestr\":\"" + nonce_str + "\",\"timestamp\":" + timestamp
				+ "," + "\"prepayid\":\"" + prepay_id + "\",\"sign\":\"" + sign + "\"}";
		logger.debug(resultString);
		
        return resultString;    //给前端app返回此字符串，再调用前端的微信sdk引起微信支付  
	}

	/**
	 * 订单支付异步通知
	 */
	@ApiOperation(value = "手机订单支付完成后回调")
	@PostMapping("/wxpay/notify")
	@Timed
	public String WXPayBack(HttpServletRequest request, HttpServletResponse response) {
		String resXml = "";
		logger.debug("进入异步通知");
		try {
			//
			InputStream is = request.getInputStream();
			// 将InputStream转换成String
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			try {
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			resXml = sb.toString();
			logger.debug(resXml);
			String result = wxPayService.payBack(resXml);
			// return "<xml><return_code><![CDATA[SUCCESS]]></return_code>
			// <return_msg><![CDATA[OK]]></return_msg></xml>";
			return result;
		} catch (Exception e) {
			logger.error("手机支付回调通知失败", e);
			String result = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
					+ "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
			return result;
		}
	}

	/**
	 * 查询微信订单
	 * 
	 * @date 2018年4月8日
	 * @version 1.0
	 * @param orderNo
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/wxpay/order/{orderNo}")
	public ResponseEntity<String> queryOrder(@PathVariable String orderNo) throws Exception {
		// 实例化客户端
		return null;
	}
}

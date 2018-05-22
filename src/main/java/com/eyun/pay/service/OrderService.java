package com.eyun.pay.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyun.pay.client.AuthorizedUserFeignClient;
import com.eyun.pay.service.dto.PayNotifyDTO;

@AuthorizedUserFeignClient(name="order")
public interface OrderService {

	@PutMapping("/api/dep-orders/deposit/{orderNo}")
	public String depositNotify(@PathVariable("orderNo") String orderNo);
	
	@PutMapping("/api/pro-order/pay/notify")
    public ResponseEntity proOrderNotify(@RequestBody PayNotifyDTO payNotifyDTO);
	
	@PutMapping("/api/leaguer-order/pay/notify")
	public ResponseEntity leaguerOrderNotify(@RequestBody PayNotifyDTO payNotifyDTO);
	
	@PutMapping("/api/leaguer-order/pay/notify2")
	public ResponseEntity leaguerOrderNotify2(@RequestBody PayNotifyDTO payNotifyDTO);

	@PutMapping("/api/face-order/pay/notify")
	public ResponseEntity faceOrderNotify(@RequestBody PayNotifyDTO payNotifyDTO);
	
}

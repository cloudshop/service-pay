package com.eyun.pay.service;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyun.pay.client.AuthorizedUserFeignClient;

@AuthorizedUserFeignClient(name="order")
public interface OrderService {

	@PutMapping("/api/dep-orders/deposit/{orderNo}")
	public String depositNotify(@RequestBody String orderNo);
	
}

package com.eyun.pay.service;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.eyun.pay.client.AuthorizedUserFeignClient;

@AuthorizedUserFeignClient(name="order")
public interface OrderService {

	@PutMapping("/api/dep-orders/deposit/{orderNo}")
	public String depositNotify(@PathVariable("orderNo") String orderNo);
	
}

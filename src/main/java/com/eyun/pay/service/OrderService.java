package com.eyun.pay.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import com.eyun.pay.client.AuthorizedUserFeignClient;

@AuthorizedUserFeignClient(name="order")
public interface OrderService {

	@PostMapping("/api/pro-orders")
	public ResponseEntity createProOrder(Object obj);
	
}

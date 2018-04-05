package com.eyun.pay.fallback;

import com.eyun.pay.domain.AlipayVM;

import feign.hystrix.FallbackFactory;


public class OrderFallback implements FallbackFactory {

	@Override
	public Object create(Throwable arg0) {
		System.out.println("feign errer");
		return new AlipayVM();
	}

}

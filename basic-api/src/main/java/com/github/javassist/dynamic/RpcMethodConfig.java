package com.github.javassist.dynamic;

import lombok.Data;

import java.util.concurrent.atomic.AtomicInteger;

@Data
public class RpcMethodConfig {

	private static AtomicInteger atomicInteger = new AtomicInteger(0);

	/**
	 * 方法签名
	 */
	protected String sign;
	/**
	 * 接口签名 + 方法签名
	 */
	protected String key;

	private Integer relativeId = atomicInteger.incrementAndGet();

}

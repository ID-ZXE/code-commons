package com.github.javassist.dynamic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 调用本地方法的标准接口，用于创建动态代理
 */
public interface DynamicInvoker {

	/**
	 * 用于全局服务方法调用计数，调用发起前+1，调用完成后-1，也用于优雅停机
	 */
	AtomicInteger count = new AtomicInteger(0);

	/**
	 * 调用本地服务方法
	 *
	 * @param methodId 本地方法ID
	 * @param args     方法调用参数
	 */
	Object invoke(int methodId, Object[] args) throws Throwable;

	/**
	 * DynamicInvoker需要通过Spring容器获取要调用的本地bean<br>
	 * 因为在编译和创建这一动态代理时，要调用的本地bean还没被创建好
	 */
	void setApplicationContext(AppContext ctx);

}

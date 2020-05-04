package com.github.javassist.dynamic;


import com.github.javassist.dynamic.service.HelloService;
import com.github.javassist.dynamic.service.IHelloService;
import com.github.javassist.dynamic.service.Person;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Map;

public class DynamicInvokerTest {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public static void main(String[] args) throws Throwable {
		HelloService helloService = new IHelloService();
		AppContext appContext = new AppContext();
		appContext.setBean("helloService", helloService);

		RpcContext rpcContext = new RpcContext();
		rpcContext.setAppContext(appContext);

		rpcContext.addMethod(1, HelloService.class.getMethod("sayHello"));
		rpcContext.addMethod(2, HelloService.class.getMethod("doProcess", Integer.class));
		rpcContext.addMethod(3, HelloService.class.getMethod("setPerson", Person.class, Integer.class));

		Map<String, RpcMethodConfig> rpcMethodConfigMap = Maps.newHashMap();
		RpcMethodConfig sayHelloMethod = new RpcMethodConfig();
		sayHelloMethod.setRelativeId(1);
		rpcMethodConfigMap.put("sayHelloMethod", sayHelloMethod);

		RpcMethodConfig doProcessMethod = new RpcMethodConfig();
		doProcessMethod.setRelativeId(2);
		rpcMethodConfigMap.put("doProcessMethod", doProcessMethod);

		RpcMethodConfig setPersonMethod = new RpcMethodConfig();
		setPersonMethod.setRelativeId(3);
		rpcMethodConfigMap.put("setPersonMethod", setPersonMethod);

		RpcServiceConfig rpcServiceConfig = new RpcServiceConfig();
		rpcServiceConfig.setRef("helloService");
		rpcServiceConfig.setInterfaceName(HelloService.class.getCanonicalName());
		rpcServiceConfig.setMethodConfigMap(rpcMethodConfigMap);

		JavassistMaker javassistMaker = new JavassistMaker();
		javassistMaker.makeDynamicInvoker(rpcContext, rpcServiceConfig);

		DynamicInvoker dynamicInvoker = rpcContext.getDynamicInvoker(1);
		dynamicInvoker.invoke(1, null);
		LOGGER.info("result:{}", dynamicInvoker.invoke(2, new Object[]{3}));

		Person person = new Person();
		person.setName("hangs.zhang");
		person = (Person) dynamicInvoker.invoke(3, new Object[]{person, 23});
		LOGGER.info("person:{}", person);
	}

}

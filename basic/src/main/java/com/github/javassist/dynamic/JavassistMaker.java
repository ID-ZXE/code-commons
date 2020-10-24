package com.github.javassist.dynamic;

import javassist.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavassistMaker {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String NEW_LINE = "\n";

	private static final String BLANK = "\t";

	private static final String BLANK2 = "\t\t";

	private final Map<String, String> typeMap = new HashMap<>();

	public JavassistMaker() {
		this.typeMap.put("byte", "java.lang.Byte");
		this.typeMap.put("boolean", "java.lang.Boolean");
		this.typeMap.put("short", "java.lang.Short");
		this.typeMap.put("int", "java.lang.Integer");
		this.typeMap.put("long", "java.lang.Long");
		this.typeMap.put("float", "java.lang.Float");
		this.typeMap.put("double", "java.lang.Double");
	}

	public void makeDynamicInvoker(RpcContext rpcContext, RpcServiceConfig serviceConfig) throws Exception {
		ClassPool clsPool = ClassPool.getDefault();
		this.makeDynamicInvoker(rpcContext, serviceConfig, clsPool);
	}

	protected void makeDynamicInvoker(RpcContext rpcContext, RpcServiceConfig serviceConfig, ClassPool classPool) throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("Proxy$").append(serviceConfig.getRef());
		String clsName = sb.toString();
		LOGGER.info("Creating service proxy class {}", clsName);

		sb.setLength(0);
		sb.append(serviceConfig.getInterfaceClazz().getPackage().getName()).append(".").append(clsName);
		CtClass ctCls = classPool.makeClass(sb.toString());
		// 接口设置
		ctCls.addInterface(classPool.getCtClass("com.github.javassist.dynamic.DynamicInvoker"));
		// service
		ctCls.addField(CtField.make("private Object serviceBean;" + NEW_LINE, ctCls));
		// 注入上下文
		ctCls.addField(CtField.make("private " + AppContext.class.getName() + " ctx;", ctCls));
		sb.setLength(0);
		sb.append("public void setApplicationContext(").append(AppContext.class.getName()).append(" ctx) {").append(NEW_LINE);
		sb.append(BLANK).append("this.ctx = ctx;").append(NEW_LINE);
		sb.append("}");
		ctCls.addMethod(CtNewMethod.make(sb.toString(), ctCls));
		// invoke的实现
		sb.setLength(0);
		sb.append("public Object invoke(int methodId, Object[] args) throws Throwable {").append(NEW_LINE);
		sb.append(BLANK).append("if (serviceBean == null) {").append(NEW_LINE);
		// ref-context
		sb.append(BLANK2).append("serviceBean = ctx.getBean(\"").append(serviceConfig.getRef()).append("\");").append(NEW_LINE);
		sb.append(BLANK).append("}").append(NEW_LINE);
		sb.append(BLANK).append(serviceConfig.getInterfaceName()).append(" ").append(serviceConfig.getRef());
		sb.append(" = (").append(serviceConfig.getInterfaceName()).append(") serviceBean;").append(NEW_LINE);
		sb.append(BLANK).append("switch(methodId) {").append(NEW_LINE);
		List<Integer> idLt = new ArrayList<>();
		Map<String, RpcMethodConfig> methodConfigMap = serviceConfig.getMethodConfigMap();
		for (RpcMethodConfig rpcMethodConfig : methodConfigMap.values()) {
			Integer id = rpcMethodConfig.getRelativeId();
			idLt.add(id);
			Method method = rpcContext.getMethod(id);
			sb.append(BLANK).append("case ").append(id).append(":").append(NEW_LINE);
			String rt = method.getReturnType().getName();
			if ("void".equals(rt)) {
				sb.append(BLANK2);
			} else if (typeMap.containsKey(rt)) {
				sb.append(BLANK2).append("return ").append(typeMap.get(rt)).append(".valueOf(");
			} else {
				sb.append(BLANK2).append("return ");
			}
			sb.append(serviceConfig.getRef()).append(".").append(method.getName()).append("(");
			Class<?>[] pta = method.getParameterTypes();
			for (int i = 0; i < pta.length; i++) {
				String t = pta[i].getName();
				if (typeMap.containsKey(t)) {
					// 如果参数是基本数据类型，其包装类不可能为null，强制转换即可
					sb.append("((").append(typeMap.get(t)).append(") args[").append(i).append("]).").append(t).append("Value(), ");
				} else {
					sb.append("(");
					int d = t.lastIndexOf("[") + 1;
					if (d > 0) {
						String a = t.substring(0, d).replaceAll("\\[", "[]");
						t = t.substring(d);
						switch (t) {
							case "B":
								sb.append("byte");
								break;
							case "I":
								sb.append("int");
								break;
							case "S":
								sb.append("short");
								break;
							case "J":
								sb.append("long");
								break;
							case "F":
								sb.append("float");
								break;
							case "D":
								sb.append("double");
								break;
							case "Z":
								sb.append("boolean");
								break;
							default:
								// Lxx.xx.xx; 去掉第一个字符L和最后一个字符;
								t = t.substring(1, t.length() - 1);
								sb.append(t);
								break;
						}
						sb.append(a);
					} else {
						sb.append(t);
					}
					sb.append(") args[").append(i).append("], ");
				}
			}
			if (pta.length > 0) {
				sb.setLength(sb.length() - 2);
			}
			if ("void".equals(rt)) {
				sb.append(");").append(NEW_LINE).append(BLANK2).append("return null;").append(NEW_LINE);
			} else if (typeMap.containsKey(rt)) {
				sb.append("));").append(NEW_LINE);
			} else {
				sb.append(");").append(NEW_LINE);
			}
		}
		sb.append(BLANK).append("default:").append(NEW_LINE);
		sb.append(BLANK2).append("throw new IllegalStateException(");
		sb.append("\"Unknown method id \".concat(String.valueOf(methodId)));").append(NEW_LINE);
		sb.append(BLANK).append("}").append(NEW_LINE);
		sb.append("}").append(NEW_LINE);

		LOGGER.info("\n{}", sb.toString());
		try {
			ctCls.addMethod(CtMethod.make(sb.toString(), ctCls));
		} catch (CannotCompileException e) {
			LOGGER.error("Can not make invoker for {}", serviceConfig, e);
			return;
		}
		// 设置context
		DynamicInvoker invoker = (DynamicInvoker) ctCls.toClass().newInstance();
		invoker.setApplicationContext(rpcContext.getApplicationContext());
		// 缓存
		for (Integer id : idLt) {
			rpcContext.putDynamicInvoker(id, invoker);
			LOGGER.info("Cache dynamic invoker of method {}.", id);
		}
	}

}

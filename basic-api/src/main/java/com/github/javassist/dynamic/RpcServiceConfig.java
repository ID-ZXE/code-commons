package com.github.javassist.dynamic;

import lombok.Data;

import java.util.Map;

@Data
public class RpcServiceConfig {

	private String ref;

	private boolean enable = true;

	private String interfaceName;

	private Map<String, RpcMethodConfig> methodConfigMap;

	public Class<?> getInterfaceClazz() throws ClassNotFoundException {
		return Class.forName(this.interfaceName);
	}

}

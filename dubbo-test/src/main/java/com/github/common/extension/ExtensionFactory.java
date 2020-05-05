package com.github.common.extension;

/**
 * ExtensionFactory
 */
@SPI
public interface ExtensionFactory {

    <T> T getExtension(Class<T> type, String name);

}

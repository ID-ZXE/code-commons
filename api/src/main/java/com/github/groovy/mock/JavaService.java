package com.github.groovy.mock;

/**
 * @author plum-wine
 */
public class JavaService {

    public void echo() {
        System.out.println("hello world");
    }

    public String echo(String message) {
        return message;
    }

    public boolean testCondition(boolean condition) {
        return condition;
    }

}

package com.github.javassist.dynamic.service;

/**
 * @author hangs.zhang
 * @date 2020/05/04 19:00
 * *****************
 * function:
 */
public interface HelloService {

    void sayHello();

    String doProcess(Integer id);

    Person setPerson(Person person, Integer age);

}

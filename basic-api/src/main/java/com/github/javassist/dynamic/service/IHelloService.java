package com.github.javassist.dynamic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 2020/05/04 19:01
 * *****************
 * function:
 */
public class IHelloService implements HelloService {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Override
    public void sayHello() {
        LOGGER.info("hello world");
    }

    @Override
    public String doProcess(Integer id) {
        return id + ":doProcess";
    }

    @Override
    public Person setPerson(Person person, Integer age) {
        person.setAge(age);
        return person;
    }

}

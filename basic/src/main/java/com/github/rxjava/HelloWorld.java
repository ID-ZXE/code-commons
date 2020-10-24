package com.github.rxjava;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;

/**
 * @author hangs.zhang
 * @date 19-8-1 下午5:35
 * *********************
 * function:
 */
public class HelloWorld {

    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public static void hello(String... names) {
        /**
         * Flowable是2.x版本新增的
         */
        Flowable.fromArray(names).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                logger.info(s);
            }
        });

        /**
         * Observable是旧版本的
         */
        Observable.fromArray(names).blockingSubscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                logger.info(s);
            }
        });
    }

    public static void main(String[] args) {
        hello("Hello World!");

        Disposable subscribe = Observable.just("Hello", "World", "Hello World")
                .map(String::length)
                .filter(e -> e > 5)
                .subscribe(e -> logger.info("got len " + e + " @" + Thread.currentThread().getName()));

    }

}

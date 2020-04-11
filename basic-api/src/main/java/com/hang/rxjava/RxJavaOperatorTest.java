package com.hang.rxjava;

import io.reactivex.*;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 19-8-5 下午3:52
 * *********************
 * function:
 */
public class RxJavaOperatorTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Test
    public void map() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                LOGGER.info("emitter thread:{}", Thread.currentThread().getName());

                emitter.onNext("zhang");
                emitter.onNext("xxx");
                emitter.onNext("yyy");
                emitter.onNext("zzz");
            }
        }).subscribeOn(Schedulers.single())
                .map(new Function<String, String>() {
                    @Override
                    public String apply(String s) throws Exception {
                        return s;
                    }
                })
                .filter(e -> !e.startsWith("zh"))
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        LOGGER.info("result:{} @{}", s, Thread.currentThread().getName());
                    }
                });
        SleepUtils.sleep(TimeUnit.MILLISECONDS, 1000);
    }

    @Test
    public void flatmap() {
        Observable.create((ObservableOnSubscribe<Integer>) emitter -> {
            emitter.onNext(1);
            emitter.onNext(2);
            emitter.onNext(3);
        }).flatMap(new Function<Integer, ObservableSource<String>>() {
            @Override
            public ObservableSource<String> apply(Integer data) throws Exception {
                final List<String> list = new ArrayList<>();
                for (int i = 0; i < 3; i++) {
                    list.add("I am value " + data);
                }
                return Observable.fromIterable(list).delay(10, TimeUnit.MILLISECONDS);
            }
        }).subscribe(s -> LOGGER.info("accept:\t{}", s));
        SleepUtils.sleep(TimeUnit.MILLISECONDS, 1000);
    }

    @Test
    public void timeout() {
        Observable<String> observable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                // do nothing
                // throw exception
                throw new RuntimeException("error");
            }
        });

        observable.timeout(50, TimeUnit.MILLISECONDS)
                .onErrorReturn(new Function<Throwable, String>() {
                    @Override
                    public String apply(Throwable throwable) throws Exception {
                        LOGGER.info("timeout,reason:{}", throwable.getMessage());
                        return "default";
                    }
                })
                .subscribe(new Observer<String>() {
                    private Disposable disposable;

                    @Override
                    public void onSubscribe(Disposable d) {
                        this.disposable = d;
                    }

                    @Override
                    public void onNext(String s) {
                        LOGGER.info("accept:{}", s);
                        disposable.dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        SleepUtils.sleep(TimeUnit.MILLISECONDS, 1000);
    }

}

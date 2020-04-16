package com.github.rxjava;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author hangs.zhang
 * @date 19-8-2 上午8:59
 * *********************
 * function:
 */
public class ObservableTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * 发布订阅
     * 切断订阅
     */
    @Test
    public void observableOnSubscribe() {
        // 第一步：初始化Observable
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                LOGGER.info("Observable emit 1");
                e.onNext(1);
                LOGGER.info("Observable emit 2");
                e.onNext(2);
                LOGGER.info("Observable emit 3");
                e.onNext(3);
                e.onComplete();
                LOGGER.info("Observable emit 4");
                e.onNext(4);
            }
            // 第三步：订阅
        }).subscribe(new Observer<Integer>() {
            // 第二步：初始化Observer
            private int i;

            private Disposable mDisposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mDisposable = d;
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                LOGGER.info("onNext value : " + integer);
                i++;
                if (i == 2) {
                    // 在RxJava 2.x 中,新增的Disposable可以做到切断的操作,让Observer观察者不再接收上游事件
                    LOGGER.info("onNext : isDisposable : true");
                    mDisposable.dispose();
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                LOGGER.info("onError value : " + e.getMessage());
            }

            @Override
            public void onComplete() {
                LOGGER.info("onComplete");
            }
        });

        SleepUtils.sleep(TimeUnit.MILLISECONDS, 1000);
    }

    /**
     * doOn与on
     * 简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程
     * 多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略
     * 但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次
     *
     * RxJava线程与线程池
     * 线程切换
     */
    @Test
    public void threadChangeTest() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);

        Observable.create(new ObservableOnSubscribe<Integer>() {
            // 发射
            @Override
            public void subscribe(@NonNull ObservableEmitter<Integer> e) throws Exception {
                LOGGER.info("Observable thread is : " + Thread.currentThread().getName());
                e.onNext(1);
                e.onComplete();
            }
            // 指定subscribe发生的线程
            // Schedulers.io() RxJava准备的为io密集型操作准备的线程
        }).subscribeOn(Schedulers.io())
                // 指定下游observe回调的线程
                // Schedulers.newThread() RxJava准备的一个普通的新线程
                .observeOn(Schedulers.newThread())
                // 订阅
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LOGGER.info("After observeOn(newThread),Current thread is " + Thread.currentThread().getName());
                    }
                    // Schedulers.computation() RxJava为cpu密集型操作准备的一个线程
                }).observeOn(Schedulers.computation())
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer integer) throws Exception {
                        LOGGER.info("After observeOn(computation),Current thread is " + Thread.currentThread().getName());
                    }
                }).observeOn(Schedulers.from(executorService))
                .doOnNext(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LOGGER.info("After observeOn(from),Current thread is " + Thread.currentThread().getName());
                    }
                }).observeOn(Schedulers.single())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        LOGGER.info("After observeOn(single),Current thread is " + Thread.currentThread().getName());
                    }
                });


        SleepUtils.sleep(TimeUnit.MILLISECONDS, 1000);
    }

    /**
     * interval 轮询 适合心跳任务
     */
    @Test
    public void intervalTest() {
        // 2s发送一次
        Disposable disposable = Flowable.interval(2, TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long second) throws Exception {
                        LOGGER.info("accept-doOnNext second " + second);
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long second) throws Exception {
                        LOGGER.info("accept-subscribe second " + second);
                    }
                });

        SleepUtils.sleep(TimeUnit.MILLISECONDS, 20000);
        // 停止轮训
        disposable.dispose();
    }

}

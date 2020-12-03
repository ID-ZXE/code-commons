package com.github.java8.async;

import org.junit.Test;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author hangs.zhang
 * @date 2019/3/22.
 * *****************
 * function:
 */
public class CompletableFutureDemo {

    @Test
    public void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        new Thread(() -> {
            sleep(1000);
            // int i = 1 / 0;
            // 主动触发计算
            completableFuture.complete("ok");
        }).start();
        // 如果没有意外，上面发的代码工作得很正常。但是，如果任务执行过程中产生了异常会怎样呢
        // 非常不幸，这种情况下你会得到一个相当糟糕的结果：异常会被限制在执行任务的线程的范围内，最终会杀死该线程，而这会导致等待get方法返回结果的线程永久地被阻塞
        System.out.println(completableFuture.get());
    }

    @Test
    public void test2() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = new CompletableFuture<>();
        new Thread(() -> {
            try {
                sleep(1000);
                int i = 1 / 0;
            } catch (Exception e) {
                future.completeExceptionally(e);
            }
            future.complete("ok");
        }).start();
        System.out.println(future.get());
    }

    @Test
    public void test3() {
        long start = System.currentTimeMillis();
        List<String> list = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Integer> taskList = Arrays.asList(2, 1, 3, 4, 5, 6, 7, 8, 9, 10);
        // 全流式处理转换成CompletableFuture[]+组装成一个无返回值CompletableFuture，join等待执行完毕。返回结果whenComplete获取
        CompletableFuture[] cfs = taskList.stream().map(i -> CompletableFuture.supplyAsync(() -> calc(i), executorService)
                .thenApply(h -> Integer.toString(h))
                .whenComplete((s, e) -> {
                    System.out.println("任务 " + s + " 完成! result= " + s + "，异常 e=" + e + ",  " + new Date());
                    list.add(s);
                })
        ).toArray(CompletableFuture[]::new);
        // 所有任务完之后 退出阻塞
        // CompletableFuture.allOf(cfs).join();
        // 任意一个完成之后 退出阻塞
        CompletableFuture.anyOf(cfs).join();
        System.out.println("cost : " + (System.currentTimeMillis() - start));
    }

    public static Integer calc(Integer i) {
        if (i == 1) {
            sleep(3000);
        } else if (i == 5) {
            sleep(5000);
        } else {
            sleep(1000);
        }
        System.out.println("task线程：" + Thread.currentThread().getName() + "任务i=" + i + ",完成！+" + new Date());
        return i;
    }

    @Test
    public void test4() {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        CompletableFuture.supplyAsync(new Supplier<String>() {
            @Override
            public String get() {
                return "hello";
            }
            // 放入线程池
            // thenApply 继续进行操作
            // 类似的还有 thenAcceptAsync 异步进行
        }, executorService).thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                return s + " world";
            }
            // thenAccept 消费上一步执行的结果
            // 无返回值
        }).thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
            // 对上一步执行结果不关心 执行下一个操作
        }).thenRun(new Runnable() {
            @Override
            public void run() {
                System.out.println("---");
            }
            // join 阻塞直到完成
        }).join();
    }

    // 结合两个CompletionStage的结果，进行转化后返回
    // 有返回值
    @Test
    public void thenCombine() {
        String result = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> s1 + " " + s2).join();
        System.out.println(result);
    }

    // 结合两个CompletionStage的结果，进行消耗
    // 无返回值
    @Test
    public void thenAcceptBoth() {
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "hello";
        }).thenAcceptBoth(CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "world";
        }), (s1, s2) -> System.out.println(s1 + " " + s2)).join();
    }

    // 不关心这两个CompletionStage的结果，只关心这两个CompletionStage执行完毕，之后在进行操作（Runnable）
    @Test
    public void runAfterBoth() {
        CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "s1";
        }).runAfterBothAsync(CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "s2";
        }), () -> System.out.println("hello world")).join();
    }

    // 两个CompletionStage，不关心这两个CompletionStage的结果，任何一个完成了都会执行下一步的操作（Runnable）
    @Test
    public void runAfterEither() {
        CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "s1";
        }).runAfterEither(CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "s2";
        }), () -> System.out.println("hello world")).join();
    }

    // 两种渠道完成同一个事情，所以就可以调用这个方法，找一个最快的结果进行处理。
    // 有返回值
    @Test
    public void applyToEither() {
        String result = CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "s1";
        }).applyToEither(CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "hello world";
        }), s -> s).join();
        System.out.println(result);
    }

    // 两个CompletionStage，谁计算的快，我就用那个CompletionStage的结果进行下一步的消耗操作
    // 无返回值
    @Test
    public void acceptEither() {
        CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "s1";
        }).acceptEither(CompletableFuture.supplyAsync(() -> {
            sleep(2000);
            return "hello world";
        }), System.out::println).join();
    }

    // 当运行时出现了异常，可以通过exceptionally进行补偿
    @Test
    public void exceptionally() {
        String result = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            if (true) throw new RuntimeException("测试一下异常情况");

            return "s1";
        }).exceptionally(e -> {
            System.out.println(e.getMessage());
            return "hello world";
        }).join();
        System.out.println(result);
    }

    // 当运行完成时，对结果的记录。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断
    // 这里为什么要说成记录，因为这几个方法都会返回CompletableFuture，
    // 当Action执行完毕后它的结果返回原始的CompletableFuture的计算结果或者返回异常。所以不会对结果产生任何的作用。
    @Test
    public void whenComplete() throws ExecutionException, InterruptedException {
        CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            if (false) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).whenComplete((s, throwable) -> {
            System.out.println(s);
            if (Objects.nonNull(throwable)) {
                System.out.println(throwable.getMessage());
            }
        }).exceptionally(throwable -> {
            System.out.println(throwable.getMessage());
            return "hello world";
        });
        System.out.println(future.get());
    }

    // 运行完成时，对结果的处理。这里的完成时有两种情况，一种是正常执行，返回值。另外一种是遇到异常抛出造成程序的中断。
    @Test
    public void handle() {
        String result = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            //出现异常
            if (true) {
                throw new RuntimeException("测试一下异常情况");
            }
            return "s1";
        }).handle((value, throwable) -> {
            if (throwable != null) {
                return "hello world";
            }
            return value;
        }).join();
        System.out.println(result);
    }

    private static void sleep(long time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
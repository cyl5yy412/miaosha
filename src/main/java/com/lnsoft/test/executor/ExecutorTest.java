package com.lnsoft.test.executor;

import java.util.concurrent.*;

/**
 * Created By Chr on 2019/4/22.
 */
public class ExecutorTest {

    public static void main(String args[]) {
        ExecutorService executorService = Executors.newFixedThreadPool(3);




        //submit
        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("b");
                return "b";
            }
        });


        //futureTask
        FutureTask<String> future2 = new FutureTask<String>(new Callable<String>() {
            public String call() {
                System.out.println("c");
                return "c";
            }
        });


        //future
        Future<String> future3 = executorService.submit((Callable<String>) () -> {
            System.out.println("a");
            return "a";
        });
        executorService.execute((Runnable) future3);

    }


}

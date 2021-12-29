package forkjoin;

import java.util.concurrent.ForkJoinPool;

/**
 * TODO
 *
 * @author suwei
 * @version 1.0
 * @date 2021/12/29 16:27
 */
public class ForkJoinDemo {
    public static void main(String[] args) {
        // 实例化ForkJoinPool
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        // 实例化有返回值的自定义RecursiveTask
        CustomRecursiveTask customRecursiveTask = new CustomRecursiveTask("abcde");
        // 调用ForkJoinPool的execute方法将任务提交给线程池
        forkJoinPool.execute(customRecursiveTask);
        // 调用ForkJoinTask的join()方法触发连接操作的执行，并获得返回值
        String result = customRecursiveTask.join();
        System.out.println("CustomRecursiveTask result = " + result);

        // 例化无返回值的自定义RecursiveAction
        CustomRecursiveAction customRecursiveAction = new CustomRecursiveAction("abcde");
        // 调用ForkJoinPool的submit方法将任务提交给线程池
        forkJoinPool.submit(customRecursiveAction);
        // 调用ForkJoinTask的join()方法触发连接操作的执行，无返回值
        customRecursiveAction.join();

        // 实例化有返回值的自定义RecursiveTask
        CustomRecursiveTask customRecursiveTask1 = new CustomRecursiveTask("l拉不拉米");
        // 调用ForkJoinPool的invoke方法将任务提交给线程池，并自动执行
        String result1 = forkJoinPool.invoke(customRecursiveTask1);
        System.out.println("CustomRecursiveTask1 result = " + result1);

        // 例化无返回值的自定义RecursiveAction
        CustomRecursiveAction customRecursiveAction1 = new CustomRecursiveAction("l拉不拉米");
        // 调用ForkJoinTask的fork方法将任务提交给线程池
        customRecursiveAction1.fork();
        // 调用ForkJoinTask的join()方法触发连接操作的执行，无返回值
        customRecursiveAction1.join();
    }
}

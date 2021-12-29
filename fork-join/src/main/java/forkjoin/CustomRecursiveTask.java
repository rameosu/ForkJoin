package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

/**
 * TODO
 *
 * @author suwei
 * @version 1.0
 * @date 2021/12/29 15:48
 */
public class CustomRecursiveTask extends RecursiveTask<String> {

    private String workload;

    private static final int THRESHOLD = 4;

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public CustomRecursiveTask(String workload) {
        this.workload = workload;
    }

    /**
     * 演示ForkJoin框架的分叉行为，当字符串变量workload的长度大于指定的阈值THRESHOLD时，
     * 使用createSubTask()进行任务拆分，通过调用join()触发执行。
     * 使用Java Stream Api 汇总子任务执行的结果
     */
    @Override
    protected String compute() {
        String result;
        if (workload.length() > THRESHOLD) {
            // 将任务列表提交给ForkJoinTask，invokeAll() 方法将子任务提交到公共池并返回一个 Future 列表，调用join()触发连接操作的执行
            result = ForkJoinTask.invokeAll(createSubTask()).stream().map(ForkJoinTask::join).collect(Collectors.joining(""));
        } else {
            result = process(workload);
        }
        System.out.println(result);
        return result;
    }

    /**
     * 递归的创建子任务，子任务将提交给ForkJoinTask，由ForkJoinTask调用重写的compute()
     *
     * @return
     */
    private List<CustomRecursiveTask> createSubTask() {
        List<CustomRecursiveTask> subTasks = new ArrayList<>();

        String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2);

        subTasks.add(new CustomRecursiveTask(partOne));
        subTasks.add(new CustomRecursiveTask(partTwo));

        return subTasks;
    }

    /**
     * 处理ForkJoinTask执行结果
     *
     * @param work
     */
    private String process(String work) {
        return work;
    }

    public static void main(String[] args) {
        // 初始化workload只用4个字符，不会执行分叉逻辑
        CustomRecursiveTask action = new CustomRecursiveTask("abcd");
        action.compute();

        // 第二次执行使用5个字符，执行分叉逻辑
        action.setWorkload("abcde");
        action.compute();
    }
}

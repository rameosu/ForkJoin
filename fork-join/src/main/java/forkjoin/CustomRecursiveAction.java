package forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;

/**
 * TODO
 *
 * @author suwei
 * @version 1.0
 * @date 2021/12/29 15:04
 */
public class CustomRecursiveAction extends RecursiveAction {

    private String workload;

    private static final int THRESHOLD = 4;

    public String getWorkload() {
        return workload;
    }

    public void setWorkload(String workload) {
        this.workload = workload;
    }

    public CustomRecursiveAction(String workload) {
        this.workload = workload;
    }

    /**
     * 为了演示ForkJoin框架的分叉行为，当字符串变量workload的长度大于指定的阈值THRESHOLD时，
     * 使用createSubTask()进行任务拆分
     */
    @Override
    protected void compute() {
        if (workload.length() > THRESHOLD) {
            // 将任务列表提交给ForkJoinTask
            ForkJoinTask.invokeAll(createSubTask());
        } else {
            print(workload);
        }
    }

    /**
     * 创建子任务，子任务将提交给ForkJoinTask，由ForkJoinTask调用重写的compute()
     * @return
     */
    private List<CustomRecursiveAction> createSubTask() {
        List<CustomRecursiveAction> subTasks = new ArrayList<>();

        String partOne = workload.substring(0, workload.length() / 2);
        String partTwo = workload.substring(workload.length() / 2);

        subTasks.add(new CustomRecursiveAction(partOne));
        subTasks.add(new CustomRecursiveAction(partTwo));

        return subTasks;
    }

    /**
     * 打印ForkJoinTask执行结果
     * @param work
     */
    private void print(String work) {
        System.out.println("This result - (" + work + ") - was processed by "
                + Thread.currentThread().getName());
    }

    public static void main(String[] args) {
        // 初始化workload只用4个字符，不会执行分叉逻辑
        CustomRecursiveAction action = new CustomRecursiveAction("abcd");
        action.compute();

        // 第二次执行使用5个字符，执行分叉逻辑
        action.setWorkload("abcde");
        action.compute();
    }
}

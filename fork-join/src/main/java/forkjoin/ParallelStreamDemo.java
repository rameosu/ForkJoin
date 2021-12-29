package forkjoin;

import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author suwei
 * @version 1.0
 * @date 2021/12/29 17:46
 */
public class ParallelStreamDemo {
    public static void main(String[] args) {
        List<Integer> listOfNumbers = Arrays.asList(1, 2, 3, 4);
        // 串行流
        int sum1 = listOfNumbers.stream().reduce(5, Integer::sum);
        // 并行流
        int sum2 = listOfNumbers.parallelStream().reduce(5, Integer::sum);
        System.out.println("串行流执行结果：" + sum1);
        System.out.println("并行流执行结果：" + sum2);

        int sum3 = listOfNumbers.parallelStream().reduce(0, Integer::sum) + 5;
        System.out.println("并行流之外加5执行结果：" + sum3);
    }
}

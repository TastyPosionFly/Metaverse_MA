package PThread;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

//用于FindMostNumber的多线程运行方法，提高运行速度
public class FindMostNumberPThread implements Runnable{

    private int[] inputArray;
    private int startIndex;
    private int endIndex;
    private Map<String, AtomicInteger> combinationCount;

    public FindMostNumberPThread(int[] inputArray,int startIndex,int endIndex,Map<String,AtomicInteger> combinationCount){
        this.inputArray = inputArray;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.combinationCount = combinationCount;
    }

    @Override
    public void run() {
        for (int i = startIndex;i<endIndex;i++){
            StringBuilder stringBuilder = new StringBuilder();

            for (int j=0;j<inputArray.length;j++){
                stringBuilder.append(inputArray[j]);
                String combinationStr = stringBuilder.toString();

                /*
                combinationCount.computeIfAbsent(combinationStr, k -> new AtomicInteger()).incrementAndGet(); 这行代码使用了Java 8中的 computeIfAbsent 方法，它的作用如下：

                combinationCount 是一个 Map，用于存储数字组合和它们出现的次数。
                computeIfAbsent 方法的第一个参数是一个键（combinationStr），它用于检查映射中是否已存在该键。
                第二个参数是一个 lambda 表达式，它定义了如果键不存在时应该执行的操作。这个 lambda 表达式接收一个参数 k，表示要计算的键，然后返回一个新的 AtomicInteger 对象，用于初始化或替换映射中的值。
                incrementAndGet() 方法用于增加 AtomicInteger 对象的值，表示增加数字组合的出现次数。
                所以，这一行代码的目的是：

                如果 combinationStr 在 combinationCount 映射中已存在，就获取该键对应的 AtomicInteger 对象，并增加其值（即增加数字组合的出现次数）。
                如果 combinationStr 在 combinationCount 映射中不存在，就创建一个新的 AtomicInteger 对象并将其放入映射中，然后增加其值，这相当于初始化数字组合的出现次数为1。
                 */
                combinationCount.computeIfAbsent(combinationStr,k->new AtomicInteger(0)).incrementAndGet();
            }
        }
    }
}

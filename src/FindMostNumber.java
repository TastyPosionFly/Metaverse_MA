    import PThread.FindMostNumberPThread;
    import org.junit.Assert;
    import org.junit.Test;

    import java.util.HashMap;
    import java.util.Map;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;
    import java.util.concurrent.TimeUnit;
    import java.util.concurrent.atomic.AtomicInteger;

    //寻找数组中出现最多最长的数字组合,用于取代之前UndirectedGraph_Test中的
    //FindMaxAdjacentCombination方法
    public class FindMostNumber {

        public String FindCombination(int[] inputLists,boolean[] Marked){
            Map<String, AtomicInteger> combination = new HashMap<>();

            int maxLength = 0;//出现最长数字组合的长度
            String maxCombination = "0";//出现的最长的数字组合
            int availableCores = Runtime.getRuntime().availableProcessors();//获取可以使用的线程核数量

            //首先不考虑被标记后的特殊情况，首先进行一般情况的处理用多线程
            ExecutorService executorService= Executors.newFixedThreadPool(availableCores);

            for (int i=0;i<availableCores;i++){
                int startIndex = i*inputLists.length/availableCores;
                int endIndex = (i+1)*inputLists.length/availableCores;
                executorService.submit(new FindMostNumberPThread(inputLists,startIndex,endIndex,combination));
            }

            executorService.shutdown();

            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            }catch (InterruptedException e){
                e.printStackTrace();
            }

            //接下来考虑那些在第二次迭代开始的数组中那些包含了被标记数字的数组


            for (Map.Entry<String,AtomicInteger> entry:combination.entrySet()){
                String tempCombination = entry.getKey();
                int count = entry.getValue().get();


                if (count > combination.get(maxCombination).get() || (count == combination.get(maxCombination).get() && tempCombination.length()>maxLength)){
                    maxLength = tempCombination.length();
                    maxCombination = tempCombination;
                }
            }

            return maxCombination;
        }

        @Test
        public void FindMostNumber_Test(){
            int[] testLists = {5,6,7,8,8,7,6,5,2,4,4,2,3,4,5,6,7,8,5,5,7,8,6,0};
            boolean[] testBoolean = {true};
            Assert.assertEquals("5678", FindCombination(testLists,testBoolean));
        }
    }

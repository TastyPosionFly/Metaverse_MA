package Grid;

import Grid.PThread.GenerateMatrix;
import Grid.PThread.MatrixCheck;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class JGridN {

    private void PrintMatrix(int[][] inputMatrix){
        for (int i=0;i<inputMatrix.length;i++){
            for (int j=0;j<inputMatrix[0].length;j++){
                System.out.print(inputMatrix[i][j]+"  ");
            }
            System.out.println(" ");
        }
        System.out.println("  ");
    }


    //测试GenerateMatrix函数是否能正常工作
    @Test
    public void MatrixGenerateTest(){
        int n = 10;//决定要有多少个不同大小的矩阵
        ArrayList<int[][]> testMatrix = new ArrayList<>();
        int availableCores = Runtime.getRuntime().availableProcessors();//获取可以使用的线程核数量

        ExecutorService executorService= Executors.newFixedThreadPool(availableCores);


        for (int i=1;i<=n;i++){
            int[][] tempMatrix = new int[i][i];
            testMatrix.add(tempMatrix);
            executorService.submit(new GenerateMatrix(i,tempMatrix));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        for (int[][] value:testMatrix){
            PrintMatrix(value);
        }
    }

    //测试检查功能是否能够正常工作
    @Test
    public void MatrixCheck(){
        int n = 500;//决定要有多少个不同大小的矩阵
        ArrayList<int[][]> testMatrix = new ArrayList<>();
        int availableCores = Runtime.getRuntime().availableProcessors();//获取可以使用的线程核数量

        ExecutorService executorService= Executors.newFixedThreadPool(availableCores);


        for (int i=1;i<=n;i++){
            int[][] tempMatrix = new int[i][i];
            testMatrix.add(tempMatrix);
            executorService.submit(new GenerateMatrix(i,tempMatrix));
        }

        executorService.shutdown();

        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        DataContainer[] dataContainers = new DataContainer[n];
        int i=0;

        for (int[][] array:testMatrix){
            dataContainers[i] = new DataContainer(array);
            i++;
        }

        ExecutorService executorService1= Executors.newFixedThreadPool(availableCores);

        for (int j=0;j< dataContainers.length;j++){
            executorService1.submit(new MatrixCheck(dataContainers[j]));
        }

        executorService1.shutdown();
        try {
            executorService1.awaitTermination(1,TimeUnit.SECONDS);
        }catch (InterruptedException e){
            e.printStackTrace();
        }

        for (DataContainer dataContainer:dataContainers){
            System.out.println(dataContainer.isCheck());
        }
    }

    @Test
    public void DataContainerTest(){
        int[][] test = new int[1][1];
        DataContainer dataContainer = new DataContainer(test);
        dataContainer.setCheck(true);
        System.out.println(dataContainer.isCheck());
    }
}

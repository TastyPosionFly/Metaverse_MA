import java.util.Scanner;
import java.util.ArrayList;


public class Main {

    public static int NodeSize = 10;//总共有点节点数

    public static void main(String[] args) {

        UndirectedGraph_Test undirectedGraphTest = new UndirectedGraph_Test();

        int[] Test =new int[10];

        for (int i=0;i<10;i++)
            Test[i]=(int)(Math.random()*10);

        undirectedGraphTest.ArrayProcessing(Test);

    }
}

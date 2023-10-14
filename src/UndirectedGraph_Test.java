import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UndirectedGraph_Test {

    private int[][] adjacencyMatrix;//用于存放无向图邻接表的二维矩阵
    private int[] Number_Value;//存放节点的值
    private boolean[] Marked;//是否被标记
    private int NodeNum;//总共的节点数,时刻在发生变化!!

    public UndirectedGraph_Test(){}

    //初始化数组
    public UndirectedGraph_Test(int NodeNum){
        adjacencyMatrix = new int[NodeNum][NodeNum];
        Number_Value = new int[NodeNum];
        Marked = new boolean[NodeNum];
        this.NodeNum = NodeNum;
    }

    //打印数组
    public void PrintGraph(int[] Numbers,int[][] AdjacencyMatrix){
        System.out.print("  ");
        for (int i=0;i<Numbers.length;i++){
            System.out.printf("%4d",Numbers[i]);
        }
        System.out.println("  ");

        for (int i=0;i<Numbers.length;i++){
            System.out.printf("%2d",Numbers[i]);
            for (int j=0;j<Numbers.length;j++)
                System.out.printf("%4d",AdjacencyMatrix[i][j]);
            System.out.println(" ");
        }

    }

    //设置节点的值,返回元组第一个是值，第二个是标记
    private ReturnClass SetValue(int[] NumberLists){
        int[] tempLists = new int[NumberLists.length];
        boolean[] tempBoolean = new boolean[NumberLists.length];

        for (int i=0;i<NumberLists.length;i++){
            tempLists[i] = NumberLists[i];
            tempBoolean[i] = false;
        }

        return new ReturnClass(tempLists,tempBoolean);
    }

    //设置无向图的邻接矩阵
    private int[][] SetEdge(int[] Numbers){

        int[][] AdjacencyMatrix = new int[Numbers.length][Numbers.length];

        for (int i=0;i<Numbers.length;i++)
            for (int j=0;j<Numbers.length;j++)
                AdjacencyMatrix[i][j] = 0;

        for (int i=1;i<Numbers.length-1;i++){
            int j=i-1,k=i+1;
            AdjacencyMatrix[i][j] = 1;
            AdjacencyMatrix[i][k] = 1;
        }

        for (int i=0;i<Numbers.length;i++) {
            if (i == 0){
                continue;
            }
            if (i == Numbers.length-1){
                continue;
            }
            AdjacencyMatrix[i][0] =1;
            AdjacencyMatrix[0][i] = 1;
            AdjacencyMatrix[Numbers.length-1][i] =1;
            AdjacencyMatrix[i][Numbers.length-1] = 1;
        }
        return AdjacencyMatrix;
    }

    private int[][] SetEdge_NoneSerial(int[] NumbersLists){

        int[][] adjacencyMatrix = new int[NumbersLists.length][NumbersLists.length];

        for(int i=0;i<NumbersLists.length;i++)
            for (int j=0;j<NumbersLists.length;j++){
                if (i==j) continue;
                double temp = Math.random();
                if (temp<0.5){
                    adjacencyMatrix[i][j]=0;
                    adjacencyMatrix[j][i]=0;
                }
                else {
                    adjacencyMatrix[i][j]=1;
                    adjacencyMatrix[j][i]=1;
                }
            }

        return adjacencyMatrix;

    }

    //获取每个数字所在的索引
    private ArrayList<Integer>[] GetIndex(int[] NumberLists){

        ArrayList<Integer>[] numbersIndex = new ArrayList[10];

        for (int i=0;i<numbersIndex.length;i++)
            numbersIndex[i] = new ArrayList<Integer>();

        for (int i=0;i<NumberLists.length;i++){
            numbersIndex[NumberLists[i]].add(i);
        }

        return numbersIndex;

    }

    //数组全排列,传入需要排列的数字的索引排列
    private int[][] Permute(ArrayList<Integer>[] NumberIndexLists){

        //所有的组合数量
        int totalCombination = 1;
        for (ArrayList<Integer> arrayList:NumberIndexLists) {
            //如果有一个没有索引说明不应该这样排列
            if (arrayList.size() == 0) {
                int[][] None = new int[][]{null};
                return None;
            }
            totalCombination *= arrayList.size();
        }
        int[][] PermuteLists = new int[totalCombination][NumberIndexLists.length];

        int[] ListSizes = new int[NumberIndexLists.length];

        for (int i=0;i<NumberIndexLists.length;i++){
            ListSizes[i]=NumberIndexLists[i].size();
        }

        for (int i=0;i<totalCombination;i++){
            int temp = i;
            for (int j=0;j<NumberIndexLists.length;j++){
                PermuteLists[i][j] = NumberIndexLists[j].get(temp%ListSizes[j]);
                temp /= ListSizes[j];
            }
        }

        return PermuteLists;
    }

    //计算矩阵中所有数字出现的次数
    private int[] CalculatingNumber(int[] Number){
        int[] number = new int[10];

        for (int i=0;i<Number.length;i++){
            number[Number[i]]++;
        }

        return number;
    }

    //查找出现最多的数字组合，*记得进行数组的处理，把标记的数字剔除出输入数组中*
    private int[] FindMaxAdjacentCombination(int[] arr,int length){
        Map<List<Integer>,Integer> combinationCountMap = new HashMap<>();

        for (int i=0;i< arr.length-length+1;i++){
            List<Integer> combination = new ArrayList<>();
            for (int j=0;j<length;j++){
                combination.add(arr[i+j]);
            }
            combinationCountMap.put(combination,combinationCountMap.getOrDefault(combination,0)+1);
        }

        int maxCount = 0;
        List<Integer> maxCombination = new ArrayList<>();
        for (Map.Entry<List<Integer>,Integer> entry:combinationCountMap.entrySet()){
            if (entry.getValue()>maxCount){
                maxCount = entry.getValue();
                maxCombination = new ArrayList<>(entry.getKey());
            }
        }

        int[] maxCombination_List = new int[length];
        for (int i=0;i<length;i++)
            maxCombination_List[i] = maxCombination.get(i);

        return maxCombination_List;
    }

    //将找到的节点标记使他不参与下一次寻找
    private ReturnClass Mark(ArrayList<Integer>[] arrayLists,int[] OriginNumbers,boolean[] OriginMark){
        int[] IndexOfNumber = new int[arrayLists.length*arrayLists[0].size()];
        int index_List=0;

        for (int i=0;i<arrayLists.length;i++){
            for (int j=0;j<arrayLists[0].size();j++){
                IndexOfNumber[index_List++] =arrayLists[i].get(j);
            }
        }

        //获取得到的余数,并且将已经找到的数字标记
        int sum=0;

        for (int index:IndexOfNumber){
            OriginMark[index] = true;
            sum += OriginNumbers[index];
        }

        //将余数插入到原先的数组中去
        UndirectedGraph_Test undirectedGraphTest =new UndirectedGraph_Test();
        ReturnClass TempObject = undirectedGraphTest.Insert(IndexOfNumber[IndexOfNumber.length-1],(sum%9),OriginNumbers,OriginMark);

        //将已经标记的数字剔除出现有的数组中，不参与下一次搜索?
        int[] TempNumbers = TempObject.getIntArray();
        boolean[] TempMark = TempObject.getBooleanArray();

        List<Integer> FinalNumber_List = new ArrayList<>();
        List<Boolean> FinalMark_List = new ArrayList<>();

        for (int i=0;i<TempNumbers.length;i++){
            if (TempMark[i])//TempMark[i] == true
                continue;
            FinalNumber_List.add(TempNumbers[i]);
            FinalMark_List.add(TempMark[i]);
        }

        //说明出现错误，因为两个数组不等长
        if (FinalMark_List.size() != FinalNumber_List.size())
            return new ReturnClass(null,null);

        int[] FinalNumbers = new int[FinalNumber_List.size()];
        boolean[] FinalMark =new boolean[FinalMark_List.size()];
        int[][] FinalAdjacencyMatrix;

        for (int i=0;i<FinalMark_List.size();i++){
            FinalNumbers[i] = FinalNumber_List.get(i);
            FinalMark[i] = FinalMark_List.get(i);
        }

        FinalAdjacencyMatrix = undirectedGraphTest.SetEdge(FinalNumbers);

        return new ReturnClass(FinalNumbers,FinalMark,FinalAdjacencyMatrix);
    }

    //*查找数组中的节点是否相互链接,多线程*
    private ArrayList<Integer> FindConnectedArray(int[][] arrays,int[][] adjacencyMatrix_temp){

        ArrayList<Integer> IndexInArrays = new ArrayList<Integer>();
        //多线程
        ExecutorService threadPool = Executors.newFixedThreadPool(16);

        for (int i=0;i<arrays.length;i++){
            int temp =i;
            threadPool.execute(()->{
                GraphChecker graphChecker = new GraphChecker(adjacencyMatrix_temp);
                boolean isConnected = graphChecker.isConnected(arrays[temp]);
                if (isConnected){
                    IndexInArrays.add(temp);
                }
            });
        }
        threadPool.shutdown();

        return IndexInArrays;
    }

    //数组插入
    private ReturnClass Insert(int StartNodeIndex,int Value,int[] ListNeedToInsert,boolean[] MarkNeedToInsert){

        int currentNum = ListNeedToInsert.length+1;
        int currentBoolean = MarkNeedToInsert.length+1;
        int[] currentList = new int[currentNum];
        boolean[] currentMark = new boolean[currentBoolean];
        int i;

        for (i=0;i<StartNodeIndex;i++){
            currentList[i] = ListNeedToInsert[i];
            currentMark[i] = MarkNeedToInsert[i];
        }

        currentList[i] = Value;
        currentMark[i] = false;

        for (;i<ListNeedToInsert.length;i++){
            currentList[i+1] = ListNeedToInsert[i];
            currentMark[i+1] = MarkNeedToInsert[i];
        }

        return new ReturnClass(currentList,currentMark);
    }

    private ArrayList<Integer>[] IntToArrayLists(int[] NumberLists){
        ArrayList<Integer>[] arrayLists = new ArrayList[NumberLists.length];

        for (int i=0;i<NumberLists.length;i++){
            arrayLists[i] = new ArrayList<>();
            arrayLists[i].add(NumberLists[i]);
        }

        return arrayLists;

    }

    private ArrayList<Integer>[] FindIndex(ArrayList<Integer> Index,ArrayList<Integer>[] NeedToSolve){

        ArrayList<Integer>[] IndexReturn = new ArrayList[Index.size()];

        for (int i=0;i<Index.size();i++){
            IndexReturn[i] = new ArrayList<>();
            int l = Index.get(i);
            for (int j=0;j<NeedToSolve[Index.get(i)].size();j++){
                IndexReturn[i].add(NeedToSolve[l].get(j));
            }
        }

        return IndexReturn;

    }

    //用于处理那些出现最多的数组所在的索引集合的函数
    private ArrayList<Integer>[] FindIndex(ArrayList<Integer>[] ArrayLists,int[] MostNumbers){
        ArrayList<Integer>[] FinalArrayLists = new ArrayList[MostNumbers.length];

        for (int i=0;i<MostNumbers.length;i++){
            FinalArrayLists[i] = new ArrayList<>();
            for (int j=0;j<ArrayLists[MostNumbers[i]].size();j++){
                FinalArrayLists[i].add(ArrayLists[MostNumbers[i]].get(j));
            }
        }

        return FinalArrayLists;
    }

    //外部调用这个函数执行所有程序
    public void ArrayProcessing(int[] NumbersLists){

        UndirectedGraph_Test undirectedGraphTest = new UndirectedGraph_Test(NumbersLists.length);
        //初始化数据
        ReturnClass object = undirectedGraphTest.SetValue(NumbersLists);
        int[] TempLists = object.getIntArray();//用于处理的数组
        boolean[] Marked = object.getBooleanArray();//用于处理的boolean数组

        int[] MiddleList;//中间数组
        int[][] MiddleLists;//中间数组二
        ArrayList<Integer>[] MiddleArrayLists,FinalArrayLists;
        ArrayList<Integer> FinalArrayListsIndex;

        //初始化无向图矩阵
        int[][] AdjacencyMatrix = undirectedGraphTest.SetEdge(NumbersLists);

        for (int i=2;i<=TempLists.length;i++){
            MiddleList = undirectedGraphTest.FindMaxAdjacentCombination(TempLists,i);
            MiddleArrayLists = undirectedGraphTest.GetIndex(TempLists);
            ArrayList<Integer>[] arrayLists = undirectedGraphTest.FindIndex(MiddleArrayLists,MiddleList);

            MiddleLists = undirectedGraphTest.Permute(arrayLists);

            FinalArrayListsIndex = undirectedGraphTest.FindConnectedArray(MiddleLists,AdjacencyMatrix);

            FinalArrayLists = undirectedGraphTest.FindIndex(FinalArrayListsIndex,MiddleArrayLists);

            ReturnClass returnClass = undirectedGraphTest.Mark(FinalArrayLists,MiddleList,Marked);

            TempLists = returnClass.getIntArray();
            Marked = returnClass.getBooleanArray();
            AdjacencyMatrix = returnClass.getAdjacencyMatrix();
        }


        //undirectedGraphTest.PrintGraph(NumbersLists.length);

    }

    public static void main(String[] args){
        int[] list = {0,1,3,2,5,6,9,7,8,5};
        int[] list_test;
        int NodeSize =10;
        boolean[] Test_Boolean = new boolean[10];

        Arrays.fill(Test_Boolean,false);

        UndirectedGraph_Test undirectedGraphTest = new UndirectedGraph_Test(NodeSize);
        list_test = undirectedGraphTest.FindMaxAdjacentCombination(list,1);

        for (int value:list_test)
            System.out.println(value);

        int[] Test_LIST = new int[NodeSize];
        for (int i = 0; i < NodeSize; i++) {
            Test_LIST[i] = (int)(Math.random()*10);
        }

        ReturnClass returnClass = undirectedGraphTest.SetValue(Test_LIST);
        int[] temp = returnClass.getIntArray();
        int[][] temp_A = undirectedGraphTest.SetEdge_NoneSerial(temp);
        undirectedGraphTest.PrintGraph(Test_LIST,temp_A);

        ReturnClass objects = undirectedGraphTest.Insert(2,2,Test_LIST,Test_Boolean);
        int[] Temp1 = objects.getIntArray();
        boolean[] booleans = objects.getBooleanArray();
        for (int value:Temp1)
            System.out.print(value+" ");

        System.out.println();
        System.out.println(booleans.length);

        ArrayList<Integer>[] Test;
        Test = undirectedGraphTest.GetIndex(undirectedGraphTest.Number_Value);

        ArrayList<Integer>[] Test_Second = new ArrayList[3];

        for (int i=0;i<3;i++){
            Test_Second[i] = new ArrayList<Integer>();
            Test_Second[i] =Test[((i*15)%9)];
        }

        int[][] test =undirectedGraphTest.Permute(Test_Second);

        int[] NumberCalculate = undirectedGraphTest.CalculatingNumber(undirectedGraphTest.Number_Value);

        return;
    }
}

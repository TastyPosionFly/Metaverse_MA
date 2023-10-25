package Grid.PThread;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GenerateMatrix implements Runnable{
    int MatrixSize;
    int [][] MatrixInput;

    public GenerateMatrix(int MatrixSize,int[][] MatrixInput){
        this.MatrixSize = MatrixSize;
        this.MatrixInput = MatrixInput;
    }

    @Override
    public void run() {
        List<Integer> numbers = new ArrayList<>();//用于生成含有不同数字的矩阵

        for (int i=1;i<=MatrixSize * MatrixSize;i++){
            numbers.add(i);
        }

        Collections.shuffle(numbers);//打乱数组

        int index = 0;
        for (int i=0;i<MatrixSize;i++){
            for (int j=0;j<MatrixSize;j++){
                MatrixInput[i][j] = numbers.get(index);
                index++;
            }
        }
    }

}

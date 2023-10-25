package Grid.PThread;

import Grid.DataContainer;

public class MatrixCheck implements Runnable{
    private int[][] inputMatrix;//输入数组
    private DataContainer dataContainer;
    private int sumNumber;

    public MatrixCheck(DataContainer dataContainer){
        this.dataContainer = dataContainer;
        inputMatrix = dataContainer.getArrayList();
        int n = inputMatrix.length;
        this.sumNumber = (1+n*n)*n/2;
    }

    private boolean DataCheckSumRow(int[][] inputMatrix){
        int k=0;
        for (int i=0;i<inputMatrix.length;i++){
            for (int j=0;j<inputMatrix[0].length;j++){
                k+=inputMatrix[i][j];
            }
            if (k != sumNumber){
                return false;
            }
        }
        return true;
    }

    private boolean DataCheckSumCol(int[][] inputMatrix){
        int k = 0;
        for (int i=0;i<inputMatrix.length;i++){
            for (int j=0;j<inputMatrix.length;j++){
                k += inputMatrix[j][i];
            }
            if (k!=sumNumber){
                return false;
            }
        }
        return true;
    }

    //正对角线
    private boolean DataCheckDiagonal_First(int[][] inputMatrix){
        int k = 0;
        for (int i=0;i<inputMatrix.length;i++){
            k += inputMatrix[i][i];
        }
        if (k != sumNumber){
            return false;
        }
        return true;
    }

    //反对角线
    private boolean DataCheckDiagonal_Second(int[][] inputMatrix){
        int k = 0;
        for (int i=0;i<inputMatrix.length;i++){
            int j;
            if (inputMatrix.length == 1){
                j = 0;
            }
            else {
                j = inputMatrix.length - i;
            }
            k += inputMatrix[j][i];
        }
        if (k!=sumNumber){
            return false;
        }
        return true;
    }

    @Override
    public void run() {
        boolean row,col,firstDiagonal,secondDiagonal;
        row = DataCheckSumRow(inputMatrix);
        col = DataCheckSumCol(inputMatrix);
        firstDiagonal = DataCheckDiagonal_First(inputMatrix);
        secondDiagonal = DataCheckDiagonal_Second(inputMatrix);
        if (row && col && firstDiagonal && secondDiagonal){
            dataContainer.setCheck(true);
        }
        else {
            dataContainer.setCheck(false);
        }
    }
}

package Grid;

import java.util.ArrayList;

public class DataContainer {
    private int[][] arrayList;
    private boolean check;

    public DataContainer(int[][] arrayList){
        this.arrayList = arrayList;
        this.check = false;
    }

    public int[][] getArrayList() {
        return arrayList;
    }

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }
}

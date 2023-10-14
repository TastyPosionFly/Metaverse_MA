public class ReturnClass {
    private int[] intArray;
    private boolean[] booleanArray;
    private int[][] adjacencyMatrix;

    public ReturnClass(int[] intArray, boolean[] booleanArray) {
        this.intArray = intArray;
        this.booleanArray = booleanArray;
    }

    public ReturnClass(int[] intArray,boolean[] booleanArray,int[][] adjacencyMatrix){
        this.intArray = intArray;
        this.booleanArray = booleanArray;
        this.adjacencyMatrix = adjacencyMatrix;
    }

    public int[] getIntArray() {
        return intArray;
    }

    public boolean[] getBooleanArray() {
        return booleanArray;
    }

    public int[][] getAdjacencyMatrix(){
        return adjacencyMatrix;
    }

}

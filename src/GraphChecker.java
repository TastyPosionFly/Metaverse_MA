import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class GraphChecker {

    private int[][] adjacencyMatrix;//临界矩阵
    private boolean[] visited;//访问数组

    public GraphChecker(int[][] adjacencyMatrix){
        this.adjacencyMatrix = adjacencyMatrix;
        visited = new boolean[adjacencyMatrix.length];
    }

    //DFS算法，检查数组中的节点是否相互连接
    public boolean isConnected(int [] nodes){

        Arrays.fill(visited, false);

        // 对数组中的每个节点进行DFS
        for (int node:nodes){
            if (!visited[node]){
                DFS(node);
            }
        }

        // 检查数组中的所有节点是否都被访问到，如果是，则它们相互连接
        for (int node:nodes){
            if (!visited[node])
                return false;
        }

        return true;
    }

    //深度优先搜索,加上一个Marked数组用作判定条件
    private void DFS(int node){

        visited[node] = true;

        for (int i=0;i<adjacencyMatrix.length;i++){
            if (adjacencyMatrix[node][i] == 1 && !visited[i]){
                DFS(i);
            }
        }

    }
}

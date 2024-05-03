import java.util.LinkedList;

public class Node{
    private int number;
    private LinkedList<Edge> edges;

    public Node(int number){
        this.number = number;
        this.edges = new LinkedList<>();
    }

    public void addEdges(Edge edge){
        this.edges.add(edge);
    }

    public LinkedList<Edge> getEdges(){
        return edges;
    }
}
import java.util.Map;
import java.lang.StringBuilder;
import java.util.HashMap;

public class Node{
    private int id;
    private Map<Integer,Edge> connections;
    private Node previousNode;

    public Node(int id){
        this.id = id;
        this.connections = new HashMap<>(); 
    }

    public void addEdge(int target,Edge edge){
        this.connections.put(target, edge);
    }

    public void removeEdge(int target){
        this.connections.remove(target);
    }

    public void setPreviousNode(Node node){
        this.previousNode = node;
    }

    public Node getPreviousNode(){
        return previousNode;
    }

    public Edge getConnection(int node){
        return connections.get(node);
    }

    public Map<Integer,Edge> connectingNodes(){
        return connections;
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        StringBuilder result = new StringBuilder();
        result.append("Node ID: " + id + "\n");
        for(int node : connections.keySet()){
            Edge edge = connections.get(node);
            result.append("Connection to " + node + " --> ");
            result.append(edge.toString() + "\n");
        }
        return result.toString();
    }


}
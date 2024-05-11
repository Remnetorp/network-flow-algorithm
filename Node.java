import java.util.LinkedList;
import java.util.Map;
import java.lang.StringBuilder;
import java.util.HashMap;

public class Node{
    private int id;
    private Map<Node,Integer> availableCapacity;

    public Node(int id){
        this.id = id;
        this.availableCapacity = new HashMap<>();
    }

    public boolean changeCapacity(Node node, int capacity){
        if(capacity > 0){
            if(!availableCapacity.containsKey(node)){
                availableCapacity.put(node, capacity);
                return true;
            }else{
                int addedCapacity = availableCapacity.get(node) + capacity;
                availableCapacity.put(node, addedCapacity);
                return true;
            }
        }else{
            if(!availableCapacity.containsKey(node)){
                return false;
            }else{
                int newCapacity = availableCapacity.get(node) + capacity;
                if (newCapacity < 0){
                    return false;
                }else{
                    availableCapacity.put(node, newCapacity);
                    return true;
                }
            }
        }
    }

    private void setCapacity(Node node, int capacity){
        availableCapacity.put(node, capacity);
    } 

    public static void setMutualCapacity(Node node1, Node node2, int capacity){
        node1.setCapacity(node2, capacity);
        node2.setCapacity(node1, capacity);

    }

    public static boolean changeMutualCapacity(Node node1, Node node2, int capacity){
        return node1.changeCapacity(node2, capacity) && node2.changeCapacity(node1, capacity);
    }

    public int getId(){
        return id;
    }

    @Override
    public String toString(){
        StringBuilder resultString = new StringBuilder();
        resultString.append("Node " + this.getId() + " has following connections: \n");
        for(Node node : availableCapacity.keySet()){
            resultString.append("To Node " + node.getId() + " => Capacity " + availableCapacity.get(node) + "\n");
        }
        return resultString.toString();
    }

}
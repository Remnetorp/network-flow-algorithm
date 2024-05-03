
public class Edge{
    private int capacity, source, target;

    public Edge(int source, int target, int capacity){
        this.source = source;
        this.target = target;
        this.capacity = capacity;
    }

    @Override
    public String toString(){
        return "Source: " + source + "\nTarget: " + target + "\nCapacity: " + capacity;
    }
}
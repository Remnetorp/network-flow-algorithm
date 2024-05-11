
public class Edge{
    private int capacity, source, target;

    public Edge(int source, int target, int capacity){
        this.source = source;
        this.target = target;
        this.capacity = capacity;
    }

    public boolean removeCapacity(int usedCapacity){
        if(usedCapacity>capacity){
            return false;
        }else{
            capacity = capacity - usedCapacity;
            return true;
        }
    }

    public int getTarget(){
        return target;
    }

    @Override
    public String toString(){
        return "Source: " + source + " Target: " + target + " Capacity: " + capacity;
    }
}
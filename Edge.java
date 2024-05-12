public class Edge{
    private int capacity;
    private int[] nodes; 

    public Edge(int node1, int node2, int capacity){
        this.nodes = new int[2];
        this.nodes[0] = node1;
        this.nodes[1] = node2;
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

    public int getCapacity(){
        return capacity;
    }

    public int[] getNodes(){
        return nodes;
    }

    @Override
    public String toString(){
        return "Node1: " + nodes[0] + " Node2: " + nodes[1] + " Capacity: " + capacity;
    }
}
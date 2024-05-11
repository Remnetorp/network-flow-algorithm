import java.util.Scanner;
import java.util.Map;
import java.util.Stack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;

public class NetworkFlow{
    private int nNodes, nEdges, nStudents, nRemove;
    private LinkedList<Integer> routesToRemove;
    private Map<Integer, Node> nodes;
    private Map<Integer, int[]> routes;

    public void readFile(boolean output){
        Scanner scanner = new Scanner(System.in);
        routesToRemove = new LinkedList<>();
        nodes = new HashMap<>();
        routes = new HashMap<>();
        nNodes = scanner.nextInt();
        nEdges = scanner.nextInt();
        nStudents = scanner.nextInt();
        nRemove = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nEdges; i++){
            Node node1, node2;
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            int capacity = scanner.nextInt();
            int[] route = new int[2];
            route[0] = source;
            route[1] = target;
            routes.put(i,route);

            if(!nodes.containsKey(source)){
                node1 = new Node(source);
                nodes.put(source,node1);
            }else{
                node1 = nodes.get(source);
            }

            if(!nodes.containsKey(target)){
                node2 = new Node(target);
                nodes.put(target, node2);
            }else{
                node2 = nodes.get(target);
            }

            Node.setMutualCapacity(node1, node2, capacity);

            scanner.nextLine();
        }

        for(int j = 0; j < nRemove; j++){
            routesToRemove.addLast(scanner.nextInt());
            scanner.nextLine();
        }

        if (output){
            System.out.println("Number of Nodes: " + nNodes);
            System.out.println("Number of Edges: " + nEdges);
            System.out.println("Number of Students: " + nStudents);
            System.out.println("Number of Routes: " + nRemove);

            for (int key : nodes.keySet()){
                Node node = nodes.get(key);
                System.out.println(node.toString());
            }
            System.out.println("First route to try to remove: " + routesToRemove.getFirst());

        }
        scanner.close();

    }


    public int testConditions(){
        return 20;
    }

    /*  public void findPaths(){
        LinkedList<Node> queue = new LinkedList<>();
        boolean[] visitedNodes = new boolean[nNodes];
        Map<Integer,int[]> paths = new HashMap();

        queue.add(nodes.get(0));
        visitedNodes[0] = true;


        while(!queue.isEmpty()){
            Node currentNode = queue.poll();
            LinkedList<Edge> edges = currentNode.getEdges();

            for(Edge currentEdge : edges){
                int targetNode = currentEdge.getTarget();
                if(!visitedNodes[targetNode]){
                    visitedNodes[targetNode] = true;
                    queue.addLast(targetNode);


                }

            }
        }

    }*/

    public void algorithm(){
        int numberRemoved = 0;
        int minFlow = nStudents;

        while(!routesToRemove.isEmpty()){
            int number = routesToRemove.poll();
            int[] route = routes.get(number);
            int source = route[0];
            int target = route[1];
            
            Node node1 = nodes.get(source);
            Node node2 = nodes.get(target);

            Node.setMutualCapacity(node1, node2, 0);

            minFlow = testConditions();
            if(minFlow > nStudents){
                numberRemoved += 1;
            }else{
                break;
            }

            System.out.println(numberRemoved + " " + minFlow);
        }



    }

    public static void main(String[] args){
        boolean output = false;
        for (String arg : args) {
            if (arg.equals("output")) {
                output = true;
            }
        }
        NetworkFlow test = new NetworkFlow();
        test.readFile(output);
        test.algorithm();


    }
}
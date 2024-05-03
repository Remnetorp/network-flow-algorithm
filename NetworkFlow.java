import java.util.Scanner;
import java.util.Map;
import java.util.Stack;
import java.util.HashMap;
import java.util.LinkedList;

public class NetworkFlow{
    private int nNodes, nEdges, nStudents, nRoutes;
    private Stack<Integer> routesToRemove;
    private Map<Integer, Node> nodes;

    public void readFile(boolean output){
        Scanner scanner = new Scanner(System.in);
        routesToRemove = new Stack<>();
        nodes = new HashMap<>();
        nNodes = scanner.nextInt();
        nEdges = scanner.nextInt();
        nStudents = scanner.nextInt();
        nRoutes = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nEdges; i++){
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            int capacity = scanner.nextInt();
            Edge edge = new Edge(source,target,capacity);
            if(nodes.containsKey(source)){
                nodes.get(source).addEdges(edge);
            }else{
                Node node = new Node(source);
                node.addEdges(edge);
                nodes.put(source,node);
            }
            scanner.nextLine();
        }

        for(int j = 0; j < nRoutes; j++){
            routesToRemove.push(scanner.nextInt());
            scanner.nextLine();
        }

        if (output){
            System.out.println("Number of Nodes: " + nNodes);
            System.out.println("Number of Edges: " + nEdges);
            System.out.println("Number of Students: " + nStudents);
            System.out.println("Number of Routes: " + nRoutes);

            for (int key : nodes.keySet()){
                LinkedList<Edge> edges = nodes.get(key).getEdges();
                for(int e = 0; e < edges.size(); e++){
                    System.out.println(edges.get(e).toString());
                }
            }
            System.out.println("Amount of routes to try to remove: " + routesToRemove.size());
        }
        scanner.close();

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
    }
}
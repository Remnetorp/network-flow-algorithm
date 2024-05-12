import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Stack;

public class NetworkFlow {
    private int nNodes, nEdges, minFlow, nRemove;
    private LinkedList<Integer> routesToRemove = new LinkedList<>();
    private Map<Integer, Node> nodes = new HashMap<>();
    private Map<Integer, Edge> routes = new HashMap<>();

    /**
     * Method readInput reads an .in file and put the data into different
     * collections. The first line of input consists of a 4 different integer. The
     * first integer is number of nodes, the second integer is the number of edges,
     * the third integer is the minimum flow from start to finish, and the last
     * integer is the amount of edges to remove from the network. Then follow N
     * lines matching the number of edges, every line contains three integers to
     * represent a edge, the two first integers are the numbers of the two nodes and
     * the third integer is the capacity of the edge. Then follow R lines matching
     * the number of edges to remove, each lines consisting of the a integer
     * representing the edge number.
     * 
     * @param output Decideds if extra information should be printed out or not in
     *               the terminal.
     * @return An array list containing the Points with x and y values from the
     *         input file.
     */
    public void readFile(boolean output) {
        Scanner scanner = new Scanner(System.in);

        nNodes = scanner.nextInt();
        nEdges = scanner.nextInt();
        minFlow = scanner.nextInt();
        nRemove = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < nNodes; i++) {
            nodes.put(i, new Node(i));
        }

        for (int j = 0; j < nEdges; j++) {
            int source = scanner.nextInt();
            int target = scanner.nextInt();
            int capacity = scanner.nextInt();
            Edge edge = new Edge(source, target, capacity);

            routes.put(j, edge);

            nodes.get(source).addEdge(target, edge);
            nodes.get(target).addEdge(source, edge);

            scanner.nextLine();
        }

        // adds the routes in the order to be removed if possible.
        for (int j = 0; j < nRemove; j++) {
            routesToRemove.addLast(scanner.nextInt());
            scanner.nextLine();
        }

        // prints extra information in terminal if required.
        if (output) {
            System.out.println("Number of Nodes: " + nNodes);
            System.out.println("Number of Edges: " + nEdges);
            System.out.println("Number of Students: " + minFlow);
            System.out.println("Number of Routes: " + nRemove);

            for (int key : nodes.keySet()) {
                Node node = nodes.get(key);
                System.out.println(node.toString());
            }
            System.out.println("First route to try to remove: " + routesToRemove.getFirst());
        }

        scanner.close();
    }

    public static void main(String[] args) {
        boolean output = false;
        boolean time = false;
        for (String arg : args) {
            if (arg.equals("output")) {
                output = true;
            } else if (arg.equals("time")) {
                time = true;
            }
        }
        NetworkFlow test = new NetworkFlow();
        long time0 = System.currentTimeMillis();
        test.readFile(output);
        long time1 = System.currentTimeMillis();
        test.algorithm();
        long time2 = System.currentTimeMillis();

        if (time) {
            System.out.println("Milliseconds spent reading input: " + (time1 - time0));
            System.out.println("Milliseconds spent executing algorithm: " + (time2 - time1));
            System.out.println("Milliseconds spent in total: " + (time2 - time0));
        }

    }

    /**
     * The algorithm method starts with removing all the edges from the list of
     * queried edges to be removed. Then it checks if the minimum flow value is
     * fulfilled in the current network, otherwise it starts to add back the edges
     * removed one by one from the last removed edge. It continously checks until
     * it either ends up not being possible or it has the minimum flow fulfilled.
     * 
     */
    public void algorithm() {
        String result = "Not possible to remove any paths.";
        Stack<Edge> deletedRoutes = new Stack<>();
        int capacity = 0;
        int currentFlow = -1;
        int resultingCapacity = 0;
        int[] connectedNodes;
        Edge route;

        // removes all the routes from the list of routes to be removed.
        for (int i = 0; i < routesToRemove.size(); i++) {
            route = routes.get(routesToRemove.get(i));
            connectedNodes = route.getNodes();
            nodes.get(connectedNodes[0]).removeEdge(connectedNodes[1]);
            nodes.get(connectedNodes[1]).removeEdge(connectedNodes[0]);
            deletedRoutes.push(route);
        }

        while (currentFlow != 0) {
            currentFlow = findPathBFS();
            resultingCapacity += currentFlow;
        }

        currentFlow = -1;

        // start adding to capacity until there is no path. If capacity is higher then
        // minimum then it is done. Otherwise it starts adding back removed routes one
        // by one from the end.
        while (!deletedRoutes.isEmpty()) {

            while (capacity < minFlow - resultingCapacity) {
                route = deletedRoutes.pop();
                capacity += route.getCapacity();
                connectedNodes = route.getNodes();
                nodes.get(connectedNodes[0]).addEdge(connectedNodes[1], route);
                nodes.get(connectedNodes[1]).addEdge(connectedNodes[0], route);
            }

            while (currentFlow != 0) {
                currentFlow = findPathBFS();
                resultingCapacity += currentFlow;
            }

            if (resultingCapacity >= minFlow) {
                result = deletedRoutes.size() + " " + resultingCapacity;
                break;
            }
            currentFlow = -1;
            capacity = 0;
        }

        System.out.println(result);
    }

    /**
     * The findPathBFS method is based on the breadth-first search algorithm. It
     * starts the search from node 0, and then investigates all nodes at the current
     * depth level before searching further "down", it continues until it finds end
     * node. If it find end node and the capacity requirements are fulfilled, it
     * will call the calculateFlow method.
     * 
     * @return An int of the capacity of the path from start to end found by the BFS
     *         search.
     */
    public int findPathBFS() {
        LinkedList<Node> queue = new LinkedList<>();
        boolean[] visitedNodes = new boolean[nNodes];

        queue.add(nodes.get(0));
        visitedNodes[0] = true;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Map<Integer, Edge> edges = currentNode.connectingNodes();

            for (int conncectingNode : edges.keySet()) {

                if (!visitedNodes[conncectingNode] && (edges.get(conncectingNode).getCapacity() > 0)) {
                    visitedNodes[conncectingNode] = true;
                    queue.addLast(nodes.get(conncectingNode));
                    nodes.get(conncectingNode).setPreviousNode(currentNode);

                    if (conncectingNode == (nNodes - 1)) {
                        return calculateFlow(conncectingNode);
                    }
                }

            }
        }
        return 0;
    }

    /**
     * The calculateFlow method goes through the current identified path from start
     * node to end node to calculate the minimum flow by finding the bottle neck
     * edge in that path.
     * 
     * @param nodeID An int representing the ID number of the node.
     * @return An int of the minimum flow value throughout the path.
     */
    public int calculateFlow(int nodeID) {
        int min = Integer.MAX_VALUE;

        while (nodeID != 0) {
            Node previousNode = nodes.get(nodeID).getPreviousNode();
            Edge connection = previousNode.getConnection(nodeID);
            int capacity = connection.getCapacity();

            if (capacity < min) {
                min = capacity;
            }

            nodeID = previousNode.getId();
        }

        nodeID = nNodes - 1;
        while (nodeID != 0) {
            Node previousNode = nodes.get(nodeID).getPreviousNode();
            Edge connection = previousNode.getConnection(nodeID);
            connection.removeCapacity(min);
            nodeID = previousNode.getId();
        }

        return min;
    }

}
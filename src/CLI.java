import java.util.Scanner;
import java.util.Vector;

public class CLI {
    public static void getGraph() {

        System.out.println("enter n graph size:");
        Scanner input = new Scanner(System.in);
        Integer size = input.nextInt();
        Graph g1 = new Graph(size);

        input.nextLine();
        do {
            System.out.println("enter edge: X Y (enter space between numbers. X & Y are the vertex index," +
                    "enter calc for calculation)");
            if (input.hasNextInt()) {
                g1.addEdge(input.nextInt(), input.nextInt());
            } else break;
        } while (input.hasNextLine());

        g1.greedyColoring();

    }

    public static void getPath() {
        findPath findPath = new findPath();
        System.out.println("enter street size:");
        Scanner input = new Scanner(System.in);
        Integer size = input.nextInt();

        int[][] graph = new int[size][4];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < 4; j++) {
                graph[i][j] = input.nextInt();
            }
        }

        findPath.initialise(size, graph);


        findPath.floydWarshall(size);
        Vector<Integer> path;

        // Path from node 1 to 3
        System.out.print("Shortest path from 1 to 3: ");
        path = findPath.constructPath(1, 3);
        findPath.printPath(path);

        // Path from node 0 to 2
        System.out.print("Shortest path from 0 to 2: ");
        path = findPath.constructPath(0, 2);
        findPath.printPath(path);


        input.nextLine();

    }

}

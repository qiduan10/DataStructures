package pa2;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.SymbolGraph;

public class DegreesOfSeparationBFS {
    private static final String TMP_FILE_NAME = "tmp.txt";
    private String source;
    private SymbolGraph sg;
    private BreadthFirstPaths bfs;

    public DegreesOfSeparationBFS(String fname, String delimiter, String source) {
        sg = new SymbolGraph(fname, delimiter);
        bfs = new BreadthFirstPaths(sg.graph(), sg.indexOf(source));
        this.source = source;
    }
    public SymbolGraph getSymbolGraph() {
        return sg;
    }
    public BreadthFirstPaths getBreadthFirstPaths() {
        return bfs;
    }
    public int baconNumber(String sink)
    {
        if (!sg.contains(sink)) {
            return -1;
        }
        int pathLen = bfs.distTo(sg.indexOf(sink));
        if (pathLen == Integer.MAX_VALUE) {
            return -1;
        }
        return pathLen / 2;
    }

    public Stack<Integer> graphPath(String sink){
        Stack<Integer> path = new Stack<Integer>();
        if (!sg.contains(sink) || !bfs.hasPathTo(sg.indexOf(sink))) {
            return path;
        }
        Stack<Integer> pStack = (Stack<Integer>) bfs.pathTo(sg.indexOf(sink));
        while (!pStack.isEmpty()) {
            path.push(pStack.pop());
        }
        return path;

    }
    public void printPath(Stack<Integer> path){
        // Now print. Every other vertex is an actor
        int MAX_BACON = 100;
        int[] hist = new int[MAX_BACON + 1];
        for (int v = 0; v < sg.graph().V(); v++) {
            int bacon = Math.min(MAX_BACON, bfs.distTo(v));
            hist[bacon]++;
        }
        // print out histogram - even indices are actors
        for (int i = 0; i < MAX_BACON; i += 2) {
            if (hist[i] == 0) break;
            StdOut.printf("%3d %8d\n", i / 2, hist[i]);
        }
        StdOut.printf("Inf %8d\n", hist[MAX_BACON]);
    }
    public static void main(String[] args) {
        String filename  = args[0];
        String delimiter = args[1];
        String source    = args[2];

        DegreesOfSeparationBFS baconGraph = new DegreesOfSeparationBFS(filename, delimiter, source);

        // Print the Bacon diagram
        //baconGraph.printBaconDiagram();
        int i, no_args = args.length;
        // Get degrees of separation
        for(i=3;i<no_args;i++) {
            baconGraph.baconNumber(args[i]);
            Stack<Integer> path = baconGraph.graphPath(args[i]);
            baconGraph.printPath(path);
        }
    }
}

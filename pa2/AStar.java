package pa2;

import edu.princeton.cs.algs4.*;
//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Edge;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.LinkedQueue;

import java.util.Arrays;

public class AStar {
    private static final Double THRESHOLD = 0.000001;

    private int h; // Regular dijkstra or A*

    private double[] distTo;

    private DirectedEdge[] edgeTo;

    private EuclideanGraph euclideanGraph;

    private EdgeWeightedDigraph edgeWeightedDigraph;

    public AStar(String G, int h) {
        euclideanGraph = new EuclideanGraph(new In(G));
        edgeWeightedDigraph = new EdgeWeightedDigraph(euclideanGraph.V());
        for (int i = 0; i < euclideanGraph.V(); i++) {
            for (int j : euclideanGraph.neighbors(i)) {
                edgeWeightedDigraph.addEdge(new DirectedEdge(i, j, euclideanGraph.distance(i, j)));
            }
        }
        this.h = h;
        distTo = new double[euclideanGraph.V()];
        edgeTo = new DirectedEdge[euclideanGraph.V()];
    }

    // Reset dists for new query
    public void reset() {
        distTo = new double[euclideanGraph.V()];
        edgeTo = new DirectedEdge[euclideanGraph.V()];
    }
    private void validateVertex(int v) {
        int V = distTo.length;
        if (v < 0 || v >= V)
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
    }

    public int route(int s, int t) {
        validateVertex(s);
        validateVertex(t);
        Arrays.fill(distTo, Double.POSITIVE_INFINITY);
        distTo[s] = 0.0;

        IndexMinPQ<Double> open = new IndexMinPQ<>(euclideanGraph.V());
        IndexMinPQ<Double> closed = new IndexMinPQ<>(euclideanGraph.V());

        int count = 0;

        open.insert(s, distTo[s] + heuristic(euclideanGraph, s, t));
        while (!open.isEmpty() && open.minIndex() != t) {
            count++;
            int v = open.minIndex();
            for (DirectedEdge e : edgeWeightedDigraph.adj(v)) {
                int to = e.to();
                double dis = distTo[v] + e.weight();
                if (!open.contains(to) && !closed.contains(to)) {
                    edgeTo[to] = e;
                    distTo[to] = dis;
                    open.insert(to, dis + heuristic(euclideanGraph, to, t));
                } else if (dis < distTo[to] - THRESHOLD) {
                    edgeTo[to] = e;
                    distTo[to] = dis;
                    if (open.contains(to)) {
                        open.decreaseKey(to, dis + heuristic(euclideanGraph, to, t));
                    }
                    if (closed.contains(to)) {
                        open.insert(to, dis + heuristic(euclideanGraph, to, t));
                        closed.delete(to);
                    }
                }
            }
            closed.insert(v, open.keyOf(v));
            open.delete(v);
        }

        return count;
    }

    // The function that biases the search
    private double heuristic(EuclideanGraph G, int v, int w)
    {
        if (h == 1) {
            return G.distance(v, w);
        }
        return 0.0;
    }

    public double distTo(int v) {
        validateVertex(v);
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        validateVertex(v);
        return Double.isFinite(distTo(v));
    }

    public Iterable<Edge> pathTo(int v) {
        validateVertex(v);
        Stack<Edge> path = new Stack<Edge>();
        int vertex = v;
        while (edgeTo[vertex] != null) {
            DirectedEdge e = edgeTo[vertex];
            path.push(new Edge(e.from(), e.to(), e.weight()));
            vertex = e.from();
        }
        return path;
    }
    private boolean check(EuclideanGraph G, int s) {

        return false;
    }
    public static void main(String[] args) {
        // Build the graph
        int heuristic = Integer.parseInt(args[2]);
        AStar sp = new AStar(args[0], heuristic);
        In paths = new In(args[1]);
        // Now run queries
        int processed = 0;
        long startTime = System.currentTimeMillis();
        while (!paths.isEmpty()) {
            int s = paths.readInt();
            int t = paths.readInt();
            StdOut.println(s + " " + t);

            processed = sp.route(s,t);
            if (sp.hasPathTo(t)) {
                StdOut.printf("Printing path! %d to %d (%.2f)  ", s, t, sp.distTo(t));
                for (Edge e : sp.pathTo(t)) {
                    StdOut.println(e + "   ");
                }
                StdOut.println();
            }
            else {
                StdOut.printf("%d to %d         no path\n", s, t);
            }
            sp.reset();
            StdOut.println("Processed " + processed);
        }
        long endTime = System.currentTimeMillis();
        long tm = endTime-startTime;
        StdOut.println(tm);
    }
}

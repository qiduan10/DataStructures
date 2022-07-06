package pa2;

import edu.princeton.cs.algs4.*;

import java.io.FileNotFoundException;

public class EuclideanGraph {
    private final static String NEWLINE = System.getProperty("line.separator");

    private int V;
    private int E;
    private LinkedBag<Integer>[] adj;
    private Point2D[] points;

    public EuclideanGraph(In in) {
        V = Integer.parseInt(in.readString());
        E = Integer.parseInt(in.readString());
        points = new Point2D[V];
        for (int i = 0; i < V; i++) {
            int v = Integer.parseInt(in.readString());
            int x = Integer.parseInt(in.readString());
            int y = Integer.parseInt(in.readString());
            if (v < 0 || v >= V) throw new RuntimeException("Illegal vertex number");
            points[v] = new Point2D(x, y);
        }

        adj = new LinkedBag[V];
        for (int i = 0; i < V; i++) {
            adj[i] = new LinkedBag<>();
        }
        for (int i = 0; i < E; i++) {
            int v = Integer.parseInt(in.readString());
            int w = Integer.parseInt(in.readString());
            if (v < 0 || v >= V) throw new RuntimeException("Illegal vertex number");
            if (w < 0 || w >= V) throw new RuntimeException("Illegal vertex number");
            adj[v].add(w);
            adj[w].add(v);
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    // Euclidean distance from v to w
    public double distance(int v, int w) {
        return points[v].distanceTo(points[w]);
    }

    // return iterator for list of neighbors of v
    public LinkedBag<Integer> neighbors(int v) {
        return adj[v];
    }

    public String toString() {
        String s = "";
        s += "V = " + V + NEWLINE;
        s += "E = " + E + NEWLINE;
        for (int v = 0; v < V && v < 100; v++) {
            String t = v + " " + points[v] + ": ";
            for (int neighbor : adj[v]) {
                t += neighbor + " ";
            }
            s += t + NEWLINE;
        }
        return s;
    }

    // test client
    public static void main(String args[]) {
        In in = new In(args[0]);
        EuclideanGraph G = new EuclideanGraph(in);
        System.out.println(G);
    }
}

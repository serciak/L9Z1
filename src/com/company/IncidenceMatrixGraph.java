package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class IncidenceMatrixGraph {

    private class Edge implements Comparable<Edge> {

        int s;
        int d;
        int weight;

        Edge() {
        }

        Edge(int s, int d, int weight) {
            this.s = s;
            this.d = d;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return this.weight - o.weight;
        }
    }

    private int vertexAmount;
    private int edgesAmount;
    private int[][] incMatrix;
    private Edge[] edges;

    public IncidenceMatrixGraph(int vertexAmount, int edgesAmount) {
        this.vertexAmount = vertexAmount;
        this.edgesAmount = edgesAmount;
        incMatrix = new int[vertexAmount][edgesAmount];
        edges = new Edge[edgesAmount];
    }

    public void addEdge(int x, int y, int weight) {
        int i;
        for (i = 0; i < edgesAmount; i++) {
            if (edges[i] == null) {
                incMatrix[y][i] = weight;
                if (x != y)
                    incMatrix[x][i] = weight;
                edges[i] = new Edge(x, y, weight);
                return;
            }
        }
        resize(0, 1);
        incMatrix[y][edgesAmount-1] = weight;
        if (x != y)
            incMatrix[x][edgesAmount-1] = weight;
        edges[i] = new Edge(x, y, weight);
    }

    public void addVertex() {
        resize(1, 0);
    }

    private void resize(int v, int e) {
        int[][] newMatrix = new int[vertexAmount+v][edgesAmount+e];
        for(int i = 0; i < vertexAmount; i++)
            for(int j = 0; j < edgesAmount; j++)
                newMatrix[i][j] = incMatrix[i][j];

        vertexAmount += v;
        incMatrix = newMatrix;

        if(e > 0) {
            Edge[] newEdges = new Edge[edgesAmount+e];
            for(int i = 0; i < edgesAmount; i++)
                newEdges[i] = edges[i];

            edges = newEdges;
            edgesAmount += e;
        }
    }

    private class set {
        int parent;
        int rank;
    }

    private int find(set[] subset, int i) {
        if(subset[i].parent != i)
            subset[i].parent = find(subset, subset[i].parent);
        return subset[i].parent;
    }

    private void Union(set subsets[], int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;
        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public void mst() {
        Edge[] res = new Edge[vertexAmount];
        int e = 0;
        int i;

        for(i = 0; i < vertexAmount; i++)
            res[i] = new Edge();

        Arrays.sort(edges);
        set[] subset = new set[vertexAmount];
        for(i = 0; i < vertexAmount; i++)
            subset[i] = new set();

        for(int j = 0; j < vertexAmount; j++) {
            subset[j].parent = j;
            subset[j].rank = 0;
        }
        i = 0;
        while (e < vertexAmount - 1) {
            Edge next = new Edge();
            next = edges[i++];
            int x = find(subset, next.s);
            int y = find(subset, next.d);

            if(x != y) {
                res[e++] = next;
                Union(subset, x, y);
            }
        }
        printMST(res);
    }

    private void printMST(Edge[] res) {
        int minWeight = 0;

        System.out.println("MST:");
        for(int i = 0; i < vertexAmount-1; i++) {
            System.out.println(res[i].s + " - " + res[i].d + " weight: " + res[i].weight);
            minWeight += res[i].weight;
        }

        System.out.println("\nMinimum weight: " + minWeight);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n");

        for(int i = 0; i < vertexAmount; i++) {
            for(int j = 0; j < edgesAmount; j++) {
                sb.append(incMatrix[i][j]).append("\t");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

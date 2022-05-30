package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class AdjacencyListGraph {

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

    private ArrayList<ArrayList<Edge>> adjList;
    private int vertexAmount;
    private ArrayList<Edge> edges;

    public AdjacencyListGraph(int vertexAmount) {
        this.vertexAmount = vertexAmount;
        adjList = new ArrayList<>();
        edges = new ArrayList<>();
        fill();
    }

    private void fill() {
        for(int i = 0; i < vertexAmount; i++)
            adjList.add(new ArrayList<>());
    }

    public void addEdge(int x, int y, int weight) {
        Edge e1 = new Edge(x, y, weight);
        Edge e2 = new Edge(y, x, weight);

        edges.add(e1);
        adjList.get(x).add(e1);
        adjList.get(y).add(e2);
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

        edges.sort(Edge::compareTo);
        set[] subset = new set[vertexAmount];
        for(i = 0; i < vertexAmount; i++)
            subset[i] = new set();

        for(int j = 0; j < vertexAmount; j++) {
            subset[j].parent = j;
            subset[j].rank = 0;
        }
        i = 0;
        while (e < vertexAmount - 1) {
            Edge next;
            next = edges.get(i++);
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
            sb.append("V").append(i).append("\n").append("head");
            for(int j = 0; j < adjList.get(i).size(); j++) {
                sb.append(" -> ").append(adjList.get(i).get(j).d);
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}

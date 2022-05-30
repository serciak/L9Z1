package com.company;

public class Main {

    public static void main(String[] args) {
	    AdjacencyMatrixGraph amg = new AdjacencyMatrixGraph(5);
        amg.addEdge(1, 2, 1);
        amg.addEdge(0, 4, 5);
        amg.addEdge(1, 4, 1);
        amg.addEdge(2, 0, 2);
        amg.addEdge(3, 2, 3);
        System.out.println(amg);
        //amg.mst();
        //amg.bfs(0, 3);
        //amg.dfs(0, 3);
        //amg.dijkstra(0);
        amg.addVertex();
        System.out.println(amg);

        AdjacencyListGraph alg = new AdjacencyListGraph(5);
        alg.addEdge(1, 2, 1);
        alg.addEdge(0, 4, 5);
        alg.addEdge(1, 4, 1);
        alg.addEdge(2, 0, 2);
        alg.addEdge(3, 2, 3);
        //alg.mst();

        IncidenceMatrixGraph img = new IncidenceMatrixGraph(5, 5);
        img.addEdge(1, 2, 1);
        img.addEdge(0, 4, 5);
        img.addEdge(1, 4, 1);
        img.addEdge(2, 0, 2);
        img.addEdge(3, 2, 3);
        //img.mst();
    }
}

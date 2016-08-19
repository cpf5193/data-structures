package dataStructures;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Graph<T extends Comparable<T>> {
  
  /*
   * A graph is represented by a map of nodes to edges. Values of nodes must be unique.
   */
  private HashMap<T, HashSet<Edge<T>>> edges;
  
  public Graph() {
    edges = new HashMap<T, HashSet<Edge<T>>>();
  }
  
  public Graph(T[] values) {
    this();
    for(T value : values) {
      addNode(value);
    }
  }
  
  public void addEdge(T from, T to, int weight) throws IllegalArgumentException {
    if (!edges.containsKey(from) || !edges.containsKey(to)) {
      throw new IllegalArgumentException("both nodes of an edge must exist in the graph before adding");
    } else {
      HashSet<Edge<T>> bucketEdges = edges.get(from);
      boolean edgeExists = bucketEdges.stream().filter(e -> e.dest.equals(to)).findFirst().isPresent();
      boolean loopExists = bucketEdges.stream().filter(e -> e.dest.equals(from)).findFirst().isPresent();
      if (edgeExists) {
        throw new IllegalArgumentException("Cannot add duplicate edge");
      } else if (loopExists) {
        throw new IllegalArgumentException("No loops allowed");
      } else {
        bucketEdges.add(new Edge<T>(to, weight));
        edges.put(from, bucketEdges);
      }
    }
    
  }
  
  private boolean containsNode(T val) {
    return edges.keySet().contains(val);
  }
  
  public void addNode(T value) throws IllegalArgumentException {
    if (edges.containsKey(value)) {
      throw new IllegalArgumentException("No duplicate node values allowed");
    } else {
      edges.put(value, new HashSet<Edge<T>>());
    }
  }
  
  public ArrayList<T> adjacentNodes(T source) {
    ArrayList<T> neighbors = new ArrayList<T>();
    for(Edge<T> edge : edges.get(source)) {
      neighbors.add(edge.dest);
    }
    return neighbors;
  }
  
  public ArrayList<Edge<T>> outgoingEdges(T source) {
    ArrayList<Edge<T>> edges = new ArrayList<Edge<T>>();
    edges.addAll(this.edges.get(source));
    return edges;
  }
  
  public String toString() {
    StringBuilder toReturn = new StringBuilder();
    for(T key : edges.keySet()) {
      toReturn.append(key.toString());
      toReturn.append(" -> [");
      for(Edge<T> edge : edges.get(key)) {
        toReturn.append(edge.dest.toString());
        toReturn.append("(");
        toReturn.append(edge.weight);
        toReturn.append("), ");
      }
      if (edges.get(key).size() > 0) {
        toReturn.delete(toReturn.length() - 2, toReturn.length());
      }
      toReturn.append("]\n");
    }
    return toReturn.toString();
  }
  
  // Return nodes searched before path from startNode to searchVal was found
  public ArrayList<T> bfs(T startNode, T searchVal) throws IllegalArgumentException {
    if (!containsNode(searchVal)) {
      throw new IllegalArgumentException("Cannot use bfs to search for a non-existent key");
    }
    HashMap<T, T> edgeTo = new HashMap<T, T>();
    Queue<T> q = new LinkedList<T>();
    q.add(startNode);
    T current = null;
    while(!q.isEmpty()) {
      current = q.remove();
      if (current.equals(searchVal)) {
        break;
      }
      for (Edge<T> edge : outgoingEdges(current)) {
        if (!edgeTo.containsKey(edge.dest)) {
          q.add(edge.dest);
          edgeTo.put(edge.dest, current);
        }
      }
    }
    Stack<T> path = new Stack<T>();
    while (!current.equals(startNode)) {
      path.push(current);
      current = edgeTo.get(current);
    }
    path.push(startNode);
    ArrayList<T> reversedPath = new ArrayList<T>();
    while(!path.isEmpty()) {
      reversedPath.add(path.pop());
    }
    return reversedPath;
  }
  
  // Return nodes searched before path from startNode to searchVal was found
  public ArrayList<T> dfs(T startNode, T searchVal) {
    if (!containsNode(searchVal)) {
      throw new IllegalArgumentException("Cannot use dfs to search for a non-existent key");
    }
    ArrayList<T> reversedPath = new ArrayList<T>();
    HashMap<T, T> edgeTo = new HashMap<T, T>();
    Stack<T> stack = new Stack<T>();
    stack.push(startNode);
    T current = null;
    while (!stack.isEmpty()) {
      current = stack.pop();
      if (current.equals(searchVal)) {
        break;
      }
      for(Edge<T> edge : edges.get(current)) {
        if (!edgeTo.containsKey(edge.dest)) {
          stack.push(edge.dest);
          edgeTo.put(edge.dest, current);
        }
      }
    }
    Stack<T> path = new Stack<T>();
    while (!current.equals(startNode)) {
      path.push(current);
      current = edgeTo.get(current);
    }
    path.push(startNode);
    reversedPath = new ArrayList<T>();
    while(!path.isEmpty()) {
      reversedPath.add(path.pop());
    }
    return reversedPath;
  }
  
  // DOESN'T WORK YET
  public void dijkstras(T source, HashMap<T, T> paths, HashMap<T, Integer> costs) {
    PriorityMinQueue<T> priorityQueue = new PriorityMinQueue<T>();
    for(T node : edges.keySet()) {
      paths.put(node, null);
      costs.put(node, Integer.MAX_VALUE);
      priorityQueue.insert(node, Integer.MAX_VALUE);
    }
    priorityQueue.decreasePriority(source, 0);
    PQNode<T> current = null;
    while(!priorityQueue.isEmpty()) {
      current = priorityQueue.removeMin();
      for(Edge<T> edge : outgoingEdges(current.key)) {
        int newWeight = current.priority + edge.weight;
        if (newWeight < costs.get(edge.dest)) {
          costs.put(edge.dest, newWeight);
          paths.put(edge.dest, current.key);
          if (!priorityQueue.isEmpty() && priorityQueue.containsKey(edge.dest)) {
            priorityQueue.decreasePriority(edge.dest, newWeight);
          }
        }
      }
    }
  }
}

package dataStructures;

import java.util.ArrayList;

public class BST<T extends Comparable<T>> {
  BSTNode<T> root;
  
  public BST() {
    root = new BSTNode<T>();
  }
  
  public BST(T rootVal) {
    this.root = new BSTNode<T>(rootVal);
  }
  
  public BST(T[] values) {
    addAll(values);
  }
  
  public void addAll(T[] values) {
    for (T val : values) {
      add(val);
    }
  }
  
  public void add(T value) {
    add(value, root);
  }
  
  private void add(T value, BSTNode<T> subroot) {
    if (root == null || root.value == null) {
      root = new BSTNode<T>(value);
      return;
    }
    int compareVal = value.compareTo(subroot.value);
    if (compareVal <= 0) {
      if (subroot.leftChild == null) {
        subroot.leftChild = new BSTNode<T>(value);
      } else {
        add(value, subroot.leftChild);
      }
    } else { // compareVal > 0
      if (subroot.rightChild == null) {
        subroot.rightChild = new BSTNode<T>(value);
      } else {
        add(value, subroot.rightChild);
      }
    }
  }

  public ArrayList<T> preOrder() {
    ArrayList<T> output = new ArrayList<T>();
    getPreOrder(root, output);
    return output;
  }
  
  private void getPreOrder(BSTNode<T> subRoot, ArrayList<T> values) {
    if (subRoot == null) {
      return;
    } else {
      values.add(subRoot.value);
      getPreOrder(subRoot.leftChild, values);
      getPreOrder(subRoot.rightChild, values);
    }
  }
  
  public ArrayList<T> inOrder() {
    ArrayList<T> output = new ArrayList<T>();
    getInOrder(root, output);
    return output;
  }
  
  private void getInOrder(BSTNode<T> subRoot, ArrayList<T> values) {
    if (subRoot == null) {
      return;
    } else {
      getInOrder(subRoot.leftChild, values);
      values.add(subRoot.value);
      getInOrder(subRoot.rightChild, values);
    }
  }
  
  public ArrayList<T> postOrder() {
    ArrayList<T> output = new ArrayList<T>();
    getPostOrder(root, output);
    return output;
  }
  
  private void getPostOrder(BSTNode<T> subRoot, ArrayList<T> values) {
    if (subRoot == null) {
      return;
    } else {
      getPostOrder(subRoot.leftChild, values);
      getPostOrder(subRoot.rightChild, values);
      values.add(subRoot.value);
    }
  }
}

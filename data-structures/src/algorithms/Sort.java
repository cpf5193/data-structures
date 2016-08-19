package algorithms;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import dataStructures.BinaryMaxHeap;

public class Sort<T extends Comparable <T>> {
  
  public T[] selectionSort(T[] arr) {
    T[] array = arr.clone();
    for (int i = 0; i < array.length; i++) {
      T min = array[i];
      int minIndex = i;
      for (int j = i + 1; j < array.length; j++) {
        if (array[j].compareTo(min) < 0) {
          min = array[j];
          minIndex = j;
        }
      }
      // Swap
      if (min.compareTo(array[i]) < 0) {
        T temp = array[i];
        array[i] = array[minIndex];
        array[minIndex] = temp;
      }
    }
    return array;
  }
  
  public T[] insertionSort(T[] arr) {
    T[] array = arr.clone();
    for(int i = 0; i < array.length; i++) {
      int j = i;
      T item = array[j];
      while (j > 0 && item.compareTo(array[j - 1]) < 0) {
        T temp = array[j - 1];
        array[j - 1] = item;
        array[j] = temp;
        j--;
      }
    }
    return array;
  }
  
  public T[] recursiveMergeSort(T[] arr) {
    T[] arrCopy = arr.clone();
    T[] auxCopy = arr.clone();
    recursiveMergeSortHelper(arrCopy, auxCopy, 0, arrCopy.length - 1);
    return arrCopy;
  }
  
  private void recursiveMergeSortHelper(T[] arr, T[] aux, int lo, int hi) {
    if (hi - lo < 1) {
      return;
    }
    int mid = lo + (hi - lo) / 2;
    recursiveMergeSortHelper(arr, aux, lo, mid);
    recursiveMergeSortHelper(arr, aux, mid + 1, hi);
    merge(arr, aux, lo, lo + (hi - lo) / 2, hi);
  }
  
  public T[] iterativeMergeSort(T[] arr) {
    T[] arrCopy = arr.clone();
    T[] auxCopy = arr.clone();
    int i;
    for (i = 1; i < arr.length; i*=2) {
      for (int j = 0; j < arr.length - i; j+=i*2) {
        merge(arrCopy, auxCopy, j, j + i-1, Math.min(j + i * 2 - 1, arr.length - 1));
      }
    }
    return arrCopy;
  }
 
  private void merge(T[] arr, T[] aux, int lo, int mid, int hi) {
    int i = lo; // lower index of half 1
    int j = mid; // upper index of half 1
    int m = j + 1; // lower index of half 2
    int n = hi; // upper index of half 2
    
    for(int k = lo; k <= hi; k++) {
      if (i > j) {
        // first half has run out, add second half
        aux[k] = arr[m++];
      } else if (m > n) {
        // second half has run out, add first half
        aux[k] = arr[i++];
      } else if (arr[i].compareTo(arr[m]) <= 0) {
        aux[k] = arr[i++];
      } else {
        aux[k] = arr[m++];
      }
    }

    // Replace sorted part
    for (i = lo; i <= hi; i++) {
      arr[i] = aux[i];
    }
  }
  
  public T[] quickSort(T[] arr) {
    // Shuffle array
    T[] arrCopy = arr.clone();
    List<T> list = Arrays.asList(arrCopy);
    Collections.shuffle(list);
    arrCopy = list.toArray(arrCopy);
    
    quickSortHelper(arrCopy, 0, arrCopy.length - 1);
    return arrCopy;
  }
  
  private void quickSortHelper(T[] arr, int lo, int hi) {
    if (hi <= lo) return;
    int newPartition = partition(arr, lo, hi);
    quickSortHelper(arr, lo, newPartition - 1);
    quickSortHelper(arr, newPartition + 1, hi);
  }
  
  private int partition(T[] arr, int lo, int hi) {
    if (hi <= lo) return lo;
    T compareVal = arr[lo];
    int i = lo;
    int j = hi + 1;
    
    while (true) {
      while (arr[++i].compareTo(compareVal) < 0) {
        if (i == hi) break;
      }
      while (arr[--j].compareTo(compareVal) > 0) {
        if (j == lo) break;
      }
      if (i >= j) break;
      swap(arr, i, j);
    }
     swap(arr, lo, j);
    return j;
  }
  
  public T[] triPartitionQuickSort(T[] arr) {
    T[] arrCopy = arr.clone();
    triPartitionQuickSortHelper(arrCopy, 0, arr.length - 1);
    return arrCopy;
  }
  
  private void triPartitionQuickSortHelper(T[] arr, int lo, int hi) {
    if (lo <= hi) return;
    
    int lt = lo;
    int gt = hi;
    int i = lo + 1;
    T partitionElt = arr[lo];
    
    while (i < gt) {
      int comparison = arr[i].compareTo(partitionElt);
      if (comparison < 0) swap(arr, lt++, i++);
      else if (comparison > 0) swap (arr, i, gt--);
      else i++;
    }
    
    triPartitionQuickSortHelper(arr, lo, lt - 1);
    triPartitionQuickSortHelper(arr, gt + 1, hi);
  }
  
  private void swap(T[] arr, int i, int j) {
    T temp = arr[i];
    arr[i] = arr[j];
    arr[j] = temp;
  }

  public T[] heapSort(T[] arr) {
    BinaryMaxHeap<T> heap = new BinaryMaxHeap<T>(arr);
    T[] sortedArray = arr.clone();
    for(int i = arr.length - 1; i >= 0; i--) {
      sortedArray[i] = heap.removeMax();
    }
    return sortedArray;
  }
}

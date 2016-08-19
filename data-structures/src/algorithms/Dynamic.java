package algorithms;

import java.util.ArrayList;
import java.util.Arrays;

public class Dynamic {

  public static void main(String[] args) {
    int[] testNDS = {5, 3, 4, 8, 6, 7};
    int[] resultNDS = longestNDS(testNDS);
    String expectedNDS = "[3, 4, 6, 7]";
    System.out.println("expected: " + expectedNDS);
    System.out.println("result: " + arrToString(resultNDS));
    
    int[] testCoins = {1, 5, 10, 25};
    int[] actualCoins = minCoins(testCoins, 174);
    System.out.println("expected: [1, 1, 1, 1, 10, 10, 25, 25, 25, 25, 25, 25]");
    System.out.println("actual: " + arrToString(actualCoins));
  }
  
  public static String arrToString(int[] arr) {
    StringBuilder toReturn = new StringBuilder("[");
    int i;
    for(i=0; i<arr.length - 1; i++) {
      toReturn.append(arr[i] + ", ");
    }
    toReturn.append(arr[i] + "]");
    return toReturn.toString();
  }
  
  public static int[] longestNDS(int[] arr) {
    if (arr.length == 0) {
      return new int[0];
    }
    int[] longestEndingAt = new int[arr.length];
    for (int i = 0; i < arr.length; i++) {
      longestEndingAt[i] = Integer.MAX_VALUE;
    }
    int[] prevIndex = new int[arr.length];
    longestEndingAt[0] = 1;
    prevIndex[0] = 0;
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] >= arr[i - 1]) {
        longestEndingAt[i] = longestEndingAt[i - 1] + 1;
        prevIndex[i] = i - 1;
      } else {
        longestEndingAt[i] = longestEndingAt[i - 1];
        prevIndex[i] = prevIndex[i - 1] == i - 1 ? i : prevIndex[i - 1];
      }
    }
    int index = arr.length - 1;
    int[] toReturn = new int[longestEndingAt[arr.length - 1]];
    for(int i=longestEndingAt[arr.length - 1] - 1; i >= 0; i--) {
      toReturn[i] = arr[index];
      index = prevIndex[index];
    }
    return toReturn;
  }
  
  public static int[] minCoins(int[] coinVals, int n) {
    ArrayList<Integer> selectedCoins = new ArrayList<Integer>();
    int[] numCoins = new int[n + 1];
    Arrays.fill(numCoins, Integer.MAX_VALUE);
    numCoins[0] = 0;
    int[] prevCoin = new int[n + 1];
    for(int i=0; i<=n; i++) {
      for (int coinVal : coinVals) {
        if (coinVal <= i && numCoins[i - coinVal] + 1 < numCoins[i]) {
          numCoins[i] = numCoins[i - coinVal] + 1;
          prevCoin[i] = coinVal;
        }
      }
    }
    int index = n;
    for (int i=numCoins[n]; i>0; i--) {
      selectedCoins.add(prevCoin[index]);
      index -= prevCoin[index];
    }
    int[] toReturn = new int[selectedCoins.size()];
    for (int i=0; i<selectedCoins.size(); i++) {
      toReturn[i] = selectedCoins.get(i);
    }
    return toReturn;
  }
}

package algorithms;

public class Dynamic {

  public static void main(String[] args) {
    int[] testNDS = {5, 3, 4, 8, 6, 7};
    int[] resultNDS = longestNDS(testNDS);
    String expectedNDS = "[3, 4, 6, 7]";
    System.out.println("expected: " + expectedNDS);
    System.out.println("result: " + arrToString(resultNDS));
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
}

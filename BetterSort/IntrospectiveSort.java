package bettersort;

import java.util.Random;

public class IntrospectiveSort {
	private static boolean breakIntro;
	
  public static <T extends Comparable<T>> void introspectiveSort(T[] items) {
    int maxDepth = 2 * (int)(Math.log(items.length) / Math.log(2));
    
    if (items.length > 0) {
        quickSort(items, 0, items.length - 1, maxDepth, 0);
      }
  }
  

  /**
   * Recursive helper method for quicksort.
   * 
   * @param items The items to sort
   * @param left The starting index of the region to sort
   * @param right The ending index of the region to sort.
   */
  private static <T extends Comparable<T>> void quickSort(T[] items, int left, int right, int maxDepth, int currDepth) {
    if (left < right) {
    	if(currDepth < maxDepth) {
    		int lastSmallIndex = partition(items, left, right);
    	      quickSort(items, left, lastSmallIndex, maxDepth, currDepth + 1);
    	      quickSort(items, lastSmallIndex + 1, right, maxDepth, currDepth + 1);
    	} else {
    		MergeSortImproved.mergeSubsortAdaptive(items, left, right);
    	}
    } 
     
  }

  /**
   * Partition the indicated region of the array. The pivot item will be placed in
   * its final sorted position, with all smaller elements moved to the left and
   * all larger elements moved to the right.
   * 
   * @return The final index of the pivot item.
   */
  	protected static <T extends Comparable<T>> int partition(T[] items, int left, int right) {
    int pivotIndex = left + (right - left) / 2;
    T pivot = items[pivotIndex];

    // Advance from both ends until window collapses.
    boolean isDone = false;
    while (!isDone) {
      // Skip rightward past < pivots.
      while (items[left].compareTo(pivot) < 0) {
        left++;
      }

      // Skip leftward past > pivots.
      while (items[right].compareTo(pivot) > 0) {
        right--;
      }

      if (left < right) {
        BasicSorts.swap(items, left, right);
        left++;
        right--;
      } else {
        isDone = true;
      }
    }

    return right;
  }
}

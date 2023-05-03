package bettersort;

import java.util.Random;


public class MergeSortImproved {
	
	private static final int MERGE_SORT_THRESHOLD = 65;
	
  /**
   * Merge sort the provided array using an improved merge operation.
   */
	@SuppressWarnings("unchecked")
	  public static <T extends Comparable<T>> void mergeSortHalfSpace(T[] items) {
	   
	    mergeSort(items, 0, items.length - 1);
	  }

	  /**
	   * Recursive helper method for the merge sort algorithm.
	   * 
	   * @param items The array to sort
	   * @param tmps Temporary array for merge operation
	   * @param left Index of the left end of the region to sort
	   * @param right Index of the right end of the region to sort.
	   */
	  private static <T extends Comparable<T>> void mergeSort(T[] items, int left, int right) {
	    if (left < right) {
	      int mid = (left + right) / 2;
	      mergeSort(items, left, mid);
	      mergeSort(items, mid + 1, right);
	      merge(items, left, right);
	    }
	  }
  
  private static <T extends Comparable<T>> void merge(T[] items, int left, int right) {
	  	int tmpIndex = left;
	  	int mid = (left + right) / 2;
		int rightIndex = mid + 1;
	    T[] tmps = (T[])new Comparable[mid + 1];
	    for (int i = left; i <= mid; i++) {
	    	tmps[i] = items[i];
	    }
	    
	    int sortIndex = left;
	    for (; tmpIndex <= mid && rightIndex <= right; sortIndex++) {
	    	if (tmps[tmpIndex].compareTo(items[rightIndex]) > 0) {
	    		items[sortIndex] = items[rightIndex];
	    		rightIndex++;
	    	} else {
	    		items[sortIndex] = tmps[tmpIndex];
	    		tmpIndex++;
	    	}
	    }
	    
	 // Copy over remainder of left subarray.
	    for (; tmpIndex <= mid; ++tmpIndex, ++sortIndex) {
	      items[sortIndex] = tmps[tmpIndex];
	    }

	    // Copy over remainder of right subarray.
	    for (; rightIndex <= right; rightIndex++, sortIndex++) {
	      items[sortIndex] = items[rightIndex];
	    }
  }

  /**
   * Merge sort the provided array by using an improved merge operation and
   * switching to insertion sort for small sub-arrays.
   */
  public static <T extends Comparable<T>> void mergeSortAdaptive(T[] items) {
    mergeSubsortAdaptive(items, 0, items.length - 1);
  }

  /**
   * Merge sort the provided sub-array using our improved merge sort. This is the
   * fallback method used by introspective sort.
   */
  public static <T extends Comparable<T>> void mergeSubsortAdaptive(T[] items, int start, int end) {
	  if (start + end > MERGE_SORT_THRESHOLD && start < end) {
	      int mid = (start + end) / 2;
	      mergeSubsortAdaptive(items, start, mid);
	      mergeSubsortAdaptive(items, mid + 1, end);
	      merge(items, start, end);
	    } else {
	    	BasicSorts.insertionSubsort(items, start, end);
	    }
  }
  
  public static void main(String[] args) {
	  Integer[] i = {3, 1, 5, 2, 7};
	  mergeSortAdaptive(i);
	  for(Integer e : i) {
		  System.out.println(e);
	  }
  }
}

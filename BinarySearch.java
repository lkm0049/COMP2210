import java.util.Arrays;
import java.util.Comparator;

/**
 * Binary search.
 */
public class BinarySearch {

    /**
     * Returns the index of the first key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     */
   public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      int left = 0;
      int right = a.length - 1;
      while (right >= left) {
         int middle = left + (right - left) / 2;
         if(comparator.compare(key, a[middle]) < 0) {
            right = middle - 1;
         }
         else if (comparator.compare(key, a[middle]) > 0) {
            left = middle + 1;
         }
         else if (middle == left) {
            return middle;
         }
         else {
            right = middle;
         }
      }
      return -1;
   }

    /**
     * Returns the index of the last key in a[] that equals the search key, 
     * or -1 if no such key exists. This method throws a NullPointerException
     * if any parameter is null.
     */
   public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> comparator) {
      if (a == null || key == null || comparator == null) {
         throw new NullPointerException();
      }
      int left = 0;
      int right = a.length - 1;
      while (right >= left) {
         int middle = left + (right - left) / 2;
         if(comparator.compare(key, a[middle]) < 0) {
            right = middle - 1;
         }
         else if (comparator.compare(key, a[middle]) > 0) {
            left = middle + 1;
         }
         else if (middle == right) {
            return middle;
         }
         else {
            left = middle;
         }
      }
      return -1;
   }

}

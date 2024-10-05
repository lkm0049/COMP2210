public class TestPrep {
   public static void shiftRight(Object[] array, int index, Object value) {
      for(int i = array.length - 1; i > index; i--) {
         array[i + 1] = array[i];
      }
      array[index] = value;
   }
   

   public static int length(Node n) {
      Node p = n;
      int len = 0;
      while (p != null) {
         len++;
         p = p.next;
      }
      return len;
   }


   @Override
   public boolean equals(Set<T> s) {
      if (s.size() != size) {
         return false;
      }
      for (T element : this) {
         if (!s.contains(element)) {
            return false;
         }
      }
      return true;
   }

   public T max() {
      Node temp = front.next;
      T max = front.element;
   
      while (temp != null) {
         T candidate = temp.element;
      
         if (candidate.compareTo(max) > 0) {
            max = candidate;
         }
      
         temp = temp.next;
      }
   
      return max;
   }


}
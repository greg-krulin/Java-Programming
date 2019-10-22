package prog06;

  import java.util.*;

  /**
   * Implements the Queue interface using a circular array.
   **/
  public class ArrayQueue<E> extends AbstractQueue<E>
    implements Queue<E> {

    // Data Fields
    /** Index of the first item in the queue. */
    private int front;
    /** Index of the last item in the queue. */
    private int back;
    /** Current size of the queue. */
    private int size;
    /** Default capacity of the queue. */
    private static final int DEFAULT_CAPACITY = 3;
    /** Array to hold the items. */
    private E[] theItems;

    // Constructors
    /**
     * Construct a queue with the default initial capacity.
     */
    public ArrayQueue () {
      this(DEFAULT_CAPACITY);
    }

    /**
     * Construct a queue with the specified initial capacity.
     * @param initCapacity The initial capacity
     */
    @SuppressWarnings("unchecked")
      public ArrayQueue (int initCapacity) {
      theItems = (E[]) new Object[initCapacity];
      front = 0;
      back = theItems.length - 1;
      size = 0;
    }

    // Public Methods
    /**
     * Inserts an item as the last item in the queue.
     * @post item is added as the last item in the queue.
     * @param item The element to add
     * @return true (always successful)
     */
    @Override
      public boolean offer (E item) {
      if (size == theItems.length)
        reallocate();
      // Move back forward one, but if it goes past the end of the
      // array, go back to zero (see nextIndex() below).
      back = nextIndex(back);
      // Store the new item at back.
      theItems[back] = item;
      size++;
      return true;
    }

    /**
     * Returns the first item in the queue without removing it.
     * @return the first item in the queue if successful;
     * return null if the queue is empty
     */
    @Override
      public E peek () {
      if (size == 0)
        return null;
      return theItems[front];
    }

    /**
     * Removes the first item in the queue and returns it
     * if the queue is not empty.
     * @post front references item that was second in the queue.
     * @return The item removed if successful or null if not
     */
    @Override
    public E poll () {
        E result = null;
        if( size != 0){
        	result = theItems[front];
        	front = nextIndex(front);
        	size--;
        }

        // EXERCISE 3

        return result;
      }

    /**
     * Return the size of the queue
     * @return The number of items in the queue
     */
    @Override
      public int size () {
      return size;
    }

    /**
     * Returns an iterator to the elements in the queue
     * @return an iterator to the elements in the queue
     */
    @Override
      public Iterator<E> iterator () {
      return new Iter();
    }
      
    private boolean labReallocate = false;

    // Private Methods
    /**
     * Get the next index.
     * @param index The current value of the index.
     * @return The next index, 0 if index == theItems.length.
     */
    private int nextIndex (int index) {
      if (index + 1 < theItems.length)
        return index + 1;
      else
        return 0;
    }

    /**
     * Double the capacity and reallocate the items.
     * @pre The array is filled to capacity.
     * @post The capacity is doubled and the first half of the
     *       expanded array is filled with items.
     */
    @SuppressWarnings("unchecked")
      private void reallocate () {
      int newCapacity = 2 * theItems.length;
      E[] newItems = (E[]) new Object[newCapacity];
      if (labReallocate) {
        int j = front;
        for (int i = 0; i < size; i++) {
          newItems[i] = theItems[j];
          j = nextIndex(j);
        }
      }
      else {
    	  if( front < back)
    		  System.arraycopy(theItems, front, newItems, 0, size);
    	  if( back < front){
    		  
    		  System.arraycopy(theItems, front, newItems, 0, size - front);
    		  System.arraycopy(theItems, 0, newItems, size - front , front);
    	  }
        // EXERCISE 8


      }
      front = 0;
      back = size - 1;
      theItems = newItems;
    }

    private boolean labIterator = false;

    /** Inner class to implement the Iterator<E> interface. */
    private class Iter implements Iterator<E> {
      // Data Fields

      // Index of next element */
      private int index;

      // Count of elements accessed so far */
      private int count = 0;

      // Methods
      // Constructor
      /**
       * Initializes the Iter object to reference the
       * first queue element.
       */
      public Iter () {
        if (labIterator) {
          index = front;
        }
        else {
        	if( size == 0)
        		index = -1;
        	else
        		index = front;
          // EXERCISE 9


        }
      }

      /**
       * Returns true if there are more elements in the queue to access.
       * @return true if there are more elements in the queue to access.
       */
      @Override
        public boolean hasNext () {
        if (labIterator)
          return count < size;
        else {
        	if(  index == -1)
        		return false;
        	else
        		return true;
          // EXERCISE 9
        	
        }
      }

      /**
       * Returns the next element in the queue.
       * @pre index references the next element to access.
       * @post index and count are incremented.
       * @return The element with subscript index
       */
      @Override
        public E next () {
        if (!hasNext()) {
          throw new NoSuchElementException();
        }
        E returnValue = theItems[index];
        if (labIterator) {
          index = nextIndex(index);
          count++;
        }
        else {
        	if( index != back)
        		index = nextIndex(index);
        	else{
        		index = -1;
        	}
        	
        
          // EXERCISE 9


        }
        return returnValue;
      }

      /**
       * Remove the item accessed by the Iter object -- not implemented.
       * @throws UnsupportedOperationException when called
       */
      @Override
        public void remove () {
        throw new UnsupportedOperationException();
      }
    }
  }
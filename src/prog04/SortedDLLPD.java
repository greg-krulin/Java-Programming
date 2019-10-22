package prog04;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class SortedDLLPD extends DLLBasedPD {
  /** Add an entry or change an existing entry.
      @param name The name of the person being added or changed.
      @param number The new number to be assigned.
      @return The old number or, if a new entry, null.
  */
  public String addOrChangeEntry (String name, String number) {
    String oldNumber = null;
    FindOutput fo = find(name);
    if (fo.found) {
      oldNumber = fo.entry.getNumber();
      fo.entry.setNumber(number);
    } else {
      // Create a new entry to insert.
    	DLLEntry entry = new DLLEntry(name, number);
    	
    	DLLEntry next = fo.entry;
    	if( next == null && tail != null)
    	{
    		tail.setNext( entry );
    		entry.setPrevious( tail );
    		entry.setNext(null);
    		tail = entry;
    		return oldNumber;
    	}
    	
    	if(next == null && tail == null)
    	{
        	head = entry;
        	tail = entry;
        	head.setPrevious(null);
        	tail.setNext(null);
        	return oldNumber;
    	}
    	
  
    	else
    	{
    		if( next.getPrevious() == null)
    		{
    			next.setPrevious(entry);
    			entry.setNext(next);
    			head = entry;
    			return oldNumber;
    		}
    		else
    		{
    			
    		DLLEntry previous = next.getPrevious();
    		
    		previous.setNext(entry);
    		entry.setPrevious( previous );
    		entry.setNext(next);
    		next.setPrevious(entry);
    		return oldNumber;
    		}
    		
    	}
    	


      // Declare and set the variable next.


      // Declare the variable previous.


      // Set it.
      // Oops that crashes if next==null.  What should it be then?
    	



      // Set the next and previous of the new entry.



      // Set the next of previous to the new entry.
      // Oops that crashes if previous==null.  What should you do then?



      
      // Set the previous of next to the new entry.
      // Oops that crashes if next==null.  What should you do then?





    }
    return oldNumber;
  }
  
  /** Find an entry in the directory.
  @param name The name to be found.
  @return A FindOutput object describing the result.
*/
  protected FindOutput find (String name) {
// EXERCISE
// For each entry in the directory.

  // If this is the entry you want

    // Return the appropriate FindOutput object
	  if( tail == null )
		  return new FindOutput( false, null );
	  else
	  {
		  DLLEntry entry = head;
		  boolean b = true;
		  while( b )
		  {
			  if( entry == null )
				  return new FindOutput(false, null);
			  
			  if( name.compareTo( entry.getName() ) == 0 )
				  return new FindOutput( true, entry);
			  
			  if( name.compareTo( entry.getName() ) < 0 )
				  return new FindOutput( false, entry );
			  
			  if( name.compareTo( entry.getName() ) > 0 )
				  entry = entry.getNext();
		  }
		  
		  

		  return new FindOutput(false, null); // Name not found.
	  }
  }

  
}

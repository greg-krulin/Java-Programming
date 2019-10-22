package prog04;

import prog02.PhoneDirectory;
import java.io.*;
import java.util.Scanner;

/** This is an implementation of the prog02.PhoneDirectory interface that uses
 *   a doubly linked list to store the data.
 *   @author vjm
 */
public class DLLBasedPD implements PhoneDirectory {
  /** The head (first entry) and tail (last entry) of the doubly
   * linked list that stores the directory data */
  protected DLLEntry head, tail;
  
  /** The data file that contains the directory data */
  protected String sourceName = null;
    
  /** Method to load the data file.
      pre:  The directory storage has been created and it is empty.
      If the file exists, it consists of name-number pairs
      on adjacent lines.
      post: The data from the file is loaded into the directory.
      @param sourceName The name of the data file
  */
  public void loadData (String sourceName) {
    // Remember the source name.
    this.sourceName = sourceName;
    try {
      // Create a Scanner for the file.
      Scanner in = new Scanner(new File(sourceName));

      // Read each name and number and add the entry to the array.
      while (in.hasNextLine()) {
        String name = in.nextLine();
        String number = in.nextLine();
        // Add an entry for this name and number.
        addOrChangeEntry(name, number);
      }
      // Close the file.
      in.close();
    } catch (FileNotFoundException ex) {
      // Do nothing: no data to load.
      return;
    } catch (Exception ex) {
      System.err.println("Load of directory failed.");
      ex.printStackTrace();
      System.exit(1);
    }
  }
    
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
      DLLEntry entry = new DLLEntry(name, number);
      if (tail == null) {
        // EXERCISE
        // Adding the first entry. 
    	head = entry;
    	tail = entry;
    	head.setPrevious(null);
    	tail.setNext(null);

      }
      else {
        // EXERCISE
        // Adding another entry at the end of the list.
    	  tail.setNext(entry);
    	  entry.setPrevious(tail);
    	  entry.setNext(null);
    	  tail = entry;
    	  
    	  

      }
    }
    return oldNumber;
  }
    
  /** Look up an entry.
      @param name The name of the person.
      @return The number. If not in the directory, null is returned.
  */
  public String lookupEntry (String name) {
    FindOutput fo = find(name);
    if (fo.found)
      return fo.entry.getNumber();
    return null;
  }
    
  /** Method to save the directory.
      pre:  The directory has been loaded with data.
      post: Contents of directory written back to the file in the
      form of name-number pairs on adjacent lines.
  */
  public void save () {
    try {
      // Create PrintStream for the file.
      PrintStream out = new PrintStream(new File(sourceName));
      
      // EXERCISE
      // For each directory entry in the directory.
      // What is the first?  What is the next?  How do you know you got them all?
      DLLEntry a = head;
      boolean b = true;
      while( b )
      {
    	  if( head == null )
    		  b = false;
    	  else
    	  {
    	// Write the name.
    	  out.println(a.getName());
    	// Write the number.
    	  out.println(a.getNumber());
    	  //It works!
    	  if( a.getNext() == null)
    		  b = false;
    	  else
    		  a = a.getNext();
    	  }
      }

      out.close();
    } catch (Exception ex) {
      System.err.println("Save of directory failed");
      ex.printStackTrace();
      System.exit(1);
    }
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
		  return new FindOutput( false, null);
	  else
	  {
	  for( DLLEntry entry = head; entry != null; entry = entry.getNext() )
	  {
		  if( entry.getName().equals(name))
			  return new FindOutput( true, entry );
	  }
    return new FindOutput(false, null); // Name not found.
	  }
  }
  
  /** Remove an entry from the directory.
      @param name The name of the person to be removed.
      @return The current number. If not in directory, null is
      returned.
  */
  public String removeEntry (String name) {
    // Call find to find the entry to remove.
    FindOutput fo = find(name);

    // EXERCISE
    // If it is not there, forget it!
    if( !fo.found )
    	return null;
    



    // Get the next entry and the previous entry.
    DLLEntry previous = fo.entry.getPrevious();
    DLLEntry next = fo.entry.getNext();



    // Two cases:  previous is null (entry is head) or not
    if( previous == null && next == null)
    {
    	head = null;
    	tail = null;
    	return fo.entry.getNumber();
    	
    }
    else
    {
    if( previous == null)
    {
    	next.setPrevious(null);
    	head = next;
    }
    
    if( previous != null )
    {
    	previous.setNext(next);
    }
    // Two cases:  next is null (entry is tail) or not
    if( next == null )
    {
    	previous.setNext(null);
    	tail = previous;
    }
    if( next != null)
    	next.setPrevious(previous);
    	



    return fo.entry.getNumber();
    }
  }
}

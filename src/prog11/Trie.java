package prog11;

import java.util.*;
import prog02.*;

public class Trie <V>
  extends AbstractMap<String, V> {

  private class Node <V>
    implements Map.Entry<String, V> {
    String key;
    V value;
    
    Node (String key, V value) {
      this.key = key;
      this.value = value;
      this.sub = key;
    }

    Node (String sub, String key, V value) {
      this.key = key;
      this.value = value;
      this.sub = sub;
    }

    public String getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }

    String sub;
    List<Node<V>> list = new ArrayList<Node<V>>();
  }
  
  private List<Node<V>> list = new ArrayList<Node<V>>();
  private int size;

  public int size () { return size; }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @param iKey The current starting character index in the key.
   * @param list The list of nodes to search for the key.
   * @return The node with that key or null if not there.
   */
  private Node<V> find (String key, int iKey, List<Node<V>> list) {
    // EXERCISE: searching for "bobcat"
	  char keyChar = key.charAt(iKey); //"b"
	  int iNode = 0;

    // Look for the node in list with node.sub.charAt(0) equal to
    // key.charAt(iKey).
	  while( iNode < list.size() && keyChar > list.get(iNode).sub.charAt(0)) {
		  iNode++;
		  //while iNode is not at the end of the list and keyChar is less than the first char of whatever member
		  //we keep on going though the list
	  }
	  if( iNode == list.size() || keyChar < list.get(iNode).sub.charAt(0)) {
		  return null;
		  //if we reach the end of the list, or go past the character in the list we stop and return null
	  }
	  // Increment iKey, set iSub = 1, and match as much as possible.
	  Node<V> node = list.get(iNode);
	  
	  //gets the node in the list, which may be the node or is a node that connects to a longer list
	  iKey++;
	  
	  //we have matched the first character, so now we need to move iKey one
	  int iSub = 1; // we have matched the first character, so just need to check iSub
	  while( iKey < key.length() && iSub < node.sub.length() && (key.charAt(iKey) == node.sub.charAt(iSub)) ) {
		  //if number of iKey is less than the key we are looking for keep going
		  //and iSub is less than the node sub length (which is current level we are at)
		  //and the key we are looking for character matches the node's character at the iSub we are at
		  //then we increase iKey and iSub and keep looking untill conditions fail
		  iKey++;
		  iSub++;		  
	  }

    // Increment iKey, set iSub=1, and match as much as possible. 
	  if( iKey == key.length() && iSub == node.sub.length() ) {
		  //we have matched iKey with the key length we are trying to find and sub is completely filled up then
		  //this is the node we want so return it
		  return node;
	  }

    // Recurse if necessary.
	  if( iKey != key.length() && iSub == node.sub.length() ) {
		  //if iKey is not same length then there are more lists to look through so recursive call
		  return find(key, iKey, node.list);
	  }

    return null;
  }    

  public boolean containsKey (Object key) {
    Node<V> node = find((String) key, 0, list);
    return node != null && node.key != null;
  }
  
  public V get (Object key) {
    Node<V> node = find((String) key, 0, list);
    if (node != null && node.key != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  private V put (String key, int iKey, V value, List<Node<V>> list) {
    // EXERCISE:

    // Same as get(find), only if there is no matching letter, add a new
    // node to list.
	  
	  //need to find a way to add bobcet
	  /**
	  if( value == null) {
		   list.add( new Node<V>(key.substring(iKey), null, null));
		   return null;
		  
	  }
	  **/
	  char keyChar = key.charAt(iKey); //"b"
	  int iNode = 0;
	  
		  while( iNode < list.size() && keyChar > list.get(iNode).sub.charAt(0)) {
			  iNode++;
			  
		  }
		  if( iNode == list.size() || keyChar < list.get(iNode).sub.charAt(0)) {
			  Node <V> node = new Node<V>( key.substring(iKey), key, value);
			  list.add(iNode, node);
			  size++;
			  return null;
		  }
		  // Again, match as much as possible.

		  Node<V> subNode = list.get(iNode);
		  
		  iKey++;
		  
		  int iSub = 1; 
		  while( iKey < key.length() && iSub < subNode.sub.length() && (key.charAt(iKey) == subNode.sub.charAt(iSub)) ) {
			  iKey++;
			  iSub++;		  
		  }




    // If iKey and iSub are both out of range...
    // (Don't forget to set node.key if it was just a placeholder.)
	if( iKey == key.length()  && iSub == subNode.sub.length()) {
		if( subNode.key == null) {
			subNode.key = key;
			size++;
		}
		return subNode.setValue(value);
		
	}

    // If just iSub is out of range, easy recursion.
	if(  iSub == subNode.sub.length() ) {
		 return put(key, iKey, value, subNode.list);
	}
	// Create the new node, insert it into the current node's list,
    // and adjust the current node's sub, key, and value.
	Node<V> newNode = new Node<V>(subNode.sub.substring(iSub), subNode.key, subNode.value);
	newNode.list = subNode.list;
	subNode.sub = subNode.sub.substring(0, iSub);
	subNode.key = null;
	subNode.setValue(null);
	subNode.list = new ArrayList<Node<V>>();
	subNode.list.add(newNode);

   

    // Easy case if iKey is out of range.
	if( iKey == key.length() ) {
		subNode.key = key;
		size++;
		return subNode.setValue(value);
		
	}
	else {
		return put(key, iKey, value, subNode.list);
	}

    // Otherwise recurse.

    //return null;
  }
  
  public V put (String key, V value) {
    // EXERCISE:
    return put(key, 0, value, list);
  }      
  
  public V remove (Object keyAsObject) {
    // EXERCISE:  Delete these lines and implement remove.
    if (false)
      return null;

    return null;
  }

  private V remove (String key, int iKey, List<Node<V>> list) {
    // EXERCISE:

    return null;
  }

  private Iterator<Map.Entry<String, V>> nodeIterator () {
    return new NodeIterator();
  }

  private class NodeIterator implements Iterator<Map.Entry<String, V>> {
    // EXERCISE
    Stack<Iterator<Node<V>>> stack = new Stack<Iterator<Node<V>>>();

    NodeIterator () {
      stack.push(list.iterator());
    }

    public boolean hasNext () {
      // EXERCISE
    	if(stack.empty()) {
    		throw new EmptyStackException();
    	}
    	Iterator<Node<V>> top = stack.peek();
    	
    	while(!stack.empty() && !top.hasNext()) {
    		top = stack.pop();
    		
    		 if( !stack.empty())
    		 	top = stack.peek();
    		 
    		
    	}
    		

      return !stack.empty();
    }

    public Map.Entry<String, V> next () {
      Node<V> node = null;
      if (!hasNext())
        throw new NoSuchElementException();
      // EXERCISE
      node = list.iterator().next();
      if(!stack.empty() && stack.peek().hasNext()){
    	  node = stack.peek().next();
    	  if( !node.list.isEmpty()) {
    		  stack.push(node.list.iterator());
    	  }
      }
      


      return node;
    }

    public void remove () {}
  }

  public Set<Map.Entry<String, V>> entrySet() { return new NodeSet(); }

  private class NodeSet extends AbstractSet<Map.Entry<String, V>> {
    public int size() { return size; }

    public Iterator<Map.Entry<String, V>> iterator () {
      return nodeIterator();
    }

    public void remove () {}
  }

  public String toString () {
    return toString(list, 0);
  }
  
  private String toString (List<Node<V>> list, int indent) {
    // EXERCISE:

    String ind = "";
    // Add indent spaces to ind
    for( int i = 0; i < indent; i++) {
    	ind+= " ";
    }

    String s = "";

    // For each node, print its information.  Don't forget to indent!
    // If it has a non-empty list, recurse.
    for( Node<V> node: list) {
    	s += ind + node.sub + " " + node.key + " " + node.value + "\n";
    	if( node.list != null) {
    		s += toString( node.list, indent + node.sub.length());
    	}
    }


    return s;
  }

  void test () {
    Node<Integer> bob = new Node<Integer>("bob", null, null);
    list.add((Node<V>) bob);
    Node<Integer> by = new Node<Integer>("by", "bobby", 0);
    bob.list.add(by);
    Node<Integer> ca = new Node<Integer>("ca", null, null);
    bob.list.add(ca);
    Node<Integer> lf = new Node<Integer>("lf", "bobcalf", 1);
    ca.list.add(lf);
    Node<Integer> t = new Node<Integer>("t", "bobcat", 2);
    ca.list.add(t);
    Node<Integer> catdog = new Node<Integer>("catdog", "catdog", 3);
    list.add((Node<V>) catdog);
    size = 4;
  }

  public static void main (String[] args) {
    Trie<Integer> trie = new Trie<Integer>();
    trie.test();
    System.out.println(trie);

    UserInterface ui = new ConsoleUI();

    String[] commands = { "toString", "containsKey", "get", "put", "size", "entrySet", "remove", "quit" };
    String key = null;
    int value = -1;

    while (true) {
      int command = ui.getCommand(commands);
      switch (command) {
      case 0:
        ui.sendMessage(trie.toString());
        break;
      case 1:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        ui.sendMessage("containsKey(" + key + ") = " + trie.containsKey(key));
        break;
      case 2:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        ui.sendMessage("get(" + key + ") = " + trie.get(key));
        break;
      case 3:
        key = ui.getInfo("key: ");
        if (key == null) {
          ui.sendMessage("null key");
          break;
        }
        try {
          value = Integer.parseInt(ui.getInfo("value: "));
        } catch (Exception e) {
          ui.sendMessage(value + "not an integer");
          break;
        }
        ui.sendMessage("put(" + key + "," + value + ") = " + trie.put(key, value));
        break;
      case 4:
        ui.sendMessage("size() = " + trie.size());
        break;
      case 5: {
        String s = "";
        for (Map.Entry<String, Integer> entry : trie.entrySet())
          s += entry.getKey() + " " + entry.getValue() + "\n";
        ui.sendMessage(s);
        break;
      }
      case 6:
        break;
      case 7:
        return;
      }
    }
  }
}

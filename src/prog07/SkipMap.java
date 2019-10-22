package prog07;
import java.util.*;

public class SkipMap <K extends Comparable<K>, V> extends DLLMap<K, V> {
  LinkedList<DLLMap<K, Node>> skips = new LinkedList<DLLMap<K, Node>>();

  Node getValue (Node<K,Node> node) {
    if (node == null)
      return null;
    return node.getValue();
  }

  public boolean containsKey (Object keyAsObject) {
    K key = (K) keyAsObject;
    Node start = null;
    for (DLLMap<K, Node> skip : skips)
      start = getValue(skip.find(key, start));
    return containsKey(key, start);
  }

  public V get (Object keyAsObject) {
	   K key = (K) keyAsObject;
	    Node start = null;
	    for (DLLMap<K, Node> skip : skips)
	      start = getValue(skip.find(key, start));
	   if( start != null && containsKey(key, start) )
		   return get(key, start);
	   else
		   return null;
	  
    //return null; // WRONG
  }

  public V remove (Object keyAsObject) {
	   K key = (K) keyAsObject;
	    Node start = null;
	    V value = null;
	    Node remove = null;
	    for (DLLMap<K, Node> skip : skips){
	    remove = start;
	    start = getValue(skip.find(key, start));
	    value = (V)skip.remove(key, remove);
	    value = (V)skip.remove(key, start);
	      
	      
	    }
	    //remove(key,start);
	    return value;
	      
	    
    //return null; // WRONG
  }
    
  public V put (K key, V value) {
	  
    Node start = null;
    
    
    for (DLLMap<K, Node> skip : skips) 
        start = getValue(skip.find(key, start));
    if( containsKey(key, start) ){
    	return put(key, value, start);
    	
    }
    // Get the value of start for the bottom level.
    // If the bottom level list contains the key, then use put
    // to update the value and return.  Make sure to use start!
    
    // Set luck.
    int luck = 0;
    while (heads())
      luck++;

    // Add additional lists if the key was very lucky:
    while (skips.size() < luck)
      skips.offerFirst(new DLLMap<K, Node>());

    start = null;
    DLLMap<K, Node> prevSkip = null;
    Node prevStart = null;
    int level = skips.size();
    for (DLLMap<K, Node> skip : skips) {
      // Save the new start in newStart (don't update start yet).
      Node newStart = getValue(skip.find(key, start));
      
      // If the level is small enough (slAdd), put the key with value
      // null into skip.  You have to use null because you haven't yet
      // created the node in the lower list.
      if( level <= luck ){
    	  skip.put(key, null,start);
      }
     
    	  


      // If the level is small enough, use skip.find to get the node
      // you just created, and then change the null value in the
      // previous list to the node.  Make sure you use the previous start.
      if( level < luck ){
    	  //skip.find(key, prevSkip);
      
     Node node = skip.find(key, start);
     prevSkip.put(key, node, prevStart);
      }


      prevSkip = skip;
      prevStart = start;
      start = newStart;
      level--;
    }

    // Add the key and value to the bottom level (using start).

    // Update the null value in the last skip list (using prevSkip and
    // prevStart).

    put(key, value, start);
    Node node = find(key, start);
    
    if( luck > 0)
    	prevSkip.put(key, node, prevStart);
    
    return null;
  }

  Random random = new Random(2);
  boolean heads () {
    return random.nextInt() % 2 == 0;
  }

  void skipify () {
    skips.clear();
    Node node = head;
    while (node != null && node.next != null) {
      DLLMap<K, Node> skip = new DLLMap<K, Node>();
      while (node != null) {
        node = node.next;
        if (node != null) {
          skip.put((K) node.key, node);
          node = node.next;
        }
      }
      skips.offerFirst(skip);
      node = skip.head;
    }
  }

  public String toString () {
    String s = "";
    for (DLLMap map : skips)
      s += map;
    return s + super.toString();
  }

  void print () {
    for (DLLMap<K, Node> skip : skips) {
      for (Node node = skip.head; node != null; node = node.next)
        System.out.print(node.key + " ");
      System.out.println();
    }
    for (Node node = head; node != null; node = node.next)
      System.out.print(node.key + " ");
    System.out.println();
    for (Node node = head; node != null; node = node.next)
      System.out.print(node.value + " ");
    System.out.println();
  }

  public static void main (String[] args) {
    SkipMap<String, Integer> map = new SkipMap<String, Integer>();
    for (int i = 1; i < 26; i++) {
      String key = "" + (char) ('A' + i);
      Integer val = i;
      map.put(key, val);
    }
    //map.skipify();
    System.out.println(map);
    
    System.out.println("containsKey(A)=" + map.containsKey("A"));
    System.out.println("containsKey(C)=" + map.containsKey("C"));
    System.out.println("containsKey(L)=" + map.containsKey("L"));
    System.out.println("containsKey(M)=" + map.containsKey("M"));
    System.out.println("containsKey(Z)=" + map.containsKey("Z"));
    System.out.println("containsKey(Zoe)=" + map.containsKey("Zoe"));
    
    System.out.println("get(A)=" + map.get("A"));
    System.out.println("get(C)=" + map.get("C"));
    System.out.println("get(L)=" + map.get("L"));
    System.out.println("get(M)=" + map.get("M"));
    System.out.println("get(Z)=" + map.get("Z"));
    System.out.println("get(Zoe)=" + map.get("Zoe"));
    
    System.out.println("remove(A)=" + map.remove("A"));
    System.out.println("remove(C)=" + map.remove("C"));
    System.out.println("remove(L)=" + map.remove("L"));
    System.out.println("remove(Q)=" + map.remove("Q"));
    System.out.println("remove(Z)=" + map.remove("Z"));
    System.out.println("remove(Zoe)=" + map.remove("Zoe"));
    
    System.out.println(map);
    
    System.out.println("put(A,10)=" + map.put("A",10));
    System.out.println("put(A,11)=" + map.put("A",11));
    System.out.println("put(L,20)=" + map.put("L",20));
    System.out.println("put(L,21)=" + map.put("L",21));
    System.out.println("put(Z,30)=" + map.put("Z",30));
    System.out.println("put(Z,31)=" + map.put("Z",31));

    System.out.println(map);
  }
}

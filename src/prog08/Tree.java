package prog08;
import java.util.*;

public class Tree <K extends Comparable<K>, V>
  extends AbstractMap<K, V> {

  private class Node <K extends Comparable<K>, V>
    implements Map.Entry<K, V> {
    K key;
    V value;
    Node left, right;
    
    Node (K key, V value) {
      this.key = key;
      this.value = value;
    }

    public K getKey () { return key; }
    public V getValue () { return value; }
    public V setValue (V newValue) {
      V oldValue = value;
      value = newValue;
      return oldValue;
    }
  }
  
  private Node root;
  private int size;

  public int size () { return size; }

  /**
   * Find the node with the given key.
   * @param key The key to be found.
   * @return The node with that key.
   */
  private Node<K, V> find (K key, Node<K,V> root) {
    // EXERCISE:
	  if( root == null )
		  return null;
	  
	  if( key.compareTo(root.key ) == 0 )
		  return root;
	  
	  if( key.compareTo(root.key ) > 0)
		  return find(key, root.right);
	  else
		  return find(key, root.left);
	  
	 // return null;

  }    

  public boolean containsKey (Object key) {
    return find((K) key, root) != null;
  }
  
  public V get (Object key) {
    Node<K, V> node = find((K) key, root);
    if (node != null)
      return node.value;
    return null;
  }
  
  public boolean isEmpty () { return size == 0; }
  
  /**
   * Add key,value pair to tree rooted at root.
   * Return root of modified tree.
   */
  private Node<K,V> add (K key, V value, Node<K,V> root) {
    // EXERCISE:
	  if( root == null )
		  return new Node<K,V>(key, value);
	  if( key.compareTo(root.key) < 0) {
		  root.left = add(key, value, root.left);
		  return root;	  
	  }
	  else {
		 root.right = add(key,value, root.right);
		  return root;
	  }
		  

    //return root;
  }
  
  int depth (Node root) {
    if (root == null)
      return -1;
    return 1 + Math.max(depth(root.left), depth(root.right));
  }

  public V put (K key, V value) {
    // EXERCISE:
	  Node<K,V> node = find(key, root);
	  if( node == null) {
		  root = add(key, value, root);
		  size++;
		  return null;
	  }
	  else {
		  V oldValue = node.setValue(value);
		  return oldValue;
	  }
		  


    //return null;
  }      
  
  public V remove (Object keyAsObject) {
    // EXERCISE:  Delete these lines and implement remove.
	  /**
	  Node<K,V> node = find((K) keyAsObject, root);
	  if( node != null) {
		  remove( (K)keyAsObject, root);
		  size--;
		  return (V)node.getValue();
		  
		  
	  }
	  else
		  return null;
		**/
	  K key1 = (K) keyAsObject;
	  if( containsKey(keyAsObject) != false) {
		  root = remove(key1, root);
		  size--;
		  return (V)root;
	  }
	  return null;
   
  }

  private Node<K,V> remove (K key, Node<K,V> root) {
    // EXERCISE:
	  if( key.compareTo(root.key) == 0) {
		  return removeRoot(root);
	  }
	  if( key.compareTo(root.key) < 0)
		   root.left=remove(key, root.left);
	  else
		  root.right = remove(key, root.right);

    return root;
  }

  /**
   * Remove root of tree rooted at root.
   * Return root of BST of remaining nodes.
   */
  private Node<K,V> removeRoot (Node<K,V> root) {
	  if( root.left == null)
		  return  root = root.right;
	  if( root.right == null)
		  return  root = root.left;
	 
	  /**
	  Node<K,V> newRoot = moveMinToRoot(root.right);
	  Node<K,V> newRight = newRoot.right;
	  Node<K,V> oldLeft = root.left;
	  root = newRoot;
	  root.left = oldLeft;
	  root.right = newRight;
	  **/
	  Node rstroot = root.right;
	  Node lstroot = root.left;
	  
	  rstroot = moveMinToRoot(rstroot);
	  rstroot.left = lstroot;
	  root = rstroot;
	  
	  
    // IMPLEMENT    

    return root;
  }

  /**
   * Move the minimum key (leftmost) node to the root.
   * Return the new root.
   */
  private Node<K,V> moveMinToRoot (Node<K,V> root) {
    // IMPLEMENT
	  Node<K,V> min = root;
	  if(min.left == null) {
		  return min;
	  }
	  
	  min = moveMinToRoot(min.left);
	  min.right = root.left;
	  if(min.right != null) {
		  root.left = min.right;
	  }
	  min.right = root;

	  root.left = null;
	  
	
    return min;
  }

  public Set<Map.Entry<K, V>> entrySet () { return null; }
  
  public String toString () {
    return toString(root, 0);
  }
  
  private String toString (Node root, int indent) {
    if (root == null)
      return "";
    String ret = toString(root.right, indent + 2);
    for (int i = 0; i < indent; i++)
      ret = ret + "  ";
    ret = ret + root.key + " " + root.value + "\n";
    ret = ret + toString(root.left, indent + 2);
    return ret;
  }

  public static void main (String[] args) {
    Tree<Character, Integer> tree = new Tree<Character, Integer>();
    String s = "balanced";
    
    for (int i = 0; i < s.length(); i++) {
      tree.put(s.charAt(i), i);
      System.out.print(tree);
      System.out.println("-------------");
    }

    for (int i = 0; i < s.length(); i++) {
      tree.remove(s.charAt(i));
      System.out.print(tree);
      System.out.println("-------------");
    }
  }
}

package prog06;
import java.util.*;
import java.io.*;

import prog02.GUI;
import prog02.UserInterface;;
//To Do:
// Finish implementing ArrayList and the loadDictionary() method.  Step 7 in HW

public class WordStep{
	
	//user interface
	private static UserInterface ui = new GUI();
	
	//WordStep uses Nodes to store specific dictionary words
	private static class Node{
		//Data fields
		private String name;
		
		private Node previous;
		
		//constructor
		
		private Node( String name ){
			this.name = name;
			previous = null;
		}
		
	}
	
	private static List<Node> nodes = new ArrayList<Node>();

	public static void main( String[] args )
	{
		//initializes game as WordStep
		WordStep game = new WordStep();
		//gets dictionary from user
		String filename = ui.getInfo("Enter dictionary file: ");
		//calls dictionary method
		game.loadDictionary(filename);
		
		int choiceNum = 0;
		String start, target;
		
		start = ui.getInfo("Input starting word: ");
		if( find(start)  == null){
			ui.sendMessage( start + " is not in dictionary");
			return;}
			
		target = ui.getInfo("Target word: ");
		if( find(target) == null ){
			ui.sendMessage(target + " is not in dictionary");
			return;}
		
		
		String[] commands = { "Human plays.", "Computer plays." };
		choiceNum = ui.getCommand(commands);
		if( choiceNum == 0){
			game.play(start, target);
		}
		if( choiceNum == 1){
			game.solve(start, target);
		}		
	}
	public void play( String start, String target){
		String current;
		while(true){
			ui.sendMessage("Current word: " + start +"\n" + "Target word: " + target);
			current = ui.getInfo("What is your next word?");
			if( !oneLetter(current, start) || (find(current) == null )){
				
				if( find(current) == null ){
					ui.sendMessage("Sorry, but " + current + " is not in the dictionary.");	
				}
				else{
					ui.sendMessage("Sorry, but " + current + " differs by more than one letter from " + start);
				}
				
			}
			else{
				start = current;	
			}
			if( start.equals(target)){
				ui.sendMessage("You win!");
				return;
			}
				
		}
		
	}
	public static boolean oneLetter( String one, String two){
		int count = 0;
		if( one.length() == two.length() ){
			for( int i =0; i < one.length(); i ++){
				if( one.charAt(i) != two.charAt(i) )
					count++;
				if( count > 1)
					return false;
				
			}
			return true;
			
		}
		else{
			return false;
		}
	}
	
	public void solve( String start, String target){
		ArrayQueue<Node> wordlist = new ArrayQueue<Node>();
		Node startNode = find(start);
		String s = "";
		
		wordlist.offer(startNode);
		Node nextNode;
		
		while(wordlist.size() != 0 )
		{
			Node theNode = wordlist.poll();
			for( Node currentNode : nodes ){
				if( (currentNode != startNode) && (currentNode.previous == null) &&
						(oneLetter(theNode.name, currentNode.name) ) ){
					nextNode = currentNode;
					nextNode.previous = theNode;
					wordlist.offer(nextNode);
					
					if( nextNode.name.equals(target) ){
						ui.sendMessage("Success! " + start + " to " + target);
						while(nextNode != null){
							s = s + "\n" + nextNode.name;
							nextNode = nextNode.previous;
							
						}
						ui.sendMessage(s);
						return;
						
					}
				}
			}
		}
		ui.sendMessage("No solution");
		
	}
	
	  public void loadDictionary (String sourceName) {
		    // Remember the source name.
		    //this.sourceName = sourceName;
		    try {
		      // Create a Scanner for the file.
		      Scanner in = new Scanner(new File(sourceName));
		      //Node previousEntry = null;

		      // Read each name and number and add the entry to the array.
		      while (in.hasNextLine()) {
		        String name = in.nextLine();
		        Node newEntry = new Node(name);
		        //if( previousEntry == null)
		        	//newEntry.previous = null;
		        //if( previousEntry != null)
		        	//newEntry.previous = previousEntry;
		        nodes.add(newEntry);
		        //previousEntry = newEntry;
		        // Add an entry for this name and number.
		       
		      }
		      // Close the file.
		      in.close();
		      //return true;
		    } catch (FileNotFoundException ex) {
		      // Do nothing: no data to load.
		    	ui.sendMessage("Dictionary file not found!");
		      return;
		    } catch (Exception ex) {
		      System.err.println("Load of directory failed.");
		      ex.printStackTrace();
		     
		      return;
		    }
		  }
	  public static Node find( String word)
	  {
		  for( Node currentNode : nodes ){
			  if( currentNode.name.equals( word) )
				  return currentNode;
		  }
		  return null;
	  }
}
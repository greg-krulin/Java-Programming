package prog02;

/**
 *
 * @author vjm
 */
public class Main {

	/** Processes user's commands on a phone directory.
      @param fn The file containing the phone directory.
      @param ui The UserInterface object to use
             to talk to the user.
      @param pd The PhoneDirectory object to use
             to process the phone directory.
	 */
	public static void processCommands(String fn, UserInterface ui, PhoneDirectory pd) {
		pd.loadData(fn);

		String[] commands = {
				"Add/Change Entry",
				"Look Up Entry",
				"Remove Entry",
				"Save Directory",
		"Exit"};

		String name, number, oldNumber;

		while (true) {
			int c = ui.getCommand(commands);
			switch (c) {
			case -1:
				ui.sendMessage("You clicked the red x, restarting.");
				break;
			case 0:
				//Add/Change Entry
				// Ask for the name.
				// !!! Check for null (cancel) or "" (blank)
				// Ask for the number.
				// !!! Check for cancel.  Blank is o.k.
				// Call addOrChangeEntry
				// Report the result
				name = ui.getInfo("Input name of person you are adding: ");
				
				if(name == null)
					break;
				
				if( name.equals(""))
				{
					ui.sendMessage("Blank entry not allowed.");
					break;
				}
				
				if( name.equals(null))
				{
					break;
				}
				number = ui.getInfo("Input number of the person: ");
				
				//If you read this program Dr. Milenkovic, would you know why in the lab oldNumber.equals("") worked
				//but now in the comforts of my home, it has stopped working and oldNumber == null is the way to go.
				//Reading a bit online, it makes sense why I was getting a java.lang.NullPointerException now since addOrChangeEntry()
				//is suppose to return null, but now the question is why did it work in lab?
				//Thank you.
				oldNumber = pd.addOrChangeEntry(name, number);
				if( oldNumber == null && !number.equals("")){
					ui.sendMessage("Added " + name + " with number " + number + ".");
					break;
				}
				
				if( oldNumber == null && number.equals(""))
				{
					ui.sendMessage("Added " + name + " to directory.");
					break;
				}
				else
				{
					ui.sendMessage("Replaced " + oldNumber + " with the new number, " + number + ", for " + name + ".");
					break;
				}
				
			case 1:
				//Look up entry
				name = ui.getInfo("Input name you are looking for: ");
				
				if(name == null)
					break;
				
				if( name.equals(""))
				{
					ui.sendMessage("Blank entry not allowed.");
					break;
				}
				
				
				else
				{
					number = pd.lookupEntry(name);
					
					if( number == null )
					{
						ui.sendMessage(name + " is not in the phone directory.");
						break;
					}
					
					if( number.equals(""))
					{
						ui.sendMessage(name + " does not have a number.");
						break;
					}
					
					else
					{
						ui.sendMessage(name + "'s number is: " + number);
						break;
					}
				}
				
			case 2:
				name = ui.getInfo("Please enter name you want to remove: ");
				
				if(name == null)
					break;
				if( name.equals(""))
				{
					ui.sendMessage("Blank entry not allowed.");
					break;
				}
				
				
				number = pd.removeEntry(name);
				if( number == null)
				{
					ui.sendMessage(name + " is not in directory");
					break;
				}
				else
				{
					ui.sendMessage(name + " with number " + number + " has been removed.");
					break;
				}
				
				//Remove Entry
				// implement
			case 3:
				pd.save();
				ui.sendMessage("Saved.");
				//Save Directory
				// implement
				break;
			case 4:
				// implement
				//Exit option on pop-up
				ui.sendMessage("Goodbye!");
				return;
			}
		}
	}

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		String fn = "csc220.txt";
		PhoneDirectory pd = new SortedPD();
		UserInterface ui = new GUI();
		processCommands(fn, ui, pd);
	}
}

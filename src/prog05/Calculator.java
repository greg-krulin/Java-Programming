package prog05;

import java.util.Stack;
import prog02.UserInterface;
import prog02.GUI;

public class Calculator {
  Object[] tokenize (String s) {
    input = s;
    index = 0;
    Object[] out = new Object[s.length()];
    int n = 0;
    
    while (!atEndOfInput ()) {
      if (isNumber())
        out[n++] = getNumber();
      else
        out[n++] = s.charAt(index++);
    }

    Object[] out2 = new Object[n];
    System.arraycopy(out, 0, out2, 0, n);
    return out2;
  }

  String input = null;
  int index = 0;

  boolean atEndOfInput () {
    while (index < input.length() &&
           Character.isWhitespace(input.charAt(index)))
      index++;
    return index == input.length();
  }

  boolean isNumber () {
    return Character.isDigit(input.charAt(index));
  }

  double getNumber () {
    int index2 = index;
    while (index2 < input.length() &&
           (Character.isDigit(input.charAt(index2)) ||
            input.charAt(index2) == '.'))
      index2++;
    double d = 0;
    try {
      d = Double.parseDouble(input.substring(index, index2));
    } catch (Exception e) {
      System.out.println(e);
    }
    index = index2;
    return d;
  }

  char getOperator () {
    char op = input.charAt(index++);
    if (OPERATORS.indexOf(op) == -1)
      System.out.println("Operator " + op + " not recognized.");
    return op;
  }

  Stack<Double> numberStack = new Stack<Double>();
  Stack<Character> operatorStack = new Stack<Character>();

  String numberStackToString () {
    String s = "numberStack: ";
    Stack<Double> helperStack = new Stack<Double>();
    // EXERCISE
    // Put every element of numberStack into helperStack
    // You will need to use a loop.  What kind?
    // What condition? When can you stop moving elements out of numberStack?
    // What method do you use to take an element out of numberStack?
    // What method do you use to put that element into helperStack.
    while( !numberStack.empty() )
    {
    	helperStack.push(numberStack.pop());
    }


    // Now put them back, but also add each one to s:
    // s = s + " " + number;
    while( !helperStack.empty() )
    {
    	double t = helperStack.pop();
    	numberStack.push(t);
    	s = s + " " + t;
    }


    return s;
  }

  String operatorStackToString () {
    String s = "operatorStack: ";
    // EXERCISE
    Stack<Character> helperStack = new Stack<Character>();
    while( !operatorStack.empty() )
    {
    	helperStack.push(operatorStack.pop() );
    }
    
    while( !helperStack.empty() )
    {
    	char t = helperStack.pop();
    	operatorStack.push(t);
    	s = s + " " + t;
    }


    return s;
  }

  static UserInterface ui = new GUI();

  void displayStacks () {
    ui.sendMessage(numberStackToString() + "\n" +
                   operatorStackToString());
  }

  double evaluate (String expr) {
    // Clean up any previous bad input.
    while (!operatorStack.empty()) operatorStack.pop();
    while (!numberStack.empty()) numberStack.pop();

    // Read the new input.
    Object[] inputs = tokenize(expr);

    for (int i = 0; i < inputs.length; i++) {
      if (inputs[i] instanceof Double) {
        double x = (Double) inputs[i];
        numberStack.push(x);
        displayStacks();
      }
      else {
        char o = (Character) inputs[i];
        if( o =='-' && i == 0)
        	processOperator('u');
        
        if( o == '-' && i != 0)
        {
        	if( (inputs[i-1] instanceof Double) || ((inputs[i-1].toString().compareTo(")") == 0 ) ) )
        			{
        				processOperator(o);
        			}
        	else{
        		processOperator( 'u');
        	}
        }
        if( o != '-'){
        processOperator(o);
        //operatorStack.push(o);
        displayStacks();
        }
      }
    }
    while( !operatorStack.empty() && !numberStack.empty() )
    	evaluateOperator ();
    	
    
 

    return numberStack.pop();
  }

  static final String OPERATORS = "()+-u*/^";
  static final int[] PRECEDENCE = { -1, -1, 1, 1, 2, 2, 2, 3 };
  int precedence (char op) {
    return PRECEDENCE[OPERATORS.indexOf(op)];
  }
  //evalutes doubles with the operator
  //prior: takes in two doubles and a single char
  //after: returns one double depending on the variables
  double evaluateOperator (double a, char op, double b)
  {
	  if( op == '+' )
		  return  a + b;
	  if( op == '-')
		  return a - b;
	  if( op == '*')
		  return a * b;
	  if( op == '/')
		  return  a/b;
	  if( op == '^')
		  return Math.pow(a,b);
	  else
	  {
		  return 0;
	  }  
  }
  //prior: pop first two numbers from numberstack and first operator from operator stack
  //after: evaluates the variables and then pushes the new number onto the number stack
  void evaluateOperator ()
  {
	  double a;
	  char c = operatorStack.pop();
	  if( c == 'u'){
		  a = numberStack.pop();
		  numberStack.push(-a);
		  displayStacks();
		  return;
	  }
	  else{
		  
	  a = numberStack.pop();
	  double b = numberStack.pop();
	  double eva = evaluateOperator(b, c, a);
	  numberStack.push(eva);
	  displayStacks();
	  return;
	  }
  }
  
  //While the top element (if there is one) of the operator stack
  //has >= precedence than op,
  //evaluate it using evaluateOperator().
  //Then push op on the stack.
  void processOperator (char op)
  {
	  if( op == '(')
	  {
		  operatorStack.push(op);
		  return;
	  }
	  
	  if( op == ')')
	  {
		  while( operatorStack.peek() != '(')
		  {
			  evaluateOperator();
		  }
		  operatorStack.pop();
		  return;
	  }
	  if( op == 'u')
	  {
		  operatorStack.push(op);
		  displayStacks();
		  return;
	  }
	  else{
		  while( !operatorStack.empty() &&operatorStack.peek() != '(' && (precedence(operatorStack.peek() ) >= precedence(op))   )
		  {
			  evaluateOperator();
		  }
		  operatorStack.push(op);
		  }
  }

  public static void main (String[] args) {
    Calculator parser = new Calculator();

    while (true) {
      String line = ui.getInfo("Enter arithmetic expression or cancel.");
      if (line == null)
        return;

      try {
        double result = parser.evaluate(line);
        ui.sendMessage(line + " = " + result);
      } catch (Exception e) {
        System.out.println(e);
      }
    }
  }
}

import java.util.Arrays;
import java.util.HashMap;
import java.util.Stack;

public class LR_Parsing_And_Making_Parse_Tree {
	
	//for Action
	final static int a_INDEX = 0;
	final static int b_INDEX = 1;
	final static int c_INDEX = 2;
	final static int d_INDEX = 3;
	final static int DOLLAR_SYM_INDEX = 4;
	//for Goto
	final static int E_INDEX = 0;
	final static int S_INDEX = 1;
	final static int A_INDEX = 2;
	final static int B_INDEX = 3;
	final static int C_INDEX = 4;
	
	//for return values of parse
	final static int DONE = 0;
	final static int NOT_DONE = 1;
	
	static boolean wasAccepted = false;
	
	//used to get integer index value for token
	static HashMap<String, Integer> symbolToIndex = new HashMap<String, Integer>();
	
	//store Action and Goto values from the LR parsing table
	static String[][] ActionTable = new String[24][5];
	static String[][] GotoTable = new String[24][5];
	//Rules[i][0] = LHS of rule
	//Rules[i][1] = number of symbols on RHS. will pop Rules[i][1]*2 from stack when Reducing since there is a symbol and state pair
	static String[][] Rules = new String[9][2];
	
	//for input and the current stack
//	static Stack<String> input = new Stack<String>();
//	static Stack<String> stack = new Stack<String>();
	
	static Stack<node> input = new Stack<node>();
	static Stack<node> stack = new Stack<node>();
	
	public static void main(String[] args) {
		//initialize HashMap
		initializeHashMap();
		
		//initialize the 2d arrays that store the table values based on the indices
		initializeArrays();
		
		//initialize input and stack
		input.push(new node(false, "$"));
		//String[] inputString = {"a","b","b","b","c","c","d"};
		//String[] inputString = {"a","c","c","d"};
		//String[] inputString = {"a","c","d","b","a","a","c","d"};
		//String[] inputString = {"a","c","d","b","d"};
		String[] inputString = {"a","b","c","d","b","a","d"};
		for(int i = inputString.length-1; i  >= 0; i--) {
			input.push(new node(false, inputString[i]));
		}
		
		
		stack.push(new node(true, "0"));

//		stack.push(input.pop());
//		stack.push(input.pop());
//		System.out.println(Arrays.toString(stack.toArray()));

//		for(int i = 0; i < ActionTable.length; i++) {
//			for(int j = 0; j < ActionTable[i].length; j++) {
//				System.out.print(ActionTable[i][j] + " ");
//			}
//			System.out.println();
//		}
//		if(ActionTable[0][1] == null) {
//			System.out.println("was null");
//		}
		
		boolean isDone = false;
		while(!isDone & count < 100) { //just in case program doesn't terminate, stop while loop when parse called 100 times
			//System.out.println("in while");
			int doneOrNot = NOT_DONE;
			//try {
				doneOrNot = parse(); //parse returns DONE or NOT_DONE which are type int
			//} catch(Exception exc) { //might have some exception popping from stacks
				//System.out.println(exc);
			//}
			
			if(doneOrNot == DONE) {
				isDone = true; //no longer need to check input anymore
			}
		}
		//do pre-order traversal of tree if input was accepted as valid
		if(wasAccepted) {
			stack.pop(); //should be state, which is not needed at this point
			node root = stack.pop();
			System.out.println("\nPre-order traversal of parse tree generated:");
			preOrderTraversal(root);
		}
	}
	
	static void preOrderTraversal(node currentNode) {
		if(currentNode != null) {
			System.out.print(currentNode + " ");
			preOrderTraversal(currentNode.child1);
			preOrderTraversal(currentNode.child2);
			preOrderTraversal(currentNode.child3);
		}
	}
	
	static void initializeHashMap() {
		//symbolToIndex
		
		//Action
		symbolToIndex.put("a", a_INDEX);
		symbolToIndex.put("b", b_INDEX);
		symbolToIndex.put("c", c_INDEX);
		symbolToIndex.put("d", d_INDEX);
		symbolToIndex.put("$", DOLLAR_SYM_INDEX);
		
		//Goto
		symbolToIndex.put("E", E_INDEX);
		symbolToIndex.put("S", S_INDEX);
		symbolToIndex.put("A", A_INDEX);
		symbolToIndex.put("B", B_INDEX);
		symbolToIndex.put("C", C_INDEX);
		
		
		
		
		//ruleNumber_To_numberSymbolsRHS
		//ruleNumber_To_numberSymbolsRHS.put();
	}
	
	static void initializeArrays() {
		//ActionTable
		
		//state 0
		ActionTable[0][a_INDEX] = "S,2";
		//state 1
		ActionTable[1][DOLLAR_SYM_INDEX] = "accept";
		//state 2
		ActionTable[2][b_INDEX] = "S,8";
		ActionTable[2][c_INDEX] = "S,6";
		ActionTable[2][d_INDEX] = "S,7";
		//state 3
		ActionTable[3][a_INDEX] = "S,10";
		ActionTable[3][c_INDEX] = "S,11";
		ActionTable[3][d_INDEX] = "S,7";
		//state 4
		ActionTable[4][DOLLAR_SYM_INDEX] = "R,1";
		//state 5
		ActionTable[5][b_INDEX] = "S,12";
		//state 6
		ActionTable[6][c_INDEX] = "S,14";
		ActionTable[6][d_INDEX] = "S,15";
		//state 7
		ActionTable[7][DOLLAR_SYM_INDEX] = "R,8";
		//state 8
		ActionTable[8][b_INDEX] = "S,8";
		ActionTable[8][c_INDEX] = "S,17";
		//state 9
		ActionTable[9][DOLLAR_SYM_INDEX] = "R,2";
		//state 10
		ActionTable[10][a_INDEX] = "R,4";
		ActionTable[10][c_INDEX] = "R,4";
		ActionTable[10][d_INDEX] = "R,4";
		//state 11
		ActionTable[11][c_INDEX] = "S,11";
		ActionTable[11][d_INDEX] = "S,7";
		//state 12
		ActionTable[12][a_INDEX] = "R,3";
		ActionTable[12][c_INDEX] = "R,3";
		ActionTable[12][d_INDEX] = "R,3";	
		//state 13
		ActionTable[13][b_INDEX] = "R,6";
		ActionTable[13][DOLLAR_SYM_INDEX] = "R,7";		
		//state 14
		ActionTable[14][c_INDEX] = "S,14";
		ActionTable[14][d_INDEX] = "S,15";		
		//state 15
		ActionTable[15][b_INDEX] = "R,8";
		ActionTable[15][DOLLAR_SYM_INDEX] = "R,8";	
		//state 16
		ActionTable[16][b_INDEX] = "R,5";
		//state 17
		ActionTable[17][c_INDEX] = "S,21";
		ActionTable[17][d_INDEX] = "S,22";
		//state 18
		ActionTable[18][DOLLAR_SYM_INDEX] = "R,7";
		//state 19
		ActionTable[19][b_INDEX] = "R,7";
		ActionTable[19][DOLLAR_SYM_INDEX] = "R,7";
		//state 20
		ActionTable[20][b_INDEX] = "R,6";
		//state 21
		ActionTable[21][c_INDEX] = "S,21";
		ActionTable[21][d_INDEX] = "S,22";
		//state 22
		ActionTable[22][b_INDEX] = "R,8";
		//state 23
		ActionTable[23][b_INDEX] = "R,7";
				
				
				
				
				
		//Goto Table
		
		//state 0
		GotoTable[0][S_INDEX] = "1";
		GotoTable[0][A_INDEX] = "3";
		//state 2
		GotoTable[2][B_INDEX] = "5";
		GotoTable[2][C_INDEX] = "4";
		//state 3
		GotoTable[3][C_INDEX] = "9";
		//state 6
		GotoTable[6][C_INDEX] = "13";
		//state 8
		GotoTable[8][B_INDEX] = "16";
		//state 11
		GotoTable[11][C_INDEX] = "18";
		//state 14
		GotoTable[14][C_INDEX] = "19";
		//state 17
		GotoTable[17][C_INDEX] = "20";
		//state 21
		GotoTable[21][C_INDEX] = "23";
		
		
		
		
		
		//Rules
		
		//rule 1
		Rules[0][0] = "S";
		Rules[0][1] = "2";
		//rule 2
		Rules[1][0] = "S";
		Rules[1][1] = "2";
		//rule 3
		Rules[2][0] = "A";
		Rules[2][1] = "3";
		//rule 4
		Rules[3][0] = "A";
		Rules[3][1] = "2";
		//rule 5
		Rules[4][0] = "B";
		Rules[4][1] = "2";
		//rule 6
		Rules[5][0] = "B";
		Rules[5][1] = "2";
		//rule 7
		Rules[6][0] = "C";
		Rules[6][1] = "2";
		//rule 8
		Rules[7][0] = "C";
		Rules[7][1] = "1";
		
	}
	static int count = 0;
	static int parse() {//throws Exception{
		count++;
		int state = Integer.parseInt(stack.peek().value);
		int symbol = symbolToIndex.get(input.peek().value); //which column based on the token
		//System.out.println("state: " + state + "   column: " + column);
		String action = ActionTable[state][symbol];
		String[] actionSplit = {};
		
		//may need to change below print statements
		System.out.println("Stack: " + Arrays.toString(stack.toArray()));
		System.out.println("Input: " + Arrays.toString(input.toArray()));
		System.out.print("Action: " + action);
		//System.out.println();
		
		if(  ( action != null ) && ( !action.equals("accept") )  )  { //short circuit and (&&) because if null then checking .equals(null) will throw exception
			actionSplit = action.split(","); //actions have comma separators in the ActionTable
			//System.out.println("Action Split: " + actionSplit[0] + " " + actionSplit[1] );
		}
		else  {
			if(action == null) { //goes first and returns so that .equals(null) on next if will never occur
				System.out.println("\n\nInput WAS NOT accepted.");
				System.out.println("Reason: Cell in the Action Table corresponding to state " + stack.peek() + " and symbol " + input.peek() + " contained no action.");
				return DONE;
			}
			if(action.equals("accept")) {
				wasAccepted = true;
				System.out.println("\n\nInput WAS accepted.");
				return DONE;
			}
			
		}
		//System.out.println("before shift");
		
		//shift
		if(actionSplit[0].equals("S")) { //can't compare strings with == Need to use .equals()
			//System.out.println("in shift");
			stack.push(input.pop()); //top of input stack pushed onto stack
			stack.push(new node(true, actionSplit[1])); //next state from ActionTable pushed onto stack
		}
		
		//reduce
		if(actionSplit[0].equals("R")) {
			int rule = Integer.parseInt(actionSplit[1]); //get rule
			int numToPop = Integer.parseInt(Rules[rule-1][1]); //get number of symbols on RHS of rule
			node[] poppedItems = new node[numToPop]; //will become children of LHS pushed onto stack ahead
			for(int i = 0; i < numToPop*2; i++) {
				node current = stack.pop(); //pop number of symbols*2 because each symbol is paired with state in stack
				if(i%2 != 0) { //odd i means item being popped should NOT be a state
					poppedItems[(int)(i/2)] = current; //so add that non state node to the array. (int)(i/2) to map i=1 -> 0 i=3 ->1 i=5 -> 2 etc.
					//index 0 will be right most item popped from stack, so that should be child 3(the right most child) of the LHSnode made ahead
				}
			}
			state = Integer.parseInt(stack.peek().value); //state in stack after popping above is done
			String LHS = Rules[rule-1][0]; //get symbol in LHS of the rule
			symbol = symbolToIndex.get(LHS); //get int value of column index for that LHS symbol
			String nextState = GotoTable[state][symbol]; //use state and symbol to find next state in GotoTable
			System.out.print(" (use GOTO["+state+", "+ LHS+"])");
			node LHSnode = new node(false, LHS);
			//length = 3: child3 = 0, child2 = 1 child1 = 2
			//length = 2: child2 = 0 child1 = 1
			//length = 1: child1 = 0
			switch(poppedItems.length) {
				case 1:
					LHSnode.child1 = poppedItems[0];
					break;
				case 2:
					LHSnode.child1 = poppedItems[1];
					LHSnode.child2 = poppedItems[0];
					break;
				case 3:
					LHSnode.child1 = poppedItems[2];
					LHSnode.child2 = poppedItems[1];
					LHSnode.child3 = poppedItems[0];
					break;
			}
			stack.push(LHSnode); //push LHS of rule onto stack
			stack.push(new node(true, nextState)); //push the state found from the GotoTable onto stack
		}
		
		System.out.println("\n");
		//didn't find an empty(null) action or the accept action, so the input can should still be parsed.
		return NOT_DONE;
	}
}

class node {
	boolean isState;
	String value;
	//rule 3 of grammar has 3 children on RHS so allow max of 3 children
	node child1;
	node child2;
	node child3;
	public node(boolean isState, String value) {
		this.isState = isState;
		this.value = value;
		child1 = null;
		child2 = null;
		child3 = null;
	}
	@Override
	public String toString() {
		return value;
	}
}





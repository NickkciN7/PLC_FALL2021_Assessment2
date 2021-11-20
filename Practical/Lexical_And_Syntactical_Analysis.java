import java.io.*;
import java.util.HashMap;
import java.util.regex.Pattern;

public class Lexical_And_Syntactical_Analysis {
	static LexicalAnalyzer lexAnalyzer;
	public static void main(String[] args) {
		
		//lexAnalyzer = new LexicalAnalyzer("switch_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("foreach_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("for_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("while_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("dowhile_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("if_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("assignment_test.txt");
		//lexAnalyzer = new LexicalAnalyzer("return_test.txt");
		
		lexAnalyzer = new LexicalAnalyzer("test.txt");
		
		program();
		lexAnalyzer.closeFile();
	}
	
	public static void program() {
		System.out.println("Enter <program>");
		if(lexAnalyzer.nextToken != lexAnalyzer.VOID_CODE) {
			error("PROGRAM");
		} 
		else {
			lexAnalyzer.lex();
			if(lexAnalyzer.nextToken != lexAnalyzer.MAIN_CODE) {
				error("PROGRAM");
			} 
			else {
				lexAnalyzer.lex();
				if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_PAREN) {
					error("PROGRAM");
				} 
				else {
					lexAnalyzer.lex();
					if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
						error("PROGRAM");
					}
					else {
						block();
					}
				}
			}
		}
		System.out.println("Exit <program>");
	}
	
	public static void block() {
		System.out.println("Enter <block>");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_CURLY) {
			error("BLOCK");
		} 
		else {
			lexAnalyzer.lex();
			statement();
			//make all statements called in statement call lex() at end(which should set nextToken = SEMICOLON)
			if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
				//System.out.println("HERE");
				error("BLOCK");
			}
			else {
				while(lexAnalyzer.nextToken == lexAnalyzer.SEMICOLON) {
					lexAnalyzer.lex();
					if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_CURLY) { 
						statement();
						if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
							
							error("BLOCK");
						}
					}
				}
			}

			//getStatements("BLOCK");
			
		}
		if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_CURLY) {
			
			error("BLOCK");
		}
		System.out.println("Exit <block>");
	}
	
//	private static void getStatements(String methodName) {
//		lexAnalyzer.lex();
//		statement();
//		//make all statements called in statement call lex() at end(which should set nextToken = SEMICOLON)
//		if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
//			error(methodName);
//		}
//		else {
//			while(lexAnalyzer.nextToken == lexAnalyzer.SEMICOLON) {
//				lexAnalyzer.lex();
//				if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_CURLY) { 
//					statement();
//				}
//			}
//		}
//	}

	public static void statement() {
		System.out.println("Enter <statement>");
		//lexAnalyzer.lex();
		if(lexAnalyzer.nextToken == lexAnalyzer.SWITCH_CODE) {
			Switch_Stmt();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.FOR_CODE) {
			For_or_Foreach_Stmt();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.WHILE_CODE) {
			While_Stmt();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.DO_CODE) {
			DoWhile_Stmt();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.IF_CODE) {
			If_Stmt();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.IDENT) {
			Assignment_Stmt();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.RETURN_CODE) {
			Return_Stmt();
		}
		else {
			error("STATEMENT");
		}
		System.out.println("Exit <statement>");
	}
	
	//c or java
	public static void Switch_Stmt() {
		System.out.println("Enter <switch_statement>");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_PAREN) {
			error("SWITCH_STMT");
		}
		else {
			lexAnalyzer.lex();
			if(lexAnalyzer.nextToken != lexAnalyzer.IDENT) {
				error("SWITCH_STMT");
			}
			else {
				lexAnalyzer.lex();
				if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
					error("SWITCH_STMT");
				}
				else {
					lexAnalyzer.lex();
					if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_CURLY) {
						error("SWITCH_STMT");
					}
					else {
						lexAnalyzer.lex();
						if(lexAnalyzer.nextToken != lexAnalyzer.CASE_CODE) {
							error("SWITCH_STMT");
						}
						else {
							while(lexAnalyzer.nextToken == lexAnalyzer.CASE_CODE) { //can have many cases so put in while loop
								lexAnalyzer.lex();
								if(lexAnalyzer.nextToken != lexAnalyzer.INT_LIT && lexAnalyzer.nextToken != lexAnalyzer.FLOAT_LIT) {
									error("SWITCH_STMT");
								}
								else {
									lexAnalyzer.lex();
									if(lexAnalyzer.nextToken != lexAnalyzer.COLON) {
										error("SWITCH_STMT");
									}
									else {
										
										///
										lexAnalyzer.lex();
										statement();
										//make all statements called in statement call lex() at end(which should set nextToken = SEMICOLON)
										if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
											error("SWITCH_STMT");
										}
										else {
											while(lexAnalyzer.nextToken == lexAnalyzer.SEMICOLON) { //get statements until
												lexAnalyzer.lex();
												if(lexAnalyzer.nextToken != lexAnalyzer.BREAK_CODE) { //get statements until "break" is found
													statement();
													if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
														error("SWITCH_STMT");
													}
												}
											}
											if(lexAnalyzer.nextToken != lexAnalyzer.BREAK_CODE) {
												//System.out.println("HERE");
												error("SWITCH_STMT");
											}
											else {
												lexAnalyzer.lex();
												if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) {
													error("SWITCH_STMT");
												}
											}
										}
										///
										
										
										///
//										lexAnalyzer.lex();
//										statement();
//										if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) {
//											
//											error("SWITCH_STMT");
//										}
//										else {
//											lexAnalyzer.lex();
//											if(lexAnalyzer.nextToken != lexAnalyzer.BREAK_CODE) {
//												System.out.println("HERE");
//												error("SWITCH_STMT");
//											}
//											else {
//												lexAnalyzer.lex();
//												if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) {
//													error("SWITCH_STMT");
//												}
//											}
//										}
										///
										
									}
								}
								lexAnalyzer.lex();
							}
							if(lexAnalyzer.nextToken == lexAnalyzer.DEFAULT_CODE) {
								lexAnalyzer.lex();
								if(lexAnalyzer.nextToken != lexAnalyzer.COLON) {
									error("SWITCH_STMT");
								}
								else {
									lexAnalyzer.lex();
									statement();
									//make all statements called in statement call lex() at end(which should set nextToken = SEMICOLON)
									if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
										error("SWITCH_STMT");
									}
									else {
										while(lexAnalyzer.nextToken == lexAnalyzer.SEMICOLON) {
											lexAnalyzer.lex();
											if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_CURLY) { 
												statement();
												if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) { 
													error("SWITCH_STMT");
												}
											}
										}
									}
									
									
//									lexAnalyzer.lex();
//									statement();
//									if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) {
//										error("SWITCH_STMT");
//									}else {
//										
//									}
									
								}
							}
							if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_CURLY) {
								error("SWITCH_STMT");
							}
						}
					}
				}
			}
		}
		lexAnalyzer.lex();
		System.out.println("Exit <switch_statement>");
	}
	public static void For_or_Foreach_Stmt() {
		System.out.println("Enter <for_or_foreach_statement>(uncertain which one yet)");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_PAREN) {
			error("FOR_OR_FOREACH_STMT");
		} 
		else {
			lexAnalyzer.lex();
			if(lexAnalyzer.nextToken != lexAnalyzer.INT_CODE && lexAnalyzer.nextToken != lexAnalyzer.FLOAT_CODE &&
			   lexAnalyzer.nextToken != lexAnalyzer.STRING_CODE && lexAnalyzer.nextToken != lexAnalyzer.CHAR_CODE	) 
			{
				error("FOR_OR_FOREACH_STMT"); 
			} 
			else {
				lexAnalyzer.lex();
				if(lexAnalyzer.nextToken != lexAnalyzer.IDENT) {
					error("FOR_OR_FOREACH_STMT");
				} 
				else {
					lexAnalyzer.lex();
					//this is where this method branches based on if its a for loop or for-each loop
					if(lexAnalyzer.nextToken == lexAnalyzer.ASSIGN_OP) {
						For_Stmt();
					} 
					else if(lexAnalyzer.nextToken == lexAnalyzer.COLON) {
						ForEach_Stmt();
					}
					else {
						error("FOR_OR_FOREACH_STMT");
					}
				}
			}
		}
		System.out.println("Exit <for_or_foreach_statement>");
	}
	public static void For_Stmt() {
		System.out.println("Enter <for_statement>");
		//lexAnalyzer.lex(); //don't need this before calling expression.
		Expression();
		if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) {
			error("FOR_STMT");
		} 
		else {
			Expression();
			if(lexAnalyzer.nextToken != lexAnalyzer.SEMICOLON) {
				error("FOR_STMT");
			}
			else {
				
				lexAnalyzer.lex();
				if(lexAnalyzer.nextToken != lexAnalyzer.IDENT) {
					error("FOR_STMT");
				}
				else {
					lexAnalyzer.lex();
					if(lexAnalyzer.nextToken != lexAnalyzer.ASSIGN_OP) {
						error("FOR_STMT");
					}
					else {
						Expression();
						if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
							error("FOR_STMT");
						}
						else {
							block();
						}
					}
				}
				
			}
//			if(lexAnalyzer.nextToken != lexAnalyzer.IDENT) {
//				error("FOR_STMT");
//			}
//			else {
//				lexAnalyzer.lex();
//				if(lexAnalyzer.nextToken != lexAnalyzer.EQUALITY_OP && lexAnalyzer.nextToken != lexAnalyzer.LESS_THAN &&
//				lexAnalyzer.nextToken != lexAnalyzer.GREATER_THAN && lexAnalyzer.nextToken != lexAnalyzer.EXCLAMATION ) 
//				{
//					error("FOR_STMT");
//				}
//				else {
//					if(lexAnalyzer.nextToken == lexAnalyzer.EQUALITY_OP) {
//						//lexAnalyzer.lex();
//					}
//					else if(lexAnalyzer.nextToken == lexAnalyzer.LESS_THAN || lexAnalyzer.nextToken != lexAnalyzer.GREATER_THAN) {
//						lexAnalyzer.lex();
//						if(lexAnalyzer.nextToken == lexAnalyzer.ASSIGN_OP) {
//							lexAnalyzer.lex();
//						} 
//					}
//					else if(lexAnalyzer.nextToken == lexAnalyzer.EXCLAMATION) {
//						lexAnalyzer.lex();
//						if(lexAnalyzer.nextToken != lexAnalyzer.ASSIGN_OP) {
//							error("FOR_STMT");
//						}
//						lexAnalyzer.lex();
//					}
//					Expression();
//				}
//			}
		}
		lexAnalyzer.lex(); //should be semicolon
		System.out.println("Exit <for_statement>");
	}
	public static void ForEach_Stmt() {
		System.out.println("Enter <foreach_statement>");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.IDENT) { //the array identifier
			error("FOREACH_STMT");
		}
		else {
			lexAnalyzer.lex();
			if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
				error("FOREACH_STMT");
			}
			else {
				block();
			}
		}
		lexAnalyzer.lex();
		System.out.println("Exit <foreach_statement>");
	}
	public static void While_Stmt() {
		System.out.println("Enter <while_statement>");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_PAREN) {
			error("WHILE_STMT");
		}
		else {
			Expression();
			if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
				error("WHILE_STMT");
			}
			else {
				block();
			}
		}
		lexAnalyzer.lex();	
		System.out.println("Exit <while_statement>");
	}
	public static void DoWhile_Stmt() {
		System.out.println("Enter <dowhile_statement>");
		block();
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.WHILE_CODE) {
			error("DOWHILE_STMT");
		}
		else {
			lexAnalyzer.lex();
			if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_PAREN) {
				error("DOWHILE_STMT");
			}
			else {
				Expression();
				if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
					error("DOWHILE_STMT");
				}
			}
		}
		lexAnalyzer.lex();
		System.out.println("Exit <dowhile_statement>");
	}
	public static void If_Stmt() {
		System.out.println("Enter <if_statement>");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.LEFT_PAREN) {
			error("IF_STMT");
		}
		else {
			Expression();
			if(lexAnalyzer.nextToken != lexAnalyzer.RIGHT_PAREN) {
				error("IF_STMT");
			}
			else {
				block();
			}
		}
		lexAnalyzer.lex();	
		System.out.println("Exit <if_statement>");
	}
	
	public static void Assignment_Stmt() {
		System.out.println("Enter <assignment_statement>");
		
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken != lexAnalyzer.ASSIGN_OP) {
			error("ASSIGNMENT_STMT");
		}
		else {
			Expression();
		}
		
		System.out.println("Exit <assignment_statement>");
	}
	public static void Return_Stmt() {
		System.out.println("Enter <return_statement>");
		
		Expression(); //can accept a single identifier too so will only call Expression in this method
		
		System.out.println("Exit <return_statement>");
	}
	
	
	//rda for mathematical expressions. Taken from my solution to Homework 3 problem 2
	public static void Expression() {
		System.out.println("Enter <Expression>");
		
		Term();
		//while nextToken is + or -
		while(lexAnalyzer.nextToken == lexAnalyzer.ADD_OP || lexAnalyzer.nextToken == lexAnalyzer.SUB_OP ) {
			//lexAnalyzer.lex();
			Term();
		}
		
		System.out.println("Exit <Expression>");
	}
	
	public static void Term() {
		System.out.println("Enter <Term>");
		
		Factor();
		//while nextToken is * or / or %
		while(lexAnalyzer.nextToken == lexAnalyzer.MULT_OP || 
			  lexAnalyzer.nextToken == lexAnalyzer.DIV_OP || 
			  lexAnalyzer.nextToken == lexAnalyzer.MOD_OP ) 
		{
			
			//lexAnalyzer.lex();
			Factor();
		}
		
		System.out.println("Exit <Term>");
	}
	
	public static void Factor() {
		System.out.println("Enter <Factor>");
		lexAnalyzer.lex();
		if(lexAnalyzer.nextToken == lexAnalyzer.IDENT || 
		   lexAnalyzer.nextToken == lexAnalyzer.INT_LIT || 
		   lexAnalyzer.nextToken == lexAnalyzer.FLOAT_LIT) 
		{
			lexAnalyzer.lex();
		}
		else if(lexAnalyzer.nextToken == lexAnalyzer.LEFT_PAREN){
			//lexAnalyzer.lex();
			Expression();
			if(lexAnalyzer.nextToken == lexAnalyzer.RIGHT_PAREN) {
				lexAnalyzer.lex();
			}
			else {
				error("FACTOR");
			}
		}
		else {
			error("FACTOR");
		}
		
		System.out.println("Exit <Factor>");
	}
	
	
	public static void error(String methodName) {
		System.out.println("\nERROR, NOT VALID INPUT. ERROR OCCURRED IN: " + methodName + "()");
		System.exit(1);
	}
}




class LexicalAnalyzer {
	/* Global declarations */
	/* Variables */
	int charClass;
	char[] lexeme = new char[100];//100 size
	char nextChar;
	int lexLen;
	int token;
	int nextToken;
	FileInputStream fin;
	int i;



	/* Character classes */
	final int LETTER_UNDSCOR = 0;
	final int LETTER = 1;
	final int DIGIT_PER = 2;
	final int UNKNOWN = 99;

	/* Token codes */
	final int FLOAT_LIT = 9;
	final int INT_LIT = 10;
	final int IDENT = 11;
	final int ASSIGN_OP = 20;
	final int EQUALITY_OP = 8;
	final int ADD_OP = 21;
	final int SUB_OP = 22;
	final int MULT_OP = 23;
	final int DIV_OP = 24;
	final int MOD_OP = 7;
	final int LEFT_PAREN = 25;
	final int RIGHT_PAREN = 26;
	final int UNDERSCORE = 27;
	final int CANT_DEFINE = 28;
	final int SEMICOLON = 6;
	final int LEFT_CURLY = 5;
	final int RIGHT_CURLY = 4;
	final int COLON = 3;
	final int LESS_THAN = 2;
	final int GREATER_THAN = 1;
	final int EXCLAMATION = 0;
	
		//reserved words
	final int FOR_CODE = 30;
	final int IF_CODE = 31;
	final int ELSE_CODE = 32;
	final int WHILE_CODE = 33;
	final int DO_CODE = 34;
	final int INT_CODE = 35;
	final int FLOAT_CODE = 36;
	final int SWITCH_CODE = 37;
	//new reserved words for this assessment
	final int VOID_CODE = 38;
	final int MAIN_CODE = 39;
	final int RETURN_CODE = 40;
	final int CASE_CODE = 41;
	final int BREAK_CODE = 42;
	final int DEFAULT_CODE = 43;
	final int STRING_CODE = 44;
	final int CHAR_CODE = 45;
	
	
	
	final int EOF = 100;
	
	//regex for Integer
	final String IntegerSuffix = "([uU][lL]?|[uU](ll|LL)|[uU][iI]64|[lL][uU]?|(ll|LL)[uU]?|[iI]64)";
	final String DecimalConstant = "0|[1-9][0-9]*"+IntegerSuffix+"?";
	final String OctalConstant = "0[0-7]+"+IntegerSuffix+"?";
	final String HexadecimalConstant = "0[xX][0-9a-fA-f]+"+IntegerSuffix+"?";
	
	///was going to use to check if next character was special symbol and was going to make it so only special 
	//symbols follow a ident,float, or int, otherwise invalid token. but didn't end up doing that. 
	//so now it just generates new token for letters/numbers after without making error
	//for example 123.2abc will give float then identifier and not error
	//ArrayList<Character> symbols = new ArrayList<Character>(); 
	
	//use when printing out what the token is rather than just printing that integer value for the token
	HashMap<Integer, String> TokenMap = new HashMap<Integer, String>();
	//used to find token code of reserved word given a string equaling the reserved word
	HashMap<String, Integer> ReservedWordMap = new HashMap<String, Integer>();

    public LexicalAnalyzer(String fileName) {
		TokenMap.put(FLOAT_LIT, "FLOAT_LIT");
		TokenMap.put(INT_LIT, "INT_LIT");
		TokenMap.put(IDENT, "IDENT");
		TokenMap.put(ASSIGN_OP, "ASSIGN_OP");
		TokenMap.put(EQUALITY_OP, "EQUALITY_OP");
		TokenMap.put(ADD_OP, "ADD_OP");
		TokenMap.put(SUB_OP, "SUB_OP");
		TokenMap.put(MULT_OP, "MULT_OP");
		TokenMap.put(DIV_OP, "DIV_OP");
		TokenMap.put(MOD_OP, "MOD_OP");
		TokenMap.put(LEFT_PAREN, "LEFT_PAREN");
		TokenMap.put(RIGHT_PAREN, "RIGHT_PAREN");
		TokenMap.put(UNDERSCORE, "UNDERSCORE");
		TokenMap.put(CANT_DEFINE, "CANT_DEFINE");
		TokenMap.put(SEMICOLON, "SEMICOLON");
		TokenMap.put(COLON, "COLON");
		TokenMap.put(LEFT_CURLY, "LEFT_CURLY");
		TokenMap.put(RIGHT_CURLY, "RIGHT_CURLY");
		TokenMap.put(EOF, "EOF");
		//reserved words
		TokenMap.put(FOR_CODE, "FOR_CODE");
		TokenMap.put(IF_CODE, "IF_CODE");
		TokenMap.put(ELSE_CODE, "ELSE_CODE");
		TokenMap.put(WHILE_CODE, "WHILE_CODE");
		TokenMap.put(DO_CODE, "DO_CODE");
		TokenMap.put(INT_CODE, "INT_CODE");
		TokenMap.put(FLOAT_CODE, "FLOAT_CODE");
		TokenMap.put(SWITCH_CODE, "SWITCH_CODE");
		TokenMap.put(VOID_CODE, "VOID_CODE");
		TokenMap.put(MAIN_CODE, "MAIN_CODE");
		TokenMap.put(RETURN_CODE, "RETURN_CODE");
		TokenMap.put(CASE_CODE, "CASE_CODE");
		TokenMap.put(BREAK_CODE, "BREAK_CODE");
		TokenMap.put(DEFAULT_CODE, "DEFAULT_CODE");
		TokenMap.put(STRING_CODE, "STRING_CODE");
		TokenMap.put(CHAR_CODE, "CHAR_CODE");
		TokenMap.put(LESS_THAN, "LESS_THAN");
		TokenMap.put(GREATER_THAN, "GREATER_THAN");
		TokenMap.put(EXCLAMATION, "EXCLAMATION");
	
		
		ReservedWordMap.put("for", FOR_CODE);
		ReservedWordMap.put("if", IF_CODE);
		ReservedWordMap.put("else", ELSE_CODE);
		ReservedWordMap.put("while", WHILE_CODE);
		ReservedWordMap.put("do", DO_CODE);
		ReservedWordMap.put("int", INT_CODE);
		ReservedWordMap.put("float", FLOAT_CODE);
		ReservedWordMap.put("switch", SWITCH_CODE);
		ReservedWordMap.put("VOID", VOID_CODE);
		ReservedWordMap.put("MAIN", MAIN_CODE);
		ReservedWordMap.put("return", RETURN_CODE);
		ReservedWordMap.put("case", CASE_CODE);
		ReservedWordMap.put("break", BREAK_CODE);
		ReservedWordMap.put("default", DEFAULT_CODE);
		ReservedWordMap.put("String", STRING_CODE);
		ReservedWordMap.put("char", CHAR_CODE);
	
		
		try {
			//fin = new FileInputStream("TEST_Problem1.txt"); //txt file needs to be in outer workspace folder, not in src with Java file
			fin = new FileInputStream(fileName);
			//System.out.println("File found");
		} catch(FileNotFoundException exc){
			System.out.println("File not found");
			return;
		}
		
		
		getChar();
		lex();

		
	}
    void closeFile() {
    	try {
			fin.close();
		} catch(IOException exc){
			System.out.println("Error closing file.");
		}
    }

	/* lookup - a function to look up operators and
	parentheses and return the token */
	int lookup(char ch) {
		 switch (ch) {
			 case '(':
				 addChar();
				 nextToken = LEFT_PAREN;
				 break;
			 case ')':
				 addChar();
				 nextToken = RIGHT_PAREN;
				 break;
			 case '+':
				 addChar();
				 nextToken = ADD_OP;
				 break;
			 case '-':
				 addChar();
				 nextToken = SUB_OP;
				 break;
			 case '*':
				 addChar();
				 nextToken = MULT_OP;
				 break;
			 case '/':
				 addChar();
				 nextToken = DIV_OP;
				 break;
			 case '%':
				 addChar();
				 nextToken = MOD_OP;
				 break;
			 case '=':
				 addChar();
				 nextToken = ASSIGN_OP;
				 break;
			 case ';':
				 addChar();
				 nextToken = SEMICOLON;
				 break;
			 case ':':
				 addChar();
				 nextToken = COLON;
				 break;
			 case '{':
				 addChar();
				 nextToken = LEFT_CURLY;
				 break;
			 case '}':
				 addChar();
				 nextToken = RIGHT_CURLY;
				 break;
			 case '<':
				 addChar();
				 nextToken = LESS_THAN;
				 break;
			 case '>':
				 addChar();
				 nextToken = GREATER_THAN;
				 break;
			 case '!':
				 addChar();
				 nextToken = EXCLAMATION;
				 break;
//			 case '_':
//				 addChar();
//				 nextToken = UNDERSCORE;
//				 break;
			 default:
				 addChar();
				 nextToken = EOF;
				 break;
		 }
		 return nextToken;
	}
	

	
	
	/******************************************************/
	/* addChar - a function to add nextChar to lexeme */
	void addChar() {
		if (lexLen <= 98) {
			//System.out.println((int)nextChar);
			//if((int)nextChar < 128) { 
				lexeme[lexLen++] = nextChar;
			//}
			lexeme[lexLen] = '\0';
		} else {
			System.out.println("Error - lexeme is too long \n");
		}

	}

	
	/******************************************************/
	/* getChar - a function to get the next character of
	input and determine its character class */
	void getChar(){
//		 if ((nextChar = getc(in_fp)) != EOF) {
//			 if (isalpha(nextChar))
//			 	charClass = LETTER;
//			 else if (isdigit(nextChar))
//			 	charClass = DIGIT;
//			 else
//			 	charClass = UNKNOWN;
//		 } else
//		 	charClass = EOF;
		
		
		//read character
		try {
			i = fin.read();
		} catch(IOException exc) {
			System.out.println(exc);
			//return;
		}
		
		nextChar = (char)i;
		//if((int)nextChar > 127) { //high nextChar values were actually just character casting -1 (or EOF)
			//System.out.println("i: " + i);
		//}
		if (i != -1) {
			 if (Character.isLetter(nextChar) | nextChar == '_') {
			 	charClass = LETTER_UNDSCOR;
			 }
			 else if (Character.isDigit(nextChar) | nextChar == '.') { //or == '.'
			 	charClass = DIGIT_PER;
			 } 
			 else {
			 	charClass = UNKNOWN;
			 }
		 } else {
		 	charClass = EOF;
		 }
	}


	/******************************************************/
	/* getNonBlank - a function to call getChar until it
	returns a non-whitespace character */
	void getNonBlank() {
		//while (isspace(nextChar))
		while (Character.isWhitespace(nextChar)) {
			getChar();
		}
	}

	/******************************************************/
	/* lex - a simple lexical analyzer for arithmetic
	expressions */
	int lex() {
		 String lexemeStr; 
		 lexLen = 0;
		 getNonBlank();
		 switch (charClass) {
			/* Identifiers */
			 case LETTER_UNDSCOR:
				 //any line with comment "this line" means code relates to making identifier work with underscore
				 boolean firstIsUnderscore = false; //this line
				 if(nextChar == '_') firstIsUnderscore = true; //this line
				 
				 addChar();
				 getChar();
				 
				 boolean anyAdded = false; //this line
				 while (charClass == LETTER_UNDSCOR || (charClass == DIGIT_PER & nextChar != '.') ) {
					 anyAdded = true; //this line
					 addChar();
					 getChar();
				 }
				 if(firstIsUnderscore && !anyAdded) { //this line
					 nextToken = UNDERSCORE; //this line
				 } else {
					 nextToken = IDENT;
				 }
				 break;
			/* Integer literals */
			 case DIGIT_PER:
				 boolean firstIsPeriod = false; //this line
				 if(nextChar == '.') firstIsPeriod = true; //this line
				 boolean canBeFloating = true; //used after going through integer part
				 addChar();
				 getChar();
				 //System.out.println(firstIsPeriod);
				 if(firstIsPeriod) {
					 /*while(matches regex pattern for float){
					  * 	addChar()
					  * 	getChar()
					  * }
					  */
				 } else {
					 
					 
					 lexemeStr = new String(lexeme).split("\0")[0];
					 //System.out.println("lex str: "+lexemeStr);
					 boolean decConst = Pattern.matches(DecimalConstant, lexemeStr);
					 boolean octConst = Pattern.matches(OctalConstant, lexemeStr);
					 boolean hexConst = Pattern.matches(HexadecimalConstant, lexemeStr);
					 
					 //needed these because while loop terminates when it shouldnt without
					 //for example hexadecimal 0x2a. 0x2a matches the regex, but because while loop goes 
					 //character at a time, it stops at 0x since 0x doesn't match regex. So I need to check
					 //if next character appended will match regex, for example 0x2 would match and overcome this issue
					 
					 boolean decConstNext = false;
					 boolean octConstNext = false;
					 boolean hexConstNext = false;
					 
					//I noticed a really high unicode number was being added near end of file because '?' was being added to lexeme. 
					//turns out to be character casting -1(or EOF) and adding that to lexeme 
					//made sure below that charClass EOF cant enter while loop
					 
					 //added that Pattern.matches... in while loop conditions to prevent anything like a period from breaking the potential floating point number apart
					 while( ( (decConst | octConst | hexConst) | (decConstNext | octConstNext | hexConstNext) ) & (charClass != EOF) & Pattern.matches("[uUlLiIxX0-9a-fA-F]", String.valueOf(nextChar)) ) {
						 //System.out.println("boolean: "+ (charClass != EOF));
						 addChar();
						 getChar();
//			
						 lexemeStr = new String(lexeme).split("\0")[0];
						 //System.out.println("lexStr: " + lexemeStr);
						 decConst = Pattern.matches(DecimalConstant, lexemeStr);
						 decConstNext = Pattern.matches(DecimalConstant, lexemeStr+String.valueOf(nextChar));
						 octConst = Pattern.matches(OctalConstant, lexemeStr);
						 octConstNext = Pattern.matches(OctalConstant, lexemeStr+String.valueOf(nextChar));
						 hexConst = Pattern.matches(HexadecimalConstant, lexemeStr);
						 hexConstNext = Pattern.matches(HexadecimalConstant, lexemeStr+String.valueOf(nextChar));
						 //if octal, hexadecimal, or has u,U,l,L,i,I cant be a floating point
						 if(octConst | octConstNext | hexConst | hexConstNext | Pattern.matches("[uUlLiI]", String.valueOf(nextChar)) ) {
							 canBeFloating = false;
						 }
						 //because stops at i6 since i6 doesnt match i64
						 if(lexemeStr.charAt(lexemeStr.length()-1) == 'i') {
							 if(nextChar == '6') {
								 hexConstNext = true;
							 }
						 }
						 
//						 System.out.println("dec: " + decConst);
//						 System.out.println("oct: " + octConst);
//						 System.out.println("hex: " + hexConst);
//						 System.out.println("decN: " + decConstNext);
//						 System.out.println("octN: " + octConstNext);
//						 System.out.println("hexN: " + hexConstNext);
//						 System.out.println(charClass);
					 }
					 if( Pattern.matches(".*[iI]6", lexemeStr) ) {nextToken = CANT_DEFINE;}
					 else {nextToken = INT_LIT;}
				 }
				 //check if number after period, if not number skip everything else with boolean and at end set nextToken to PERIOD
				 //if first char entering this case wasn't a period, keep checking entire lexeme against Integer regex in while loop adding a new character each time like it is now
				 //when it hits period, e, or E stop while loop and do new while loop and other logic checking against regex for floating point
				 if(canBeFloating | firstIsPeriod) {
					 boolean eStarts = false;
					 boolean isNotFloating = false; //if next character is not ., e, E then not floating
					 if(firstIsPeriod | nextChar == '.' ) {
						 if(!firstIsPeriod) {
							 //if firstIsPeriod then addChar and getChar already done once above
							 addChar();
							 getChar();
						 }
						 while (charClass == DIGIT_PER & nextChar != '.') {
							 addChar();
							 getChar();
						 }
						 if(nextChar == 'e' | nextChar == 'E') {
							 eStarts = true;
						 } else if (Pattern.matches("[fFlL]", String.valueOf(nextChar))) {
							 addChar();
							 getChar();
						 }
					 } else if(nextChar == 'e' | nextChar == 'E') {
						 eStarts = true;
					 } else {
						 isNotFloating = true;
					 }
					 
					 if(eStarts) {
						 addChar();
						 getChar();
						 if(nextChar == '-') {
							 addChar();
							 getChar();
						 }
						 while (charClass == DIGIT_PER & nextChar != '.') {
							 addChar();
							 getChar();
						 }
						 if( Pattern.matches("[fFlL]", String.valueOf(nextChar)) ) {
							 addChar();
							 getChar();
						 }
						 //System.out.println("e starts");
					 }
					 
					 if(!isNotFloating) {
						 nextToken = FLOAT_LIT;
					 }
				 }
				 
				 
				 
				 //at end determine if nextToken is Integer, floating point or just a period based on boolean values or something

				
			 	break;
			/* Parentheses and operators */
			 case UNKNOWN:
				 lookup(nextChar);
				 getChar();
				 break;
				/* EOF */
			case EOF:
				 nextToken = EOF;
				 lexeme[0] = 'E';
				 lexeme[1] = 'O';
				 lexeme[2] = 'F';
				 lexeme[3] = '\0';
			 	 break;
		 } /* End of switch */
		 /*printf("Next token is: %d, Next lexeme is %s\n",
		 nextToken, lexeme);*/
//		 if(charClass != EOF & charClass != UNKNOWN) {
//			 while(!symbols.contains(nextChar) & nextChar != ' ' ) {
//				 addChar();
//				 getChar();
//				 nextToken = CANT_DEFINE;
//			 }
//		 }
		 
		 //checks if there were 2 = in a row meaning the lexeme should be == instead of = and token should be EQUALITY_OP instead of ASSIGN_OP
		 if(nextToken == ASSIGN_OP) {
			 if(nextChar == '=') {
				 nextToken = EQUALITY_OP;
				 addChar();
				 getChar();
			 }
		 }
		 
		 lexemeStr = new String(lexeme).split("\0")[0]; //was printing the null character and non null characters after before changing to this line. Only store the part until first null char
		 
		 if(nextToken == IDENT) { //all reserved words would initially be identified as an identifier 
			 if(ReservedWordMap.containsKey(lexemeStr)) { //if identifier is actually a reserved word
				 nextToken = ReservedWordMap.get(lexemeStr); //set nextToken to the token code for that reserved word
			 }
		 }
		 
		 System.out.println("Next token is: {token value: "+nextToken+", token name: "+ TokenMap.get(nextToken)+"}, Next lexeme is "+ lexemeStr);
		 return nextToken;
	} /* End of function lex */
}

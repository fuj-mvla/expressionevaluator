// TODO: Auto-generated Javadoc
public class ExpressionEvaluator {
	// These are the required error strings for that MUST be returned on the appropriate error 
	// for the JUnit tests to pass
	private static final String PAREN_ERROR = "Paren Error: ";
	private static final String OP_ERROR = "Op Error: ";
	private static final String DATA_ERROR = "Data Error: ";
	private static final String DIV0_ERROR = "Div0 Error: ";
	private static final int prec0 = 0;
	private static final int prec1 = 1;
	private static final int prec2 = 2;
	private static final int prec3 = 3;
	// The placeholders for the two stacks
	private GenericStack<Double> dataStack;
	private GenericStack<String>  operStack;
	private GenericStack k;
	/**
	 * Convert to tokens. Takes a string and splits it into tokens that
	 * are either operators or data. This is where you should convert 
	 * implicit multiplication to explict multiplication. It is also a candidate
	 * for recognizing negative numbers, and then including that negative sign
	 * as part of the appropriate data token.
	 *
	 * @param str the str
	 * @return the string[]
	 */
	private String[] convertToTokens(String str) {
		str = padString(str);
		String[] split = str.split("\\s+");
		split = errorCheck(split);
		return split;
	}
	
	/**
	 * Pads the operators with strings, and checks for imp multipulcation and negative numbers
	 *
	 * @param in the in
	 * @return the string
	 */
	private String padString(String in) {
		in = in.replaceAll("([\\+\\-\\/(\\)\\*])"," $1 ");
		in = in.replaceAll("^\\s+(.*)","$1");
		in = in.replaceAll("^-\\s+(\\d+)", "-$1");
		in = in.replaceAll("([+-/*])\\s+-\\s+(\\d+)", "$1 -$2");
		in = in.replaceAll("\\(\\s+-\\s+(\\d+)", "( -$1");
		in = in.replaceAll("(\\d+)\\s+\\(", "$1 * (");
		in = in.replaceAll("\\)\\s+\\(",  ") * (");
		in = in.replaceAll("\\)\\s+(\\d+)", ") * $1");
		in = in.replaceAll("-\\s+\\(([\\d-+/*\\s]*)\\)", "( -1 * ( $1 ) )");
		in = in.replaceAll("^-\\s+", "-1 * ");
		return in;
	}
	
	/**
	 * Negative numbers.
	 *
	 * @param String array
	 * @return the string[]
	 */
	
	
	/**
	 * General error checking method that checks for data, op and paren error. 
	 * It checks the errors by calling seperate methods for each error check
	 * Called by the convert to tokens method
	 *
	 * @param in the in
	 * @return the string[]
	 */
	private String[] errorCheck(String[] in ) {
		in = errorCheckParen(in);
		if (in[0].equals(PAREN_ERROR)) {
			return in;
		}
		in = errorCheckData(in);
		if(in[0].equals(DATA_ERROR)) {
			return in;
		}
		in = errorCheckOp(in);
		if(in[0].equals(OP_ERROR)) {
			return in;
		}
		
		return in;
	}
	
	
	
	
	
	/**
	 * Parenthesis error check, returns the original array if no error, returns the array with the message if there is error
	 *
	 * @param String array
	 * @return String array with the error message if it has one
	 */
	private String[] errorCheckParen(String[] in) {
		int inc = 0;
		String currtok = "";
		String 	prevtok = "";
		for (int i = 0;i <in.length;i++) {
			currtok = in[i];
			if (currtok.equals(")")& prevtok.equals("(")) {
				String[] x = {PAREN_ERROR};
				return x;
			}
			if (in[i].equals(")")) {
				inc--;
			}
			else if (in[i].equals("(")) {
				inc++;
			}
			if (inc<0) {
				String[] x = {PAREN_ERROR};
				return x;
			}
			prevtok = in[i];
		}
		if (inc!=0) {
			String[] x = {PAREN_ERROR};
			return x;
		}
		return in;
	}
	
	/**
	 * Error checking for operators. Same as error check paren but for operators.
	 *
	 * @param in the in
	 * @return the string[]
	 */
	private String[] errorCheckOp(String[]in) {
		String prevtok = "";
		String currtok = "";
		if (!in[0].equals("(")&!in[0].equals("-")&&!identifyTokenType(in[0]).equals("Integer")&&!identifyTokenType(in[0]).equals("Double")) {
			String[] er = {OP_ERROR};
			return er;
		}
		if (!in[in.length-1].equals(")") && !identifyTokenType(in[in.length-1]).equals("Integer")&&!identifyTokenType(in[in.length-1]).equals("Double")) {
			String[] er = {OP_ERROR};
			return er;
		}
		for (int i =0;i < in.length;i++) {
			currtok = in[i];
			if (identifyTokenType(currtok).equals("Operation")&&identifyTokenType(prevtok).equals("Operation")||identifyTokenType(currtok).equals("Parenthesis")&&identifyTokenType(prevtok).equals("Operation"))	 {
				String[] er = {OP_ERROR};
				return er;
			}
			prevtok = in[i];
		}
		return in;
	}
	
	/**
	 * Error checking for wrong data entered or invalid formats.
	 *
	 * @param in the in
	 * @return the string[]
	 */
	private String[] errorCheckData(String[]in) {
		String prevtok = "";
		String currtok = "";
		for (int i = 0;i< in.length;i++) {
			currtok = in[i];
			if (identifyTokenType(currtok).equals("Integer") && identifyTokenType(prevtok).equals("Integer")){
				String[] er = {DATA_ERROR};
				return er;
			}
			else if(identifyTokenType(currtok).equals("Double") && identifyTokenType(prevtok).equals("Double")) {
				String[] er = {DATA_ERROR};
				return er;
			}
			else if (identifyTokenType(in[i]).equals("Error")) {
				String[] er = {DATA_ERROR};
				return er;
			}
			prevtok = in[i];
		}
		
		return in;
	}
	
	
	
	/**
	 * Evaluate expression. This is it, the big Kahuna....
	 * It is going to be called by the GUI (or the JUnit tester),
	 * and:
	 * a) convert the string to tokens
	 * b) if conversion successful, perform static error checking
	 *    - Paren Errors
	 *    - Op Errors 
	 *    - Data Errors
	 * c) if static error checking is successful:
	 *    - evaluate the expression, catching any runtime errors.
	 *      For the purpose of this project, the only runtime errors are 
	 *      divide-by-0 errors.
	 *
	 * @param str the str
	 * @return the string
	 */
	protected String evaluateExpression(String str) {
        dataStack =  new GenericStack<Double>();
		operStack =  new GenericStack<String>();
		String[] data = convertToTokens(str);
		if (data[0].equals(PAREN_ERROR)|| data[0].equals(OP_ERROR)|| data[0].equals(DATA_ERROR)) {
			return data[0];
		}
		for(int i = 0;i < data.length;i++) {
			String token = data[i];
			if (identifyTokenType(token).equals("Integer")||identifyTokenType(token).equals("Double")) {
				dataStack.push(Double.parseDouble(token));
			}
			else {
				if (operStack.empty() || isHigherPrecedence(token,operStack.peek())) {
					operStack.push(token);
				}
				else {;
					EvaluateTOS(token,data);
					if (data[0].equals(DIV0_ERROR)) {
						return data[0];
					}
				}
			}
		}
		while(!operStack.empty()) {
			double d1 = dataStack.pop();
			double d2 = dataStack.pop();
			String op = operStack.pop();
			if (divZeroErr(d1,d2,op)) {
				return DIV0_ERROR;
			}
			dataStack.push(performCalc(d1,d2,op));
		}
		return str + "=" + dataStack.pop() + "";
	}
	
	
	/**
	 * Evaluate TOS, runs until the precedence of the top of stack for op is less than the current token
	 * When called, it performs the operations and handles parenthesis until the stack is empty or higherprecdence is no longer true
	 *
	 * @param String data
	 */
	private void EvaluateTOS(String data,String[] x) {
		double d1;
		double d2;
		String op;
		while( !isHigherPrecedence(data,operStack.peek())){
			if (!data.equals(")")) {
				d1 = dataStack.pop();
				d2 = dataStack.pop();
				op = operStack.pop();
				if (divZeroErr(d1,d2,op)) {
					x[0] = DIV0_ERROR;
					return;
				}
				dataStack.push(performCalc(d1,d2,op));
			}
			else {
				while(!operStack.peek().equals("(")) {
					d1 = dataStack.pop();
					d2 = dataStack.pop();
					op = operStack.pop();
					if (divZeroErr(d1,d2,op)) {
						x[0] = DIV0_ERROR;
						return;
					}
					dataStack.push(performCalc(d1,d2,op));
				}
				operStack.pop();
				return;
			}
			if (operStack.empty()) {
				break;
			}
		}
		operStack.push(data);
	}
	
	private boolean divZeroErr(double d2,double d1,String op) {
		if (op.equals("/")&&d2==0) {
			return true;
		}
		return false;
	}
	/**
	 * performs the calculations given the 2 numbers and the operator.
	 * cheecks to see what operator was passed in, and does the calculations accordingly.
	 *
	 * @param double d2
	 * @param double d1
	 * @param String op
	 * @return the result
	 */
	private double performCalc(double d2,double d1,String op) {
		if (op.equals("+")) {
			return d1+d2;
		}
		else if(op.equals("-")) {
			return d1-d2;
		}
		else if(op.equals("*")) {
			return d1*d2;
		}
		else {
			return d1/d2;
		}
		
	}
	
	/**
	 * Checks the precedence between the operators of d1 and d2. return true if d1 is higher precedence, false if not
	 * Always returns true if any op is (, false if )
	 *
	 * @param String d1
	 * @param String d2
	 * @return true, if is higher precedence
	 */
	private boolean isHigherPrecedence(String d1, String d2) {
		int prec = 0;
		int precd2 = 0;
			if (d1.equals("+")||d1.equals("-")) {
				prec = prec1;
			}
			else if(d1.equals("/")||d1.equals("*")) {
				prec = prec2;
			}
			else if(d1.equals("(")) {
				return true;
			}
			else {
				return false;
			}
			if (d2.equals("+")||d2.equals("-")) {
				precd2 = prec1;
			}
			else if(d2.equals("/")||d2.equals("*")) {
				precd2 = prec2;
			}
			else if(d2.equals("(")) {
				return true;
			}
			return (prec>precd2);
		}
		
	
	
	/**
	 * Identifys the token type for int,double, paren, op, or error.
	 *
	 * @param String x
	 * @return type of token
	 */
	private String identifyTokenType(String x) { 
	            if(x.matches("^-*[0-9]+$")) {
	                x = "Integer";
	            }
	            else if(x.matches("^-*[0-9]+[\\.]+[0-9]+$")) {
	               x = "Double";
	            }
	            else if(x.matches("^[\\Q)\\E]+$")) {
	            	x = "Parenthesis";
	            }
	            else if(x.matches("^[\\Q+-*/\\E]+$")) {
	                x = "Operation";

	            }
	            else if (x.matches("^[\\Q(\\E]+$")){
	                x = "Right paren";
	            }
	            else {
	            	x = "Error";
	            }
	        return x;
	        
	}
}


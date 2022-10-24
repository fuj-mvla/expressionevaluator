// TODO: Auto-generated Javadoc
public class ExpressionEvaluator {
	// These are the required error strings for that MUST be returned on the appropriate error 
	// for the JUnit tests to pass
	private static final String PAREN_ERROR = "Paren Error: ";
	private static final String OP_ERROR = "Op Error: ";
	private static final String DATA_ERROR = "Data Error: ";
	private static final String DIV0_ERROR = "Div0 Error: ";

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
		return null;
	}
	
	private String padString(String in) {
		in = in.replaceAll("([\\+\\-\\/(\\)\\*])"," $1 ");
		in = in.replaceAll("^\\s+(.*)","$1");
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
		String original = str;
		String[] data = convertToTokens(str);
		int i = 0;
		
		while(true) {
			String token = data[i];
			if (identifyTokenType(token).equals("Integer")||identifyTokenType(token).equals("Double")) {
				dataStack.push(Double.parseDouble(token));
			}
			else {
				
			}
			break;
		}
		
		return str + "=" + dataStack.pop() + "";
	}
	private void EvaluateTOS() {
		
	}
	
	private boolean isHigherPrecedence() {
		
		return false;
	}
	private String identifyTokenType(String x) { 
	            if(x.matches("^[0-9]+$")) {
	                x = "Integer";
	            }
	            else if(x.matches("^[0-9]+[\\.]+[0-9]+$")) {
	               x = "Double";
	            }
	            else if(x.matches("^[\\Q+-*/()\\E]+$")) {
	                x = "Operation";

	            }
	            else {
	                x = "Error";
	            }
	        
	        return x;
	}
}

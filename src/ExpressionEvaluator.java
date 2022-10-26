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
		return split;
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
		for(int i = 0;i < data.length;i++) {
			String token = data[i];
			String type = identifyTokenType(token);
			if (type.equals("Integer")||type.equals("Double")) {
				dataStack.push(Double.parseDouble(token));
			}
			else {
				if (operStack.empty() || isHigherPrecedence(token,operStack.peek())) {
					operStack.push(token);
				}
				else {
					EvaluateTOS(token);
				}
			}
		}
		double val;
		while(!operStack.empty()) {
			
			val= performCalc(dataStack.pop(),dataStack.pop(),operStack.pop());
			dataStack.push(val);
		}
		
		return str + "=" + dataStack.pop() + "";
	}
	private void EvaluateTOS(String data) {
		double val;
		while( !isHigherPrecedence(data,operStack.peek())){
			
			if (!data.equals(")")) {
				System.out.println(dataStack.toString());
				System.out.println(operStack.toString());
				val= performCalc(dataStack.pop(),dataStack.pop(),operStack.pop());
				dataStack.push(val);
			}
			else {
				System.out.println("here");
				while(!operStack.peek().equals("(")) {
					val= performCalc(dataStack.pop(),dataStack.pop(),operStack.pop());
					dataStack.push(val);
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

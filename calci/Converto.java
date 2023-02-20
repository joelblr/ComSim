package calci;

import errors.*;
import java.util.*;


final class Func {

	private static long gcd(long a, long b) {
		for (long i = (a < b) ? a : b;	i > 1; i--)
			if (a % i == 0 && b % i == 0)
				return i;
		/* when one of them is zero */
		return Math.max(a, b);
	}

	public static double nCr(double n, double r) {

		if ( Integer.parseInt( (n +"").split(".")[1]) != 0 || 
				Integer.parseInt( (r +"").split(".")[1]) != 0)
			throw new NumberException("<nCr : INTEGER not DECIMAL!>");

		if (n < r)
			throw new NumberException("<nCr : N >= R !>");
		if (r < 0)
			throw new NumberException("<nCr : N, R >= 0 !>");


		long N = (long) n, R = (long) r;
		long c = 1, k = 1;

		R = Math.min(R, N-R);

		if (R != 0) {
			while (R > 0) {

				c *= N;  k *= R;

				long m = gcd(c, k);

				c /= m;  k /= m;
				N--;	 R--;

			}
		}

		return c;
	}

	public static double nPr(double n, double r) {

		if ( Integer.parseInt( (n +"").split(".")[1]) != 0 || 
				Integer.parseInt( (r +"").split(".")[1]) != 0)
			throw new NumberException("<nPr : INTEGER not DECIMAL!>");

		if (n < r)
			throw new NumberException("<nPr : N >= R !>");
		if (r < 0)
			throw new NumberException("<nPr : N, R >= 0 !>");


		long N = (long) n, R = (long) r;
		long p = 1;

		for (long i = N; i > N-R; i--)
			p *= i;

		return p;
	}

	public static double fact(double x) {
		return nPr(x, x);
	}

}



public class Converto {

	protected static LinkedList <String> postfix;
	protected static LinkedHashSet<String> unique;
	protected static HashSet<String> uniOperators;
	protected static HashSet<String> biOperators;

	static {
		postfix = new LinkedList<String>();
		unique = new LinkedHashSet<String>();
		uniOperators = new HashSet<String>();
		biOperators = new HashSet<String>();

		uniOperators.addAll(Arrays.asList("~", "neg", "ceil", "floor", "abs",
				"pow", "sqrt", "cbrt", "exp", "log", "ln", "fact", "ncr", "npr",
				"sin", "cos", "tan", "csc", "sec", "cot",
				"asin", "acos", "atan", "acsc", "asec", "acot",
				"sinh", "cosh", "tanh", "csch", "sech", "coth"
				)); //round, min, max, avg

		biOperators.addAll(Arrays.asList(
				"$", "*", "/", "%", "+", "-",
				"<<", ">>", "&", "^", "|"
				));

	}


	public static LinkedList <String> getPostfix() {
		return postfix;
	}

	public static void setPostfix(LinkedList <String> pf) {
		postfix = pf;
	}


	/**TODO use factorial as {@code !}*/
	private static int priority(String ch) {

		if (ch == "(" || ch == "|" || ch == "^" || ch == "&" || ch == ">>" || ch == "<<")
			return (ch == "(") ? 0 : (ch == "|") ? 1 : (ch == "^") ? 2 : (ch == "&") ? 3 : 4 ;
		else if (ch == "+" || ch == "-")
			return 5;
		else if (ch == "$" || ch == "~" || ch == "*" || ch == "/" || ch == "%")
			return (ch == "$") ? 7 : (ch == "~") ? 9 : 6;

		return 9;
	}


	public void findPostfix(String infix, char type) {

		postfix.clear();
		/* Boolean Exp */
		if (type == 'B')
			unique.clear();

		Stack <String> stac = new Stack <String>();
		char[] itr = infix.toCharArray();

		for (int i = 0; i < infix.length(); i++) {

			char ch1 = itr[i];
			char ch2 = itr[(i+1)%infix.length()];

			if (Character.isWhitespace(ch1)) {
				continue;

			/* extracting a num (123) or a func name (sin). */
			}else if (Character.isLetterOrDigit(ch1)) {

				postfix.add("");
				do {	//Number/Name
					postfix.addLast(postfix.pollLast() + ch1);
					ch1 = infix.charAt(i+1);
				}while (Character.isLetterOrDigit(ch1) && ++i != 0);


				/* To check if last element is num */
				try {
					Double.parseDouble(postfix.peekLast());

				/* To check if atleast it's a valid func name. */
				}catch (NumberFormatException e) {
					if (type == 'B')
						unique.add(postfix.peekLast().toUpperCase());

					else if (!(uniOperators.contains(postfix.peekLast().toLowerCase()) ||
							biOperators.contains(postfix.peekLast().toLowerCase()) ))
						throw new InvalidFunctionException();
				}


			/* For Opening Bracket */
			}else if (ch1 == '(') {
				stac.push(ch1+"");

			/* For Closing Bracket */
			}else if (ch1 == ')') {

				while (stac.peek() != "(")
					postfix.add( stac.pop());
				stac.pop();

			/* For Operators */
			}else {

				String op = ch1 + "";
				/* For >> << Operators */
				if (ch1 == '<' || ch1 == '>') {
					if (ch2 != ch1)
						throw new InvalidOperatorException();
					op += op;
				}

				while (priority(stac.peek()) >= priority(op))
					postfix.add( stac.pop());

				stac.push(op);

			}
		}

	}

}

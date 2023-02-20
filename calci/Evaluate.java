package calci;

import errors.*;
import java.util.*;


public class Evaluate extends Converto {


	private static double compute(double x, String op, double y) {

		double ans = 0;

		/* Math Operations */
		if (op == "ceil" || op == "floor" || op == "abs")
			ans = (op == "ceil") ? Math.ceil(x) : (op == "floor") ? Math.floor(x) : Math.abs(x);
		else if (op == "pow" || op == "sqrt" || op == "cbrt")
			ans = (op == "pow") ? Math.pow(x, y) : (op == "sqrt") ? Math.sqrt(x) : Math.cbrt(x);
		else if (op == "exp" || op == "log" || op == "ln")
			ans = (op == "exp") ? Math.exp(x) : (op == "log") ? Math.log10(x) : Math.log(x);
		else if (op == "ncr" || op == "npr" || op == "fact")
			ans = (op == "ncr") ? Func.nCr(x, y) : (op == "npr") ? Func.nPr(x, y) : Func.fact(x);


		if (CalciMain.getAngleMode() != "rad")
			x = Math.toRadians(x);

		/* Trigonometry Functions: Basic, Inverse, Hyperbolic */
		if (op == "sin" || op == "cos" || op == "tan")
			ans = (op == "sin") ? Math.sin(x) : (op == "cos") ? Math.cos(x) : Math.tan(x);
		else if (op == "asin" || op == "acos" || op == "atan")
			ans = (op == "asin") ? Math.asin(x) : (op == "acos") ? Math.acos(x) : Math.atan(x);
		else if (op == "sinh" || op == "cosh" || op == "tanh")
			ans = (op == "sinh") ? Math.sinh(x) : (op == "cosh") ? Math.cosh(x) : Math.tanh(x);

		else if (op == "csc" || op == "sec" || op == "cot")
			ans = (op == "csc") ? 1/Math.sin(x) : (op == "sec") ? 1/Math.cos(x) : 1/Math.tan(x);
		else if (op == "acsc" || op == "asec" || op == "acot")
			ans = (op == "acsc") ? Math.asin(1/x) : (op == "asec") ? Math.acos(1/x) : Math.atan(1/x);
		else if (op == "csch" || op == "sech" || op == "coth")
			ans = (op == "csch") ? 1/Math.sinh(x) : (op == "sech") ? 1/Math.cosh(x) : 1/Math.tanh(x);

		/* Basic Arithmetic Operations */
		if (op.equals("$") || op.equals("*") || op.equals("+") || op.equals("-")) {
			ans = (op == "$") ? Math.pow(x, y) : (op == "*") ? x*y : (op == "+") ? x+y : x-y ;

		}else if (op.equals("/") || op.equals("%")) {
			if (y == 0)
				throw new NumberException("<DIVISION BY ZERO!>");
			ans = (op == "/") ? x/y : x%y ;
		}

		/* Bitwise Operations */
		if ( Integer.parseInt( (x +"").split(".")[1]) != 0 || 
				Integer.parseInt( (y +"").split(".")[1]) != 0)
			throw new NumberException("<Bitwise : INTEGER not DECIMAL!>");

		int a = (int)x, b = (int)y;
		if (op.equals(">>") || op.equals("<<")) {
			if (y < 0)
				throw new NumberException("<L/R Shift : Shift >= 0 !>");
			ans = (op == ">>") ? a>>b : a<<b;

		}else if (op == "&" || op == "|" || op == "^" || op == "~" || op == "neg")
			ans = (op == "&") ? a&b : (op == "|") ? a|b : (op == "^") ? a^b : ~a;


		/* NaN, +-Infinity Errors */
		if (Double.isInfinite(ans) || Double.isNaN(ans))
			throw new NumberException("<BAD NUMBERS!>");

		return ans;
	}


	public double evalPostfix() {

		Stack <Double> stac = new Stack <>();

		for (String ch : postfix) {

			/* For Unary Operators */
			if (uniOperators.contains(ch)) {
				double y = stac.pop();
				stac.push( compute(y, ch, 0));

			/* For Binary Operators */
			}else if (biOperators.contains(ch)) {
				double y = stac.pop();
				double x = stac.pop();
				stac.push( compute(x, ch, y));

			/* For Operands */
			}else
				stac.push( Double.valueOf(ch));

		}

		return stac.pop();

	}


	public static double evalPostfix(int[] BoolInput) {

		Stack <Double> stac = new Stack <>();

		String[] itr = postfix.toArray(new String[0]);
		for (int i = 0; i < postfix.size(); i++) {
			String ch = itr[i];
		
			/* For Unary Operators */
			if (uniOperators.contains(ch)) {
				double y = stac.pop();
				stac.push( compute(y, ch, 0));

			/* For Binary Operators */
			}else if (biOperators.contains(ch)) {
				double y = stac.pop();
				double x = stac.pop();
				stac.push( compute(x, ch, y));

			/* For Boolean Operands */
			}else if ( Character.isLetter(ch.charAt(0)) ) {
				char ch1 = ch.charAt(0);

				if ( Character.isLowerCase(ch1) ) {
					ch1 = Character.toUpperCase(ch1);
					stac.push( (double)(BoolInput[i] ^ 1) );

				}else
					stac.push( (double)BoolInput[i]);

			/* For Constants */
			}else
				stac.push( Double.valueOf(ch));

		}

		return stac.pop();

	}
}

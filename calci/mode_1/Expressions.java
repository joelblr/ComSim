package calci.mode_1;

import calci.Convertor;
import design.*;
import java.util.*;

public class Expressions {


	private static void getExpression(String type) {

		List<String> stdin = 
				Design.printBox(
						"ENTER " + type + " EXPRESSION: $",
						" ".repeat(55)
					);

		String infix = "(" + stdin.get(0) + ")";
		
		if (type == "ARITHMETIC") {
			Convertor.findPostfix(infix, 'A');

		}else {
			Convertor.findPostfix(infix, 'B');
			N = Convertor.getUnique().size();
			BoolInput = new int[N];
		}
	}


	//Arithmetic Expression
	public static void arithmeticRun() {
		getExpression("ARITHMETIC");
		getAnswer();
	}

	private static void getAnswer() {

		String ans = Double.toString(Math.round(Convertor.evalPostfix(new int[0])*1000.0)/1000.0);
		Design.printBox(String.format("ANSWER:  %s T", ans));

	}


	//Boolean Expression
	private static int N;
	private static int BoolInput[];

	public static void booleanRun() {
		getExpression("BOOLEAN");
		printTruthTable();

	}private static void nextInput() {
		for (int idx = N-1; idx > -1; idx--) {
			BoolInput[idx] ^= 1;
			if (BoolInput[idx] == 1)
				return;
		}

	}private static void printTruthTable() {

		String row = "";
		Design.printTop(4*N+3);

		for (char key : Convertor.getUnique().keySet()) {
			String newKey = Colorify.bold + Colorify.fg_red + key + Colorify.reset;
			row += " "+newKey+" │";

		}row += Colorify.bold + Colorify.fg_green + " f " + Colorify.reset;

		Design.printJoinRow("", row);
		row = "───┼".repeat(N) + "───";
		Design.printJoinRow("", row);

		String newKey;
		for (int k = 0; k < 1<<N; k++) {
			row = "";
			for (int val : BoolInput) {
				newKey = Colorify.bold + Colorify.fg_yellow + val + Colorify.reset;
				row += " "+newKey+" │";

			}
			newKey = Colorify.bold + Colorify.fg_green + 
					(int)Convertor.evalPostfix(BoolInput) + Colorify.reset;
			row += " " + newKey + " ";

			Design.printJoinRow("", row);
			nextInput();

		}Design.printBase(4*N + 3);

		return;
	}


}

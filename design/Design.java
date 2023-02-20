package design;

import java.util.*;

/**
 * @author Joel A
 * */
public class Design extends ConsoleCmd {


	final static int factor;
	public final static String sym = " λ ";
	final static String prefix, mid;
	final static String leftR, rightR;
	final static String leftI, rightI;
	final static String leftT, rightT;
	final static String leftL, rightL;



	static {
		prefix = " ".repeat(8);
		factor = 3 + 0 + 0 + 3;
		String fg_box = fg_blue;

		mid = bold + fg_box + "─" + reset;
		leftR = prefix + bold + fg_box + "┌" + reset;	rightR = bold + fg_box + "┐" + reset;
		leftT = prefix + bold + fg_box + "├" + reset;	rightT = bold + fg_box + "┤" + reset;
		leftI = prefix + bold + fg_box + "│   "  + reset;	rightI = bold + fg_box + "   │" + reset;
		leftL = prefix + bold + fg_box + "└" + reset;	rightL = bold + fg_box + "┘" + reset;
//		mid = "─";
//		leftR = prefix + "┌";	rightR = "┐";
//		leftT = prefix + "├";	rightT = "┤";
//		leftI = prefix + "│   ";	rightI = "   │";
//		leftL = prefix + "└";	rightL = "┘";
	}



	private static int findMaxWidth(String ... str) {
		int max_size = 0;
		for (String s : str) {
			int len = s.split(sym)[0].length();
//			int len = s.split(sym)[0].stripTrailing().length();
			max_size = Math.max(len, max_size);

		}return max_size;
	}


	/**╭─ This is a Tag Msg ─────────────────╮

	 * Prints the Top-Part of the Message Box
	 * @param   width  -> width of the Message Box
	 */
	public static void printTag(int width, String ... tag) {

		if (width == 0)
			width = findMaxWidth(tag[0]);
		int len = tag[0].length();
		tag[0] = colourMsg(tag);
		System.out.println(leftR+mid+ " "+tag[0]+" " +mid.repeat(width+factor-len-3)+rightR);

	}/**╭──────────────────────────────────╮
	 * Prints the Top-Part of the Message Box
	 * @param   width  -> width of the Message Box
	 */
	public static void printTop(int width) {
		System.out.println(leftR+mid.repeat(width + factor)+rightR);

	}/**│  Some Message to display  │
	 * Prints the Message-Part of the Message Box
	 * @param   width    -> width of the Message Box
	 * @param   message  -> message of the Message Box
	 */
	public static void printFrame(int width, String colFormat, String ... message) {

		if (width == 0)
			width = findMaxWidth(message);

		for (String msg : message) {
			int spaceLen = Math.abs(width-msg.length());
//			String colored = colorMsg(type, msg);
			msg = colourMsg(new String[] {msg, colFormat});
			System.out.println(leftI+msg+" ".repeat(spaceLen)+rightI);
		}

	}/**╰───────────────────────────────────╯
	 * Prints the Bottom-Part of the Message Box
	 * @param   width    -> width of the Message Box
	 */
	public static void printBase(int width) {
		System.out.println(leftL+mid.repeat(width+factor)+rightL);

	}/**├───────────────────────────────────┤
	 * Prints the Joint-Part b/w 2 segments of the Message Box
	 * @param   width    -> width of the Message Box
	 */
	public static void printJoint(int width) {
		System.out.println(leftT+mid.repeat(width+factor)+rightT);

	}


	private static List<String> getStdin(List<Integer[]> inputs, int lines) {

		int i = lines;
		List<String> stdin = new LinkedList<String>();
		System.out.print("\033["+(i-inputs.get(0)[0])+"A");

		i = inputs.get(0)[0] -1;
		for (Integer[] arr : inputs) {

			if ((arr[0]-i) != 0) {
				System.out.print("\033["+(arr[0]-i)+"B");
			}
			System.out.print("\033["+arr[1]+"C");
			System.out.print(fg_green + bold);
			stdin.add(scan.nextLine());
			System.out.print(reset);

			i = arr[0]+1;
		}

		System.out.print("\n".repeat(lines - i -1));

		return stdin;
	}
	/**
	 * @Info Prints the Input Message in a Box Format
	 * @param   msg   ->  Messages
	 * @usage 	give a set of Strings as parameters.<p>
	 * 			* If a line-break is needed after a line then
	 * give an empty String "" as the next String.<p>
	 * 			* If some input has to be taken at some line
	 * then at the end of that line, add " $" (space dollar).
	 * @example Design.printBox(<p>
						"Line1", "",<p>
						"Line2",<p>
						"Line3", "",<p>
						"Line4 $",<p>
						"Line5 $",<p>
						"Line6", "",<p>
						"Enter Choice: $"<p>
					);
		@todo : <p>
			use "msg T" : for title coloring<p>
			use "msg E" : for error coloring<p>
			use "msg $" : for input coloring<p>
			use "msg M" : for info coloring.....
		
	 */
	public static List<String> printBox(String ... msg) {

		List<Integer[]> cursorEof = new LinkedList<Integer[]>();
		int i,  width = findMaxWidth(msg); //based on split factor
//		printTop(width);
		int lines = 0;
		for (i = 0; i < msg.length; i++) {

			lines++;
			if (msg[i] == "") {
				printJoint(width);	continue;

			}else if (msg[i] == "<>") {
				printTop(width);	continue;
			}

			String[] codes = msg[i].split(sym);

			if (codes[0].stripTrailing().endsWith("#")) {
				if (i == 0) {
//					lines++;
					System.out.println();
				}
				codes[0] = codes[0].stripTrailing();
				codes[0] = codes[0].substring(0, codes[0].length()-2);
				printTag(width, codes);

			}else if (codes[0].stripTrailing().endsWith("$")) {
				int len1 = codes[0].stripTrailing().length()-2;
				codes[0] = codes[0].substring(0, len1);
//				String field = msg[i].substring(0, msg[i].length()-1);
				printFrame(width, codes[1], codes[0]);

				int len = prefix.length() + factor/2+1 + codes[0].length();// + 1;
				cursorEof.add(new Integer[] {i, len});

			}else {
				printFrame(width, codes[1], codes[0]);

			}

		}printBase(width); lines += 2;

		List<String> stdin = new LinkedList<String>();
		if (! cursorEof.isEmpty())
			stdin = getStdin(cursorEof, lines);

		return stdin;
	}

	public static void printJoinRow(String type, String row) {
		printFrame(0, type, row);
	}


	public static void main(String[] args) {
		//TODO  "msg >> B : G, T"  => B: colour of Key, G: colour of Value, T: Tag (M: msg, ...)
		/**
		 * "Jesus Loves You >> B = G, T"
		 * */

		Design.printBox(
				"Program Simulator #"+ sym +"Y", "",
				" *1*  Casio fx-707"+ sym +"C",
				" *2*  Celonis"+ sym +"R",
				" *3*  BESCOM"+ sym +"R",
				" *4*  Cash Ware"+ sym +"R",
				" *0*  Close Program Simulator"+ sym +"C", "",
				"Enter Choice:  $"+ sym +"P"
			);

		//		Design.printBox(
//				"Line1 Y T", "",
//				"Line2 R",
//				"Line3 C", "",
//				"Line4 B",
//				"Line5 G",
//				"Line6 P", "",
//				"Line5 b",
//				"Line6 W", "",
//				"Enter Choice: $"
//			);
//		Design.printBox(
//				"╭─╮",
//				"│J│",
//				"│E│",
//				"│S│",
//				"│U│",
//				"│S│",
//				"╰─╯",
//				"┌─┐",
//				"│J│",
//				"│E│",
//				"├S┤",
//				"│U│",
//				"├S┤",
//				"└─┘"
//			);
	}

}



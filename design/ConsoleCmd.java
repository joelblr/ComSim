package design;

import java.io.IOException;


public class ConsoleCmd extends Colorify {

	private static int k;

	/**
	 * @Info Moves the Cursor to the given Position in the Console of the Terminal
	 * @param Names : {@code Self Explanatory}

	 */
	public final static void cursorGoto(int up, int down, int fwd, int bwd) {
		System.out.print("\033["+up+"A"+"\033["+down+"B");
		System.out.print("\033["+fwd+"C"+"\033["+bwd+"D");
	}
	/**
	 * @Info Clears the Console of the Terminal
	 */
	public final static void clearScreen() {

		try {//Windows
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

		}catch(IOException | InterruptedException e) {//Linux
			System.out.print("\033[H\033[2J");
		}
	}
	/**
	 * @Info Displays the Error Message
	 * @param msg : Appropriate Error message to print
	 * */
	public final static void showErrorMessage(String msg) {
//		Design.printBox(new String(Character.toChars(0x2757))+" "+msg);
		Design.printBox(msg + " <.> R");
		Design.errorFlag = false;
//		Design.throwError(msg);
	}
	/**
	 * @Info Displays the Continue-Process Message
	 * */
	public final static void continueProcess() {
		Design.printBox("<>",	"PRESS <ENTER> KEY TO CONTINUE...  $"+ Design.sym +"G");
	}
	/**
	 * @Info Displays the waiting Process
	 * @param time  number of milli seconds
	 * */
	public final static void loadingProcess(int time) {

		String[] colors =	{fg_black, fg_blue, fg_cyan, fg_green,
							fg_purple, fg_red, fg_white, fg_yellow};

		String a = "|/-\\";
		long start = System.currentTimeMillis();
		try {
			int i = 0;
			Design.printBox("<>",
					"Loading your Choice     "+ Design.sym +"W");
			System.out.print("\033[2A\033["+(8+4+22)+"C");
			while (true) {
				
				System.out.print( colors[k++%8] + bold + a.charAt(i) + reset  + " ");
				Thread.sleep(150);
				System.out.print("\b\b");
				long now = System.currentTimeMillis();
				if (now - start >= time) break;
				i = (i+1) % 4;
			}

		}catch(Exception e) {
			//e.printStackTrace();
		}
		finally {
			System.out.print("\033[1B");
			for (int i = 0; i < 3; i++) {
				System.out.print("\033[2K");
				System.out.print("\033[1A");
			}
		}
		System.out.println();
	}


}

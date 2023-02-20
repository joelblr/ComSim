package calci;

import design.Design;
import java.util.*;

import calci.mode_1.*;
import calci.mode_2.*;
import calci.mode_4.*;
import calci.mode_5.*;



public class CalciMain {

	private static String angle;

	static {
		angle = "rad";	// deg or rad
	}

	public static void setAngleMode(String mode) {
		angle = mode;

	}public static String getAngleMode() {
		return angle;
	}

	private static void showSetup() {

		while (true) {
			Design.clearScreen();
			if (Design.errorFlag)
				Design.showErrorMessage(Design.errorMsg);
	
			List<String> stdin = 
					Design.printBox(
						"Casio fx-991+ Setup Y", "",
						" *1*  Degree C",
						" *2*  Radian C",
						" *0*  Close Setup C", "",
						"Enter Choice: $"
					);
	
			try {
	
				Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
				Design.loadingProcess(1500);
	
				switch (Design.IntegerChoice) {
	
					case 1 :
						setAngleMode("deg");
						return;
	
					case 2 :
						setAngleMode("rad");
						return;
	
					case 0 :
						return;
	
					default :
						throw new NumberFormatException("<INVALID CHOICE!>");
				}
	
			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUT!>";
				else
					Design.errorMsg = e.getMessage();
	
				Design.errorFlag = true;
	
			}
		}
	}

	public static void showMenu() {

		String sym = Design.sym;
		List<String> stdin = 
				Design.printBox(
					"Casio fx-991+ Modes #"+ sym +"Y", "",
					" *1*  Scientific"+ sym +"C",	//TODO
					" *2*  Programming"+ sym +"C",	//TODO
					" *3*  Complex Mode"+ sym +"R",	//TODO
					" *4*  Base-N Mode"+ sym +"C",	//TODO : modify mode2 and mode4
					" *5*  Equation Mode"+ sym +"C",	//XXX : ....
					" *6*  Matrix Mode"+ sym +"R",	//TODO
					" *7*  Table Mode"+ sym +"R",	//TODO
					" *8*  Vector Mode"+ sym +"R",	//TODO
					" *9*  Setup Mode"+ sym +"C",	//XXX ......
					" *0*  Close Casio fx-707"+ sym +"C", "",
					"Enter Choice:  $"+ sym +"C"
				);

		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
	}


	public static void run() {
		CalciRun();
	}


	public static void CalciRun() {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag) {
					Design.showErrorMessage(Design.errorMsg);
					Design.errorFlag = false;
				}
				showMenu();
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 1 :
						CompMain.run();
						break;
	
					case 2 :
						ProgMain.run();
						break;

//					case 3 :
//						Expressions.booleanRun();
//						break;

					case 4 :
						NBaseMain.run();
						break;

					case 5 :
						EqnMain.run();
						break;

					case 9 :
						showSetup();
						break;

					case 0 :
						return;

					default :
						throw new NumberFormatException("<INVALID CHOICE!>");

				}//Design.continueProcess();

			//To catch Errors: Implement Verify.java inside Convertor.java
			}catch (ArithmeticException e) {
				Design.loadingProcess(600);
				Design.errorMsg = "<DIVISION BY ZERO!>";	Design.errorFlag = true;

			}catch (NumberFormatException e) {
				Design.loadingProcess(600);
				if (!e.getMessage().startsWith("<"))
					Design.errorMsg = "<INVALID INPUTs!>";
				else
					Design.errorMsg = e.getMessage();

				Design.errorFlag = true;

			}catch (InputMismatchException e) {
				Design.loadingProcess(600);
				Design.errorMsg = "<NOT A QUADRATIC!>";	Design.errorFlag = true;

			}catch(ArrayIndexOutOfBoundsException e) {
				Design.errorMsg = "<NOT ENOUGH INPUTS!>";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}catch(Exception e) {
				Design.errorMsg = "<HEY!>";	Design.errorFlag = true;
				e.printStackTrace();
				Design.scan.nextLine();

			}

		}

	}
}




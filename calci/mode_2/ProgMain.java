package calci.mode_2;

import java.util.*;
import design.Design;


/**
 * @info Mode-1 : Computation
 * */
public class ProgMain {


	public static void showMenu() {

		Design.printBox(
			"</> Programming Mode Y", "",
			"Solve Boolean Expressions C",
			"*AC*  Switch Mode C", ""
		);

	}


	public static void run() {
		Mode2Run();
	}


	public static void Mode2Run() {

		while (Design.StringChoice != "AC") {

//			try {

				Design.clearScreen();
				if (Design.errorFlag)
					Design.showErrorMessage(Design.errorMsg);

				showMenu();
				List<String> stdin = 
						Design.printBox(
								"Boolean Expression # <.> Y",
								" $" + " ".repeat(50) + " <.> G"
							);

				Design.StringChoice = stdin.get(0);
				Design.printBox(
						"Answer # <.> Y",
						"x".repeat(50) + " <.> G"
					);

//				Design.cursorGoto(0, 0, 0, 0);

//				switch (Design.IntegerChoice) {
//
//					case 1 :
//						Expressions.booleanRun();
//						break;
//	
//					case 2 :
//						Expressions.arithmeticRun();
//						break;
//
//					case 0 :
//						return;
//
//					default :
////						Design.errorMsg = "<INVALID CHOICE!>";  Design.errorFlag = true;
//						throw new NumberFormatException("<INVALID CHOICE!>");
//
//				}Design.continueProcess();
//
//			//To catch Errors: Implement Verify.java inside Convertor.java
//			}catch (ArithmeticException e) {
//				Design.loadingProcess(600);
//				Design.errorMsg = "<DIVISION BY ZERO!>";	Design.errorFlag = true;
//
//			}catch (NumberFormatException e) {
//				Design.loadingProcess(600);
//				Design.errorMsg = "<INVALID INPUT!>";	Design.errorFlag = true;
//
//			}catch (InputMismatchException e) {
//				Design.loadingProcess(600);
//				Design.errorMsg = "<NOT A QUADRATIC!>";	Design.errorFlag = true;
//
//			}catch(Exception e) {
//				Design.errorMsg = "<HEY!>";	Design.errorFlag = true;
//				e.printStackTrace();
//				Design.scan.nextLine();
//
//			}

		}

	}
}
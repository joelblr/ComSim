
import design.*;
import calci.CalciMain;
import java.util.*;

public class SuperMain {


	public static void showMenu() {

		String sym = Design.sym;
		List<String> stdin = 
				Design.printBox(
					"Program Simulator #"+ sym +"Y", "",
					" *1*  Casio fx-707"+ sym +"C",
					" *2*  Celonis"+ sym +"R",
					" *3*  BESCOM"+ sym +"R",
					" *4*  Cash Ware"+ sym +"R",
					" *0*  Close Program Simulator"+ sym +"C", "",
					"Enter Choice:  $"+ sym +"P"
				);

		Design.IntegerChoice = Integer.parseInt(stdin.get(0).trim());
	}


	public static void run() {
		return;
	}


	public final static void main(String[] args) {

		while (true) {

			try {

				Design.clearScreen();
				if (Design.errorFlag)
					Design.showErrorMessage(Design.errorMsg);

				showMenu();
				Design.loadingProcess(1500);

				switch (Design.IntegerChoice) {

					case 1 :
						CalciMain.run();
						break;

//					case 2 :
//						EmployeeMain.run();
//						break;
//
//					case 3 :
//						BescomMain.run();
//						break;
//
//					case 4 :
//						BankMain.run();
//						break;


					case 0 :
						Design.scan.close();
						Design.clearScreen();
						System.exit(0);

					default :
						Design.errorMsg = "<INVALID CHOICE!>";	Design.errorFlag = true;
				}

			}catch (NumberFormatException e) {
				System.out.println();
				Design.loadingProcess(600);
				e.printStackTrace();
				Design.errorMsg = "<INVALID INPUT!>";	Design.errorFlag = true;

			}catch (Exception e) {}
		}

	}
}


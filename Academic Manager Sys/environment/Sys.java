package environment;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Sys {

	private Scanner keyboard;
	private boolean execution;
	
	private void println(Object ob){
		System.out.println(ob);
	}
	
	private int readNumberLikeString() throws NumberFormatException {
		String typedOperation = keyboard.nextLine();
		int aux = Integer.parseInt(typedOperation);
		return aux;
	}
	
	private String readString() throws NoSuchElementException {
		String typedString = "";
		typedString = keyboard.nextLine();
		return typedString;
	}
	
	private void shutdownModule(){
		String typedStrings = "";
		println("\n#########    Shutdown     ##########\n");
		println("Do you wanna shutdown the system? Y/N");
		try{
			typedStrings = readString();
			boolean validAnswer1, validAnswer2;
			validAnswer1 = (typedStrings.compareToIgnoreCase("y") == 0);
			validAnswer2 = (typedStrings.compareToIgnoreCase("n") == 0);
			if( validAnswer1 ||  validAnswer2){
				
				if(validAnswer1){
					execution = false;
					println("System will shutdown soon... See u!");
				}
				else{
					println("System will continue to running, don't worry!");
				}
			}
			else{
				println("Invalid answer! Nothing will be done...");
			}
		}
		catch(NoSuchElementException e){
			println(e.getStackTrace());
		}
		finally{
			println("\n####################################\n");
		}
	}
	
	private void menuSubInputHandle(int n){
		
		if(n == 1){
			println("login module call here");
		}

		else if(n == 2){
			println("undefined module");
		}

		else if(n == 3){
			println("undefined module");
		}

		else if(n == 4){
			println("undefined module");
		}

		else if(n == 5){
			println("undefined module");
		}

		else if(n == 6){
			println("undefined module");
		}

		else if(n == 7){
			shutdownModule();
		}

		else{
			println("Invalid input! Just integers numbers in the range [1, 7] are valid.\n");
		}
	}
	
	private void menuInputHandler(){
		try{
			menuSubInputHandle(this.readNumberLikeString());
		}
		catch(NumberFormatException e){
			println("Invalid input! Provider numbers only.\n");
		}
	}
	
	private void menuDisplay(){
		println("###########\tMenu\t############\n");
		println("Type the co-related number to access an operation!");
		println("(1) - Login");
		println("(2) - ");
		println("(3) - ");
		println("(4) - ");
		println("(5) - ");
		println("(6) - Logout");
		println("(7) - Shutdown");
	}
	
	public void mainMenu(){
		while(execution){
			menuDisplay();
			menuInputHandler();
		}
	}
	
	
	public Sys() {
		this.keyboard = new Scanner(System.in);
		this.execution = true;
	}

	public static void main(String[] args) {
		Sys operator = new Sys();
		operator.mainMenu();
	}

}

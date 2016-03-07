package environment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import environment.users.*;
import shapes.*;

public class DashBoardSubSystem extends Sys{
	
	private static int readNumberLikeString(Scanner inheritKeyboard) throws NumberFormatException{
		String typedOperation = inheritKeyboard.nextLine();
		int aux = Integer.parseInt(typedOperation);
		return aux;
	}

	private static String readString(Scanner inheritKeyboard) throws NoSuchElementException{
		String typedString = "";
		typedString = inheritKeyboard.nextLine();
		return typedString;
	}
	
	private static void admSubInputHandler(int n, Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		if(n == 1){
			AllocateSubSystem.allocationModule(inheritKeyboard, dB1, dB2, dB3, logged);
		}
		else if(n == 2){
			EditSubSystem.mainModule(inheritKeyboard, logged, dB1, dB2, dB3);
		}
		else if(n == 3){
			DeallocateSubSystem.deallocationModule(inheritKeyboard, dB1, dB2, dB3, logged);
		}
		else if(n == 4){
			//cancel call, do nothing.
		}
		else{
			println("Operation Aborted! Just integers numbers in the range [1, 4] are valid.");
		}
	}
	
	private static void admInputHandler(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		try{
			admSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, logged, dB1, dB2, dB3);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid Input! Type numbers only!");
			admInputHandler(inheritKeyboard, logged, dB1, dB2, dB3);
		}
	}
	
	private static void admModule(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		initiateTask("Administrator Routines");
		println("Type the correspondent number to perform a routine");
		println("(1) - Register Routines");
		println("(2) - Editing Routines");
		println("(3) - Unregister Routines");
		println("(4) - Cancel");
		admInputHandler(inheritKeyboard, logged, dB1, dB2, dB3);
		terminateTask();
	}
	
	private static void helperSubAnyRestrict(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		try{
			anyUserSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, logged, dB1, dB2, dB3);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid Input! Type numbers only!");
			anyUserInputHandler(inheritKeyboard, logged, dB1, dB2, dB3);
		}
	}
	
	private static void helperRestricTeacherRoutine(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		
	}
	
	private static void helperRestrictStudentsRoutine(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		println("Type correspondent number to perform a sub-routine");
		println("(1) Register New Issue");
		println("(2) Subscribed to a Project");
		helperSubAnyRestrict(inheritKeyboard, logged, dB1, dB2, dB3);
	}
	
	private static void anyUserSubInputHandler(int n, Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		if(n == 1){
			if(logged instanceof Teacher){
				println("undefined call");
			}
			else{
				println("undefined call");
			}
		}
		else if(n == 2){
			println("undefined call");
		}
		else if(n == 3){
			println("undefined call");
		}
		else if (n == 4){
			println("undefined call");
		}
		else{
			println("Operation Aborted! Just integers numbers in the range [1, 4] are valid.");
		}
	}
	
	private static void anyUserInputHandler(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		try{
			anyUserSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, logged, dB1, dB2, dB3);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid Input! Type numbers only!");
			anyUserInputHandler(inheritKeyboard, logged, dB1, dB2, dB3);
		}
	}
	
	private static void anyUserModule(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		initiateTask(logged.getName()+" Routines");
		println("Type the correspondent number to perform a routine");
		println("(1) - Register Routines");
		println("(2) - Editing Routines");
		println("(3) - Unregister Routines");
		anyUserInputHandler(inheritKeyboard, logged, dB1, dB2, dB3);
		terminateTask();
		
	}
	
	public static void mainModule(Scanner inheritKeyboard, User logged, List<User> database1, List<Project> database2, List<Issue> database3){
		if(logged == null){
			println("You must do login first!");
		}
		else{
			if(logged instanceof Administrator){
				admModule(inheritKeyboard, logged, database1, database2, database3);
			}
			else{
				anyUserModule(inheritKeyboard, logged, database1, database2, database3);
			}
		}
	}
}

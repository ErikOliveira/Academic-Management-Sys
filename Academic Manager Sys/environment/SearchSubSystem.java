package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import shapes.*;

public class SearchSubSystem extends Sys{
	
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

	private static void helperSearchCollaborator(Scanner inheritKeyboard, List<User> list){
		String typedUserIdentity = "";
		println("Type the collaborator name such as it was registered...");
		try{
			typedUserIdentity = readString(inheritKeyboard);
			if(typedUserIdentity.isEmpty()){
				println("Operation aborted! It's not possible look to an user without name");
			}
			else{
				println("------ Looking for all collaborator with name: " + typedUserIdentity + " ------");
				ArrayList<User> searched = Handtools.locateUsersByAlias(list, typedUserIdentity);
				
				if(searched.isEmpty()){
					println("\tThere's no match! :(");
				}
				else{
					for(int i = 0; i < searched.size(); ++i){
						println("\t----- Basic info -----");
						println(Handtools.summaryUserInfo(searched.get(i)));
						println("\t----------------------");
					}
				}
				println("---------------------------------------------------------");
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know");
		}
	}
	
	public static void searchSubInputHandler(int n, Scanner inheritKeyboard, List<User> dB1, List<Project> dB2){
		if(n == 1){
			helperSearchCollaborator(inheritKeyboard, dB1);
		}
		
		else if(n == 2){
			println("undefinedCall");
		}
		
		else if(n == 3){
			//cancel call, do nothing!
		}
		
		else{
			println("Invalid input! Just integers numbers in the range [1, 3] are valid.\n Operation Aborted!");
		}
		
	}
	
	private static void searchInputHandler(Scanner inheritKeyboard, List<User> dB1, List<Project> dB2){
		try{
			searchSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, dB1, dB2);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e,"Invalid input! Provider numbers only.\n");
		}
	}
	
	public static final void mainModule(Scanner inheritkeyboard, List<User> database1, List<Project> database2){
		println("\n#########\tSearch\t##########\n");
		println("What you are looking for?");
		println("(1) - Collaborator");
		println("(2) - Project");
		println("(3) - back");
		searchInputHandler(inheritkeyboard, database1, database2);
		println("\n####################################\n");
	}
}

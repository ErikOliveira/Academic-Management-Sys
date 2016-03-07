package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import environment.users.Administrator;
import shapes.*;

public class EditSubSystem extends Sys{
	public static final int maximumAttempts = 3;
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
	
	private static void helperAdmEditUser(Scanner inheritKeyboard, List<User> list){
		String typedUserIdentity = "";
		User target = null;
		println("Type your unique ID number...\nIf you don't remember it don't type anything, the hit Enter key");

		try{
			typedUserIdentity = readString(inheritKeyboard);
			if(typedUserIdentity.isEmpty()){
				println("Operation Aborted! It could not be performed withou a unique ID, Find it out and try again.");
			}
			else {
				target = (User) Handtools.locateByUID(list, typedUserIdentity);
				
				if(target == null){
					println("Operation Aborted! The typed unique ID not match with a valid user. Check your type and try again");
				}
				
				else{
					helperEditUserName(inheritKeyboard, target, list);
					helperEditUserEmail(inheritKeyboard, target, list);
				}
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know");
		}
		finally{
			terminateModule();
		}
	}
	
	private static void helperEditUserName(Scanner inheritKeyboard, User logged,List<User> list/*, List<Project> list2, List<Issue> list3*/){
		String typedName = "";
		int attempts = 0;
		
		try{
			while(typedName.isEmpty() & (attempts < maximumAttempts)){
				println("Type a new name");
				typedName = readString(inheritKeyboard);
				if(typedName.isEmpty()){
					println("An empty name is not allowed! Try again.\nRemaing Attempts: " + Integer.toString(maximumAttempts - attempts));
				}
				else{
					User target = new User(logged.getUniqueID(),typedName,logged.getEmail(),"000000");
					String confirmation = "";
					boolean validAnswers[] = {false, false};
					while(confirmation.isEmpty()){
						println("Do you really wanna rename it users? Y/N");
						println("\t\t\tBefore:\n"+Handtools.summaryUserInfo(logged));
						println("\t\t\tAfter:\n"+Handtools.summaryUserInfo(target));
						confirmation = readString(inheritKeyboard);
						validAnswers[0] = confirmation.compareToIgnoreCase("y") == 0;
						validAnswers[1] = confirmation.compareTo("n") == 0;
						if(validAnswers[0] || validAnswers[1]){
							if(validAnswers[0]){
								list.get(Integer.parseInt(logged.getUniqueID())).setName(typedName);
								/*ArrayList<Project> projects = new ArrayList<Project>();
								ArrayList<Issue> issues = new ArrayList<Issue>();

								projects = Handtools.locateProjectWithItParticipant(list2, logged);
								for(int i = 0; i < projects.size(); ++i){
									projects.get(i).unSubscribed(logged);
								}
								

								issues = Handtools.locateIssuePerAuthor(list3, logged);
								for(int i = 0; i < issues.size(); ++i){
									list3.get(i).s
								}

								list1.add(Integer.parseInt(typedUserUID), null);
								list1.remove(Integer.parseInt(typedUserUID)+1);*/
								println("Changed!");
							}
							else if(validAnswers[1]){
								println("Canceling...");
							}
							else{
								println("Invalid input! Just type 'y' to confirm or 'n' to cancel");
								confirmation = "";
							}
						}
					}
				}
				++attempts;
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Only Type numbers!");
		}
		finally{
			terminateTask();
		}
	}
	
	private static void helperEditUserEmail(Scanner inheritKeyboard, User logged, List<User> list){
		String typedEmail = "";
		int attempts = 0;
		
		try{
			while((typedEmail.isEmpty() || !typedEmail.contains("@")) & (attempts < maximumAttempts)){
				println("Type a new E-mail");
				typedEmail = readString(inheritKeyboard);
				if(typedEmail.isEmpty()){
					println("An empty E-mail alias or one without '@' is not allowed! Try again.\nRemaing Attempts: " + Integer.toString(maximumAttempts - attempts));
				}
				else{
					User target = new User(logged.getUniqueID(),logged.getName(),typedEmail,"000000");
					String confirmation = "";
					boolean validAnswers[] = {false, false};
					while(confirmation.isEmpty()){
						println("Do you really wanna change it users' E-mail? Y/N");
						println("\t\t\tBefore:\n"+Handtools.summaryUserInfo(logged));
						println("\t\t\tAfter:\n"+Handtools.summaryUserInfo(target));
						confirmation = readString(inheritKeyboard);
						validAnswers[0] = confirmation.compareToIgnoreCase("y") == 0;
						validAnswers[1] = confirmation.compareTo("n") == 0;
						if(validAnswers[0] || validAnswers[1]){
							if(validAnswers[0]){
								list.get(Integer.parseInt(logged.getUniqueID())).setEmail(typedEmail);
								/*ArrayList<Project> projects = new ArrayList<Project>();
								ArrayList<Issue> issues = new ArrayList<Issue>();

								projects = Handtools.locateProjectWithItParticipant(list2, logged);
								for(int i = 0; i < projects.size(); ++i){
									projects.get(i).unSubscribed(logged);
								}
								

								issues = Handtools.locateIssuePerAuthor(list3, logged);
								for(int i = 0; i < issues.size(); ++i){
									list3.get(i).s
								}

								list1.add(Integer.parseInt(typedUserUID), null);
								list1.remove(Integer.parseInt(typedUserUID)+1);*/
								println("Changed!");
							}
							else if(validAnswers[1]){
								println("Canceling...");
							}
							else{
								println("Invalid input! Just type 'y' to confirm or 'n' to cancel");
								confirmation = "";
							}
						}
					}
				}
				++attempts;
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Only Type numbers!");
		}
		finally{
			terminateTask();
		}
	}
	
	private static void helperEditUserPassword(Scanner inheritKeyboard, User logged, List<User> list){
		String typedPassword[] = {"", "", ""};
		boolean validator[] = {false, false, false};
		int attempts = 0;
		try{
			while(!((validator[0] & validator[1] & validator[2]) || (attempts == maximumAttempts))){
				if(!logged.auth(typedPassword[2])){
					if(attempts != 0)
						println("Your password not match");
					
					println("Type your current password");
					typedPassword[2] = readString(inheritKeyboard);
				}
				
				if(!validator[2]){
					if(attempts != 0){
						if(typedPassword[0].length() < 6){
							println("Too short! Password must be at least 6 chars long!");
						}
						if(typedPassword[0].length() > 10){
							println("Too long! Password must be up to 10 chars long!");
						}
						if(!typedPassword[0].equals(typedPassword[1])){
							println("The new pairs of password don't match");
						}
					}
					
					println("Type your new password");
					typedPassword[0] = readString(inheritKeyboard);
					println("Type your new password again");
					typedPassword[1] = readString(inheritKeyboard);
				}
				
				validator = Handtools.validatorUserInfo(logged.getName(), logged.getEmail(), typedPassword);
				if(logged.auth(typedPassword[0]) & validator[2]){
					list.get(Integer.parseInt(logged.getUniqueID())).changePassword(typedPassword[2], typedPassword[0]);
					break;
				}
				
				++attempts;
				if(attempts == maximumAttempts){
					println("Operation Aborted! Maximum attempts reach");
				}
				else
					println("Remaing Attempts: " + (maximumAttempts - attempts));
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Only Type numbers!");
		}
		finally{
			terminateTask();
		}
	}
	
	private static void editSubInputMyUserHandler(int n, Scanner inheritKeyboard, User logged, List<User> list){
		if(n == 1){
			helperEditUserName(inheritKeyboard, logged, list);
		}
		else if(n == 2){
			helperEditUserEmail(inheritKeyboard, logged, list);
		}
		else if(n == 3){
				helperEditUserPassword(inheritKeyboard, logged, list);
		}
		else if(n == 4){
			//cancel call, do nothing.
		}
		else{
			println("Operation Aborted! Just integers numbers in the range [1, 4] are valid.");
		}
	}
	
	private static void editSubInputUserHandler(int n, Scanner inheritKeyboard, User logged, List<User> list){
		if(n == 1){
			helperAdmEditUser(inheritKeyboard, list);
		}
		else if(n == 2){
			//cancel call, do nothing.
		}
		else{
			println("Operation Aborted! Just integers numbers in the range [1, 2] are valid.");
		}
	}
	
	private static void editInputMyUserHandler(Scanner inheritKeyboard, User logged, List<User> list){
		try{
			if(logged instanceof Administrator)
				editSubInputUserHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, logged, list);
			else
				editSubInputMyUserHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, logged, list);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid Input! Type numbers only!");
			editInputMyUserHandler(inheritKeyboard, logged, list);
		}
	}
	
	private static void editMyUser(Scanner inheritKeyboard, User logged,List<User> list){
		initiateTask("Edit "+((logged instanceof Administrator)? "an user":"my")+" info");
		println("Type the correspondent number to access an operation!");
		if(logged instanceof Administrator){
			println("(1) - Edit general acess user information");
			println("(2) - cancel");
		}
		else{
			println("(1) - Edit my name");
			println("(2) - Edit my e-mail");
			println("(3) - Change your own password");
			println("(4) - cancel");
		}
		editInputMyUserHandler(inheritKeyboard, logged, list);
		terminateTask();
	}
	
	public static void mainSubInputHandler(int n, Scanner inheritKeyboard, User logged, List<User> database1, List<Project> database2, List<Issue> database3){
		if(n == 1){
			if(logged instanceof Administrator){
				editMyUser(inheritKeyboard, logged, database1);
			}
		}
		else if(n == 2){
		}
		else if(n == 3){
			
		}
		else if(n == 4){
			//cancel call, do nothing
		}
		else
		{
			println("Operation Aborted! Just integers numbers in the range [1, 4] are valid.");
		}
	}
	
	private static void mainInputHandler(Scanner inheritKeyboard, User logged, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		try{
			mainSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, logged, dB1, dB2, dB3);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid Input! Type numbers only!");
			mainInputHandler(inheritKeyboard, logged, dB1, dB2, dB3);
		}
	}
	
	public static void mainModule(Scanner inheritKeyboard, User logged, List<User> database1, List<Project> database2, List<Issue> database3){
		initiateModule(":: Edition ::");
		println("What edit task do wanna perform?");
		println("(1) - " + ((logged instanceof Administrator)? "An User ":"My User ") + "information");
		println("(2) - An Issue Information that I was author");
		println("(3) - An Engagement with a project");
		println("(4) - Cancel");
		mainInputHandler(inheritKeyboard, logged, database1, database2, database3);
		terminateModule();
	}

}

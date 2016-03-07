package environment;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import environment.users.*;
import shapes.*;

public class DeallocateSubSystem extends Sys{
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
	
	private static void helperRemoveIssue(Scanner inheritKeyboard, List<Issue> list3){
		String typedIssueUID = "";
		Issue target = null;
		initiateTask("Remove Issue");
		println("\tType issue unique ID, if you don't remember it use the search option in main menu");
		try{
			typedIssueUID = readString(inheritKeyboard);
			if(typedIssueUID.isEmpty()){
				println("Operation aborted! It's not possible perform it to an issue without unique ID number");
			}
			else{
				target = (Issue) Handtools.locateByUID(list3, typedIssueUID);
				if(target == null){
					println("Operation aborted! It unique ID not return any valid issue. Check your type and trying again");
				}
				else{
					String confirmation = "";
					boolean validAnswers[] = {false, false};
					while(confirmation.isEmpty()){
						println("Do you really wanna remove it issue? Y/N");
						println(Handtools.summaryIssueInfo(target));
						confirmation = readString(inheritKeyboard);
						validAnswers[0] = confirmation.compareToIgnoreCase("y") == 0;
						validAnswers[1] = confirmation.compareTo("n") == 0;
						if(validAnswers[0] || validAnswers[1]){
							if(validAnswers[0]){
								list3.add(Integer.parseInt(typedIssueUID), null);
								list3.remove(Integer.parseInt(typedIssueUID)+1);
								println("Removed!");
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
	
	private static void helperRemoveProject(Scanner inheritKeyboard, List<User> list1, List<Project> list2, List<Issue> list3){
		String typedProjectUID = "";
		Project target = null;
		initiateTask("Remove Project");
		println("\tType project unique ID, if you don't remember it uses the search option in main menu, then hit ENTER key");
		try{
			typedProjectUID = readString(inheritKeyboard);
			if(typedProjectUID.isEmpty()){
				println("Operation aborted! It's not possible perform it to an project without unique ID number");
			}
			else{
				target = (Project) Handtools.locateByUID(list2, typedProjectUID);
				if(target == null){
					println("Operation aborted! It unique ID not return any valid project. Check your type and trying again");
				}
				else{
					String confirmation = "";
					boolean validAnswers[] = {false, false};
					while(confirmation.isEmpty()){
						println("Do you really wanna remove it project? Y/N");
						println(Handtools.summaryProjectInfo(target));
						confirmation = readString(inheritKeyboard);
						validAnswers[0] = confirmation.compareToIgnoreCase("y") == 0;
						validAnswers[1] = confirmation.compareTo("n") == 0;
						if(validAnswers[0] || validAnswers[1]){
							if(validAnswers[0]){
								ArrayList<User> users = target.getParticipants();
								ArrayList<Issue> issues = new ArrayList<Issue>();
								
								for(int i = 0; i < users.size(); ++i){
									if(users.get(i) instanceof Graduating){
										if(!((Graduating) users.get(i)).unEngage()){
											break;
										}
									}
								}
								
								issues = Handtools.locateIssuePerProjectRelated(list3, target);
								for(int i = 0; i < issues.size(); ++i){
									issues.get(i).removeRelated();
								}
								
								list2.add(Integer.parseInt(typedProjectUID), null);
								list2.remove(Integer.parseInt(typedProjectUID)+1);
								println("Removed!");
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
	
	private static void helperRemoveUser(Scanner inheritKeyboard, List<User> list1, List<Project> list2, List<Issue> list3){
		String typedUserUID = "";
		User target = null;
		initiateTask("Remove User");
		println("\tType user unique ID, if you don't remember it uses the search option in main menu, the hit ENTER key");
		try{
			typedUserUID = readString(inheritKeyboard);
			if(typedUserUID.isEmpty()){
				println("Operation aborted! It's not possible perform it to an user without unique ID number");
			}
			else{
				target = (User) Handtools.locateByUID(list1, typedUserUID);
				if(target == null){
					println("Operation aborted! It unique ID not return any valid user. Check your type and trying again");
				}
				else if(target instanceof Administrator){
					println("Operation Aborted! Administrator could not be removed.");
				}
				else{
					String confirmation = "";
					boolean validAnswers[] = {false, false};
					while(confirmation.isEmpty()){
						println("Do you really wanna remove it users? Y/N");
						println(Handtools.summaryUserInfo(target));
						confirmation = readString(inheritKeyboard);
						validAnswers[0] = confirmation.compareToIgnoreCase("y") == 0;
						validAnswers[1] = confirmation.compareTo("n") == 0;
						if(validAnswers[0] || validAnswers[1]){
							if(validAnswers[0]){
								ArrayList<Project> projects = new ArrayList<Project>();
								ArrayList<Issue> issues = new ArrayList<Issue>();
								
								projects = Handtools.locateProjectWithItParticipant(list2, target);
								for(int i = 0; i < projects.size(); ++i){
									projects.get(i).unSubscribed(target);
								}
								
								issues = Handtools.locateIssuePerAuthor(list3, target);
								for(int i = 0; i < issues.size(); ++i){
									if(issues.get(i).getAuthors().size() == 1){
										list3.add(Integer.parseInt(issues.get(i).getUniqueID()), null);
										list3.remove(Integer.parseInt(issues.get(i).getUniqueID())+1);
									}
									else{
										issues.get(i).declineCopyright(target);
									}
								}
								
								list1.add(Integer.parseInt(typedUserUID), null);
								list1.remove(Integer.parseInt(typedUserUID)+1);
								println("Removed!");
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
	
	public static void deallocationSubInputHandler(int n, Scanner inheritKeyboard, List<User> dB1, List<Project> dB2, List<Issue> dB3, User logged){
		if(n == 1){
			helperRemoveUser(inheritKeyboard, dB1, dB2, dB3);
		}
		else if(n == 2){
			helperRemoveProject(inheritKeyboard, dB1, dB2, dB3);
		}
		else if(n == 3){
			helperRemoveIssue(inheritKeyboard, dB3);
		}
		else if(n == 4){
			//cancel call, do nothing!
		}
		else{
			println("Operation Aborted! Just integers numbers in the range [1, 4] are valid.");
		}
	}
	
	private static void deallocationInputHandler(Scanner inheritKeyboard, List<User> dB1, List<Project> dB2, List<Issue> dB3, User logged){
		try{
			deallocationSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, dB1, dB2, dB3, logged);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid input! Provider numbers only.\n");
		}
	}
	
	public static void deallocationModule(Scanner inheritKeyboard, List<User> database1, List<Project> database2, List<Issue> database3, User logged){
		initiateModule(":: Deallocation ::");
		println("What you will attemp to remove?");
		println("(1) - An User");
		println("(2) - A Project");
		println("(3) - An Issue");
		println("(4) - Nothing");
		deallocationInputHandler(inheritKeyboard, database1, database2, database3, logged);
		terminateModule();
	}
}

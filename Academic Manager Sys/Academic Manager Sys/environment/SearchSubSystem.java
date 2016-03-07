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

	private static void helperSearchCollaborator(Scanner inheritKeyboard, List<User> list1, List<Project> list2, List<Issue> list3){
		String typedUserIdentity = "";
		println("Type the collaborator name such as it was registered...");
		try{
			typedUserIdentity = readString(inheritKeyboard);
			if(typedUserIdentity.isEmpty()){
				println("Operation aborted! It's not possible look to an user without name");
			}
			else{
				initiateTask("Looking for all collaborator with name: " + typedUserIdentity);
				ArrayList<User> searched = Handtools.locateUsersByAlias(list1, typedUserIdentity);
				ArrayList<Project> projects;
				ArrayList<Issue> issues;
				
				if(searched.isEmpty()){
					println("\tThere's no match! :(");
				}
				else{
					for(int i = 0; i < searched.size(); ++i){
						
						initiateTask("Basic info");
						println(Handtools.summaryUserInfo(searched.get(i)));
						
						initiateTask("\n\t","Academic Production");
						issues = Handtools.locateIssuePerAuthor(list3, searched.get(i));
						
						if(issues.isEmpty()){
							println("\t"+searched.get(i).getName()+" hasn't published any issue, yet!");
						}
						
						else{
							for(int j = 0; j < issues.size(); ++j){
								terminateTask("\t");
								println(Handtools.summaryIssueInfo(issues.get(j)));
								terminateTask("\t");
							}
						}
						terminateTask();
						
						initiateTask("\n\t","Academic Engagement");
						projects = Handtools.locateProjectWithItParticipant(list2, searched.get(i));
						
						if(projects.isEmpty()){
							println("\t" + searched.get(i).getName() + " hasn't engage with any project, yet!");
						}
						for(int k = 0; k < projects.size(); ++k){
							terminateTask();
							println(Handtools.summaryProjectInfo(projects.get(k)));
							terminateTask();
						}
						terminateTask();
					}
				}
				println("---------------------------------------------------------");
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know");
		}
	}
	
	private static void helperSearchProject(Scanner inheritKayboard, List<User> list1, List<Project> list2, List<Issue> list3){
		String typedProjectTitle = "";
			try{
				println("Type the project title was it was registered...");
				typedProjectTitle = readString(inheritKayboard);
				if(typedProjectTitle.isEmpty()){
					println("Operation aborted! It's not possible look to an project without title");
				}
				else{
					initiateTask("Looking for all project with title: " + typedProjectTitle);
					ArrayList<Project> searched = Handtools.locateProjectByTitle(list2, typedProjectTitle);
					ArrayList<Issue> issues;
					ArrayList<User> participants;
					
					if(searched.isEmpty()){
						println("\tThere's no match! :(");
					}
					else{
						for(int i = 0; i < searched.size(); ++i){
							
							initiateTask("\t","Basic info");
							println(Handtools.summaryProjectInfo(searched.get(i)));
							
							initiateTask("\t","Academic Production");
							issues = Handtools.locateIssuePerProjectRelated(list3, searched.get(i));
							
							if(issues.isEmpty()){
								println("\tIt project hasn't produced related issues, yet!");
							}
							
							else{
								for(int j = 0; j < issues.size(); ++j){
									println(Handtools.summaryIssueInfo(issues.get(j)));
								}
							}
							terminateTask();
							
							initiateTask("\t","Work Team");
							participants = searched.get(i).getParticipants();
							
							if(participants.isEmpty()){
								println("\tIt project hasn't any participant, yet!");
							}
							for(int k = 0; k < participants.size(); ++k){
								println("\t"+participants.get(k).getName()+" ("+participants.get(k).getUniqueID()+")");
							}
							terminateTask();
						}
					}
					
					println("---------------------------------------------------------");
				}
			}
			catch(NoSuchElementException e){
				defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know");
			}
	}
	
	public static void helperListIssue(List<Issue> list){
		initiateTask("Listing All Issue");
		if(list.isEmpty()){
			println("\tThere's no Issue");
		}
		else{
			for(int i = 0; i < list.size(); ++i){
				initiateTask("\t", "Issue info");
				println(Handtools.summaryIssueInfo(list.get(i)));
				terminateTask("\t");
			}
		}
		println("---------------------------------------------------------");
	}
	
	public static void searchSubInputHandler(int n, Scanner inheritKeyboard, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		if(n == 1){
			helperSearchCollaborator(inheritKeyboard, dB1, dB2, dB3);
		}
		
		else if(n == 2){
			helperSearchProject(inheritKeyboard, dB1, dB2, dB3);
		}
		
		else if(n == 3){
			helperListIssue(dB3);
		}
		
		else if(n == 4){
			//cancel call, do nothing!
		}
		
		else{
			println("Invalid input! Just integers numbers in the range [1, 4] are valid.\n Operation Aborted!");
		}
		
	}
	
	private static void searchInputHandler(Scanner inheritKeyboard, List<User> dB1, List<Project> dB2, List<Issue> dB3){
		try{
			searchSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, dB1, dB2, dB3);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e,"Invalid input! Provider numbers only.\n");
		}
	}
	
	public static final void mainModule(Scanner inheritkeyboard, List<User> database1, List<Project> database2, List<Issue> database3){
		println("\n#########\tSearch\t##########\n");
		println("What you are looking for?");
		println("(1) - Collaborator");
		println("(2) - Project");
		println("(3) - Issues");
		println("(4) - back");
		searchInputHandler(inheritkeyboard, database1, database2, database3);
		println("\n####################################\n");
	}
}

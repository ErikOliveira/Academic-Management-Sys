package environment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import environment.issue.*;
import environment.users.*;
import shapes.*;

public class AllocateSubSystem extends Sys{
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
	
	private static int helperSubCreaterUserInputHandler(int n, String userInfo[], List<User> list){
		if(n == 1){
			String uid = Handtools.genUID(list);
			Graduating user = new Graduating(uid, userInfo[0], userInfo[1], userInfo[2]);
			list.add(Integer.parseUnsignedInt(uid), user);
			println("User registered!");
			return 0;
		}
		else if(n == 2){
			String uid = Handtools.genUID(list);
			Master user = new Master(uid, userInfo[0], userInfo[1], userInfo[2]);
			list.add(Integer.parseUnsignedInt(uid), user);
			println("User registered!");
			return 0;
		}
		else if(n == 3){
			String uid = Handtools.genUID(list);
			PhD user = new PhD(uid, userInfo[0], userInfo[1], userInfo[2]);
			list.add(Integer.parseUnsignedInt(uid), user);
			println("User registered!");
			return 0;
		}
		else if(n == 4){
			String uid = Handtools.genUID(list);
			Teacher user = new Teacher(uid, userInfo[0], userInfo[1], userInfo[2]);
			list.add(Integer.parseUnsignedInt(uid), user);
			println("User registered!");
			return 0;
		}
		else if(n == 5){
			println("canceling...");
			return 0;
		}
		else{
			println("Invalid input! Just integers numbers in the range [1, 5] are valid.");
			return -1;
		}
	}
	
	private static void helperSubCreateUser(String userInfo[], Scanner inheritKeyboard, List<User> list){
		int typeUser = -1;
		
		try{
			while(typeUser < 0){
				println("\tWhat kind of access it's use will have?");
				println("\t(1) - Undergraduate: not allowed to engage in more than two project in progress at same time");
				println("\t(2) - Master degree: not specified how many projects it will could engage in");
				println("\t(3) - PhD: not specified how many projects it will could engage in");
				println("\t(4) - Teacher: Could leader a group in a project and do two kinda of issue");
				println("\t(5) - cancel");
				typeUser = readNumberLikeString(inheritKeyboard);
				typeUser = helperSubCreaterUserInputHandler(typeUser, userInfo, list);
			}
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid input type numbers only.");
			helperSubCreateUser(userInfo, inheritKeyboard, list);
		}
	}
	
	private static void helperCreateUser(Scanner inheritKeyboard, List<User> list){
		String typedName = "";
		String typedEmail = "";
		String typedPassword[] = {"", ""};
		String stringized[] = {"", "", ""};
		int attempts = 0;
		boolean validator[] = {false, false, false};
		println("----- Registering User -----");
		try{
			while(!((validator[0] & validator[1] & validator[2]) || (attempts == maximumAttempts))){
				if(!validator[0]){
					if(attempts == 0)
						println("\tType User's birth name:");
					else
						println("\tUsers without name aren't allowed");
					
					typedName = readString(inheritKeyboard);
				}
				if(!validator[1]){
					if(attempts == 0)
						println("\tType User's E-Mail address:");
					else
						println("\tType a valid email, like: yourAlias@domain.com");
					
					typedEmail = readString(inheritKeyboard);
				}
				if(!validator[2]){
					if(attempts != 0){
						if(typedPassword[0].isEmpty())
							println("\tPassword could not be empty or less than six characthers or more than ten");
						if(typedPassword[0].length() < 6)
							println("\tPassword is too short");
						if(typedPassword[0].length() > 10)
							println("\tPassword is too long");
						if(!typedPassword[0].equals(typedPassword[1]))
							println("\tThe passwords not match");
					}
					
					println("\tType User's Password");
					typedPassword[0] = readString(inheritKeyboard);
					
					println("\tType the Password again");
					typedPassword[1] = readString(inheritKeyboard);
				}
				validator = Handtools.validatorUserInfo(typedName, typedEmail, typedPassword);
				++attempts;
			}
			
			if(attempts < maximumAttempts){
				stringized[0] = typedName;
				stringized[1] = typedEmail;
				stringized[2] = typedPassword[0];
				helperSubCreateUser(stringized, inheritKeyboard, list);
			}
			else{
				println("Maximum attempts reached... Operation Aborted!");
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Only Type numbers!");
		}
		println("\t----------------------");
	}
	
	private static void helperSubCreateProject(String projectInfo[], Scanner inheritKeyboard, List<Project> list){
		String uid = Handtools.genUID(list);
		Project project = new Project(uid, projectInfo[0], projectInfo[1], projectInfo[2], projectInfo[3], Double.parseDouble(projectInfo[4]),
										Handtools.helperValidatorStringDate(projectInfo[5]), Handtools.helperValidatorStringDate(projectInfo[6]));
		String confirmation = "";
		try{
			
			while(confirmation.equals("")){
				println("\tDo you really wanna saves it project? Y/N");
				println(Handtools.summaryProjectInfo(project));
				confirmation = readString(inheritKeyboard);
				
				boolean validAnswer[] =	{confirmation.compareToIgnoreCase("y") == 0 ,
						confirmation.compareToIgnoreCase("n") == 0};
				
				if(validAnswer[0]){
					list.add(Integer.parseUnsignedInt(uid), project);
					println("Project saved!");
				}

				else if(validAnswer[1]){

					println("\tAll modifications will be lost, and it can be undone!");
					println("\tAre you really sure? Y/N");

					confirmation = readString(inheritKeyboard);

					if(confirmation.compareToIgnoreCase("y") == 0){
						println("discarding...");
					}
					else if(confirmation.compareToIgnoreCase("n") == 0){
						println("Ok! thinking it more time");
						confirmation = "";
					}
					else{
						println("Invalid input just type 'y' for confirm or 'n' for discard");
						confirmation = "";
					}
				}
				
				else{
					println("Invalid input just type 'y' for confirm or 'n' for discard");
					confirmation = "";
				}
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
	}
	
	private static void helperCreateProject(Scanner inheritKeyboard, List<Project> list){
		String typedTitle = "";
		String typedSponsor = "";
		String typedObjective = "";
		String typedDescription = "";
		String typedBugdget = "";
		String typedStartDate[] = {"", "", ""}; // 0 - day, 1 - mouth, 2 - year
		String typedEndDate[] = {"", "", ""}; // 0 - day, 1 - mouth, 2 - year
		String stringized[] = {"", "", "", "", "", "", ""};
		int attempts = 0;
		boolean validator[] = {false, false, false, false, false, false, false};
		println("----- Registering Project -----");
		try{
			while(!((validator[0] & validator[1] & validator[2] & validator[3] & validator[4] & validator[5] & validator[6]) || (attempts == maximumAttempts))){
				
				if(!validator[0]){
					if(attempts != 0){
						if(typedTitle.isEmpty())
							println("\tProjects without title aren't allowed");
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType an Title for this Project:");
					typedTitle = readString(inheritKeyboard);
				}
				if(!validator[1]){
					if(attempts != 0){
						if(typedSponsor.isEmpty())
							println("\tProjects without sponsor aren't allowed");
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType the name who will finance this project:");
					typedSponsor = readString(inheritKeyboard);
				}
				
				if(!validator[2]){
					if(attempts != 0){
						if(typedObjective.isEmpty())
							println("\tProjects without objective aren't allowed");
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType an goal for this Project");
					typedObjective = readString(inheritKeyboard);
				}
				
				if(!validator[3]){
					if(attempts != 0){
						if(typedDescription.isEmpty())
							println("\tProjects without description aren't allowed");
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType an description for this Project");
					typedDescription = readString(inheritKeyboard);
				}
				if(!validator[4]){
					if(attempts != 0){
						if(typedBugdget.contains(","))
							println("\tCommas isn't allowed. Check your typing and try again");
						else
							println("\tProjects with null or negative gross budget aren't allowed");
					}
					
					println("\tType the estimated budget that this project need to be launched");
					typedBugdget = readString(inheritKeyboard);
				}
				
				if(!validator[5]){
					if(attempts != 0){
						if(typedStartDate[0].isEmpty() || typedStartDate[1].isEmpty() || typedStartDate[2].isEmpty()){
							println("\tThe Start date has some field empty. It isn't allowed");
						}
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType the year this project will be launched");
					typedStartDate[2] = readString(inheritKeyboard);
					println("\tType the mouth this will be launched");
					typedStartDate[1] = readString(inheritKeyboard);
					println("\tType the day it will be launched");
					typedStartDate[0] = readString(inheritKeyboard);
				}
				
				if(!validator[6]){
					if(attempts != 0){
						if(typedEndDate[0].isEmpty() || typedEndDate[1].isEmpty() || typedEndDate[2].isEmpty()){
							println("\tThe End date has some field empty. It isn't allowed");
						}
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType the year this project will be ended");
					typedEndDate[2] = readString(inheritKeyboard);
					println("\tType the mouth this will be ended");
					typedEndDate[1] = readString(inheritKeyboard);
					println("\tType the day it will be ended");
					typedEndDate[0] = readString(inheritKeyboard);
				}
				
				validator = Handtools.validatorProjectInfo(typedTitle, typedSponsor, typedObjective, typedDescription, typedBugdget, typedStartDate, typedEndDate);
				++attempts;
			}
			
			if(attempts < maximumAttempts){
				stringized[0] = typedTitle;
				stringized[1] = typedSponsor;
				stringized[2] = typedObjective;
				stringized[3] = typedDescription;
				stringized[4] = typedBugdget;
				stringized[5] = typedStartDate[2]+"-"+typedStartDate[1]+"-"+typedStartDate[0];
				stringized[6] = typedEndDate[2]+"-"+typedEndDate[1]+"-"+typedEndDate[0];
				helperSubCreateProject(stringized, inheritKeyboard, list);
			}
			else{
				println("Maximum attempts reached... Operation Aborted!");
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Only type numbers! restart all from begin");
			helperCreateProject(inheritKeyboard, list);
			
		}
		println("\t----------------------");
	}
	
	private static int helperSubCreateIssueInputHandler (int n, String issueInfo[], User author, List<Issue> list){
		if(n == 1){
			String uid = Handtools.genUID(list);
			Article issue = new Article(uid, issueInfo[0], issueInfo[1], Handtools.helperValidatorStringDate(issueInfo[2]));
			issue.claimCopyright(author);
			list.add(Integer.parseUnsignedInt(uid), issue);
			println("Issue registered!");
			return 0;
		}
		else if(n == 2){
			if(author instanceof Teacher){
				String uid = Handtools.genUID(list);
				Guidance issue = new Guidance(uid,issueInfo[0], issueInfo[1], Handtools.helperValidatorStringDate(issueInfo[2]));
				issue.claimCopyright(author);
				list.add(Integer.parseUnsignedInt(uid), issue);
				println("Issue registered!");
				return 0;
			}
			else{
				println("\tOnly a teacher could publish its type consider publish it as Article");
				return -1;
			}
		}
		else if(n == 3){
			println("canceling...");
			return 0;
		}
		else{
			println("Invalid input! Just integers numbers in the range [1, 3] are valid.");
			return -1;
		}
	}
	
	private static void helperSubCreateIssue(String issueInfo[], Scanner inheritKeyboard, List<Issue> list, User author){
		int typeIssue = -1;
		try{
			while(typeIssue < 0){
				println("\tWhat kind of issues it is");
				println("\t(1) - Article: All collaborate cold publish it");
				println("\t(2) - Guidance: Only Teachers could publish it");
				println("\t(3) - cancel");
				typeIssue = readNumberLikeString(inheritKeyboard);
				typeIssue = helperSubCreateIssueInputHandler(typeIssue, issueInfo, author, list);
			}
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid input type numbers only.");
			helperSubCreateIssue(issueInfo, inheritKeyboard, list, author);
		}
	}
	
	private static void helperCreateIssue(Scanner inheritKeyboard, List<Issue> list, User author){
		String typedTitle = "";
		String typedConference = "";
		String typedReleaseDate[] = {"", "", ""};
		String stringized[] = {"", "", ""};
		int attempts = 0;
		boolean validator[] = {false, false, false};
		println("----- Registering Issue -----");
		try{
			while(!((validator[0] & validator[1] & validator[2]) || (attempts == maximumAttempts))){
				if(!validator[0]){
					if(attempts != 0){
						if(typedTitle.isEmpty())
							println("\tIssues without title aren't allowed");
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType Issue's title:");
					typedTitle = readString(inheritKeyboard);
				}
				if(!validator[1]){
					if(attempts != 0){
						if(typedConference.isEmpty())
							println("\tIssues without conference aren't allowed");
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType Conference's name it issues is from:");
					typedConference = readString(inheritKeyboard);
				}
				if(!validator[2]){
					if(attempts != 0){
						if(typedReleaseDate[0].isEmpty() || typedReleaseDate[1].isEmpty() || typedReleaseDate[2].isEmpty()){
							println("\tThe Release date has some field empty. It isn't allowed");
						}
						else
							println("\tFor internal reasons the system need you type it again. Sorry :(");
					}
					
					println("\tType the year of publishing");
					typedReleaseDate[2] = readString(inheritKeyboard);
					println("\tType the mouth of publishing");
					typedReleaseDate[1] = readString(inheritKeyboard);
					println("\tType the day of publishing");
					typedReleaseDate[0] = readString(inheritKeyboard);
				}
				
				validator = Handtools.validatorIssueInfo(typedTitle, typedConference, typedReleaseDate);
				++attempts;
			}
			
			if(attempts < maximumAttempts){
				stringized[0] = typedTitle;
				stringized[1] = typedConference;
				stringized[2] = typedReleaseDate[2]+"-"+typedReleaseDate[1]+"-"+typedReleaseDate[0];
				helperSubCreateIssue(stringized, inheritKeyboard, list, author);
			}
			else{
				println("Maximum attempts reached... Operation Aborted!");
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Only Type numbers!");
		}
		println("\t----------------------");
	}
	
	public static void allocationSubInputHandler(int n, Scanner inheritKeyboard, List<User> dB1, List<Project> dB2, List<Issue> dB3, User logged){
		if(n == 1){
			helperCreateUser(inheritKeyboard, dB1);
		}
		else if(n == 2){
			helperCreateProject(inheritKeyboard, dB2);
		}
		else if(n == 3){
			helperCreateIssue(inheritKeyboard, dB3, logged);
		}
		else if(n == 4){
			//cancel call, do nothing!
		}
		else{
			println("Invalid input! Just integers numbers in the range [1, 4] are valid.\n Operation Aborted!");
		}
	}
	
	private static void allocationInputHandler(Scanner inheritKeyboard, List<User> dB1, List<Project> dB2, List<Issue> dB3, User logged){
		try{
			allocationSubInputHandler(readNumberLikeString(inheritKeyboard), inheritKeyboard, dB1, dB2, dB3, logged);
		}
		catch(NumberFormatException e){
			defaultTextErrorWithException(e, "Invalid input! Provider numbers only.\n");
		}
	}
	
	public static void allocationModule(Scanner inheritKeyboard, List<User> database1, List<Project> database2, List<Issue> database3, User logged){
		initateModule(":: Alocation ::");
		println("What you are ready to create?");
		println("(1) - New User");
		println("(2) - New Project");
		println("(3) - New Issue");
		println("(4) - Nothing");
		allocationInputHandler(inheritKeyboard, database1, database2, database3, logged);
		terminateModule();
	}
}

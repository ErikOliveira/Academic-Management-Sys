package environment;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import environment.users.*;
import shapes.*;

public class Sys {

	private Scanner keyboard;
	private boolean execution;
	protected static int authAttempts = 3;
	
	private User logged;
	private User target;
	private ArrayList<User> user;
	private ArrayList<Project> projects;
	private ArrayList<Issue> dataBase;
	
	protected static void println(Object ob){
		System.out.println(ob);
	}
	
	protected static void defaultTextErrorWithException(Exception e, String aditionalDetails){
		println("Something went wrong! "+ aditionalDetails);
		println(e);
	}
	
	protected static void initiateModule(String moduleName){
		println("\n#########\t" + moduleName + "\t##########\n");
	}
	
	protected static void terminateModule(){
		println("\n####################################\n");
	}
	
	protected static void initiateTask(String leftAdjustmnt, String taskIdentifier){
		println(leftAdjustmnt + "-----\t" + taskIdentifier + "\t-----");
	}
	
	protected static void initiateTask(String taskIdentifier){
		println("-----\t" + taskIdentifier + "\t-----");
	}
	
	protected static void terminateTask(String leftAdjument){
		println(leftAdjument + "\t----------------------");
	}
	protected static void terminateTask(){
		println("\t----------------------");
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
		
		initiateModule("Shutdown");
		println("Do you wanna shutdown the system? Y/N");
		
		try{
			typedStrings = readString();
			boolean validAnswer1, validAnswer2;
			validAnswer1 = (typedStrings.compareToIgnoreCase("y") == 0);
			validAnswer2 = (typedStrings.compareToIgnoreCase("n") == 0);
			if( validAnswer1 ||  validAnswer2){
				
				if(validAnswer1){
					if(logged instanceof Administrator){
						execution = false;
						println("System will shutdown soon... See u!");
					}
					else{
						println("You not have rights to perform this! Only Administrator can shutdown the system, sorry! :(");
					}
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
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
		}
		finally{
			terminateModule();
		}
	}
	
	private void sysLogGenModule(){
		initiateModule(":: Summary Log ::");
		int coll[] = Handtools.countCollaboratorByType(user);
		println("----------\tCollaborators\t----------\n" +
					"\tAdministrator\t: " + coll[0] +
					"\tGraduate: " + coll[1] +
					"\n\tMaster\t\t: " + coll[2] +
					"\tPhD\t: " + coll[3] +
					"\n\tTeacher\t\t: " + coll[4] +
					"\tTotal\t: " + coll[5]);
		
		int proj[] = Handtools.countProjectsByState(projects);
		println("\n-----------\tProjects\t----------\n" +
					 "\tdrafting    : " + proj[0] +
					 "\t\tprogress: " + proj[1] +
					 "\n\taccomplished: " + proj[2] +
					 "\t\ttotal\t: " + proj[3]);
		
		int iss[] = Handtools.countIssuePerType(dataBase);
		println("\n----------\tIssues\t\t----------\n" +
					 "\tarticles: " + iss[0] +
					 "\tguindance: " + iss[1] +
					 "\n\ttotal\t: " + iss[2]);
		println("\n------------------------------------------");
		terminateModule();
	}
	
	private void menuSubInputHandler(int n){
		
		if(n == 1){
			target = AuthSubSystem.loginModule(keyboard, user, logged);
			if(target != null){
				logged = target;
			}
		}

		else if(n == 2){
			DashBoardSubSystem.mainModule(keyboard, logged, user, projects, dataBase);
		}

		else if(n == 3){
			SearchSubSystem.mainModule(keyboard, user, projects, dataBase);
		}

		else if(n == 4){
			this.sysLogGenModule();
		}

		else if(n == 5){
			logged = AuthSubSystem.logoutModule(keyboard, logged);
		}

		else if(n == 6){
			this.shutdownModule();
		}

		else{
			println("Invalid input! Just integers numbers in the range [1, 6] are valid.\n");
		}
	}
	
	private void menuInputHandler(){
		try{
			menuSubInputHandler(this.readNumberLikeString());
		}
		catch(NumberFormatException e){
			println("Invalid input! Provider numbers only.\n");
		}
	}
	
	private void menuDisplay(){
		initiateModule(":: Menu ::");
		println("Type the correspondent number to access an operation!");
		println("(1) - Login");
		println("(2) - DashBoard");
		println("(3) - Search");
		println("(4) - Summary Log");
		println("(5) - Logout");
		println("(6) - Shutdown");
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
		this.logged = this.target = null;
		this.user = new ArrayList<User>();
		this.projects = new ArrayList<Project>();
		this.dataBase = new ArrayList<Issue>();
		
		Administrator adm = new Administrator(Handtools.genUID(user));
		this.user.add(adm);
	}

	public static void main(String[] args) {
		Sys operator = new Sys();
		operator.mainMenu();
	}

}

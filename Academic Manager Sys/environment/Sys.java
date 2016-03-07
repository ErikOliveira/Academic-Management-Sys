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
	
	protected static void initateModule(String moduleName){
		println("\n#########\t" + moduleName + "\t##########\n");
	}
	
	protected static void terminateModule(){
		println("\n####################################\n");
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
		
		initateModule("Shutdown");
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
		initateModule(":: Summary Log ::");
		println("----------\tCollaborators\t----------");
		println("\ttotal: " + user.size());
		
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
			println("test alocate not leave it here!....");
			AllocateSubSystem.allocationModule(keyboard, user, projects, dataBase, logged);
		}

		else if(n == 3){
			println("undefined module");
		}

		else if(n == 4){
			SearchSubSystem.mainModule(keyboard, user, projects);
		}

		else if(n == 5){
			this.sysLogGenModule();
		}

		else if(n == 6){
			logged = AuthSubSystem.logoutModule(keyboard, logged);
		}

		else if(n == 7){
			this.shutdownModule();
		}

		else{
			println("Invalid input! Just integers numbers in the range [1, 7] are valid.\n");
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
		initateModule(":: Menu ::");
		println("Type the co-related number to access an operation!");
		println("(1) - Login");
		println("(2) - DashBoard");
		println("(3) - ");
		println("(4) - Search");
		println("(5) - Summary Log");
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

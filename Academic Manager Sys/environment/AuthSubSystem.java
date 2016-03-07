package environment;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

import shapes.User;

public class AuthSubSystem extends Sys{
	
	private static String readString(Scanner inheritKeyboard) throws NoSuchElementException{
		String typedString = "";
		typedString = inheritKeyboard.nextLine();
		return typedString;
	}
	
	private static String helperLoggedInfoString(User logged){
		return logged.getName() + " (" + logged.getUniqueID() + ")";
	}
	
	public static void helperLoggedInfo(User logged){
		if(logged != null)
			println("Logged in as: " + helperLoggedInfoString(logged));
		else
			println("Invalid user!");
	}
	
	public static User loginModule(Scanner inheritKeyboard, List<User> database1, User logged){
		
		String typedUserIdentity = "";
		User target = null;
		
		initateModule(":: Login ::");
		println("Type your unique ID number...\nIf you don't remember it don't type anything, the hit Enter key");

		try{
			typedUserIdentity = readString(inheritKeyboard);
			if(typedUserIdentity.isEmpty()){
				SearchSubSystem.searchSubInputHandler(1, inheritKeyboard, database1, null);
				println("Find out your unique ID in the list above, if there any entry, and try login again!");
			}
			else {
				target = (User) Handtools.locateByUID(database1, typedUserIdentity);
				String typedPassword = "";

				if(target == null){
					defaultTextErrorWithException(null, "This user was not found, that's all we know!");
				}
				else if(target == logged){
					println("You are already logged, nothing to do!");
				}
				else{
					for(int i = 0; i < authAttempts; ++i){

						println("Type your password such as its was registered...");
						typedPassword = readString(inheritKeyboard);

						if(target.auth(typedPassword)){
							helperLoggedInfo(target);
							break;
						}

						else{
							if(i+1 == authAttempts){
								println("Password not match! and maximum attempts was reach. Operation fail!");
								target = null;
							}
							else{
								println("Password not match! remain attempts: " + Integer.toString(authAttempts - (i + 1)));
							}
						}
					}
				}
			}
		}
		catch(NoSuchElementException e){
			defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know");
		}
		finally{
			terminateModule();
		}
		
		return target;
	}
	
	public static User logoutModule(Scanner inheritKeyboard, User logged){
		String typedString = "";
		User target = logged;
		
		initateModule(":: Logout ::");
		
		if(target == null){
			println("You must do login first!");
		}
		
		else{
			try{
				helperLoggedInfo(target);
				println("Do you wanna Logout? Y/N");
				 typedString = readString(inheritKeyboard);
				 boolean validAnswer1, validAnswer2;
				 validAnswer1 = (typedString.compareToIgnoreCase("y") == 0);
				 validAnswer2 = (typedString.compareToIgnoreCase("n") == 0);
				 if(validAnswer1 || validAnswer2){
					 if(validAnswer1){
						 println(helperLoggedInfoString(target) + " is now logged out");
						 target = null;
					 }
					 else{
						 println("Operation Aborted!");
					 }
				 }
				 else{
					 println("Invalid answer! Nothing will be done...");
				 }
			}
			catch(NoSuchElementException e){
				defaultTextErrorWithException(e, "There's no such entry in the input stream, that's all we know!");
			}
		}
		
		terminateModule();
		return target;
	}
}

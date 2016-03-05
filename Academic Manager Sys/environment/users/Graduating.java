package environment.users;

import shapes.Engagement;
import shapes.User;

/* The class are structured in that four blocks:
 * 					- Basic Info: declarations of variables
 * 					- Getters & Setters
 * 					- Custom methods: specialized actions obey rules
 * 					- Constructors
 */

public class Graduating extends User implements Engagement{
	
	//	----- Basic Information -----
	
	public static final int maximumEngagementAllowed = 2;
	protected int engagementInProjects;
	
	
	
	//	----- Getters & Setters -----
	
	public int getEngagement(){
		return engagementInProjects;
	}
	
	
	
	//	----- Custom methods -----
	
	public boolean engage(){
		if(engagementInProjects <= maximumEngagementAllowed){
			engagementInProjects++;
			return true;
		}
		else{
			return false;
		}
	}
	
	public boolean unEngage(){
		if(engagementInProjects > 0){
			engagementInProjects--;
			return true;
		}
		else{
			return false;
		}
	}
	
	
	
	//	----- Constructors -----
	
	public Graduating(String uniqueID, String name, String email, String password){
		super(uniqueID, name, email, password);
		engagementInProjects = 0;
	}
}

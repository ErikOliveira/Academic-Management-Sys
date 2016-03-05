package shapes;

import java.util.ArrayList;
import java.util.Date;

import environment.users.*;

/* The class are structured in that four blocks:
 * 					- Basic Info: declarations of variables
 * 					- Getters & Setters
 * 					- Custom methods: specialized actions obey rules
 * 					- Constructors
 */

public class Project {

	//		----- Basic Information -----
	
	public static final String status0 = "in drafting process";
	public static final String status1 = "in progress";
	public static final String status2 = "accomplishment";
	private String title;
	private String sponsor;
	private String status; //¡I am not sure it's really needed!
	private String objective;
	private String description;
	private double cost;
	private Date start;
	private Date end;
	private ArrayList<User> participants;
	private ArrayList<Integer> invalidSubscriptions;
	
	
	
	//			----- Getters & Setters -----
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		if(status.compareToIgnoreCase(status1) != 0){
			this.status = status;
		}
		
		else if(status.compareToIgnoreCase(status1) == 0){
			this.helperStartProject();
		}
	}

	public String getObjective() {
		return objective;
	}

	public void setObjective(String objective) {
		this.objective = objective;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public ArrayList<User> getParticipants() {
		return participants;
	}

	protected void setParticipants(ArrayList<User> participants) {
		this.participants = participants;
	}
	
	
	
	//		----- Custom methods -----
	
	private boolean helperSearchByTeacher(){
		for(int i = 0; i < participants.size(); ++i){
			if(participants.get(i) instanceof Teacher){
				return true;
			}
		}
		
		return false;
	}
	
	private void helperCleanUp(){
		// use loop control like translation for index
		for(int i = 0; i < invalidSubscriptions.size(); ++i){
			participants.remove((this.invalidSubscriptions.get(i) - i));
		}
	}
	
	private void helperValidCandidate(User user, int uid){
		if(user instanceof Engagement){
			if(!((Engagement) user).engage()){
				this.invalidSubscriptions.add(uid);
			}
		}
	}
	
	private void helperStartProject(){
		if(this.helperSearchByTeacher()){
			for(int i = 0; i < participants.size(); ++i){
				helperValidCandidate(participants.get(i), i);
			}
			
			helperCleanUp();
		}
	}
	
	private void helperSubscribed(User user){
		this.participants.add(user);
	}
	
	public void subscribed(User user){
		
		if(user != null){
			
			if(status.compareTo(status1) == 0){

				if(user instanceof Engagement){
					
					if(((Engagement) user).engage()){
						helperSubscribed(user);
					}
					
					else{
						System.out.println("Subscription not done! Maximum Engagement Reach!\n");
					}
				}

				else{
					helperSubscribed(user);
				}
			}
			
			else{
				helperSubscribed(user);
			}
		}
	}
	
	

	//		----- Constructors -----
	
	public Project(String title, String donor, String objective, String description, double budget, Date from, Date to){
		
		this();
		this.setTitle(title);
		this.setSponsor(donor);
		this.setObjective(objective);
		this.setDescription(description);
		this.setCost(budget);
		this.setStart(from);
		this.setEnd(to);
	}
	
	public Project() {
		
		this.title = "";
		this.sponsor = "";
		this.status = Project.status0;
		this.objective = "";
		this.description = "";
		this.cost = 0;
		this.start = null;
		this.participants = new ArrayList<User>();
		this.invalidSubscriptions = new ArrayList<Integer>();
	}
}

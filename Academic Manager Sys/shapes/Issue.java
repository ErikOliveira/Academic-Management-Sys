package shapes;

import java.util.ArrayList;
import java.util.Date;

/* The class are structured in that four blocks:
 * 					- Basic Info: declarations of variables
 * 					- Getters & Setters
 * 					- Custom methods: specialized actions obey rules
 * 					- Constructors
 */

public class Issue {
	
	//		----- Basic Information -----
	
	private String title;
	private String conference;
	private Project related;
	private Date published;
	private ArrayList<User> authors;
	
	
	
	//		----- Getters & Setters -----
	
	public String getTitle() {
		return title;
	}
	
	
	public void setTitle(String title) {
		this.title = title;
	}

	public String getConference() {
		return conference;
	}

	public void setConference(String conference) {
		this.conference = conference;
	}

	public Project getRelated() {
		return related;
	}

	public void setRelated(Project related) {
		if(related != null){
			if(related.getStatus() == "in progress"){
				this.related = related;
			}
		}
	}

	public Date getPublished() {
		return published;
	}

	public void setPublished(Date published) {
		this.published = published;
	}

	public ArrayList<User> getAuthors() {
		return authors;
	}
	
	protected void setAuthors(ArrayList<User> authors) {
		this.authors = authors;
	}
	
	
	
	//		----- Custom methods -----
	
	public void claimCopyright(User user){
		if(user != null){
			this.authors.add(user);
		}
	}
	
	
	
	//		----- Constructors -----
	
	public Issue(String title, String conference, Date releaseDate){
		
		this();
		this.setTitle(title);
		this.setConference(conference);
		this.setPublished(releaseDate);
	}

	public Issue() {

		this.title = "";
		this.conference = "";
		this.related = null;
		this.published = null;
		this.authors = new ArrayList<User>();
	}
}

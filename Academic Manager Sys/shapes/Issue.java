package shapes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

/* The class are structured in that four blocks:
 * 					- Basic Info: declarations of variables
 * 					- Getters & Setters
 * 					- Custom methods: specialized actions obey rules
 * 					- Constructors
 */

public class Issue implements Comparable<Issue>{
	
	//		----- Basic Information -----
	
	private String uniqueID;
	private String title;
	private String conference;
	private Project related;
	private Date published;
	private ArrayList<User> authors;
	
	
	
	//		----- Getters & Setters -----
	
	public String getUniqueID() {
		return uniqueID;
	}


	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}


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
			Collections.sort(authors);
		}
	}
	
	public boolean lookforauthor(User user){
		
		for(int i = 0; i < this.authors.size(); ++i){
			if(this.authors.get(i) == user)
				return true;
		}
		
		return false;
	}
	
	@Override
	public int compareTo(Issue o) {
		// TODO Auto-generated method stub
		return this.published.compareTo(o.published);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		DateFormat format = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault());
		String authors = "";
		
		Collections.sort(this.authors);
		for(int i = 0; i < this.authors.size(); ++i){
			authors += "\t\t" + this.authors.get(i).getName() + "\n";
		}
		
		return this.uniqueID + "#" + this.title + "#" + this.conference + "#" + authors + "#" +
				format.format(published) + "#" + ((this.related == null)? "":this.related.getTitle());
	}
	
	
	
	//		----- Constructors -----

	public Issue(String uniqueID, String title, String conference, Date releaseDate){
		
		this();
		this.setUniqueID(uniqueID);
		this.setTitle(title);
		this.setConference(conference);
		this.setPublished(releaseDate);
	}

	public Issue() {
		this.uniqueID = "";
		this.title = "";
		this.conference = "";
		this.related = null;
		this.published = null;
		this.authors = new ArrayList<User>();
	}
}

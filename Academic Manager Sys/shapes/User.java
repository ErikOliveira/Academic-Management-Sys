package shapes;

/* The class are structured in that four blocks:
 * 					- Basic Info: declarations of variables
 * 					- Getters & Setters
 * 					- Custom methods: specialized actions obey rules
 * 					- Constructors
 */

public abstract class User implements Authenticable, Comparable<User>{
	
	//		----- Basic Information -----
	
	private String uniqueID;
	private String name;
	private String eMail;
	private String password;
	
	//		----- Getters & Setters -----
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		if(name.compareTo(this.name) != 0){
			this.name = name;
		}
	}

	public String getEmail() {
		return eMail;
	}

	public void setEmail(String eMail) {
		
		if(eMail.contains("@")){
			
			if(eMail.compareTo(this.eMail) != 0){
				this.eMail = eMail;
			}
		}
	}

	protected void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
	
	
	
	//		----- Custom methods -----
	
	@Override
	public boolean auth(String isPassword) {
		return password.equals(isPassword);
	}

	@Override
	public void changePassword(String password, String itsNewPassword) {
		if(auth(password)){
			this.password = itsNewPassword;
		}
	}
	
	@Override
	public int compareTo(User o) {
		// TODO Auto-generated method stub
		return this.name.compareTo(o.getName());
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return this.uniqueID + "#" + this.name + "#" + this.eMail;
	}
	
	
	
	//		----- Constructors -----

	public User(String uID, String name, String eMail, String password){
		this();
		setUniqueID(uID);
		setName(name);
		setEmail(eMail);
		this.password = password;
	}
	
	protected User(){
		this.uniqueID = "";
		this.name = "";
		this.eMail = "";
		this.password = "";
	}
}

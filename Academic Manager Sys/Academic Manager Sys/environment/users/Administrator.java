package environment.users;

import shapes.User;

public class Administrator extends User{

	public Administrator(String uID, String name, String eMail, String password) {
		super(uID, name, eMail, password);
		// TODO Auto-generated constructor stub
	}
	
	public Administrator(String uID){
		super();
		this.setUniqueID(uID);
		this.setName("adm");
		this.changePassword("", "000000");
	}
}

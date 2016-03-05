package shapes;

public interface Authenticable {
	public boolean auth(String isPassword);
	public void changePassword(String password, String itsNewPassword);
}

package entity;


/**
 * @author: 李旭芸
 * @className: User
 * @packageName: entity
 * @lastmodifiedtime:2019年9月4日 
 **/
public class User {
	private String userName;
	private String pwd;
	private String account;
	
	public User() {
	}

	public User(String userName, String pwd,String account) {
		super();
		this.userName = userName;
		this.pwd = pwd;
		this.account = account;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", pwd='" + pwd + '\'' +
				", account='" + account + '\'' +
				'}';
	}
}

package Beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class User implements Serializable {
	
	String name ;
	String last_name ;
	String email ;
	String password;
	String tell;
	
	
	

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}



	public User(String name, String last_name,String tell, String email, String password) {
		super();
		this.name = name;
		this.last_name = last_name;
		this.tell = tell;
		this.email = email;
		this.password = password;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLast_name() {
		return last_name;
	}



	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}
	
	
	public String getTell() {
		return tell;
	}
	
	
	public void setTell(String tell) {
		this.tell = tell;
	}
	
}

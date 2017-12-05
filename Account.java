package cu.cs.cpsc215.project2;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *  Class to hold the user's account information 
 * 		strings to hold their information
 * 		booleans to know whether they have authority or not
 * 		and an array list to hold the books they check out
 * @author Caroline
 *
 */
public class Account implements Serializable {

	private String myuserid, myuserpassword, 
    	   		   myusertype, myusername,
    	   		   myuseremail, myuserphone;
	private boolean isAdmin;
	private boolean isStaff;
	private ArrayList<Book> grabbedBooks;
	private Integer ID = 0;
	
	public Account() {
		myuserid = "";
		myuserpassword = ""; 
		myusertype = "";
		myusername = "";
		myuseremail = "";
		myuserphone = "";
		isAdmin = false;
		isStaff = false;
		grabbedBooks = new ArrayList<Book>();
	}
	
	public Account(String userid, String userpassword, 
	   		   		String usertype, String username,
	   		   		String useremail, String userphone) {
		myuserid = userid;
		myuserpassword = userpassword; 
		myusertype = usertype;
		myusername = username;
		myuseremail = useremail;
		myuserphone = userphone;
		
		if (userid.contains("admin") || userid.contains("Admin")) {
			isAdmin = true;
		} else if(usertype.contains("admin")  || usertype.contains("Admin")) {
			isAdmin = true;
		} else if (usertype.contains("staff") || usertype.contains("Staff")) {
			isStaff = true;
		}
		
		grabbedBooks = new ArrayList<Book>();
	}
	
	public void setUserID(String userid) {
		myuserid = userid;
	}
	
	public String getUserID() {
		return myuserid;
	}
	
	public void setUserPassword(String userpassword) {
		myuserpassword = userpassword;
	}
	
	public String getUserPassword() {
		return myuserpassword;
	}
	
	public void setUserType(String usertype) {
		myusertype = usertype;
	}
	
	public String getUserType() {
		return myusertype;
	}
	
	public void setUserName(String username) {
		myusername = username;
	}
	
	public String getUserName() {
		return myusername;
	}
	
	public void setUserEmail(String useremail, JTextField usertext) {
		if (!useremail.contains("@")) {
			JFrame errors = new JFrame();
			JOptionPane.showMessageDialog(errors, 
					"Error, invaild email address. Please enter an email with an '@' sign.", 
											"Error", JOptionPane.ERROR_MESSAGE);
			usertext.setBackground(Color.pink);
		} else {	
			myuseremail = useremail;
		}	
	}
	
	public String getUserEmail() {
		return myuseremail;
	}
	
	public void setUserPhone(String userphone) {
		if (userphone.length() > 10) {
			JFrame errors = new JFrame();
			JOptionPane.showMessageDialog(errors, 
					"Error, invaild phone number. Too many digits Please try again.", 
											"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			 for (char c : userphone.toCharArray()) {
				 if (!Character.isDigit(c)) {
					 JFrame errors = new JFrame();
					 JOptionPane.showMessageDialog(errors, 
							"Error, invaild phone number. Please try again.", 
											"Error", JOptionPane.ERROR_MESSAGE);
			        } else {
			        	myuserphone = userphone;
			        }
			 }
		}	
	}
	
	public String getUserPhone() {
		return myuserphone;
	}
	
	public void setUserIDNum(Integer IdNum) {
		ID = IdNum;
	}
	
	public Integer getUserIDNum() {
		return ID;
	}
	
	
	public void setUserIDnumber(String userType) {	
		if(userType.equals("admin") || userType.equals("Admin")) {
			ID = 0;
		} else {
			ID++;
		}
	}
	
	public void setUserAuthory(String userType) {
		if (userType.contains("admin") || userType.contains("Admin")) {
			isAdmin = true;
		} else if (userType.contains("staff") || userType.contains("Staff")) {
			isStaff = true;
		}		
	}
	
	public boolean acceptPassword(String password) {
		if (this.getUserPassword().equals(password)) {	
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasAuthority(Account account) {
		if (account.isAdmin == true || account.isStaff == true) {
			return true;
		} else {
			return false;
		}
	}
	
	public void grabBook(Book book) {
		if(!book.getStatus()) {
			grabbedBooks.add(book);
			book.setStatus(true);
		}	
	}
	
	public void releaseBook(Book book) {
		grabbedBooks.remove(book);
		book.setStatus(false);
	}
	
	public void changeStatus(Book book) {
		if(grabbedBooks.contains(book)) {
			book.setStatus(false);
		}
	}
	
	public ArrayList<Book> getmyBooks() {
		return grabbedBooks;
	}
	
}

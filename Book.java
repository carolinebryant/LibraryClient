package cu.cs.cpsc215.project2;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *  Class to hold all of the book information strings and an array list 
 *  	for information about the book itself, and boolean flag to mark
 *  		 books as checked out or not when a user selects it
 * @author Caroline
 *
 */
public class Book implements Serializable {

	private String mytitle;
	private String myauthor;
	private String mygenre;
	private ArrayList<String> mytags;
	private boolean isCheckedOut;
		
	public Book() {
		mytitle = "";
		myauthor = "";
		mygenre = "";
		mytags = new ArrayList<String>();
		isCheckedOut = false;
	}
	
	public Book(String title, String author, String genre, ArrayList<String> tags) {
		mytitle = title;
		myauthor = author;
		mygenre = genre;
		mytags = tags;
		isCheckedOut = false;
	}
	
	String getTitle() {
		return mytitle;
	}
	
	String getAuthor() {
		return myauthor;
	}
	
	String getGenre() {
		return mygenre;
	}
	
	ArrayList<String> getTags() {
		return mytags;
	}
	
	String notFound() {
		return "Not Found.";
	}
	
	void setTitle(String title) {
		mytitle = title;
	}
	
	void setAuthor(String author) {
		myauthor = author;
	}
	
	void setGenre(String genre) {
		mygenre = genre;
	}
	
	void setTags(String tags) {
		
		String[] allTags = tags.split(",");
		
		for(int it = 0; it < allTags.length; it++)
			mytags.add(allTags[it]);
	}
	
	/**
	 * function to set, or update, the state of the book
	 * 		state: checked out or not
	 * @param status
	 * @return
	 */
	boolean setStatus(boolean status) {
		if (status) {
			return isCheckedOut = true;
		}
		
		return isCheckedOut = false;
	}
	
	boolean getStatus() {
		return isCheckedOut;
	}
}

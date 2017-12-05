package cu.cs.cpsc215.project2;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class for formatting the table to hold the library of available books.
 * 	Implements and overrides methods from the Abstract Table Model class to 
 * 	create a table with the information about each book. 
 */


import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;
/**
 * Class for formatting the table to display the books in the library, 
 * 		 and will be used to display the user searches or the entire library
 * 	Implements and overrides methods from the Abstract Table Model class to 
 * 	create a table with the information about each book. 
 * @author Caroline
 *
 */
public class BookTableModel extends AbstractTableModel {

	private static ArrayList<Book> library;
	private ArrayList<Book> searchLibrary;
	private boolean isSearch;
	
	private static final long serialVersionUID = 1L;
	private final static String filename = "SDFBookArchive.txt";;
	private File myfile;

	BookTableModel() {
		
		library = new ArrayList<Book>();
		searchLibrary = new ArrayList<Book>();
		isSearch = false;
		
		try {
        	FileInputStream fis = new FileInputStream(filename);         	
        	ObjectInputStream ois = new ObjectInputStream(fis);           	
        	library.addAll((ArrayList<Book>)ois.readObject());
        	
        	fis.close();
        	ois.close();
        } catch(Exception e) {
        	myfile = new File(filename);
        	
        	Book hamlet = new Book();
        	hamlet.setTitle("Hamlet");
        	hamlet.setAuthor("Shakesspeare");
        	hamlet.setGenre("Classic Literature");
        	hamlet.setTags("classics");
    		library.add(hamlet);
    		
    		Book tSAR = new Book();
    		tSAR.setTitle("The Sun Also Rises");
    		tSAR.setAuthor("Ernest Hemingway");
    		tSAR.setGenre("Classic Literature");
    		tSAR.setTags("classics");
    		library.add(tSAR);
    		
    		Book tKR = new Book();
    		tKR.setTitle("The Kite Runner");
    		tKR.setAuthor("Khaled Hosseini");
    		tKR.setGenre("Historical Fiction");
    		tKR.setTags("drama, good reads");
    		library.add(tKR);
    		
    		Book HP = new Book();
    		HP.setTitle("Harry Potter: and the Order of the Phoneix");
    		HP.setAuthor("J.K. Rowling");
    		HP.setGenre("Teen Fiction");
    		HP.setTags("good reads");
    		library.add(HP);
        }
	}
	
	/**
	 * Boolean isSearch is used to trigger if there is a user search or not, 
	 * 		and signal other functions to know which array list to return.
	 */
	void setSearch(boolean trigger) {
		if (trigger)
			isSearch = true;
		else 
			isSearch = false;
	}
	
	boolean getSearchStatus() {
		return isSearch;
	}
	
	/**
	 * returns the status of the book 
	 * @param book
	 * @return
	 */
	boolean isBookCheckedOut(Book book) {
		return book.getStatus();
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		
		if (isSearch) {
			return searchLibrary.size();
		}
		return library.size();
	}

	/**
	 * get value at function is using the boolean 'isSearch' to determine
	 * 		 whether to get the values from the user search or the entire library.
	 */
	@Override
	public Object getValueAt(int row, int column) {
		
		
		if (searchLibrary.isEmpty() && isSearch) {
			return "Nothing found";
		}
		
		if(isSearch){
			switch(column) {
			case 0:
				return searchLibrary.get(row).getTitle();
			case 1:
				return searchLibrary.get(row).getAuthor();
			case 2:
				return searchLibrary.get(row).getGenre();
			default:
				if (searchLibrary.get(row).getStatus())
					return "yes";
				else 
					return "no";
			}
		} 
		
		switch(column) {
			case 0:
				return library.get(row).getTitle();
			case 1:
				return library.get(row).getAuthor();
			case 2:
				return library.get(row).getGenre();
			default:
				if (library.get(row).getStatus())
					return "yes";
				else 
					return "no";
		}
	}
	
	@Override 
	public String getColumnName(int column) {
		switch(column) {
		case 0:
			return "Title";
		case 1:
			return "Author";
		case 2:
			return "Genre";
		default:
			return "Checked Out";
			
		}
	}
	
	/**
	 * add book function will add a book into either the list of searches, or 
	 * 		the regular library, depending on the boolean triggered by a user search.
	 * @param newbook
	 */
	public void addBook(Book newbook) {
		if(isSearch) {
			searchLibrary.add(newbook);
		} else {
			library.add(newbook);
		}
		fireTableDataChanged();
	}	
	
	/**
	 * delete book function will delete a book from either the list of searches, or 
	 * 		the regular library, but will not delete a book that is checked out.
	 * @param book
	 */
	void deleteBook(Book book) {
		if(book.getStatus()) {
			JFrame errors = new JFrame();
			JOptionPane.showMessageDialog(errors, 
					"Error, cannot delete a book that is checked out. Please try again.", 
											"Error", JOptionPane.ERROR_MESSAGE);
		} else {
			if(isSearch) {
				searchLibrary.remove(book);
			} else {	
				library.remove(book);
			}	
		}
		fireTableDataChanged();
	}
	
	
	@Override 
	public void setValueAt(Object word, int row, int column) {
		
		switch(column) {
			case 0:
				library.get(row).setTitle(word.toString());
			case 1:
				library.get(row).setAuthor(word.toString());
			case 2:
				library.get(row).setGenre(word.toString());
			default:
				library.get(row).setStatus(((Book) word).getStatus());	
		}
	}
	
	public Book getBook(int row) {
		return library.get(row);
	}
	
	public ArrayList<Book> getTable() {
		return library;
	}
	
	public ArrayList<Book> getSearchTable() {
		return searchLibrary;
	}
	
	public void setTable(ArrayList<Book> table) {
		library = table;
	}
	
	public void setStatus(int row) {
		library.get(row).setStatus(true);	
	}

	/**
	 * resets our search table model in order for the user to make another query,
	 * 		and must be reset to display the next search results.
	 */
	public void resetSearchTable() {
		searchLibrary.clear();;
	}
	
	public String getFilename() {
		return filename;
	}
	
	/**
	 *  Saves our library after any changes -- adds, deletes, checked out books
	 * @param bookArchive
	 */
	public void saveLibrary(ArrayList<Book> bookArchive) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilename()));
			System.out.println(bookArchive);
			oos.writeObject(bookArchive);
			oos.close();
			
		} catch(Exception e) {
			System.out.println("I didnt save");
		}
	}
}

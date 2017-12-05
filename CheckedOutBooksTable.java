package cu.cs.cpsc215.project2;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Class for formatting the table to hold the current users list of checked out books.
 * 	-- displays on the left-hand side of the checkout window
 * 	Implements and overrides methods from the Abstract Table Model class to 
 * 	create a table with the information about each book. 
 * @author Caroline
 *
 */
public class CheckedOutBooksTable extends AbstractTableModel implements Serializable {

	private Account referenceAccount;
	
	CheckedOutBooksTable(Account user) {

		referenceAccount = user;
	}
	
	boolean isBookCheckedOut(Book book) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 3;
	}

	@Override
	public int getRowCount() {
		return referenceAccount.getmyBooks().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch(column) {
			case 0:
				return referenceAccount.getmyBooks().get(row).getTitle();
			case 1:
				return referenceAccount.getmyBooks().get(row).getAuthor();
			default:
				return referenceAccount.getmyBooks().get(row).getGenre();
		}
	}
	
	@Override 
	public String getColumnName(int column) {
		switch(column) {
		case 0:
			return "Title";
		case 1:
			return "Author";
		default:
			return "Genre";		
		}
	}
	
	public void addBook(Book newbook) {
		referenceAccount.getmyBooks().add(newbook);
		newbook.setStatus(true);
	}
	
	void deleteBook(Book book) {
		referenceAccount.getmyBooks().remove(book);
		book.setStatus(false);
	}
	
	@Override 
	public void setValueAt(Object word, int row, int column) {
		switch(column) {
			case 0:
				referenceAccount.getmyBooks().get(row).setTitle(word.toString());
			case 1:
				referenceAccount.getmyBooks().get(row).setAuthor(word.toString());
			default:
				referenceAccount.getmyBooks().get(row).setGenre(word.toString());
		}
	}
}

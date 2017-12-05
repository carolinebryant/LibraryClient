package cu.cs.cpsc215.project2;
import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

/**
 * Class for formatting the table to hold the list of all the books in the library.
 * 	Implements and overrides methods from the Abstract Table Model class to 
 * 	create a table with the information about each book.
 *  -- no functionality -- only for display 
 * @author Caroline
 *
 */
public class CatalogTableModel extends AbstractTableModel {

	private BookTableModel referenceLibrary;
	
	CatalogTableModel(BookTableModel bookTableModel) {

		referenceLibrary = bookTableModel;
	}
	
	
	boolean isBookCheckedOut(Book book) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return referenceLibrary.getTable().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch(column) {
			case 0:
				return referenceLibrary.getTable().get(row).getTitle();
			case 1:
				return referenceLibrary.getTable().get(row).getAuthor();
			case 2:
				return referenceLibrary.getTable().get(row).getGenre();
			default:
				return referenceLibrary.getTable().get(row).getTags().toString();
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
			return "Tags";
			
		}
	}
	
	public void addBook(Book newbook) {
		referenceLibrary.getTable().add(newbook);
	}	
	
	void deleteBook(Book book) {
		referenceLibrary.getTable().remove(book);
	}
	
	@Override 
	public void setValueAt(Object word, int row, int column) {
		switch(column) {
			case 0:
				referenceLibrary.getTable().get(row).setTitle(word.toString());
			case 1:
				referenceLibrary.getTable().get(row).setAuthor(word.toString());
			case 2:
				referenceLibrary.getTable().get(row).setGenre(word.toString());
			default:
				referenceLibrary.getTable().get(row).setTags(word.toString());	
		}
	}
	
	public ArrayList<Book> getTable() {
		return referenceLibrary.getTable();
	}
}

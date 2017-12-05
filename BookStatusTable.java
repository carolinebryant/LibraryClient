package cu.cs.cpsc215.project2;
import javax.swing.table.AbstractTableModel;

/**
 * Class for formatting the table to display the books the user has already selected 
 * 		at checkout and will display its current status 
 * 	Implements and overrides methods from the Abstract Table Model class to 
 * 	create a table with the information about each book. 
 * @author Caroline
 *
 */
public class BookStatusTable extends AbstractTableModel {

private Account referenceAccount;
	
	BookStatusTable(Account user) {

		referenceAccount = user;
	}
	
	boolean isBookCheckedOut(Book book) {
		return false;
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public int getRowCount() {
		return referenceAccount.getmyBooks().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch(column) {
			case 0:
				if (referenceAccount.getmyBooks().get(row).getStatus())
					return "Check Out";
				else 
					return "Return";
			default:
				return referenceAccount.getmyBooks().get(row).getTitle();
		
		}
	}
	
	@Override 
	public String getColumnName(int column) {
		switch(column) {
		case 0:
			return "Action";
		default:
			return "Book";
		}
	}
	
	public void addBook(Book newbook) {
		referenceAccount.getmyBooks().add(newbook);
	}	
	
	void deleteBook(Book book) {
		referenceAccount.getmyBooks().remove(book);
	}
	
	
	@Override 
	public void setValueAt(Object word, int row, int column) {
		switch(column) {
			case 0:
				referenceAccount.getmyBooks().get(row).setStatus(((Book) word).getStatus());
			
			default:
				referenceAccount.getmyBooks().get(row).setTitle(word.toString());
		}
	}
	
	public Book getBook(int row) {
		return referenceAccount.getmyBooks().get(row);
	}
}

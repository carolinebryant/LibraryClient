package cu.cs.cpsc215.project2;

import javax.swing.JTable;

public interface StrategySearch {

	//public void search(BookTableModel library, String usersearch, int row, int column);
	public boolean search(Book book, String usersearch);
}

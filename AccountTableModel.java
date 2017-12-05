package cu.cs.cpsc215.project2;
import java.awt.List;
import java.util.ArrayList;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

public class AccountTableModel extends AbstractTableModel {

	private AccountDatabase referenceAccountDatabase;
	private ArrayList<Account> accounts;
	
	public AccountTableModel(AccountDatabase accountDatabase) {
		// storing our hash map of user accounts into an array list to format in our table
		referenceAccountDatabase = accountDatabase;
		accounts = new ArrayList<Account>(accountDatabase.getDatabase().values());
	}
	
	/**
	 * resetting our list in order to display a new table -- to switch between tables
	 * @param accountDatabase
	 */
	public void resetList(AccountDatabase accountDatabase) {
		referenceAccountDatabase = accountDatabase;
		accounts = new ArrayList<Account>(accountDatabase.getDatabase().values());
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return referenceAccountDatabase.getDatabase().size();
	}

	@Override
	public Object getValueAt(int row, int column) {
		switch(column) {
		case 0:
			return ((Account) accounts.get(row)).getUserIDNum();
		case 1:
			return ((Account) accounts.get(row)).getUserID();
		case 2:
			return ((Account) accounts.get(row)).getUserName();
		default:
			return ((Account) accounts.get(row)).getUserType();
		}
	}
	
	@Override 
	public String getColumnName(int column) {
		switch(column) {
		case 0:
			return "ID";
		case 1:
			return "Username";
		case 2:
			return "Name";
		default:
			return "Type";
			
		}
	}
	
	public Account getAccount(int row) {
		return ((Account) accounts.get(row));
	}
}

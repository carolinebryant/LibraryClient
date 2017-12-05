package cu.cs.cpsc215.project2;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
/**
 * Database to hold the records of all of the user accounts.
 * 		saves all of the new created accounts to memory or removes 
 *  		deleted accounts from the database.
 * @author Caroline
 *
 */
public class AccountDatabase implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private static Map<String, Account> accountDatabase;
	private final static String filename = "SDFaccounts.txt";;
	private File myfile;
	private Integer totalAccounts = 0;
	
	public AccountDatabase() {
		
		accountDatabase = new HashMap<String, Account>();

            try {
            	FileInputStream fis = new FileInputStream(filename);         	
            	ObjectInputStream ois = new ObjectInputStream(fis);           	
            	accountDatabase.putAll((HashMap<String, Account>)ois.readObject());
            	
            	fis.close();
            	ois.close();
            } catch(Exception e) {
            	
            	myfile = new File(filename);
            	// making our admin accounts
            	Account admin = new Account("admin", "", "admin", "admin", "admin@admin","0000000000");
            	Account Admin = new Account("Admin", "", "Admin", "Admin", "Admin@admin", "0000000000");
            	
            	admin.setUserIDnumber("admin");
            	Admin.setUserIDnumber("Admin");
            	
            	admin.setUserAuthory("admin");
            	admin.setUserAuthory("Admin");
            	accountDatabase.put("admin", admin);
            	accountDatabase.put("Admin", Admin);
            }
    } 
	
	/**
	 * function to signal that we've found the account that we were searching for, 
	 * 		and will only return true if we have both found the account and verified 
	 * 			the password.
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean findAccount(String username, String password) {
		if (accountDatabase.get(username) != null) {
			if(accountDatabase.get(username).acceptPassword(password))
			return true;
		} 
		return false;
	}
	
	public void addAccount(String username, Account newAccount) {
		accountDatabase.put(username, newAccount);
		totalAccounts++;
	}
	
	public void deleteAccount(String username) {
		accountDatabase.remove(username);
	}
	
	public Account getUser(String username) {
		return accountDatabase.get(username); 
	}
	
	public String getUsername(int row) {
		return accountDatabase.get(row).getUserID();
	}
	
	public Integer getExistingIDNum(int row) {
		return accountDatabase.get(row).getUserIDNum();
	}
	
	public Map<String, Account> getDatabase() {
		return accountDatabase;
	}
	
	public String getFilename() {
		return filename;
	}
	
	public void saveAccounts(Map<String, Account> accountDatabase) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilename()));
			System.out.println(accountDatabase);
			oos.writeObject(accountDatabase);
			oos.close();
			
		} catch(Exception e) {
			System.out.println("I didnt save");
		}
	}
	
	public Integer getNumAccounts() {
		return totalAccounts;
	}
	
	/**
	 * function to return the account based on the Id passed in.
	 * 		references the whole accounts database to find the user.
	 * @param Id
	 * @return
	 */
	public Account getUserBasedOnId(Integer Id) {
		// storing our hash map of accounts into an array list to iterate through
		ArrayList<Account> accounts =  new ArrayList<Account>(accountDatabase.values());
		for (Account it : accounts) {
			if (it.getUserIDNum().equals(Id)) {
				return it;
			}
		}	
		return null;
	}
}

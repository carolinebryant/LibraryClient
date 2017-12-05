package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 * Class to make our main window and interactive client 
 * @author Caroline
 *
 */
public class MainWindow extends JFrame {

	private static String title = "SDF Library";
	private JPanel mainpanel;
		private JLabel header;
	private JTabbedPane tabbedpanel;
		private JPanel tab1_library;
			private JScrollPane scrolllibrary;
			private JTable librarytable;
			
		private JPanel tab2_catalog;
			private JScrollPane scrollbooks;
			private JTable bookstable;
			private JPanel buttons_catalog;
				private JButton searchbutton;
				private JButton fuzzysearchbutton;
				private JTextField usersearch;
				private JButton resetbutton;
			private JPanel buttons_catalog2;
				private JButton addBookbutton;
				private JButton removeBookbutton;
			
		private JPanel tab3_accounts;
			private JScrollPane scrollaccounts;
			private JTable accountsTable;
			private JPanel buttons_accounts;
			private JButton addAccountbutton;
			private JButton deleteAccountbutton;
			
		private JPanel tab4_checkout;
			private JPanel tab4_mainPanel;
			private JPanel tablePanel;
				private JScrollPane scrollUserBooks;
					private JTable userBooksTable;
				private JScrollPane scrollCheckedOut;
				private JTable checkedOutTable;
			private JPanel buttons_panel;
				private JButton checkoutbutton;
				private JButton okaybutton;
					private JTextField userSwitch;
	private JPanel bottompanel;
		private JButton logout;
	
	private static boolean hasSearched = false;
	
	MainWindow (CatalogTableModel catalog, BookTableModel books, AccountTableModel accounts, 
				Account currentUser, AccountDatabase database) {
		super(title);
		this.setSize(1000, 600);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		 //so our window appears in the middle of the screen
	    this.setLocationRelativeTo(null);
	    
	    // initializing main window layout
	    mainpanel = new JPanel();
	    mainpanel.setBackground(Color.lightGray);
	    mainpanel.setLayout(new BorderLayout());
		this.add(mainpanel);
	    
		// setting the greeting to have the current user's user-name
		header = new JLabel("Welcome, " + currentUser.getUserName() + ", to the SDF Library Client");
		header.setAlignmentX(CENTER_ALIGNMENT);
		header.setAlignmentY(CENTER_ALIGNMENT);
		header.setFont(new Font("Cambria", Font.CENTER_BASELINE, 20));
		mainpanel.add(header, BorderLayout.NORTH);
		
		tabbedpanel = new JTabbedPane();
	
		// tab for list of all available books to check out
		tab1_library = new JPanel();
		tab1_library.setBackground(Color.gray);
		
			// table for catalog tab
			librarytable = new JTable(catalog); // adding table onto panel
			librarytable.addMouseListener(new mouseClick("Catalog", currentUser.hasAuthority(currentUser), 
					librarytable, books, accounts, database));
			librarytable.getTableHeader();
			librarytable.setSize(700, 500);
			scrolllibrary = new JScrollPane(librarytable, 
										JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
										JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrolllibrary.setBackground(Color.gray);
			scrolllibrary.setSize(400, 600);
			tab1_library.add(scrolllibrary, BorderLayout.CENTER); // adding table to panel within tab
			tabbedpanel.addTab("Library", tab1_library); // creating a tab

		// tab for information about each book
		tab2_catalog = new JPanel();
		tab2_catalog.setBackground(Color.gray);
		
			// for buttons and searches within the tab
			buttons_catalog = new JPanel();
			buttons_catalog.setBackground(Color.gray);
			
			buttons_catalog2 = new JPanel();
			buttons_catalog2.setBackground(Color.gray);
			
			// text box
			usersearch = new JTextField(30);
			buttons_catalog.add(usersearch, BorderLayout.WEST);
			
			// buttons
			searchbutton = new JButton("Search");
			searchbutton.setAlignmentX(CENTER_ALIGNMENT);
			searchbutton.addActionListener(new searchAction("search", books));
			buttons_catalog.add(searchbutton, BorderLayout.CENTER);
			
			fuzzysearchbutton = new JButton("Fuzzy Search");
			fuzzysearchbutton.setAlignmentX(CENTER_ALIGNMENT);
			fuzzysearchbutton.addActionListener(new searchAction("fuzzysearch", books));
			buttons_catalog.add(fuzzysearchbutton, BorderLayout.CENTER);
			
			resetbutton = new JButton("Reset");
			resetbutton.addActionListener(new resetAction(usersearch, books, catalog, librarytable));
			buttons_catalog.add(resetbutton, BorderLayout.EAST);
			
			tab2_catalog.add(buttons_catalog, BorderLayout.NORTH);
			tab2_catalog.add(buttons_catalog2, BorderLayout.SOUTH);
			
			bookstable = new JTable(books); // adding table onto panel
			bookstable.getTableHeader();
			bookstable.addMouseListener(new mouseClick("Catalog", currentUser.hasAuthority(currentUser), 
														bookstable, books, accounts, database));
			
			
			scrollbooks = new JScrollPane(bookstable, 
										JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
										JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollbooks.setBackground(Color.gray);
			scrollbooks.setSize(400, 600);
			tab2_catalog.add(scrollbooks, BorderLayout.CENTER); // adding table to panel within tab
			
			tabbedpanel.addTab("Books", tab2_catalog); // creating a tab
		
		// changing main window view for staff members
		if(currentUser.hasAuthority(currentUser)) {
			// an add book button for the books tab
			addBookbutton = new JButton("Add Book");
			addBookbutton.addActionListener(new addBook(books));
			buttons_catalog2.add(addBookbutton, BorderLayout.EAST);
			
			removeBookbutton = new JButton("Remove Book");
			removeBookbutton.addActionListener(new removeBook(librarytable, books));
			buttons_catalog2.add(removeBookbutton, BorderLayout.WEST);
			
			// panel for accounts
			tab3_accounts = new JPanel();
			tab3_accounts.setBackground(Color.gray);
			
			// make account window
			accountsTable = new JTable(accounts);
			accountsTable.addMouseListener(new mouseClick("Accounts", currentUser.hasAuthority(currentUser), 
																accountsTable, books, accounts, database));
			accountsTable.getTableHeader();
			scrollaccounts = new JScrollPane(accountsTable, 
											JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollaccounts.setBackground(Color.gray);
			scrollaccounts.setSize(400, 600);
			tab3_accounts.add("Accounts", scrollaccounts); // adding table to panel within tab
			tabbedpanel.addTab("Accounts", tab3_accounts); // creating a tab
			
			buttons_accounts = new JPanel();
			buttons_accounts.setBackground(Color.gray);
			
			// buttons
			addAccountbutton = new JButton("Add Account");
			addAccountbutton.setAlignmentX(CENTER_ALIGNMENT);
			addAccountbutton.addActionListener(new addAction(accounts, database));
			tab3_accounts.add(addAccountbutton, BorderLayout.WEST);
			
			deleteAccountbutton = new JButton("Delete Account");
			deleteAccountbutton.setAlignmentX(CENTER_ALIGNMENT);
			deleteAccountbutton.addActionListener(new deleteAction(accountsTable, accounts, database));
			tab3_accounts.add(deleteAccountbutton, BorderLayout.CENTER);
		}
		
		// tab for information about each book
		tab4_checkout = new JPanel();
		tab4_checkout.setBackground(Color.gray);
			
			tab4_mainPanel = new JPanel();
			tab4_mainPanel.setBackground(Color.gray);
			tab4_mainPanel.setLayout(new BorderLayout());
			
			tablePanel = new JPanel();
			tablePanel.setBackground(Color.gray);
			tablePanel.setLayout(new GridLayout(1, 2));
			
			BookStatusTable bst = new BookStatusTable(currentUser);
			
			checkedOutTable =  new JTable(bst);
			
			scrollUserBooks =  new JScrollPane(checkedOutTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollUserBooks.setSize(100, 200);			
			
			CheckedOutBooksTable chm = new CheckedOutBooksTable(currentUser);

			userBooksTable = new JTable(chm);
			
			scrollCheckedOut = new JScrollPane(userBooksTable, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
											JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollCheckedOut.setSize(100, 200);
			
			userBooksTable.addMouseListener(new doubleClick("chm",currentUser, checkedOutTable, userBooksTable, bst, chm, books));
			checkedOutTable.addMouseListener(new doubleClick("bst",currentUser, checkedOutTable, userBooksTable,  bst, chm, books));
			
			tablePanel.add(scrollCheckedOut);
			tablePanel.add(scrollUserBooks);		
			
			// buttons for tab 4
			buttons_panel = new JPanel();
			GridBagLayout gridlayout = new GridBagLayout();
			buttons_panel.setLayout(gridlayout);
			buttons_panel.setBackground(Color.gray);
			
			okaybutton = new JButton("Okay");
			checkoutbutton = new JButton("Add Checkout");
			
			GridBagConstraints Constraint = new GridBagConstraints();
			
			Constraint.weightx = 1;
			Constraint.weighty = 1;
			 
			Constraint.gridx = 0;
			Constraint.gridy = 0;
			buttons_panel.add(checkoutbutton, Constraint);
			    
		    Constraint.gridx = 1;
		    Constraint.gridy = 0;	    
		    buttons_panel.add(okaybutton, Constraint);
			
		    // adding action listeners
			checkoutbutton.addActionListener(new addCheckout(currentUser.hasAuthority(currentUser), books, 
					currentUser, database, catalog, accounts, bst, chm));
			
			okaybutton.addActionListener(new okayAction(this, currentUser.hasAuthority(currentUser), books, 
					currentUser, database, catalog, accounts, bst, chm, userBooksTable));
			
			tab4_mainPanel.add(tablePanel, BorderLayout.CENTER);			
			tab4_mainPanel.add(buttons_panel, BorderLayout.SOUTH);
			tab4_checkout.add(tab4_mainPanel);		
			tabbedpanel.addTab("Checkout", tab4_checkout); // creating a tab
			
			// disabling checkout button so admin cannot check out books
			if(currentUser.hasAuthority(currentUser)) {
				checkoutbutton.setEnabled(false);
			}
			
		bottompanel = new JPanel();
		bottompanel.setBackground(Color.gray);
		logout = new JButton("Logout");
		logout.setAlignmentX(CENTER_ALIGNMENT);
		logout.addActionListener(new logoutAction(this, database));
		bottompanel.add(logout, BorderLayout.CENTER);
		
		
		// adding our panels to the main panel within frame
		mainpanel.add(tabbedpanel, BorderLayout.CENTER);
		mainpanel.add(bottompanel, BorderLayout.SOUTH);
		this.setVisible(true);
		this.setResizable(false);
	}
	
	public class logoutAction implements ActionListener {
		
		private JFrame frame;
		private AccountDatabase accountdatabase;
		
		public logoutAction(JFrame f, AccountDatabase database) {
			frame = f;
			accountdatabase = database;	
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Login loginWindow = new Login(accountdatabase);
			frame.dispose();
		}
	}
	
	public class searchAction implements ActionListener {
		private String toggle;
		private BookTableModel referenceLibrary;
		private String userinput;	
		
		public searchAction(String type, BookTableModel library) {
			toggle = type;
			referenceLibrary = library;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			userinput = usersearch.getText();	
			
			if(toggle.equals("search")) {
				
				Search usersearch = new Search();
				referenceLibrary.setSearch(true);
				for (Book book : referenceLibrary.getTable()) {
					if (usersearch.search(book, userinput)) {
						referenceLibrary.getSearchTable().add(book);
					}
				}
				referenceLibrary.fireTableDataChanged();
			}				
			
			else if(toggle.equals("fuzzysearch")) {

				FuzzySearch usersearch1 = new FuzzySearch();
				referenceLibrary.setSearch(true);
				for(Book book : referenceLibrary.getTable()) {
					if(usersearch1.search(book, userinput))	{			
						referenceLibrary.getSearchTable().add(book);
					}		
				}
				referenceLibrary.fireTableDataChanged();
			}
			hasSearched = true;
		}
		
	}
	
	public class resetAction implements ActionListener {

		private JTextField userquery;
		private JTable referenceTable;
		private BookTableModel referenceLibrary;
		private CatalogTableModel referencecatalog;
		
		public resetAction(JTextField usersearch, BookTableModel library, CatalogTableModel catalog, JTable catalogTable) {
			userquery = usersearch;
			referenceLibrary = library;
			referenceTable = catalogTable;
			referencecatalog = catalog;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			hasSearched = false;
			referenceLibrary.resetSearchTable();
			userquery.setText("");
			referenceLibrary.setSearch(false);
			referenceLibrary.fireTableStructureChanged();
		}
	}
	
	public class addAction implements ActionListener {
		
		private AccountDatabase referencedatabase;
		private AccountTableModel referenceaccounts;

		
		public addAction(AccountTableModel accounts, AccountDatabase database) {
			referencedatabase = database;
			referenceaccounts = accounts;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			CreateAcc createaccountWindow = new CreateAcc(referenceaccounts, referencedatabase);		
		}
		
	}
	
	public class deleteAction implements ActionListener {
		
		private JTable referenceTable;
		private AccountDatabase database;
		private AccountTableModel referenceaccounts;
		
		public deleteAction(JTable table, AccountTableModel accounts, AccountDatabase accountdatabase) {
			referenceTable = table;
			database = accountdatabase;
			referenceaccounts = accounts;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			String username = (String)referenceTable.getValueAt(referenceTable.getSelectedRow(), 1);
			database.deleteAccount(username);
			
			referenceaccounts.resetList(database);
			referenceaccounts.fireTableStructureChanged();
			
			database.saveAccounts(database.getDatabase());
		}
		
	}
	
	public class addBook implements ActionListener {
		
		private BookTableModel referenceLibrary;
		
		public addBook(BookTableModel books) {
			referenceLibrary = books;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(hasSearched) {
				JFrame errors = new JFrame();
				JOptionPane.showMessageDialog(errors, 
						"Error, please press reset before adding a book.", 
												"Error", JOptionPane.ERROR_MESSAGE);
			} else {
				AddBook addbook = new AddBook(referenceLibrary);
			}
		}
		
	}
	
	 public class removeBook implements ActionListener {

	    	JTable frame;
	    	private BookTableModel referenceLibrary;
	    	
	    	public removeBook(JTable table, BookTableModel books) {
	    		frame = table;
	    		referenceLibrary = books;
	    	}
			@Override
			public void actionPerformed(ActionEvent e) {
				
				referenceLibrary.getTable().remove(frame.getSelectedRow());
				referenceLibrary.fireTableStructureChanged();	
				
				referenceLibrary.saveLibrary(referenceLibrary.getTable());
			}
	    }
	
	public class mouseClick implements MouseListener {
		private Point pos;
		private boolean hasAuthority;
		private String tableMarker;
		private JTable referenceTable;
		private BookTableModel referenceBooks;
		private AccountTableModel accountsModel;
		private AccountDatabase referenceAccountDatabase;
		
		public mouseClick(String tableType, boolean authority, JTable table, 
							BookTableModel books, AccountTableModel accounts, AccountDatabase accountDatabase) {
			tableMarker = tableType;
			hasAuthority = authority;
			referenceTable = table;
			referenceBooks = books;
			accountsModel = accounts;
			referenceAccountDatabase = accountDatabase;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
			if (e.getClickCount() == 2) {
				
				// view book details within catalog
				if (tableMarker.equals("Catalog")) {
					// open new window
					pos = e.getPoint();
					int row = referenceTable.rowAtPoint(pos);
					// create and show window with book information
					ViewBook bookDetails = new ViewBook(hasAuthority, referenceTable.getSelectedRow(), referenceBooks);
					
				} // view accounts
				else if (tableMarker.equals("Accounts")) {
					// open new window
					pos = e.getPoint();
					int row = referenceTable.rowAtPoint(pos);
					Account userAcc = accountsModel.getAccount(row);
					// create and show window with user information
					ViewAcc viewUserAccount = new ViewAcc(userAcc, accountsModel, referenceAccountDatabase);
				}
			}	
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub	
		}
		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub			
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub			
		}	
	}
	
	public class doubleClick implements MouseListener {

		Account user;
		String flag;
		JTable referenceTable;
		JTable otherTable;
		BookTableModel referenceLibrary;
		BookStatusTable referencebst;
		CheckedOutBooksTable referencechm;
		
		public doubleClick(String toggle, Account currentUser, JTable table, JTable table2, 
							BookStatusTable bst, CheckedOutBooksTable chm, BookTableModel btm) {
			flag = toggle;
			user = currentUser;
			referenceTable = table;
			otherTable = table2;
			referencebst = bst;
			referencechm = chm;
			referenceLibrary = btm;
		}
		@Override
		public void mouseClicked(MouseEvent e) {
			if (e.getClickCount() == 2) {
				
				if(flag.equals("bst")) {
					if(referencebst.getValueAt(referenceTable.getSelectedRow(), 0).equals("Check Out")) {
						user.grabBook(referencebst.getBook(referenceTable.getSelectedRow()));
						referenceLibrary.setStatus(referenceTable.getSelectedRow());
					} else {
						user.releaseBook(referencebst.getBook(referenceTable.getSelectedRow()));
					}	
				}
				
				
				if(flag.equals("chm")) {
					for(Book book : user.getmyBooks()) {
						user.changeStatus(book);
					}
				}			
				referencechm.fireTableDataChanged();
				referencebst.fireTableDataChanged();
				referenceLibrary.fireTableDataChanged();
			}
		}
		@Override
		public void mouseEntered(MouseEvent arg0) {
		}
		@Override
		public void mouseExited(MouseEvent arg0) {	
		}
		@Override
		public void mousePressed(MouseEvent arg0) {
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {
		}
		
	}
	
	public class addCheckout implements ActionListener {

		boolean userAuthority;
		private Account user;
		private CatalogTableModel referencecatlogModel;
		private BookTableModel referenceLibrary;
		private AccountTableModel referenceaccountTable;
		private AccountDatabase referenceAccounts;
		private CheckedOutBooksTable referencechm;
		private BookStatusTable referencebst;
		
		public addCheckout(boolean authority, BookTableModel library, Account currentUser, 
						AccountDatabase database, CatalogTableModel catalogModel, AccountTableModel accountTable,
						BookStatusTable bst, CheckedOutBooksTable chm) {
			
			userAuthority = authority;
			user = currentUser;
			referencecatlogModel = catalogModel;
			referenceaccountTable = accountTable;
			referenceLibrary = library;
			referenceAccounts = database;
			referencechm = chm;
			referencebst = bst;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			
			// open new catalog window 
			CatalogWindow catalogWindow = new CatalogWindow(userAuthority, user, referenceLibrary, 
					referencecatlogModel, referenceaccountTable, referenceAccounts ,referencebst, referencechm);
		}	
	}
	
	public class switchAccount implements ActionListener {

		private JFrame f;
		private Account user;
		private Account oldUser;
		private JTable referenceCHM;
		private AccountDatabase referenceAccounts;

		switchAccount(JFrame frame, Account currentUser, JTable userBooksTable, AccountDatabase database) {
			f = frame;
			user = currentUser;
			oldUser = currentUser;
			referenceCHM = userBooksTable;
			referenceAccounts = database;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
		
		  try {
			int userId = (int)Integer.parseInt(userSwitch.getText());
			  try {
				  user = referenceAccounts.getUserBasedOnId((Integer)userId);
		    			    	
			  } catch (NullPointerException ex) {
				  JFrame errors = new JFrame();
					JOptionPane.showMessageDialog(errors, 
							"Error, no account found with that ID. Please try again.", 
													"Error", JOptionPane.ERROR_MESSAGE);
			  }
			} catch (NumberFormatException ex) {
		    	JFrame errors = new JFrame();
				JOptionPane.showMessageDialog(errors, 
						"Error, invaild Id number. Please try again.", 
												"Error", JOptionPane.ERROR_MESSAGE);
		    }
			CheckedOutBooksTable chm = new CheckedOutBooksTable(user);
			referenceCHM.setModel(chm);
			chm.fireTableDataChanged();
			f.dispose();
		}	
	}
	
	public class okayAction implements ActionListener {

		private JFrame frame;
		private Account user;
		private Account oldUser;
		private boolean userAuthority;
		private JTable table;
		private CatalogTableModel referencecatlogModel;
		private BookTableModel referenceLibrary;
		private AccountTableModel referenceaccountTable;
		private AccountDatabase referenceAccounts;
		private CheckedOutBooksTable referencechm;
		private BookStatusTable referencebst;
		
		public okayAction(JFrame f, boolean authority, BookTableModel library, Account currentUser, 
						AccountDatabase database, CatalogTableModel catalogModel, AccountTableModel accountTable,
						BookStatusTable bst, CheckedOutBooksTable chm, JTable userBooksTable) {
			frame = f;
			userAuthority = authority;
			user = currentUser;
			oldUser = currentUser;
			referencecatlogModel = catalogModel;
			referenceaccountTable = accountTable;
			referenceLibrary = library;
			referenceAccounts = database;
			referencechm = chm;
			referencebst = bst;
			table = userBooksTable;

		}
		@Override
		public void actionPerformed(ActionEvent e) {

			if(user.getUserType().toLowerCase().contains("admin")) {
				JFrame frame = new JFrame("Switch Users");
				frame.setSize(500, 200);
				frame.setBackground(Color.gray);
				frame.setLocationRelativeTo(null);
				frame.setLayout(new BorderLayout());
				
				JLabel header = new JLabel("Please enter your user Id below: click 'Switch' to change users");
				header.setAlignmentX(CENTER_ALIGNMENT);
				userSwitch = new JTextField(10);
				JButton switchbutton = new JButton("Switch");
				
				JPanel panel1 = new JPanel();
				GridBagLayout layout = new GridBagLayout();
				panel1.setLayout(layout);

				GridBagConstraints Constraint = new GridBagConstraints();
			    Constraint.weightx = 1;
			    Constraint.weighty = 1;
			    
			    // adding our components to the grid
			    Constraint.gridx = 0;
			    Constraint.gridy = 0;
			    panel1.add(userSwitch, Constraint);
			    
			    Constraint.gridx = 1;
			    Constraint.gridy = 0;
			    panel1.add(switchbutton, Constraint);
			    
			    switchbutton.addActionListener(new switchAccount(frame, user, table, referenceAccounts));
			    
			    frame.add(header, BorderLayout.NORTH);
			    frame.add(panel1, BorderLayout.CENTER);
			    frame.setVisible(true);
			} else {
			
				for (int i = 0; i < user.getmyBooks().size(); i++) {
					if(referencebst.getValueAt(i, 0).equals("Check Out")) {
						user.grabBook(referencebst.getBook(i));
					} else {
						user.releaseBook(referencebst.getBook(i));
					}
				}	
				
				// update the tables 
				referencechm.fireTableDataChanged();
				referencebst.fireTableDataChanged();
				referenceLibrary.fireTableDataChanged();
				
				// save our changes 
				referenceAccounts.saveAccounts(referenceAccounts.getDatabase());
				
				JFrame information = new JFrame();
				JOptionPane.showMessageDialog(information, "Horray! Your transaction is complete! Take your books.", 
												"information", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		
	}
}


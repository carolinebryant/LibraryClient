package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class CatalogWindow extends JFrame implements Serializable {
	
	private static String title = "Catalog";
	private JPanel mainpanel;
	private JPanel tab2_catalog;
	private JScrollPane scrollbooks;
	private JTable bookstable;
	private JPanel buttons_catalog;
		private JButton searchbutton;
		private JButton fuzzysearchbutton;
		private JTextField usersearch;
		private JButton resetbutton;
	private JPanel buttons_catalog2;
		private JButton selectbutton;
		private JButton cancelbutton;
		
	private boolean hasSearched = false;
	
	public CatalogWindow(boolean hasAuthority, Account currentUser, BookTableModel books, 
					CatalogTableModel catalog, AccountTableModel accounts, AccountDatabase database,
					BookStatusTable bst, CheckedOutBooksTable chm) {
		
		super(title);
		this.setSize(800, 800);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		 //so our window appears in the middle of the screen
	    this.setLocationRelativeTo(null);
	    
	    // initializing main window layout
	    mainpanel = new JPanel();
	    mainpanel.setBackground(Color.lightGray);
	    mainpanel.setLayout(new BorderLayout());
		this.add(mainpanel);
		
		// tab for information about each book
		tab2_catalog = new JPanel();
		tab2_catalog.setBackground(Color.gray);
		
		// for buttons and searches within the tab
		buttons_catalog = new JPanel();
		buttons_catalog.setBackground(Color.gray);
		
		buttons_catalog2 = new JPanel();
		buttons_catalog2.setBackground(Color.gray);
		
		bookstable = new JTable(books); // adding table onto panel
		bookstable.getTableHeader();
		bookstable.addMouseListener(new mouseClick("Catalog", currentUser.hasAuthority(currentUser), 
													bookstable, books, accounts, database));
		
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
		resetbutton.addActionListener(new resetAction(usersearch, books, catalog, bookstable));
		buttons_catalog.add(resetbutton, BorderLayout.EAST);
		
		tab2_catalog.add(buttons_catalog, BorderLayout.NORTH);
		tab2_catalog.add(buttons_catalog2, BorderLayout.SOUTH);
		
		scrollbooks = new JScrollPane(bookstable,
									JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, 
									JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollbooks.setBackground(Color.gray);
		scrollbooks.setSize(400, 600);
		tab2_catalog.add(scrollbooks, BorderLayout.CENTER); // adding table to panel within tab
		
		buttons_catalog2 = new JPanel();
		buttons_catalog2.setBackground(Color.gray);
		buttons_catalog2.setLayout(new GridBagLayout());
		
		buttons_catalog2.setBackground(Color.gray);
		
		cancelbutton = new JButton("Cancel");
		selectbutton = new JButton("Select");
		selectbutton.addActionListener(new selectAction(this, currentUser, bookstable, books, database, bst, chm));
		
		GridBagConstraints Constraint = new GridBagConstraints();
		
		Constraint.weightx = 1;
		Constraint.weighty = 1;
		 
		Constraint.gridx = 0;
		Constraint.gridy = 0;
		buttons_catalog2.add(selectbutton, Constraint);
		    
	    Constraint.gridx = 1;
	    Constraint.gridy = 0;	    
	    buttons_catalog2.add(cancelbutton, Constraint);
	    
		cancelbutton.addActionListener(new CancelAction(this));
		cancelbutton.setAlignmentX(CENTER_ALIGNMENT);
		cancelbutton.setAlignmentY(CENTER_ALIGNMENT);
		
		mainpanel.add(tab2_catalog, BorderLayout.CENTER); 
		mainpanel.add(buttons_catalog2, BorderLayout.SOUTH);
		this.setVisible(true);
		this.setResizable(false);
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
			referenceLibrary.resetSearchTable();
			userquery.setText("");
			referenceLibrary.setSearch(false);
			referenceLibrary.fireTableStructureChanged();
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
	
	  public class CancelAction implements ActionListener {
			
			private JFrame frame;
			
			public CancelAction(JFrame f) {
				frame = f;
			}
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
			}
		}
	  
	  public class selectAction implements ActionListener {

		 private JFrame frame; 
		 Account user; 
		 JTable referencebookstable;
		 BookTableModel referencelibrary;
		 BookStatusTable referencebst;
		 CheckedOutBooksTable referencechm;
		 AccountDatabase referencedatabase;
		  
		 public selectAction(JFrame f, Account currentUser, JTable bookstable, BookTableModel books, 
				 				AccountDatabase database, BookStatusTable bst, CheckedOutBooksTable chm) {
			 frame = f;
			 user = currentUser;
			 referencebst = bst;
			 referencechm = chm;
			 referencelibrary = books;
			 referencebookstable = bookstable;
			 referencedatabase = database;
		 }
		  
		@Override
		public void actionPerformed(ActionEvent e) {
			Book grabbedBook = referencelibrary.getTable().get(referencebookstable.getSelectedRow());
			user.grabBook(grabbedBook);
			referencebst.fireTableDataChanged();
			
			referencedatabase.saveAccounts(referencedatabase.getDatabase());
			frame.dispose();
		}
		  
	  }
}

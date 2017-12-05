package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ViewAcc extends JFrame {

	private static String title = "View User Account";
	private JPanel panel_top;
	private JLabel top_label;
	private JLabel top_label1;
	private JPanel panel_mid;
	private GridBagLayout gridlayout;
	
	private JLabel userid;
	private JTextField usertext1;
	    
	private JLabel userpassword;
	private JPasswordField usertext2;
	private JTextField showPassword;
	    
	private JLabel usertype;
	private JTextField usertext3;
	    
	private JLabel username;
	private JTextField usertext4;
	    
	private JLabel useremail;
	private JTextField usertext5;
	    
	private JLabel userphone;
	private JTextField usertext6;
	
	private GridBagConstraints Constraint;
	
	private JPanel panel_bot;
	private GridBagLayout gridlayout1;
	private GridBagConstraints Constraint1;
	
	private JButton savebutton;
    private JButton backbutton;
    
    public ViewAcc(Account user, AccountTableModel accounts, AccountDatabase database) {
    	// making our main window
    			super(title);
    			this.setSize(600, 400);
    			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    			
    			 //so our window appears in the middle of the screen
    		    this.setLocationRelativeTo(null);
    		    
    		    // initializing main window layout
    		    this.setLayout(new BorderLayout());
    		    
    		    // instantiating our text fields
    		    usertext1 = new JTextField(30);
    		    usertext2 = new JPasswordField(30);
    		    showPassword = new JTextField(30);
    		    usertext3 = new JTextField(30);
    		    usertext4 = new JTextField(30);
    		    usertext5 = new JTextField(30);
    		    usertext6 = new JTextField(30);
    			
    		    // adding our top panel
    		    panel_top = new JPanel();
    		    					// (what you want to layout, axis)
    		    panel_top.setLayout(new BoxLayout(panel_top, BoxLayout.Y_AXIS));
    		    
    		    /* our top panel */
    		    top_label = new JLabel("Account information");
    		    top_label1 = new JLabel("edit fields and click 'Save' to change credentials, or  'Cancel' to exit");
    		    top_label.setAlignmentX(CENTER_ALIGNMENT);
    		    top_label1.setAlignmentX(CENTER_ALIGNMENT);

    		    panel_top.add(top_label);
    		    panel_top.add(top_label1);	   
    		    panel_top.setBackground(Color.gray);
    		    
    		    /* our mid panel */
    		    panel_mid = new JPanel();
    		    gridlayout = new GridBagLayout();
    		    panel_mid.setLayout(gridlayout);
    		    panel_mid.setBackground(Color.gray);
    		    
    		    // components 
    		    userid = new JLabel("Username:");
    		    System.out.println(user.getUserID());
    		    usertext1.setText(user.getUserID());
    		    
    		    userpassword = new JLabel("Password:");
    		    showPassword.setText(user.getUserPassword());
    		    usertext2.setText(showPassword.getText());
    		    
    		    usertype = new JLabel("User Type:");
    		    usertext3.setText(user.getUserType());
    		    
    		    username = new JLabel("Prefered Name:");
    		    usertext4.setText(user.getUserName());
    		    
    		    useremail = new JLabel("Email Address:");
    		    usertext5.setText(user.getUserEmail());
    		    
    		    userphone = new JLabel("Phone Number:");
    		    usertext6.setText(user.getUserPhone());
    		    
    		    Constraint = new GridBagConstraints();
    		    Constraint.weightx = 1;
    		    Constraint.weighty = 1;
    		    
    		    // adding our components to the grid
    		    Constraint.gridx = 0;
    		    Constraint.gridy = 0;
    		    panel_mid.add(userid, Constraint);
    		    
    		    Constraint.gridx = 1;
    		    Constraint.gridy = 0;
    		    panel_mid.add(usertext1, Constraint);
    		    
    		    Constraint.gridx = 0;
    		    Constraint.gridy = 1;
    		    panel_mid.add(userpassword, Constraint);
    		    
    		    Constraint.gridx = 1;
    		    Constraint.gridy = 1;
    		    panel_mid.add(showPassword, Constraint);
    		    
    		    Constraint.gridx = 0;
    		    Constraint.gridy = 2;
    		    panel_mid.add(usertype, Constraint);
    		    
    		    Constraint.gridx = 1;
    		    Constraint.gridy = 2;
    		    panel_mid.add(usertext3, Constraint);
    		    
    		    Constraint.gridx = 0;
    		    Constraint.gridy = 3;
    		    panel_mid.add(username, Constraint);
    		    
    		    Constraint.gridx = 1;
    		    Constraint.gridy = 3;
    		    panel_mid.add(usertext4, Constraint);
    		    
    		    Constraint.gridx = 0;
    		    Constraint.gridy = 4;
    		    panel_mid.add(useremail, Constraint);
    		    
    		    Constraint.gridx = 1;
    		    Constraint.gridy = 4;
    		    panel_mid.add(usertext5, Constraint);
    		    
    		    Constraint.gridx = 0;
    		    Constraint.gridy = 5;
    		    panel_mid.add(userphone, Constraint);
    		    
    		    Constraint.gridx = 1;
    		    Constraint.gridy = 5;
    		    panel_mid.add(usertext6, Constraint);
    		    
    		    /* our lower panel */
    		    panel_bot = new JPanel();
    		    gridlayout1 = new GridBagLayout();
    		    panel_bot.setLayout(gridlayout1);
    		    panel_bot.setBackground(Color.gray);
    		    
    		    Constraint1 = new GridBagConstraints();
    		    
    		    // components
    		    savebutton = new JButton("Save");
    		    backbutton = new JButton("Cancel");
    		    
    		    // adding actions to our buttons
    		    savebutton.addActionListener(new createAccount(this, user, accounts, database));
    		    backbutton.addActionListener(new CancelAction(this));
    		
    		    Constraint1.weightx = 1;
    		    Constraint1.weighty = 1;
    		    
    		    Constraint1.gridx = 1;
    		    Constraint1.gridy = 0;
    		    panel_bot.add(savebutton, Constraint1);
    		    
    		    Constraint1.gridx = 0;
    		    Constraint1.gridy = 0;	    
    		    panel_bot.add(backbutton, Constraint1);
    	    
    		    // adding our separate panels 
    		    this.add(panel_top, BorderLayout.NORTH);
    		    this.add(panel_mid, BorderLayout.CENTER);	    
    		    this.add(panel_bot, BorderLayout.SOUTH);
    		    
    		    // making our main window visible 
    		    this.setVisible(true);
    }
    
    public class createAccount implements ActionListener {
		
		private JFrame frame;
		private Account currentUser;
		private AccountDatabase database;
		private AccountTableModel referenceaccounts;
		
		public createAccount(JFrame f, Account user, AccountTableModel accounts, AccountDatabase accountdatabase) {
			frame = f;
			currentUser = user;
			database = accountdatabase;
			referenceaccounts = accounts;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			String password = new String(usertext2.getPassword());
			
			Account newaccount = new Account();
			newaccount.setUserID(usertext1.getText());
			newaccount.setUserPassword(password);
			newaccount.setUserType(usertext3.getText());
			newaccount.setUserName(usertext4.getText());
			newaccount.setUserEmail(usertext5.getText(), usertext5);
			newaccount.setUserPhone(usertext6.getText());							 
			
			// setting user id by passing in their user type
			newaccount.setUserIDnumber(usertext3.getText());
			newaccount.setUserAuthory(usertext3.getText());
			
			database.getDatabase().replace(usertext1.getText(), currentUser, newaccount);

			System.out.println(database.getDatabase().toString());
			referenceaccounts.resetList(database);
			referenceaccounts.fireTableStructureChanged();
			
			database.saveAccounts(database.getDatabase());
			frame.dispose();
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
}

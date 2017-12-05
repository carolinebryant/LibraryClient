package cu.cs.cpsc215.project2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;


public class Login extends JFrame {

	private static String title = "Login";
	JTextField usertext1;
	JPasswordField usertext2;
	AccountDatabase copyDatabase;
	BookTableModel btm;
	CatalogTableModel catalog;
	AccountTableModel atm;
	
	public Login(AccountDatabase accountDatabase) {
		// setting up our main window
		super(title);
		this.setSize(400, 200);
	    this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    
	    //so our window appears in the middle of the screen
	    this.setLocationRelativeTo(null);
	    
	    // initializing main window layout
	    this.setLayout(new BorderLayout());
	    
	    // copying database
	    copyDatabase = accountDatabase;
	    
	    // adding our top panel
	    JPanel panel_top = new JPanel();
	    					// (what you want to layout, axis)
	    panel_top.setLayout(new BoxLayout(panel_top, BoxLayout.Y_AXIS));
	    panel_top.setBackground(Color.lightGray);
	    
	    /* our top panel */
	    JLabel top_label = new JLabel("Welcome to the SDF Library client!");
	    JLabel top_label1 = new JLabel("Enter your login information below,");
	    JLabel top_label2 = new JLabel("click 'Okay' to confirm credentials, or  'Cancel' to exit");
	    top_label.setAlignmentX(CENTER_ALIGNMENT);
	    top_label1.setAlignmentX(CENTER_ALIGNMENT);
	    top_label2.setAlignmentX(CENTER_ALIGNMENT);
	    panel_top.add(top_label);
	    panel_top.add(top_label1);
	    panel_top.add(top_label2);	   
	    
	    /* our mid panel */
	    JPanel panel_mid = new JPanel();
	    GroupLayout grouplayout = new GroupLayout(panel_mid);
	    panel_mid.setLayout(grouplayout);
	    panel_mid.setBackground(Color.lightGray);
	    
	    // components 
	    JLabel userid = new JLabel("User Id:");
	    usertext1 = new JTextField(30);
	    
	    JLabel userpassword = new JLabel("Password:");
	    usertext2 = new JPasswordField(30);
	    
	    // setting auto gaps
	    grouplayout.setAutoCreateGaps(true);
	    grouplayout.setAutoCreateContainerGaps(true);

	    // horizontal group
	    GroupLayout.SequentialGroup hGroup = grouplayout.createSequentialGroup();
	    
	    hGroup.addGroup(grouplayout.createParallelGroup().
	            addComponent(userid).addComponent(userpassword));
	    hGroup.addGroup(grouplayout.createParallelGroup().
	            addComponent(usertext1).addComponent(usertext2));
	    grouplayout.setHorizontalGroup(hGroup);
	    
	    // vertical group
	    GroupLayout.SequentialGroup vGroup = grouplayout.createSequentialGroup();
	    
	    vGroup.addGroup(grouplayout.createParallelGroup(Alignment.BASELINE).
	            addComponent(userid).addComponent(usertext1));
	    vGroup.addGroup(grouplayout.createParallelGroup(Alignment.BASELINE).
	            addComponent(userpassword).addComponent(usertext2));
	    grouplayout.setVerticalGroup(vGroup);
	    
	    /* our lower panel */
	    JPanel panel_bot = new JPanel();
	    GridBagLayout gridlayout = new GridBagLayout();
	    panel_bot.setLayout(gridlayout);
	    
	    GridBagConstraints Constraint = new GridBagConstraints();
	    
	    // components
	    JButton okaybutton = new JButton("Okay");
	    JButton cancelbutton = new JButton("Cancel");
	    
	    // adding actions to our buttons
	    okaybutton.addActionListener(new loginAction(this, accountDatabase));
	    cancelbutton.addActionListener(new CloseWindow());
	
	    Constraint.weightx = 1;
	    Constraint.weighty = 1;
	    
	    Constraint.gridx = 0;
	    Constraint.gridy = 0;	    
	    panel_bot.add(okaybutton, Constraint);
	    
	    Constraint.gridx = 1;
	    Constraint.gridy = 0;
	    panel_bot.add(cancelbutton, Constraint);
	    
	    // setting panel color
	    Color bot_color = Color.gray;
	    panel_bot.setBackground(bot_color);
	    
	    // adding our separate panels 
	    this.add(panel_top, BorderLayout.NORTH);
	    this.add(panel_mid, BorderLayout.CENTER);
	    this.add(panel_bot, BorderLayout.SOUTH);
	    
	    // making our main window visible 
	    this.setVisible(true);
	    this.setResizable(false);
	}
	
	public class loginAction implements ActionListener {
		
		private JFrame frame;
		private AccountDatabase database;
		
		public loginAction(JFrame f, AccountDatabase accDatabase) {
			frame = f;
			database = accDatabase;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// open main window
			// scan through our list of valid user-names 
			usertext1.getText();
			String password = new String(usertext2.getPassword());
		
			if(copyDatabase.findAccount(usertext1.getText(), password)) {

				btm = new BookTableModel();
				catalog = new CatalogTableModel(btm);
				atm = new AccountTableModel(copyDatabase);
				MainWindow mainwindow = new MainWindow(catalog, btm, atm, database.getUser(usertext1.getText()), database);
				frame.dispose();
			} else {
				JFrame errors = new JFrame();
				JOptionPane.showMessageDialog(errors, "Incorrect Username or Password. Please try again.", 
												"Error", JOptionPane.ERROR_MESSAGE);
			}
		}	
	}
	
	public class CloseWindow implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}
	
}

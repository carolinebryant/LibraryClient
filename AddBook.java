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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

	public class AddBook extends JFrame {

		private static String title = "Add Book";
		private JPanel panel_top;
		private JLabel top_label;
		private JLabel top_label1;
		private JPanel panel_mid;
		private GridBagLayout gridlayout;
		
		private JLabel bookTitle;
		private JTextField text1;
		    
		private JLabel bookAuthor;
		private JTextField text2;
		    
		private JLabel bookGenre;
		private JTextField text3;
		    
		private JLabel bookTags;
		private JTextField text4;
		
		private GridBagConstraints Constraint;
		
		private JPanel panel_bot;
		private GridBagLayout gridlayout1;
		private GridBagConstraints Constraint1;
		
		private JButton savebutton;
	    private JButton backbutton;
	    
	    public AddBook(BookTableModel books) {
	    	
	    	// making our main window
			super(title);
			this.setSize(600, 400);
			this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			
			 //so our window appears in the middle of the screen
		    this.setLocationRelativeTo(null);
		    
		    // initializing main window layout
		    this.setLayout(new BorderLayout());
		    
		    // adding our top panel
		    panel_top = new JPanel();
		    					// (what you want to layout, axis)
		    panel_top.setLayout(new BoxLayout(panel_top, BoxLayout.Y_AXIS));
		    
		    /* our top panel */
		    top_label = new JLabel("Book information");
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
		    bookTitle = new JLabel("Title:");
		    text1 = new JTextField(30);	
		    
		    bookAuthor = new JLabel("Author:");
		    text2 = new JTextField(30);	    
		    
		    bookGenre = new JLabel("Genre:");
		    text3 = new JTextField(30);
		    
		    bookTags = new JLabel("Tags:");
		    text4 = new JTextField(30);

		    Constraint = new GridBagConstraints();
		    Constraint.weightx = 1;
		    Constraint.weighty = 1;
		    
		    Constraint = new GridBagConstraints();
		    Constraint.weightx = 1;
		    Constraint.weighty = 1;
		    
		    // adding our components to the grid
		    Constraint.gridx = 0;
		    Constraint.gridy = 0;
		    panel_mid.add(bookTitle, Constraint);
		    
		    Constraint.gridx = 1;
		    Constraint.gridy = 0;
		    panel_mid.add(text1, Constraint);
		    
		    Constraint.gridx = 0;
		    Constraint.gridy = 1;
		    panel_mid.add(bookAuthor, Constraint);
		    
		    Constraint.gridx = 1;
		    Constraint.gridy = 1;
		    panel_mid.add(text2, Constraint);
		    
		    Constraint.gridx = 0;
		    Constraint.gridy = 2;
		    panel_mid.add(bookGenre, Constraint);
		    
		    Constraint.gridx = 1;
		    Constraint.gridy = 2;
		    panel_mid.add(text3, Constraint);
		    
		    Constraint.gridx = 0;
		    Constraint.gridy = 3;
		    panel_mid.add(bookTags, Constraint);
		    
		    Constraint.gridx = 1;
		    Constraint.gridy = 3;
		    panel_mid.add(text4, Constraint);
		    
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
		    savebutton.addActionListener(new saveBook(this, books));
		    backbutton.addActionListener(new CancelAction(this));
		    
		    Constraint1.weightx = 1;
		    Constraint1.weighty = 1;
		    
		    Constraint1.gridx = 0;
		    Constraint1.gridy = 0;	    
		    panel_bot.add(backbutton, Constraint1);
		    
		    Constraint1.gridx = 1;
		    Constraint1.gridy = 0;
		    panel_bot.add(savebutton, Constraint1);
	    
		    // adding our separate panels 
		    this.add(panel_top, BorderLayout.NORTH);
		    this.add(panel_mid, BorderLayout.CENTER);	    
		    this.add(panel_bot, BorderLayout.SOUTH);
		    
		    // making our main window visible 
		    this.setVisible(true);
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
	    
	    public class saveBook implements ActionListener {
			
			private JFrame frame;
			private BookTableModel referenceLibrary;
			
			public saveBook(JFrame f, BookTableModel books) {
				frame = f;
				referenceLibrary = books;
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				Book newbook = new Book();
				
				if(text1.getText().trim() == null || text2.getText().trim() == null || 
					text3.getText().trim() == null || text4.getText().trim() == null || 
						text1.getText().trim().isEmpty() || text2.getText().trim().isEmpty()
						|| text3.getText().trim().isEmpty() || text4.getText().trim().isEmpty()) {
					
					JFrame errors = new JFrame();
					JOptionPane.showMessageDialog(errors, 
							"Error, must fill in all required fields to add a book. Please try again.", 
													"Error", JOptionPane.ERROR_MESSAGE);
					if(text1.getText().trim() == null ||
						text1.getText().trim().isEmpty()) text1.setBackground(Color.pink);
					if(text2.getText().trim() == null || 
						text2.getText().trim().isEmpty()) text2.setBackground(Color.pink);
					if(text3.getText().trim() == null ||
						text3.getText().trim().isEmpty())text3.setBackground(Color.pink);
					if(text4.getText().trim() == null || 
						text4.getText().trim().isEmpty())text4.setBackground(Color.pink);
					
				} else {	
					
					newbook.setTitle(text1.getText());
					newbook.setAuthor(text2.getText());
					newbook.setGenre(text3.getText());
					newbook.setTags(text4.getText());
					
					referenceLibrary.addBook(newbook);			
					referenceLibrary.fireTableStructureChanged();					
					referenceLibrary.saveLibrary(referenceLibrary.getTable());
					
					frame.dispose();
				}
			}
			
		}
}

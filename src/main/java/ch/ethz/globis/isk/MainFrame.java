package ch.ethz.globis.isk;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame{
	
	public MainFrame(String title){
		super(title);
		
		//Layout manager
		setLayout(new BorderLayout());
		
		//Create Swing component
		final JTextArea textArea = new JTextArea();
		JButton searchButton = new JButton("Search");
		JButton cancelButton = new JButton("Cancel");
		
		JLabel titleLabel = new JLabel("Publication title:");
		JLabel idLabel = new JLabel("ID:");
		JLabel beginLabel = new JLabel("Begin Offset:");
		JLabel endLabel = new JLabel("End Offset:");
		JLabel authorLabel = new JLabel("Authors:");
		JLabel conferenceLabel = new JLabel("Authors:");
		
		final JTextField titleTextField = new JTextField();
		final JTextField idTextField = new JTextField();
		JTextField beginTextField = new JTextField();
		JTextField endTextField = new JTextField();
		final JTextField authorTextField = new JTextField();
		final JTextField conferenceTextField = new JTextField();

		
		JPanel pane = new JPanel(new GridBagLayout());
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		////////First row /////////
		gc.anchor = GridBagConstraints.LINE_END;
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		
		gc.gridx = 0;
		gc.gridy = 0;
		
		add(titleLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;
		add(idLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 2;
		add(beginLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 3;
		add(endLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 4;
		add(authorLabel, gc);
		
		gc.gridx = 0;
		gc.gridy = 5;
		add(conferenceLabel, gc);
		
		////////second row/////////
		
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		gc.gridy = 0;
		
		gc.ipadx = 200;
		
		add(titleTextField, gc);
		
		gc.gridx = 1;
		gc.gridy = 1;
		add(idTextField, gc);
		
		gc.gridx = 1;
		gc.gridy = 2;
		add(beginTextField, gc);
		
		gc.gridx = 1;
		gc.gridy = 3;
		add(endTextField, gc);
		
		gc.gridx = 1;
		gc.gridy = 4;
		add(authorTextField, gc);
		
		gc.gridx = 1;
		gc.gridy = 5;
		add(conferenceTextField, gc);
		
		/////// Search Button /////////

		gc.gridx = 1;
		gc.gridy = 6;
		
		gc.ipadx = 50;
		
		add(searchButton, gc);
		
		
		////////////////
	

		
		
		
		searchButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				
				
				String query = "";
				
				//TODO:
				query = query + titleTextField.getText();
									
				
				
				//////////////Table/////////
				JFrame frame = new JFrame("FrameDemo");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
				String[] columnNames = {"Publication Title",
		                "Year",
		                "Electronic Edition",
		                "Authors",
		                "Conference", 
		                "Part of Proceedings"};
				
				//TODO: Here we need to put our own data
				Object[][] data = {
					    {query, "Smith",
					     "Snowboarding", new Integer(5), new Boolean(false), new Boolean(false)},
					    {"John", "Doe",
					     "Rowing", new Integer(3), new Boolean(true), new Boolean(false)},
					    {"Sue", "Black",
					     "Knitting", new Integer(2), new Boolean(false), new Boolean(false)},
					    {"Jane", "White",
					     "Speed reading", new Integer(20), new Boolean(true), new Boolean(false)},
					    {"Joe", "Brown",
					     "Pool", new Integer(10), new Boolean(false), new Boolean(false)}
					};
				JTable table = new JTable(data, columnNames);
				
				Container container = getContentPane();
				
				container.setLayout(new BorderLayout());
				container.add(table.getTableHeader(), BorderLayout.PAGE_START);
				container.add(table, BorderLayout.CENTER);
				frame.add(new JScrollPane(table));
				frame.pack();
				frame.setVisible(true);
				
			}
		});
	}
	
	
	
}

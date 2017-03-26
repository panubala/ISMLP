package ch.ethz.globis.isk;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
		JButton findButton = new JButton("Search");
		JButton cancelButton = new JButton("Cancel");
		JLabel titleLabel = new JLabel("Publication title:");
		JLabel beginLabel = new JLabel("Begin Offset:");
		JLabel endLabel = new JLabel("End Offset:");
		JLabel authorLabel = new JLabel("Authors:");
		JLabel conferenceLabel = new JLabel("Authors:");
		
		JTextField titleTextField = new JTextField();
		JTextField beginTextField = new JTextField();
		JTextField endTextField = new JTextField();
		JTextField authorTextField = new JTextField();
		JTextField conferenceTextField = new JTextField();
		
		
		JCheckBox caseCheckBox = new JCheckBox("check");
		JCheckBox wholeCheckBox = new JCheckBox("whole");
		JCheckBox wrapCheckBox = new JCheckBox("wrap");
		JCheckBox backCheckBox = new JCheckBox("back");
		JTable table = new JTable();
		
		//Add Swing components to content panel
		Container c = getContentPane();
		
		
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setAutoCreateGaps(true);
		layout.setAutoCreateContainerGaps(true);

		layout.setHorizontalGroup(layout.createSequentialGroup()
		    .addComponent(titleLabel)
		    .addComponent(beginLabel)
		    .addComponent(endLabel)
		    .addComponent(authorLabel)
//		    .addComponent(conferenceLabel)
		    
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(titleTextField)
		        .addComponent(beginTextField)
		        .addComponent(endTextField)
		        .addComponent(authorTextField)
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(beginLabel)
		        .addComponent(beginTextField)))
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		        .addComponent(findButton)
		        .addComponent(cancelButton)));
		layout.linkSize(SwingConstants.HORIZONTAL, findButton, cancelButton);

		layout.setVerticalGroup(layout.createSequentialGroup()
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(titleLabel)
		        .addComponent(titleTextField)
		        .addComponent(findButton))
		        .addGroup(layout.createSequentialGroup()
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		        .addComponent(beginLabel)
		        .addComponent(beginTextField))
		        .addComponent(cancelButton))
		    .addGroup(layout.createSequentialGroup()
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
		    	.addComponent(endLabel)
		    	.addComponent(endTextField)))
		    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
			    	.addComponent(authorLabel)
			    	.addComponent(authorTextField)));
		 
		
//		c.add(textArea, GroupLayout.DEFAULT_SIZE);
//		c.add(button, BorderLayout.SOUTH);
//		c.add(new JLabel("Publication Search"));
		
		//Add behaviour
		findButton.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				textArea.append("Hello\n");
				
			}
		});
	}
	
	
	
}

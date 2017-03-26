package ch.ethz.globis.isk;
import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.*;

public class GUI extends JFrame{
	public static void main(String[] args)
    {
         myGuiProgramm();
        
    }
	
	public static void myGuiProgramm(){
    	//ZooDatabase database = new ZooDatabase("database");
    	//String filter = "...";
		//database.getWithFilter(ZooPerson.class, filter);
		
		JFrame frame = new MainFrame("Publications");
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        frame.setSize(800,500);
       // frame.add(new JLabel("Publication Search"));
 
        frame.setVisible(true);
	}
}


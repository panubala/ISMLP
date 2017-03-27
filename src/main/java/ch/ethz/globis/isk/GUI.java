package ch.ethz.globis.isk;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.jdo.JDOUserException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.*;

import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;

public class GUI extends JFrame{
	public static void main(String[] args)
    {
         myGuiProgramm();
        
    }
	
	public static void myGuiProgramm(){
    	ZooDatabase database = new ZooDatabase("database", false);
    	database.open();
    	
    	try {
    		// 1.)
    		ZooPublication publication = database.getPublicationById("some");
        	if (publication != null)
    			System.out.println(publication.getTitle());
        	else
        		System.out.println("Not found");
        	
    		// 2.)
    		Collection<ZooPublication> publications1 = database.getPublicationsByTitle("some", 0, 20);
        	if (!publications1.isEmpty()) {
        		for (ZooPublication p : publications1)
        			System.out.println(p.getTitle());
        	} else
        		System.out.println("Not found");
    		
    		// 3.)
    		Collection<ZooPublication> publications2 = database.getPublicationsByTitleOrdered("some", 0, 20);
        	if (!publications2.isEmpty()) {
        		for (ZooPublication p : publications2)
        			System.out.println(p.getTitle());
        	} else
        		System.out.println("Not found");
    		
    		// 4.)
//    		Collection<ZooPerson> authors = database.getCoAuthors("Michael G. Burke");
//        	if (!authors.isEmpty()) {
//        		for (ZooPerson a : authors)
//        			System.out.println(a.getName());
//        	} else
//        		System.out.println("Not found");
        	
        	// 7.)
        	Collection<Integer> publicationsFromTo = database.getPublicationsPerYear(1980, 1970);
        	if(!publicationsFromTo.isEmpty()){
        		int currYear = 1980;
        		for(Integer i: publicationsFromTo){
        			System.out.println(currYear + ": " + i.toString());
        			currYear++;
        		}
        	} else
        		System.out.println("Not found");
        	
        	
    	} catch (JDOUserException e) {
    		System.out.println(e.getMessage());
    		// TODO: Display error message in UI
    	}
    	
    	
    	
    	database.close();
		
//		JFrame frame = new MainFrame("Publications");
////		
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        
//        frame.setSize(800,500);
//       // frame.add(new JLabel("Publication Search"));
//        frame.setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
//                new MainFrame("Publications").setVisible(true);
            	JFrame frame = new MainFrame("Publications");
            	frame.setSize(580,560);
            	frame.setVisible(true);
            	
            }
        });

}}


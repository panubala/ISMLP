package ch.ethz.globis.isk.WholeSystem;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.ethz.globis.isk.xmldb.Database;
import ch.ethz.globis.isk.xmldb.MainFrame;
import ch.ethz.globis.isk.mongodb.*;
import ch.ethz.globis.isk.Zoo.*;

public class WGUI extends JFrame {
	public static void main(String[] args) {
    	//final ch.ethz.globis.isk.Zoo.Database zoodb = new ch.ethz.globis.isk.Zoo.Database("database", false);
		
		System.out.println("---### DEBUG: Creating MoongoDB object... ");
    	final ch.ethz.globis.isk.mongodb.Database mongodb = new ch.ethz.globis.isk.mongodb.Database("database");
    	
    	System.out.println("---### DEBUG: Creating xmldb object...");
    	final ch.ethz.globis.isk.xmldb.Database xmldb = new ch.ethz.globis.isk.xmldb.Database();
    //	zoodb.open();
    	
    	mongodb.open();
    	System.out.println("MongoDb connected");
    	xmldb.open();
    	System.out.println("XMLDb connected");
    	
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new WMainFrame("Database", mongodb, xmldb);
            	
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent event) {
			//			zoodb.close();
						mongodb.close();
						xmldb.close();
						System.exit(0);
					}
				});
				
            	frame.setSize(896,619);
            	frame.setVisible(true);
            }
        });
    }
}

package ch.ethz.globis.isk.xmldb;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class GUI extends JFrame {

	public static void main(String[] args) {
    	final Database db = new Database();
    	db.open();
    	
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new MainFrame("XML Database", db);
            	
				frame.addWindowListener(new WindowAdapter() {
					public void windowClosing(WindowEvent event) {
						db.close();
						System.exit(0);
					}
				});
				
            	frame.setSize(896,619);
            	frame.setVisible(true);
            }
        });
    }
}


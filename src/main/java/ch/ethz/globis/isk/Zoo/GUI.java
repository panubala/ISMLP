package ch.ethz.globis.isk.Zoo;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import ch.ethz.globis.isk.Zoo.MainFrame;

public class GUI extends JFrame{
	public static void main(String[] args) {
         myGuiProgramm();
    }
	
	public static void myGuiProgramm() {
		SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
            	JFrame frame = new MainFrame("Publications");
            	frame.setSize(890,560);
            	frame.setVisible(true);
            }
        });
	}
	
}


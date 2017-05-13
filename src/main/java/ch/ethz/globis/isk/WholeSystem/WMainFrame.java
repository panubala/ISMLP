package ch.ethz.globis.isk.WholeSystem;

import javax.swing.JFrame;



import ch.ethz.globis.isk.xmldb.Panel;

public class WMainFrame extends JFrame {
	

	public WMainFrame(String title,
			ch.ethz.globis.isk.mongodb.Database db2, 
			ch.ethz.globis.isk.xmldb.Database db3) {
			super(title);
			add(new WPanel(db2, db3));
			pack();
	}
	

}

package ch.ethz.globis.isk.xmldb;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	public MainFrame(String title, Database db) {
		super(title);
		add(new Panel(db));
		pack();
	}
}

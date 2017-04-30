package ch.ethz.globis.isk.xmldb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import ch.ethz.globis.isk.domain.mongodb.MongoDomainObject;
import ch.ethz.globis.isk.util.TableCellListener;
import ch.ethz.globis.isk.xmldb.api.BaseXClient.Query;

public class Table extends JTable {
	
	JFrame frame;
	DefaultTableModel model;
	JScrollPane scrollPane;
	
	Database db;
	String xml;
	Query query;
	String[] tagNames;

	JTextField searchTextField;
	JLabel idLabel;
	JTextField idTextField;
	
	public <T extends MongoDomainObject> Table(
			final Database db,
			final String xml,
			final String queryFileName,
			final String title,
			final String[] columnNames,
			final String[] tagNames,
			final boolean allowModifications) {
		
		this.db = db;
		this.xml = xml;
		this.query = queryFileName != null ? db.executeFile(queryFileName) : null;
		this.tagNames = tagNames;
		
		frame = new JFrame(title);
        
		if (allowModifications) {
			createTopPanel();
			createBottomPanel();
		}

        model = new DefaultTableModel() {        	
			@Override
				public boolean isCellEditable(int row, int column) {
				return allowModifications;
			}
		};
        model.setColumnIdentifiers(columnNames);
        
        setModel(model);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setFillsViewportHeight(true);
        
        scrollPane = new JScrollPane(this);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        
        frame.add(scrollPane);
        frame.setSize(1280, 720);
        
        // Listener for cell edit
        if (allowModifications) {
	        TableCellListener tcl = new TableCellListener(this, new AbstractAction() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	    			TableCellListener tcl = (TableCellListener) e.getSource();
	    			
	        		if (tcl.getColumn() == 0) {
	        			model.setValueAt(tcl.getOldValue(), tcl.getRow(), tcl.getColumn());
	        			return;
	        		}
	        		
	    			//String tagName = tagNames[tcl.getColumn()];
	    			//Object id = model.getValueAt(tcl.getRow(), 0);
	    			//String value = (String) tcl.getNewValue();
	            }
	        });
        }
        
        // Delete action
        if (allowModifications) {
	        getInputMap().put(KeyStroke.getKeyStroke("DELETE"), "delete");
	        getActionMap().put("delete", new AbstractAction() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int[] rows = getSelectedRows();
					//Object id;
					for (int i = rows.length - 1; i >= 0; i--) {
						//id = model.getValueAt(rows[i], 0);
						model.removeRow(rows[i]);
					}
				}
			});
        }
        
        if (xml != null) {
        	insert(db.readFile(xml));
        } else {
			try {
				if (query.more())
					insert(query.next());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        frame.setVisible(true);
	}
	
	private void createTopPanel() {
		JPanel topPanel = new JPanel(new FlowLayout());
		
		searchTextField = new JTextField(50);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent event) {
				String input =
					  "let $root := doc('" + xml + " ')/root "
					+ "return <root>{ "
					+ "		for $i in $root//* "
					+ "		return "
					+ "		if (some $el in $i//* satisfies (contains($el, '" + searchTextField.getText() + "'))) "
					+  		"then $i "
					+ "		else () "
					+ "}</root> ";
				deleteAllRows();
				try {
					Query query = db.execute(input);
					if (query.more())
						insert(query.next());
				} catch (IOException e) {
					e.printStackTrace();
				}
            }
        });
		
		topPanel.add(searchTextField);
		topPanel.add(searchButton);
		
		frame.add(topPanel, BorderLayout.PAGE_START);
	}
	
	private void createBottomPanel() {
		JPanel bottomPanel = new JPanel(new FlowLayout());
		
		idLabel = new JLabel("ID");
		idTextField = new JTextField(50);
		
		JButton insertButton = new JButton("Insert New Row");
		insertButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent event) {
				String id = idTextField.getText();

				if (!id.isEmpty()) {
					
				}
				
				idLabel.setText("ID");
				
				JScrollBar vertical = scrollPane.getVerticalScrollBar();
				vertical.setValue(0);
            }
        });

		bottomPanel.add(idLabel);
		bottomPanel.add(idTextField);
		bottomPanel.add(insertButton);
		
		frame.add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	private void deleteAllRows() {
		while (model.getRowCount() > 0)
	    	model.removeRow(0);
	}
	
    // Insert all documents as rows
	private void insert(String xml) {
		try {
    		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    		DocumentBuilder builder = factory.newDocumentBuilder();
    		
    		Document document = builder.parse(new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8"))));
    		document.getDocumentElement().normalize();
    		Node root = document.getDocumentElement();
    		
    		Node rowNode = root.getFirstChild();
    		while (rowNode != null) {
    			if (rowNode.getNodeType() == Node.ELEMENT_NODE) {
    	        	Vector<Object> row = new Vector<>();
    	        	
    	    		Node fieldNode = rowNode.getFirstChild();
    	    		while (fieldNode != null) {
    	    			if (fieldNode.getNodeType() == Node.ELEMENT_NODE
    	    					&& Arrays.asList(tagNames).contains(fieldNode.getNodeName())) {
	    	        		row.add(fieldNode.getTextContent());
    	    			}
    	    			fieldNode = fieldNode.getNextSibling();
    	    		}
	    			
    	        	model.addRow(row);
    			}
    			rowNode = rowNode.getNextSibling();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

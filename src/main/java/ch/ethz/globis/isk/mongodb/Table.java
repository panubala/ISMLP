package ch.ethz.globis.isk.mongodb;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;

import ch.ethz.globis.isk.domain.mongodb.MongoDomainObject;
import ch.ethz.globis.isk.util.TableCellListener;

public class Table extends JTable {
	
	JFrame frame;
	DefaultTableModel model;
	JScrollPane scrollPane;
	
	MongoCollection<Document> collection;
	String[] fieldNames;

	JTextField searchTextField;
	JLabel idLabel;
	JTextField idTextField;
	
	
	public <T extends MongoDomainObject> Table(final MongoCollection<Document> collection, final MongoCursor<Document> cursor, final String title, final String[] columnNames, final String[] fieldNames, final boolean allowModifications) {
		this.collection = collection;
		this.fieldNames = fieldNames;
		
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
	        		
	    			String fieldName = fieldNames[tcl.getColumn()];
	    			Object id = model.getValueAt(tcl.getRow(), 0);
	    			String value = (String) tcl.getNewValue();
	    			if (!(value.charAt(0) == '[' && value.charAt(value.length()-1) == ']'))
	    				collection.findOneAndUpdate(Filters.eq(fieldNames[0], id), Updates.set(fieldName, value));
	    			else {
	    				List<String> values = Arrays.asList(value.substring(1, value.length()-1).split("\\s*,\\s*"));
						collection.findOneAndUpdate(Filters.eq(fieldNames[0], id), Updates.set(fieldName, values));
	    			}
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
					Object id;
					for (int i = rows.length - 1; i >= 0; i--) {
						id = model.getValueAt(rows[i], 0);
						model.removeRow(rows[i]);
						collection.findOneAndDelete(Filters.eq(fieldNames[0], id));
					}
				}
			});
        }
        
        insert(cursor);
        
        frame.setVisible(true);
	}
	
	private void createTopPanel() {
		JPanel topPanel = new JPanel(new FlowLayout());
		
		searchTextField = new JTextField(50);
		
		JButton searchButton = new JButton("Search");
		searchButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent event) {
				deleteAllRows();
				StringBuilder sb = new StringBuilder();
				for (String fieldName : fieldNames) {
					sb.append("(this['").append(fieldName).append("'] != undefined && ")
							.append("this['").append(fieldName).append("']")
							.append(".toString().includes('")
							.append(searchTextField.getText())
							.append("'))").append(" || ");
				}
				
				String javaScriptExpression = sb.substring(0, sb.length()-4);
				
				MongoCursor<Document> cursor = collection.find(Filters.where(javaScriptExpression)).iterator();
				deleteAllRows();
				insert(cursor);
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
				Document document = new Document();
				String id = idTextField.getText();

				if (!id.isEmpty()) {
					if (collection.find(Filters.eq("_id", id)).iterator().hasNext()) {
						idLabel.setText("ID (Error: non-unique ID)");
						return;
					}
					document.append("_id", id);
				}
				
				idLabel.setText("ID");
				
				collection.insertOne(document);
				model.insertRow(0, new Object[] { document.get("_id") });
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
	private void insert(MongoCursor<Document> cursor) {
		Document document;
        Vector<Object> row;
        while (cursor.hasNext()) {
        	document = cursor.next();
        	row = new Vector<>();
        	for (String fieldName : fieldNames)
        		row.add(document.get(fieldName));
        	model.addRow(row);
        }
	}
}

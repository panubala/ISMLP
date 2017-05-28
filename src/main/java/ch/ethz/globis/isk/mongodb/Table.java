package ch.ethz.globis.isk.mongodb;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Iterator;
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
	String title;
	String[] fieldNames;

	JTextField searchTextField;
	JLabel idLabel;
	JTextField idTextField;
	JLabel errorLabel;
	
	public Table(String title, String[] columnNames) {
		
	}
	
	public <T extends MongoDomainObject> Table(final MongoCollection<Document> collection, final Iterator<Document> cursor, final String title, final String[] columnNames, final String[] fieldNames, final boolean allowModifications) {
		
		
		this.collection = collection;
		this.title = title;
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
        	new TableCellListener(this, new AbstractAction() {
	        	@Override
	            public void actionPerformed(ActionEvent e) {
	    			TableCellListener tcl = (TableCellListener) e.getSource();
	    			
	        		if (tcl.getColumn() == 0) {
	        			model.setValueAt(tcl.getOldValue(), tcl.getRow(), tcl.getColumn());
						errorLabel.setText("Error: can't edit ID");
	        			return;
	        		}
	        		
	    			String fieldName = fieldNames[tcl.getColumn()];
	    			Object id = model.getValueAt(tcl.getRow(), 0);
	    			String value = (String) tcl.getNewValue();
	    			
	    			String errorMessage = validate(id, fieldName, value, tcl.getOldValue().toString(), tcl.getRow());
	    			if (errorMessage != null) {
	        			model.setValueAt(tcl.getOldValue(), tcl.getRow(), tcl.getColumn());
						errorLabel.setText("Error: " + errorMessage);
	        			return;
	    			}
	    			
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
		errorLabel = new JLabel("");
		
		JButton insertButton = new JButton("Insert New Row");
		insertButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent event) {
				Document document = new Document();
				String id = idTextField.getText();

				if (!id.isEmpty()) {
					if (collection.find(Filters.eq("_id", id)).iterator().hasNext()) {
						errorLabel.setText("Error: non-unique ID");
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
		bottomPanel.add(errorLabel);
		
		frame.add(bottomPanel, BorderLayout.PAGE_END);
	}
	
	private void deleteAllRows() {
		while (model.getRowCount() > 0)
	    	model.removeRow(0);
	}
	
    // Insert all documents as rows
	private void insert(Iterator<Document> cursor) {
		
		//--- Debug code-----
		
		
		if(null == cursor){
			System.out.println("---### ERROR: In MongoDB: In Table: In insert() cursor == null");
		} else{
			System.out.println("---### DEBUG: In MongoDB: Size of collection: " + collection.count());
		}
			
		//-------------------
		
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
	
	private String validate(Object id, String fieldName, String value, String oldValue, int row) {
		String errorMessage;
		
		// 2. The attribute ’Publication.title’ must be of type string and must not be null.
		if (fieldName.equals("title")) {
			errorMessage = "title must be string and non-null";
			if (value != null && !value.equals("")) {
				return null;
			} else {
				return errorMessage;
			}
		}
		
		// 3. The attribute ’Publication.year’ must be of type integer and between the values 1901 and CurrentYear+1.
		if (fieldName.equals("year")) {
			errorMessage = "year must be integer and between 1901 and currentYear+1";
			try {
				int year = Integer.parseInt(value);
				if (1900 < year && year <= Calendar.getInstance().get(Calendar.YEAR) + 1) {
					return null;
				} else {
					return errorMessage;
				}
			} catch (Exception e) {
				return errorMessage;
			}
		}
		
		// 4. There exists at least one author for each publication (the ’Publication.authors’ list cannot be null or empty).
		if (fieldName.equals("authors")) {
			errorMessage = "authors must contain at least one author";
			if (value == null || value.equals("")) {
				return errorMessage;
			}
			List<String> values = Arrays.asList(value.substring(1, value.length()-1).split("\\s*,\\s*"));
			if (!(values.isEmpty() || (values.size() == 1 && values.get(0).equals("")))) {
				return null;
			} else {
				return errorMessage;
			}
		}
		
		// 5. If the attribute ’Proceedings.isbn’ is updated the message ‘ISBN updated, old value was <old ISBN>’ should
		// be automatically added to the attribute ’Proceedings.note’ whereas <old ISBN> must be replaced by the
		// concrete old ISBN.
		if (fieldName.equals("isbn")) {
			String message = "ISBN updated, old value was " + oldValue;
			model.setValueAt(message, row, 9);
			collection.findOneAndUpdate(Filters.eq(fieldNames[0], id), Updates.set("note", value));
			return null;
		}
		
		// 6. The attribute ’InProceedings.pages’ must match to one of the following three patterns: ‘<Integer>’ (e.g.
		// ‘750’), ‘<Integer>-<Integer>’ (e.g. ‘750-757’) or null.
		if (fieldName.equals("pages")) {
			if (value == null || value.equals("")) {
				return null;
			}
			errorMessage = "pages doesn't match pattern '<Integer>', '<Integer>-<Integer>' or null";
			String[] patterns = new String[] { "(\\d)+", "(\\d)+-(\\d)+" };
			for (String pattern : patterns) {
				if (value.matches(pattern)) {
					return null;
				}
			}
			return errorMessage;
		}
		
		// 7. The attribute ’InProceedings.proceedings’ must not be null and must reference an existing ’Proceedings’
		if (fieldName.equals("proceedings")) {
			errorMessage = "proceedings has to be non-null and must be reference to existing proceedings";
			if (value == null || value.equals("")) {
				return errorMessage;
			}
			if (collection.find(Filters.exists("proceedings")).filter(Filters.eq("_id", value)).iterator().hasNext()) {
				return null;
			}
			return errorMessage;
		}
		
		// 8. Changes to a ’Proceedings’ are automatically reflected/propagated in the attribute ’Proceedings’ of the entity
		// ’InProceedings’. I.e., if you store multiple copies of Proceedings data in your DBMS (such as MongoDB),
		// ensure that all copies are updated if one copy is modified.
		// DONE (there is only ever one copy of each proceeding)
		
		// 9. The attribute ’InProceedings.note’ must be a string of the set { Draft, Submitted, Accepted, Published }
		if (title.equals("Inproceedings") && fieldName.equals("note")) {
			errorMessage = "note must be a string of the set { Draft, Submitted, Accepted, Published }";
			if (value == null
					|| (!value.equals("Draft")
					&& !value.equals("Submitted")
					&& !value.equals("Accepted")
					&& !value.equals("Published"))) {
				return errorMessage;
			}
			return null;
		}
		
		return null;
	}
}

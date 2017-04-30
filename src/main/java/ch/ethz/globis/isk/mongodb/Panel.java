package ch.ethz.globis.isk.mongodb;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.bson.Document;
import org.bson.conversions.Bson;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.mongodb.QueryBuilder;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;
import com.mongodb.operation.GroupOperation;

public class Panel extends JPanel {
    
	private Database db;
	private Color bgLight = new Color(97, 212, 195);
	private Color bgDark = new Color(36, 47, 65);
	private Color fontColor = Color.WHITE;
	private Font smallFont = new Font("Century Gothic", 0, 10);
	private Font mediumFont = new Font("Century Gothic", 0, 26);
	private Font largeFont = new Font("Century Gothic", 1, 30);
	private String invalidInput = "Invalid Input";
	
	private JButton conferenceEditionsButton = new JButton();
	private JButton inProceedingsButton = new JButton();
	private JButton proceedingsButton = new JButton();
	private JButton personsButton = new JButton();
	private JButton publishersButton = new JButton();
	private JButton seriesButton = new JButton();
	private JButton publicationsButton = new JButton();
	private JButton conferencesButton = new JButton();
	private JButton query1Button = new JButton();  
	private JButton query2Button = new JButton(); 
	private JButton query3Button = new JButton();
	private JButton query4Button = new JButton();
	private JButton query5Button = new JButton();
	private JButton query6Button = new JButton();
	private JButton query7Button = new JButton();
	private JButton query8Button = new JButton();
	private JButton query9Button = new JButton();
	private JButton query10Button = new JButton();
	private JButton query11Button = new JButton();
	private JButton query12Button = new JButton();
	private JButton query13Button = new JButton();
	private JButton query14Button = new JButton();
	private JLabel titleLabel = new JLabel();
	private JLabel resultLabel = new JLabel();
	private JPanel leftPanel = new JPanel();
	private JPanel rightPanel = new JPanel();
	private JTextField textField1 = new JTextField();
	private JTextField textField2 = new JTextField();
	private JTextField textField3 = new JTextField();
	
    public Panel(Database db) {
    	this.db = db;
    	
        setBackground(bgLight);
        setLayout(new AbsoluteLayout());

        leftPanel.setLayout(new AbsoluteLayout());
        leftPanel.setBackground(bgDark);
        add(leftPanel, new AbsoluteConstraints(0, 0, 190, 580));
        
        rightPanel.setLayout(new AbsoluteLayout());
        rightPanel.setBackground(bgDark);
        add(rightPanel, new AbsoluteConstraints(540, 0, 340, 580));
        
        
        titleLabel.setFont(largeFont);
        titleLabel.setForeground(fontColor);
        titleLabel.setText("Domain Objects");
        

        publicationsButton.setText("Publications");
        publicationsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                publicationsButtonActionPerformed(evt);
            }
        });

        proceedingsButton.setText("Proceedings");
        proceedingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                proceedingsButtonActionPerformed(evt);
            }
        });

        inProceedingsButton.setText("Inproceedings");
        inProceedingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                inProceedingsButtonActionPerformed(evt);
            }
        });

        conferencesButton.setText("Conferences");
        conferencesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                conferencesButtonActionPerformed(evt);
            }
        });
        
        conferenceEditionsButton.setText("Conference Editions");
        conferenceEditionsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                conferenceEditionsButtonActionPerformed(evt);
            }
        });

        personsButton.setText("Authors/Editors");
        personsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                personsButtonActionPerformed(evt);
            }
        });

        publishersButton.setText("Publishers");
        publishersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                publishersButtonActionPerformed(evt);
            }
        });

        seriesButton.setText("Series");
        seriesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                seriesButtonActionPerformed(evt);
            }
        });
        
        add(titleLabel, 				new AbsoluteConstraints(230, 45, 300, 47));
        add(publicationsButton, 		new AbsoluteConstraints(250, 120, 230, -1));
        add(proceedingsButton, 			new AbsoluteConstraints(250, 150, 230, -1));
        add(inProceedingsButton, 		new AbsoluteConstraints(250, 180, 230, -1));
        add(conferencesButton,			new AbsoluteConstraints(250, 210, 230, -1));
        add(conferenceEditionsButton, 	new AbsoluteConstraints(250, 240, 230, -1));
        add(personsButton, 				new AbsoluteConstraints(250, 270, 230, -1));
        add(publishersButton, 			new AbsoluteConstraints(250, 300, 230, -1));
        add(seriesButton, 				new AbsoluteConstraints(250, 330, 230, -1));
        
        
        textField1.setFont(smallFont);
        textField2.setFont(smallFont);
        textField3.setFont(smallFont);
        
        
        query1Button.setText("1. Publication by id");
        query1Button.setFont(smallFont);
        query1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query1ButtonActionPerformed(evt);
            }
        });

        query2Button.setText("2. Publications by title, begin-, end-offset");
        query2Button.setFont(smallFont);
        query2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query2ButtonActionPerformed(evt);
            }
        });

        query3Button.setText("3. Query 2 ordered by title");
        query3Button.setFont(smallFont);
        query3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query3ButtonActionPerformed(evt);
            }
        });

        query4Button.setText("4. Co-authors of person");
        query4Button.setFont(smallFont);
        query4Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query4ButtonActionPerformed(evt);
            }
        });

        query5Button.setText("5. Shortest path between two authors");
        query5Button.setFont(smallFont);
        query5Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query5ButtonActionPerformed(evt);
            }
        });
		
        query6Button.setText("6. Avg number of publications");
        query6Button.setFont(smallFont);
        query6Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query6ButtonActionPerformed(evt);
            }
        });

        query7Button.setText("7. Publications count per year in interval");
        query7Button.setFont(smallFont);
        query7Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query7ButtonActionPerformed(evt);
            }
        });
        
        query8Button.setText("8. Publications count of conference");
        query8Button.setFont(smallFont);
        query8Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query8ButtonActionPerformed(evt);
            }
        });

        query9Button.setText("9. Authors/Editors count of conference");
        query9Button.setFont(smallFont);
        query9Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query9ButtonActionPerformed(evt);
            }
        });
		
        query10Button.setText("10. Authors of conference");
        query10Button.setFont(smallFont);
        query10Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query10ButtonActionPerformed(evt);
            }
        });

        query11Button.setText("11. Publications of conference");
        query11Button.setFont(smallFont);
        query11Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query11ButtonActionPerformed(evt);
            }
        });

        query12Button.setText("12. Authors that are also the editors");
        query12Button.setFont(smallFont);
        query12Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query12ButtonActionPerformed(evt);
            }
        });

        query13Button.setText("13. Publications where author listed last");
        query13Button.setFont(smallFont);
        query13Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query13ButtonActionPerformed(evt);
            }
        });

        query14Button.setText("14. Publishers of proceedings whose authors appear in any inproceedings in range of years");
        query14Button.setFont(smallFont);
        query14Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query14ButtonActionPerformed(evt);
            }
        });
        
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(mediumFont);
        resultLabel.setForeground(fontColor);
        
        rightPanel.add(textField1, 		new AbsoluteConstraints(60, 30, 230, -1));
        rightPanel.add(textField2, 		new AbsoluteConstraints(60, 60, 230, -1));
        rightPanel.add(textField3, 		new AbsoluteConstraints(60, 90, 230, -1));
        rightPanel.add(query1Button, 	new AbsoluteConstraints(60, 120, 230, -1));
        rightPanel.add(query2Button, 	new AbsoluteConstraints(60, 150, 230, -1));
        rightPanel.add(query3Button, 	new AbsoluteConstraints(60, 180, 230, -1));
        rightPanel.add(query4Button, 	new AbsoluteConstraints(60, 210, 230, -1));
        rightPanel.add(query5Button, 	new AbsoluteConstraints(60, 240, 230, -1));
        rightPanel.add(query6Button, 	new AbsoluteConstraints(60, 270, 230, -1));
        rightPanel.add(query7Button, 	new AbsoluteConstraints(60, 300, 230, -1));
        rightPanel.add(query8Button, 	new AbsoluteConstraints(60, 330, 230, -1));
        rightPanel.add(query9Button, 	new AbsoluteConstraints(60, 360, 230, -1));
        rightPanel.add(query10Button, 	new AbsoluteConstraints(60, 390, 230, -1));
        rightPanel.add(query11Button, 	new AbsoluteConstraints(60, 420, 230, -1));
        rightPanel.add(query12Button, 	new AbsoluteConstraints(60, 450, 230, -1));
        rightPanel.add(query13Button, 	new AbsoluteConstraints(60, 480, 230, -1));
        rightPanel.add(query14Button, 	new AbsoluteConstraints(60, 510, 230, -1));
        rightPanel.add(resultLabel, 	new AbsoluteConstraints(60, 540, 230, -1));
    }

    private void publicationsButtonActionPerformed(ActionEvent evt) {
    	new Table(db.publications,
				db.publications.find().iterator(),
    			"Publications",
    			new String[] { "ID", "Title" },
    			new String[] { "_id", "title" },
    			true);
    }

    private void proceedingsButtonActionPerformed(ActionEvent evt) {
    	new Table(db.publications,
				db.publications.find(Filters.exists("publications")).iterator(),
    			"Proceedings",
    			new String[] { "ID", "Title", "Year", "Publisher", "ISBN", "Editors", "Series", "Conference Edition", "Publications" },
    			new String[] { "_id", "title", "year", "publisher", "isbn", "editors", "series", "conferenceEdition", "publications" },
    			true);
    }
    
    private void inProceedingsButtonActionPerformed(ActionEvent evt) {
    	new Table(db.publications,
				db.publications.find(Filters.exists("proceedings")).iterator(),
    			"Inproceedings",
    			new String[] { "ID", "Title", "Authors", "Proceedings" },
    			new String[] { "_id", "title", "authors", "proceedings" },
    			true);
    }
    
    private void conferenceEditionsButtonActionPerformed(ActionEvent evt) {
    	new Table(db.conferenceEditions,
				db.conferenceEditions.find().iterator(),
    			"Conference Editions",
    			new String[] { "ID", "Conference", "Year", "Proceedings" },
    			new String[] { "_id", "conference", "year", "proceedings" },
    			true);
    }

    private void conferencesButtonActionPerformed(ActionEvent evt) {
    	new Table(db.conferences,
				db.conferences.find().iterator(),
    			"Conferences",
    			new String[] { "ID", "Name", "Conference Edition" },
    			new String[] { "_id", "name", "editions" },
    			true);
    }
    
    private void personsButtonActionPerformed(ActionEvent evt) {
    	// Search persons by name
    	
    	/*String name = this.jTextField6.getText();
    	String expr = "(this['name'] != undefined && this['name'].toString().includes('" + name + "'))";*/
    	
    	new Table(db.persons,
				db.persons.find().iterator(),
    			"Authors/Editors",
    			new String[] { "ID", "Name", "Authored Publications", "Edited Publications" },
    			new String[] { "_id", "name", "authoredPublications", "editedPublications" },
    			true);
    }

    private void publishersButtonActionPerformed(ActionEvent evt) {
    	new Table(db.publishers,
				db.publishers.find().iterator(),
    			"Publishers",
    			new String[] { "ID", "Name", "Publications" },
    			new String[] { "_id", "name", "publications" },
    			true);
    }
    
    private void seriesButtonActionPerformed(ActionEvent evt) {
    	new Table(db.series,
				db.series.find().iterator(),
    			"Series",
    			new String[] { "ID", "Name", "Publications" },
    			new String[] { "_id", "name", "publications" },
    			true);
    }

    private void query1ButtonActionPerformed(ActionEvent evt) {
    	String id = textField1.getText();
    	
    	Iterator<Document> iterator = db.publications.find(Filters.eq("_id", id)).iterator();
    	new Table(db.publications,
				iterator,
    			"Publication by ID",
    			new String[] { "ID", "Title" },
    			new String[] { "_id", "title" },
    			false);
    }

    private void query2ButtonActionPerformed(ActionEvent evt) {
    	String title = textField1.getText();
    	int beginOffset;
    	int endOffset;
    	
    	try {
        	beginOffset = Integer.parseInt(textField2.getText());
        	endOffset = Integer.parseInt(textField3.getText());
    	} catch (Exception e) {
    		resultLabel.setText(invalidInput);
    		return;
    	}
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("title",
    						new Document("$regex", ".*" + title + ".*i")
    					)
					),
					new Document("$skip", beginOffset),
					new Document("$limit", endOffset - beginOffset)
    			)).iterator();
    	
    	new Table(db.publications,
				iterator,
    			"Publications by title, begin-offset, end-offset",
    			new String[] { "ID", "Title" },
    			new String[] { "_id", "title" },
    			false);
    }

    private void query3ButtonActionPerformed(ActionEvent evt) {
    	String title = textField1.getText();
    	int beginOffset;
    	int endOffset;
    	
    	try {
        	beginOffset = Integer.parseInt(textField2.getText());
        	endOffset = Integer.parseInt(textField3.getText());
    	} catch (Exception e) {
    		resultLabel.setText(invalidInput);
    		return;
    	}
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("title",
    						new Document("$regex", ".*" + title + ".*i")
    					)
					),
					new Document("$skip", beginOffset),
					new Document("$limit", endOffset - beginOffset),
					new Document("$sort", new Document("title", 1))
    			)).iterator();
    	
    	new Table(db.publications,
				iterator,
    			"Publications by title, begin-offset, end-offset ordered by title",
    			new String[] { "ID", "Title" },
    			new String[] { "_id", "title" },
    			false);
    }

    private void query4ButtonActionPerformed(ActionEvent evt) {
    	//String name = "Kevin D. Ashley";
    	String name = textField1.getText();
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
    				new Document("$match",
						new Document("authors", name)
					),
    				new Document("$project",
    					new Document("author", name)
    					.append("coAuthors",
		    				new Document("$filter",
								new Document("input", "$authors")
			    				.append("as", "author")
			    				.append("cond",
			    					new Document("$ne", Arrays.asList(
			    						"$$author", name
		    						))
								)
		    				)
	    				)
					),
    				new Document("$unwind", "$coAuthors"),
    				new Document("$group",
    					new Document("_id", "$author")
    					.append("coAuthors",
							new Document("$addToSet", "$coAuthors")
						)
					)
    			)).iterator();
    	
    	new Table(db.publications,
				iterator,
    			"Co-Authors",
    			new String[] { "Author", "Co-Authors" },
    			new String[] { "_id", "coAuthors" },
    			false);
    }

    private void query5ButtonActionPerformed(ActionEvent evt) {
    	String author1 = textField1.getText();
    	String author2 = textField2.getText();
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
					new Document("$match",
						new Document("$or", Arrays.asList(
							new Document("authors", author1),
							new Document("authors", author2)
						))
					),
					/*new Document("$lookup",
						new Document("from", "persons")
						.append("localField", "authors")
						.append("foreignField", "name")
						.append("as", "author")
					),*/
					new Document("$unwind", "$authors"),
    						
					new Document("$graphLookup",
						new Document("from", "$$ROOT")
						.append("startWith", "$authors")
						.append("connectFromField", "authors")
						.append("connectToField", "authors")
						.append("as", "connectedAuthors")
					)
    			)).iterator();
    	
    	new Table(db.publications,
				iterator,
    			"Co-Authors",
    			new String[] { "Author", "connectedAuthors", "authors", "author" },
    			new String[] { "_id", "connectedAuthors", "authors", "author" },
    			false);
    }

    private void query6ButtonActionPerformed(ActionEvent evt) {
    	
//    	dbpublications.aggregate([
//    	                          {$project: {_id: 1, title: 1, authors: 1,
//    	                        	  total: {$size: {"$ifNull": ["$authors", []]}}}},
//    	                          {$group: {_id: "Average Number", count: {$avg: "$total"}}}
//    	    ])
    	
    	AggregateIterable<Document> query = db.publications.aggregate(Arrays.asList(
    			new Document("$project", 
    					new Document("_id", 1)
    					.append("title", 1)
    					.append("authors", 1)
    					.append("total",
    							new Document("$size",
    									new Document("$ifNull",
    											Arrays.asList("$authors", Arrays.asList())
    											)
    									)
    							)
    					),
    			new Document("$group", 
    					new Document("_id", "average")
    					.append("count",
    							new Document("$avg", "$total")))
    			));
    	
    	Document firstRes = query.first();
    	
    	if(firstRes.containsKey("count")){
    		this.resultLabel.setText(Double.toString(firstRes.getDouble("count")));
    	}
    	

    	
    	new Table(db.publications,
    			query.iterator(),
    			"Global avg",
    			new String[] {"ID", "Count"},
    			new String[] {"_id", "count"},
    			true);
    	
    }

    private void query7ButtonActionPerformed(ActionEvent evt) {
    	// Number of publications per year
    	String str1  = textField1.getText();
    	String str2 = textField2.getText();
    	
    	int yearFrom;
    	int yearTo;
    	
    	try {
	    	yearFrom = Integer.parseInt(str1);
	    	yearTo   = Integer.parseInt(str2);
    	} catch (Exception e) {
    		resultLabel.setText(invalidInput);
    		return;
    	}
    	
    	if(yearFrom > yearTo){
    		int temp = yearTo;
    		yearTo = yearFrom;
    		yearFrom = temp;
    	}
    	
    	/*String expr = "(this['year'] != undefined && this['year'] >= "
    					+ yearFrom
    					+ " && this['year'] <= "
    					+ yearTo
    					+ ")";
    	    	
		long result = db.publications.count(Filters.where(expr)); 
		resultLabel.setText(Long.toString(result));
    	
    	new Table(db.publications, 
    			db.publications.find(Filters.where(expr)).iterator(), 
    			"Publications between " + yearFrom + " and " + yearTo,
    			new String[] {"Title", "Year"}, 
    			new String[] {"title", "year"},
    			false);*/
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
					new Document("$project",
						new Document("_id", "$_id")
						.append("year", "$year")
						.append("gte",
							new Document("$gte", Arrays.asList(
								"$year", yearFrom
							))
						)
						.append("lte",
							new Document("$lte", Arrays.asList(
								"$year", yearTo
							))
						)
					),
					new Document("$match",
						new Document("$and", Arrays.asList(
							new Document("gte", true),
							new Document("lte", true)
						))
					),
					new Document("$group",
						new Document("_id", "$_id")
						.append("count",
							new Document("$sum", 1)
						)
						.append("year",
							new Document("$first", "$year")
						)
					),
					new Document("$group",
						new Document("_id", "$year")
						.append("count",
							new Document("$sum", "$count")
						)
					),
					new Document("$sort", new Document("_id", 1))
    			)).iterator();
    	
    	new Table(db.publications, 
    			iterator,
    			"Publications between " + yearFrom + " and " + yearTo,
    			new String[] {"Year", "Number of Publications"},
    			new String[] {"_id", "count"},
    			false);
    }

    private void query8ButtonActionPerformed(ActionEvent evt) {
    	String conferenceName = textField1.getText();
    	
    	Iterator<Document> iterator =
    			db.conferences.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("count",
							new Document("$sum",
								new Document("$size", "$proceedings.publications")
							)
						)
					)
    			)).iterator();
    	
    	new Table(db.conferences, 
    			iterator,
    			"Number of Publications of conference " + conferenceName,
    			new String[] {"Name", "Number of publications" },
    			new String[] {"name", "count" },
    			false);
    }

    private void query9ButtonActionPerformed(ActionEvent evt) {
    	String conferenceName = textField1.getText();
    	
    	Iterator<Document> iterator =
    			db.conferences.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "proceedings.publications")
						.append("foreignField", "_id")
						.append("as", "inProceedings")
					),
					new Document("$unwind", "$inProceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("editors",
							new Document("$addToSet", "$proceedings.editors")
						)
						.append("authors",
							new Document("$addToSet", "$inProceedings.authors")
						)
					),
					
					new Document("$project",
						new Document("name", "$name")
						.append("authors",
							new Document("$setUnion", Arrays.asList(
								"$authors", "$editors"
							))
						)
					),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("count",
							new Document("$sum",
								new Document("$size", "$authors")
							)
						)
					)
    			)).iterator();
    	
    	new Table(db.conferences, 
    			iterator,
    			"Number of Authors/Editors of conference " + conferenceName,
    			new String[] {"Name", "Number of authors/editors" },
    			new String[] {"name", "count" },
    			false);
    }

    private void query10ButtonActionPerformed(ActionEvent evt) {
    	String conferenceName = textField1.getText();
	
		Iterator<Document> iterator =
				db.conferences.aggregate(Arrays.asList(
					new Document("$match",
						new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "proceedings.publications")
						.append("foreignField", "_id")
						.append("as", "inProceedings")
					),
					new Document("$unwind", "$inProceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("authors",
							new Document("$addToSet", "$proceedings.editors")
						)
						.append("authors",
							new Document("$addToSet", "$inProceedings.authors")
						)
					),
					new Document("$unwind", "$authors"),
					new Document("$unwind", "$authors"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("authors",
							new Document("$addToSet", "$authors")
						)
					)
				)).iterator();
	
		new Table(db.conferences, 
				iterator,
				"Authors/Editors of conference " + conferenceName,
				new String[] {"Name", "Authors/Editors" },
				new String[] {"name", "authors" },
				false);
    }

    private void query11ButtonActionPerformed(ActionEvent evt) {
    	String conferenceName = textField1.getText();
    	
    	Iterator<Document> iterator =
    			db.conferences.aggregate(Arrays.asList(
    				new Document("$match",
    					new Document("name", conferenceName)
					),
					new Document("$lookup",
						new Document("from", "conferenceEditions")
						.append("localField", "editions")
						.append("foreignField", "_id")
						.append("as", "editions")
					),
					new Document("$unwind", "$editions"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "editions.proceedings")
						.append("foreignField", "_id")
						.append("as", "proceedings")
					),
					new Document("$unwind", "$proceedings"),
					
					new Document("$lookup",
						new Document("from", "publications")
						.append("localField", "proceedings.publications")
						.append("foreignField", "_id")
						.append("as", "inProceedings")
					),
					new Document("$unwind", "$inProceedings"),
					
					new Document("$group",
						new Document("_id", null)
						.append("name",
							new Document("$first", "$name")
						)
						.append("inProceedings",
							new Document("$addToSet", "$inProceedings._id")
						)
						.append("proceedings",
							new Document("$addToSet", "$proceedings._id")
						)
					),
					
					new Document("$project",
						new Document("name", "$name")
						.append("publications",
							new Document("$setUnion", Arrays.asList(
								"$inProceedings", "$proceedings"
							))
						)
					)
    			)).iterator();
    	
    	new Table(db.conferences, 
    			iterator,
    			"Publications of conference " + conferenceName,
    			new String[] {"Name", "Publications" },
    			new String[] {"name", "publications" },
    			false);
    }

    private void query12ButtonActionPerformed(ActionEvent evt) {
    	
    	
    	// JavaScript command: Always returns empty arrays as intersection. (???)
//    	db.getCollection('persons').aggregate([
//    	                                       {$match: {"authoredPublications": {$exists: true}}},
//    	                                       {$match: {"editedPublications": {$exists: true}}},
//    	                                       {$project: {"_id": 1, "name": 1, "authoredPublications": 1, "editedPublications": 1,
//    	                                           "intersect": {$setIntersection: ["$authoredPublications", "$editedPublications"]}
//    	                                           }
//    	                                       },
//    	                                       {$match: {"intersect": { $exists: true, $ne: [] }}}
//    	                                   ])
		
    }

    private void query13ButtonActionPerformed(ActionEvent evt) {
		
    	String name = textField1.getText();
    	
    	// Exact matching could be used alternatively
    	//String expr = "this.authors != undefined && this.authors[this.authors.length - 1] === '" + name + "'";
    	String expr = "this.authors != undefined && this.authors[this.authors.length - 1].includes('" + name + "')";
    	
    	Iterator<Document> iterator = db.publications.find(Filters.where(expr)).iterator();
    	
    	new Table(db.publications,
    			iterator,
    			"Publications containing " + name + " as last author",
    			new String[]{"ID", "Title", "Authors", "Pages", "Year"},
    			new String[]{"_id", "title", "authors", "pages", "year"},
    			true);
    	
    }

    private void query14ButtonActionPerformed(ActionEvent evt) {
    	int yearFrom;
    	int yearTo;
    	
    	try {
        	yearFrom = Integer.parseInt(textField1.getText());
        	yearTo = Integer.parseInt(textField2.getText());
    	} catch (Exception e) {
    		resultLabel.setText(invalidInput);
    		return;
    	}
    	
    	Iterator<Document> iterator =
    			db.publications.aggregate(Arrays.asList(
					new Document("$project",
						new Document("_id", "$_id")
						.append("publisher", "$publisher")
						.append("authors", "$authors")
						.append("editors", "$editors")
						.append("year", "$year")
						.append("gte",
							new Document("$gte", Arrays.asList(
								"$year", yearFrom
							))
						)
						.append("lte",
							new Document("$lte", Arrays.asList(
								"$year", yearTo
							))
						)
					),
					new Document("$match",
						new Document("$and", Arrays.asList(
							new Document("gte", true),
							new Document("lte", true)
						))
					),
					
					new Document("$lookup",
						new Document("from", "$$ROOT")
						.append("localField", "authors")
						.append("foreignField", "editors")
						.append("as", "proceedings")
					),
					
					new Document("$group",
						new Document("_id", "$publisher")
					)
    			)).iterator();
    	
    	new Table(db.publishers, 
    			iterator,
    			"Publishers of proceedings whose authors appear in inproceedings in range of years " + yearFrom + " to " + yearTo,
    			new String[] {"Publisher"},
    			new String[] {"_id"},
    			false);
    }
}
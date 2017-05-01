package ch.ethz.globis.isk.xmldb;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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

import ch.ethz.globis.isk.domain.mongodb.MongoConferenceEdition;
import ch.ethz.globis.isk.domain.mongodb.MongoInProceedings;
import ch.ethz.globis.isk.domain.mongodb.MongoPerson;
import ch.ethz.globis.isk.domain.mongodb.MongoPublisher;
import ch.ethz.globis.isk.domain.mongodb.MongoSeries;
import ch.ethz.globis.isk.xmldb.api.BaseXClient.Query;

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
    	new Table(
    			db,
    			"publications.xml",
    			null,
    			"Publications",
    			new String[] { "ID", "Title" },
    			new String[] { "id", "title" },
    			true
    	);
    }

    private void proceedingsButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"proceedings.xml",
    			null,
    			"Proceedings",
    			new String[] { "ID", "Title", "Conference", "Conference Edition", "Publisher", "Series", "ISBN", "Editors"  },
    			new String[] { "id", "title", "cid", "ceid", "publisher", "sid", "isbn", "editor"  },
    			true
    	);
    }
    
    private void inProceedingsButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"inproceedings.xml",
    			null,
    			"Inproceedings",
    			new String[] { "ID", "Title", "Proceedings", "Authors" },
    			new String[] { "id", "title", "pid", "author" },
    			true
    	);
    }
    
    private void conferenceEditionsButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"conferenceEditions.xml",
    			null,
    			"ConferenceEditions",
    			new String[] { "Year", "Conference", "Proceedings" },
    			new String[] { "id", "cid", "pid" },
    			true
    	);
    }

    private void conferencesButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"conferences.xml",
    			null,
    			"Conferences",
    			new String[] { "Name", "Editions" },
    			new String[] { "id", "ceid" },
    			true
    	);
    }
    
    private void personsButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"persons.xml",
    			null,
    			"Authors/Editors",
    			new String[] { "Name", "Authored Publications", "Edited Publications" },
    			new String[] { "id", "iid", "pid" },
    			true
    	);
    }

    private void publishersButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"publishers.xml",
    			null,
    			"Publishers",
    			new String[] { "Name", "Publications" },
    			new String[] { "id", "pid" },
    			true
    	);
    }
    
    private void seriesButtonActionPerformed(ActionEvent evt) {
    	new Table(
    			db,
    			"series.xml",
    			null,
    			"Series",
    			new String[] { "Name", "Publications" },
    			new String[] { "id", "pid" },
    			true
    	);
    }

    private void query1ButtonActionPerformed(ActionEvent evt) {
    	String id = textField1.getText();
    	String input = "let $publications := doc('publications.xml')/root//* "
		+ "return <root>{ "
		+ "for $p in $publications "
		+ "return "
		+ "if (contains($p/id, '" + id + "')) "
		+ "then $p "
		+ "else () "
		+ "}</root> ";
    	Query query = db.execute(input);
    	
    	new Table(
    			db,
    			null,
    			query,
    			"Publications by id",
    			new String[] { "ID", "Title" },
    			new String[] { "id", "title" },
    			false
    	);
    }

    private void query2ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query3ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query4ButtonActionPerformed(ActionEvent evt) {
    }

    private void query5ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query6ButtonActionPerformed(ActionEvent evt) {
    	Query query = db.executeFile("query6.xq");
    	
    	new Table(
    			db,
    			null,
    			query,
    			"Global average number of authors per publication",
    			new String[] { "Avg number of authors" },
    			new String[] { "avg" },
    			false
    	);
    }

    private void query7ButtonActionPerformed(ActionEvent evt) {
    	
    	int beginYear = Integer.parseInt(textField1.getText());
    	int endYear   = Integer.parseInt(textField2.getText());
    	
    	if (beginYear > endYear){
    		int temp = beginYear;
    		beginYear = endYear;
    		endYear = temp;
    	}
    	
    	String beginStr = Integer.toString(beginYear);
    	String endStr   = Integer.toString(endYear);
    	
    	String input = "let $publications := doc('publications.xml')/root//* "
    			+ "return <root>{ "
    			+ 	"<item>{ "
    			+ 		"<num>{ "
    			+ 			"count( "
    			+ 				"for $p in $publications "
    			+ 				"where (($p/year >= " + beginStr + ") and ($p/year <= " + endStr + ")) or (($p/ceid >= " + beginStr + ") and ($p/ceid <= " + endStr + ")) "
    			+ 				"return $p "
    			+ 			") "
    			+ 		"}</num> "
    			+ 	"}</item> "
    			+ "}</root> ";
    	
    	
    	Query query = db.execute(input);
    	
    	new Table(
    			db,
    			null,
    			query,
    			"Number of publications per year between " + beginStr + " and " + endStr + ".",
    			new String[] { "Total" },
    			new String[] { "num" },
    			false
    	);
    	
    }

    private void query8ButtonActionPerformed(ActionEvent evt) {
    	
    	// e.g. "ICPP"
    	String confID = textField1.getText();
    	
    	String input = "let $publications := doc('publications.xml')/root//* "
    			+ "return <root>{ "
    			+ 	"<item>{ "
    			+ 		"<num>{ "
    			+ 			"count( "
    			+ 				"for $p in $publications "
    			+ 				"where $p/cid = '" + confID + "' "
    			+ 				"return $p "
    			+ 			") "
    			+ 		"}</num> "
    			+ 	"}</item> "
    			+ "}</root> ";
    	
    	Query query = db.execute(input);
    	
    	new Table(
    			db,
    			null,
    			query,
    			"Number of publications for conference " + confID + ". ",
    			new String[] { "Total" },
    			new String[] { "num" },
    			false
    	);
    	
    }

    private void query9ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query10ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query11ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query12ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query13ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query14ButtonActionPerformed(ActionEvent evt) {
    	
    }
}
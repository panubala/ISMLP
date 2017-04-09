package ch.ethz.globis.isk.mongodb;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;

public class Panel extends JPanel {
    
	private Database db;
	private Color bgLight = new Color(97, 212, 195);
	private Color bgDark = new Color(36, 47, 65);
	private Color font = Color.WHITE;
	
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
	private JLabel title = new JLabel();
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
        add(leftPanel, new AbsoluteConstraints(0, 0, 190, 550));
        
        rightPanel.setLayout(new AbsoluteLayout());
        rightPanel.setBackground(bgDark);
        add(rightPanel, new AbsoluteConstraints(550, 0, 340, 550));
        
        
        title.setFont(new Font("Century Gothic", 1, 30));
        title.setForeground(font);
        title.setText("Domain Objects");
        

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
        
        add(title, 						new AbsoluteConstraints(230, 45, 300, 47));
        add(publicationsButton, 		new AbsoluteConstraints(250, 120, 230, -1));
        add(proceedingsButton, 			new AbsoluteConstraints(250, 150, 230, -1));
        add(inProceedingsButton, 		new AbsoluteConstraints(250, 180, 230, -1));
        add(conferencesButton,			new AbsoluteConstraints(250, 210, 230, -1));
        add(conferenceEditionsButton, 	new AbsoluteConstraints(250, 240, 230, -1));
        add(personsButton, 				new AbsoluteConstraints(250, 270, 230, -1));
        add(publishersButton, 			new AbsoluteConstraints(250, 300, 230, -1));
        add(seriesButton, 				new AbsoluteConstraints(250, 330, 230, -1));
        
        
        textField1.setFont(new Font("Century Gothic", 0, 10));
        textField2.setFont(new Font("Century Gothic", 0, 10));
        textField3.setFont(new Font("Century Gothic", 0, 10));
        
        
        query1Button.setText("1. Find publication by id");
        query1Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query1ButtonActionPerformed(evt);
            }
        });

        query2Button.setText("2. ");
        query2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query2ButtonActionPerformed(evt);
            }
        });

        query3Button.setText("3. ");
        query3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query3ButtonActionPerformed(evt);
            }
        });

        query4Button.setText("4. ");
        query4Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query4ButtonActionPerformed(evt);
            }
        });

        query5Button.setText("5. ");
        query5Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query5ButtonActionPerformed(evt);
            }
        });

        query6Button.setText("6. ");
        query6Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query6ButtonActionPerformed(evt);
            }
        });

        query7Button.setText("7. ");
        query7Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query7ButtonActionPerformed(evt);
            }
        });
        
        query8Button.setText("8. ");
        query8Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query8ButtonActionPerformed(evt);
            }
        });

        query9Button.setText("9. ");
        query9Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query9ButtonActionPerformed(evt);
            }
        });

        query10Button.setText("10. ");
        query10Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query10ButtonActionPerformed(evt);
            }
        });

        query11Button.setText("11. ");
        query11Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query11ButtonActionPerformed(evt);
            }
        });

        query12Button.setText("12. ");
        query12Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query12ButtonActionPerformed(evt);
            }
        });

        query13Button.setText("13. ");
        query13Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query13ButtonActionPerformed(evt);
            }
        });

        query14Button.setText("14. ");
        query14Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                query14ButtonActionPerformed(evt);
            }
        });
        
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
    	
    }

    private void query7ButtonActionPerformed(ActionEvent evt) {
    	
    }

    private void query8ButtonActionPerformed(ActionEvent evt) {
    	
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
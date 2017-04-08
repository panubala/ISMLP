package ch.ethz.globis.isk.mongodb;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Set;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import org.bson.Document;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;

import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.*;

import ch.ethz.globis.isk.domain.mongodb.MongoConferenceEdition;
import ch.ethz.globis.isk.domain.mongodb.MongoInProceedings;
import ch.ethz.globis.isk.domain.mongodb.MongoPerson;
import ch.ethz.globis.isk.domain.mongodb.MongoPublisher;
import ch.ethz.globis.isk.domain.mongodb.MongoSeries;

public class Panel extends JPanel {
    
	private Database db;

    public Panel(Database db) {
    	this.db = db;
        initComponents();
    }
    
    private void initComponents() {
        jPanel1 = new JPanel();
        jPanel2 = new JPanel();
        jLabel9 = new JLabel();
        jLabel1 = new JLabel();
        jSeparator1 = new JSeparator();
        jPanel4 = new JPanel();
        jSeparator8 = new JSeparator();
        jTextField16 = new JTextField();
        jTextField17 = new JTextField();
        jPanel5 = new JPanel();
        jButton9 = new JButton();
        jButton14 = new JButton();
        jButton15 = new JButton();
        jButton19 = new JButton();
        jTextField6 = new JTextField();
        jButton3 = new JButton();
        jButton4 = new JButton();
        jButton5 = new JButton();
        jButton6 = new JButton();
        jButton7 = new JButton();
        jButton8 = new JButton();
        jButton10 = new JButton();
        jButton11 = new JButton();
        jButton12 = new JButton();
        jButton13 = new JButton();
        jSeparator7 = new JSeparator();
        jButton16 = new JButton();
        jButton17 = new JButton();
        jButton18 = new JButton();
        jButton20 = new JButton();

        setBackground(new Color(97, 212, 195));
        setForeground(new Color(255, 255, 255));
        setLayout(new AbsoluteLayout());

        jPanel1.setBackground(new Color(36, 47, 65));
        jPanel1.setLayout(new AbsoluteLayout());
        add(jPanel1, new AbsoluteConstraints(379, 0, -1, 529));

        jPanel2.setBackground(new Color(36, 47, 65));

        jLabel9.setFont(new Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setForeground(new Color(255, 255, 255));
        jLabel9.setIcon(new ImageIcon("/Users/panuyabalasuntharam/Desktop/0001.png")); // NOI18N

        GroupLayout jPanel2Layout = new GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, new AbsoluteConstraints(0, 0, 190, 550));

        jLabel1.setFont(new Font("Century Gothic", 1, 30)); // NOI18N
        jLabel1.setForeground(new Color(255, 255, 255));
        jLabel1.setText("Publication Search");
        add(jLabel1, new AbsoluteConstraints(230, 20, 300, 47));
        add(jSeparator1, new AbsoluteConstraints(1057, 243, 300, 0));

        jPanel4.setBackground(new Color(36, 47, 65));
        jPanel4.setLayout(new AbsoluteLayout());

        jSeparator8.setForeground(new Color(255, 255, 255));
        jPanel4.add(jSeparator8, new AbsoluteConstraints(20, 100, 290, -1));

        jTextField16.setBackground(new Color(36, 47, 65));
        jTextField16.setFont(new Font("Century Gothic", 0, 10)); // NOI18N
        jTextField16.setForeground(new Color(255, 255, 255));
        jTextField16.setText("Enter");
        jTextField16.setBorder(null);
        jTextField16.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTextField16MouseClicked(evt);
            }
        });
        jTextField16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField16, new AbsoluteConstraints(170, 70, 120, 20));

        jTextField17.setBackground(new Color(36, 47, 65));
        jTextField17.setFont(new Font("Century Gothic", 0, 10)); // NOI18N
        jTextField17.setForeground(new Color(255, 255, 255));
        jTextField17.setText("Enter");
        jTextField17.setBorder(null);
        jTextField17.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTextField17MouseClicked(evt);
            }
        });
        jTextField17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField17, new AbsoluteConstraints(20, 70, 140, 20));

        GroupLayout jPanel5Layout = new GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5, new AbsoluteConstraints(130, 480, -1, 40));

        jButton9.setText("No of Publications per year");
        jButton9.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new AbsoluteConstraints(60, 210, 230, -1));

        jButton14.setText("Search Publisher");
        jButton14.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton14, new AbsoluteConstraints(60, 300, 230, -1));

        jButton15.setText("Shortest Path between 2 authors");
        jButton15.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15, new AbsoluteConstraints(60, 240, 230, -1));

        jButton19.setText("Search Publication");
        jButton19.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton19, new AbsoluteConstraints(60, 270, 230, -1));

        add(jPanel4, new AbsoluteConstraints(550, 0, 340, 550));

        jTextField6.setBackground(new Color(97, 212, 195));
        jTextField6.setFont(new Font("Century Gothic", 0, 10)); // NOI18N
        jTextField6.setForeground(new Color(255, 255, 255));
        jTextField6.setText("Enter filter ");
        jTextField6.setBorder(null);
        jTextField6.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });
        jTextField6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6, new AbsoluteConstraints(260, 70, 210, 26));

        jButton3.setText("No of authors of a conference");
        add(jButton3, new AbsoluteConstraints(250, 390, 230, -1));

        jButton4.setText("Search author/ editors");
        jButton4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new AbsoluteConstraints(250, 480, 230, -1));

        jButton5.setText("Search Edition");
        add(jButton5, new AbsoluteConstraints(250, 150, 230, -1));
        jButton5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Search InProceeding");
        add(jButton6, new AbsoluteConstraints(250, 180, 230, -1));
        jButton6.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Search Proceeding");
        jButton7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        add(jButton7, new AbsoluteConstraints(250, 210, 230, -1));

        jButton8.setText("Search Author");
        add(jButton8, new AbsoluteConstraints(250, 240, 230, -1));
        jButton8.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setText("Search Publisher");
        jButton10.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10, new AbsoluteConstraints(250, 300, 230, -1));

        jButton11.setText("Search Series");
        jButton11.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        add(jButton11, new AbsoluteConstraints(250, 330, 230, -1));

        jButton12.setText("Find average number of authors");
        add(jButton12, new AbsoluteConstraints(250, 360, 230, -1));

        jButton13.setText("Search Publication");
        jButton13.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        add(jButton13, new AbsoluteConstraints(250, 270, 230, -1));

        jSeparator7.setForeground(new Color(255, 255, 255));
        add(jSeparator7, new AbsoluteConstraints(260, 100, 210, -1));

        jButton16.setText("Search Conference");
        jButton16.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        add(jButton16, new AbsoluteConstraints(250, 120, 230, -1));

        jButton17.setText("Search Co-Authors");
        jButton17.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        add(jButton17, new AbsoluteConstraints(250, 420, 230, -1));

        jButton18.setText("No of publications in conference");
        jButton18.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        add(jButton18, new AbsoluteConstraints(250, 450, 230, -1));
        
      
    }                      

    private void jTextField16MouseClicked(MouseEvent evt) {
    	
    }                                         

    private void jTextField16ActionPerformed(ActionEvent evt) {
    	
    }                                            

    private void jTextField17MouseClicked(MouseEvent evt) {
    	
    }                                         

    private void jTextField17ActionPerformed(ActionEvent evt) {
    	
    }                                            

    private void jTextField6MouseClicked(MouseEvent evt) {
    	
    }                                        

    private void jTextField6ActionPerformed(ActionEvent evt) {
    	
    }    
    
    private void jButton8ActionPerformed(ActionEvent evt) {
    	
    }  

    private void jButton9ActionPerformed(ActionEvent evt) {
    	
    }                                          

    private void jButton10ActionPerformed(ActionEvent evt) {
    	
    }                                       
    
    private void jButton5ActionPerformed(ActionEvent evt) {
    	
    }
    
    private void jButton6ActionPerformed(ActionEvent evt) {
    	new Table(db.publications,
				db.publications.find(Filters.exists("proceedings")).iterator(),
    			"Inproceedings",
    			new String[] { "ID", "Title", "Authors", "Proceedings" },
    			new String[] { "_id", "title", "authors", "proceedings" });
    }
    
    private void jButton11ActionPerformed(ActionEvent evt) {
    	
    }                                         

    private void jButton7ActionPerformed(ActionEvent evt) {
    	new Table(db.publications,
				db.publications.find(Filters.exists("publications")).iterator(),
    			"Proceedings",
    			new String[] { "ID", "Title", "Year", "Publisher", "ISBN", "Editors", "Series", "Conference Edition", "Publications" },
    			new String[] { "_id", "title", "year", "publisher", "isbn", "editors", "series", "conferenceEdition", "publications" });
    }                                        

    private void jButton4ActionPerformed(ActionEvent evt) {
    	
    }                                        

    private void jButton13ActionPerformed(ActionEvent evt) {
    	new Table(db.publications,
				db.publications.find().iterator(),
    			"Publications",
    			new String[] { "ID", "Title" },
    			new String[] { "_id", "title" });
    }                    

    private void jButton14ActionPerformed(ActionEvent evt) {
    	
    }                                         

    private void jButton15ActionPerformed(ActionEvent evt) {
    	
    }                                         

    private void jButton16ActionPerformed(ActionEvent evt) {
    	
    }                                         

    private void jButton17ActionPerformed(ActionEvent evt) {
    	
    }                                         

    private void jButton18ActionPerformed(ActionEvent evt) {
    	
    }                                         

    private void jButton19ActionPerformed(ActionEvent evt) {
    	
    }                                         


    // Variables declaration - do not modify
    private JButton jButton3;
    private JButton jButton4;
    private JButton jButton5;
    private JButton jButton6;
    private JButton jButton7;
    private JButton jButton8;
    private JButton jButton9;                   
    private JButton jButton10;
    private JButton jButton11;
    private JButton jButton12;
    private JButton jButton13;
    private JButton jButton14;
    private JButton jButton15;
    private JButton jButton16;
    private JButton jButton17;
    private JButton jButton18;
    private JButton jButton19;
    private JButton jButton20;
    private JLabel jLabel1;
    private JLabel jLabel9;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JSeparator jSeparator1;
    private JSeparator jSeparator7;
    private JSeparator jSeparator8;
    private JTextField jTextField16;
    private JTextField jTextField17;
    private JTextField jTextField6;
	
}
package ch.ethz.globis.isk.Zoo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ch.ethz.globis.isk.domain.ConferenceEdition;
import ch.ethz.globis.isk.domain.DomainObject;
import ch.ethz.globis.isk.domain.InProceedings;
import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.Publication;
import ch.ethz.globis.isk.domain.zoodb.ZooConference;
import ch.ethz.globis.isk.domain.zoodb.ZooConferenceEdition;
import ch.ethz.globis.isk.domain.zoodb.ZooInProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;
import ch.ethz.globis.isk.domain.zoodb.ZooPublisher;
import ch.ethz.globis.isk.domain.zoodb.ZooSeries;
import ch.ethz.globis.isk.util.Pair;

public class Panel extends javax.swing.JPanel {
    
    String filter = "";
    String field1 = "";
    String field2 = "";

    public Panel() {
        initComponents();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jPanel4 = new javax.swing.JPanel();
        jSeparator8 = new javax.swing.JSeparator();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jButton9 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jTextField6 = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jSeparator7 = new javax.swing.JSeparator();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();

        setBackground(new java.awt.Color(97, 212, 195));
        setForeground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(36, 47, 65));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 0, -1, 529));

        jPanel2.setBackground(new java.awt.Color(36, 47, 65));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setIcon(new javax.swing.ImageIcon("/Users/panuyabalasuntharam/Desktop/0001.png")); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 550));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Publication Search");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 300, 47));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 243, 300, 0));

        jPanel4.setBackground(new java.awt.Color(36, 47, 65));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, 290, -1));

        jTextField16.setBackground(new java.awt.Color(36, 47, 65));
        jTextField16.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(255, 255, 255));
        jTextField16.setText("Enter");
        jTextField16.setBorder(null);
        jTextField16.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField16MouseClicked(evt);
            }
        });
        jTextField16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField16ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 70, 120, 20));

        jTextField17.setBackground(new java.awt.Color(36, 47, 65));
        jTextField17.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jTextField17.setText("Enter");
        jTextField17.setBorder(null);
        jTextField17.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField17MouseClicked(evt);
            }
        });
        jTextField17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField17ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 140, 20));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel4.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 480, -1, 40));

        jButton9.setText("No of Publications per year");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 210, 230, -1));

        jButton14.setText("Search Publisher");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 300, 230, -1));

        jButton15.setText("Shortest Path between 2 authors");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 230, -1));

        jButton19.setText("Search Publication");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 270, 230, -1));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 340, 550));

        jTextField6.setBackground(new java.awt.Color(97, 212, 195));
        jTextField6.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField6.setForeground(new java.awt.Color(255, 255, 255));
        jTextField6.setText("Enter filter ");
        jTextField6.setBorder(null);
        jTextField6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField6MouseClicked(evt);
            }
        });
        jTextField6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField6ActionPerformed(evt);
            }
        });
        add(jTextField6, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 210, 26));

        jButton3.setText("No of authors of a conference");
        add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 390, 230, -1));

        jButton4.setText("Search author/ editors");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 480, 230, -1));

        jButton5.setText("Search Edition");
        add(jButton5, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 150, 230, -1));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("Search InProceeding");
        add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 180, 230, -1));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("Search Proceeding");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 230, -1));

        jButton8.setText("Search Author");
        add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 240, 230, -1));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setText("Search Publisher");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 300, 230, -1));

        jButton11.setText("Search Series");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 330, 230, -1));

        jButton12.setText("Find average number of authors");
        add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 360, 230, -1));

        jButton13.setText("Search Publication");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 270, 230, -1));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 100, 210, -1));

        jButton16.setText("Search Conference");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 120, 230, -1));

        jButton17.setText("Search Co-Authors");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 420, 230, -1));

        jButton18.setText("No of publications in conference");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 450, 230, -1));
        
      
    }// </editor-fold>                        

    private void jTextField16MouseClicked(java.awt.event.MouseEvent evt) {
    }                                         

    private void jTextField16ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField17MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField17ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField6MouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jTextField6ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }    
    
    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {     
    	Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooPerson> collection = db.getWithFilter(ZooPerson.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }  

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                          

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {     
		Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooPublisher> collection = db.getWithFilter(ZooPublisher.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }                                       
    
    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {     
		Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooConferenceEdition> collection = db.getWithFilter(ZooConferenceEdition.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }
    
    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {     
		Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooInProceedings> collection = db.getWithFilter(ZooInProceedings.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }
    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {                                          
    	Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooSeries> collection = db.getWithFilter(ZooSeries.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }                                         

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooProceedings> collection = db.getWithFilter(ZooProceedings.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }                                        

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {                                          
    	Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooPublication> collection = db.getWithFilter(ZooPublication.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }                                         

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {  
		Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooConference> collection = db.getWithFilter(ZooConference.class, "");
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }                                         

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {                                          
    	Pair<Object[][], String[]> objectsAndTitle = null;
		Database db = new Database("database", false);
		try {
			db.open();
			Collection<ZooPerson> collection = db.getCoAuthors(jTextField6.getText());
			
			objectsAndTitle = getObjectsAndTitle(collection, filter);
			DefaultTableModel model = new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b);
			openNewTable(model);
		} catch (Exception e) {
    		System.out.println(e.getMessage());
    		final String error = e.getMessage();
    		SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                	JFrame frame = new JFrame(error);
                	PanelErrorMessage panel = new PanelErrorMessage();
                	frame.add(panel);
                	frame.pack();
                	frame.setVisible(true);
                }
            });
		} finally {
			db.close();
		}
    }                                         

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    	
    }                                         


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JButton jButton20;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration                   


    private Pair<Object[][], String[]> getObjectsAndTitle(Collection<?> collection, String filter) {
    	if (collection.isEmpty())
    		return null;
		
		Object[][] objects = new Object[collection.size()][];
		int i = 0;
		for (Object object : collection) {
			if (object instanceof ZooPublication) {
				ZooPublication publication = (ZooPublication) object;
		    	String authors = "";
		    	for (Person author : publication.getAuthors()) {
		    		authors += author.getName() + ", ";
		    	}
		    	if (authors.length() > 2)
		    		authors = authors.substring(0, authors.length() - 2);
		    	
		    	if ((publication.getTitle() != null && publication.getTitle().contains(filter))
		    			|| (Integer.toString(publication.getYear()).contains(filter))
		    			|| (authors.contains(filter)))
		    		objects[i++] = new Object[]{ publication.getTitle(), publication.getYear(), authors };
			} else if (object instanceof ZooConference) {
				ZooConference conference = (ZooConference) object;
		    	String editions = "";
		    	for (ConferenceEdition edition : conference.getEditions()) {
		    		editions += edition.getYear() + ", ";
		    	}
		    	if (editions.length() > 2)
		    		editions = editions.substring(0, editions.length() - 2);
		    	
		    	if ((conference.getName() != null && conference.getName().contains(filter))
		    			|| (editions.contains(filter)))
		    		objects[i++] = new Object[]{ conference.getName(), editions };
			} else if (object instanceof ZooConferenceEdition) {
				ZooConferenceEdition conferenceEdition = (ZooConferenceEdition) object;
		    	if ((conferenceEdition.getConference() != null && conferenceEdition.getConference().getName() != null && conferenceEdition.getConference().getName().contains(filter))
		    			|| (Integer.toString(conferenceEdition.getYear()).contains(filter)))
		    		objects[i++] = new Object[]{ conferenceEdition.getConference().getName(), conferenceEdition.getYear() };
			} else if (object instanceof ZooPerson) {
				ZooPerson person = (ZooPerson) object;
		    	String publications = "";
		    	for (Publication publication : person.getAuthoredPublications()) {
		    		publications += publication.getTitle() + ", ";
		    	}
		    	for (Publication publication : person.getEditedPublications()) {
		    		publications += publication.getTitle() + ", ";
		    	}
		    	if (publications.length() > 2)
		    		publications = publications.substring(0, publications.length() - 2);
		    	
		    	if ((person.getName() != null && person.getName().contains(filter))
		    			|| (publications.contains(filter)))
		    		objects[i++] = new Object[]{ person.getName(), publications };
			} else if (object instanceof ZooPublisher) {
				ZooPublisher publisher = (ZooPublisher) object;
		    	String publications = "";
		    	for (Publication publication : publisher.getPublications()) {
		    		publications += publication.getTitle() + ", ";
		    	}
		    	if (publications.length() > 2)
		    		publications = publications.substring(0, publications.length() - 2);
		    	
		    	if ((publisher.getName() != null && publisher.getName().contains(filter))
		    			|| (publications.contains(filter)))
		    		objects[i++] = new Object[]{ publisher.getName(), publications };
			} else if (object instanceof ZooSeries) {
				ZooSeries series = (ZooSeries) object;
		    	String publications = "";
		    	for (Publication publication : series.getPublications()) {
		    		publications += publication.getTitle() + ", ";
		    	}
		    	if (publications.length() > 2)
		    		publications = publications.substring(0, publications.length() - 2);
		    	
		    	if ((series.getName() != null && series.getName().contains(filter))
		    			|| (publications.contains(filter)))
		    		objects[i++] = new Object[]{ series.getName(), publications };
			}
		}

		String[] title = null;
		if (collection.iterator().next() instanceof ZooPublication)
			title = new String[] { "Title", "Year", "Authors" };
		else if (collection.iterator().next() instanceof ZooConference)
			title = new String[] { "Name", "Editions" };
		else if (collection.iterator().next() instanceof ZooConferenceEdition)
			title = new String[] { "Conference", "Edition" };
		else if (collection.iterator().next() instanceof ZooPerson)
			title = new String[] { "Name", "Publications" };
		else if (collection.iterator().next() instanceof ZooPublisher)
			title = new String[] { "Name", "Publications" };
		else if (collection.iterator().next() instanceof ZooSeries)
			title = new String[] { "Name", "Publications" };
    	
    	return new Pair<Object[][], String[]> (objects, title);
    }
	
	private void openNewTable(DefaultTableModel model) {
		//////////////Table/////////
		JFrame frame = new JFrame("Result");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
	
		panel.setBackground(new java.awt.Color(97, 212, 195));
        panel.setForeground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

		JTable table = new JTable();
		table.setModel(model);
		table.setFont(new java.awt.Font("Century Gothic", 0, 12));
		table.setGridColor(new java.awt.Color(97, 212, 195));
//		table.setBackground(new java.awt.Color(97, 212, 195));
		table.getTableHeader().setOpaque(false);
		table.getTableHeader().setBackground(new java.awt.Color(97, 212, 195));
		table.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
		table.getTableHeader().setBorder(null);
		table.getTableHeader().setFont(new java.awt.Font("Century Gothic", 0, 12));
		
		Container container = getRootPane();
		
		container.setLayout(new BorderLayout());
		container.setBackground(new java.awt.Color(97, 212, 195));
		container.add(table.getTableHeader(), BorderLayout.PAGE_START);
		container.add(table, BorderLayout.CENTER);
		frame.add(new JScrollPane(table));
		frame.pack();
//		frame.add(panel);
		frame.setVisible(true);		
	}
}
    
    
    


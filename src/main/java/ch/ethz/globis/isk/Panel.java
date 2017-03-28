package ch.ethz.globis.isk;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Collection;
import java.util.Collections;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import ch.ethz.globis.isk.domain.DomainObject;
import ch.ethz.globis.isk.domain.InProceedings;
import ch.ethz.globis.isk.domain.Person;
import ch.ethz.globis.isk.domain.zoodb.ZooInProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPerson;
import ch.ethz.globis.isk.domain.zoodb.ZooProceedings;
import ch.ethz.globis.isk.domain.zoodb.ZooPublication;

public class Panel extends javax.swing.JPanel {

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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        jPanel3 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();

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
                .addContainerGap(23, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel9)
                .addContainerGap(372, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 540));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Publication Search");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 300, 47));

        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("PUBLICATION TITLE");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, 20));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("AUTHORS ");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, 20));

        jTextField1.setBackground(new java.awt.Color(97, 212, 195));
        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Enter ");
        jTextField1.setBorder(null);
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 305, 26));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 243, 300, 0));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 305, -1));

        jTextField2.setBackground(new java.awt.Color(97, 212, 195));
        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setText("Enter");
        jTextField2.setBorder(null);
        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField2MouseClicked(evt);
            }
        });
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 303, 30));

        jTextField3.setBackground(new java.awt.Color(97, 212, 195));
        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setText("Enter");
        jTextField3.setBorder(null);
        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField3MouseClicked(evt);
            }
        });
        jTextField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField3ActionPerformed(evt);
            }
        });
        add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 303, 30));

        jTextField4.setBackground(new java.awt.Color(97, 212, 195));
        jTextField4.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
        jTextField4.setText("Enter");
        jTextField4.setBorder(null);
        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField4MouseClicked(evt);
            }
        });
        jTextField4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField4ActionPerformed(evt);
            }
        });
        add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 303, 30));

        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 303, 20));

        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 303, 20));

        jPanel3.setBackground(new java.awt.Color(36, 47, 65));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Conference");
        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(439, 380, -1, -1));

        jButton1.setBackground(new java.awt.Color(36, 47, 65));
        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Search");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, 120, 40));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("CONFERENCE");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 370, -1, 20));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 303, 20));
    }// </editor-fold>                        

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
       
    }                                           

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    
    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {                                         
        jTextField1.setText("");
    }
    
    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {                                         
    	jTextField2.setText("");
    }
    
    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {                                         
    	jTextField3.setText("");
    }
    
    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {                                         
    	jTextField4.setText("");
    }
    
    
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
    	
		
		String query = "";
		
		//TODO:
		query = query;
							
		
		
		//////////////Table/////////
		JFrame frame = new JFrame("Result");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
	
		panel.setBackground(new java.awt.Color(97, 212, 195));
        panel.setForeground(new java.awt.Color(255, 255, 255));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
		
		JTable table = new JTable();
		
		Pair<Object[][], String[]> objectsAndTitle = null;
		ZooDatabase db = new ZooDatabase("database", false);
		try {
			db.open();
			Collection<ZooPublication> publications = db.getWithFilter(ZooPublication.class, "");
			objectsAndTitle = getObjectsAndTitle(publications);
			
			table.setModel(new DefaultTableModel(objectsAndTitle.a, objectsAndTitle.b));
			table.setFont(new java.awt.Font("Century Gothic", 0, 12));
			table.setGridColor(new java.awt.Color(97, 212, 195));
	//		table.setBackground(new java.awt.Color(97, 212, 195));
			table.getTableHeader().setOpaque(false);
			table.getTableHeader().setBackground(new java.awt.Color(97, 212, 195));
			table.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
			table.getTableHeader().setBorder(null);
			table.getTableHeader().setFont(new java.awt.Font("Century Gothic", 0, 12));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.close();
		}
		
		
		
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
    
    private Pair<Object[][], String[]> getObjectsAndTitle(Collection<ZooPublication> publications) {
		Object[][] objects = new Object[publications.size()][];
		int i = 0;
		for (ZooPublication publication : publications) {
	    	String authors = "";
	    	for (Person author : publication.getAuthors()) {
	    		authors += author.getName() + ", ";
	    	}
	    	if (authors.length() > 2)
	    		authors = authors.substring(0, authors.length() - 2);
	    	
			objects[i++] = new Object[]{ publication.getTitle(), publication.getYear(), authors };
		}
    	
    	return new Pair<Object[][], String[]> (objects, new String[]{ "title", "year", "authors" });
    }
    
   /* private Pair<Object[][], String[]> getObjectsAndTitle(Collection<ZooPerson> persons) {
    	String publications = "";
    	for (Person person : persons) {
    		publications += author.getName() + ", ";
	    	String authors = "";
	    	for (Person author : publication.getAuthors()) {
	    		authors += author.getName() + ", ";
	    	}
	    	if (authors.length() > 2)
	    		authors = authors.substring(0, authors.length() - 2);
    	}
    	if (publications.length() > 2)
    		publications = publications.substring(0, publications.length() - 2);
    	
		Object[][] objects = new Object[persons.size()][];
		int i = 0;
		for (ZooPerson person : persons) {
			objects[i++] = new Object[]{ person.getName(), person.get(), person };
		}
    	
    	return new Pair<Object[][], String[]> (objects, new String[]{ "title", "year", "authors" });
    }*/


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration                   
}


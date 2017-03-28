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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
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
        jTextField5 = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jTextField1 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jTextField11 = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jSeparator7 = new javax.swing.JSeparator();
        jTextField13 = new javax.swing.JTextField();
        jSeparator8 = new javax.swing.JSeparator();
        jLabel11 = new javax.swing.JLabel();
        jTextField14 = new javax.swing.JTextField();
        jTextField15 = new javax.swing.JTextField();
        jTextField16 = new javax.swing.JTextField();
        jTextField17 = new javax.swing.JTextField();
        jTextField18 = new javax.swing.JTextField();
        jTextField19 = new javax.swing.JTextField();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jSeparator6 = new javax.swing.JSeparator();
        jTextField10 = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

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
                .addContainerGap(382, Short.MAX_VALUE))
        );

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 550));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Publication Search");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 300, 47));

        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("ID");
        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, 20));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("AUTHORS ");
        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, 20));
        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 243, 300, 0));

        jTextField2.setBackground(new java.awt.Color(97, 212, 195));
        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setText("Find co-authors...");
        jTextField2.setBorder(null);
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 310, 140, 30));

        jTextField3.setBackground(new java.awt.Color(97, 212, 195));
        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
        jTextField3.setText("Enter");
        jTextField3.setBorder(null);
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

        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 460, 120, 40));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("CONFERENCE");
        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 370, -1, 20));

        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 303, 20));

        jTextField5.setBackground(new java.awt.Color(97, 212, 195));
        jTextField5.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField5.setForeground(new java.awt.Color(255, 255, 255));
        jTextField5.setText("Enter ");
        jTextField5.setBorder(null);
        jTextField5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField5MouseClicked(evt);
            }
        });
        jTextField5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField5ActionPerformed(evt);
            }
        });
        add(jTextField5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 190, 26));

        jPanel4.setBackground(new java.awt.Color(36, 47, 65));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jTextField1.setBackground(new java.awt.Color(36, 47, 65));
        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setText("Enter begin-offset");
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
        jPanel4.add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 80, 100, 20));

        jTextField7.setBackground(new java.awt.Color(36, 47, 65));
        jTextField7.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField7.setForeground(new java.awt.Color(255, 255, 255));
        jTextField7.setText("end");
        jTextField7.setBorder(null);
        jTextField7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField7MouseClicked(evt);
            }
        });
        jTextField7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField7ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField7, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 340, 120, 20));

        jTextField9.setBackground(new java.awt.Color(36, 47, 65));
        jTextField9.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField9.setForeground(new java.awt.Color(255, 255, 255));
        jTextField9.setText("Enter begin-offset");
        jTextField9.setBorder(null);
        jTextField9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField9MouseClicked(evt);
            }
        });
        jTextField9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField9ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField9, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 100, 20));

        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 280, 290, -1));

        jTextField11.setBackground(new java.awt.Color(36, 47, 65));
        jTextField11.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField11.setForeground(new java.awt.Color(255, 255, 255));
        jTextField11.setText("Enter title");
        jTextField11.setBorder(null);
        jTextField11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField11MouseClicked(evt);
            }
        });
        jTextField11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField11ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 80, 60, 20));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("NUMBER OF PUBLICATION PER YEAR");
        jPanel4.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("FIND PUBLICATIONS");
        jPanel4.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jSeparator7.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jSeparator7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 110, 290, -1));

        jTextField13.setBackground(new java.awt.Color(36, 47, 65));
        jTextField13.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField13.setForeground(new java.awt.Color(255, 255, 255));
        jTextField13.setText("begin");
        jTextField13.setBorder(null);
        jTextField13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField13MouseClicked(evt);
            }
        });
        jTextField13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField13ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 340, 130, 20));

        jSeparator8.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(jSeparator8, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 290, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("FIND THE SHORTEST PATH BETWEEN 2 AUTHORS");
        jPanel4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, -1, -1));

        jTextField14.setBackground(new java.awt.Color(36, 47, 65));
        jTextField14.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField14.setForeground(new java.awt.Color(255, 255, 255));
        jTextField14.setText("Number of authors and editors of a conference");
        jTextField14.setBorder(null);
        jTextField14.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField14MouseClicked(evt);
            }
        });
        jTextField14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField14ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField14, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 270, 20));

        jTextField15.setBackground(new java.awt.Color(36, 47, 65));
        jTextField15.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField15.setForeground(new java.awt.Color(255, 255, 255));
        jTextField15.setText("Author No 1");
        jTextField15.setBorder(null);
        jTextField15.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField15MouseClicked(evt);
            }
        });
        jTextField15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField15ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField15, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 160, 130, 20));

        jTextField16.setBackground(new java.awt.Color(36, 47, 65));
        jTextField16.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField16.setForeground(new java.awt.Color(255, 255, 255));
        jTextField16.setText("Enter Conference");
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
        jPanel4.add(jTextField16, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 120, 20));

        jTextField17.setBackground(new java.awt.Color(36, 47, 65));
        jTextField17.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField17.setForeground(new java.awt.Color(255, 255, 255));
        jTextField17.setText("Enter ID");
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
        jPanel4.add(jTextField17, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 50, 140, 20));

        jTextField18.setBackground(new java.awt.Color(36, 47, 65));
        jTextField18.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField18.setForeground(new java.awt.Color(255, 255, 255));
        jTextField18.setText("Author No 2");
        jTextField18.setBorder(null);
        jTextField18.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField18MouseClicked(evt);
            }
        });
        jTextField18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField18ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField18, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 160, 120, 20));

        jTextField19.setBackground(new java.awt.Color(36, 47, 65));
        jTextField19.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField19.setForeground(new java.awt.Color(255, 255, 255));
        jTextField19.setText("Average number of authors");
        jTextField19.setBorder(null);
        jTextField19.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField19MouseClicked(evt);
            }
        });
        jTextField19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField19ActionPerformed(evt);
            }
        });
        jPanel4.add(jTextField19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 210, 160, 20));

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

        jPanel6.setBackground(new java.awt.Color(97, 212, 195));

        jButton2.setBackground(new java.awt.Color(36, 47, 65));
        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Search");
        jButton2.setBorder(null);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel4.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 460, 130, 40));

        add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 0, 340, 550));

        jSeparator6.setForeground(new java.awt.Color(255, 255, 255));
        add(jSeparator6, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 305, -1));

        jTextField10.setBackground(new java.awt.Color(97, 212, 195));
        jTextField10.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
        jTextField10.setForeground(new java.awt.Color(255, 255, 255));
        jTextField10.setText("Enter");
        jTextField10.setBorder(null);
        jTextField10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField10ActionPerformed(evt);
            }
        });
        add(jTextField10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 140, 30));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("PUBLICATION TITLE");
        add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));
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

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jTextField5MouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField7MouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField9MouseClicked(java.awt.event.MouseEvent evt) {                                         
        // TODO add your handling code here:
    }                                        

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {                                            
        // TODO add your handling code here:
    }                                           

    private void jTextField10ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField11MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField13MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField13ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField14MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField14ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField15MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField15ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField16MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
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

    private void jTextField18MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField18ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jTextField19MouseClicked(java.awt.event.MouseEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void jTextField19ActionPerformed(java.awt.event.ActionEvent evt) {                                             
        // TODO add your handling code here:
    }                                            

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        // TODO add your handling code here:
    }


    // Variables declaration - do not modify                     
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JSeparator jSeparator7;
    private javax.swing.JSeparator jSeparator8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField13;
    private javax.swing.JTextField jTextField14;
    private javax.swing.JTextField jTextField15;
    private javax.swing.JTextField jTextField16;
    private javax.swing.JTextField jTextField17;
    private javax.swing.JTextField jTextField18;
    private javax.swing.JTextField jTextField19;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration                   
}

    
    
    
    
    
    ///////////OLDVERSION/////
//    @SuppressWarnings("unchecked")
//    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
//    private void initComponents() {
//
//        jPanel1 = new javax.swing.JPanel();
//        jPanel2 = new javax.swing.JPanel();
//        jLabel9 = new javax.swing.JLabel();
//        jLabel1 = new javax.swing.JLabel();
//        jLabel2 = new javax.swing.JLabel();
//        jLabel3 = new javax.swing.JLabel();
//        jLabel4 = new javax.swing.JLabel();
//        jTextField1 = new javax.swing.JTextField();
//        jSeparator1 = new javax.swing.JSeparator();
//        jSeparator2 = new javax.swing.JSeparator();
//        jTextField2 = new javax.swing.JTextField();
//        jTextField3 = new javax.swing.JTextField();
//        jTextField4 = new javax.swing.JTextField();
//        jSeparator3 = new javax.swing.JSeparator();
//        jSeparator4 = new javax.swing.JSeparator();
//        jPanel3 = new javax.swing.JPanel();
//        jLabel5 = new javax.swing.JLabel();
//        jButton1 = new javax.swing.JButton();
//        jLabel10 = new javax.swing.JLabel();
//        jSeparator5 = new javax.swing.JSeparator();
//
//        setBackground(new java.awt.Color(97, 212, 195));
//        setForeground(new java.awt.Color(255, 255, 255));
//        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jPanel1.setBackground(new java.awt.Color(36, 47, 65));
//        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(379, 0, -1, 529));
//
//        jPanel2.setBackground(new java.awt.Color(36, 47, 65));
//
//        jLabel9.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel9.setIcon(new javax.swing.ImageIcon("/Users/panuyabalasuntharam/Desktop/0001.png")); // NOI18N
//
//        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
//        jPanel2.setLayout(jPanel2Layout);
//        jPanel2Layout.setHorizontalGroup(
//            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel2Layout.createSequentialGroup()
//                .addGap(17, 17, 17)
//                .addComponent(jLabel9)
//                .addContainerGap(23, Short.MAX_VALUE))
//        );
//        jPanel2Layout.setVerticalGroup(
//            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(jPanel2Layout.createSequentialGroup()
//                .addGap(18, 18, 18)
//                .addComponent(jLabel9)
//                .addContainerGap(372, Short.MAX_VALUE))
//        );
//
//        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 190, 540));
//
//        jLabel1.setFont(new java.awt.Font("Century Gothic", 1, 30)); // NOI18N
//        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel1.setText("Publication Search");
//        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 50, 300, 47));
//
//        jLabel2.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel2.setText("PUBLICATION TITLE");
//        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 130, -1, -1));
//
//        jLabel3.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel3.setText("ID");
//        add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 210, -1, 20));
//
//        jLabel4.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel4.setText("AUTHORS ");
//        add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 290, -1, 20));
//
//        jTextField1.setBackground(new java.awt.Color(97, 212, 195));
//        jTextField1.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
//        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
//        jTextField1.setText("Enter ");
//        jTextField1.setBorder(null);
//        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jTextField1MouseClicked(evt);
//            }
//        });
//        jTextField1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField1ActionPerformed(evt);
//            }
//        });
//        add(jTextField1, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 305, 26));
//        add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1057, 243, 300, 0));
//
//        jSeparator2.setForeground(new java.awt.Color(255, 255, 255));
//        add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 180, 305, -1));
//
//        jTextField2.setBackground(new java.awt.Color(97, 212, 195));
//        jTextField2.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
//        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
//        jTextField2.setText("Enter");
//        jTextField2.setBorder(null);
//        jTextField2.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jTextField2MouseClicked(evt);
//            }
//        });
//        jTextField2.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField2ActionPerformed(evt);
//            }
//        });
//        add(jTextField2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 310, 303, 30));
//
//        jTextField3.setBackground(new java.awt.Color(97, 212, 195));
//        jTextField3.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
//        jTextField3.setForeground(new java.awt.Color(255, 255, 255));
//        jTextField3.setText("Enter");
//        jTextField3.setBorder(null);
//        jTextField3.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jTextField3MouseClicked(evt);
//            }
//        });
//        jTextField3.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField3ActionPerformed(evt);
//            }
//        });
//        add(jTextField3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 390, 303, 30));
//
//        jTextField4.setBackground(new java.awt.Color(97, 212, 195));
//        jTextField4.setFont(new java.awt.Font("Century Gothic", 0, 10)); // NOI18N
//        jTextField4.setForeground(new java.awt.Color(255, 255, 255));
//        jTextField4.setText("Enter");
//        jTextField4.setBorder(null);
//        jTextField4.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseClicked(java.awt.event.MouseEvent evt) {
//                jTextField4MouseClicked(evt);
//            }
//        });
//        jTextField4.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jTextField4ActionPerformed(evt);
//            }
//        });
//        add(jTextField4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 230, 303, 30));
//
//        jSeparator3.setForeground(new java.awt.Color(255, 255, 255));
//        add(jSeparator3, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 260, 303, 20));
//
//        jSeparator4.setForeground(new java.awt.Color(255, 255, 255));
//        add(jSeparator4, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 420, 303, 20));
//
//        jPanel3.setBackground(new java.awt.Color(36, 47, 65));
//        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//
//        jLabel5.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel5.setText("Conference");
//        jPanel3.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(439, 380, -1, -1));
//
//        jButton1.setBackground(new java.awt.Color(36, 47, 65));
//        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jButton1.setForeground(new java.awt.Color(255, 255, 255));
//        jButton1.setText("Search");
//        jButton1.setBorder(null);
//        jButton1.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent evt) {
//                jButton1ActionPerformed(evt);
//            }
//        });
//        jPanel3.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 40));
//
//        add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 460, 120, 40));
//
//        jLabel10.setFont(new java.awt.Font("Century Gothic", 0, 13)); // NOI18N
//        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
//        jLabel10.setText("CONFERENCE");
//        add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 370, -1, 20));
//
//        jSeparator5.setForeground(new java.awt.Color(255, 255, 255));
//        add(jSeparator5, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 340, 303, 20));
//    }// </editor-fold>                        
//
//    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        // TODO add your handling code here:
//       
//    }                                           
//
//    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        // TODO add your handling code here:
//    }                                           
//
//    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        // TODO add your handling code here:
//    }                                           
//
//    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {                                            
//        // TODO add your handling code here:
//    }                                           
//
//    
//    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {                                         
//        jTextField1.setText("");
//    }
//    
//    private void jTextField2MouseClicked(java.awt.event.MouseEvent evt) {                                         
//    	jTextField2.setText("");
//    }
//    
//    private void jTextField3MouseClicked(java.awt.event.MouseEvent evt) {                                         
//    	jTextField3.setText("");
//    }
//    
//    private void jTextField4MouseClicked(java.awt.event.MouseEvent evt) {                                         
//    	jTextField4.setText("");
//    }
//    
//    
//    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
//    	
//		
//		String query = "";
//		
//		//TODO:
//		
//		query = jTextField2.getText();
//		
//							
//		
//		
//		//////////////Table/////////
//		JFrame frame = new JFrame("Result");
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		JPanel panel = new JPanel();
//	
//		panel.setBackground(new java.awt.Color(97, 212, 195));
//        panel.setForeground(new java.awt.Color(255, 255, 255));
//        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
//		
//		JTable table = new JTable();
//		
//		table.setModel(new javax.swing.table.DefaultTableModel(
//	            new Object [][] {
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"},
//	                {"hallo", "hallo", "hallo", "hallo", "hallo", "hallo"}
//	            },
//	            new String [] {
//	                "Publication Title", "Year", "Electronic Edition", "Authors", "Conference", "Part of Proceedings"
//	            }
//	        ));
//		table.setFont(new java.awt.Font("Century Gothic", 0, 12));
//		table.setGridColor(new java.awt.Color(97, 212, 195));
////		table.setBackground(new java.awt.Color(97, 212, 195));
//		table.getTableHeader().setOpaque(false);
//		table.getTableHeader().setBackground(new java.awt.Color(97, 212, 195));
//		table.getTableHeader().setForeground(new java.awt.Color(255, 255, 255));
//		table.getTableHeader().setBorder(null);
//		table.getTableHeader().setFont(new java.awt.Font("Century Gothic", 0, 12));
//		
//		
//		
//		Container container = getRootPane();
//		
//		container.setLayout(new BorderLayout());
//		container.setBackground(new java.awt.Color(97, 212, 195));
//		container.add(table.getTableHeader(), BorderLayout.PAGE_START);
//		container.add(table, BorderLayout.CENTER);
//		frame.add(new JScrollPane(table));
//		frame.pack();
////		frame.add(panel);
//		frame.setVisible(true);		
//
//    }                                        
//
//
//    // Variables declaration - do not modify                     
//    private javax.swing.JButton jButton1;
//    private javax.swing.JLabel jLabel1;
//    private javax.swing.JLabel jLabel10;
//    private javax.swing.JLabel jLabel2;
//    private javax.swing.JLabel jLabel3;
//    private javax.swing.JLabel jLabel4;
//    private javax.swing.JLabel jLabel5;
//    private javax.swing.JLabel jLabel9;
//    private javax.swing.JPanel jPanel1;
//    private javax.swing.JPanel jPanel2;
//    private javax.swing.JPanel jPanel3;
//    private javax.swing.JSeparator jSeparator1;
//    private javax.swing.JSeparator jSeparator2;
//    private javax.swing.JSeparator jSeparator3;
//    private javax.swing.JSeparator jSeparator4;
//    private javax.swing.JSeparator jSeparator5;
//    private javax.swing.JTextField jTextField1;
//    private javax.swing.JTextField jTextField2;
//    private javax.swing.JTextField jTextField3;
//    private javax.swing.JTextField jTextField4;
//    // End of variables declaration                   
//}


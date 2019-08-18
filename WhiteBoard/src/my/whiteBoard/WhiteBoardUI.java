package my.whiteBoard;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WhiteBoardUI extends javax.swing.JFrame {
    
    static String portString;
    static int currentX, currentY, oldX, oldY, eraserOn;
    static DrawingPanel editor = null;
    static int flag = 0; // 1 means sending the pixel and 0 means not
    static String selectedColor = "";
    static MulticastSocket multicastSocket = null;
    static InetAddress group = null;
    static int portNum;
    static String hostname;

    public WhiteBoardUI() {
        initComponents();
        Container content = this.getContentPane();
        
        content.setLayout(new BorderLayout());
        try {
            editor = new DrawingPanel(oldX, oldY, currentX, currentY, selectedColor, eraserOn);
        } catch (Exception ex) {
            Logger.getLogger(WhiteBoardUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        editor.setFlagg(flag);
        editor.setEnabled(false);
        editor.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                oldX = (int)me.getX();
		oldY = (int)me.getY();
		if (eraserOn == 1) {
			editor.graphics2D.fillRect(oldX, oldY, 20, 20);
		}
		repaint();
		if (flag == 1) {
                    try {
			String pkt = oldX + " " + oldY + " " + oldX + " " + oldY + " " + selectedColor + " " + eraserOn;
			DatagramPacket dp = new DatagramPacket(pkt.getBytes(), pkt.length(), group, Integer.parseInt(port.getText()));
			multicastSocket.send(dp);
                    } catch (Exception e) {
			e.printStackTrace();
                      }
		} 
            }
        });
        
        editor.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent me) {
                currentX = me.getX();
                currentY = me.getY();
                if (editor.graphics2D != null)
                    if (eraserOn == 1) {
			editor.graphics2D.fillRect(oldX, oldY, 20, 20);
                    } else {
                        editor.graphics2D.drawLine(oldX, oldY, currentX, currentY);
                    }
                    repaint();
                    if (flag == 1) {
			try {
                            String pkt = oldX + " " + oldY + " " + currentX + " " + currentY + " " + selectedColor + " " + eraserOn;
                            DatagramPacket dp = new DatagramPacket(pkt.getBytes(), pkt.length(), group, Integer.parseInt(port.getText()));
                            multicastSocket.send(dp);
			} catch (Exception e) {
                            e.printStackTrace();
			}
                    }
                    oldX = currentX;
                    oldY = currentY; 
            }
        });
        
        drawPanel.add(editor, BorderLayout.CENTER);
}

    public static void readThread(MulticastSocket mcSocket, InetAddress group, int thePort) {
        byte[] buff = new byte[1024];
        DatagramPacket dp = new DatagramPacket(buff, buff.length, group, thePort);
        try {
            mcSocket.receive(dp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String packet = "";
        packet = new String(buff, 0, dp.getLength());
        String message[] = packet.split(" ");
        oldX = Integer.parseInt(message[0]);
        oldY = Integer.parseInt(message[1]);
        currentX = Integer.parseInt(message[2]);
        currentY = Integer.parseInt(message[3]);
        selectedColor = new String(message[4]);
        eraserOn = Integer.parseInt(message[5]);
        if (eraserOn == 2) {
            editor.rclear();
        } else {
            editor.rdrawing(oldX, oldY, currentX, currentY, selectedColor, eraserOn);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        leftPanel = new javax.swing.JPanel();
        pencil = new javax.swing.JButton();
        erase = new javax.swing.JButton();
        reset = new javax.swing.JButton();
        topPanel = new javax.swing.JPanel();
        host = new javax.swing.JTextField();
        port = new javax.swing.JTextField();
        ready = new javax.swing.JButton();
        drawPanel = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("WhiteBoard Application");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });

        pencil.setText("Pencil");
        pencil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pencilActionPerformed(evt);
            }
        });

        erase.setText("Erase");
        erase.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eraseActionPerformed(evt);
            }
        });

        reset.setText("Clear All");
        reset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout leftPanelLayout = new javax.swing.GroupLayout(leftPanel);
        leftPanel.setLayout(leftPanelLayout);
        leftPanelLayout.setHorizontalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(reset, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(erase, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pencil, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        leftPanelLayout.setVerticalGroup(
            leftPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(leftPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pencil)
                .addGap(34, 34, 34)
                .addComponent(erase)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(reset)
                .addGap(25, 25, 25))
        );

        host.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hostActionPerformed(evt);
            }
        });

        ready.setText("Join Group");
        ready.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                readyActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout topPanelLayout = new javax.swing.GroupLayout(topPanel);
        topPanel.setLayout(topPanelLayout);
        topPanelLayout.setHorizontalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(ready, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        topPanelLayout.setVerticalGroup(
            topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(topPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(topPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(host, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ready))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        drawPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        drawPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                drawPanelMouseClicked(evt);
            }
        });
        drawPanel.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addComponent(drawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 528, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(135, 135, 135)
                        .addComponent(leftPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(topPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(drawPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(30, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetActionPerformed
        editor.clear();        // TODO add your handling code here:
    }//GEN-LAST:event_resetActionPerformed

    private void eraseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eraseActionPerformed
        editor.erase();        // TODO add your handling code here:
    }//GEN-LAST:event_eraseActionPerformed

    private void pencilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pencilActionPerformed
        editor.setBlack();        // TODO add your handling code here:
    }//GEN-LAST:event_pencilActionPerformed

    private void readyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_readyActionPerformed
        portString=port.getText();
        if (multicastSocket != null) {
            try {
                multicastSocket.leaveGroup(group);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        
        hostname = new String(host.getText());
        portNum = Integer.parseInt(port.getText());
        try {
            group = InetAddress.getByName(hostname);
            multicastSocket = new MulticastSocket(portNum);
            System.out.println("You are now Joined to group with IP address "+host.getText()+" and port number "+port.getText());
            
            multicastSocket.setTimeToLive(5);
            multicastSocket.joinGroup(group);
            
            editor.setEnabled(true);
            flag = 1;
            editor.setFlagg(flag);
            editor.setGroup(group);
            editor.setPort(portNum);
            editor.setMCSocket(multicastSocket);
            Thread t = new Thread(new Runnable() {
		public void run() {
                    try {
			while (true) {
			readThread(multicastSocket, group, portNum);
			}
                    } catch (Exception e) {
			e.printStackTrace();
                    }
		}
            });
            
            try {
                t.start();
            } catch (Exception e) {
                e.printStackTrace();
            }

        } catch (Exception e2) {
            e2.printStackTrace();
        }  
    }//GEN-LAST:event_readyActionPerformed

    private void drawPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_drawPanelMouseClicked
        //System.out.println("Panel Clicked");// TODO add your handling code here:
    }//GEN-LAST:event_drawPanelMouseClicked

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked
        //System.out.println("Frame Clicked");// TODO add your handling code here:
    }//GEN-LAST:event_formMouseClicked

    private void hostActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hostActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hostActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(WhiteBoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WhiteBoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WhiteBoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WhiteBoardUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WhiteBoardUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel drawPanel;
    private javax.swing.JButton erase;
    private javax.swing.JTextField host;
    private javax.swing.JPanel leftPanel;
    private javax.swing.JButton pencil;
    private javax.swing.JTextField port;
    private javax.swing.JButton ready;
    private javax.swing.JButton reset;
    private javax.swing.JPanel topPanel;
    // End of variables declaration//GEN-END:variables
}

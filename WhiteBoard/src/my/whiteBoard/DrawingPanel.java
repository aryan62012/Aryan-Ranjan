package my.whiteBoard;

import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseListener;
import static com.sun.java.accessibility.util.AWTEventMonitor.addMouseMotionListener;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import javafx.scene.input.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JComponent;

public class DrawingPanel extends JComponent {

	private static final long serialVersionUID = 1L;
        
	public MulticastSocket multicastSocket;
	public InetAddress group;
	public int portNum;
	public int flag = 0;
	int currentX, currentY, oldX = 0, oldY = 0, eraser = 0;
	String selectedColor;
	int eraserOn = 0;
	Image image;
	Graphics2D graphics2D;
        
	public void setFlagg(int flag) {
		this.flag = flag;
	}
	
	public void setGroup(InetAddress group) {
		this.group = group;
	}

	public void setPort(int thePort) {
		this.portNum= thePort;
	}

	public void setMCSocket(MulticastSocket mc) {
		this.multicastSocket = mc;
	}

	
        public final void rdrawing(int oldX, int oldY, int currentX, int currentY, String coloring, int eraser) {
		if (graphics2D != null)
			switch (coloring) {
                            case "black":
                                    graphics2D.setPaint(Color.black);
                                    break;
                            case "white":
                                    graphics2D.setPaint(Color.white);
                                    break;
                            default:
                                    break;
                        }
		if (eraser == 1) {
			graphics2D.fillRect(oldX, oldY, 20, 20);
		} 
                else {
			graphics2D.drawLine(oldX, oldY, currentX, currentY);
		}
		
		repaint();
	}

	public DrawingPanel(int oX, int oY, int cX, int cY, String ccolor, int eraser) throws Exception {
                this.currentX = cX;
		this.currentY = cY;
		this.oldX = oX;
		this.oldY = oY;
		setDoubleBuffered(false);
                this.addMouseListener(new MouseAdapter() {
                    public void mousePressed(MouseEvent e) {
                    System.out.println("Mouse Pressed");
                    oldX = (int)e.getX();
                    oldY = (int)e.getY();
                    if (eraserOn == 1) {
			graphics2D.fillRect(oldX, oldY, 30, 30);
                    }
                    repaint();
                    
                    if (flag == 1) {
                        try {
                            
                            String pkt = oldX + " " + oldY + " " + oldX + " " + oldY + " " + selectedColor + " " + eraserOn;
                            
                            DatagramPacket dp = new DatagramPacket(pkt.getBytes(), pkt.length(), group, 5001);
                            
                            multicastSocket.send(dp);
                        } catch (Exception exp) {
                            exp.printStackTrace();
                        }
                    }
                    }
		});

        }		
        
	public void paintComponent(Graphics g) {
		if (image == null) {
			image = createImage(getSize().width, getSize().height);
			graphics2D = (Graphics2D) image.getGraphics();
			graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}
		g.drawImage(image, 0, 0, null);
	}

	public void erase() {
		graphics2D.setPaint(Color.white);
		selectedColor = "white";
		eraserOn = 1;
		if (flag == 1) {
			try {
				String pkt = getSize().width + " " + getSize().height + " " + getSize().width + " " + getSize().height + " " + selectedColor + " " + 1;
				DatagramPacket dp = new DatagramPacket(pkt.getBytes(), pkt.length(), group, Integer.parseInt(WhiteBoardUI.portString));
				multicastSocket.send(dp);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		//setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new ImageIcon("eraser.png").getImage(), new Point(0, 0), "e"));
		repaint();
	}

	public void rclear() {
		eraserOn = 0;
		graphics2D.setPaint(Color.white);
		graphics2D.fillRect(0, 0, getSize().width, getSize().height);
		graphics2D.setPaint(Color.black);
		repaint();
	}

	public void clear() {
            this.eraserOn = 0;
            graphics2D.setPaint(Color.white);
            selectedColor = "white";
            graphics2D.fillRect(0, 0, getSize().width, getSize().height);
            graphics2D.setPaint(Color.black);
            selectedColor = "black";
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if (flag == 1) {
		try {
                    String pkt = oldX + " " + oldY + " " + currentX + " " + currentY + " " + selectedColor + " " + 2;
                    DatagramPacket dp = new DatagramPacket(pkt.getBytes(), pkt.length(), group, Integer.parseInt(WhiteBoardUI.portString));
                    multicastSocket.send(dp);
                } catch (Exception e1) {
                    e1.printStackTrace();
		}
            }
            repaint();
	}

	public void setBlack() {
            eraserOn = 0;
            graphics2D.setPaint(Color.black);
            selectedColor= "black";
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
            if (flag == 1) {
		try {
                    String pkt = oldX + " " + oldY + " " + currentX + " " + currentY + " " + selectedColor+ " " + eraserOn;
                    DatagramPacket dp = new DatagramPacket(pkt.getBytes(), pkt.length(), group, Integer.parseInt(WhiteBoardUI.portString));
                    multicastSocket.send(dp);
                } catch (Exception e1) {
                    e1.printStackTrace();
		}
            }
		repaint();
	}
}


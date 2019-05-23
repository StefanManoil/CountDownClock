package gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ImagesPanel extends JPanel{
	 ImageIcon backgroundImage;
	  //the constructor
	  public ImagesPanel(){
		   try {
			   //backgroundImage = new ImageIcon(ImageIO.read(Main.class.getResourceAsStream("/images/background.jpg")));
			   backgroundImage = new ImageIcon(ImageIO.read(new File(System.getProperty("user.home"), "/Desktop/CountdownClock/resources/images/background.jpg")));
			}
		    catch (IOException ioe) {
				System.out.println("Couldn't load the background image");
				ioe.printStackTrace();
			}    
	     repaint();
	  }
	  protected void paintComponent(Graphics g) {
		// Repaints the frame and its components
	      //super.paint(g);
	      // Declare and initialize a Graphics2D object
	      Graphics2D g2 = (Graphics2D) g;
	      
	      g2.drawImage(backgroundImage.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
		  
	  }

}

import javax.swing.SwingUtilities;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 * La fenêtre principale du programme.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class ParkingMain {
	
	/**
	 * instancie la classe principale et configure les proprietes importante
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
				AppFrame app = new AppFrame();

				app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				app.setTitle("Smart Parking Version 0.1.0");
				app.setPreferredSize(new Dimension(500, 600));

				ImageIcon img = new ImageIcon(getClass().getClassLoader().getResource("images/icon.png"));
				app.setIconImage(img.getImage());

				app.setVisible(true);
	        }
	    });
		
	}
	
}


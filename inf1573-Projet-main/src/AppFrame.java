import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * Classe d'initialisation des éléments visuels du programme.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class AppFrame extends JFrame {
	private static final long serialVersionUID = 146571341364742862L;

	/**
	 * Construit la fenetre principale et y ajoute les éléments visuelles nécessaire.
	 */
    public AppFrame(){
        /* Abres de la structure
        -frame
            -outer
                -topPanel
                    -ParkingFrame
                -bottomPanel
                    -button1
                    -button2
                    -button3
        */
    	
        // Intitialisation de "outer"
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Intitialisation de "topPanel"
        JPanel topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);	
	    topPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        MapFrame parkingFrame = new MapFrame();	
        topPanel.add(parkingFrame);

        //Intitialisation de "BottomPanel"
        JPanel bottomPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        bottomPanel.setBackground(Color.WHITE);	
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JButton annulerButton = new JButton("Annuler/Réinitialiser");
        annulerButton.setEnabled(false);
        annulerButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
            	parkingFrame.cancelSearch(outer);
            	annulerButton.setEnabled(false);
            }
        });

		JButton rechercheButton = new JButton("Recherche");
		rechercheButton.setEnabled(false);
		rechercheButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
            	annulerButton.setEnabled(true);
            	rechercheButton.setEnabled(false);
            	parkingFrame.runSearch(outer, bottomPanel);
            }
        });
        
		bottomPanel.add(rechercheButton);
        bottomPanel.add(annulerButton);
       
        // Initialisation de la carte
        ImageIcon backgroundIMG = new ImageIcon(getClass().getClassLoader().getResource("images/parking_overlay.png")); 
        JLabel background = new JLabel();
        background.setIcon(backgroundIMG);
        background.setBounds(20, 30, 400, 400);
        background.setOpaque(false);
        background.getPreferredSize();
        background.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
            	rechercheButton.setEnabled(true);
            	parkingFrame.selectCell(event.getPoint());
            	outer.repaint();
            }
        });

        //On ajoute 
        outer.add(background, null);
        outer.add(bottomPanel, BorderLayout.SOUTH);
        outer.add(topPanel, BorderLayout.CENTER);
        this.add(outer);
        this.setResizable(false);
        this.pack();

        this.setBackground(Color.WHITE);
    }
				
					
}

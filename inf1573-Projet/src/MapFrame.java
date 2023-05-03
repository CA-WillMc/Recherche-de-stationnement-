import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;
import java.awt.Color;
import javax.swing.JPanel;

import com.coggers.parking.Cell;
import com.coggers.parking.Grid;
import com.coggers.parking.Road;

/**
 * La classe décrivant l'agencement de la carte visuel.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class MapFrame extends JPanel {
	private static final long serialVersionUID = 3697766966396676042L;
	private Grid grid;
	private JPanel container;
	
	/**
	 * Construit la carte visuel et y ajoute les éléments visuelles nécessaire.
	 */
	public MapFrame() {
		int gridWidth = 400;
		int gridHeight = 400;
		
		int map[][] = this.buildMap();
		grid = new Grid(gridWidth, gridHeight, map);
		
		container = new JPanel(new BorderLayout());
		container.setPreferredSize(new Dimension(gridWidth, gridHeight));
		
		// Les options par défaut de GridBagConstraints permettent d'aligner
		// verticalement et horizontalement au centre le contenu du panel container
		container.add(grid, BorderLayout.NORTH);

		this.setBackground(Color.WHITE);
		this.add(container);
		
	}
	
	/**
	 * Sélectionne le noeud de la grille se trouvant à la position.
	 * @param position Les coordonnées X et Y du noeud (en pixel).
	 */
	public void selectCell(Point position) {
		grid.reset();
		Cell cellAtPosition = grid.getCellAtPosition(position);
		if (cellAtPosition instanceof Road) {
			if (grid.getStartCell() != null) ((Road) grid.getStartCell()).resetColor();
			grid.setStartCell(cellAtPosition);
			grid.getStartCell().setColor(Color.BLUE);
			grid.update();
		}
	}
	
	/**
	 * Démarre la recherche de l'espace de stationnement optimal.
	 */
	public void runSearch(JPanel mapPanel, JPanel buttonPanel) {
		grid.executeSearch(mapPanel, buttonPanel);
	}
	
	/**
	 * Arrête la recherche de l'espace de stationnement optimal.
	 */
	public void cancelSearch(JPanel mapPanel) {
		grid.stopSearch(mapPanel);
	}
	
	/**
	 * Lit un fichier externe et construit la représentation d'une carte à l'aide d'une matrice.
	 * @return Une matrice d'entiers
	 */
	private int[][] buildMap() {
		ArrayList<int[]> mapList = new ArrayList<int[]>();
		
		InputStream mapInput = MapFrame.class.getResourceAsStream("maps/map.txt");
		Scanner reader = new Scanner(mapInput);
		
		try {
			while (reader.hasNextLine()) {
				String data = reader.nextLine();
				String tokens[] = data.split(", ");
				int numbers[] = new int[tokens.length];
			
			    for (int i = 0; i < tokens.length; i++) {
			        numbers[i] = Integer.parseInt(tokens[i]);
			    }
			
			    mapList.add(numbers);
			}
	     
			reader.close();
	     
			int map[][] = new int[mapList.size()][mapList.get(0).length];
			for (int i = 0; i < mapList.size(); i++) {
				map[i] = mapList.get(i);
			}
			
			return map;
		}  catch (IllegalStateException e) {
	      System.out.println("Map could not be read.");
	      return new int[0][0];
	    }
	}
}
package com.coggers.parking;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.coggers.parking.Spot.Status;

/**
 * Une représentation visuelle d'une grille permettant de modéliser un graphe
 * traversable par un algorithme de Dijkstra.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Grid extends JPanel {	
	private static final long serialVersionUID = 5268017295202116818L;
	private int width;
	private int height;
	
	private int rows;
	private int columns;

	private int rowHeight;
	private int columnWidth;
	
	private Cell cells[][];
	
	private Cell startCell;
	private ArrayList<Cell> shortestPath;
	
	private Pathfinder pathfinder;
	private Cell currentCell;
	boolean isMoving = false;

	/**
	 * Construit la grille qui modélise les routes et les espaces de stationnement.
	 * @param width La largeur de la grille.
	 * @param height La hauteur de la grille.
	 * @param map La représentation tabulaire de la carte
	 */
	public Grid(int width, int height, int[][] map) {		
		this.width = width;
		this.height = height;
		
		this.rows = map.length;
		this.columns = map[0].length;
		
		this.rowHeight = this.height / rows;
		this.columnWidth = this.width / columns;
		
		this.cells = new Cell[rows][columns];
		
		// L'ajout de 1 pixel permet de s'assurer que toutes les bordures soient visibles
		this.setPreferredSize(new Dimension(width + 1, height + 1));
		
		this.pathfinder = new Pathfinder();
		this.shortestPath = new ArrayList<Cell>();
		
		// Constuction de la grille
		this.build(map);
	}
	
	/**
	 * Retourne le noeud de départ.
	 * @return startCell Le noeud de départ.
	 */	
	public Cell getStartCell() {
		return startCell;
	}
	
	/**
	 * Assigne un noeud au noeud de départ.
	 * @param cell Le noeud.
	 */	
	public void setStartCell(Cell cell) {
		startCell = cell;
	}
	
	/**
	 * Retourne le noeud à la position.
	 * @param position Les coordonnées X et Y du noeud (en pixel).
	 * @return cell Le noeud à la position X et Y.
	 */	
	public Cell getCellAtPosition(Point position) {
		int cellX = position.x / columnWidth;
		int cellY = position.y / rowHeight;
		
		return cells[cellY][cellX];
	}
	
	/**
	 * Met à jour la représentation graphique de la grille en appelant la méthode repaint de JPanel.
	 */	
	public void update() {
		this.repaint();
	}
	
	/**
	 * Réinitialise la grille pour que la prochaine recherche de chemin soit exacte .
	 */	
	public void reset() {
		shortestPath.clear();
		for (int i = 0; i < this.rows; i++) {
			for (int j = 0; j < this.columns; j++) {
				cells[i][j].clearPathfindingMetrics();
			}
		}
	}
	
	/**
	 * Construit le tableau de noeuds qui modélise les routes et les espaces de stationnement.
	 * @param map La représentation tabulaire de la carte
	 */
	private void build(int[][] map) {		
		// Construction de la grille de noeuds
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				Cell currentCell;
				
				if (map[i][j] == 0) {
					currentCell = new Wall(new Point(j * columnWidth, i * rowHeight), columnWidth, rowHeight);
				} else if (map[i][j] > 0 && map[i][j] <= 100) {
					currentCell = new Road(new Point(j * columnWidth, i * rowHeight), columnWidth, rowHeight, map[i][j]);
				} else if (map[i][j] == 200) {
					currentCell = new Spot(new Point(j * columnWidth, i * rowHeight), columnWidth, rowHeight, Status.FREE);
				} else if (map[i][j] == 300) {
					currentCell = new Spot(new Point(j * columnWidth, i * rowHeight), columnWidth, rowHeight, Status.OCCUPIED);
				} else {
					currentCell = new Wall(new Point(j * columnWidth, i * rowHeight), columnWidth, rowHeight);
				}

				cells[i][j] = currentCell;
			}
		}
		
		// Ajout des arêtes à chaque noeud de type Road
		// Les arêtes diagonales sont omises
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if ((cells[i][j] instanceof Road)) {
					int currentCellTraffic = ((Road) cells[i][j]).getTraffic();
					int destinationCellTraffic = 0;
					
					if (i + 1 < rows && !(cells[i + 1][j] instanceof Wall)) {
						destinationCellTraffic = cells[i + 1][j] instanceof Road ? ((Road) cells[i + 1][j]).getTraffic() : 0;
						cells[i][j].addEdge(new Edge(columnWidth * ((currentCellTraffic + destinationCellTraffic) / 2), cells[i + 1][j]));
					}
					
					if (j + 1 < columns && !(cells[i][j + 1] instanceof Wall)) {
						destinationCellTraffic = cells[i][j + 1] instanceof Road ? ((Road) cells[i][j + 1]).getTraffic() : 0;
						cells[i][j].addEdge(new Edge(rowHeight * ((currentCellTraffic + destinationCellTraffic) / 2), cells[i][j + 1]));
					}
					
					if (i - 1 >= 0  && !(cells[i - 1][j] instanceof Wall)) {
						destinationCellTraffic = cells[i - 1][j] instanceof Road ? ((Road) cells[i - 1][j]).getTraffic() : 0;
						cells[i][j].addEdge(new Edge(columnWidth * ((currentCellTraffic + destinationCellTraffic) / 2), cells[i - 1][j]));
					}
					
					if (j - 1 >= 0 && !(cells[i][j - 1] instanceof Wall)) {
						destinationCellTraffic = cells[i][j - 1] instanceof Road ? ((Road) cells[i][j - 1]).getTraffic() : 0;
						cells[i][j].addEdge(new Edge(rowHeight * ((currentCellTraffic + destinationCellTraffic) / 2), cells[i][j - 1]));
					}
				}
			}
		}
	}
	
	/**
	 * Exécute la recherche de chemin le plus court. Utilise un SwingWorker pour effectué la recherche à l'aide de threads.
	 * @param mapPanel L'instance d'un JPanel qui contient la grille.
	 */
	public void executeSearch(JPanel mapPanel, JPanel buttonPanel) {
		Grid that = this;
		SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
			protected Void doInBackground() throws SpotTakenException {
				for (int i = 0; i < rows; i++) {
					for (int j = 0; j < columns; j++) {
						Cell c = cells[i][j];
						if (c instanceof Spot && ((Spot) c).getStatus() == Status.FREE) {
							ArrayList<Cell> path = pathfinder.findShortestPath(startCell, c, that);
							if (shortestPath.size() == 0) {
								shortestPath = path;
							} else {
								double previousPathDistanceFromStart = shortestPath.get(shortestPath.size() - 1).getDistanceFromStart();
								double pathDistanceFromStart = path.get(path.size() - 1).getDistanceFromStart();
								if (pathDistanceFromStart < previousPathDistanceFromStart) {
									shortestPath = path;
								}
							}
						}
					}
				}
				
				long currentTime = System.currentTimeMillis();
				ArrayList<Cell> shortestPathCopy = new ArrayList<Cell>(shortestPath);
				isMoving = true;
				((Spot) shortestPathCopy.get(shortestPath.size() - 1)).setStatus(Status.RESERVED);
				
				do {
					long timeSinceLastStep = System.currentTimeMillis() - currentTime;
					if (timeSinceLastStep >= 250) {
						currentTime = System.currentTimeMillis();
						
						// Simulation du scénario où un conducteur se fait voler sa place réservée
						double exceptionSimulate = Math.random() * 100;
						if (exceptionSimulate >= 97.5) {
							throw new SpotTakenException();
						}
						
						if (shortestPathCopy.get(0) != null) {
							currentCell = shortestPathCopy.get(0);
							
							currentCell.setColor(Color.WHITE);
							if (currentCell instanceof Spot) {
								((Spot) currentCell).setStatus(Status.OCCUPIED);
							}
							shortestPathCopy.remove(0);
							
							mapPanel.repaint();
						} else {
							isMoving = false;
						}
					}
				} while(isMoving);
				
				return null;
			}
			
			@Override
			protected void done() {
		        if(!super.isCancelled()) {
		            try {
						super.get();
					} catch (InterruptedException | ExecutionException e) {
						if (e.getCause() instanceof SpotTakenException) {
							((Spot) shortestPath.get(shortestPath.size() - 1)).setStatus(Status.OCCUPIED);
							that.stopSearch(mapPanel);
							that.setStartCell(currentCell);
							currentCell.setColor(Color.BLUE);
							
							that.reset();
							
							for (Component c : buttonPanel.getComponents()) {
								if (c instanceof JButton) {
									JButton button = (JButton) c;
									if (button.getText().equals("Recherche")) {
										button.setEnabled(true);
									}
									if (button.getText().equals("Annuler/Réinitialiser")) {
										button.setEnabled(false);
									}
								}
							}
							
							mapPanel.repaint();
							JOptionPane.showMessageDialog(null, "Votre espace réservé n'est plus disponible. Veuillez relancer la recherche.", e.getCause().getMessage(), JOptionPane.ERROR_MESSAGE);
						}
					}
		        }
			}
		};
		
		worker.execute();
	}
	
	/**
	 * Arrête la recherche de chemin le plus court.
	 * @param mapPanel L'instance d'un JPanel qui contient la grille.
	 */
	public void stopSearch(JPanel mapPanel) {
		isMoving = false;
		for (Cell c : shortestPath) {
			if (c instanceof Road) {
				((Road) c).resetColor();
			}
			
			if (c instanceof Spot && ((Spot) c).getStatus() == Status.RESERVED) {
				((Spot) c).setStatus(Status.FREE);
			}
		}
		mapPanel.repaint();		
	}
	
	
	@Override
	/**
	 * Réécriture de la méthode paintComponent héritée de JPanel pour dessiner chaque noeud.
	 * @param g L'objet Graphics utilisé pour dessiner dans le JPanel.
	 */
	public void paintComponent(Graphics g) {		
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				cells[i][j].draw(g);
			}
		}
	}

}

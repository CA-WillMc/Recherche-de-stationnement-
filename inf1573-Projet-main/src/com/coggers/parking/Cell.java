package com.coggers.parking;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;

/**
 * Une représentation visuelle d'un noeud d'un graphe pouvant être dessiné sur une grille.
 * Le noeud contient les attributs nécessaire pour permettre la recherche
 * du plus court chemin par l'algorithme de Dijkstra.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Cell implements Comparable<Cell> {
	private Point position;
	private int width;
	private int height;
	private Color color;
	private double distanceFromStart;
	private Cell predecessor;
	private ArrayList<Edge> edges;

	/**
	 * Construit l'objet de type Cell.
	 * @param position Les coordonnées X et Y du noeud (en pixel).
	 * @param width La largeur du noeud.
	 * @param height La hauteur du noeud.
	 */
	public Cell(Point position, int width, int height) {
		this.position = position;
		this.width = width;
		this.height = height;
		this.color = Color.WHITE;
		this.distanceFromStart = Double.POSITIVE_INFINITY;
		this.edges = new ArrayList<Edge>();
	}

	/**
	 * Retourne la couleur du noeud.
	 * @return color La couleur du noeud.
	 */	
	public Color getColor() {
		return color;
	}
	
	/**
	 * Assigne une couleur au noeud.
	 * @param color La couleur du noeud.
	 */
	public void setColor(Color color) {
		this.color = color;
	}
	
	/**
	 * Retourne la position en X et Y du noeud.
	 * @return position La position en X et Y du noeud.
	 */	
	public Point getPosition() {
		return position;
	}
	
	/**
	 * Retourne la hauteur du noeud.
	 * @return height La hauteur du noeud.
	 */	
	public int getHeight() {
		return height;
	}
	
	/**
	 * Retourne la largeur du noeud.
	 * @return width La largeur du noeud.
	 */	
	public int getWidth() {
		return width;
	}
	
	/**
	 * Retourne toutes les arêtes accessibles à partir de ce noeud.
	 * @return edges La liste des arêtes accessibles.
	 */	
	public ArrayList<Edge> getEdges() {
		return this.edges;
	}
	
	/**
	 * Retourne la distance de ce noeud par rapport au noeud de départ.
	 * @return distanceFromStart La distance du noeud par rapport au noeud de départ.
	 */
	public double getDistanceFromStart() {
		return this.distanceFromStart;
	}
	
	/**
	 * Assigne la distance de ce noeud par rapport au noeud de départ.
	 * @param distanceFromStart La distance du noeud par rapport au noeud de départ.
	 */
	public void setDistanceFromStart(double distanceFromStart) {
		this.distanceFromStart = distanceFromStart;
	}
	
	/**
	 * Réinitialise la valeur par défaut de la distance de ce noeud par rapport au noeud de départ.
	 */
	private void clearDistanceFromOrigin() {
		this.distanceFromStart = Double.POSITIVE_INFINITY;
	}
	
	/**
	 * Retourne le prédécesseur de ce noeud.
	 * @return predecessor Le prédécesseur de ce noeud.
	 */
	public Cell getPredecessor() {
		return this.predecessor;
	}
	
	/**
	 * Assigne le prédécesseur de ce noeud.
	 * @param predecessor Le prédécesseur de ce noeud.
	 */
	public void setPredecessor(Cell predecessor) {
		this.predecessor = predecessor;
	}
	
	/**
	 * Supprime la référence du prédécesseur de ce noeud.
	 */
	private void clearPredecessor() {
		this.predecessor = null;
	}
	
	/**
	 * Réinitialise toutes les données reliées à la recherche de chemin du noeud.
	 */
	public void clearPathfindingMetrics() {
		this.clearDistanceFromOrigin();
		this.clearPredecessor();
	}
	
	/**
	 * Ajoute une arête à ce noeud.
	 * @param edge Une arête à être ajoutée à ce noeud.
	 */
	public void addEdge(Edge edge) {
		this.edges.add(edge);		
	}
	
	/**
	 * Dessine un noeud dans une composante Swing.
	 * @param g Un objet de type Graphics.
	 */
	public void draw(Graphics g) {
		g.setColor(color);
		g.fillRect(position.x, position.y, width, height);
		g.setColor(Color.BLACK);
		g.drawRect(position.x, position.y, width, height);
	}

	@Override
	/**
	 * Compare deux noeuds.
	 * Cette méthode permet d'ajouter des objets de type Cell à une liste de type PriorityQueue.
	 * @param o Le noeud à comparer avec ce noeud. 
	 * @return Le résultat de la comparaison (-1, 0, 1).
	 **/
	public int compareTo(Cell o) {
		return Double.compare(distanceFromStart, o.distanceFromStart);
	}
	
	@Override
	/**
	 * Convertit l'objet noeud en une chaîne de caratères.  
	 * @return Une chaîne de caratère représentant les coordonnées du noeud.
	 **/
	public String toString() {
		return "x: " + this.position.x / this.width + " y: " + this.position.y / this.height;
	}
}

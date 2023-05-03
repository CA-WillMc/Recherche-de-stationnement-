package com.coggers.parking;

import java.util.*;

/**
 * Trouve le chemin le plus court entre deux noeuds d'un graphe.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Pathfinder {
	// Les noeuds qui doivent être parcourus
	private PriorityQueue<Cell> openList;

	/**
	 * Construit une instance de l'algorithme de recherche de chemin.
	 */
	public Pathfinder() {
		// Une PriorityQueue permet à l'algorithme de simplement prendre le noeud
		// se trouvant au haut de la liste et avoir la garantie qu'il s'agisse du
		// noeud ayant le plus petit coût.
		this.openList = new PriorityQueue<Cell>();
	}

	/**
	 * Trouve le chemin le plus court entre deux noeuds d'un graphe. 
	 * @param start Le noeud de départ.
	 * @param end Le noeud d'arrivée
	 * @param grid Le graphe contenant les noeuds.
	 */
	public ArrayList<Cell> findShortestPath(Cell start, Cell end, Grid grid) {
		start.setDistanceFromStart(0);
		openList.add(start);

		// Boucle tant qu'il y a des noeuds à visiter
		while (!openList.isEmpty()) {
			Cell current = openList.poll();
			
			// Sort de la boucle si le noeud visité est la destination
			if (current == end) {
				break;
			}

			// Examine les arêtes du noeud présentement visité pour trouver son meilleur voisin
			for (Edge e : current.getEdges()) {
				// Calcule de la distance depuis le noeud de départ si on emprunte cette arête
				double distanceFromStart = current.getDistanceFromStart() + e.getCost();
				
				// Lit le noeud au bout de l'arête présentement examinée
				Cell neighbour = (Cell) e.getDestination();

				// Si la distance calculée est plus petite que la valeur neighbour.getDistanceFromStart()
				// nous avons trouvé un chemin plus court que précédemment trouvé
				if (distanceFromStart < neighbour.getDistanceFromStart()) {
					// Supprime le noeud de la PriorityQueue
					openList.remove(neighbour);
					
					// Mets à jour les métriques du noeud voisin
					neighbour.setDistanceFromStart(distanceFromStart);
					neighbour.setPredecessor(current);
					
					// Ajoute le noeud mit à jour à la PriorityQueue où le noeud ayant
					// le plus petit coût est au haut de la liste
					openList.add(neighbour);
				}
			}
		}

		// La recherche est terminée, on ajoute le noeud d'arrivée
		// à la liste représentant le chemin le plus court
		ArrayList<Cell> shortestPath = new ArrayList<Cell>();
		Cell current = end;
		shortestPath.add(current);

		// À partir du noeud d'arrivée, navigue d'un prédécesseur à l'autre afin de
		// bâtir le chemin calculé par l'algorithme de Dijkstra
		while (current.getPredecessor() != null) {
			shortestPath.add((Cell) current.getPredecessor());
			current = (Cell) current.getPredecessor();
		}
		
		// Inverse l'ordre de la liste représentant le chemin le plus court
		// pour faire en sorte que le noeud à l'index 0 soit le noeud de départ
		Collections.reverse(shortestPath);

		return shortestPath;
	}
}
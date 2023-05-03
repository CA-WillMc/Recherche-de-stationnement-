package com.coggers.parking;

/**
 * Une arête avec poids reliant deux noeuds d'un graphe.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Edge {	
	private int cost;	
	private Cell destination;
	
	/**
	 * Construit une arête avec poids pour connecter deux noeuds ensemble.
	 * @param cost Le coût pour traverser cette arête.
	 * @param destination La destination de cette arête pour ce noeud
	 */
	public Edge(int cost, Cell destination){
		this.cost = cost;
		this.destination = destination;
	}
	
	/**
	 * Retourne le coût pour traverser cette arête.
	 * @return cost Le coût pour traverser cette arête.
	 */
	public int getCost(){
		return cost;
	}
	
	/**
	 * Retourne la destination de cette arête
	 * @return destination La destination de cette arête.
	 */
	public Cell getDestination(){
		return destination;
	}	
	
	/**
	 * Assigne un coût à cette arête.
	 * @param cost Le coût pour traverser cette arête.
	 */
	public void setCost(int cost){
		this.cost = cost;
	}
}

package com.coggers.parking;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * Une spécialisation de la classe Cell pour représenter visuellement un noeud de type stationnement.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Spot extends Cell {
	/**
	 *  Statuts possibles d'un espace de stationnement
	 */
	enum Status {
		/**
		 * Le statut disponible
		 */
		FREE,
		/**
		 * Le statut occupé
		 */
		OCCUPIED,
		/**
		 * Le statut réservé
		 */
		RESERVED
	};
	
	Status status;
	private Color labelColor;
	
	/**
	 * Construit l'objet de type Spot.
	 * Fait appel au constructeur de la classe mère et assigne la couleur noire au noeud.
	 * @param position Les coordonnées X et Y du noeud (en pixel).
	 * @param width La largeur du noeud.
	 * @param height La hauteur du noeud.
	 * @param status Le statut de l'espace de stationnement.
	 */
	public Spot(Point position, int width, int height, Status status) {
	  super(position, width, height);
	  
	  this.setColor(Color.WHITE);
	  this.setStatus(status);
	}
	
	/**
	 * Assigne une couleur à l'espace de stationnement en fonction de son statut.
	 * @param status Le statut de l'espace de stationnement.
	 */
	public void setStatus(Status status) {
		this.status = status;
		switch (status) {
			case FREE:
				this.labelColor = Color.GREEN;
				break;
			case OCCUPIED:
				this.labelColor = Color.RED;
				break;
			case RESERVED:
				this.labelColor = Color.ORANGE;
				break;
		}
	}
	
	/**
	 * Retourne le statut de l'espace de stationnement.
	 * @return
	 */
	public Status getStatus() {
		return this.status;
	}
	
	/**
	 * Dessine un noeud dans une composante Swing.
	 * @param g Un objet de type Graphics.
	 */
	public void draw(Graphics g) {
		super.draw(g);
		
		g.setColor(this.labelColor);
		g.drawOval(getPosition().x + 2, getPosition().y + 2, getWidth() - 4, getHeight() - 4);
		g.drawString("P", getPosition().x + (getWidth() / 2) - 4, getPosition().y + (getHeight() / 2) + 4);
	}
}

package com.coggers.parking;

import java.awt.Color;
import java.awt.Point;

/**
 * Une spécialisation de la classe Cell pour représenter visuellement un noeud de type mur.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Wall extends Cell {
	/**
	 * Construit l'objet de type Wall.
	 * Fait appel au constructeur de la classe mère et assigne la couleur noire au noeud.
	 * @param position Les coordonnées X et Y du noeud (en pixel).
	 * @param width La largeur du noeud.
	 * @param height La hauteur du noeud.
	 */
	public Wall(Point position, int width, int height) {
	  super(position, width, height);
	  this.setColor(Color.BLACK);
	}
}
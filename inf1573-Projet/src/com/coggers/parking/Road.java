package com.coggers.parking;

import java.awt.Color;
import java.awt.Point;

/**
 * Une spécialisation de la classe Cell pour représenter visuellement un noeud de type route.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class Road extends Cell {
	private int traffic;
	/**
	 * Construit l'objet de type Road.
	 * Fait appel au constructeur de la classe mère et assigne la couleur blanche au noeud.
	 * @param position Les coordonnées X et Y du noeud (en pixel).
	 * @param width La largeur du noeud.
	 * @param height La hauteur du noeud.
	 */
	public Road(Point position, int width, int height, int traffic) {
	  super(position, width, height);
	  this.traffic = traffic;
	  this.setColor(convertTrafficToColor());
	}
	
	/**
	 * Reinitialise la couleur du noeud à sa valeur originale.
	 */
	public void resetColor() {
		this.setColor(convertTrafficToColor());
	}
	
	/**
	 * Retourne le coefficient de trafique du noeud de type Road.
	 * @return traffic Le coefficient de trafique.
	 */
	public int getTraffic() {
		return traffic;
	}
	
	
	/**
	 * Convertit la valeur du coefficient de trafique en une couleur.
	 * @return color La couleur équivalente au coefficient de trafique.
	 */
	private Color convertTrafficToColor() {
		float value = (float) traffic / 100; //this is your value between 0 and 1
		float minHue = 120f / 255; //corresponds to green
		float maxHue = 0; //corresponds to red
		float hue = value * maxHue + (1 - value) * minHue; 
		
		return new Color(Color.HSBtoRGB(hue, 1, 1));
	}
}

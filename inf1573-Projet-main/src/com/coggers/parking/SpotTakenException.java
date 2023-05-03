package com.coggers.parking;

/**
 * La classe décrivant une exception lancée lorsqu'un espace de stationnement réservé est pris par
 * un conducteur autre que celui qui fait la réservation.
 * @author Jacob Chapman
 * @author William McAllister
 * @author Jean-Francois Morel
 * @author Jean Vézina
 * @version 1.0
 */
public class SpotTakenException extends Exception { 
	private static final long serialVersionUID = -453366073346055754L;

	public SpotTakenException() {
        super("Espace non-disponible");
    }
}

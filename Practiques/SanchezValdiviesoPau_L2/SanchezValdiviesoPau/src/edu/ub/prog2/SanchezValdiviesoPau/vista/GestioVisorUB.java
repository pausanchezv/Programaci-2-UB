
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import java.util.Scanner;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
 */
public class GestioVisorUB {

    /**
     * 
     * Mètode main del programa
     * @param args els arguments del main
     */
    public static void main(String[] args) {      
        Scanner sc = new Scanner(System.in);
        VisorUB2 vista = new VisorUB2();
        vista.gestioVisorUB(sc);
    }

 
}

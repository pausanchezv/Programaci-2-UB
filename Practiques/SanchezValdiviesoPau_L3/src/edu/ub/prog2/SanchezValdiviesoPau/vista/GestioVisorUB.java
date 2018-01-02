
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import java.util.Scanner;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class GestioVisorUB {

    /**
     * 
     * Mètode main del programa
     * @param args
     */
    public static void main(String[] args) {      
        Scanner sc = new Scanner(System.in);
        VisorUB3 vista = new VisorUB3();
        vista.gestioVisorUB(sc);
    }

 
}

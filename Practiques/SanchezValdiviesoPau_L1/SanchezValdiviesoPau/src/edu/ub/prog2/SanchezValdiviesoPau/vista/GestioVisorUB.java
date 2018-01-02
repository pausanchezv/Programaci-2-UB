package edu.ub.prog2.SanchezValdiviesoPau.vista;
import java.util.Scanner;

/**
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº1
 */
public class GestioVisorUB {

    /**
     * 
     * Triem el visor que volem utilitzar 
     * 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Scanner sc = new Scanner(System.in);
        String option = selectImageVisor(sc);
        
        while (!option.equals("3")){
            switch (option) {
                case "1":
                case "2":
                    VisorUB1 vista = new VisorUB1(Integer.parseInt(option)); // crea obj VisorUB1
                    vista.gestioVisorUB(sc);
                    break;
                default:
                    echoMessage(1); // msg error
                    break;
            }
            option = selectImageVisor(sc);
        }
        echoMessage(2); // msg despedida
    }
    
    /**
     * 
     * Permet seleccionar el visor que volem utilitzar.
     * @param sc Scanner per llegir dades per teclat
     * @return la dada entrada per l'usuari en forma d'String
     */
    private static String selectImageVisor(Scanner sc){
        System.out.println("Quin visor vols utilitzar?\n\n(1) El visor UB-1\n(2) El visor UB-2\n(3) Sortir\n\n--> Entra una opció: ");
        return (sc.nextLine());
    }
    
    /**
     * Mostra un missatge segons un argument passat.
     * @param action és el nº de misssatge que volem mostrar
     */
    private static void echoMessage(int action){
        if(action == 1)
            System.out.println("Opció incorrecta!. Recorda: (1 o 2)!");
        else
            System.out.println("Fins aviat!");
    }
}

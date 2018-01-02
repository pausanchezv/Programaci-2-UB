
package edu.ub.prog2.SanchezValdiviesoPau.vista;

/**
 * 
 * Classe que arrenca l'aplicació i crida al fitxer de configuració Settings.java.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public class VisorUB4 {

    /**
     * 
     * Mètode main del programa
     * @param args
     */
    public static void main(String[] args) {      
        Settings settings = new Settings();
        showVisorUB4(settings);
    }
    
    /**
     * 
     * Arrenca el VisorUB4.
     * @param settings és el fitxer .java de configuració de l'aplicació.
     */        

    public static void showVisorUB4(final Settings settings) {
       javax.swing.SwingUtilities.invokeLater(new Runnable() {
           @Override
           public void run() {
                settings.initVisorUB4();
           }
       });
    }

 
}

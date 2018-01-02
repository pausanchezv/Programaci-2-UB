
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.SanchezValdiviesoPau.vista.*;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.InImageList;
import java.io.*;

/**
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº1
 */
public class TaulaImatges implements InImageList, Serializable{
    
    // constants
    private static final int MAX_IMAGES = 100;
    
    // atributs i variables
    private Imatge [] array;
    private int size;
    
    /**
     * 
     * Construnctor.
     * Inicialitza l'array d'imatges, i el comptador a zero
     */
    public TaulaImatges() {
        array = new Imatge[MAX_IMAGES];
        size = 0;
    }
    
    /**
     * 
     * Retorna el numero d'imatges que conté l'array.
     * @return el numero d'imatges actuals de l'array
     */
    @Override
    public int getSize() {
        return size;
    }
    
    /**
     * 
     * Afegeix una imatge a l'array comprovant quel l'array no estigui plè i que no n'hi hagi una d'igual.
     * @param obj és l'objecte imatge que volem afegir a l'array
     */
    @Override
    public void addImage(ImageFile obj) {
        if(!isFull() && imageExist((Imatge)obj) == -1){
            array[size] = (Imatge)obj;
            size++;
            VisorUB1.savedRemember = false; // info necessaria per mostrar correctament alguns missatges informatius
        } else{
             VisorUB1.error = true; // si no pot afegir la imatge, la vista farà un echo de l'error ja que no el podem fer aqui! ;)
        }
    }
    
    /**
     * 
     * Elimina la imatge que ens arriba per paràmetre.  La imatge s'elimina movent les posicions
     *  de l'array a partir de l'obj que es vol eliminar. Així no queden espais buits entre objectes.
     * 
     * @param obj és l'objecte que cal eliminar
     */
    @Override
    public void removeImage(ImageFile obj) {
        int pos = imageExist((Imatge)obj);
        if(pos != -1){
            for(int i = pos; i < size; i++){
                array[i] = array[i + 1];
            }
            size--;
            VisorUB1.savedRemember = false; // info necessaria per mostrar correctament alguns missatges informatius
        }
    }
    
   /**
     * 
     * Retorna la imatge d'una osició concreta.
     * @param pos és la posició de l'ob que volem retornar
     * @return l'objecte Imatge de la posició solicitada o null si no existeix
     */
    @Override
    public ImageFile getAt(int pos) {
        if(imageExist(array[pos]) != -1){
            return array[pos];
        }
        return null;
    }
    
    /**
     * 
     * Elimina tots els elements de l'array i posa el contador a zero.
     */
    @Override
    public void clear() {
        for(int i = 0; i < size; i++){
            array[i] = null;
            size = 0;
        }
    }
    
    /**
     * 
     * Comprova si l'array està plè
     * @return true si l'array està plè, false en cas contrari
     */
    @Override
    public boolean isFull() {
        return (getSize() == array.length);
    }
    
    /**
     * 
     * Comprova que una imatge passada per paràmetre existeixi dins l'array.
     * 
     * @param obj és l'objecte Imatge a comparar
     * @return la posició en que es troba l'obj si existeix, si no, per defecte retorna -1
     */
    private int imageExist(Imatge obj){
        for(int i = 0; i < size; i++){
            if(obj.equals(array[i]))
                return i;
        }
        return -1;
    }
   
    /**
     * 
     * @return l'String de la llista de totes les imatges permetent assignar el numero d'imatge [num] davant
     * de cada objecte imatge.
     */
    @Override
    public String toString(){
        
        if(size == 0)
            return "\nNo hi ha cap imatge!\n";

        String str = "\nLlista de fitxers\n==================\n\n";
        for(int i = 0; i < size; i++)
            str += "[" + (i + 1) + "] " + array[i].toString() + "\n";
        return str;
    }
    
}

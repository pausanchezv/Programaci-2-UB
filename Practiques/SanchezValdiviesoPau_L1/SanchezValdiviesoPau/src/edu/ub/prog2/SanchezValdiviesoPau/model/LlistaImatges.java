
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.SanchezValdiviesoPau.vista.VisorUB1;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.ImageList;
import java.io.*;
import java.util.ArrayList;

/**
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº1
 */
public class LlistaImatges extends ImageList implements Serializable{
    
    // constants
    private static final int MAX_IMAGES = 100;
    
    // atributs i variables
    private ArrayList <Imatge> array;

    /**
     * 
     * Costructor.
     * Inicialitza l'array d'imatges.
     */
    public LlistaImatges() {
        array = new ArrayList <Imatge> (MAX_IMAGES);
    }
    
    /**
     * 
     * Retorna el numero d'imatges que conté l'array.
     * @return el numero d'imatges actuals de l'array
     */
    @Override
    public int getSize() {
        return array.size();
    }
    
    /**
     * 
     * Afegeix una imatge a l'array comprovant quel l'array no estigui plè i que no n'hi hagi una d'igual.
     * @param obj és l'objecte imatge que volem afegir a l'array
     */
    @Override
    public void addImage(ImageFile obj) {
        if(!isFull() && imageExist((Imatge)obj) == -1){
                array.add((Imatge)obj);
                VisorUB1.savedRemember = false;
        }else{
            VisorUB1.error = true; // si no pot afegir la imatge, la vista farà un echo de l'error ja que no el podem fer aqui! ;)
        }
    }
    
    /**
     * 
     * Elimina la imatge que ens arriba per paràmetre. Aqui no cal moure les posicions
     * doncs la classe ArrayList és dinàmica i elimina els espais buits ella sola.
     * 
     * @param obj és l'objecte que cal eliminar
     */
    @Override
    public void removeImage(ImageFile obj) {
        int pos = imageExist((Imatge)obj);
        if(pos != -1){
            array.remove(obj);
            VisorUB1.savedRemember = false;
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
        if(imageExist(array.get(pos)) != -1){
            return array.get(pos);
        }
        return null;
    }
    
    /**
     * 
     * Elimina tots els elements de l'array.
     */
    @Override
    public void clear() {
        array.clear();
    }
    
    /**
     * 
     * Comprova si l'array està plè.
     * @return true si l'array està plè, false en cas contrari
     */
    @Override
    public boolean isFull() {
        return (getSize() >= MAX_IMAGES);
    }
    
    /**
     * 
     * Comprova que una imatge passada per paràmetre existeixi dins l'array.
     * 
     * @param obj és l'objecte Imatge a comparar
     * @return la posició en que es troba l'obj si existeix, si no, per defecte retorna -1
     */
    private int imageExist(Imatge obj){
        for(int i = 0; i < getSize(); i++){
            if(obj.equals(array.get(i)))
                return i;
        }
        return -1;
    }
    
    /**
     * 
     * @return l'String de la llista de totes les imatges permetent assignar el numero d'imatge [num] davant
     * de cada objecte imatge.
     * 
     * /obs/ No utilitzo un foreach o un iterador pq necessito l'index per mostrar el número davant de tot.
     */
    @Override
    public String toString(){
        
        if(array.isEmpty())
            return "\nNo hi ha cap imatge!\n";

        String str = "\nLlista de fitxers\n==================\n\n";
        for(int i = 0; i < array.size(); i++)
            str += "[" + (i + 1) + "] " + array.get(i).toString() + "\n";
        return str;
            
    }
    
}


package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.ImageList;
import edu.ub.prog2.utils.VisorException;
import java.util.ArrayList;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
 */
public class LlistaImatges extends ImageList{

    /**
     * 
     * Constructor 1
     * Es crida desde BibliotecaImatges. No té capacitat incial.
     * Inicialitza l'array d'imatges.
     */
    public LlistaImatges() {
        setList(new ArrayList <ImageFile> ());
    }
    
    /**
     * 
     * Constructor 2
     * Es crida desde AlbumImatges. Té capacitat inicial i aquesta és 10.
     * @param INIT_IMAGES es l'a quantitat inicial de memòria reservada per 10 imatges. Després pot variar.
     */
    public LlistaImatges(int INIT_IMAGES) {
        setList(new ArrayList <ImageFile> (INIT_IMAGES));
    }
    
    /**
     * 
     * Retorna el numero d'imatges que conté l'array.
     * @return el numero d'imatges actuals de l'array
     */
    @Override
    public int getSize() {
        return getList().size();
    }
    
    /**
     * 
     * Afegeix una imatge a l'array sense tenir en compte res. Les restriccions estàn a les sobreescriptures (polimorfisme).
     * @param obj és l'objecte Imatge que volem afegir a l'array
     * @throws edu.ub.prog2.utils.VisorException es fa servir en el mètode sobreescrit de BibliotecaImatges
     */
    @Override
    public void addImage(ImageFile obj) throws VisorException{
        getList().add((Imatge)obj);
    }
    
    /**
     * 
     * Elimina la imatge que ens arriba per paràmetre sense tenir en compte res.
     * Les restriccions estàn a la sobreescriptura (polimorfisme).
     * @param obj és l'objecte que cal eliminar
     */
    @Override
    public void removeImage(ImageFile obj) {
        getList().remove(obj);
    }
    
    /**
     * 
     * Retorna la imatge d'una posició concreta.
     * @param pos és la posició de l'ob que volem retornar
     * @return l'objecte Imatge de la posició solicitada o null si no existeix
     */
    @Override
    public ImageFile getAt(int pos) {
        return pos < getSize() ? getList().get(pos) : null;
    }
    
    /**
     * 
     * Elimina tots els elements de l'array.
     */
    @Override
    public void clear() {
        getList().clear();
    }
    
    /**
     * 
     * Comprova si l'array està plè.
     * @return sempre false perque no hi ha limit de capacitat
     */
    @Override
    public boolean isFull() {
        return false;   // No hi ha límit de capacitat, prefereixo retornar false directament aqui ja que és un metode obligatori. Si escau, per futurs canvis el sobreescric i fora.
    }
         
    /**
     * 
     * @return l'String de la llista de totes les imatges permetent assignar el numero d'imatge [num] davant
     * de cada objecte imatge.
     */
    @Override
    public String toString(){
        
        if(getList().isEmpty())
            return "\nNo hi ha cap imatge!\n";

        String str = "\nLlista de fitxers\n==================\n\n";
        for(int i = 0; i < getList().size(); i++)
            str += "[" + (i + 1) + "] " + getList().get(i).toString() + "\n";
        return str;
            
    } 
}
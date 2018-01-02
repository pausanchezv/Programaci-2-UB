
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import java.util.ArrayList;
import java.util.Collections;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
 */
public class AlbumImatges extends LlistaImatges{
    
    private static final int INIT_IMAGES = 10; // nº d'espai per 10 imatges que reservem inicialment però pot variar
    private String title;
    private String author;
    private ImageFile front;
    
    /**
     * 
     * Constructor de l'àlbum
     * @param title és el títol de l'àlbum
     * @param author és el nom del seu autor
     */
    public AlbumImatges(String title, String author){
        super(INIT_IMAGES);
        this.title = title;
        this.author = author;
        this.front = null;
    }
    
    /**
     * 
     * @param title títol de l'àlbum
     */
    public void setTitle(String title) {
        this.title = title;
    }
    
    /**
     * 
     * @param front és la portada
     */
    public void setFront(ImageFile front) {
        this.front = front;
    }
    
    /**
     * 
     * @return el títol de l'àlbum
     */
    public String getTitle() {
        return title;
    }
    
    /**
     * 
     * @return la portada de l'àlbum
     */
    public ImageFile getFront() {
        return front;
    }
    
    /**
     * 
     * Afegeix una imatge a l'array comprovant quel l'array no estigui plè i que no n'hi hagi una d'igual.
     * @param obj és l'objecte imatge que volem afegir a l'array
     */
    @Override
    public void addImage(ImageFile obj) {
        getList().add(obj);
        if(getSize() == 1){
            setFront(obj);
        }
    }
    
    /**
     * 
     * Mètode per eliminar una imatge per posició.
     * Miro quantes vegades existeix la imatge a l'àlbum abans d'eliminar la imatge. Quan acabo, miro si la imatge
     * només existia un cop i aquesta era la portada i si era així ara la portada és la imatge que queda a la posició 0.
     * Si l'àlbum s'ha buidat, cap problea pq el getAt() retornarà null.

     * @param obj és l'obj Imatge que volem saber si era la portada
     */
    @Override
    public void removeImage(ImageFile obj){
        int occurrences = Collections.frequency(getList(), obj);
        getList().remove(obj);
        if((occurrences == 1 && front.equals(obj)) || getAlbum().isEmpty())
            setFront(getAt(0));
    }
    
    /**
     * 
     * @return el contingut de l'àlbum 
     */
    public ArrayList <ImageFile> getAlbum(){
        return getList();
    }
    
    /**
     * 
     * @param obj l'ob a comparar
     * @return true si son iguals, false si no ho són
     */
    @Override
    public boolean equals(Object obj) {
        final AlbumImatges other = (AlbumImatges) obj;
        return this.title.equals(other.title);
    }
    
    /**
     * 
     * @return l'String de l'objecte AlbumImatge amb la info del àlbum.
     */
    @Override
    public String toString() {
        return "Àlbum {" + "Títol: " + this.title + ", Autor: " + this.author + ", Portada: " + this.front + "}";
    }
}

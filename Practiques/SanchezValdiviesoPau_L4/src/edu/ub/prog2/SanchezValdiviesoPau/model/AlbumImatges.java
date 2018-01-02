
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import java.util.ArrayList;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class AlbumImatges extends LlistaImatges{
    
    private static final int INIT_IMAGES = 10; // nº d'espai per 10 imatges que reservem inicialment però pot variar
    private String title;
    private String author;
    private ImageFile front;
    private int posFront;
    
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
        this.posFront = 0;
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
     * @param posFront és la posició
     */
    public void setFront(ImageFile front, int posFront) {
        this.front = front;
        this.posFront = posFront;
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
     * @return l'autor
     */
    public String getAuthor() {
        return author;
    }
    
    /**
     * 
     * @return la posició de la imatge de portada dins l'array
     */
    public int getPosFront(){
        return posFront;
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
            setFront(obj, 0);
        }
    }
    
    /**
     * 
     * Mètode per eliminar una imatge per objecte.
     * @param obj és l'obj Imatge que volem saber si era la portada
     */
    @Override
    public void removeImage(ImageFile obj){
        getList().remove(obj);
    }
    
    /**
     * Elimina una imatge per posició. Si cal actualitza la portada.
     * @param pos és la posició de la imatge
     */
    public void removeImage(int pos){
        getList().remove(pos);
        if(pos == posFront || getAlbum().isEmpty()){
            setFront(getAt(0), 0);
        }
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

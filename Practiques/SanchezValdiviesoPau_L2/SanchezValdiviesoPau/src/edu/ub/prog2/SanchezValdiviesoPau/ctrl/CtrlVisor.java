
package edu.ub.prog2.SanchezValdiviesoPau.ctrl;
import edu.ub.prog2.SanchezValdiviesoPau.model.*;
import edu.ub.prog2.utils.BasicCtrl;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.VisorException;
import java.io.IOException;
import java.util.*;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
 */
public class CtrlVisor extends BasicCtrl{
    
    private DadesVisor data;
    
    public CtrlVisor(){
        data = new DadesVisor();
    }
    
    
    /************************************************************************/
    /****************** Mètodes per la gestió d'un àlbum ********************/
    /************************************************************************/
    
    /**
     * 
     * Canvia el títol de l'àlbum
     * @param numAlbum és la posició de l'àlbum que volem editar
     * @param title és el nou títol que li posem a l'àlbum
     * @return false si el títol ja existeix, true si fem el setTitle corresponent
     */
    public boolean editTitle(int numAlbum, String title){
         for(AlbumImatges album : getAllAlbums()){
            if(title.equals(album.getTitle()))
                return false;
        }
        data.getAlbums().get(numAlbum).setTitle(title);
        return true;
    }
    
    /**
     * 
     * Canvia la portada de l'àlbum
     * @param numAlbum és la posició de l'àlbum que volem editar
     * @param numImage és la nova portada de l'àlbum
     */
    public void editFront(int numAlbum, int numImage){
         data.getAlbums().get(numAlbum).setFront(data.getAlbums().get(numAlbum).getAt(numImage));
    }
    
    /**
     * 
     * Afegeix una imatge a un àlbum (ambdós passats per argument). #Sobrecarregat#
     * @param numAlbum és la posició on es troba l'àlbum al qual volem afegir la imatge
     * @param pos és la posició en què es troba la imatge dins la biblioteca
     */
    public void addImage (int numAlbum, int pos){
        Imatge obj = (Imatge) data.getLibrary().getAt(pos);
        data.getAlbums().get(numAlbum).addImage(obj);
    }
    
    /**
     * 
     * Elimina una imatge d'un àlbum (amb dos passats per argument). #Sobrecarregat#
     * Creo un obj Album amb el primer paràmetre i un obj Imatge amb el segón i llavors crido al mètode que elimina.
     * @param numAlbum és la posició de l'album el qual volem eliminar-ne una imatge dins l'array d'àlbums
     * @param pos és la posició on es troba la imatge dins de l'àlbum
     */
    public void removeImage (int numAlbum, int pos){
        ImageFile image = data.getAlbums().get(numAlbum).getAt(pos);
        AlbumImatges album = data.getAlbums().get(numAlbum);
        album.removeImage(image);
    }
    
    /************************************************************************/
    /************* Mètodes per la gestió general dels àlbums ****************/
    /************************************************************************/
    
    /**
     * 
     * Crea un album o retorna false a la vista si no el pot crear per mostrar el missatge d'error
     * @param title és el títol de l'àlbum (no pot estar repetit)
     * @param author és el nom de l'autor de l'àlbum
     * @return true si s'ha afegit l'àlbum, false si no s'ha afegit pq n'hi ha un amb el mateix nom
     */
    public boolean addAlbum(String title, String author) {
        AlbumImatges obj = new AlbumImatges(title, author);
        return data.addAlbum(obj);
    }
    
    /**
     * 
     * Elimina un àlbum i tot el seu contingut
     * @param pos és la posició de l'àlbum dins l'array dels àlbums
     */
    public void removeAlbum(int pos){
        data.removeAlbum(pos);
    }
    
    /**
     * 
     * @return tots els àlbums d'imatges
     */
    public ArrayList<AlbumImatges> getAllAlbums(){
        return data.getAlbums();
    }
    
    /**
     * 
     * @param pos la posició de l'àlbum que retorna el mètode
     * @return un àlbum d'imatges
     */
    public AlbumImatges getAlbum(int pos){
        return data.getAlbums().get(pos);
    }
    
    /******************************************************************/
    /*************** Mètodes per gestionar la biblioteca **************/
    /******************************************************************/
    
    /**
     * 
     * @return l'array d'imatges de la biblioteca
     */
    public LlistaImatges getLibrary(){
        return data.getLibrary();
    }
    
    /**
     * Elimina totes les imatges de la biblioteca de cop
     */
    public void removeLibrary(){
        data.getLibrary().clear();
    }
    
    /**
     * 
     * @param path és la ruta de la imatge
     * @param imageName és el nom de la imatge en el visor
     * @throws VisorException per tractar el cas de no poder-se afegir la imatge
     */
    public void addImage(String path, String imageName) throws VisorException{
        data.getLibrary().addImage(new Imatge(path, imageName));
    }
    
    /**
     * 
     * @param pos la posició de la imatge que volem eliminar de la biblioteca i dels àlbums on es trobi
     */
    public void removeImage(int pos){
        ImageFile obj = data.getLibrary().getAt(pos);
        data.removeAllImageOccurrences(obj);
        data.getLibrary().removeImage(obj);
    }
    
    /******************************************************************/
    /******************* Altres mètodes de control ********************/
    /******************************************************************/
    
    /**
     * 
     * Guarda les dades en fitxer extern
     * @param file el path de l'arxiu que l'usuari ha triat
     * @throws java.io.IOException llancem l'excepció sobre el mètode que ens invoca
     */
    public void saveData(String file) throws IOException{
        data.saveData(file);
    }
    
    /**
     * 
     * Recupera les dades de fitxer extren
     * @param file el path de l'arxiu que l'usuari ha triat
     * @throws java.io.IOException llancem l'excepció sobre el mètode que ens invoca
     * @throws java.lang.ClassNotFoundException llancem l'excepció sobre el mètode que ens invoca
     */
    public void loadData(String file) throws IOException, ClassNotFoundException{
        data = DadesVisor.loadData(file);
    }
    
    /**
     * 
     * Objecte per imprimir els àlbums mitjançant el seu toString()
     * @return l'obj DadesVisor actual
     */
    public DadesVisor getObjDadesVisor(){
        return data;
    }

}

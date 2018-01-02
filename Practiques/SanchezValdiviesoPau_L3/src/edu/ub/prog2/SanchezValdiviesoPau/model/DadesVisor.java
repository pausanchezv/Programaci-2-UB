
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.VisorException;
import java.io.Serializable;
import java.util.*;
import java.io.*;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class DadesVisor implements Serializable{
    
    /**
     * Extensions possibles per imatges i arxius de dades.
     */
    private final String [] PATTERN_EXTENSION_IMAGEFILE = {"jpg", "jpeg", "bmp", "png", "psd", "gif", "svg"};
    private final String [] PATTERN_EXTENSION_DATAFILE = {"dat", "obj"};
    
    private BibliotecaImatges library;
    private ArrayList <AlbumImatges> albums;
    private int numImages;
       
    /**
     * 
     * Constructor. crea un objecte BibliotecaImatges i un obj albums que és un ArrayList d'albums d'Imatges.
     */
    public DadesVisor(){
        library = new BibliotecaImatges();
        albums = new ArrayList <AlbumImatges>();
        numImages = 0;
    }
    
    /**
     * 
     * @return l'array d'imatges de la biblioteca
     */
    public BibliotecaImatges getLibrary(){
        return library;
    }
    
    /**
     * 
     * @return l'array d'albums
     */
    public ArrayList <AlbumImatges> getAlbums(){
        return albums;
    }
    
    /**
     * 
     * @return l'array d'exetensions vàlides per imatges
     */
    public String [] getImageExtension(){
        return PATTERN_EXTENSION_IMAGEFILE;
    }
    
    /**
     * 
     * @return l'array d'extensions vàlides per fitxers
     */
    public String [] getDataFileExtension(){
        return PATTERN_EXTENSION_DATAFILE;
    }
    
    /**
     * 
     * Afegeix una imatge a la biblioteca
     * @param path és la ruta de la imatge
     * @param imageName és el nom de la imatge en el visor
     * @throws VisorException per tractar el cas de no poder-se afegir la imatge
     */
    public void addImage(String path, String imageName) throws VisorException{
        getLibrary().addImage(new Imatge(path, imageName, numImages));
        numImages ++;
    }
    
    
    /*******************************************************************/
    /************ Mètodes de l'aplicació de filtrar imatges ************/
    /*******************************************************************/
     
    /**
     * 
     * Guarda una imatge després de la seva transformació
     * @param img és la imatge
     * @param array és la llista on es troba la imatge
     * @param pos és la posició de la llista on es troba la imatge
     * @throws IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImage(Imatge img, LlistaImatges array, int pos) throws IOException, VisorException{
        Imatge obj = img.save();
        replaceImage(array, obj, pos);
        setFrontIfAlbum(array, (Imatge)array.getAt(pos), pos);
    }
    
    /**
     * 
     * Visualitza una imatge aplicant o no un filtre
     * @param obj és la imatge que volem visualitzar
     * @throws Exception transmeto l'excepció al següent mètode 
     */
    public void showImage(Imatge obj) throws Exception{
        obj.show(false);
    }
    
    /**
     * 
     * @param array és la llista de la qual farem la substitució d'obj
     * @param obj és el nou obj que entra a l'array
     * @param pos és la posició a la que entra el nou obj substituint l'anterior
     */
    private void replaceImage(LlistaImatges array, Imatge obj, int pos) throws VisorException{
        array.replaceObj(pos, obj);
    }
    
    /**
     * 
     * Mira si la imatge que estem filtrant era la portada de l'àlbum i la canvia.
     * @param array és la llista d'imatges que ens arriba
     * @param img és la imatge que comparem
     * @param obj és la imatge que substituïm si cal, sino, res.
     */
    private void setFrontIfAlbum(LlistaImatges array, Imatge img, int pos){
         if(array instanceof AlbumImatges){
            AlbumImatges album = (AlbumImatges)array;
            if(pos == album.getPosFront() || album.getAlbum().isEmpty()){
                album.setFront(img, pos);
            }
        }
    }
   
    
    /******************************************************/
    /*********** Mètodes de gestió dels àlbums ************/
    /******************************************************/
    
    /**
     * 
     * Afegeix un àlbum
     * @param obj és un àlbum que volem afegir a l'array d'àlbums
     * @return true si afegeix l'àlbum, false si ja existia.
     */
    public boolean addAlbum(AlbumImatges obj){
        if(albums.contains(obj)) // contains utilitza el nostre equals sobreescrit per comparar !!
            return false;
        albums.add(obj);
        return true;  
    }
    
    /**
     * 
     * Elimina un àlbum i tot el seu contingut
     * @param pos és la posició on es troba l'àlbum que volem eliminar
     */
    public void removeAlbum(int pos){
        albums.remove(pos);
    }
    
    /**
     * 
     * Elimina totes les ocurrences d'una imatge que ha estat eliminada de la biblioteca en tots els àlbums i
     * actualitza la portada si cal.
     * @param obj és l'obj Imatge que volem eliminar de tots els àlbums
     */
    public void removeAllImageOccurrences(Imatge obj){
        for (AlbumImatges album : albums){
            if(!album.getAlbum().isEmpty()){
                for(Iterator <ImageFile> it = album.getAlbum().iterator(); it.hasNext();){
                    ImageFile img = it.next();
                    Imatge im = (Imatge)img;
                    if(im.removeFilters().equals(obj.removeFilters()) && obj.getId() == im.getId()) it.remove();
                    Imatge front = (Imatge)album.getFront();
                    if((front.removeFilters().equals(obj.removeFilters()) && obj.getId() == im.getId()) || album.getAlbum().isEmpty()){
                        album.setFront(album.getAt(0), 0);
                    }
                }
            }
        }
    }
    
    
    /*****************************************************************/
    /************ Mètodes per guardar i recuperar dades **************/
    /*****************************************************************/
      
    /**
     * 
     * Guarda l'obj al directori on vol l'usuari
     * @param file és el nom del fitxer
     * @throws java.io.IOException
     */
    public void saveData(String file) throws IOException{
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));
        output.writeObject(this);
        output.close(); 
    }
    
    /**
     * 
     * Recupera l'obj del fitxer extern
     * @param file és el fitxer
     * @return l'objecte recuperat
     * @throws java.io.IOException passo les exepcions per gestionar-les a la vista
     * @throws java.lang.ClassNotFoundException  passo les exepcions per gestionar-les a la vista
     */
    public static DadesVisor loadData(String file) throws IOException, ClassNotFoundException{
        DadesVisor data = null;
        ObjectInputStream input = new ObjectInputStream(new FileInputStream(file));
        data = (DadesVisor) input.readObject();
        input.close();
        return data;
    }
    
    /**
     * 
     * Actualitza el visor quan recuperem dades guardades perquè  si l'usuari ha eliminat alguna imatge de disc
     * petaría el programa quan el posem en marxa si recupera objectes que apunten a imatges que ja han
     * deixat d'existir a disc.
     */
    public void updateVisor(){
        updateLibrary();
        updateAlbums();
    }
    
    /**
     * 
     * Actualitza la biblioteca al recuperar dades per si s'han eliminat imatges del PC.
     * En aquest cas també actualitzem els àlbums (portada inclosa)
     */
    private void updateLibrary(){
        for(Iterator <ImageFile> it = library.getList().iterator(); it.hasNext();){
            ImageFile img = it.next();
            if(!img.exists()){
                removeAllImageOccurrences((Imatge)img);
                it.remove();
            }
        }
    }
    
    /**
     * 
     * Actualitza ela àlbums al recuperar dades per si s'han eliminat imatges del PC.
     * S'elimina de disc una imatge que pertany a un àlbum però no a la biblioteca 
     * (cas de quan apliquem un filtre a una imatge d'un àlbum)
     */
    private void updateAlbums(){
        for(AlbumImatges album : albums){
            for(Iterator <ImageFile> it = album.getAlbum().iterator(); it.hasNext();){
                ImageFile img = it.next();
                if(!img.exists()){
                    it.remove();
                    if(album.getFront().equals(img)){
                        album.setFront(album.getAt(0), 0);
                    }
                }
            }
        }
    }
    
    /**
     * 
     * @return l'String de l'ob per ser imprès per pantalla
     */
    @Override
    public String toString(){
        
        if(albums.isEmpty())
            return "\nNo hi ha cap àlbum!\n";

        String str = "\nLlista d'àlbums\n====================\n\n";
        for(int i = 0; i < albums.size(); i++)
            str += "[" + (i + 1) + "] " + albums.get(i).toString() + "\n";
        return str;
            
    } 
    
}

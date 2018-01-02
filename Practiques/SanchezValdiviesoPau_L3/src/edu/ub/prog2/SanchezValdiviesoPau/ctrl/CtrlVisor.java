
package edu.ub.prog2.SanchezValdiviesoPau.ctrl;
import edu.ub.prog2.SanchezValdiviesoPau.model.*;
import edu.ub.prog2.utils.BasicCtrl;
import edu.ub.prog2.utils.VisorException;
import java.awt.Dialog;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class CtrlVisor extends BasicCtrl{
    
    private DadesVisor data;
    
    /**
     * Variables per visualitzar llistes d'imatges amb l'onTimer.
     */
    private LlistaImatges currentList;
    private int currentImage;
    private Dialog imageDialog;
    
     
    /**
    * Mètode constructor. Crea un obj de dadesVisor i inicialitza i deixa a punt les variables per si volem
    * visualitzar llistes d'imatges.
    */
    public CtrlVisor(){
        data = new DadesVisor();
        initVisualize();
    }
    
    
    /************************************************************/
    /***** Mètodes per visualitzar col·leccions d'imatges *******/
    /************************************************************/
        
    /**
     * 
     * Inicialitza les variables de visualització de llistes d'imatges amb els
     * valors predeterminats.
     */
    private void initVisualize(){
        currentList = null;
        imageDialog = null;
        currentImage = 0;
    }
    /**
     * 
     * Engega el visualitzador i ensenya la primera imatge (per no esperar 4 segons).
     * @param list és Object ja que a la vista no podem crear objectes del model.
     * @throws VisorException transmeto l'excepció a la vista
     */
    public void play(Object list) throws VisorException, Exception{
        currentList = (LlistaImatges) list;
        imageDialog = currentList.getAt(currentImage).show(false);
        currentImage ++;
        setTimer(4000);
        startTimer();
    }
    
    /**
     * 
     * Atura el visualitzador a petició de l'usuari. És una opció de menú, que només perment atura si aquest està
     * en marxa, naturalment. slideActive és un boleà que controla si el reproductor està en marxa o no.
     * @throws VisorException transemto l'excepció al següent mètode.
     */
    public void stop() throws VisorException{
        if(currentList != null){
            imageDialog.dispose();
            currentImage = 0;
            currentList = null;
            stopTimer();
         }else{
            throw new VisorException("\nNo pots aturar el visor perquè encara no està en marxa!\n");
         }
    }
    
    /**
     * 
     * Mètode sobreescrit per visualitzar una col·lecció d'imatges. Visualitza les imatges i quan acaba la collection
     * torna a començar des de la primera imatge. Per aturar cal prémer la opció 8 del menú.
     * 1. Tanca la imatge anterior
     * 2. Poso el dialog a null i el recullo amb el GarbageCollector
     * 3. Creo un nou dialog-imatge i torna a començar al principi quan acaba la llista.
     */
    @Override
    public void onTimer(){
        imageDialog.dispose();
        if (currentImage >= currentList.getList().size()) {
            currentImage = 0;
        } try {
            imageDialog = currentList.getAt(currentImage).show(false);
            currentImage ++;
        } catch (Exception ex) {
            Logger.getLogger(CtrlVisor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    /***********************************************************************/
    /*********** Mètodes per visualitzar i transformar imatges *************/
    /***********************************************************************/
    
    /**
     * 
     * Mostra una imatge sense filtre (en el seu estat original).
     * @param pos és la posició on es troba dins l'array
     * @param list és l'array d'imatges (-1 es considera biblioteca i si no doncs és índex d'àlbum)
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     */
    public void showImage(int pos, int list) throws Exception{
        Imatge obj = getObj(pos, list).removeFilters();
        data.showImage(obj);
    }
    
    /**
     * 
     * Mostra una imatge amb filtre sepia.
     * @param pos és la posició on es troba dins l'array
     * @param list és l'array d'imatges (-1 es considera biblioteca i si no doncs és índex d'àlbum)
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     */
    public void showImageSepia(int pos, int list) throws Exception{
        Imatge obj = getObj(pos, list);
        ImatgeSepia img = new ImatgeSepia(obj.getAbsolutePath(), obj.getImageName(), obj.getId());
        data.showImage(img);
    }
    
    /**
     * 
     * Mostra una imatge amb filtre BN.
     * @param pos és la posició on es troba dins l'array
     * @param list és l'array d'imatges (-1 es considera biblioteca i si no doncs és índex d'àlbum)
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     */
    public void showImageBN(int pos, int list) throws Exception{
        Imatge obj = getObj(pos, list);
        ImatgeBN img = new ImatgeBN(obj.getAbsolutePath(), obj.getImageName(), obj.getId());
        data.showImage(img);
    }
    
    /**
     *
     * Guarda una imatge sense filtres.
     * @param pos és la posició on es troba dins l'array
     * @param list és l'array d'imatges (-1 es considera biblioteca i si no doncs és índex d'àlbum)
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImage(int pos, int list) throws IOException, VisorException{
        Imatge obj = getObj(pos, list);
        if(!obj.removeFilters().getAbsolutePath().equals(obj.getFullPath())){
            LlistaImatges array = getList(list);
            data.saveImage(obj, array, pos);
        }
    }
    
    /**
     *
     * Guarda una imatge amb filtre sepia.
     * @param pos és la posició on es troba dins l'array
     * @param list és l'array d'imatges (-1 es considera biblioteca i si no doncs és índex d'àlbum)
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImageSepia(int pos, int list) throws IOException, VisorException{
        Imatge obj = getObj(pos, list);
        if(!obj.getFileWithFilter("_SP.").getAbsolutePath().equals(obj.getFullPath())){
            LlistaImatges array = getList(list);
            ImatgeSepia img = new ImatgeSepia(obj.getFullPath(), obj.getImageName(), obj.getId());
            data.saveImage(img, array, pos);
        }
    }
    
    /**
     *
     * Guarda una imatge amb filtre BN.
     * @param pos és la posició on es troba dins l'array
     * @param list és l'array d'imatges (-1 es considera biblioteca i si no doncs és índex d'àlbum)
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImageBN(int pos, int list) throws IOException, VisorException{
        Imatge obj = getObj(pos, list);
        if(!obj.getFileWithFilter("_BN.").getAbsolutePath().equals(obj.getFullPath())){
            LlistaImatges array = getList(list);
            ImatgeBN img = new ImatgeBN(obj.getFullPath(), obj.getImageName(), obj.getId());
            data.saveImage(img, array, pos);
        }
        
    }
    
    /*********************************************************/
    /***** Mètodes de suport per filtrar / visualitzar *******/
    /*********************************************************/
    
    /**
     * 
     * Retorna un obj (és per evitar codis repetits) "Suport".
     * Segons el cas retorna ina imatge d'un àlbum o directament de la biblioteca.
     * @param pos és la posició on es troba dins l'array
     * @return l'obj
     */
    private Imatge getObj(int pos, int list){
        return (Imatge)((list == -1) ? getLibrary().getAt(pos) : getAlbum(list).getAt(pos));
    }
    
    /**
     * 
     * Retorna la biblioteca si el paràmetre passat és (-1) o un àlbum per posició.
     * @param pos és l'índex, que si és -1 es considera biblioteca
     * @return la llista d'imatges 
     */
    private LlistaImatges getList(int pos){
        return (pos < 0) ? getLibrary() : getAlbum(pos);
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
         data.getAlbums().get(numAlbum).setFront(data.getAlbums().get(numAlbum).getAt(numImage), numImage);
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
        AlbumImatges album = data.getAlbums().get(numAlbum);
        album.removeImage(pos);
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
        data.addImage(path, imageName);
    }
    
    /**
     * 
     * @param pos la posició de la imatge que volem eliminar de la biblioteca i dels àlbums on es trobi
     */
    public void removeImage(int pos){
        Imatge obj = (Imatge) data.getLibrary().getAt(pos);
        data.removeAllImageOccurrences(obj);
        data.getLibrary().removeImage(obj);
    }
    
    /******************************************************************/
    /******************* Altres mètodes de control ********************/
    /******************************************************************/
    
    /**
     * 
     * @return l'array d'exetensions vàlides per imatges
     */
    public String [] getImageExtension(){
        return data.getImageExtension();
    }
    
    /**
     * 
     * @return l'array d'extensions vàlides per fitxers
     */
    public String [] getDataFileExtension(){
        return data.getDataFileExtension();
    }
    
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
     * Actualitza el visor quan recuperem dades guardades per si l'usuari ha eliminat alguna imatge de disc
     * petaría el programa quan el posem en marxa si recupera objectes que apunten a imatges que ja han
     * deixat d'existir a disc.
     */
    public void updateVisor(){
        data.updateVisor();
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

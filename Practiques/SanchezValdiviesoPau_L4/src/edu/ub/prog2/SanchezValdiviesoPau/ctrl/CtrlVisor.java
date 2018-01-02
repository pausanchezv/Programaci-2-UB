
package edu.ub.prog2.SanchezValdiviesoPau.ctrl;
import edu.ub.prog2.SanchezValdiviesoPau.model.*;
import edu.ub.prog2.SanchezValdiviesoPau.vista.Settings;
import edu.ub.prog2.utils.BasicCtrl;
import edu.ub.prog2.utils.VisorException;
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
    private Settings settings;
    
    /**
     * Variables per visualitzar llistes d'imatges amb l'onTimer.
     */
    private boolean visualizeActive;
    private LlistaImatges currentList;
    private int currentImage;
    private int currTimer = 4000;
    private boolean pause;
    
     
    /**
    * Mètode constructor. Crea un obj de dadesVisor i inicialitza i deixa a punt les variables per si volem
    * visualitzar llistes d'imatges.
     * @param settings l'obj principal.
    */
    public CtrlVisor(Settings settings){
        data = new DadesVisor();
        this.settings = settings;
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
        this.visualizeActive = false;
        this.currentList = null;
        this.currentImage = 0;
        this.pause = false;
    }
    /**
     * 
     * Engega el visualitzador i ensenya la primera imatge (per no esperar 4 segons).
     * @param list és Object ja que a la vista no podem crear objectes del model.
     * @throws VisorException transmeto l'excepció a la vista
     */
    public void play(Object list) throws VisorException, Exception{
        visualizeActive = true;
        currentList = (LlistaImatges) list;
        settings.getVisualizeZone((Imatge) currentList.getAt(currentImage));
        currentImage ++;
        setTimer(currTimer);
        startTimer();
    }
    
    /**
     * 
     * Fa correccions després d'incrementar o decrementar la velocitat.
     * @throws VisorException 
     */
    public void restartAfter() throws VisorException{
        setTimer(currTimer);
        startTimer();
    }
    
    /**
     * 
     * Atura el visualitzador a petició de l'usuari. És una opció de menú, que només perment atura si aquest està
     * en marxa, naturalment. slideActive és un boleà que controla si el reproductor està en marxa o no.
     * @throws VisorException transemto l'excepció al següent mètode.
     */
    public void stop() throws VisorException{
        visualizeActive = false;
        currTimer = 4000;
        ready();
        currentImage = 0;
        currentList = null;
        stopTimer();
        settings.getDisplayLibrary();
    }
    
    /**
     * 
     * Pausa la visualització.
     */
    public void pause(){
        this.pause = true;
    }
    
    /**
     * 
     * Reanuda la visualització.
     */
    public void ready(){
        this.pause = false;
    }
    
    /**
     * 
     * Accelera la visualització.
     * @throws edu.ub.prog2.utils.VisorException
     */
    public void inrTimer() throws Exception{
        currTimer /= 1.5f;
        restartAfter();
    }
    
    /**
     * 
     * Decrementa l'acceleració de la visualització.
     * @throws java.lang.Exception
     */
    public void dcrTimer() throws Exception{
        currTimer *= 1.5f;
        restartAfter();
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
        if(!pause){
            if (currentImage >= currentList.getList().size()) {
                currentImage = 0;
            } try {
                settings.getVisualizeZone((Imatge) currentList.getAt(currentImage));
                currentImage ++;
            } catch (Exception ex) {
                Logger.getLogger(CtrlVisor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    /**
     * 
     * @param clause és true o false.
     */
    public void setVisualizeActive(boolean clause){
        visualizeActive = clause;
    }
    
    /**
     * 
     * @return si el visor esà o no actiu.
     */
    public boolean getVisualizeActive(){
        return visualizeActive;
    }
    
    
    /***********************************************************************/
    /*********** Mètodes per visualitzar i transformar imatges *************/
    /***********************************************************************/
    
    /**
     * 
     * Mostra una imatge sense filtre (en el seu estat original).
     * @param obj és la imatge
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     */
    public void showImage(Imatge obj) throws Exception{
        data.showImage(obj.removeFilters());
    }
    
    /**
     * 
     * Mostra una imatge amb filtre sepia.
     * @param obj és la imatge
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     */
    public void showImageSepia(Imatge obj) throws Exception{
        ImatgeSepia img = new ImatgeSepia(obj.getAbsolutePath(), obj.getImageName(), obj.getId());
        data.showImage(img);
    }
    
    /**
     * 
     * Mostra una imatge amb filtre BN.
     * @param obj és la imatge
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     */
    public void showImageBN(Imatge obj) throws Exception{
        ImatgeBN img = new ImatgeBN(obj.getAbsolutePath(), obj.getImageName(), obj.getId());
        data.showImage(img);
    }
    
    /**
     *
     * Guarda una imatge sense filtres.
     * @param obj és la imatge
     * @param pos és la posició on es troba dins l'array
     * @param array és la llista
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImage(Imatge obj, LlistaImatges array, int pos) throws IOException, VisorException{
        if(!obj.removeFilters().getAbsolutePath().equals(obj.getFullPath())){
            data.saveImage(obj, array, pos);
        }
    }
    
    /**
     *
     * Guarda una imatge amb filtre sepia.
     * @param obj és la imatge
     * @param array és la llista
     * @param pos és la posició on es troba dins l'array
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImageSepia(Imatge obj, LlistaImatges array, int pos) throws IOException, VisorException{
        if(!obj.getFileWithFilter("_SP.").getAbsolutePath().equals(obj.getFullPath())){
            ImatgeSepia img = new ImatgeSepia(obj.getFullPath(), obj.getImageName(), obj.getId());
            data.saveImage(img, array, pos);
        }
    }
    
    /**
     *
     * Guarda una imatge amb filtre BN.
     * @param obj és la imatge
     * @param pos és la posició on es troba dins l'array
     * @param array és la llista
     * @throws java.io.IOException transmeto l'excepció al seguent mètode.
     * @throws edu.ub.prog2.utils.VisorException transmeto l'excepció al seguent mètode.
     */
    public void saveImageBN(Imatge obj, LlistaImatges array, int pos) throws IOException, VisorException{
        if(!obj.getFileWithFilter("_BN.").getAbsolutePath().equals(obj.getFullPath())){
            ImatgeBN img = new ImatgeBN(obj.getFullPath(), obj.getImageName(), obj.getId());
            data.saveImage(img, array, pos);
        }
        
    }
    
    /************************************************************************/
    /****************** Mètodes per la gestió d'un àlbum ********************/
    /************************************************************************/
    
    /**
     * 
     * Canvia el títol de l'àlbum
     * @param obj és l'àlbum que volem editar
     * @param title és el nou títol que li posem a l'àlbum
     * @return false si el títol ja existeix, true si fem el setTitle corresponent
     */
    public boolean editTitle(AlbumImatges obj, String title){
         for(AlbumImatges album : getAllAlbums()){
            if(title.equalsIgnoreCase(album.getTitle()))
                return false;
        }
        obj.setTitle(title);
        return true;
    }
    
    /**
     * 
     * Canvia la portada de l'àlbum
     * @param album és l'àlbum a canviar la portada
     * @param numImage és la nova portada de l'àlbum
     */
    public void editFront(AlbumImatges album, int numImage){
         album.setFront(album.getAt(numImage), numImage);
    }
    
    /**
     * 
     * Afegeix una imatge a un àlbum (ambdós passats per argument). #Sobrecarregat#
     * @param numAlbum és la posició on es troba l'àlbum al qual volem afegir la imatge
     * @param obj és l'obj imatge
     */
    public void addImage (int numAlbum, Imatge obj){
        data.getAlbums().get(numAlbum).addImage(obj);
    }
    
    /**
     * 
     * Elimina una imatge d'un àlbum (amb dos passats per argument). #Sobrecarregat#
     * Creo un obj Album amb el primer paràmetre i un obj Imatge amb el segón i llavors crido al mètode que elimina.
     * @param album és l'àlbum d'imatges
     * @param pos és la posició on es troba la imatge dins de l'àlbum
     */
    public void removeImage (AlbumImatges album, int pos){
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
        data.getAlbums().clear();
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
     * @param obj és la imatge a eliminar
     */
    public void removeImage(Imatge obj){
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

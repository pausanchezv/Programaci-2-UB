
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import java.io.Serializable;
import java.util.*;
import java.io.*;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
 */
public class DadesVisor implements Serializable{
    
    private BibliotecaImatges library;
    private ArrayList <AlbumImatges> albums;
       
    /**
     * 
     * Constructor, crea un objecte BibliotecaImatges i un obj albums que és un ArrayList d'albums d'Imatges
     */
    public DadesVisor(){
        library = new BibliotecaImatges();
        albums = new ArrayList <AlbumImatges>();
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
     * Elimina totes les ocurrences d'una imatge que ha estat eliminada de la biblioteca en tots els àlbums.
     * Deixo comentada abans de l'iterador una alternativa per fer el mateix en una sola linia de codi.
     * 1. Per cada àlbum miro primer si està buit
     * 2. Deixo comentada una línia màgica que ens estalviaría les 6 linies que ocupa l'iterador. No la utilitzo perque es demana un iterador.
     * 4. Al sortir miro si la portada era la Imatge que hem eliminat i si és així actualizo a la imatge de la posició 0.
     * Si l'àlbum ha quedat buit s'actualitza automàticament a null a través del retorn del getAt.
     * @param obj és l'obj Imatge que volem eliminar de tots els àlbums
     */
    public void removeAllImageOccurrences(ImageFile obj){
        for (AlbumImatges album : albums){
            if(!album.getAlbum().isEmpty()){
                //album.getAlbum().removeAll(Arrays.asList(obj));
                for(Iterator <ImageFile> it = album.getAlbum().iterator(); it.hasNext();){
                    ImageFile img = it.next();
                    if(img.equals(obj))
                        it.remove();
                }
                if((album.getFront().equals(obj)) || album.getAlbum().isEmpty())
                    album.setFront(album.getAt(0));
            }
        }
    }
      
    /**
     * 
     * Guarda l'obj al directori on vol l'usuari
     * @param file és el nom del fitxer
     * @throws java.io.IOException no volem gestionar l'excepció aqui doncs la llancem al proper mètode
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


package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;
import javax.swing.JDialog;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class Imatge extends ImageFile implements Filtrable{
    
    private String imageName;
    protected int id;
    
    /**
     * 
     * Constructor.
     * @param fullPath és la ruta absoluta, aquesta és enviada a la classe mare
     * @param imageName és el nom que l'usuari assigna a la imatge
     */
    public Imatge(String fullPath, String imageName, int id) {
        super(fullPath);
        this.imageName = imageName;
        this.id = id;
    }
    
    /**
     * 
     * Retorna la ruta absoluta de la imatge.
     * @return el path absolut del fitxer
     */
    @Override
    public String getFullPath() {
        return this.getAbsolutePath();
    }
    
    /**
     * 
     * Retorna l'extensió del fitxer.
     * @return l'extensió del fitxer Imatge
     */
    @Override
    public String getExtension() {
       return this.getName().substring(this.getName().lastIndexOf('.') + 1);
    }
    
    /**
     * 
     * @return el títol de la imatge
     */
    public String getImageName() {
        return imageName;
    }
    
    /**
     * 
     * Retorna la data de mosificació.
     * @return la darrera modificació de l'arxiu en formnat Date()
     */
    @Override
    public Date getLastModification() {
        return new Date(this.lastModified());
    }
    
    public int getId(){
        return id;
    }
    
    /**
     * 
     * Retorna el nom del fitxer sense l'extensió.
     * @return el nom del fitxer sense extensió
     */
    public String getNameWithoutExtension(){
        return this.getName().substring(0, this.getName().lastIndexOf('.'));
    }
    
    /**
     * 
     * Equals sobreescrit on una imatge es cosidera igual que una altra si coincideixen en nom i ruta
     * @param obj és la imatge que ens arriba per coparar
     * @return true si és igual, false en cas contrari
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        final Imatge other = (Imatge) obj;
        if (!Objects.equals(this.getFullPath(), other.getFullPath()) || !Objects.equals(this.imageName, other.imageName)) {
            return false;
        }
        return true;
    }
       
    /**
     * 
     * Mètode d'impressió de l'objecte.
     * @return l'String de l'objecte per imprimir per pantalla
     */
    @Override
    public String toString() {
        return "Imatge{" + "Nom: " + this.imageName + ", Data: " + this.getLastModification() + ", Nom fitxer: " + this.getNameWithoutExtension() + ", Extensió: " + this.getExtension() + ", Ruta: " + this.getPath() + '}';
    }
    
    @Override
    public JDialog show(boolean modal) throws IOException, Exception {
        JDialog obj = super.show(modal);
       // obj.pack();
        obj.setSize(800, 580);
        obj.setLocationRelativeTo(null);
        return obj;
    }
  
    /**
     * 
     * Prepara una imatge per ser guardada a l'array sense filtres. Doncs quan no té filtres no cal
     * guardar-la a disc pq ja hi pertanys per si mateixa.
     * @return l'obj Imatge sense filtres si és que en tenia.
     * @throws IOException 
     */
    public Imatge save() throws IOException{
        return hasFilters() ? this.removeFilters() : this;
    }
    
    /*********************************************************************/
    /**** Mètodes de suport heredats per totes les imatges amb filtre ****/
    /**** de moment només dues classes filles, però ampliable ************/
    /*********************************************************************/
    
    /**
     * 
     * Aplica un filtre, retorna un file amb el filtre reflectit en la seva url.
     * @param filter és el tipus de filtre
     * @return un obj de tipus file amb el filtre corresponent aplicat
     */
    @Override
    public File getFileWithFilter(String filter) {
        File file = hasFilters() ? getFileWithoutFilter() : new File(getFullPath());
        return new File(file.getAbsolutePath().substring(0, file.getAbsolutePath().lastIndexOf('.')) + filter + getExtension());
    }
    
    /**
     * 
     * Treu el filtre de la url de la imatge retornant un file normal sense filtre.
     * @return un obj de tipus file sense cap filtre que apunta al path original sense filtres
     */
    @Override
    public File getFileWithoutFilter(){
        return new File(getFullPath().substring(0, getFullPath().lastIndexOf('.') - 3) + '.' +  getExtension()); 
    }
    
    /**
     * 
     * Ens diu si existeix un determinat fitxer a disc amb un dtereminat filtre.
     * @param filter és el tipus de filtre
     * @return ens diu si un file existeix a disc
     */
    @Override
    public boolean isFileExists(String filter){
        return getFileWithFilter(filter).exists();
    }
    
    /**
     * 
     * Comprova si aquesta imatge té algún filtre aplicat en aquest moment
     * @return true si està filtrada, false si no ho està
     */
    @Override
    public boolean hasFilters(){
        for(String filter : PATTERN_FILTERS){
            if(getFullPath().contains(filter))
                return true;
        }
        return false;
    }
    
    /**
     * 
     * Elimina els filtres i retorna l'obj original.
     * @return on obj tipus Imatge que apunta al file de disc que no té cap filtre
     */
    @Override
    public Imatge removeFilters(){
        File file = hasFilters() ? getFileWithoutFilter() : this;
        return new Imatge(file.getAbsolutePath(), getImageName(), id);
    }
  
}


package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import java.util.Date;
import java.util.Objects;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
 */
public class Imatge extends ImageFile{
    
    private String imageName;
    
    /**
     * 
     * Constructor.
     * @param fullPath és la ruta absoluta, aquesta és enviada a la classe mare
     * @param imageName és el nom que l'usuari assigna a la imatge
     */
    public Imatge(String fullPath, String imageName) {
        super(fullPath);
        this.imageName = imageName;
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
     * Retorna la data de mosificació.
     * @return la darrera modificació de l'arxiu en formnat Date()
     */
    @Override
    public Date getLastModification() {
        return new Date(this.lastModified());
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
        if (this.getClass() != obj.getClass()) {
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
  
}

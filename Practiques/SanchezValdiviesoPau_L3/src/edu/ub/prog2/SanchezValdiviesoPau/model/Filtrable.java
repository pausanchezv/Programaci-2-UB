
package edu.ub.prog2.SanchezValdiviesoPau.model;
import java.io.File;

/**
 *
 * Interface Filtrable per la classe Imatge. Donem una forma que adoptaràn tots els
 * objs filtrables, en aquest cas, les imatges.
 *
 */
public interface Filtrable {
    
    /**
     * 
     * Tipus de filtres aplicables a les imatges. Per afegir filtres només l'hem d'afegir aqui
     * doncs tots els mètodes modularitzats per tractar tants filtres com hi hagi en un futur.
     * Aqui només hi ha constants i mètodes per treballar amb filtres. Res més.
     */
    public final String [] PATTERN_FILTERS = {"_SP", "_BN"};
    
    /**
     * 
     * Aplica un filtre, retorna un file amb el filtre reflectit en la seva url.
     * @param filter és el tipus de filtre
     * @return un obj de tipus file amb el filtre corresponent aplicat
     */
    public File getFileWithFilter(String filter);
    
    /**
     * 
     * Treu el filtre de la url de la imatge retornant un file normal sense filtre.
     * @return un obj de tipus file sense cap filtre que apunta al path original sense filtres
     */
    public File getFileWithoutFilter();
    
    /**
     * 
     * Ens diu si existeix un determinat fitxer a disc amb un dtereminat filtre.
     * @param filter és el tipus de filtre
     * @return ens diu si un file existeix a disc
     */
    public boolean isFileExists(String filter);
    
    /**
     * 
     * Comprova si aquesta imatge té algún filtre aplicat en aquest moment
     * @return true si està filtrada, false si no ho està
     */
    public boolean hasFilters();
    
    /**
     * 
     * Elimina els filtres i retorna l'obj original.
     * @return on obj tipus Imatge que apunta al file de disc que no té cap filtre
     */
    public Imatge removeFilters();
    
    
}

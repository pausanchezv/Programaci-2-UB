
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.VisorException;


/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class BibliotecaImatges extends LlistaImatges{
    
    public BibliotecaImatges(){
        super();
    }
    
    /**
     * 
     * Afegeix una imatge a l'array comprovant quel l'array no estigui plè i que no n'hi hagi una d'igual.
     * @param obj és l'objecte imatge que volem afegir a l'array
     * @throws edu.ub.prog2.utils.VisorException
     * 
     * /obs/ La comprobació exists() és redundant ja que faig triar els fitxers desde JFileChooser i per tant
     * sempre existiran. Però ho demana l'enunciat i ho poso perquè no em baixi la nota.
     */
    @Override
    public void addImage(ImageFile obj) throws VisorException{
        if(!imageExists((Imatge)obj) && obj.exists()){
            getList().add((Imatge)obj);
        }else{
            throw new VisorException("La imatge ja existeix");
        }
    }
    
    
}

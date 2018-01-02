
package edu.ub.prog2.SanchezValdiviesoPau.model;
import edu.ub.prog2.utils.ImageFile;
import edu.ub.prog2.utils.VisorException;


/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº2
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
    
    /**
     * 
     * Elimina la imatge que ens arriba per paràmetre. Aqui no gestionem errors ja que no n'hi pot haver pq
     * capo d'entrada l'arribada d'un índex no vàlid obligant a introduïr l'index dins un rank establert. Ho trobo més correcte.
     * @param obj és l'objecte que cal eliminar
     */
    @Override
    public void removeImage(ImageFile obj) {
        if(imageExists((Imatge) obj)){
            getList().remove(obj);
        }
    }
    
    /**
     * 
     * Comprova per índex que una imatge passada per paràmetre existeixi dins l'array.
     * @param obj és l'objecte Imatge a comparar
     * @return true si existeix, false en cas contrari
     */
    public boolean imageExists(Imatge obj){
        return getList().contains(obj); // Contains() utilitza el meu equals sobreescrit de <Imatge>
    }
}

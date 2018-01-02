
package edu.ub.prog2.SanchezValdiviesoPau.model;

import edu.ub.prog2.utils.ImageFile;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import javax.swing.JDialog;


/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class ImatgeBN extends Imatge{

    public ImatgeBN(String fullPath, String imageName, int id) {
        super(fullPath, imageName, id);
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
    
    private BufferedImage color2gray(){
        BufferedImage inBufferedImage = (BufferedImage)getImage();
        int width = inBufferedImage.getWidth();
        int height = inBufferedImage.getHeight();	
        BufferedImage outImage = new BufferedImage(width,height, BufferedImage.TYPE_3BYTE_BGR);
        for(int i = 0; i < height; i++){
            for(int j = 0; j < width; j++){
                Color c	= new Color(inBufferedImage.getRGB(j, i));
                int red	= (int)(c.getRed() * 0.2126);
                int green = (int)(c.getGreen() * 0.7152);
                int blue = (int)(c.getBlue() * 0.0722);
                Color newColor = new Color(red + green + blue, red + green + blue, red + green + blue);
                outImage.setRGB(j, i, newColor.getRGB());
            }
        }									
        return(outImage);
    }
    
    
    /**
     * 
     * Mostra una Imatge per previsualitzar-la abans de decidir si la guardem a l'array o no.
     * Només ha de crear el file en cas de que no existeixi a disc. Si ja existía ens estalviem aquest pas. D'això
     * se n'encarrega el mètode save.
     * @param modal per aturar la interacció per consola
     * @return el dialog show de la imatge
     * @throws IOException transmeto l'excepció al proper mètode
     * @throws Exception transmeto l'excepció al proper mètode 
     */
    @Override
    public JDialog show(boolean modal) throws IOException, Exception {
        Imatge obj = save();
        return obj.show(modal);
    }
    
    /**
     * 
     * Guarda una imatge a disc si aquesta no existeix. Si existeix retorna el camí per poder ser visualitzat.
     * @return l'obj creat per ser substituït a l'array
     * @throws IOException transmeto l'excepció al proper mètode 
     */
    @Override
    public Imatge save() throws IOException{
        if(hasFilters()) removeFilters();
        Imatge obj =  new Imatge(getFileWithFilter("_BN.").getAbsolutePath(), this.getImageName(), id);
        if(!isFileExists("_BN.")){
            ImageFile.saveImage(color2gray(), getFileWithFilter("_BN."));
        }
        return obj;
    }
}

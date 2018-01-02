/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import edu.ub.prog2.SanchezValdiviesoPau.accessoris.GradientButton;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.SanchezValdiviesoPau.model.AlbumImatges;
import edu.ub.prog2.SanchezValdiviesoPau.model.Imatge;
import edu.ub.prog2.SanchezValdiviesoPau.model.LlistaImatges;
import edu.ub.prog2.utils.VisorException;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * Classe que conté mètodes i funcions globals al visor.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class Functions {
    
    private JFileChooser select;
    private final CtrlVisor ctrl;
    private Settings settings;
    
    /**
     * 
     * Constructor
     * @param ctrl obj del controlador
     */
    public Functions(CtrlVisor ctrl){
        select = new JFileChooser();
        this.ctrl = ctrl;
    }
    
    /**
     * 
     * Constructor
     * @param ctrl obj del controlador
     * @param settings
     */
    public Functions(CtrlVisor ctrl, Settings settings){
        select = new JFileChooser();
        this.ctrl = ctrl;
        this.settings = settings;
    }
    
    /**
     * 
     * Guarda una imatge sense filtres.
     * @param obj
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     */
    private void saveImage(Imatge obj, LlistaImatges array, int pos){
        try {
            ctrl.saveImage(obj, array, pos);
        } catch (IOException | VisorException ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 
     * Guarda amb filtre sepia.
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     */
    private void saveImageSepia(Imatge obj, LlistaImatges array, int pos){
        try {
            ctrl.saveImageSepia(obj, array, pos);
        } catch (IOException | VisorException ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
   
   /**
     * 
     * Guarda una imatge amb filtre BN.
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     */
    private void saveImageBN(Imatge obj, LlistaImatges array, int pos){
        try {
            ctrl.saveImageBN(obj, array, pos);
        } catch (IOException | VisorException ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   /**
    * 
    * Visualitza una imatge sense filtres.
    * @param obj és la imatge
    */
    public void showImage(Imatge obj){
        try {
            ctrl.showImage(obj);
        } catch (Exception ex) {
           getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   /**
    * 
    * Visualitza una imatge amb filtre sepia
    * @param obj és la imatge
    */
   private void showImageSepia(Imatge obj){
        try {
            ctrl.showImageSepia(obj);
        } catch (Exception ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
   /**
    * 
    * Visualitza una imatge amb filtre BN.
     * @param obj és la imatge
    */
    public void showImageBN(Imatge obj){
        try {
            ctrl.showImageBN(obj);
        } catch (Exception ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
   
    /**
     *
     * Visualitza un àlbum o la biblioteca
     * @param list és llista que volem visualizar
     */
    public void slideImages(Object list){ // Object pq no puc importar res del model !!
        try {
            ctrl.play(list);
        } catch (VisorException ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
           getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 
     * Atura la reproducció de la visualització.
     */
    public void stopSlide(){
        try {
            ctrl.stop();
        } catch (VisorException ex) {
            getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * 
     * Inicialitza els valor per defecte de JFileChooser per no arrossegar informació anterior ja que
     * utilitzo el mateix obj per tots els frames de gestió de fitxers.
     * @param title és el títol del frame que es veu a la part superior
     */
    private void initializeFileChooserValues(String title){
        select.setSelectedFile(new File("")); // Neteja el camp 'nom'
        select.resetChoosableFileFilters(); // Neteja les extensions seleccionables
        select.setDialogTitle(title); // Mostra el títol del Frame passat com a argument
        select.setCurrentDirectory(null); // Resetejo el directori pq no torni a l'últim explorat
    }
    
    /**
     * 
     * Guarda un fitxer de dades al disc, per a fer-ho ha de tenir en compte que l'extensió del fitxer
     * sigui vàlida: .dat o .obj per no liar molt la troca.
     */
    public void saveData(){
        initializeFileChooserValues("Tria el destí i el nom del fitxer que vols guardar");
        select.setFileFilter(new FileNameExtensionFilter("Arxius de dades 'dat', 'obj'", "dat", "obj"));
        int element = select.showSaveDialog(null);
        if (element == JFileChooser.APPROVE_OPTION) {
            try {
                Boolean cancelAction = false;
                String file = select.getSelectedFile().getAbsolutePath(); 
                while(!isValidExtension(file, ctrl.getDataFileExtension())){
                    getMessage("Extensió invàlida!", "Les extensions possibles són:\n.dat i .obj", JOptionPane.ERROR_MESSAGE);
                    element = select.showOpenDialog(null);
                    if(element == JFileChooser.CANCEL_OPTION){
                        cancelAction = true;
                        break;
                    }else{
                        file = select.getSelectedFile().getAbsolutePath();
                    }
                }
                if(!cancelAction){
                    ctrl.saveData(file);
                    getMessage("Error!", "Dades guardades amb èxit!", JOptionPane.PLAIN_MESSAGE);
                }
            } catch (IOException ex) {
                getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * 
     * Carrega un visor d'exemple.
     */
    public void loadDataExample(){
        try {
            ctrl.loadData("src/data/VisorUB4.dat");
            ctrl.updateVisor();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Functions.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * Recupera dades des del disc.
     * Si no se selecciona un .dat o un .obj l'exepció VisorExeption retorna els casos d'error
     * @return si ha guardat
     */
    public boolean loadData(){
        initializeFileChooserValues("Selecciona un fitxer de dades per recuperar");
        select.setFileFilter(new FileNameExtensionFilter("Arxius de dades 'dat', 'obj'", "dat", "obj")); 
        int element = select.showOpenDialog(null);
        if (element == JFileChooser.APPROVE_OPTION) {
            try {
                ctrl.loadData(select.getSelectedFile().getAbsolutePath());
                ctrl.updateVisor();
                return true;
            } catch (IOException | ClassNotFoundException ex) {
                getMessage("Error!", "S'ha produït un error!", JOptionPane.ERROR_MESSAGE);
            }
        }
        return false;
    }
    
    /**
     * 
     * Mètode per mostrar missatges d'error tipus POP-UP. Ara es fan servir a pocs llocs pq encara utilitzem
     * els missatges per consola. Només interactuen amb els dialegs de JFilechooser de swing.
     * @param title és el títol del missatge del Frame
     * @param text és el missatge del Frame
     * @param type és el tipus de Frame que mostra el diàleg
     */
    public void getMessage(String title, String text, int type){
        Component frame = null;
        JOptionPane.showMessageDialog(frame, text, title, type);  
    }
    
    /**
     * 
     * Mètode per preguntar a l'usuari Si o No.
     * @param title és el títol del missatge del Frame
     * @param text és el missatge del Frame
     * @return retorna la opció triada
     */
    public int getQuestion(String title, String text){
        String [] options = {"No vull!", "Si! Ho vull!"};
        return JOptionPane.showOptionDialog(null, text, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,options, options[0]);
   
    }
    
    /**
     * 
     * Mètode que filtra les extensions permeses.
     * @param path és la ruta del fitxer
     * @return true si l'extensió donada és correcte, false en cas contrari
     */
    private boolean isValidExtension(String path, String [] patternExtension){
        String currentExtension = path.substring(path.lastIndexOf('.') + 1);
        for(String baseExtension : patternExtension){
            if(currentExtension.equalsIgnoreCase(baseExtension)) // permeto barrejar  majúscula/minúscula
                return true;
        }
        return false;
    }
    
    /**
     * 
     * Retorna un jLabel amb l'estil que demanem.
     * @param title és el text
     * @param color el color del text
     * @param top marge de dalt
     * @param right marge dret
     * @param left marge esquerre
     * @param bottom marge inferior
     * @param size tamany de la lletra
     * @return l'onj jlabel
     */
    public JLabel getLabel(String title, Color color, int top, int right, int left, int bottom, int size){
        JLabel label = new JLabel(title, SwingConstants.LEFT);
        label.setForeground(color);
        label.setBorder(BorderFactory.createEmptyBorder(top, right, bottom, left));
        label.setFont(new Font(label.getName(), Font.PLAIN, size));
        label.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        return label;
    } 
    
    /**
     * 
     * Permet seleccionar una imatge directament de disc comprovant que existeixi i comprovant la validesa de l'extensió.
     * @return la ruta de la imatge o null si es tanca la finestra de SWING sense afegir una imatge
     */
    public String selectImage(){
        initializeFileChooserValues("Selecciona una imatge del teu PC");
        int element = select.showOpenDialog(null);
        if(element == JFileChooser.APPROVE_OPTION){
            String path = select.getSelectedFile().getAbsolutePath(); 
            while(!isValidExtension(path, ctrl.getImageExtension())){
                getMessage("Extensió invàlida!", "Les extensions possibles són:\njpg, jpeg, bmp, png, psd, gif i svg", JOptionPane.ERROR_MESSAGE);
                element = select.showOpenDialog(null);
                if(element == JFileChooser.CANCEL_OPTION) 
                    return null;
                path = select.getSelectedFile().getAbsolutePath(); 
            }
            return path;
        }
        return null;
    }
    
    /**
     * 
     * Afegeix una imatge al visor.
     * @param imageName nom de la imatge
     * @param path del fitxer
     * @return true si s'ha pogut afegir, false si ha saltat l'excepció.
     */
    public boolean addImage(String imageName, String path){
        try {
           ctrl.addImage(path, imageName);
        } catch (VisorException ex) {
           return false;
        }
        return true;
    }
    
    /**
     * 
     * Decideix si una imatge pot ser eliminada després de tenir en compte tots els casos possibles.
     * Es pot cancel·lar l'operació prement la lletra 'c'.
     * @param obj és l'obj que volem eliminar.
     */
    public void removeImage(Imatge obj){
        int res = getQuestion("Alerta!", "¿Segur que vols eliminar la imatge?");
        if(res == 1) {
            ctrl.removeImage(obj);
        }
    }
    
    /**
     * 
     * Elimina una imatge d'un àlbum.
     * @param album és l'àlbum d'imatges.
     * @param pos és la posició de la imatge.
     */
    public void removeImage(AlbumImatges album, int pos){
        int res = getQuestion("Alerta!", "¿Segur que vols eliminar la imatge?");
        if(res == 1) {
            ctrl.removeImage(album, pos);
        }
    }
    
    /**
     * 
     * Afegeix un àlbum nou comprovant que no n'existeixi un d'igual en títol
     * @param name
     * @param author
     * @return 
     */
    public boolean addAlbum(String name, String author){
        return ctrl.addAlbum(name, author);
    }
    
    /**
     * 
     * Elimina totes les imatges i el fitxer de dades sense tenir en compte cap condició. Formateja l'aplicació.
     */
    public void removeAll(){
        int res = getQuestion("Alerta!", "Aquesta opció destrueix tots els àlbums i les imatges. ¿Vols continuar?");
        if(res == 1) { //No
            ctrl.removeLibrary();
            settings.getDisplayLibrary();
        }
    }
    
    /**
     * 
     * Crea una finstra d'opcions.
     * @param obj és la imatge a visualitzar
     * @param numList és el número de llista
     * @param pos és la posició de de la imatge.
     */
    
    public void showDialogImageOptions(final Imatge obj, final int numList, final int pos){
        
        final LlistaImatges array = (numList == -1) ? ctrl.getLibrary() : ctrl.getAlbum(numList);
        final JDialog option = new JDialog();
        option.setSize(700,400);
        
        option.setLocationRelativeTo(null);
        
        JPanel content = new JPanel(new GridLayout(1, 2));
        
        JPanel contentLeft = new JPanel();
        JPanel contentRight = new JPanel();
        
        GridLayout gridDialog = new GridLayout(4, 1);
        contentLeft.setLayout(gridDialog);
        gridDialog.setVgap(20);

        JLabel labelOptions = getLabel("Visualitzar", Color.GRAY, 20, 50, 0, 0, 20);
        contentLeft.add(labelOptions);
        
        GradientButton emptyShow = new GradientButton("Sense filtres", Color.YELLOW, Color.ORANGE, Color.BLACK);
        GradientButton graysShow = new GradientButton("Blanc i negre", Color.YELLOW, Color.ORANGE, Color.BLACK);
        GradientButton sepiaShow = new GradientButton("Sípia", Color.YELLOW, Color.ORANGE, Color.BLACK);
        
        emptyShow.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                showImage(obj);
                option.dispose();
            }
        });
        graysShow.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                showImageBN(obj);
                option.dispose();
            }
        });
        sepiaShow.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                showImageSepia(obj);
                option.dispose();
            }
        });
        
        contentLeft.add(emptyShow);
        contentLeft.add(graysShow);
        contentLeft.add(sepiaShow);
        contentLeft.setBorder(new EmptyBorder(0, 15, 15, 15));   

        GridLayout gridDialog2 = new GridLayout(4, 1);
        contentRight.setLayout(gridDialog2);
        gridDialog2.setVgap(20);

        JLabel labelOptions2 = getLabel("Transformar", Color.GRAY, 20, 50, 0, 0, 20);
        contentRight.add(labelOptions2);
        
        GradientButton emptyFilter = new GradientButton("Sense filtres", Color.YELLOW, Color.ORANGE, Color.BLACK);
        GradientButton graysFilter = new GradientButton("Blanc i negre", Color.YELLOW, Color.ORANGE, Color.BLACK);
        GradientButton sepiaFilter = new GradientButton("Sípia", Color.YELLOW, Color.ORANGE, Color.BLACK);
        
        emptyFilter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                saveImage(obj, array, pos);
                returnToCurrentDisplay(numList, option);
            }
        });
        graysFilter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                saveImageBN(obj, array, pos);
                returnToCurrentDisplay(numList, option);
            }
        });
        sepiaFilter.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                saveImageSepia(obj, array, pos);
                returnToCurrentDisplay(numList, option);
            }
        });
        
        contentRight.add(emptyFilter);
        contentRight.add(graysFilter);
        contentRight.add(sepiaFilter);
        contentRight.setBorder(new EmptyBorder(0, 15, 15, 15));

        content.add(contentLeft);
        content.add(contentRight);
        option.add(content);
        option.setVisible(true);
       
    }
    
    /**
     * 
     * Surt de l'aplicació.
     */
    public void exit(){
        int res = getQuestion("Alerta!", "¿Segur que vols sortir?");
        if(res == 1) { //No
            System.exit(0);
        }
    }
    
    /**
     * 
     * @param numList torna a la pantalla actual.
     * @param option és el JDialog.
     */
    public void returnToCurrentDisplay(int numList, JDialog option){
        if(numList == -1){ 
            settings.getDisplayLibrary();
        }else{
            settings.getDisplayAlbum(numList);
        }
        option.dispose();
    }
}

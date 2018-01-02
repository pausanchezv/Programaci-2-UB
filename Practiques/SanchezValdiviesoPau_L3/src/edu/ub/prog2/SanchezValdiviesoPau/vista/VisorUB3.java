
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.utils.Menu;
import edu.ub.prog2.utils.VisorException;
import java.awt.Component;
import java.io.*;
import java.util.Scanner;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº3
 */
public class VisorUB3 { 
    
    private enum OptionsMainMenu {MANAGE_LIBRARY, MANAGE_ALBUMS, SAVE_DATA, LOAD_DATA, EXIT};
    private enum OptionsManageLibrary {ADD_IMAGE, VIEW_LIBRARY, REMOVE_IMAGE, VISUALIZE_IMAGE, TRANSFORM_IMAGE, REMOVE_LIBRARY, VISUALIZE_LIBRARY, STOP_VISUALIZE, RETURN_TO_BACK};
    private enum OptionsManageAllAlbums {ADD_ALBUM, VIEW_ALL_ALBUMS, REMOVE_ALBUM, MANAGE_ALBUM, RETURN_TO_BACK};
    private enum OptionsManageAlbum {ADD_IMAGE, VIEW_ALBUM, REMOVE_IMAGE, EDIT_ALBUM, VISUALIZE_IMAGE, TRANSFORM_IMAGE, VISUALIZE_ALBUM, STOP_VISUALIZE, RETURN_TO_BACK};
    private enum OptionsEditAlbumInfo {EDIT_TITLE, EDIT_FRONT, RETURN_TO_BACK};
    private enum OptionsVisualizaImage{VISUALIZA_NO_FILTER, VISUALIZAZE_SEPIA, VISUALIZE_BN,  RETURN_TO_BACK};
    private enum OptionsTransformImage{FILTER_SEPIA, FILTER_BN, WITHOUT_FILTER,  RETURN_TO_BACK};
    
    private final String [] descMenuPrincipal = {"Gestionar biblioteca", "Gestionar Àlbums", "Guardar dades", "Carregar dades", "Sortir"};;
    private final String [] descManageLibrary = {"Afegir imatge", "Mostrar biblioteca", "Eliminar una imatge", "Visualitzar Imatge", "Transformar Imatge", "Eliminar llibreria", "Visualitzar biblioteca", "Aturar la visualització", "Menú anterior"};
    private final String [] descManageAllAlbums = {"Afegir un àlbum", "Mostrar els àlbums", "Eliminar àlbum", "Gestionar un àlbum", "Menú anterior"};
    private final String [] descManageAlbum = {"Afegir una imatge", "Mostrar l'àlbum", "Eliminar una imatge", "Editar la info de l'àlbum", "Visualitzar Imatge", "Transformar Imatge", "Visualitzar àlbum", "Aturar la visualització", "Menú anterior"};
    private final String [] descEditAlbumInfo = {"Editar el títol", "Canviar la portada", "Menú anterior"};
    private final String [] descVisualizeImage = {"Visualització sense filtre", "Visualització amb filtre sèpia", "Visualització amb filtre blanc i negre", "Menú anterior"};
    private final String [] descTransformImage = {"Filtre Sepia", "Filtre blanc i negre", "Sense filtre", "Menú anterior"};
    
    private JFileChooser select;
    private CtrlVisor ctrl;
     
    /**
     * 
     * Constructor de la classe.
     * Crea un obj ctrl que ara serà l'única manera de contactar amb el model
     * Crea un obj JFileChoser de swing per seleccionar fitxers del disc de manera eficient
     *
     */
    public VisorUB3(){
        ctrl = new CtrlVisor();
        select = new JFileChooser();
    }
    
    /**
     * 
     * Crea el menú de l'aplicació i es crida des del main()
     * @param sc Scanner per llegir dades per teclat
     */
    public void gestioVisorUB(Scanner sc) {
        menuManageMain(sc);
    }

    /**
     * 
     * Menú principal del visor d'imatges
     * @param sc Objecte de tipus Scanner per rebre dades de teclat
     */
    private void menuManageMain(Scanner sc) {
        Menu<OptionsMainMenu> menu = new Menu<OptionsMainMenu>("Menu Principal VisorUB3",OptionsMainMenu.values());
        menu.setDescripcions(descMenuPrincipal);
        OptionsMainMenu option;
        
        do {
            menu.mostrarMenu();
            option = (OptionsMainMenu) getOption(sc, 0, OptionsMainMenu.values().length); // mètode propi per selccionar l'opció robustament (sense errors)
            
            switch(option) {
                case MANAGE_LIBRARY:
                    menuManageLibrary(sc);
                    break;
                case MANAGE_ALBUMS:
                    menuManageAllAlbums(sc);
                    break;
                case SAVE_DATA:
                    saveData();
                    break;
                case LOAD_DATA:
                    loadData();
                    break;
                case EXIT:
                    exitVisor();
                    break;
            }

        } while(option != OptionsMainMenu.EXIT);
    }
    
    /**
     * 
     * Menú secundari per gestionar la biblioteca
     * @param sc Objecte de tipus Scanner que permet accedir al teclat
     */
    private void menuManageLibrary(Scanner sc) {
        Menu<OptionsManageLibrary> menu = new Menu<OptionsManageLibrary>("Gestó de la biblioteca",OptionsManageLibrary.values());
        menu.setDescripcions(descManageLibrary);
        OptionsManageLibrary option;
        
        do {
            menu.mostrarMenu();
            option = (OptionsManageLibrary) getOption(sc, 1, OptionsManageLibrary.values().length);
            int library = -1; // -1 indica que manipulem imatges directament de biblioteca i no d'albums
            
            switch(option) {
                case ADD_IMAGE:
                    addImage(sc);
                    break;
                case VIEW_LIBRARY:
                    viewLibrary();
                    break;
                case REMOVE_IMAGE:
                    removeImage(sc);
                    break;
                case VISUALIZE_IMAGE:
                    menuVisualizeImage(sc, library);
                    break;
                case TRANSFORM_IMAGE:
                    menuTransformImage(sc, library);
                    break;
                case REMOVE_LIBRARY:
                    removeLibrary(sc);
                    break;
                case VISUALIZE_LIBRARY:
                    slideImages(ctrl.getLibrary());
                    break;
                case STOP_VISUALIZE:
                    stopSlide();
                    break;
                case RETURN_TO_BACK:
                    break;
            }

        } while(option != OptionsManageLibrary.RETURN_TO_BACK);
    }
    
    /**
     * 
     * Menú secundari per gestionar la llista d'àlbums
     * @param sc Objecte de tipus Scanner que permet accedir al teclat
     */
    private void menuManageAllAlbums(Scanner sc) {
        Menu<OptionsManageAllAlbums> menu=new Menu<OptionsManageAllAlbums>("Gestió dels àlbums",OptionsManageAllAlbums.values());
        menu.setDescripcions(descManageAllAlbums);
        OptionsManageAllAlbums option;
        
        do {
            menu.mostrarMenu();
            option = (OptionsManageAllAlbums) getOption(sc, 2, OptionsManageAllAlbums.values().length);
                
            switch(option) {
                case ADD_ALBUM:
                    addAlbum(sc);
                    break;
                case VIEW_ALL_ALBUMS:
                    viewAllAlbums();
                    break;
                case REMOVE_ALBUM:
                    removeAlbum(sc);
                    break;
                case MANAGE_ALBUM:
                    menuManageAlbum(sc);
                    break;
                case RETURN_TO_BACK:
                    break;
            }

        } while(option != OptionsManageAllAlbums.RETURN_TO_BACK);
    }
    
    /**
     * 
     * Menú secundari per gestionar un àlbum
     * @param sc Objecte de tipus Scanner que permet accedir al teclat
     */
    private void menuManageAlbum(Scanner sc) {
        Menu<OptionsManageAlbum> menu=new Menu<OptionsManageAlbum>("Gestió d'un àlbum",OptionsManageAlbum.values());
        menu.setDescripcions(descManageAlbum);
        OptionsManageAlbum option;
        int album = selectAlbum(sc); // retorna l'àlbum que volem gestionar
        
        if(album != -1){ // -1 indica que no hi ha àlbums
            do {
                menu.mostrarMenu();
                option = (OptionsManageAlbum) getOption(sc, 3, OptionsManageAlbum.values().length);

                switch(option) {

                    case ADD_IMAGE:
                        addImage(sc, album);
                        break;
                    case VIEW_ALBUM:
                        viewAlbum(album);
                        break;
                    case REMOVE_IMAGE:
                        removeImage(sc, album);
                        break;
                    case EDIT_ALBUM:
                        menuEditAlbumInfo(sc, album);
                        break;
                    case VISUALIZE_IMAGE:
                        menuVisualizeImage(sc, album);
                        break;
                    case TRANSFORM_IMAGE:
                        menuTransformImage(sc, album);
                        break;
                    case VISUALIZE_ALBUM:
                        slideImages(ctrl.getAlbum(album));
                        break;
                    case STOP_VISUALIZE:
                        stopSlide();
                        break;
                    case RETURN_TO_BACK:
                        break;
                }

            } while(option != OptionsManageAlbum.RETURN_TO_BACK);
        }
    }
    
    /**
     * 
     * Menú secundari per editar la info d'un àlbum
     * @param sc Objecte de tipus Scanner que permet accedir al teclat
     */
    private void menuEditAlbumInfo(Scanner sc, int album) {
        Menu<OptionsEditAlbumInfo> menu=new Menu<OptionsEditAlbumInfo>("Edita els atributs de l'àlbum",OptionsEditAlbumInfo.values());
        menu.setDescripcions(descEditAlbumInfo);
        OptionsEditAlbumInfo option;
        
        do {
            menu.mostrarMenu();
            option = (OptionsEditAlbumInfo) getOption(sc, 4, OptionsEditAlbumInfo.values().length);

            switch(option) {
                case EDIT_TITLE:
                    editTitle(sc, album);
                    break;
                case EDIT_FRONT:
                    editFront(sc, album);
                    break;
                case RETURN_TO_BACK:
                    break;
            }

        } while(option != OptionsEditAlbumInfo.RETURN_TO_BACK);
    }
    
    /**
     * 
     * Menú secundari per visualitzar imatges
     * @param sc Objecte de tipus Scanner que permet accedir al teclat
     */
    private void menuVisualizeImage(Scanner sc, int list) {
        Menu<OptionsVisualizaImage> menu=new Menu<OptionsVisualizaImage>("Visualitza una imatge de la Biblioteca",OptionsVisualizaImage.values());
        menu.setDescripcions(descVisualizeImage);
        OptionsVisualizaImage option;
        
        do {
            menu.mostrarMenu();
            option = (OptionsVisualizaImage) getOption(sc, 5, OptionsVisualizaImage.values().length);
            
            switch(option) {
                case VISUALIZA_NO_FILTER:
                    showImage(sc, list);
                    break;
                case VISUALIZAZE_SEPIA:
                    showImageSepia(sc, list);
                    break;
                case VISUALIZE_BN:
                    showImageBN(sc, list);
                    break;
                case RETURN_TO_BACK:
                    break;
            }

        } while(option != OptionsVisualizaImage.RETURN_TO_BACK);
    }
    
    /**
     * 
     * Menú secundari per filtrar imatges
     * @param sc Objecte de tipus Scanner que permet accedir al teclat
     */
    private void menuTransformImage(Scanner sc, int list) {
        Menu<OptionsTransformImage> menu=new Menu<OptionsTransformImage>("Transforma una imatge de la Biblioteca", OptionsTransformImage.values());
        menu.setDescripcions(descTransformImage);
        OptionsTransformImage option;
        
        do {
            menu.mostrarMenu();
            option = (OptionsTransformImage) getOption(sc, 6, OptionsTransformImage.values().length);

            switch(option) {
                case FILTER_SEPIA:
                    saveImageSepia(sc, list); // list pot voler dir àlbum o biblioteca
                    break;
                case FILTER_BN:
                    saveImageBN(sc, list);
                    break;
                case WITHOUT_FILTER:
                    saveImage(sc, list);
                    break;
                case RETURN_TO_BACK:
                    break;
            }

        } while(option != OptionsTransformImage.RETURN_TO_BACK);
    }
    
    
    /*************************************************************/
    /********* Mètodes per visualitzar i filtrar imatges *********/
    /*************************************************************/
    
    /**
     *
     * Visualitza un àlbum o la biblioteca
     * @param list és llista que volem visualizar
     */
    public void slideImages(Object list){ // Object pq no puc importar res del model !!
        try {
            ctrl.play(list);
        } catch (VisorException ex) {
            getErrorPrintMsg(ex);
        } catch (Exception ex) {
            getErrorPrintMsg(ex);
        }
    }
    
    /**
     * 
     * Atura la reproducció de la visualització
     */
    public void stopSlide(){
        try {
            ctrl.stop();
        } catch (VisorException ex) {
            getErrorPrintMsg(ex);
        }
    }
    
    /**
     * 
     * Guarda una imatge sense filtres.
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     */
    private void saveImage(Scanner sc, int list){
        int pos = getImage(sc, list);
        if(pos != -1){
            try {
                ctrl.saveImage(pos, list);
            } catch (IOException ex) {
                getErrorPrintMsg(ex);
            } catch (VisorException ex) {
                getErrorPrintMsg(ex);
            }
        }
    }
    
    /**
     * 
     * Guarda amb filtre sepia.
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     */
   private void saveImageSepia(Scanner sc, int list){
        int pos = getImage(sc, list);
        if(pos != -1){
            try {
                ctrl.saveImageSepia(pos, list);
            } catch (IOException ex) {
                getErrorPrintMsg(ex);
            } catch (VisorException ex) {
                getErrorPrintMsg(ex);
            }
        }
    }
   
   /**
     * 
     * Guarda una imatge amb filtre BN.
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     */
   private void saveImageBN(Scanner sc, int list){
        int pos = getImage(sc, list);
        if(pos != -1){
            try {
                ctrl.saveImageBN(pos, list);
            } catch (IOException ex) {
                getErrorPrintMsg(ex);
            } catch (VisorException ex) {
                getErrorPrintMsg(ex);
            }
        }
    }
    
   /**
    * 
    * Visualitza una imatge sense filtres
    * @param sc per llegir dades de teclat
    * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
    */
    private void showImage(Scanner sc, int list){
        int pos = getImage(sc, list);
        if(pos != -1){
            try {
                ctrl.showImage(pos, list);
            } catch (Exception ex) {
                getErrorPrintMsg(ex);
            }
        }
    }
    
   /**
    * 
    * Visualitza una imatge amb filtre sepia
    * @param sc per llegir dades de teclat
    * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
    */
    private void showImageSepia(Scanner sc, int list){
        int pos = getImage(sc, list);
        if(pos != -1){
            try {
                ctrl.showImageSepia(pos, list);
            } catch (Exception ex) {
                getErrorPrintMsg(ex);
            }
        }
    }
    
   /**
    * 
    * Visualitza una imatge amb filtre BN.
    * @param sc per llegir dades de teclat
    * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
    */
    public void showImageBN(Scanner sc, int list){
        int pos = getImage(sc, list);
        if(pos != -1){
            try {
                ctrl.showImageBN(pos, list);
            } catch (Exception ex) {
                getErrorPrintMsg(ex);
            }
        }
    }
    
    /**
     * 
     * Retorna una imatge (és per no repetir codi masses vegades)
     * @param sc per llegir dades de teclat
     * @param list si és -1 es refereix a biblioteca i si no és l'index de l'àlbum
     * @return 
     */
    private int getImage(Scanner sc, int list){
        int size = Integer.MIN_VALUE;
        if(list == -1){
            viewLibrary();
            size = ctrl.getLibrary().getSize();
        }else{
            viewAlbum(list);
            size = ctrl.getAlbum(list).getSize();
        }
        int image =  getPos(sc, "\nTria una imatge (1 - " + size + ")? 'Prem c per cancel·lar --> ", size, true); 
        return image;
    }
    
    
    /************************************************************************/
    /************ Mètodes privats de suport EDICIÓ D'ÀLBUM ******************/
    /************************************************************************/
    
    
    /**
     * 
     * Edita el títol d'un àlbum existent tenint en comte tots els casos que es demanen
     * @param sc Scanner per llegir dades de teclat
     * @param album el numero d'àlbum que estem gestionant
     */
    private void editTitle(Scanner sc, int album){
        System.out.print("\nEntra el nou títol --> ");
        boolean changeTitle = ctrl.editTitle(album, sc.nextLine());
        String out = changeTitle ? "\nTítol Actualitzat!\n" : "\nAquest títol ja existeix!\n";
        System.out.println(out);
            
    }
    
    /**
     * 
     * Permet canviar la portada d'un àlbum tenint en compte tots els casos d'error
     * @param sc Scanner per llegir dades de teclat
     * @param album el numero d'àlbum que estem gestionantalbum 
     */
    private void editFront(Scanner sc, int album){
        if(ctrl.getAlbum(album).getAlbum().isEmpty()){
            System.out.println("\nAquest àlbum encara no té imatges!\n");
        }else{
            viewAlbum(album);
            int size = ctrl.getAlbum(album).getSize();
            int front = getPos(sc, "\nEntra el numero de la nova portada (1 - " + size + ") 'Prem c per cancel·lar --> ", size, true);
            if(front != -1){
                ctrl.editFront(album, front);
                System.out.println("\nPortada actualitzada!\n");
            }
        }
    }
   
    /**
     * 
     * Elimina una determinada imatge d'un determinat àlbum
     * @param sc Scanner per llegir dades de teclat
     * @param album el numero d'àlbum que estem gestionantalbum album 
     */
   private void removeImage(Scanner sc, int album){
       if(ctrl.getAlbum(album).getAlbum().isEmpty()){
            System.out.println("\nAquest àlbum encara no té imatges!\n");
        }else{
            viewAlbum(album);
            int size = ctrl.getAlbum(album).getSize();
            int front = getPos(sc, "\nQuina imatge vols eliminar (1 - " + size + ") 'Prem c per cancel·lar --> ", size, true);
            if(front != -1){
                ctrl.removeImage(album, front);
                System.out.println("\nImatge eliminada de l'àlbum!\n");
            }
       }
    }
   
   /**
    * 
    * Afegeix una determinada imatge d'un determinat àlbum
    * @param sc Scanner per llegir dades de teclat
    * @param album el numero d'àlbum que estem gestionantalbum 
    */
    private void addImage(Scanner sc, int album){
        viewLibrary();
        if(!ctrl.getLibrary().getList().isEmpty()){
            int size = ctrl.getLibrary().getSize();
            int image = getPos(sc, "Quina imatge vols afegir (1 - " + size + ")? 'Prem c per canecel·lar' --> ", size, true);
            if(image != -1){
                ctrl.addImage(album, image);
                System.out.println("\nImatge afegida a l'àlbum!\n");
            }
        }
    }
    
    /**
     * 
     * Fa un echo per pantalla de l'àlbum i el seu contingut
     * @param album 
     */
    private void viewAlbum(int album){
        System.out.println("\n" + ctrl.getAlbum(album) + "\n");
        if(ctrl.getAlbum(album).getAlbum().isEmpty())
            System.out.println("No hi ha cap imatge en aquest àlbum!\n");
        for(int i = 0; i < ctrl.getAlbum(album).getSize(); i++)
            System.out.println("[" + (i + 1) + "] " + ctrl.getAlbum(album).getAt(i));
    }
    
    /************************************************************************/
    /*************** Mètodes privats dels àlbums en general *****************/
    /************************************************************************/
    
    /**
     * 
     * Permet seleccionar un àlbum per a gestionar
     * @param sc Scanner per llegir dades de teclat
     * @return la posició de l'àlbum en l'array dels àlbums
     */
    private int selectAlbum(Scanner sc){
        viewAllAlbums();
        int size = ctrl.getAllAlbums().size();
        int album = getPos(sc, "Quin àlbum vols gestionar (1 - " + size + ")? 'Prem c per cancel·lar' --> ", size, true);
        if(album != -1) System.out.println("\nEstàs gestionant l'àlbum (" + (album + 1) + ")\n");
        return (!ctrl.getAllAlbums().isEmpty() ? album : -1);
    }
    
    /**
     * 
     * Afegeix un àlbum nou comprovant que no n'existeixi un d'igual en títol
     * @param sc Scanner per llegir dades de teclat
     */
    private void addAlbum(Scanner sc){
        System.out.println("\nQuin és el títol de l'àlbum?");
        String title = sc.nextLine();
        System.out.println("\nQui n'és l'autor?");
        String author = sc.nextLine();
        boolean isAdded = ctrl.addAlbum(title, author);
        String out = isAdded ? "\nÀlbum afegit amb èxit!\n" : "\nJa hi ha un àlbum amb el mateix nom!\n";
        System.out.println(out);
    }
    
    /**
     * 
     * Selecciona un àlbum per eliminar
     * @param sc Scanner per llegir dades de teclat
     */
    private void removeAlbum(Scanner sc){
         viewAllAlbums();
         if(!ctrl.getAllAlbums().isEmpty()){
            int size = ctrl.getAllAlbums().size();
            int album = getPos(sc, "Quin àlbum vols eliminar? (1 - " + size + ") 'Prem c per cancel·lar'--> ", size, true);
            if(album > -1) {
                ctrl.removeAlbum(album); 
                System.out.println("\nÀlbum eliminat amb èxit!\n");
            }
         }
    }
    
    /**
     * 
     * Fa un echo per pantalla de tots els àlbums existents
     */
    private void viewAllAlbums(){
        System.out.println(ctrl.getObjDadesVisor());
    }

    
    /*************************************************************************/
    /*************** Mètodes privats de suport BIBLIOTECA ********************/
    /*************************************************************************/
    
    /**
     * 
     * Recull les dades bàsiques d'una imatge per tractar la  seva adhesió a l'array prèvia comprovació de tots els casos possibles 
     * i la validesa de l'extensió.
     * @param sc Scanner per rebre dades per teclat
     */
    private void addImage(Scanner sc){
        try {
            System.out.println("Entra el nom de la imatge");
            String imageName = sc.nextLine();
            String path = selectImage();
            if(path != null){
                ctrl.addImage(path, imageName);
                System.out.println("\nImatge afegida amb èxit!\n");
            }else{
                System.out.println("\nOperació cancel·lada!\n");
            }
        } catch (VisorException ex) {
            System.out.println(ex);
        }
    }
    
    /**
     * 
     * Permet seleccionar una imatge directament de disc comprovant que existeixi i comprovant la validesa de l'extensió.
     * @return la ruta de la imatge o null si es tanca la finestra de SWING sense afegir una imatge
     */
    private String selectImage(){
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
     * Decideix si una imatge pot ser eliminada després de tenir en compte tots els casos possibles.
     * Es pot cancel·lar l'operació prement la lletra 'c'.
     * @param sc Scanner per entrar dades per teclat
     * @param array és un obj de la classe TaulaImatges
     */
    private void removeImage(Scanner sc){
        viewLibrary();
        if(!ctrl.getLibrary().getList().isEmpty()){
            int size = ctrl.getLibrary().getSize();
            int image = getPos(sc, "Quina imatge vols eliminar? (1 - " + size + ")\nPrem 'c' per cancel·lar.. --> ", size, true);
            if(image != -1){
                ctrl.removeImage(image);
                System.out.println("\nImatge eliminada amb èxit\n");
            }
        }
    }
    
    /**
     * 
     * Elimina totes les imatges i el fitxer de dades sense tenir en compte cap condició. Formateja l'aplicació.
     * @param sc Scanner per entrar dades per teclat
     */
    private void removeLibrary(Scanner sc){
        if(ctrl.getLibrary().getSize() > 0){
            System.out.print("\nSegur que vols eliminar-ho tot? (s/n) --> ");
            String res = sc.nextLine();
            if(!res.equalsIgnoreCase("s") && !res.equalsIgnoreCase("n")){
                System.out.println("\nResposta incorrecta!");
                removeLibrary(sc); // crida recursiva fins entrar una resposta vàlida (alternativa al while)
            }
            if(res.equalsIgnoreCase("s")){
                ctrl.removeLibrary();
                System.out.println("\nAplicació formatejada amb èxit!\n");
            }
        } else {
            System.out.println("\nAquí no hi ha res per eliminar!\n");
        }
    }
    
    /**
     * 
     * Mètode que mostra a l'usuari totes les imatges de la llista quan és cridat
     * @param array és l'objecte per imprimir per pantalla
     */
    private void viewLibrary(){
        System.out.println(ctrl.getLibrary());

    }
    
    /**********************************************************************/
    /********** Mètodes per guardar i recuperar les dades *****************/
    /**********************************************************************/
    

    /**
     * 
     * Guarda un fitxer de dades al disc, per a fer-ho ha de tenir en compte que l'extensió del fitxer
     * sigui vàlida: .dat o .obj per no liar molt la troca.
     * Per a fer el diàleg estable i robust cal fer algunes comprovacions:
     * 
     * Primer if --> mira que es premi el botó "guardar" del frame
     * While --> itera fins que l'extensió és .dat o .obj
     * (Dins el while) If --> Si és prem cancel·lar després de fallar amb l'extensió, permet sortir
     * (Dins el while) Else --> Assigna el path a una variable
     * If --> Crida al controlador per guardar el fitxer si no hem cancelat l'operació
     * 
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
                        System.out.println("\nOperació cancel·lada\n");
                        break;
                    }else{
                        file = select.getSelectedFile().getAbsolutePath();
                    }
                }
                if(!cancelAction){
                    ctrl.saveData(file);
                    System.out.println("\nDades guardades amb èxit!\n");
                }
            } catch (IOException ex) {
                System.out.println("\nError al guardar el fitxer\n");
            }
        }else{
            System.out.println("\nOperació cancel·lada!\n");
        }
    }
    
    /**
     * 
     * Recupera dades des del disc.
     * Si no se selecciona un .dat o un .obj l'exepció VisorExeption retorna els casos d'error
     */
    private void loadData(){
        initializeFileChooserValues("Selecciona un fitxer de dades per recuperar");
        select.setFileFilter(new FileNameExtensionFilter("Arxius de dades 'dat', 'obj'", "dat", "obj")); 
        int element = select.showOpenDialog(null);
        if (element == JFileChooser.APPROVE_OPTION) {
            try {
                ctrl.loadData(select.getSelectedFile().getAbsolutePath());
                ctrl.updateVisor();
                System.out.println("\nDades recuperades amb èxit!\n");
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println("\nError al carregar el fitxer!\n");
            }
        } else {
            System.out.println("\nOperació cancel·lada!\n");
        }
 
    }
    
    
    /**********************************************************************/
    /*************** Altres mètodes privats de suport *********************/
    /**********************************************************************/
    
    /**
     * 
     * Inicialitza els valor per defecte de JFileChooser per no arrossegar informació anterior ja que
     * utilitzo el mateix obj per tots els frames de gestió de fitxers.
     * @param title és el títol del frame que es veu a la part superior
     */
    public void initializeFileChooserValues(String title){
        select.setSelectedFile(new File("")); // Neteja el camp 'nom'
        select.resetChoosableFileFilters(); // Neteja les extensions seleccionables
        select.setDialogTitle(title); // Mostra el títol del Frame passat com a argument
        select.setCurrentDirectory(null); // Resetejo el directori pq no torni a l'últim explorat
    }
    
    /**
     * 
     * Missatge de despedida
     */
    private void exitVisor(){
        System.out.println("Fins aviat!");
    }
    
    /**
     * 
     * Selecciona un enter vàlid i el retorna (per gestionar àlbum, canviar portada, eliminar àlbum, etc...)
     * @param sc Scanner per rebre dades de teclat
     * @param msg String que mostra el missatge adequat
     * @param cancel Per decidir si hi ha opció de cancelar l'operació o no
     * @return la posició
     */
    private int getPos(Scanner sc, String msg, int size, boolean cancel){
        System.out.print(msg);
        String pos = sc.nextLine();
        while(pos.equals("") || pos.contains(" ") || !isNumeric(pos) || !inRank(1, size, Integer.parseInt(pos))){
            if(pos.equalsIgnoreCase("c") && cancel){
                System.out.println("\nOperació cancel·lada!\n");
                return -1;
            }
            System.out.print("\nEntrada incorrecta! Entra un numero vàlid --> ");
            pos = sc.nextLine();
        }
        return (Integer.parseInt(pos) - 1);
    }
    
    /**
     * 
     * Mètode que comprova que un numero es trobi dins d'un rank
     * @param first inici del rang
     * @param last final del rang
     * @param pos numero que volem saber si es troba dins el rang
     * @return true si es troba dins el rang, false en cas contrari
     */
    private boolean inRank(int first, int last, int pos){
        return (pos >= first && pos <= last);
    }
    
    /**
     * 
     * Mètode que comprova que un o més caràcters són dígits (si es tracta d'un numero).
     * @param str entra un String per comparar
     * @return true si és un numero, false en cas contrari
     */
    private boolean isNumeric(String str){
        for (char c : str.toCharArray()){
            if (!Character.isDigit(c)) 
                return false;
        }
        return true;
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
     * Soluciona els problemes del ménu per si l'usuari entra caràcters no numèrics o nombres fora de rank
     * @param sc Scanner per rebre dades de teclat
     * @return el valor de la posició de l'enum d les opcions del menú ara ja sense cap errada
     */
    private Object getOption(Scanner sc, int num, int lenOptions){
        int opt = getPos(sc, "Entra una opció --> ", lenOptions, false);
        return (num == 0 ? OptionsMainMenu.values()[opt] : 
                (num == 1 ? OptionsManageLibrary.values()[opt] : 
                (num == 2 ? OptionsManageAllAlbums.values()[opt] : 
                (num == 3 ? OptionsManageAlbum.values()[opt] : 
                (num == 4 ? OptionsEditAlbumInfo.values()[opt] : 
                (num == 5 ? OptionsVisualizaImage.values()[opt] : 
                (num == 6 ? OptionsTransformImage.values()[opt] :  null )))))));
    }
    
    /**
     * 
     * Mètode per mostrar missatges d'error tipus POP-UP. Ara es fan servir a pocs llocs pq encara utilitzem
     * els missatges per consola. Només interactuen amb els dialegs de JFilechooser de swing.
     * @param title és el títol del missatge del Frame
     * @param text és el missatge del Frame
     * @param type és el tipus de Frame que mostra el diàleg
     */
    private void getMessage(String title, String text, int type){
        Component frame = null;
        JOptionPane.showMessageDialog(frame, text, title, type);  
    }
    
    /**
     * Printeja per pantalla l'error de l'una excepció.
     * @param ex -es el missatge de l'excepció
     */
    private void getErrorPrintMsg(Exception ex){
        System.out.println("S'ha produït un error --> " + ex);
    }
}


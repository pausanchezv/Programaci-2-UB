
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import edu.ub.prog2.SanchezValdiviesoPau.model.*;
import edu.ub.prog2.utils.InImageList;
import edu.ub.prog2.utils.Menu;
import java.io.*;
import java.util.Scanner;

/**
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº1
 */
public class VisorUB1 {
    
    // constants
    private static final File DB_FILE = new File("src" + File.separator + "dades" + File.separator + "dades.dat");
    private static final String DB_NAME = DB_FILE.getName(); 
    private static final String DB_FOLDER = DB_FILE.getPath().substring(0 , DB_FILE.getPath().lastIndexOf(File.separator));
    private final String [] PATTERN_EXTENSION = {"jpg", "jpeg", "bmp", "png", "psd", "gif", "svg"};
    
    // atributs i variables
    private static enum OpcionsMenuPrincipal {ADD_IMAGE, REMOVE_IMAGE, VIEW_LIST, SAVE_LIST, LOAD_LIST, CLEAR_LIST, EXIT};
    private String version; // ens diu la versió del visor en el que estem (per l'usuari saber quin visor està utilitzant)
    private static String [] descMenuPrincipal;
    private InImageList array;
    
    // Atributs statics de classe
    public static boolean savedRemember; // provisionalment l'activo des del model per mostrar alguns misatges informatius al guardar/recuperar
    public static boolean error; // per mostrar msg d'error en cas que no es pugui afegir una imatge ja que no ens deixes fer echos a <llistaImatges>
    
    /**
     * Constructor de la classe.
     * Crea les opcions del menú i crida al mètode que crea l'objecte del visor seleccionat.
     * Crea un boleà que m'ajuda en les opcions de guardar/recuperar per comprovar si hi ha dades
     *
     * @param numVisor és el visor que volem utilitzar
     */
    public VisorUB1(int numVisor){
        selectVisor(numVisor);
        descMenuPrincipal = new String[] {"Afegir una imatge", "Eliminar una imatge", "Mostrar llista d'imatges", "Guardar la llista", "Recuperar la llista", "Eliminar-ho tot", "Sortir"};
        savedRemember = false;
        error = false;
    }
    
    /**
     * Crea el menú de l'aplicació i es crida des del main()
     * @param sc Scanner per llegir dades per teclat
     */
    public void gestioVisorUB(Scanner sc) {
        gestioMenuPrincipal(sc);
    }

    /**
     * Menú principal del visor d'imatges
     * @param sc Objecte de tipus Scanner per rebre dades de teclat
     */
    private void gestioMenuPrincipal(Scanner sc) {

        Menu<OpcionsMenuPrincipal> menu = new Menu<OpcionsMenuPrincipal>("Menu Principal Visor UB1 <" + version + ">",OpcionsMenuPrincipal.values());
        menu.setDescripcions(descMenuPrincipal);
        OpcionsMenuPrincipal option;
        
        do {

            menu.mostrarMenu();
            option = getOption(sc); // <<--- Mètode creat per millorar la robustesa del menú

            switch(option) {
                case ADD_IMAGE:
                    addImageInfo(sc); 
                    break;
                case REMOVE_IMAGE:
                    selectImageToRemove(sc);
                    break;
                case VIEW_LIST:
                    viewAllImages();
                    break;
                case SAVE_LIST:
                    saveDataOption(sc);
                    break;
                case LOAD_LIST:
                    loadDataOption(sc);
                    break;
                case CLEAR_LIST:
                    removeAllImages(sc);
                    break;
                case EXIT:
                    exitVisor();
                    break;
                // default innecessari. Faig retornar el missatge d'error amb la meva func getOption() de manera que aqui mai arriba un cas incorrecte!
            }
        } while(option != OpcionsMenuPrincipal.EXIT);
    }
    
    /********************************************************************/
    /********************** Mètodes privats de suport *************************/
    /*******************************************************************/
    
    /**
     * Mètode que seleciona el visor que volem inicialitzar
     * @param num és el numero del visor que volem veure
     */
    private void selectVisor(int num){
        if(num == 1){
            array = new TaulaImatges();
            version = "Array normal";
        }else{
            array = new LlistaImatges();
            version = "ArrayList";
        }
    }
    
    /**
     * Recull les dades bàsiques d'una imatge, crea l'objecte i l'envia al mètode addImage per tractar la
     * seva adhesió a l'array prèvia comprovació de tots els casos possibles i la validesa de l'extensió.
     * 
     * @param sc Scanner per rebre dades per teclat
     * @return l'objecte Imatge que acabem de crear
     */
    private void addImageInfo(Scanner sc){
        System.out.println("Entra el nom de la imatge");
        String imageName = sc.nextLine();
        System.out.println("Entra la ruta de la imatge");
        String path = sc.nextLine();
        while(path.contains(" ") || !isValidExtension(path)){ // comprova que el path sigui vàlid
            if(path.contains(" ")){ // primer miro espais
                System.out.println("\nNo pots posar espais!\nTorna a entrar la ruta!");
            }else if(!isValidExtension(path)){ // si no hi ha espais ara miro extensió
                System.out.println("\nL'extensió ha de ser vàlida (jpg, jpeg, gif, png, psd, bmp, svg)\nTorna a entrar la ruta!"); 
            }
            path = sc.nextLine();
        }
        array.addImage(new Imatge(path, imageName));
        savedRemember = false;
        if(error){
            System.out.println("\nError! La imatge ja existeix o l'array està plè!\n");
            error = false;
        }else{
            System.out.println("\nImatge afegida amb èxit!\n");
        }
    }
    
    /**
     * 
     * Decideix si una imatge pot ser eliminada després de tenir en compte tots els casos possibles.
     * Es pot cancel·lar l'operació prement la lletra 'c'.
     * 
     * Necessito comprovar si l'array conté imatges des d'aqui ja que si no em demanaria entrar quina imatge vull eliminar igualment i
     * aleshores hi ha conflicte!. Em sap greu ja sé que vas dir de comprovar-ho a Llista/TaulaImatges pero en aquest cas no pot ser.
     * 
     * @param sc Scanner per entrar dades per teclat
     * @param array és un obj de la classe TaulaImatges
     * 
     */
    private void selectImageToRemove(Scanner sc){
        System.out.println(array); // Mostrem les imatges primer
        if(array.getSize() > 0){
            System.out.println("Quina imatge vols eliminar? (1 - " + array.getSize() + ")\nPrem 'c' per cancel·lar..");
            String pos = sc.nextLine();

            while((!isNumeric(pos) || !inRank(1, array.getSize(), Integer.parseInt(pos))) && !pos.equalsIgnoreCase("c")){ // Mira la validesa del numero
                    System.out.println("\nError! Numero incorrecte!\nEntra'n un altre o prem 'c' per cancel·lar..");
                    pos = sc.nextLine();
            }
            if(!pos.equalsIgnoreCase("c")){
                Imatge obj = (Imatge)array.getAt(Integer.parseInt(pos) - 1);
                array.removeImage(obj);
                System.out.println("\nImatge eliminada amb èxit\n");
            }
        }
    }
    
    /**
     * Elimina totes les imatges i el fitxer de dades sense tenir en compte cap condició. Formateja l'aplicació.
     * @param sc 
     */
    private void removeAllImages(Scanner sc){
        System.out.println("\nSegur que vols eliminar-ho tot? (s/n)");
        String res = sc.nextLine();

        if(!res.equalsIgnoreCase("s") && !res.equalsIgnoreCase("n")){
            System.out.println("\nResposta incorrecta!");
            removeAllImages(sc); // crida recursiva fins entrar una resposta vàlida (alternativa al while)
        }
        if(res.equalsIgnoreCase("s")){
            array.clear();
            DB_FILE.delete();
            System.out.println("\nAplicació formatejada amb èxit!\n");
        }
    }
    
    /**
     * Mètode que mostra a l'usuari totes les imatges de la llista quan és cridat
     * @param array és l'objecte per imprimir per pantalla
     */
    private void viewAllImages(){
        System.out.println(array);
    }
    
    /**
     * Aquest mètode comprova tot el que cal abans de guardar les dades. En cas que tot estigui ok, es fa la crida
     * a la funció següent que s'encarrega de fer l'acció de guardar.
     * 1. Comprova si existeixen dades anteriors i en cas afirmatiu demana si les vols sobreescriure.
     * 2. Només hi ha dues respostes correctes (s, n) en cas de rebre qualsevol entrada no vàlida, la funció es
     *    torna a cridar a ella mateixa recursivament fins a rebre una entrada vàlida.
     * 3. Si la resposta és afirmativa cridem a la funció que guardarà les dades sobreescrivint les anteriors.
     *    Si la resposta és negativa retornem al menú.
     *    Si no hi havia dades anteriors cridem a la funció que guardarà les dades.
     * 
     * @param sc Scanner per llegir dades per teclat
     * //obs// El boleà savedRemember controla que si guardem dues vegades seguides sense haver realitzat cap canvi
     * no torni a aparèixer el missatge de sobreescriptura fins que es produeixin nous canvis.
     */
    
    private void saveDataOption(Scanner sc){
        if(isFileExists(getFolderFiles(DB_FOLDER), DB_NAME) && !savedRemember){
            System.out.println("\nVols sobreescriure les dades anteriors? (s/n)");
            String resp = sc.nextLine();
            if(resp.equalsIgnoreCase("s")){
                saveImagesToDataFile();
            }else if(!resp.equalsIgnoreCase("n")){
                System.out.println("\nResposta incorrecta!\n");
                saveDataOption(sc); // crido recursivament fins que la resposta és correcta (alternativa al while)
            }
        }else{
            saveImagesToDataFile();
        }
    }
    
    /**
     * Guarda l'obj al fitxer local "/dades/dades.dat" controlant les excepcions.
     * 
     * Si l'array està buit, llavors elimino el fitxer base de dades "dades.dat" per 
     * poder tornar a realitzar les operacions i comprobacions prèvies correctament.
     */
    private void saveImagesToDataFile(){
        try {
            ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(DB_FILE));
            output.writeObject(array);
            output.close();
            System.out.println("\nDades guardades amb èxit!\n");
            savedRemember = true;
            if(array.getSize() < 1)
                DB_FILE.delete();
        } catch (FileNotFoundException ex) {
            System.out.println("\nError. El fitxer no existeix!\n");
        } catch (IOException ex) {
            System.out.println("\nError guardar\n");
        }
    }
    
    /**
     * Mètode que comprova tot el que cal abans de carregar les dades des del disc. Si tot està ok es fa la crida a la
     * funció següent que s'encarrega de fer l'acció de carregar les dades.
     * 1. Si no existeix fitxer de dades no hi ha res per recuperar i ja hem acabat.
     * 2. Si tenim imatges a l'array i el boleà savedRemember es false avisa que tenim dades a l'array sense guardar.
     * 3. Només hi ha dues respostes correctes (s, n) en cas de rebre qualsevol entrada no vàlida, la funció es
     *    torna a cridar a ella mateixa recursivament fins a rebre una entrada vàlida.
     * 4. Si la resposta és afirmativa cridem a la funció que carregarà les dades des del disc.
     *    Si la resposta és negativa retornem al menú.
     *    Si no hi havia dades anteriors cridem a la funció que carrega les dades des del disc.
     * 
     * @param sc Scanner per llegir dades per teclat
     * //obs// El boleà savedRemember controla que si carreguem dues vegades seguides sense haver realitzat cap canvi
     * no torni a aparèixer el missatge de "dades sense guardar" fins que es produeixin nous canvis.
     */
    private void loadDataOption(Scanner sc){
        if(!isFileExists(getFolderFiles(DB_FOLDER), DB_NAME)){
            System.out.println("\nNo hi ha dades per recuperar!\n");
        }else{
            if(array.getSize() > 0 && !savedRemember){
                System.out.println("\nHi ha dades sense guardar.\nVols continuar igualment? (s/n)");
                String resp = sc.nextLine();
                if(resp.equalsIgnoreCase("s")){
                    loadImagesFromDataFile();
                }else if(!resp.equalsIgnoreCase("n")){
                    System.out.println("\nResposta incorrecta!\n");
                    loadDataOption(sc); // crido recursivament fins que la resposta és correcta (alternativa al while)
                }
            }else{
                loadImagesFromDataFile();
            }
        }
    }
    
    /**
     * Recupera l'obj del fitxer extern "/dades/dades.dat" i el carrega per a poder seguir sent utilitzat.
     */
    private void loadImagesFromDataFile(){
        try {
            ObjectInputStream input = new ObjectInputStream(new FileInputStream(DB_FILE));
            array = (InImageList) input.readObject();
            input.close();
            System.out.println("\nDades recuperades amb èxit!\n");
            savedRemember = true;
        } catch (FileNotFoundException ex) {
            System.out.println("\nError. El fitxer no existeix!\n");
        } catch (IOException | ClassNotFoundException ex) {
            System.out.println("\nError al carregar el fitxer\n");
        }
    }
    
    /**
     * Missatge de despedida i sortida del programa sense tornar al menú de selecció de visor.
     */
    private void exitVisor(){
        System.out.println("Fins aviat!");
        System.exit(0);
    }
    
    /**
     * Mètode que comprova que un numero es trobi dins d'un rang
     * 
     * @param first inici del rang
     * @param last final del rang
     * @param pos numero que volem saber si es troba dins el rang
     * @return true si es troba dins el rang, false en cas contrari
     */
    private boolean inRank(int first, int last, int pos){
        return (pos >= first && pos <= last);
    }
    
    /**
     * Mètode que comprova que un o més caràcters són dígits (si es tracta d'un numero).
     * 
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
     * Mètode que filtra les extensions permeses. En el nostre cas imatges però la modularitat del mètode permet ser
     * transportat a qualsevol tipus de fitxer.
     * 
     * @param path és la ruta del fitxer
     * @return true si l'extensió donada és correcte, false en cas contrari
     */
    private boolean isValidExtension(String path){
        String currentExtension = path.substring(path.lastIndexOf('.') + 1);
        for(String baseExtension : PATTERN_EXTENSION){
            if(currentExtension.equalsIgnoreCase(baseExtension)) // permeto barrejar  majúscula/minúscula
                return true;
        }
        return false;
    }
    
    /**
     * Mètode que m'he vist obligat a crear per solucionar els problemes del menú ja que al introduïr una caracter no
     * numèric el programa petava i no era prou robust.
     * 
     * @param sc Scanner per rebre dades de teclat
     * @return el valor de la posició de l'enum d les opcions del menú ara ja sense cap errada
     */
    private OpcionsMenuPrincipal getOption(Scanner sc){
        System.out.println("--> Entra una opció: ");
        String option = sc.nextLine();
        while(option.contains(" ") || !isNumeric(option) || !inRank(1, OpcionsMenuPrincipal.values().length, Integer.parseInt(option))){
            System.out.println("--> Opció incorrecta!!. Entra una opció: ");
            option = sc.nextLine();
        }
        return OpcionsMenuPrincipal.values()[Integer.parseInt(option) - 1];
    }
    
    /**
     * Comprova si existeix un fitxer de dades en una llista de fitxers
     * @param files és l'array de fitxers que ens retorna folderFiles(), són els fitxers de dades existents
     * @param fileName és el nom del fitxer que volem comparar per saber si existeix.
     * @return true si el fitxer existeix, false en cas contrari
     */
    private boolean isFileExists(File [] files, String fileName){
        for(File file : files){
            if(file.getName().equals(fileName)){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Mètode que ens retorna tots els arxius d'un directori en un array
     * @return el contingut del directori contenidor dels arxius de dades
     * @param folder és el directori que conté els arxius de dades
     */
    private File [] getFolderFiles(String folder){
        File file = new File(folder);
        return file.listFiles();
    }
    
}
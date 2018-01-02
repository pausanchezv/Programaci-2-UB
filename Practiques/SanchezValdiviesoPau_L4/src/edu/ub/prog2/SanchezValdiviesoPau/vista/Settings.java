
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.SanchezValdiviesoPau.model.Imatge;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * Aquesta és la classe més important de totes. Aqui es creen tots els displays (pantalles)
 * del visor i es fan les crides al display que toca a petició de l'event actual.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public class Settings{ 
    
    /**
     * Les 5 zones que formen el visorUB4.
     */
    private final JPanel container;
    private final JPanel north;
    private final JPanel south;
    private final JPanel center;     
    private final JPanel west;
    private final JPanel east;
    
    /**
     * Variables de control i funcions de suport.
     */
    private final CtrlVisor ctrl;
    private final Functions func;
    
    /**
     * Pantalles del VisorUB4.
     */
    private final DisplayWelcome welcome;
    private final DisplayLibrary library;
    private final DisplayAlbums albums;
    private final DisplayAddImage addImage;
    private final DisplayAddAlbum addAlbum;
    private final DisplayAlbum album;
    private final DisplayAddImageToAlbum addImageAlbum;
    private final DisplayChangeFront changeFront;
    private final DisplayEditTitleAlbum editTitle;
    private final DisplayVisualize visualize;
    
    /**
     * Menú principal.
     */
    private TopBarMenu topMenu;
    private final JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    
    /**
     * Zona d'scroller principal central.
     */
    private final JScrollPane scrollPane;
    
    /**
     * Construeix el mapa de zones i crea les seves instàncies (úniques al
     * del programa).
     */
    public Settings(){
        
        this.container  = new JPanel(new BorderLayout());
        this.north = new JPanel();
        this.south = new JPanel();
        this.center = new JPanel();       
        this.west = new JPanel();
        this.east = new JPanel();
        
        this.ctrl = new CtrlVisor(this);
        this.func = new Functions(ctrl);
      
        this.menuBar = new JMenuBar();
        
        this.scrollPane = new JScrollPane(center);
        this.scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        this.scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        this.container.add(scrollPane);
        
        this.welcome = new DisplayWelcome(container, north, center, east, ctrl, this);
        this.library = new DisplayLibrary(container, scrollPane, north, center, east, ctrl, this);
        this.albums = new DisplayAlbums(container, north, center, east, ctrl, this);
        this.addImage = new DisplayAddImage(container, scrollPane, north, east, center, ctrl, this);
        this.addAlbum = new DisplayAddAlbum(container, scrollPane, north, east, center, ctrl, this);
        this.album = new DisplayAlbum(container, north, center, east, ctrl, this);
        this.addImageAlbum = new DisplayAddImageToAlbum(container, north, center, east, ctrl, this);
        this.changeFront = new DisplayChangeFront(container, north, center, east, ctrl, this);
        this.editTitle = new DisplayEditTitleAlbum(container, scrollPane, north, east, center, ctrl, this);
        this.visualize = new DisplayVisualize(container, scrollPane, north, east, center, ctrl, this);
    }
    
    
    /**
     * 
     * Crea la pantalla Biblioteca.
     */
    public void getDisplayLibrary(){
        library.createZone();
    }
    
    /**
     * 
     * Crea la pantalla de benvinguda.
     */
    public void getDisplayWelcome(){
        welcome.createZone();
    }
    
    /**
     * 
     * Crea la pantalla de gestió dels àlbums.
     */
    public void getDisplayAlbums(){
        albums.createZone();
    }
    
    /**
     * 
     * Crea la pantalla per afegir una imatge.
     */
    public void getDisplayAddImage(){
        addImage.createZone();
    }
    
    /**
     * 
     * Crea la pantalla Biblioteca.
     */
    public void getDisplayAddAlbum(){
        addAlbum.createZone();
    }
    
    /**
     * 
     * Crea la pantalla d'ujn àlbum.
     * @param pos és el número d'àlbum
     */
    public void getDisplayAlbum(int pos){
        album.createZone(pos);
    }
    
    /**
     * 
     * Crea la pantalla d'un àlbum.
     * @param pos és el número d'àlbum
     */
    public void getDisplayAddImageToAlbum(int pos){
        addImageAlbum.createZone(pos);
    }
    
    /**
     * 
     * Crea la pantalla per canviar la portada.
     * @param pos és el número d'àlbum
     */
    public void getDisplayChangeFront(int pos){
        changeFront.createZone(pos);
    }
    
    /**
     * 
     * Crea la pantalla per canviar títol àlbum.
     * @param pos és el número d'àlbum
     */
    public void getDisplayEditTitleAlbum(int pos){
        editTitle.createZone(pos);
    }
    
    /**
     * 
     * Crea la pantalla per canviar títol àlbum.
     * @param obj és la imatge.
     */
    public void getVisualizeZone(Imatge obj){
        visualize.createZone(obj);
    }
    
     /**
     * Crea totes les zones assignant les preferences desitjades.
     * @return el JPanel principal separat en seccions (nort, sud, centre, est i oest)
     */
    public Container createMainPanel(){
        
        container.setOpaque(true);
       
        north.setPreferredSize(new Dimension(0, 65));
        east.setPreferredSize(new Dimension(190, 100));
        west.setPreferredSize(new Dimension(20, 100));
        south.setPreferredSize(new Dimension(20, 30));
        
        north.setBackground(Color.LIGHT_GRAY);
        south.setBackground(Color.LIGHT_GRAY);
        east.setBackground(Color.LIGHT_GRAY);
        west.setBackground(Color.LIGHT_GRAY);
        
        container.setLayout(new BorderLayout());
        container.add(center);
        
        container.add(north, BorderLayout.NORTH);
        container.add(south, BorderLayout.SOUTH);
        container.add(center, BorderLayout.CENTER);
        container.add(west, BorderLayout.WEST);
        container.add(east, BorderLayout.EAST);  
        
        /**
         * La zona sud és úncica, per això la creo aquí només una vegada.
         */
        JLabel author = func.getLabel("<html>Produced by: <b>@pausanchezv</b></html>", Color.GRAY, 4 ,310, 1, 1, 12);
        south.add(author);
        
        return container;
        
    }
    
    /**
     * 
     * Inicialitza el VisorUB4.
     * Aqui es crea el frame principal i li afegeixo el contenidor de les zones a dins.
     * Creo el menú principal i faig aparèixer la pantalla de benvinguda.
     */
    public void initVisorUB4() { 
        JFrame frame = new JFrame("VisorUB4");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(createMainPanel());
        frame.setSize(950, 660);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        
        topMenu = new TopBarMenu(ctrl, this);
        frame.setJMenuBar(topMenu.createMenuBar());
        
        getDisplayWelcome();
    }
    
}
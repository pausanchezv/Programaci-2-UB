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
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Display que representa la pantalla que es mostra per canviar una portada.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayChangeFront extends Display{
    
    public static final String displayTitle = "Canviar portada de: ";
    public final Settings settings;
    
    /**
     * 
     * Constructor de la classe.
     * @param contentPane és el panell bàsic de l'aplicació
     * @param north és el panell nord
     * @param center és el panell centre del visor
     * @param east és el panell est del visor
     * @param ctrl obj del controlador
     * @param settings és l'obj que controla les zones del visor
     */
    public DisplayChangeFront(JPanel contentPane, JPanel north, JPanel center, JPanel east, CtrlVisor ctrl, Settings settings){
        this.ctrl = ctrl;
        this.settings = settings;
        this.func = new Functions(ctrl, settings);
        this.contentPane = contentPane;
        this.center = center;
        this.north = north;
        this.east = east;
        this.title = func.getLabel("", Color.WHITE, 9, 10, 10, 10, 24); 

    }
    
    /**
     * 
     * Crea totes les zones de la pantalla Biblioteca.
     * @param album és el numero de l'àlbum
     */
    public void createZone(int album){
        getNorthZone(displayTitle  + ctrl.getAlbum(album).getTitle());
        getCenterZone(album);
        getEastZone(album);
    }
    
    /**
     * 
     * Crea la zona central de la pantalla Biblioteca.
     * @param album és la posició de l'àlbum
     */
    public void getCenterZone(int album){
        super.clearCenterZone();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        getBoxes(center, album);
    }
    
    /**
     * 
     * Crea els BOX per les imatges
     * @param panel és el panell de la zona central
     * @param album és la posició de l'àlbum
     */
    public void getBoxes(JPanel panel, int album){
        JPanel box;
        int size = ctrl.getAlbum(album).getSize();
        
        for (int i = 0; i < size; i++) {
            box = new JPanel(new BorderLayout());
            box = configBoxes(box, (Imatge) ctrl.getAlbum(album).getAt(i), i, album);
            panel.add(box);
            super.balanceSpace(i, size, panel);
            if (i < size - 1) addSeparator(panel);
        }
    }
    
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és cadascún dels box (caixes d'imatge)
     * @param img és la imatge
     * @param pos és la posició de la imatge dins l'array
     * @param albm és la posició de l'àlbum.
     * @return un BOX per afegir al scrollPane
     */
    public JPanel configBoxes(JPanel box, Object img, final int pos, final int albm){
        
        final Imatge obj = (Imatge)img;
        final AlbumImatges album = ctrl.getAlbum(albm);
        
        box.setPreferredSize(new Dimension(230,160));
        box.setVisible(true);
        
        JPanel middle = new JPanel();       
        JPanel left = new JPanel();
        
        box.add(middle, BorderLayout.CENTER);
        box.add(left, BorderLayout.WEST);
        
        
        /**
         * Zona oest (BOX)
         */
        left.setPreferredSize(new Dimension(210, 180)); // caixa imatge
        ImageIcon imgIcon= new ImageIcon(obj.getAbsolutePath());
        Image scaledImage = imgIcon.getImage().getScaledInstance(210, 180, Image.SCALE_SMOOTH);
        ImageIcon imageResized = new ImageIcon(scaledImage);
        JLabel image = new JLabel(imageResized);
        left.setLayout(new CardLayout());
        left.setBorder(BorderFactory.createMatteBorder(5, 5,5,5, Color.WHITE));
        left.add(image);
        box.setBackground(Color.WHITE);
        left.setBackground(Color.WHITE);
        
        
        /**
         * El centre serà una matriu dividida en nord i sud
         */
        GridLayout horizontalGrid = new GridLayout(2,1);
        
        /**
         * Zona nord (BOX grid)
         */
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(15, 10, 10, 10));
        
        JLabel name = func.getLabel("Títol: " + obj.getImageName(), Color.BLACK, 10, 0, 0, 0, 15);
        JLabel url = func.getLabel("URL: " + obj.getFullPath(), Color.GRAY, 0, 0, 0, 0, 14);
        
        name.setBorder(new EmptyBorder(0, 0, 6, 0));
        top.add(name);
        top.add(url);
        
        /**
         * Zona sud (BOX grid)
         */
        GridLayout bottomLayout = new GridLayout(1,1);
 
        JPanel bottom = new JPanel(bottomLayout);
        bottom.setBorder(new EmptyBorder(15, 15, 15, 15));
        bottomLayout.setHgap(15); 

        GradientButton adding = new GradientButton("Nova portada per l'àlbum : " + album.getTitle(), Color.YELLOW, Color.ORANGE, Color.BLACK);
        
        adding.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {          
                ctrl.editFront(album, pos);
                settings.getDisplayAlbums();
            }
        });
        
        bottom.add(adding);

        middle.setLayout(horizontalGrid);
        middle.add(top);
        middle.add(bottom);

        return box;
    }
    
    /**
     * 
     * Crea la zona est. Adaptada a cada display (pantalla).
     * @param album és el numero d'album
     */
    public void getEastZone(final int album){
        super.clearEastZone();
        GridLayout verticalGrid = new GridLayout(2, 1);
        east.setLayout(verticalGrid);
        east.setBorder(BorderFactory.createEmptyBorder(0,10,5,10));
        
        verticalGrid.setHgap(20);
        verticalGrid.setVgap(20);
        
        JPanel top = new JPanel();
        JPanel bottom = new JPanel();
        
        top.setBackground(Color.LIGHT_GRAY);
        bottom.setBackground(Color.LIGHT_GRAY);
        
        GridLayout horizontalGrid = new GridLayout(4, 1);
        GridLayout horizontalMinGrid = new GridLayout(2, 1);
        
        horizontalGrid.setHgap(20);
        horizontalGrid.setVgap(20);
        
        top.setLayout(horizontalGrid);
        
        GradientButton back = new GradientButton("Tornar enrere", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton showLibrary = new GradientButton("Visualitzar tot", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton removeLibrary = new GradientButton("Canviar portada", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton manageAlbums = new GradientButton("Canviar títol", Color.GRAY, Color.BLACK, Color.WHITE);
        
        back.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAlbum(album);
            }
        });
        
        top.add(back);
        top.add(showLibrary);
        top.add(removeLibrary);
        top.add(manageAlbums);
        
        showLibrary.setVisible(false);
        removeLibrary.setVisible(false);
        manageAlbums.setVisible(false);
        
        bottom.setLayout(horizontalMinGrid);
        
        JPanel forLang = new JPanel();
        forLang.setBackground(Color.LIGHT_GRAY);
        forLang.setLayout(new BoxLayout(forLang, Y_AXIS));
        forLang.setBorder(BorderFactory.createEmptyBorder(40, 0, 55, 0));
        
        JPanel end = new JPanel();
        GridLayout endGrid = new GridLayout(2, 1);
        end.setBackground(Color.LIGHT_GRAY);
        end.setLayout(endGrid);
        endGrid.setVgap(20);
        
        GradientButton toUrl = new GradientButton("A Biblioteca...", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
        GradientButton exit = new GradientButton("Sortir", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
        
        toUrl.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {          
                settings.getDisplayLibrary();
            }
        });
        
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                func.exit();
            }
        });
              
        end.add(toUrl);
        end.add(exit);
        
        JPanel alg = new JPanel();
        alg.setBackground(Color.LIGHT_GRAY);
        forLang.add(alg);
        bottom.add(forLang);
        bottom.add(end);

        east.add(top);
        east.add(bottom);
        
    }

    
}
    

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;

import edu.ub.prog2.SanchezValdiviesoPau.accessoris.GradientButton;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.SanchezValdiviesoPau.model.AlbumImatges;
import edu.ub.prog2.utils.VisorException;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Display que representa la pantalla que es mostra quan estem a la llista d'àlbums.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayAlbums extends Display{
    
    private static final String displayTitle = "Gestiona tots els àlbums";
    private final Settings settings;
    
    /**
     * 
     * Constructor.
     * @param contentPane és el panell bàsic de l'aplicació
     * @param north zona nord del display
     * @param center zona centre del display
     * @param east zona est del display
     * @param settings és l'obj que controla les zones del visor.
     * @center middle és el panell que volem tractar i disenyar
     * @param ctrl obj del controlador
     */
    
    public DisplayAlbums(JPanel contentPane, JPanel north, JPanel center, JPanel east, CtrlVisor ctrl, Settings settings){
        this.ctrl = ctrl;
        this.func = new Functions(ctrl);
        this.contentPane = contentPane;
        this.center = center;
        this.north = north;
        this.title = func.getLabel("", Color.WHITE, 9, 10, 10, 10, 24);
        this.settings = settings;
        this.east = east;
    }
    
    /**
     * 
     * Crea una nova zona (pantalla).
     */
    public void createZone(){
        stopVisualize();
        getNorthZone(displayTitle);
        getCenterZone();
        getEastZone();
    }
    
    /**
     * 
     * Estableix una nova zona central i la prepara per un nou ús.
     */
    public void getCenterZone(){
        super.clearCenterZone();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        getBoxes(center); 
    }
    
    /**
     * 
     * Crea els BOX per les imatges
     * @param panel és el panell de la zona central
     */
    public void getBoxes(JPanel panel){
        ArrayList array = ctrl.getAllAlbums();
        if(array.isEmpty()){
            createEmptyZone(panel);
        }else{
            JPanel box;
            int size = array.size();

            for (int i = 0; i < size; i++) {
                box = new JPanel(new BorderLayout());
                box = configBoxes(box, array.get(i), i);
                panel.add(box);
                super.balanceSpace(i, size, panel);
                if (i < size - 1) addSeparator(panel); 
            }
        }
    }
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és la caixa on va cada àlbum.
     * @param album és l'àlbum d'imatges
     * @return 
     */
    public JPanel configBoxes(JPanel box, Object album, final int pos){
        
        final AlbumImatges obj = (AlbumImatges)album;
        
        box.setPreferredSize(new Dimension(230,160));
        box.setVisible(true);
        
        JPanel middle = new JPanel();       
        JPanel left = new JPanel();
        
        box.add(middle, BorderLayout.CENTER);
        box.add(left, BorderLayout.WEST);
        
        
        /**
         * Zona oest (BOX)
         */
        JLabel image = null;
        ImageIcon imgIcon = null;
        
        if(obj.getFront() == null){
            imgIcon= new ImageIcon("src/default_images/empty.png");
        }else{
            imgIcon= new ImageIcon(obj.getFront().getAbsolutePath());
        }
        
        Image scaledImage = imgIcon.getImage().getScaledInstance(210, 180, Image.SCALE_SMOOTH);
        ImageIcon imageResized = new ImageIcon(scaledImage);
        image = new JLabel(imageResized);
        
        left.setPreferredSize(new Dimension(210, 180)); // caixa imatge
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
         * Zona nord
         */
        JPanel top = new JPanel();
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        top.setBorder(new EmptyBorder(15, 10, 10, 10));
        
        JLabel name = func.getLabel("Títol: " + obj.getTitle(), Color.BLACK, 10, 0, 0, 0, 15);
        JLabel url = func.getLabel("Autor: "  + obj.getAuthor() , Color.GRAY, 0, 0, 0, 0, 14);
        
        name.setBorder(new EmptyBorder(0, 0, 6, 0));
        top.add(name);
        top.add(url);
        
        /**
         * Zona sud
         */
        GridLayout bottomLayout = new GridLayout(1,2);
 
        JPanel bottom = new JPanel(bottomLayout);
        bottom.setBorder(new EmptyBorder(15, 15, 15, 15));
        bottomLayout.setHgap(15); 
        
        JButton browse = new JButton("Gestionar l'àlbum");
        browse.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               settings.getDisplayAlbum(pos);
            }
        });
        bottom.add(browse);
        JButton show = new JButton("Visualitzar (On Timer)");
        show.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
               func.slideImages(ctrl.getAlbum(pos));
               
            }
        });
        bottom.add(show);
        middle.setLayout(horizontalGrid);
        middle.add(top);
        middle.add(bottom);

        return box;
    }
    
    /**
     * 
     * Crea la zona est. Adaptada a cada display (pantalla).
     */
    public void getEastZone(){
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
        
        GradientButton addAlbum = new GradientButton("Afegir àlbum", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton showLibrary = new GradientButton("Visualitzar tot", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton removeLibrary = new GradientButton("Esborra-ho TOT", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton manageAlbums = new GradientButton("Gestionar àlbums", Color.GRAY, Color.BLACK, Color.WHITE);
        
        addAlbum.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAddAlbum();
            }
        });
        
        removeLibrary.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                func.removeAll();
            }
        });
        
        top.add(addAlbum);
        top.add(removeLibrary);
        top.add(showLibrary);
        top.add(manageAlbums);
        
        showLibrary.setVisible(false);
        manageAlbums.setVisible(false);
        removeLibrary.setVisible(false);
        
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
    
    
    /**
     * 
     * Crea àlbums (cas de quan no n'hi ha cap).
     * @param panel és el panell central
     */
    private void createEmptyZone(JPanel panel){
        panel.setLayout(new GridLayout(3,1));
        JLabel label = func.getLabel("<html><b>No hi ha cap àlbum!</b></html>", Color.GRAY, 0, 50, 0, 0, 35);
        GradientButton button = new GradientButton("Afegir un àlbum", Color.CYAN, Color.BLUE, Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAddAlbum();
            }
        });
        button.setPreferredSize(new Dimension(400, 100));
        panel.add(label);
        panel.add(new JPanel());
        panel.add(button);
    }
    
    /**
     * 
     * Atura la visualització.
     */
    @Override
    public void stopVisualize(){
        if(ctrl.getVisualizeActive()){
            try {
                super.stopVisualize();
            } catch (VisorException ex) {
                Logger.getLogger(DisplayAddImage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
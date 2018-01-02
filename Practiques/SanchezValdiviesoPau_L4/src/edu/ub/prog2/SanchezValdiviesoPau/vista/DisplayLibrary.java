/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;

import edu.ub.prog2.SanchezValdiviesoPau.accessoris.GradientButton;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.SanchezValdiviesoPau.model.Imatge;
import edu.ub.prog2.SanchezValdiviesoPau.model.LlistaImatges;
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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Display que representa la pantalla que estem a la biblioteca.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayLibrary extends Display{
    
    private static final String displayTitle = "Gestió de la Biblioteca";
    private final Settings settings;
    private final JScrollPane scrollPane;
    
    /**
     * 
     * Constructor de la classe.
     * @param contentPane és el panell bàsic de l'aplicació
     * @param scrollPane és l'scrooll pricipal.
     * @param north és el panell nord
     * @param center és el panell centre del visor
     * @param east és el panell est del visor
     * @param ctrl obj del controlador
     * @param settings és l'ob que controla les zones del visor.
     */
    
    public DisplayLibrary(JPanel contentPane, JScrollPane scrollPane, JPanel north, JPanel center, JPanel east, CtrlVisor ctrl, Settings settings){
        this.ctrl = ctrl;
        this.settings = settings;
        this.func = new Functions(ctrl, settings);
        this.contentPane = contentPane;
        this.center = center;
        this.north = north;
        this.east = east;
        this.title = func.getLabel("", Color.WHITE, 9, 10, 10, 10, 24);   
        this.scrollPane = scrollPane;
    }
    
    /**
     * 
     * Crea totes les zones de la pantalla Biblioteca.
     */
    public void createZone(){
        stopVisualize();
        getNorthZone(displayTitle);
        getCenterZone();
        getEastZone();
    }
    
    /**
     * 
     * Crea la zona central de la pantalla Biblioteca.
     */
    public void getCenterZone(){
        super.clearCenterZone();
        center.setPreferredSize(null);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        getBoxes(center);
    }
    
    /**
     * 
     * Crea els BOX per les imatges
     * @param panel és el panell de la zona central
     */
    public void getBoxes(JPanel panel){
        LlistaImatges array = ctrl.getLibrary();
        if(array.isEmpty()){
            createEmptyZone(panel);
            
        }else{
            JPanel box;
            int size = array.getSize();

            for (int i = 0; i < size; i++) {
                box = new JPanel(new BorderLayout());
                box = configBoxes(box, (Imatge) array.getAt(i), i);
                panel.add(box);
                super.balanceSpace(i, size, panel);
                if (i < size - 1) addSeparator(panel);
            }
        }
    }
    
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és cadascún dels box (caixes d'imatge)
     * @param img és la imatge
     * @param pos és la posició de la imatge dins l'array
     * @return un BOX per afegir al scrollPane
     */
    public JPanel configBoxes(JPanel box, Object img, final int pos){
        
        final Imatge obj = (Imatge)img;
        final LlistaImatges library = ctrl.getLibrary();
        
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
        JLabel url = func.getLabel("URL: " + obj.getPath(), Color.GRAY, 0, 0, 0, 0, 14);
        
        name.setBorder(new EmptyBorder(0, 0, 6, 0));
        top.add(name);
        top.add(url);
        
        /**
         * Zona sud (BOX grid)
         */
        GridLayout bottomLayout = new GridLayout(1,2);
 
        JPanel bottom = new JPanel(bottomLayout);
        bottom.setBorder(new EmptyBorder(15, 15, 15, 15));
        bottomLayout.setHgap(15); 
        
        JButton view = new JButton("Visualitzar / Transformar");
        JButton options = new JButton("Eliminar imatge");
        
        view.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {          
                func.showDialogImageOptions(obj, -1, pos);
            }
        });
        
        options.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                func.removeImage(obj);
                createZone();
            }
        });
        
        
        bottom.add(view);
        bottom.add(options);

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
        
        GradientButton addImage = new GradientButton("Afegir imatge", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton showLibrary = new GradientButton("Visualitzar tot", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton removeLibrary = new GradientButton("Esborra-ho TOT", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton manageAlbums = new GradientButton("Gestionar àlbums", Color.GRAY, Color.BLACK, Color.WHITE);
        
        addImage.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAddImage();
            }
        });
        showLibrary.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                func.slideImages(ctrl.getLibrary());
            }
        });
        removeLibrary.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                func.removeAll();
            }
        });
        manageAlbums.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAlbums();
            }
        });
        
        top.add(addImage);
        top.add(removeLibrary);
        top.add(manageAlbums);
        top.add(showLibrary);
        
        if(ctrl.getLibrary().isEmpty()){
            removeLibrary.setVisible(false);
            manageAlbums.setVisible(false);
            showLibrary.setVisible(false);
        }
        
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
        
        GradientButton toUrl = new GradientButton("Inici", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
        GradientButton exit = new GradientButton("Sortir", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
        
        exit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                func.exit();
            }
        });
              
        end.add(toUrl);
        end.add(exit);
        toUrl.setVisible(false);
        
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
     * Crea la biblioteca (cas de quan està buida).
     * @param panel és el panell central
     */
    private void createEmptyZone(JPanel panel){
        panel.setLayout(new GridLayout(3,1));
        JLabel label = func.getLabel("<html><b>La Biblioteca està buida!</b></html>", Color.GRAY, 0, 50, 0, 0, 35);
        GradientButton button = new GradientButton("Afegir una imatge", Color.CYAN, Color.BLUE, Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAddImage();
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
    

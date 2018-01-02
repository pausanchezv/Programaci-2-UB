/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;

import edu.ub.prog2.SanchezValdiviesoPau.accessoris.GradientButton;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * Display que representa la pantalla de benvinguda.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayWelcome extends Display{
    
    private static final String displayTitle = "Benvingut al VisorUB4";
    private final Settings settings;

    /**
     * 
     * Constructor
     * @param contentPane és el panell bàsic de l'aplicació
     * @param north és la zona nord de la pantalla
     * @param center és la zona centre de la pantalla
     * @param east és la zona est de la pantalla
     * @param settings és l'obj que controla el visor
     * @center middle és el panell que volem tractar i disenyar
     * @param ctrl obj del controlador
     */
    public DisplayWelcome(JPanel contentPane, JPanel north, JPanel center, JPanel east, CtrlVisor ctrl, Settings settings){
        this.ctrl = ctrl;
        this.func = new Functions(ctrl);
        this.settings = settings;
        this.contentPane = contentPane;
        this.center = center;
        this.north = north;
        this.east = east;
        this.title = func.getLabel("", Color.WHITE, 9, 10, 10, 10, 24);  
    }
    
    /**
     * 
     * Crea la pantalla d'inici.
     */
    public void createZone(){
        super.clearCenterZone();
        getNorthZone(displayTitle);
        getCenterZone();
        getEastZone();
    }
    
    /**
     * 
     * Crea la zona central.
     */
    public void getCenterZone(){
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));
        getBoxes(center);
        JScrollPane scrollPane = new JScrollPane(center);
        scrollPane.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.LIGHT_GRAY));
        contentPane.add(scrollPane);
    }
    
    /**
     * 
     * Crea els BOX per les imatges
     * @param panel és el panell de la zona central
     */
    public void getBoxes(JPanel panel){
        JPanel box;
        box = new JPanel();
        box = configBoxes(box, new BorderLayout(), Integer.MIN_VALUE);
        panel.add(box);    
    }
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és la caixa de la zona de benvinguda
     * @param layout és el tipus de layout
     * @param noMandatory aquí no necessitem cap índex
     * @return 
     */
    public JPanel configBoxes(JPanel box, Object layout, int noMandatory){
        box.setLayout((BorderLayout)layout);
        box.setPreferredSize(new Dimension(0,0));
        
        JPanel pala = new JPanel();
        JLabel res = func.getLabel("<html><b>Visor - UB4</b></html>", Color.WHITE, 30, 75, 75, 30, 60);
        pala.setBackground(new Color(255,255,255,100));
        pala.setBorder(BorderFactory.createEmptyBorder(20, 20,20,20));
        pala.add(res);
        
        final GradientButton button = new GradientButton("Carregar el visor amb imatges i àlbums d'exemple", new Color(255, 39, 91), new Color(255, 39, 41), Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(20, 40,20,40));
        button.setFont(new Font("Arial", Font.BOLD, 17));
        button.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) { 
                button.setText("Carregant dades ...");
                func.loadDataExample();
                settings.getDisplayLibrary();
            }
        });
       
        JLabel background = new JLabel(new ImageIcon("src/default_images/init.jpg"));
        box.add(background);
        background.setLayout(new FlowLayout());

        JPanel trans = new JPanel();
        trans.setBackground(new Color(0,0,0,100));
        trans.setPreferredSize(new Dimension(center.getWidth() + 10,center.getHeight() + 40));
        trans.setBorder(BorderFactory.createEmptyBorder(-10, 1, 1, 1));
        
        JPanel separ = new JPanel();
        separ.setPreferredSize(new Dimension(600,70));
        JPanel separBottom = new JPanel();
        separBottom.setPreferredSize(new Dimension(600,70));
        separ.setBackground(new Color(0,0,0,0));
        separBottom.setBackground(new Color(0,0,0,0));
        trans.add(separ);
        trans.add(pala);
        trans.add(separBottom);
        trans.add(button);
        
        background.add(trans);
        
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
        
        GradientButton toLibrary = new GradientButton("Biblioteca", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton toAlbums = new GradientButton("Gestió d'àlbums", Color.GRAY, Color.BLACK, Color.WHITE);
        final GradientButton trainingVisor = new GradientButton("Visor de proves", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton loadData = new GradientButton("Carregar dades", Color.GRAY, Color.BLACK, Color.WHITE);
        
        toLibrary.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayLibrary();
            }
        });
        toAlbums.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAlbums();
            }
        });
        trainingVisor.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {   
                trainingVisor.setText("Carregant ...");
                func.loadDataExample();
                settings.getDisplayLibrary();
            }
        });
        loadData.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                if(func.loadData()) settings.getDisplayLibrary();
            }
        });
        
        top.add(toLibrary);
        top.add(toAlbums);
        top.add(trainingVisor);
        top.add(loadData);
        
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
        
        GradientButton toUrl = new GradientButton("A Biblioteca", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
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

}
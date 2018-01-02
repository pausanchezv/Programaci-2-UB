/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;

import edu.ub.prog2.SanchezValdiviesoPau.accessoris.GradientButton;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.utils.VisorException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import static javax.swing.BoxLayout.Y_AXIS;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 * 
 * Display que representa la pantalla que es mostra per afegir un àlbum.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayAddAlbum extends Display{
    
    public static final String displayTitle = "Afegint un àlbum";
    private static final String errorName = "Error! Els 2 camps són obligatoris!!";
    
    private final Settings settings;
    private final JScrollPane scrollPane;
    
    /**
     * 
     * Constructor de la classe.
     * @param contentPane és el panell bàsic de l'aplicació
     * @param scrollPane és l'scroll principal del visor.
     * @param north és el panell nord
     * @param center és el panell centre del visor
     * @param east és el panell est del visor
     * @param ctrl obj del controlador
     * @param settings és l'obj que controla les dades del visor.
     */
    public DisplayAddAlbum(JPanel contentPane, JScrollPane scrollPane, JPanel north, JPanel east, JPanel center, CtrlVisor ctrl, Settings settings){
        this.ctrl = ctrl;
        this.func = new Functions(ctrl);
        this.contentPane = contentPane;
        this.scrollPane = scrollPane;
        this.center = center;
        this.north = north;
        this.east = east;
        this.title = func.getLabel("", Color.WHITE, 9, 10, 10, 10, 24);  
        
        this.settings = settings;
    }
    
    /**
     * 
     * Crea una nova zona (pantalla) a petició de l'usuari.
     */
    public void createZone(){
        stopVisualize();
        getNorthZone(displayTitle);
        getCenterZone();
        getEastZone();
    }
    
    /**
     * 
     * Estableix una nova zona central a petició de l'usuari.
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
        JPanel box;
        box = new JPanel();
        box = configBoxes(box, new GridLayout(5,1), Integer.MIN_VALUE);
        panel.add(box);
    }
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és el box (caixa de la imatge)
     * @param layout és el tipus de layout
     * @param noMandatory aqui no necessitem cap índex.
     * @return 
     */
    public JPanel configBoxes(JPanel box, Object layout, int noMandatory){
        
        box.setLayout((GridLayout) layout);
        box.setPreferredSize(new Dimension(scrollPane.getWidth(),scrollPane.getHeight()));
        box.setVisible(true);
        box.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        box.setBorder(new EmptyBorder(40,50,40,50));
        
        JPanel top = new JPanel(new FlowLayout());
        top.setLayout(new BoxLayout(top, BoxLayout.Y_AXIS));
        JLabel labelTop = func.getLabel("Nom de l'àlbum" , Color.GRAY, 10, 0, 20, 0, 18);
        labelTop.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        
        final JTextField textFieldTop = new JTextField();
        textFieldTop.setPreferredSize(new Dimension(300, 50));
        textFieldTop.setFont(new Font("SansSerif", Font.BOLD, 17));
        textFieldTop.setBorder(BorderFactory.createEmptyBorder(1,15,1,1));
        textFieldTop.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        
        final JLabel errorFields = func.getLabel("" , Color.RED, 0,0,0,0, 17);
        errorFields.setVisible(false);
        
        JPanel bottom = new JPanel(new FlowLayout());
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));
        JLabel labelBottom = func.getLabel("Nom de l'autor" , Color.GRAY, 10, 0, 20, 0, 18);
        labelBottom.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        
        final JTextField textFieldBottom = new JTextField();
        textFieldBottom.setPreferredSize(new Dimension(300, 50));
        textFieldBottom.setFont(new Font("SansSerif", Font.BOLD, 17));
        textFieldBottom.setBorder(BorderFactory.createEmptyBorder(1,15,1,1));
        textFieldBottom.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        
        labelTop.setLabelFor(textFieldTop);
        top.add(labelTop);
        top.add(textFieldTop);
        
        labelBottom.setLabelFor(textFieldBottom);
        bottom.add(labelBottom);
        bottom.add(textFieldBottom);
        
        GridLayout buttonsLayout = new GridLayout(1, 2);
        JPanel buttons = new JPanel(buttonsLayout);
        buttonsLayout.setHgap(20);
        buttonsLayout.setVgap(50);
        JButton clean = new JButton("Esborra els camps");
        JButton add = new JButton("Afegeix l'àlbum");
        buttons.add(clean);
        buttons.add(add);
             
        box.add(top);
        box.add(bottom);
        box.add(errorFields);      
        box.add(new JPanel());
        box.add(buttons);
        
        clean.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                cleanFields(textFieldTop, textFieldBottom, errorFields);
            }
        });
        
        add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 addAlbum(textFieldTop, textFieldBottom, errorFields);
            }
        });
        
        return box;
    }
    
    /**
     * 
     * Afegeix un àlbum al visor.
     * @param textFieldTop és el camp de text superior.
     * @param textFieldBottom és el camp de text inferior
     * @param errorFields és el jLabel que mostra l'error, si escau
     */
    private void addAlbum(JTextField textFieldTop, JTextField textFieldBottom, JLabel errorFields){
        boolean isAdded;
        JTextField [] array = {textFieldTop, textFieldBottom};
        boolean error = super.comproveFields(array);
        
        if(error){
            if(textFieldTop.getText().equals("") || textFieldBottom.getText().equals("")){
                errorFields.setText(errorName);
            }
            errorFields.setVisible(true);
        }else{
            errorFields.setVisible(false);
            isAdded = func.addAlbum(textFieldTop.getText(), textFieldBottom.getText());
            if(isAdded){
                settings.getDisplayAlbums();
            } else{
                func.getMessage("Error !!", "Ja existeix un àlbum amb el mateix nom!!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * 
     * @param textFieldTop
     * @param textFieldBottom 
     */
    private void cleanFields(JTextField textFieldTop, JTextField textFieldBottom, JLabel errorFields){
        textFieldTop.setText("");
        textFieldBottom.setText("");
        errorFields.setVisible(false);
        textFieldTop.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        textFieldBottom.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
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
        
        GradientButton cancel = new GradientButton("Cancel·lar", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton showLibrary = new GradientButton("Visualitzar tot", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton removeLibrary = new GradientButton("Esborra-ho TOT", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton manageAlbums = new GradientButton("Gestionar àlbums", Color.GRAY, Color.BLACK, Color.WHITE);
        
        cancel.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                settings.getDisplayAlbums();
            }
        });
        
        top.add(cancel);
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
        
        GradientButton toUrl = new GradientButton("Àlbums...", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
        GradientButton exit = new GradientButton("Sortir", Color.LIGHT_GRAY, Color.GRAY, Color.BLACK);
        
        toUrl.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {          
                settings.getDisplayAlbums();
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
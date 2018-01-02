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
 * Display que representa la pantalla que es mostra per afegir una imatge a biblioteca.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayAddImage extends Display{
    
    public static final String displayTitle = "Afegint una imatge a biblioteca";
    
    private final Settings settings;
    private final JScrollPane scrollPane;
    
    /**
     * 
     * Constructor de la classe.
     * @param contentPane és el panell bàsic de l'aplicació
     * @param scrollPane és l'ScrollBar del panell central.
     * @param north és el panell nord
     * @param east és la zona est del visor.
     * @param center és el panell centre del visor
     * @param settings és un obj per cirdar mètodes de creació de zones.
     * @param ctrl obj del controlador
     */
    public DisplayAddImage(JPanel contentPane, JScrollPane scrollPane, JPanel north, JPanel east, JPanel center, CtrlVisor ctrl, Settings settings){
        super.ctrl = ctrl;
        this.func = new Functions(ctrl);
        this.contentPane = contentPane;
        this.scrollPane = scrollPane;
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
        JPanel box;
        box = new JPanel();
        box = configBoxes(box, new GridLayout(5,1), Integer.MIN_VALUE);
        panel.add(box);
    }
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és el panell per cada box.
     * @param layout és el tipus d'envoltori per panell.
     * @param noMandatory aquí no necessitem cap índex.
     * @return una unitat de box.
     */
    public JPanel configBoxes(JPanel box, Object layout, int noMandatory){
        
        box.setLayout((GridLayout) layout);
        box.setPreferredSize(new Dimension(scrollPane.getWidth(),scrollPane.getHeight()));
        box.setVisible(true);
        box.setSize(scrollPane.getWidth(), scrollPane.getHeight());
        box.setBorder(new EmptyBorder(50,50,50,50));

        JPanel flower = new JPanel(new FlowLayout());
        flower.setLayout(new BoxLayout(flower, BoxLayout.Y_AXIS));
        JLabel label = func.getLabel("Nom de la imatge" , Color.GRAY, 10, 0, 20, 0, 18);
        label.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        
        final JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(300, 50));
        textField.setFont(new Font("SansSerif", Font.BOLD, 17));
        textField.setBorder(BorderFactory.createEmptyBorder(1,15,1,1));
        textField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
        
        label.setLabelFor(textField);
        flower.add(label);
    
        flower.add(textField);
        box.add(flower);
        
        JPanel separator = new JPanel();
        separator.setPreferredSize(new Dimension(20, 10));
        box.add(separator); 
        
        JPanel panelMedium = new JPanel();
        panelMedium.setLayout(new BoxLayout(panelMedium, BoxLayout.Y_AXIS));
        
        final JTextField textFieldReadOnly = new JTextField();
        textFieldReadOnly.setPreferredSize(new Dimension(300, 50));
        textFieldReadOnly.setFont(new Font("SansSerif", Font.BOLD, 17));
        textFieldReadOnly.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
        textFieldReadOnly.setEditable(false);
        textFieldReadOnly.setBackground(Color.WHITE);

        panelMedium.add(textFieldReadOnly);
        box.add(panelMedium);
        
        panelMedium.add(new JPanel());
        
        GridLayout buttonsLayout = new GridLayout(1, 2);
        JPanel buttons = new JPanel(buttonsLayout);
        
        buttonsLayout.setHgap(20);
        buttonsLayout.setVgap(50);
        
        JButton clean = new JButton("Esborra-ho tot");
        JButton select = new JButton("Selecciona un fitxer");
        JButton add = new JButton("Acceptar i afegir la imatge");
        add.setBackground(Color.BLUE);
        add.setForeground(Color.WHITE);
        
        buttons.add(clean);
        buttons.add(select);

        GridLayout addLayout = new GridLayout(2, 1);
        JPanel buttonAdd = new JPanel(addLayout);
        
        addLayout.setHgap(0);
        addLayout.setVgap(0);
        
        buttonAdd.add(new JPanel());
        buttonAdd.add(add);
        
        select.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                selectImage(textField, textFieldReadOnly);
            }
        });
        
        clean.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                 cleanNameField(textField);
                 cleanPathField(textFieldReadOnly);
            }
        });
        
        add.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                addImage(textField, textFieldReadOnly);
            }
        });
        
        box.add(buttons);
        box.add(buttonAdd);

        return box;
    }
    
    /**
     * 
     * Afegeix una imatge a la biblioteca fent les comprovacions prèvies.
     * @param textField és ecamp nom.
     * @param textFieldReadOnly és el camp path.
     */
    private void addImage(JTextField textField, JTextField textFieldReadOnly){
        boolean isAdded;
        JTextField [] array = {textField, textFieldReadOnly};
        boolean error = super.comproveFields(array);
        if(error && !textFieldReadOnly.getText().equals("") || textField.getText().equals("")){
            textFieldReadOnly.setForeground(Color.RED);
            textFieldReadOnly.setText("Has de posar nom i després seleccionar un fitxer !!");
        }
        else if(!error && !textFieldReadOnly.getForeground().equals(Color.RED)){
            isAdded = func.addImage(textField.getText(), textFieldReadOnly.getText());
            if(isAdded){
                //func.getMessage("Fet !!", "Imatge afegida amb èxit!", JOptionPane.PLAIN_MESSAGE);
                settings.getDisplayLibrary();
            } else{
                func.getMessage("OOOPS !!", "Aquesta imatge ja existeix!!", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    /**
     * 
     * Selecciona una imatge de disc si totes les comprovacions són correctes.
     * @param textField és el camp nom.
     * @param textFieldReadOnly és el camp path.
     */
    private void selectImage(JTextField textField, JTextField textFieldReadOnly){
        cleanPathField(textFieldReadOnly);
        JTextField [] array = {textField};
        boolean error = super.comproveFields(array);
        if(!error){
            textFieldReadOnly.setText("");
            String select = func.selectImage();
            textFieldReadOnly.setText(select);
        }
    }
    
    /**
     * 
     * neteja el camp de tect nom.
     * @param textField és el camp de text
     */
    private void cleanNameField(JTextField textField){
        textField.setText("");
        textField.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));
    }
    
    /**
     * 
     * Neteja el camp de text del path.
     * @param textFieldReadOnly és el camp de text
     */
    private void cleanPathField(JTextField textFieldReadOnly){
        textFieldReadOnly.setText("");
        textFieldReadOnly.setForeground(Color.LIGHT_GRAY);
        textFieldReadOnly.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
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
                settings.getDisplayLibrary();
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
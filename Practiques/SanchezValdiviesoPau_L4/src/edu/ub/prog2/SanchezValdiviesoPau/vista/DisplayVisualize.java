/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;

import edu.ub.prog2.SanchezValdiviesoPau.accessoris.GradientButton;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.SanchezValdiviesoPau.model.Imatge;
import edu.ub.prog2.utils.VisorException;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * 
 * Display que representa la pantalla que es mostra per visualitzar amb l'onTimer.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class DisplayVisualize extends Display{
    
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
    public DisplayVisualize(JPanel contentPane, JScrollPane scrollPane, JPanel north, JPanel east, JPanel center, CtrlVisor ctrl, Settings settings){
        this.ctrl = ctrl;
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
     * @param obj és la imatge actual.
     */

    public void createZone(Imatge obj){
        getNorthZone(obj.getImageName());
        getCenterZone(obj);
        getEastZone();
    }       
    
    /**
     * 
     * Estableix una nova zona central i la prepara per un nou ús.
     * @param obj és la imatge actual.
     */

    public void getCenterZone(Imatge obj){
        super.clearCenterZone();
        getBoxes(center, obj);
        center.setPreferredSize(new Dimension(100, 100));
    }
    
    /**
     * 
     * Crea els BOX per les imatges
     * @param panel és el panell de la zona central
     * @param obj és la imatge actual.
     */

    public void getBoxes(JPanel panel, Imatge obj){
        panel = configBoxes(panel, new FlowLayout(), obj);
    }
    
    /**
     * 
     * Configura els espais i continguts d'un box
     * @param box és el panell per cada box.
     * @param layout és el tipus d'envoltori per panell.
     * @param obj és la imatge actual.
     * @return una unitat de box.
     */

    public JPanel configBoxes(JPanel box, Object layout, Imatge obj){
        ImageIcon img = new ImageIcon(obj.getAbsolutePath());
        Image scaledImage = img.getImage().getScaledInstance(center.getWidth(),center.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon visual = new ImageIcon(scaledImage);
        JLabel background = new JLabel(visual);
        
        box.add(background);
        box.repaint();
        background.setLayout(new FlowLayout());
        box.add(background);

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
        
        GradientButton stop = new GradientButton("Stop", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton inr = new GradientButton("+ Velocitat", Color.GRAY, Color.BLACK, Color.WHITE);
        GradientButton dcr = new GradientButton("- Velocitat", Color.GRAY, Color.BLACK, Color.WHITE);
        final GradientButton pause = new GradientButton("Play / Pause", Color.GRAY, Color.BLACK, Color.WHITE);
        
        final JPanel container = new JPanel(new CardLayout());
        
        stop.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                try {
                    ctrl.stop();
                } catch (VisorException ex) {
                    Logger.getLogger(DisplayVisualize.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
     
        
        final ActionListener forPlay = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                ctrl.ready();
                pause.removeActionListener(this);
                pause.addActionListener(null);
            }
        };
        
        
         ActionListener forPause = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                ctrl.pause();
                pause.removeActionListener(this);
                pause.addActionListener(forPlay);
            }
        };
        pause.addActionListener(forPause);
        
        
        inr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                try {
                    ctrl.inrTimer();
                } catch (Exception ex) {
                    Logger.getLogger(DisplayVisualize.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        dcr.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                try {
                    ctrl.dcrTimer();
                } catch (Exception ex) {
                    Logger.getLogger(DisplayVisualize.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
               
        container.add(pause);
        pause.setSize(container.getWidth(), container.getHeight());
        
        top.add(inr);
        top.add(dcr);
        top.add(container);
        top.add(stop);
        
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
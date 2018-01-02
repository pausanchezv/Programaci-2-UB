/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;

import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import edu.ub.prog2.utils.VisorException;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Classe abstracta zona. Cada zona del visor serà filla seva.
 * Aquesta classe no ha d'instanciar cap obj ja que una zona és inútil si no sabem
 * quina zona és.
 */
public abstract class Display {
    
    /**
     * 
     * Variables de control.
     */
    protected Functions func;
    protected CtrlVisor ctrl;
    
    /**
     * 
     * Contenidors de display.
     */
    protected JPanel contentPane;  
    protected JPanel center;
    protected JPanel north;
    protected JPanel east;
    
    /**
     * 
     * Variables de display.
     */
    protected JLabel title;
    
    
    /**
     * 
     * Estableix la zona nord d'acord amb la pantalla en la que estem.
     * @param str és el títol de la pantalla.
     */
    protected void getNorthZone(String str){
        north.removeAll();
        north.add(title);
        title.setText(str);
        title.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 0));
    }
    
    /**
     * 
     * Neteja la zona central per canviar de pantalla.
     */
    public void clearCenterZone(){
        center.setVisible(false);
        center.removeAll();
        center.setVisible(true);
    }
    
    /**
     * 
     * Neteja i prepara la zona est per un nou ús si escau.
     */
    protected void clearEastZone(){
        east.setVisible(false);
        east.removeAll();
        east.setVisible(true);
    }
    
    /**
     * 
     * Crea un separador de boxes.
     * @param panel és el panell central
     */
    protected void addSeparator(JPanel panel){
        JPanel separator = new JPanel();
        separator.setBackground(Color.LIGHT_GRAY);
        separator.setPreferredSize(new Dimension(20, 20));
        panel.add(separator); 
    }
    
    /**
     * 
     * @param array és l'array d'obj JTextField
     * @return true si hi ha error, false en cas contrari
     */
    protected boolean comproveFields(JTextField [] array){
        boolean error = false;
        for(JTextField obj : array){
            if(obj.getText().equals("")){
                error = error || true;
                obj.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.RED));
            }else{
                error = error || false;
                obj.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.LIGHT_GRAY));              
            }
        }
        return error;
    }
    
    /**
     * 
     * omple els espais buits fins que hi hagi almenys 3 imatges.
     * @param i és el número d'iteració.
     * @param size és el tamany actual.
     * @param panel és el panell sobre el que actuem.
     */
    protected void balanceSpace(int i, int size, JPanel panel){
        if(size == 1){
            for (int s = 0; s < 22; s++){
                addSeparator(panel);
            }
        }
        if(size == 2 && i == 1){
            for (int s = 0; s < 11; s++){
                addSeparator(panel);
            }
        }
    }
    
    /**
     * 
     * Stop visualize.
     * @throws edu.ub.prog2.utils.VisorException
     */
    public void stopVisualize() throws VisorException{
        ctrl.stop();
    }

}

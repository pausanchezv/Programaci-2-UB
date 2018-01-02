/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.accessoris;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import javax.swing.JButton;

/**
 *
 * Aquesta classe crea un estil pels colors amb degradat pels jButtons 
 * amb el nostre estil personalitzat.
 */
public final class GradientButton extends JButton{
        
        /**
         * 
         * Colors de dalt, de baix i de text
         */
        private final Color colorTop;
        private final Color colorBottom;
        
        /**
         * 
         * Costructor.
         * @param str és el text del botó
         * @param colorTop és el color superior del degradat
         * @param colorBottom és el color inferior del degradat
         * @param foreground és el color de la lletra
         */
        public GradientButton(String str, Color colorTop, Color colorBottom, Color foreground){
            super(str);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setForeground(foreground);
            this.colorTop = colorTop;
            this.colorBottom = colorBottom;
        }
        
        /**
         * 
         * Aplica el degradat al botó.
         * @param g és un component gràfic AWT
         */
        @Override
        protected void paintComponent(Graphics g){
            Graphics2D g2 = (Graphics2D)g.create();
            g2.setPaint(new GradientPaint(
                    new Point(0, 0), 
                    colorTop, 
                    new Point(0, getHeight()), 
                    colorBottom.darker()));
            g2.fillRect(0, 0, getWidth(), getHeight());
            g2.dispose();

            super.paintComponent(g);
        }
    }

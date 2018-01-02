/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ub.prog2.SanchezValdiviesoPau.vista;
import edu.ub.prog2.SanchezValdiviesoPau.ctrl.CtrlVisor;
import java.awt.event.*;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
 
/**
 * 
 * Classe que mostra la barra de menú superior.
 * @author - Pau Sanchez Valdivieso - @pausanchezv (Twitter)
 * @version - Visor d'imatges UB, entrega nº4
 */
public final class TopBarMenu{
    
    private final JMenuBar menuBar;
    private JMenu menu;
    private JMenuItem menuItem;
    
    private final CtrlVisor ctrl;
    private final Functions func;
    private final Settings settings;

    
    /**
     * 
     * Constructor
     * @param ctrl obj del controlador
     * @param settings és l'obj que controla les zones del visor.
     */
    public TopBarMenu(CtrlVisor ctrl, Settings settings){
        this.menuBar = new JMenuBar();
        this.ctrl = ctrl;
        this.func = new Functions(ctrl);
        this.settings = settings;
    }

    
    /**
     * 
     * Inicialitza el menú
     * @return la barra superior de menú
     */
    public JMenuBar createMenuBar() {
      
        /**
         * 
         * Menú Inici
         */
        
        menu = new JMenu("Menu");
        menu.addSeparator();
        menu = new JMenu("Arxiu");
        menuBar.add(menu);
        menuItem = new JMenuItem("Guardar", KeyEvent.VK_G);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                func.saveData();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Carregar dades", KeyEvent.VK_R);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                if(func.loadData()) settings.getDisplayLibrary();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Carregar un visor d'exemple", KeyEvent.VK_R);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                func.loadDataExample();
                settings.getDisplayLibrary();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Sortir", KeyEvent.VK_E);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {              
                func.exit();
            }
        });
        menu.add(menuItem);
        
        /**
         * 
         * Menú biblioteca
         */
        
        menu = new JMenu("Gestionar la biblioteca");
        menuBar.add(menu);
        menuItem = new JMenuItem("Afegir una imatge", KeyEvent.VK_A);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getDisplayAddImage();
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Gestionar els continguts", KeyEvent.VK_G);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getDisplayLibrary();
            }
        });
        menu.add(menuItem);
 
        
        /**
         * 
         * Menú àlbums
         */
        
        menu = new JMenu("Gestionar Àlbums");
        menuBar.add(menu);
        menuItem = new JMenuItem("Veure la llista d'àlbums", KeyEvent.VK_L);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
   

                settings.getDisplayAlbums();
  
            }
        });
        menu.add(menuItem);
        menu.addSeparator();
        menuItem = new JMenuItem("Afegir un àlbum", KeyEvent.VK_N);
        menuItem.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                settings.getDisplayAddAlbum();
            }
        });
        menu.add(menuItem);
  
        return menuBar;
    }

    
 
}
 
    



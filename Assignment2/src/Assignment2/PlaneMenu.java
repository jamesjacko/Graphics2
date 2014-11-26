/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author jacko
 */
public class PlaneMenu extends JMenuBar {
    JMenu file;
    JMenuItem open;
    JMenuItem exit;
    public PlaneMenu(){
        super();
        file = new JMenu("File");
        open = new JMenuItem("Open");
        exit = new JMenuItem("Exit");
        
        file.add(open);
        file.add(exit);
        
        add(file);
    }
}

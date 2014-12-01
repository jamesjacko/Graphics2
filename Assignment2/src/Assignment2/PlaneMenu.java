/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private DataSetVisualiser ds;
    public PlaneMenu(final DataSetVisualiser ds){
        super();
        file = new JMenu("File");
        open = new JMenuItem("Open");
        exit = new JMenuItem("Exit");
        this.ds = ds;
        
        file.add(open);
        
        open.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                ChooseFile choose = new ChooseFile();
                ds.loadFile(choose.getFile(), choose.getDimensions());
            }
        });
        file.add(exit);
        
        add(file);
    }
}

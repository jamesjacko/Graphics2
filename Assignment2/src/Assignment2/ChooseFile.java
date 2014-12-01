/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author jacko
 */
public class ChooseFile extends JDialog {
    
    private File file;
    private int sizes[] = new int[3];
    private JTextField size[] = new JTextField[3];
    
    public ChooseFile(){
        this.setModal(true);
        final JFileChooser chooser = new JFileChooser("./src/Assignment2");
        JPanel choosePanel = new JPanel(new BorderLayout());
        JPanel textFields = new JPanel();
        
        String labels[] = {"width","height","depth"};
        
        
        JLabel textLabels[] = new JLabel[3];
        for(int i = 0, len = size.length; i < len; i++){
            textLabels[i] = new JLabel(labels[i]);
            size[i] = new JTextField();
            size[i].setPreferredSize(new Dimension(50,30));
            textFields.add(textLabels[i]);
            textFields.add(size[i]);
        }
        //JButton go = new JButton("Load");
        
        chooser.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ev){
                int i = 0;
                for(JTextField sizeInput: size){
                    System.out.println(sizeInput.getText());
                    sizes[i++] = Integer.parseInt(sizeInput.getText());
                }
                file = chooser.getSelectedFile();
                
                dispose();
            }
        });
        choosePanel.add(chooser, BorderLayout.CENTER);
        choosePanel.add(textFields, BorderLayout.NORTH);
        //choosePanel.add(go, BorderLayout.SOUTH);
        add(choosePanel);
        pack();
        setVisible(true);
    }
    
    public File getFile(){
        return file;
    }
    public int[] getDimensions(){
        return sizes;
    }
}

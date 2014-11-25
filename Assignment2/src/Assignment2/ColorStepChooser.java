package Assignment2;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jacko
 */
public class ColorStepChooser extends JDialog {
    private IndexedColor color;
    private JColorChooser colorChooser;
    private JSlider slider;
    
    public ColorStepChooser(){
        color = new IndexedColor(0, Color.black);
        this.setModal(true);
        this.add(setupPanel());
        this.pack();
        this.setVisible(true);
    }
    
    public ColorStepChooser(IndexedColor color){
        this.color = color;
        this.setModal(true);
        this.add(setupPanel());
        this.pack();
        this.setVisible(true);
    }
   
    private JPanel setupPanel(){
        JPanel panel = new JPanel(new BorderLayout());
        colorChooser = new JColorChooser();
        slider = new JSlider(0, 255, color.getIndex());
        JButton ok = new JButton("Ok");
        ok.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                color = new IndexedColor(slider.getValue(),colorChooser.getColor());
                dispose();
            } 
        });
        JPanel indexSlider = new JPanel();
        JLabel sliderLabel = new JLabel("Select Value");
        
        indexSlider.add(sliderLabel);
        indexSlider.add(slider);
        panel.add(colorChooser, BorderLayout.NORTH);
        panel.add(indexSlider, BorderLayout.CENTER);
        panel.add(ok, BorderLayout.SOUTH);
        return panel;
    }
    
    public IndexedColor getIndexedColor(){
        return color;
    }
    
}

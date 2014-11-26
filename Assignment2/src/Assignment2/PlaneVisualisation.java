/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.*;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;


public class PlaneVisualisation extends JInternalFrame {
    
    final private VDSEx ds;
    private int dimension;
    private ArrayList<IndexedColor> colorSteps;
    private ArrayList<ArrayList> colorProfiles;
    private GLCanvas glcanvas;
    private JComboBox profileChooser;
    static int openFrameCount = 0;
    static final int xOffset = 260, yOffset = 0;
    
    
    final private int[][] combos = {{1,2},{0,2},{0,1}};
    
    public PlaneVisualisation(VDSEx ds1, int dimension, ArrayList<ArrayList> firstColorProfiles){
        super("Plane #" + (openFrameCount),
          false, //resizable
          false, //closable
          false, //maximizable
          false);//iconifiable
        setLayout(new BorderLayout());
        
        this.ds = ds1;
        this.dimension = dimension;
        
        this.colorProfiles = firstColorProfiles;
        
        this.colorSteps = this.colorProfiles.get(0);
        int[] size = ds.getDimensions();
        int largest = 0;
        for(int i: size)
            largest = (i > largest)? i: largest;
        Dimension canvasSize = new Dimension(largest, largest);
        int[] dims = ds.getDimensions();
        glcanvas = new GLCanvas();
        int max = (dimension < 3)? dims[dimension] : 360;
        JSlider ortho = new JSlider(0, max - 1);
        profileChooser = new JComboBox();
        int count = 0;
        for(ArrayList<IndexedColor> single: colorProfiles)
            profileChooser.addItem(String.format("Profile %d",++count));
        
        final ColorViewer colorFrame = new ColorViewer(colorSteps, "Colors", glcanvas, this);
        
        sortColors();
        
        final GLCanvas _canv = glcanvas;
        
        ortho.addChangeListener(new ChangeListener(){

            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider slider = (JSlider) e.getSource();
                ds.setDimValue(slider.getValue());
                _canv.repaint();
            }
        
        });
        
        final PlaneVisualisation ps = this;
        
        profileChooser.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JComboBox cb = (JComboBox) e.getSource();
                colorSteps = colorProfiles.get(cb.getSelectedIndex());
                _canv.repaint();
            }
        });
        
        if(dimension < 3){
            PlaneVisualisationListener pvl = new PlaneVisualisationListener(this, this.ds, this.dimension, ds.getMaxValue()/2, largest);
            glcanvas.addGLEventListener(pvl);
        } else {
            PlaneVisualisationNonOrthogonalListener pvl = new PlaneVisualisationNonOrthogonalListener(this, this.ds, this.dimension, ds.getMaxValue()/2, largest);
            glcanvas.addGLEventListener(pvl);
        }
        
        
        glcanvas.setPreferredSize(new Dimension(largest, largest));
        add(glcanvas, BorderLayout.NORTH);
        add(ortho, BorderLayout.CENTER);
        add(profileChooser, BorderLayout.SOUTH);
        pack();
        setLocation((xOffset*openFrameCount) + 150 , yOffset*openFrameCount);
        openFrameCount++;
    }
    
    
    
    public double[] getColor(int step){
        double[] color = new double[3];
        int index;
        for(int i = 0; i < colorSteps.size(); i++){
            index = colorSteps.get(i).getIndex();
            if(step > index) continue;
            if(step < index){
                color = makeColor(i ,step);
                break;
            }
            if(step == index) color = getIndexColor(i);
        }
        return color;
    }
    
    private double[] getIndexColor(int i){
        double color[] = new double[3];
        color[0] = colorSteps.get(i).getRed();
        color[1] = colorSteps.get(i).getGreen();
        color[2] = colorSteps.get(i).getBlue();
        return color;
    }
    
    private double[] makeColor(int i, int step){
        int diff = colorSteps.get(i).getIndex() - colorSteps.get(i-1).getIndex();
        int stepDiff = step - colorSteps.get(i-1).getIndex();
        double rgb[] = new double[3];
        rgb[0] = (colorSteps.get(i-1).getRed() + ((colorSteps.get(i).getRed() - colorSteps.get(i-1).getRed()) / diff * stepDiff)) / 255.0;
        rgb[1] = (colorSteps.get(i-1).getGreen() + ((colorSteps.get(i).getGreen() - colorSteps.get(i-1).getGreen()) / diff * stepDiff)) / 255.0;
        rgb[2] = (colorSteps.get(i-1).getBlue() + ((colorSteps.get(i).getBlue() - colorSteps.get(i-1).getBlue()) / diff * stepDiff)) / 255.0;
        return rgb;
    }
    
    private void refactorColors(DefaultTableModel model){
        for(int i = 0; i < model.getRowCount(); i++){
            model.removeRow(i);
        }
        for(IndexedColor color : colorSteps){
            model.addRow(new Object[]{color.getIndex() + "   ", color.getRGB() + "   ", "remove"});
        }
    }
    
    public void sortColors(){
        Collections.sort(colorSteps, new Comparator<IndexedColor>() {
            @Override
            public int compare(IndexedColor ic1, IndexedColor ic2){
                return ic1.getIndex() - ic2.getIndex();
            }
        });
    }
    
    public void repaintCanvas(){
        glcanvas.repaint();
    }
    
    public void refactorColorProfiles(){
        if(profileChooser.getItemCount() < colorProfiles.size())
            profileChooser.addItem(String.format("Profile %d",colorProfiles.size()));
    }
    
    
}

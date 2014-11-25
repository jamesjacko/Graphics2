package Assignment2;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jacko
 */
public class ColorViewer extends JDialog {
    
    private ArrayList<IndexedColor> colorSteps;
    private JTable table;
    private JList<IndexedColor> list;
    private GLCanvas glCanvas;
    private JScrollPane scroll;
    private MouseListener listListener;
    private JButton addColorStep;
    private DefaultListModel<IndexedColor> listModel;
    private PlaneVisualisation ps;
    private DataSetVisualiser ds;
    private JPanel container;
    
    public ColorViewer(ArrayList<IndexedColor> colorSteps, String title, GLCanvas glCanvas, PlaneVisualisation ps){
        //super(title);
        this.ps = ps;
        this.colorSteps = colorSteps;
        this.glCanvas = glCanvas;
        buildFrame();
    }
    
    public ColorViewer(ArrayList<IndexedColor> colorSteps, String title, DataSetVisualiser ds){
        //super(title);
        this.colorSteps = colorSteps;
        this.ds = ds;
        buildFrame();
    }
    
    private void buildFrame(){
        listListener = new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                if(e.getClickCount()==2){
                    int selected = list.getSelectedIndex();
                    System.out.println("" + selected);
                    ColorStepChooser chooser = new ColorStepChooser(colorSteps.get(selected));
                    int step = colorSteps.get(selected).getIndex();
                    colorSteps.set(selected, chooser.getIndexedColor());
                    listModel.set(selected, colorSteps.get(selected));
                    repaint();
                    ds.repaintViews();
                }
            }
            
        };
        addColorStep = new JButton("Add Color Step");
        addColorStep.addActionListener(new AddColorStepListener());
        container = new JPanel(new BorderLayout());
        buildList(listListener);
        container.add(addColorStep, BorderLayout.CENTER);
        add(container);
        setSize(200,200);
    }
    
    class AddColorStepListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            ColorStepChooser chooser = new ColorStepChooser();
            colorSteps.add(chooser.getIndexedColor());
            Collections.sort(colorSteps, new Comparator<IndexedColor>() {
                @Override
                public int compare(IndexedColor ic1, IndexedColor ic2){
                    return ic1.getIndex() - ic2.getIndex();
                }
            });
            listModel.removeAllElements();
            for(IndexedColor color: colorSteps)
                listModel.addElement(color);
            
            
            repaint();
            ds.repaintViews();
        }
        
    }
    
    public void showFrame(){
        setVisible(true);
    }
    
    public void buildList(MouseListener listListener){        
        System.out.println("Repaint");
        listModel = new DefaultListModel<>();
        for(IndexedColor color: colorSteps)
            listModel.addElement(color);
        list = new JList<>(listModel);
        list.setCellRenderer(new IndexedColorRenderer());
        list.addMouseListener(listListener);
        scroll = new JScrollPane(list);
        container.add(scroll, BorderLayout.NORTH);
    }

    private void refactorList() {
        this.remove(scroll);
        buildList(listListener);
    }
    
    
    
    
    
}

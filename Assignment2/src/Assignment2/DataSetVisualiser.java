
package Assignment2;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.media.opengl.*;
import javax.swing.event.*;

public class DataSetVisualiser extends JFrame {
    private DataSetLoader dl;
    private VDSEx ds;
    private int[] dimensions;
    private JPanel planesPanel;
    private int panelCount;
    private ArrayList<ArrayList> colorProfiles;
    private ArrayList<PlaneVisualisation> planes;
    
    public DataSetVisualiser(){
        super("Data Set Visualiser");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JFileChooser chooser = new JFileChooser("Choose Files");
        
//        int val = chooser.showOpenDialog(this);
//        File file;
//        if(val==JFileChooser.APPROVE_OPTION){
//            file = chooser.getSelectedFile();
//            dl = new DataSetLoader(file, 256, 256, 256);
//        }
        
        dl = new DataSetLoader("brain.raw", 181, 217, 181);
        
        ds = dl.getSet();
        panelCount = 2;
        dimensions = ds.getDimensions();
        planesPanel = new JPanel(new GridLayout(0, 3, 10, 10));
        
        colorProfiles = new ArrayList<>();
        
        ArrayList<IndexedColor> firstProfile = new ArrayList<IndexedColor>();
        
        firstProfile.add(new IndexedColor(0, Color.black));
        firstProfile.add(new IndexedColor(255, Color.white));
        
        colorProfiles.add(firstProfile);
        
        ArrayList<IndexedColor> secondProfile = new ArrayList<IndexedColor>();
        
        secondProfile.add(new IndexedColor(0, Color.white));
        secondProfile.add(new IndexedColor(255, Color.black));
        
        colorProfiles.add(secondProfile);
        
        JPanel layout = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel();
        
        planes = new ArrayList<PlaneVisualisation>();
        planes.add(new PlaneVisualisation(ds, 0, colorProfiles)); 
        planes.add(new PlaneVisualisation(ds, 1, colorProfiles));
        planes.add(new PlaneVisualisation(ds, 2, colorProfiles));
        JButton press = new JButton("Add Panel");
        JButton addColorProfile = new JButton("Color Profiles");
        
        press.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                addPanel();
            }
        });
        
        final DataSetVisualiser ds = this;
        
        addColorProfile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                ColorProfiles profiles = new ColorProfiles(ds);
            }
        });
        
        controlPanel.add(press);
        controlPanel.add(addColorProfile);
        for(PlaneVisualisation plane: planes)
            planesPanel.add(plane);
        
        layout.add(planesPanel, BorderLayout.NORTH);
        layout.add(controlPanel, BorderLayout.CENTER);
        
        add(layout);
        
        //add the GLCanvas just like we would any Component
        
        pack();
        
        
        //center the JFrame on the screen
        centerWindow(this);
    }
    
    public void addPanel(){
        if(panelCount < 6){
            PlaneVisualisation plane = new PlaneVisualisation(ds, 1, colorProfiles);
            planes.add(plane);
            planesPanel.add(planes.get(panelCount - 1));
            panelCount++;
            pack();
            centerWindow(this);
        }
    }
    
    public void repaintViews(){
        for(PlaneVisualisation plane: planes){
            plane.repaintCanvas();
            plane.refactorColorProfiles();
        }
    }
    
    public void centerWindow(Component frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize  = frame.getSize();

        if (frameSize.width  > screenSize.width ) frameSize.width  = screenSize.width;
        if (frameSize.height > screenSize.height) frameSize.height = screenSize.height;

        frame.setLocation (
            (screenSize.width  - frameSize.width ) >> 1, 
            (screenSize.height - frameSize.height) >> 1
        );
    }
    
    public ArrayList<ArrayList> getColorProfiles(){
        return colorProfiles;
    }
    
    public void addColorProfile(){
        ArrayList<IndexedColor> newProfile = new ArrayList<IndexedColor>();
        
        newProfile.add(new IndexedColor(0, Color.white));
        newProfile.add(new IndexedColor(255, Color.black));
        
        colorProfiles.add(newProfile);
    }
            
    
    
}

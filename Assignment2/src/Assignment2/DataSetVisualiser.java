
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
    private JDesktopPane desktop;
    private ColorProfiles colors;
    
    public DataSetVisualiser(){
        super("Data Set Visualiser");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        
        
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
        
        
        
        planes = new ArrayList<PlaneVisualisation>();
        
        
        
        JPanel layout = new JPanel(new BorderLayout());
        JPanel controlPanel = new JPanel();
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
        desktop = new JDesktopPane();
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
        colors = new ColorProfiles(ds);
        
        desktop.add(colors);
        
        layout.add(desktop, BorderLayout.CENTER);
        //layout.add(controlPanel, BorderLayout.CENTER);
        add(layout);
        //setContentPane(desktop);
        setJMenuBar(new PlaneMenu(this));
        //add the GLCanvas just like we would any Component
        
        setExtendedState(MAXIMIZED_BOTH);
        
        
        //center the JFrame on the screen
        centerWindow(this);
    }
    
    public void loadFile(File file, int[] sizes){
        
        desktop.removeAll();
        dl = new DataSetLoader(file, sizes[0], sizes[1], sizes[2]);
        
        ds = dl.getSet();
        panelCount = 2;
        dimensions = ds.getDimensions();
        
        desktop.add(colors);
        
        planes.removeAll(planes);
        
        planes.add(new PlaneVisualisation(ds, 0, colorProfiles, "Orthogonal X")); 
        planes.add(new PlaneVisualisation(ds, 1, colorProfiles, "Orthogonal Y"));
        planes.add(new PlaneVisualisation(ds, 2, colorProfiles, "Orthogonal Z"));
        planes.add(new PlaneVisualisation(ds, 3, colorProfiles, "Non Orthogonal X"));
        planes.add(new PlaneVisualisation(ds, 4, colorProfiles, "Non Orthogonal Y"));
        planes.add(new PlaneVisualisation(ds, 5, colorProfiles, "Non Orthogonal Z"));
        
        for(PlaneVisualisation plane: planes){
            desktop.add(plane);
            plane.setVisible(true);
        }
    }
    
    public void addPanel(){
//        if(panelCount < 6){
//            PlaneVisualisation plane = new PlaneVisualisation(ds, 1, colorProfiles);
//            planes.add(plane);
//            planesPanel.add(planes.get(panelCount - 1));
//            panelCount++;
//            pack();
//            centerWindow(this);
//        }
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

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import static Assignment2.PlaneVisualisation.openFrameCount;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.media.opengl.awt.GLCanvas;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


/**
 *
 * @author jacko
 */
public class ColorProfiles extends JInternalFrame {
    private DataSetVisualiser ds;
    private ArrayList<ArrayList> profiles;
    private JList profileList;
    private DefaultListModel<String> profileListModel;
    public ColorProfiles(DataSetVisualiser dsv){
        super("Color Profiles",
          false, //resizable
          false, //closable
          false, //maximizable
          false);//iconifiable
        this.ds = dsv;
        profileListModel = new DefaultListModel<>();
        profileList = new JList(profileListModel);
        loadColorProfiles();
        JButton addProfile = new JButton("Add Profile");
        addProfile.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                int cur = profiles.size();
                ds.addColorProfile();
                ds.repaintViews();
                loadColorProfiles();
                ColorViewer colorFrame = new ColorViewer(profiles.get(cur), "Color Profile", ds);
                colorFrame.showFrame();
                
            }
        });
        JPanel layout = new JPanel(new BorderLayout());
        
        layout.add(new JScrollPane(profileList), BorderLayout.NORTH);
        layout.add(addProfile, BorderLayout.CENTER);
        add(layout);
        
        pack();
        setVisible(true);
    }
    public void loadColorProfiles(){
        profileListModel.removeAllElements();
        profiles = ds.getColorProfiles();
        int index = 0;
        for(ArrayList profile: profiles)
            profileListModel.addElement(String.format("Profile %d", ++index));
        profileList.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                ColorViewer colorFrame;
                if(e.getClickCount()==2){
                    int selected = profileList.getSelectedIndex();
                    colorFrame = new ColorViewer(profiles.get(selected), "Color Profile", ds);
                    colorFrame.showFrame();
                }
            }
            
        });
    }
}

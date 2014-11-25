
package Assignment2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PlaneVisualisationListener implements GLEventListener {
    
    private VDSEx ds;
    private GLU glu;
    PlaneVisualisation pv;
    private int dimension;
    private int dimValue;
    private int[][][] volumeData;
    private int canvasSize;
    
    // x = 0, y = 1, z = 2
    final private int[][] combos = {{1,2},{0,2},{0,1}};
    
    public PlaneVisualisationListener(PlaneVisualisation pv, VDSEx ds, int dimension, int dimValue, int canvasSize){
        this.pv = pv;
        this.ds = ds;
        this.dimension = dimension;
        this.volumeData = ds.getVolumeData();
        this.dimValue = dimValue;
        this.canvasSize = canvasSize;
        ds.setDimValue(dimValue);
    }
    
    

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        glu = new GLU();
        
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glViewport(0, 0, canvasSize, canvasSize);
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluOrtho2D(0.0, canvasSize, 0.0, canvasSize);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        dimValue = ds.getDimValue();
        int dimensions[] = ds.getDimensions();
        for(int i = 0; i < dimensions[combos[dimension][0]]; i++){
            for(int j = 0; j < dimensions[combos[dimension][1]]; j++){
                
                int index;
                switch(dimension){
                    case 0:
                        index = volumeData[dimValue][i][j];
                        break;
                    case 1:
                        index = volumeData[i][dimValue][j];
                        break;
                    default:
                        index = volumeData[i][j][dimValue];
                }
                double color[] = pv.getColor(index);
                gl.glColor3d(color[0], color[1], color[2]);
                drawPixel(gl, i, j, -1, 2, new Point2D.Double(0,0));
            }
            
        }
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {
        
    }

    
    public void drawPixel(GL2 gl, double x, double y, double z, int size, Point2D.Double cent){
        gl.glBegin(GL2.GL_POLYGON);
            gl.glVertex3d(x + cent.x, y + cent.y, z);
            gl.glVertex3d(x + size + cent.x, y + cent.y, z);
            gl.glVertex3d(x + size + cent.x, y + size + cent.y, z);
            gl.glVertex3d(x + cent.x, y + size + cent.y, z);
        gl.glEnd(); 
    }

    @Override
    public void dispose(GLAutoDrawable glad) {
    
    }
    
    
    
}

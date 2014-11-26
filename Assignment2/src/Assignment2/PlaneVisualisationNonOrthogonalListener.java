
package Assignment2;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.*;
import javax.media.opengl.*;
import javax.media.opengl.glu.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class PlaneVisualisationNonOrthogonalListener implements GLEventListener {
    
    private VDSEx ds;
    private GLU glu;
    PlaneVisualisation pv;
    private int dimension;
    private int dimValue;
    private int[][][] volumeData;
    private int canvasSize;
    
    // x = 0, y = 1, z = 2
    final private int[][] combos = {{1,2},{0,2},{0,1}};
    
    public PlaneVisualisationNonOrthogonalListener(PlaneVisualisation pv, VDSEx ds, int dimension, int dimValue, int canvasSize){
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
        int angle = dimValue;
        
        int opposite = (angle + 180) % 360;
        
        double dist = (dimensions[0] / 2) * Math.sqrt(2); 
        
        int x1 = (int) (Math.cos(Math.toRadians(angle)) * dist);
        int y1 = (int) (Math.sin(Math.toRadians(angle)) * dist);
        
        int x2 = (int) (Math.cos(Math.toRadians(opposite)) * dist);
        int y2 = (int) (Math.sin(Math.toRadians(opposite)) * dist);
        
        Point2D.Double start = new Point2D.Double(x1, y1);
        Point2D.Double end = new Point2D.Double(x2, y2);
        
        
        
            drawLine(gl, start, end, dimensions);
        
        
    }
    
    public void drawLine(GL2 gl, Point2D.Double start, Point2D.Double end, int[] dimensions){
        for(int z = 0; z < dimensions[2]; z++){
            double dy = end.y - start.y;
            double dx = end.x - start.x;
            double error = 0.0;
            double deltaError = Math.abs(dy / dx);
            double y = start.y + (dimensions[1] / 2);
            double x = start.x + (dimensions[0] / 2);
            double endX = (x > end.x)? end.x - 1 + (dimensions[0] / 2): end.x + 1 + (dimensions[0] / 2);
            double endY = (y > end.y)? end.y - 1 + (dimensions[1] / 2): end.y + 1 + (dimensions[1] / 2);


            double incX = (start.x > end.x)? -1 : 1;
            double incY = (start.y > end.y)? -1 : 1;


            if(deltaError > 1){
                deltaError = Math.abs(dx / dy);
                while(y != endY){

                        if((x >= 0 && x <= dimensions[0] - 1) && (y >= 0 && y <= dimensions[1] - 1)){
                            int index = volumeData[(int)x][(int)y][z];
                            double color[] = pv.getColor(index);
                            gl.glColor3d(color[0], color[1], color[2]);
                            drawPixel(gl, y, z, -1.0, 1, new Point2D.Double(0,0));
                        }

                    error += deltaError;
                    if(error >= 0.5){
                        x += incX;
                        error -= 1;
                    }
                    y += incY;

                }
            }else{
                while(x != endX){
                        if((x >= 0 && x <= dimensions[0] - 1) && (y >= 0 && y <= dimensions[1] - 1)){
                            int index = volumeData[(int)x][(int)y][z];
                            double color[] = pv.getColor(index);
                            gl.glColor3d(color[0], color[1], color[2]);
                            drawPixel(gl, dimensions[0] - x, z, -1.0, 1, new Point2D.Double(0,0));
                        }
                    error += deltaError;
                    if(error >= 0.5){
                        y += incY;
                        error -= 1;
                    }
                    x += incX;
                }
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

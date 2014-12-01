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
    final private int[][] combos = {{1, 2}, {0, 2}, {0, 1}};

    public PlaneVisualisationNonOrthogonalListener(PlaneVisualisation pv, VDSEx ds, int dimension, int dimValue, int canvasSize) {
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
        int dimensions[] = new int[3];
        int angle = dimValue;
        
        switch(dimension){
            case 3:
                dimensions[0] = ds.getDimensions()[1];
                dimensions[1] = ds.getDimensions()[2];
                dimensions[2] = ds.getDimensions()[0];
                break;
            case 4:
                dimensions[0] = ds.getDimensions()[0];
                dimensions[1] = ds.getDimensions()[2];
                dimensions[2] = ds.getDimensions()[1];
                break;
            default:
                dimensions[0] = ds.getDimensions()[0];
                dimensions[1] = ds.getDimensions()[1];
                dimensions[2] = ds.getDimensions()[2];
                break;
        }

        double theta = angle * (Math.PI / 180);
        double radius = Math.sqrt(2) * (dimensions[0] / 2);
        
        int x2 = (int) ((dimensions[0] / 2 ) + (radius * Math.cos(theta)));
        int y2 = (int) ((dimensions[1] / 2 ) + (radius * Math.sin(theta)));
        
        theta = (angle + 180) * (Math.PI / 180);
        int x1 = (int) ((dimensions[0] / 2 ) + (radius * Math.cos(theta)));
        int y1 = (int) ((dimensions[1] / 2 ) + (radius * Math.sin(theta)));

        ArrayList<int[]> lines = getLine(gl, x2, y2, x1, y1, dimensions);
        
        int[][] projection = new int[lines.size()][dimensions[2]];
        
        for(int i = 0; i < lines.size(); i++){
            for(int j = 0; j < dimensions[2]; j++){
                switch (dimension){
                    case 3:
                        projection[i][j] = volumeData[j][lines.get(i)[0]][lines.get(i)[1]];
                        break;
                    case 4:
                        projection[i][j] = volumeData[lines.get(i)[0]][j][lines.get(i)[1]];
                        break;
                    default:
                        projection[i][j] = volumeData[lines.get(i)[0]][lines.get(i)[1]][j];
                }
            }
        }
        drawArray(gl, projection, pv);
        

    }

    public ArrayList<int[]> getLine(GL2 gl, int x1, int y1, int x2, int y2, int[] dimensions) {
        ArrayList<int[]> points = new ArrayList<int[]>();

        int dx = x2 - x1;
        int dy = y2 - y1;

        int y = y1;
        int x = x1;

        int incX, incY;

        if (dx >= 0) {
            incX = 1;
        } else {
            incX = -1;
            dx = -dx;
        }

        if (dy >= 0) {
            incY = 1;
        } else {
            incY = -1;
            dy = -dy;
        }
        int error;
        if(dx > 0){
            error = dx >> 1;
        } else {
            error = dy >> 1;
        }

        int length = Math.max(dy, dx) + 1;
        
        for (int i = 0; i < length; i++) {
            if(error < 0)
                    error *= -1;
            if (dx > dy) {
                error += dy;
                if(error >= dx){
                    error -= dx;
                    
                    y += incY;
                }
                
                x += incX;

            } else {

                error += dx;
                if(error >= dy){
                    error -= dy;
                    
                    x += incX;
                }
                
                y += incY;
            }
            if(x > -1 && x < dimensions[0] && y > -1 && y < dimensions[1]){
                points.add(new int[]{x, y});
            }
            
        }
       
        return points;
    }

    @Override
    public void reshape(GLAutoDrawable glad, int i, int i1, int i2, int i3) {

    }

    public void drawPixel(GL2 gl, double x, double y, double z, int size, Point2D.Double cent,
            GLAutoDrawable drawable) {
        Double ar = (double) Math.min(drawable.getSurfaceWidth(), drawable.getSurfaceHeight())
                / Math.max(ds.getDimensions()[0], ds.getDimensions()[2]);
        x *= ar;
        y *= ar;
        z *= ar;
        size *= ar;
        gl.glBegin(GL2.GL_POLYGON);
        gl.glVertex3d(x + cent.x, y + cent.y, z);
        gl.glVertex3d(x + size + cent.x, y + cent.y, z);
        gl.glVertex3d(x + size + cent.x, y + size + cent.y, z);
        gl.glVertex3d(x + cent.x, y + size + cent.y, z);
        gl.glEnd();
    }
    
    public void drawArray(GL2 gl, int[][] array, PlaneVisualisation pv){
        
        for(int i = 0; i < array.length; i++){
            for(int j = 0; j < array[0].length; j++){
                int index = array[i][j];
                double color[] = pv.getColor(index);
                gl.glColor3d(color[0], color[1], color[2]);
                gl.glBegin(GL2.GL_QUADS);
                    gl.glVertex2i(i, j);
                    gl.glVertex2i(i + 1, j);
                    gl.glVertex2i(i + 1, j + 1);
                    gl.glVertex2i(i, j + 1);
                gl.glEnd();
            }
        }
    }

    @Override
    public void dispose(GLAutoDrawable glad) {

    }

}

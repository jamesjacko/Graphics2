/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author eeu203
 */
public class VDSEx extends VolumetricDataSet {
    private int currentDimValue;
    public VDSEx(final File in, final int xSize, final int ySize, final int zSize) throws IOException, FileNotFoundException{
        this(new FileInputStream(in), xSize, ySize, zSize);
        this.currentDimValue = 0;
    }
    
    public VDSEx(final InputStream in, final int xSize, final int ySize, final int zSize) throws IOException, FileNotFoundException{
        super(in, xSize, ySize, zSize);
        this.currentDimValue = 0;
    }
    
    
    
    
    public void setDimValue(int dimValue){
        this.currentDimValue = dimValue;
    }
    
    public int getDimValue(){
        return this.currentDimValue;
    }
}

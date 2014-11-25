/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import java.io.File;
import java.io.IOException;

/**
 *
 * @author eeu203
 */
public class DataSetLoader {
    private VDSEx ds;
    private int dimension = 1;
    private int[] dimensionVal = {0,0,0};
    public DataSetLoader(String dataset, int x, int y, int z){
        try{
            ds = new VDSEx(getClass().getResourceAsStream(dataset),x,y,z);   
        } catch (IOException e){
            System.out.println("Sux to be you");
        }
    }
    public DataSetLoader(File dataset, int x, int y, int z){
        try{
            ds = new VDSEx(dataset,x,y,z);   
        } catch (IOException e){
            System.out.println("Sux to be you");
        }
    }
    public VDSEx getSet(){
        return ds;
    }
    public void setDimension(int dim){
        this.dimension = dimension;
    }
    public int getDimension(){
        return this.dimension;
    }
    public void setDimensionVal(int dimension, int dimVal){
        this.dimensionVal[dimension] = dimVal;
    }
    public int[] getDimensionVal(){
        return this.dimensionVal;
    }
}

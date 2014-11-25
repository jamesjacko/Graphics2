package Assignment2;

import java.awt.Color;
/**
 * @title IndexedColor class 
 * subclass of awt.Color adds an index instance variable
 * @author James Jackson
 */
public class IndexedColor extends Color{
    private final int index;
    
    /**
     * Constructor for the IndexedColor
     * @param index the index for the color
     * @param color the color object to set the color
     */
    public IndexedColor(int index, Color color){
        super(color.getRed(), color.getGreen(), color.getBlue());
        this.index = index;
    }
    
    /**
     * Gets the index of the current instance
     * @return the index
     */
    public int getIndex(){
        return index;
    }
  
    
    
    /**
     * Override equals method to compare indexes of the array
     * @param o the object to compare
     * @return are the objects equal
     */
    @Override
    public boolean equals(Object o)
    {
        IndexedColor other;
        if(o instanceof IndexedColor){
            other = (IndexedColor) o;
            return other.getIndex() == this.index;
        } else {
            return false;
        }
    }
    
    public String toString(){
        return Integer.toString(index);
    }
    
}

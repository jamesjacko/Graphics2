package Assignment2; 

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * VolumetricDataSet
 *
 * Contains voxel data with minimum and maximal values and
 * an appropriate loader.
 *
 * Created 16 September 2014
 *
 * @author Gray, Cameron C. <ees419@bangor.ac.uk>
 *
 */
public class VolumetricDataSet {

    private final int[]     dimensions;
    private int             maxValue;
    private int             minValue;
    private final int[][][] volumeData;

    /**
     * Construct a new dataset from a File.
     *
     * @param in
     *            the file to read the data from.
     * @param xSize
     *            the maximal x dimension to use.
     * @param ySize
     *            the maximal y dimension to use.
     * @param zSize
     *            the maximal z dimension to use.
     * @throws IOException
     *             if the underlying reader encounters an error.
     * @throws FileNotFoundException
     *             if the file cannot be found.
     */
    public VolumetricDataSet(final File in, final int xSize, final int ySize,
            final int zSize) throws IOException, FileNotFoundException {

        this(new FileInputStream(in), xSize, ySize, zSize);

    }

    /**
     * Construct a new dataset.
     *
     * @param in
     *            the stream to read the data from.
     * @param xSize
     *            the maximal x dimension to use.
     * @param ySize
     *            the maximal y dimension to use.
     * @param zSize
     *            the maximal z dimension to use.
     * @throws IOException
     *             if the underlying reader encounters an error.
     */
    public VolumetricDataSet(final InputStream in, final int xSize,
            final int ySize, final int zSize) throws IOException {
        int value = 0, xCur = 0, yCur = 0, zCur = 0;
        volumeData = new int[xSize][ySize][zSize];
        while ((value = in.read()) != -1) {
            volumeData[xCur][yCur][zCur] = value;

            if (value > maxValue)
                maxValue = value;
            if (value < minValue)
                minValue = value;

            if (++xCur == xSize) {
                xCur = 0;
                if (++yCur == ySize) {
                    yCur = 0;
                    zCur++;
                }
            }
        }
        dimensions = new int[] { xSize, ySize, zSize };
    }

    /**
     * Retrieve the length of the dimensions of the data set.
     *
     * @return the dimensions.
     */
    public int[] getDimensions() {
        return dimensions;
    }

    /**
     * @return the maximal value
     */
    public int getMaxValue() {
        return maxValue;
    }

    /**
     * @return the minimal value.
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * @return the entire data set as a 3-D array. x is the 1st dimension, y the
     *         2nd and z the 3rd.
     */
    public int[][][] getVolumeData() {
        return volumeData;
    }

}

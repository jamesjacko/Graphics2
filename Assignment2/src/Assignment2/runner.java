/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Assignment2;

import javax.swing.SwingUtilities;

/**
 *
 * @author jacko
 */
public class runner {
    public static void main(String[] args) {
        final DataSetVisualiser app = new DataSetVisualiser();
        
        SwingUtilities.invokeLater (
            new Runnable() {
                public void run() {
                    app.setVisible(true);
                }
            }
        );
    }
}

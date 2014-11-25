package Assignment2;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
public class IndexedColorRenderer extends JLabel implements ListCellRenderer<IndexedColor>  {
    
    public IndexedColorRenderer() {
        setOpaque(true);
    }
    @Override
    public Component getListCellRendererComponent(JList<? extends IndexedColor> list, IndexedColor color, int index, boolean isSelected, boolean cellHasFocus) {
        
        String step = color.toString();
        ColorIcon icon = new ColorIcon(color);
         
        setIcon(icon);
        setText(step);
        
        
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
         
        return this;
    }
    
     

}

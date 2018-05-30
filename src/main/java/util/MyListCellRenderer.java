package util;

import javax.swing.*;
import java.awt.*;

public class MyListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
         super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
         if(index % 2 == 0){
//             setBackground(Color.ORANGE);
             setFont(new Font("微软雅黑",Font.BOLD,15));

         }else{
//             setBackground(Color.WHITE);
             setFont(new Font("微软雅黑",Font.BOLD,15));
         }
         return this;
    }
}

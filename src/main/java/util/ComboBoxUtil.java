package util;

import javax.swing.*;

public class ComboBoxUtil  extends DefaultComboBoxModel<String> {



    ComboBoxUtil(String[] s) {
        for(int i=0;i<s.length;i++){
            addElement(s[i]);
        }
    }
}

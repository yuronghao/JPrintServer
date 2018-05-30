package util;

import bean.Printset;
import com.emi.ui.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogUitl {
    private static DefaultListModel listModel =null;

    public static DefaultListModel getInstance() {
        if(listModel == null){
             listModel =  new DefaultListModel();
        }
        return listModel;
    }
    public static void addElement(String element){
        JList jList  = MainWindow.mainWindow.getLogList();
        DefaultListModel defaultListModel = LogUitl.getInstance();
        SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        String dateString  = sm.format(new Date());
        defaultListModel.addElement(dateString+"|"+element);
        jList.setModel(defaultListModel);
        jList.setCellRenderer(new MyListCellRenderer());
        jList.setSelectionForeground(Color.RED);
    }


    public static String getDetailString(Printset printset){
            StringBuffer stringBuffer = new StringBuffer();
            Field[] fields = printset.getClass().getDeclaredFields();
        String[] types1={"int","java.lang.String","boolean","char","float","double","long","short","byte"};
        String[] types2={"Integer","java.lang.String","java.lang.Boolean","java.lang.Character","java.lang.Float","java.lang.Double","java.lang.Long","java.lang.Short","java.lang.Byte"};
        for (Field f : fields){
            f.setAccessible(true);
            // 字段名
//            System.out.println(f.getName() + ":");
            // 字段值
            for(int i=0;i<types1.length;i++){
                if(f.getType().getName().equalsIgnoreCase(types1[i])|| f.getType().getName().equalsIgnoreCase(types2[i])){
                    try {
//                        System.out.println(f.get(printset)+"");
                        if(f.get(printset) != null && !"".equals(f.get(printset))){
                            stringBuffer.append(f.getName() + ":");
                            stringBuffer.append(f.get(printset) + ";");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

        }

            return stringBuffer.toString();


    }



    public static  void main(String[] args){
        Printset printset = new Printset();
        printset.setSntext("aaaa");
        printset.setStandard("bbbb");
        String aa = LogUitl.getDetailString(printset);
        System.out.println(aa);


    }



}

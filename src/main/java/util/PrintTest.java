package util;

import com.emi.ui.MainWindow;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class PrintTest {

    public static  void main(String[] args){
        //1.得到一个文件的输入流
        FileInputStream fiStream = null;
        try {
              fiStream = new FileInputStream("C:\\Users\\yurh\\Desktop\\taicang.jpg");
            long size = fiStream.getChannel().size();
            System.out.println(size);
        } catch (FileNotFoundException ffne) {
            ffne.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();

        Doc myDoc = new SimpleDoc(fiStream, flavor, null);

            DocPrintJob job = null;
            PrintService PS = PrintServiceLookup.lookupDefaultPrintService();
            job = PS.createPrintJob();

            try {
                job.print(myDoc, pras);
            } catch (PrintException pe) {
                pe.printStackTrace();
            }finally {
                if(fiStream != null){
                    try{
                        fiStream.close();
                    }catch (IOException e){
                        e.printStackTrace();
                    }

                }
            }

    }
}

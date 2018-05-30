package logic;

import bean.Printset;
import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.emi.ui.MainWindow;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import util.LogUitl;
import util.MysqlConn;
import util.SystemUtil;

import javax.print.*;
import javax.print.attribute.DocAttributeSet;
import javax.print.attribute.HashDocAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.print.event.PrintJobListener;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static cn.hutool.db.DbUtil.close;

/**
 * 打印线程
 */
public class RunPrintThread extends Thread {

    private static final Log logger = LogFactory.get();

    @Override
    public void run() {

        long startTimeMillis = System.currentTimeMillis();
        // 计时
        while (!SystemUtil.printexit) {
            try{
                String sql = "  select * from printset p where p.isUsed = 1 and p.isPrint = 0 ";
                List<Printset> printsetList = MysqlConn.executeList(Printset.class,sql);

                boolean flag = false;
                if(printsetList != null && printsetList.size() >0){
                    for(int i = 0 ;i<printsetList.size();i++){
                        Printset printset = printsetList.get(i);
                        //1.得到一个文件的输入流
                        FileInputStream fiStream = null;
                        try {
                            String toPrintPath = printset.getDoPDFPath().replace("\\","\\\\");
                            fiStream = new FileInputStream(toPrintPath);
                        } catch (FileNotFoundException ffne) {
                            logger.error("读取预打印文件错误，message="+ffne.getMessage());
                            LogUitl.addElement("读取预打印文件错误，message："+ffne.getMessage());
                            //文件读取不到，删除数据库记录
                            this.delPrintsetById(printset.getId());
                        }
                        DocFlavor flavor = DocFlavor.INPUT_STREAM.JPEG;
                        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
                        PrintService printService[] = PrintServiceLookup.lookupPrintServices(flavor, pras);

                        if(fiStream != null){
                            Doc myDoc = new SimpleDoc(fiStream, flavor, null);
                            if(printService.length > 0){
                                DocPrintJob job = null;
                                for(int k=0;k<printService.length;k++){
                                    PrintService tempprintService = printService[k];
                                    if(tempprintService.getName().equals(MainWindow.mainWindow.getSettingPrintServercomboBox().getSelectedItem().toString())){
                                        job = printService[k].createPrintJob();
                                    }
                                }
                                try {
                                    job.print(myDoc, pras);

                                    job.addPrintJobListener(new PrintJobAdapter(){
                                        public void printDataTransferCompleted(PrintJobEvent event){
                                            System.out.println("数据传输成功!!");
                                        }
                                        public void printJobNoMoreEvents(PrintJobEvent event){
                                            System.out.println("该接口中没有更多的方法可以让打印机调用!!");
                                        }
                                        public void printJobCanceled(PrintJobEvent event){
                                            System.out.println("取消打印服务!!");
                                        }
                                        public void printJobCompleted(PrintJobEvent event){
                                            System.out.println("打印任务完成!!");
                                        }
                                        public void printJobFailed(PrintJobEvent event){
                                            System.out.println("打印任务失败!!");
                                        }
                                        public void printJobRequiresAttention(PrintJobEvent event){
                                            System.out.println("可以恢复的错误,如打印机缺纸!!");
                                        }
                                    });
                                    flag = true;
                                } catch (PrintException pe) {

                                    logger.error("执行打印文档任务错误，message="+pe.getMessage());
                                    LogUitl.addElement("执行打印文档任务错误，message="+pe.getMessage());
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


                    }

                    //全部提交打印队列之后，①修改printset为已打印isPrint,②移动PrintPDF文件夹下的文件到OldPrintPDFDir并删除
                    if(flag){
                        for(int i  = 0 ;i<printsetList.size();i++){
                            printsetList.get(i).setIsPrint(1);
                            Printset printset = printsetList.get(i);
                            String sendPath = SystemUtil.configHome + "PrintPDF" + File.separator+printset.getTempPDFName();
                            String getPath = SystemUtil.configHome + "OldPrintPDFDir" +File.separator+ printset.getTempPDFName();
                            FileUtil.copy(sendPath,getPath,true);//打印好的文件复制到OldPrintPDFDir文件夹
                            FileUtil.del(sendPath);//删除JPG文件

                            String pdfname = printset.getTempPDFName().split(".")[0]+".PDF";
                            String pdfPath = SystemUtil.configHome + "PrintPDF" + File.separator+pdfname;
                            FileUtil.del(pdfPath);//删除PDF文件
                        }
                        this.updatePrintSet(printsetList);
                    }
                }


                long currentTimeMillis = System.currentTimeMillis();
                long lastTimeMillis = currentTimeMillis - startTimeMillis;

                try {
                    logger.error("打印线程等待10秒~");
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.error("打印主程序错误，请检查。message="+e.getMessage());
                LogUitl.addElement("打印主程序错误，请检查。message="+e.getMessage());
            }

        }
    }

    /**
     * 自定义进度条单元格渲染器
     */
    public static class MyProgressBarRenderer extends JProgressBar implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            Integer v = (Integer) value;//这一列必须都是integer类型(0-100)
            setValue(v);
            return this;
        }
    }




    public void updatePrintSet(List<Printset> printsetList){
        int rowsTmp = 0;
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            conn = MysqlConn.getConn(); //打开一个池连接
            conn.setAutoCommit(false);
            //第二步：更新 表中 lb1 为 40
            String strsql = "UPDATE printset SET isUsed='1',doPDFPath = ?,isPrint = ? " +
                    "WHERE id=? ";

            if(printsetList.size()!=0){
                for (Printset printset : printsetList) {
                    pstmt = conn.prepareStatement(strsql.toString());
                    pstmt.setString(1, printset.getDoPDFPath());
                    pstmt.setInt(2,printset.getIsPrint());
                    pstmt.setInt(3, printset.getId());
                    pstmt.addBatch();
                    pstmt.executeBatch();
                    conn.commit();
                    if (null==conn) { //如果连接关闭了 就在创建一个 为什么要这样 原因是 conn.commit()后可能conn被关闭
                        conn = MysqlConn.getConn();
                        conn.setAutoCommit(false);
                    }
                    rowsTmp++;
                }
                pstmt.executeBatch();
                conn.commit();
            }
            conn.setAutoCommit(true);
            logger.info("printset 数据表更新完毕" );
        }catch (Exception e) {
            try {
                conn.rollback();
                logger.info("printset 数据表更新异常-回滚[" + e.getMessage() + "]");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            close(conn, pstmt, null);
        }
    }


    public void delPrintsetById(Integer id){
        Connection conn = null;
        Statement pstmt = null;
        try {
            conn = MysqlConn.getConn(); //打开一个池连接
            pstmt = conn.createStatement();

            String deleteSql = "DELETE from printset where id = '"+id+"' ";
            pstmt.executeUpdate(deleteSql);
            logger.info("printset 删除数据id为"+id );
        }catch (Exception e) {
            try {
                conn.rollback();
                logger.info("printset 数据表更新异常-回滚[" + e.getMessage() + "]");
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }finally{
            close(conn, pstmt, null);
        }

    }







}

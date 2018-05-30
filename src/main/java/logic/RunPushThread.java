package logic;

import bean.Printset;
import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.hutool.core.date.BetweenFormater;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.emi.ui.MainWindow;
import com.mysql.cj.core.util.LogUtils;
import me.chanjar.weixin.mp.api.WxMpService;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import org.apache.commons.io.FileUtils;
import sun.applet.Main;
import util.LogUitl;
import util.MysqlConn;
import util.PDF2IMAGE;
import util.SystemUtil;

import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSize;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.print.PrinterJob;
import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import static cn.hutool.db.DbUtil.close;

/**
 * 生成PDF线程
 */
public class RunPushThread extends Thread {


    private static final Log logger = LogFactory.get();

    @Override
    public void run() {



        long startTimeMillis = System.currentTimeMillis();
        // 计时
        while (!SystemUtil.pushexit) {
            try{
                String sql = "  select * from printset p where p.isUsed = 0 and p.isPrint = 0 ";
                List<Printset> printsetList = MysqlConn.executeList(Printset.class,sql);
                if(printsetList == null || printsetList.size() == 0){

                }else{
                    // 初始化
                    MainWindow.mainWindow.getPrintTotalProgressBar().setIndeterminate(true);

                    MainWindow.mainWindow.getPrintTotalProgressBar().setIndeterminate(false);
                    MainWindow.mainWindow.getPrintTotalProgressBar().setMaximum((int) printsetList.size()-1);
                    MainWindow.mainWindow.getPrintTotalProgressBar().setMinimum(0);
                    for(int i = 0;i<printsetList.size();i++){
                        Printset printset = printsetList.get(i);
                        MainWindow.mainWindow.getPrintTotalProgressBar().setValue(i);
                        //提交打印队列
                        File file3 = new File(printset.getTemplatePath());//编译好的报表路径
                        JasperReport jasperReport = null;
                        try {
                            jasperReport = (JasperReport) JRLoader.loadObject(file3);
                            Map<String, Object> parameters = new HashMap<String, Object>();
                            parameters.put("sntext", printset.getSntext());
                            parameters.put("standard", printset.getStandard());
                            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
//                    JasperPrintManager.printReport(jasperPrint,false);
                            String tempPDFName = String.valueOf(UUID.randomUUID());
                            String tempPDFPath = SystemUtil.configHome + "PrintPDF" +File.separator+ tempPDFName +".PDF";

                            JasperExportManager.exportReportToPdfFile(jasperPrint,tempPDFPath);
                            RandomAccessFile randomAccessFile = null;
                            FileChannel channel = null;
                            try{
                                randomAccessFile = new RandomAccessFile(tempPDFPath, "rw");
                                channel = randomAccessFile.getChannel();
                                FileLock lock = null;
                                while (lock == null) {
                                    try {
                                        lock = channel.lock();
                                    } catch (Exception e) {
                                        System.out.println("Write Process : get lock failed");
                                    }
                                }
                                lock.release();

                                PDF2IMAGE.pdf2Image(tempPDFPath, SystemUtil.configHome + "PrintPDF", 300);


                            }catch (Exception e){
                                e.printStackTrace();
                            }finally {
                                if(randomAccessFile != null){
                                    try {
                                        randomAccessFile.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                                if (null != channel) {
                                    try {
                                        channel.close();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }




                            printset.setIsUsed(1);
                            printset.setDoPDFPath(SystemUtil.configHome + "PrintPDF" +File.separator+ tempPDFName+".JPG");
                            printset.setIsPrint(0);
                            printset.setTempPDFName(tempPDFName+".JPG");

                            LogUitl.addElement("成功，输出信息："+LogUitl.getDetailString(printset));
                        } catch (JRException e) {
                            e.printStackTrace();
                            logger.error(e);
                            LogUitl.addElement("打印出错："+e.getMessage()+"线程等待5秒");
                            try {
                                Thread.sleep(5000);
                                notifyAll();
                            } catch (InterruptedException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }


                    //修改mysql PDF路径并设置isUsed为1
                    this.updatePrintSet(printsetList);

                    long currentTimeMillis = System.currentTimeMillis();
                    long lastTimeMillis = currentTimeMillis - startTimeMillis;


                }


                try {
                    Thread.sleep(10000);
                    logger.error("生成PDF/JPG 线程等待10秒~");
                    LogUitl.addElement("生成PDF/JPG 线程等待10秒~");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    logger.error(e);
                }
            }catch (Exception e){
                e.printStackTrace();
                logger.error("生成PDF/JPG 主程序错误，请检查。message:"+e.getMessage());
                LogUitl.addElement("生成PDF/JPG 主程序错误，请检查。message:"+e.getMessage());
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
            String strsql = "UPDATE printset SET isUsed='1',doPDFPath = ?,isPrint = ?, tempPDFName=?" +
                    "WHERE id=? ";

            if(printsetList.size()!=0){
                for (Printset printset : printsetList) {
                    pstmt = conn.prepareStatement(strsql.toString());
                    pstmt.setString(1, printset.getDoPDFPath());
                    pstmt.setInt(2,printset.getIsPrint());
                    pstmt.setString(3,printset.getTempPDFName());
                    pstmt.setInt(4, printset.getId());
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
}

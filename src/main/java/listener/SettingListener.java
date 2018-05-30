package listener;

import bean.Printset;
import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.emi.ui.Init;
import com.emi.ui.MainWindow;
import logic.RunPrintThread;
import logic.RunPushThread;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.commons.lang3.StringUtils;
import util.LogUitl;
import util.MysqlConn;
import util.SystemUtil;
import util.WriteFile;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 设置tab相关事件监听
 * Created by rememberber(https://github.com/rememberber) on 2017/6/16.
 */
public class SettingListener {
    private static final Log logger = LogFactory.get();

    private static boolean selectAllToggle = false;

    public static void addListeners() {

//        // 设置-常规-启动时自动检查更新
//        MainWindow.mainWindow.getAutoCheckUpdateCheckBox().addActionListener(e -> {
//            Init.configer.setAutoCheckUpdate(MainWindow.mainWindow.getAutoCheckUpdateCheckBox().isSelected());
//            Init.configer.save();
//        });

        // 设置- -》自定义设置-》 提交打印队列按钮
        MainWindow.mainWindow.getCustomTjButton().addActionListener(e -> {
            if(checkBeforeCustomSet()){
                try {
                    Init.configer.setSntext(MainWindow.mainWindow.getSntextField().getText());
                    Init.configer.setSncode(new String(MainWindow.mainWindow.getSncodeField().getText()));
                    Init.configer.setStandard(new String(MainWindow.mainWindow.getStandardField().getText()));
                    Init.configer.setPrintserver(new String(MainWindow.mainWindow.getPrintservercomboBox().getSelectedItem().toString()));
                    Init.configer.save();

                    String sntext = MainWindow.mainWindow.getSntextField().getText();//物料名称
                    String sncode = MainWindow.mainWindow.getSncodeField().getText();//物料编码
                    String standard = MainWindow.mainWindow.getStandardField().getText();//物料规格
                    String printserver = MainWindow.mainWindow.getPrintservercomboBox().getSelectedItem().toString();//打印机名称
                    String templatePath = MainWindow.mainWindow.getCustomTemplateFilePathField().getText();//模板文件路径
//                String CustomDataPath = MainWindow.mainWindow.getCustomDataPathField().getText();//要上传的文件路径
//                String path = CustomDataPath +"/"+UUID.randomUUID().toString()+".txt";
//                File file = new File(path);
//                if(!file.exists()){
//                    file.createNewFile();
//                }
//
//                //测试数据 Fax|xx.fr3|sntext=钢材|sncode=sn-21321|standard=测试规格1|date=2018-5-25
//                String content = printserver+"|"+templatePath+"|sntext="+sntext+"|sncode="+sncode+"|standard="+standard+"|date=2018-5-25";
//                WriteFile.writeFileContent(path,content);
                    List<Printset> printsetList = new ArrayList<Printset>();
                    SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
                    Printset printset = new Printset();
                    printset.setSntext(sntext);
                    printset.setSncode(sncode);
                    printset.setStandard(standard);
                    printset.setPrintserver(printserver);
                    String realtemplatePath = templatePath.replace("\\","\\\\");
                    printset.setTemplatePath(realtemplatePath);
                    sm.format(new Date());
                    printsetList.add(printset);
                    MysqlConn.insert(printsetList);

                    File file1 = new File(templatePath);//编译好的报表路径
                    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file1);
                    Map<String, Object> parameters = new  HashMap<String, Object>();
                    parameters.put("sntext", sntext);
                    parameters.put("standard", standard);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
                    if(jasperPrint.getPages().size()<1){
                        JOptionPane.showMessageDialog(null, "对不起,没有查询到要打印的数据\n请选择数据后再进行打印!",
                                "打印提示信息", JOptionPane.INFORMATION_MESSAGE);
//                        con.close();
                        return;
                    }
                    JasperPrintManager.printReport(jasperPrint, true);




//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "成功！", "成功",
//                        JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "提交打印队列失败！\n\n" + e1.getMessage(), "失败",
                            JOptionPane.ERROR_MESSAGE);
                    LogUitl.addElement("提交打印队列失败！" + e1.getMessage());
                }
            }

        });

        //设置-》自定义设置-》打印预览按钮
        MainWindow.mainWindow.getToShowSncodeButton().addActionListener(e -> {
            if(checkBeforeCustomSet()){
                try {
                    Init.configer.setSntext(MainWindow.mainWindow.getSntextField().getText());
                    Init.configer.setSncode(new String(MainWindow.mainWindow.getSncodeField().getText()));
                    Init.configer.setStandard(new String(MainWindow.mainWindow.getStandardField().getText()));
                    Init.configer.setPrintserver(new String(MainWindow.mainWindow.getPrintservercomboBox().getSelectedItem().toString()));
                    Init.configer.save();
                    String sntext = MainWindow.mainWindow.getSntextField().getText();//物料名称
                    String sncode = MainWindow.mainWindow.getSncodeField().getText();//物料编码
                    String standard = MainWindow.mainWindow.getStandardField().getText();//物料规格
                    String printserver = MainWindow.mainWindow.getPrintservercomboBox().getSelectedItem().toString();//打印机名称
                    String templatePath = MainWindow.mainWindow.getCustomTemplateFilePathField().getText();//模板文件路径
//                String CustomDataPath = MainWindow.mainWindow.getCustomDataPathField().getText();//要上传的文件路径
//                String path = CustomDataPath +"/"+UUID.randomUUID().toString()+".txt";
//                File file = new File(path);
//                if(!file.exists()){
//                    file.createNewFile();
//                }
//                String content = printserver+"|"+templatePath+"|sntext="+sntext+"|sncode="+sncode+"|standard="+standard+"|date=2018-5-25";
//                List<Printset> printsetList = new ArrayList<Printset>();
//                SimpleDateFormat sm = new SimpleDateFormat("yyyy-MM-dd");
//                Printset printset = new Printset();
//                printset.setSntext(sntext);
//                printset.setSncode(sncode);
//                printset.setStandard(standard);
//                printset.setPrintserver(printserver);
//                String realtemplatePath = templatePath.replace("\\","\\\\");
//                printset.setTemplatePath(realtemplatePath);
//                sm.format(new Date());
//                printsetList.add(printset);
//                MysqlConn.insert(printsetList);

                    File file1 = new File(templatePath);//编译好的报表路径
                    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(file1);
                    Map<String, Object> parameters = new  HashMap<String, Object>();
                    parameters.put("sntext", sntext);
                    parameters.put("standard", standard);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
                    if(jasperPrint.getPages().size()<1){
                        JOptionPane.showMessageDialog(null, "对不起,没有查询到要打印的数据\n请选择数据后再进行打印!",
                                "打印提示信息", JOptionPane.INFORMATION_MESSAGE);
//                        con.close();
                        return;
                    }
                    JasperViewer.setDefaultLookAndFeelDecorated(false);
                    //预览后直接退出程序
                    //JasperViewer.viewReport(jasperPrint);
                    //预览后不退出程序
                    JasperViewer.viewReport(jasperPrint,false);

                    //不预览直接打印
                    //JasperPrintManager.printReport(jasperPrint, true);
//                JasperViewer jasperViewer = new JasperViewer(jasperPrint);
//                jasperViewer.setVisible(true);






                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "打印预览失败！\n\n" + e1.getMessage(), "失败",
                            JOptionPane.ERROR_MESSAGE);
                    LogUitl.addElement("异常信息，message：" + e1.getMessage());
                }
            }

        });





        // 自定义浏览 模板文件
        MainWindow.mainWindow.getCustomTemplateButton().addActionListener(e -> {
            File beforeFile = new File(MainWindow.mainWindow.getCustomTemplateFilePathField().getText());
            JFileChooser fileChooser;

            if (beforeFile.exists()) {
                fileChooser = new JFileChooser(beforeFile);
            } else {
                fileChooser = new JFileChooser();
            }

            FileFilter filter = new FileNameExtensionFilter("*.jasper", "jasper");
            fileChooser.setFileFilter(filter);

            int approve = fileChooser.showOpenDialog(MainWindow.mainWindow.getSettingPanel());
            if (approve == JFileChooser.APPROVE_OPTION) {
                MainWindow.mainWindow.getCustomTemplateFilePathField().setText(fileChooser.getSelectedFile().getAbsolutePath());
            }

        });


        //自定义浏览 处理路径
//        MainWindow.mainWindow.getCustomDataPathButton().addActionListener(e -> {
//            File beforeFile = new File(MainWindow.mainWindow.getCustomDataPathField().getText());
//            JFileChooser fileChooser;
//
//            if (beforeFile.exists()) {
//                fileChooser = new JFileChooser(beforeFile);
//            } else {
//                fileChooser = new JFileChooser();
//            }
//
//            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//            int approve = fileChooser.showOpenDialog(MainWindow.mainWindow.getSettingPanel());
//            if (approve == JFileChooser.APPROVE_OPTION) {
//                MainWindow.mainWindow.getCustomDataPathField().setText(fileChooser.getSelectedFile().getAbsolutePath());
//            }
//
//        });


        //打印设置浏览 文件模板
        MainWindow.mainWindow.getTemplateButton().addActionListener(e -> {
            File beforeFile = new File(MainWindow.mainWindow.getTemplateFilePathField().getText());
            JFileChooser fileChooser;

            if (beforeFile.exists()) {
                fileChooser = new JFileChooser(beforeFile);
            } else {
                fileChooser = new JFileChooser();
            }

            FileFilter filter = new FileNameExtensionFilter("*.jasper",  "jasper");
            fileChooser.setFileFilter(filter);

            int approve = fileChooser.showOpenDialog(MainWindow.mainWindow.getSettingPanel());
            if (approve == JFileChooser.APPROVE_OPTION) {
                MainWindow.mainWindow.getTemplateFilePathField().setText(fileChooser.getSelectedFile().getAbsolutePath());
            }

        });

        //开始按钮
        MainWindow.mainWindow.getStartButton().addActionListener(e -> {
            if (checkBeforePush()) {
                int isPush = JOptionPane.showConfirmDialog(MainWindow.mainWindow.getSettingPanel(),
                        new StringBuilder("确定开始打印吗？\n\n打印模板文件：").
                                append(MainWindow.mainWindow.getTemplateFilePathField().getText()).
                                append("\n打印机：").append(MainWindow.mainWindow.getSettingPrintServercomboBox().getSelectedItem().toString()).toString(), "确认开始轮询打印？",
                        JOptionPane.INFORMATION_MESSAGE);
                if (isPush == JOptionPane.YES_OPTION) {
                    // 按钮状态
                    MainWindow.mainWindow.getStartButton().setEnabled(false);
                    MainWindow.mainWindow.getStopButton().setEnabled(true);
                    Thread puthread = new RunPushThread();
                    SystemUtil.pushexit = false;
                    puthread.start();
                    Thread printhread = new RunPrintThread();
                    SystemUtil.printexit = false;
                    printhread.start();

                }
            }

        });


        //停止按钮
        MainWindow.mainWindow.getStopButton().addActionListener(e -> {
            if (checkBeforePush()) {
                int isPush = JOptionPane.showConfirmDialog(MainWindow.mainWindow.getSettingPanel(),
                        new StringBuilder("结束打印线程？").toString(), "确认开始轮询打印？",
                        JOptionPane.INFORMATION_MESSAGE);
                if (isPush == JOptionPane.YES_OPTION) {
                    // 按钮状态
                    MainWindow.mainWindow.getStartButton().setEnabled(true);
                    MainWindow.mainWindow.getStopButton().setEnabled(false);
                    SystemUtil.pushexit = true;
                    SystemUtil.printexit = true;
                    LogUitl.addElement("打印线程停止~" );
                    LogUitl.addElement("输出PDF/JPG线程停止~" );

                }
            }

        });








        //打印设置浏览 处理路径
//        MainWindow.mainWindow.getDataPathButton().addActionListener(e -> {
//            File beforeFile = new File(MainWindow.mainWindow.getDataPathField().getText());
//            JFileChooser fileChooser;
//
//            if (beforeFile.exists()) {
//                fileChooser = new JFileChooser(beforeFile);
//            } else {
//                fileChooser = new JFileChooser();
//            }
//
//            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//
//            int approve = fileChooser.showOpenDialog(MainWindow.mainWindow.getSettingPanel());
//            if (approve == JFileChooser.APPROVE_OPTION) {
//                MainWindow.mainWindow.getDataPathField().setText(fileChooser.getSelectedFile().getAbsolutePath());
//            }
//
//        });



//        // mysql数据库-测试链接
//        MainWindow.mainWindow.getSettingTestDbLinkButton().addActionListener(e -> {
//            try {
//                DbUtilMySQL dbMySQL = DbUtilMySQL.getInstance();
//                String DBUrl = MainWindow.mainWindow.getMysqlUrlTextField().getText();
//                String DBName = MainWindow.mainWindow.getMysqlDatabaseTextField().getText();
//                String DBUser = MainWindow.mainWindow.getMysqlUserTextField().getText();
//                String DBPassword = new String(MainWindow.mainWindow.getMysqlPasswordField().getPassword());
//                Connection conn = dbMySQL.testConnection(DBUrl, DBName, DBUser, DBPassword);
//                if (conn == null) {
//                    JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "连接失败", "失败",
//                            JOptionPane.ERROR_MESSAGE);
//                } else {
//                    JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "连接成功！", "成功",
//                            JOptionPane.INFORMATION_MESSAGE);
//                }
//            } catch (Exception e1) {
//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "连接失败！\n\n" + e1.getMessage(), "失败",
//                        JOptionPane.ERROR_MESSAGE);
//                logger.error(e1);
//            }
//        });

//        // mysql数据库-保存
//        MainWindow.mainWindow.getSettingDbInfoSaveButton().addActionListener(e -> {
//            try {
//                Init.configer.setMysqlUrl(MainWindow.mainWindow.getMysqlUrlTextField().getText());
//                Init.configer.setMysqlDatabase(MainWindow.mainWindow.getMysqlDatabaseTextField().getText());
//                Init.configer.setMysqlUser(MainWindow.mainWindow.getMysqlUserTextField().getText());
//                Init.configer.setMysqlPassword(new String(MainWindow.mainWindow.getMysqlPasswordField().getPassword()));
//                Init.configer.save();
//
//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "保存成功！", "成功",
//                        JOptionPane.INFORMATION_MESSAGE);
//            } catch (Exception e1) {
//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "保存失败！\n\n" + e1.getMessage(), "失败",
//                        JOptionPane.ERROR_MESSAGE);
//                logger.error(e1);
//            }
//        });

//        // 外观-保存
//        MainWindow.mainWindow.getSettingAppearanceSaveButton().addActionListener(e -> {
//            try {
//                Init.configer.setTheme(MainWindow.mainWindow.getSettingThemeComboBox().getSelectedItem().toString());
//                Init.configer.setFont(MainWindow.mainWindow.getSettingFontNameComboBox().getSelectedItem().toString());
//                Init.configer.setFontSize(Integer.parseInt(MainWindow.mainWindow.getSettingFontSizeComboBox().getSelectedItem().toString()));
//                Init.configer.save();
//
//                Init.initTheme();
//                Init.initGlobalFont();
//                SwingUtilities.updateComponentTreeUI(MainWindow.frame);
//                SwingUtilities.updateComponentTreeUI(MainWindow.mainWindow.getTabbedPane());
//
//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "保存成功！\n\n部分细节将在下次启动时生效！\n\n", "成功",
//                        JOptionPane.INFORMATION_MESSAGE);
//            } catch (Exception e1) {
//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "保存失败！\n\n" + e1.getMessage(), "失败",
//                        JOptionPane.ERROR_MESSAGE);
//                logger.error(e1);
//            }
//        });

//        // 历史消息管理-全选
//        MainWindow.mainWindow.getMsgHisTableSelectAllButton().addActionListener(e -> new Thread(() -> {
//            toggleSelectAll();
//            DefaultTableModel tableModel = (DefaultTableModel) MainWindow.mainWindow.getMsgHistable()
//                    .getModel();
//            int rowCount = tableModel.getRowCount();
//            for (int i = 0; i < rowCount; i++) {
//                tableModel.setValueAt(selectAllToggle, i, 0);
//            }
//        }).start());

//        // 历史消息管理-删除
//        MainWindow.mainWindow.getMsgHisTableDeleteButton().addActionListener(e -> new Thread(() -> {
//            try {
//                DefaultTableModel tableModel = (DefaultTableModel) MainWindow.mainWindow.getMsgHistable()
//                        .getModel();
//                int rowCount = tableModel.getRowCount();
//
//                int selectedCount = 0;
//                for (int i = 0; i < rowCount; i++) {
//                    boolean isSelected = (boolean) tableModel.getValueAt(i, 0);
//                    if (isSelected) {
//                        selectedCount++;
//                    }
//                }
//
//                if (selectedCount == 0) {
//                    JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "请至少选择一个！", "提示",
//                            JOptionPane.INFORMATION_MESSAGE);
//                } else {
//                    int isDelete = JOptionPane.showConfirmDialog(MainWindow.mainWindow.getSettingPanel(), "确认删除？", "确认",
//                            JOptionPane.INFORMATION_MESSAGE);
//                    if (isDelete == JOptionPane.YES_OPTION) {
//                        Map<String, String[]> msgMap = Init.msgHisManager.readMsgHis();
//                        for (int i = 0; i < rowCount; ) {
//                            boolean delete = (boolean) tableModel.getValueAt(i, 0);
//                            if (delete) {
//                                String msgName = (String) tableModel.getValueAt(i, 1);
//                                if (msgMap.containsKey(msgName)) {
//                                    msgMap.remove(msgName);
//                                    File msgTemplateDataFile = new File(SystemUtil.configHome + "data"
//                                            + File.separator + "template_data" + File.separator + msgName + ".csv");
//                                    if (msgTemplateDataFile.exists()) {
//                                        msgTemplateDataFile.delete();
//                                    }
//                                }
//                                tableModel.removeRow(i);
//                                MainWindow.mainWindow.getMsgHistable().updateUI();
//                                i = 0;
//                                rowCount = tableModel.getRowCount();
//                                continue;
//                            } else {
//                                i++;
//                            }
//                        }
//                        Init.msgHisManager.writeMsgHis(msgMap);
//
//                        Init.initMsgTab(null);
//                    }
//                }
//            } catch (Exception e1) {
//                JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "删除失败！\n\n" + e1.getMessage(), "失败",
//                        JOptionPane.ERROR_MESSAGE);
//                logger.error(e1);
//            }
//        }).start());
//
//    }

        /**
         * 切换全选/全不选
         *
         * @return
         */
//    private static void toggleSelectAll() {
//        if (!selectAllToggle) {
//            selectAllToggle = true;
//        } else {
//            selectAllToggle = false;
//        }
//    }
    }




    private static boolean checkBeforePush() {

        if (StringUtils.isEmpty(MainWindow.mainWindow.getTemplateFilePathField().getText())) {
            JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "请先选择报表编译模板路径！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);

            return false;
        }

        return true;
    }


    //自定义操作按钮之前确认
    private static boolean checkBeforeCustomSet() {

        if (StringUtils.isEmpty(MainWindow.mainWindow.getSntextField().getText())) {
            JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "物料名称不能为空！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (StringUtils.isEmpty(MainWindow.mainWindow.getSncodeField().getText())) {
            JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "物料编码不能为空！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }
        if (StringUtils.isEmpty(MainWindow.mainWindow.getStandardField().getText())) {
            JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "物料规格不能为空！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return false;
        }

        if (StringUtils.isEmpty(MainWindow.mainWindow.getCustomTemplateFilePathField().getText())) {
            JOptionPane.showMessageDialog(MainWindow.mainWindow.getSettingPanel(), "请先选择报表编译模板路径！", "提示",
                    JOptionPane.INFORMATION_MESSAGE);

            return false;
        }

        return true;
    }

}

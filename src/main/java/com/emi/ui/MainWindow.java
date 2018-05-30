package com.emi.ui;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.apple.eawt.Application;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import listener.*;
import util.LogUitl;
import util.SystemUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow {
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JPanel indexPanel;
    private JPanel logPanel;
    private JTable logTable;
    private JScrollPane logScrollPane;
    private JLabel versionLable;
    private JLabel logoLable;
    private JLabel companyLable;
    private JPanel settingPanel;
    private JPanel setPrintPanel;
    private JPanel customRightPanel;
    private JTextField sntextField;
    private JTextField sncodeField;
    private JTextField standardField;
    private JComboBox printservercomboBox;
    private JButton customTjButton;
    private JButton toShowSncodeButton;
    private JTextField templateFilePathField;
    private JButton templateButton;
    private JComboBox settingPrintServercomboBox;
    private JTextField dataPathField;
    //    private JButton dataPathButton;
    private JButton startButton;
    private JButton stopButton;
    private JPanel customLeftPanel;
    private JTextField customTemplateFilePathField;
    private JButton customTemplateButton;
    private JLabel showSncode;
    private JTextField customDataPath;
    private JButton customDataPathButton;
    private JEditorPane editorPane1;
    private JProgressBar printTotalProgressBar;
    private JList logList;
    private JLabel wxLable;
    private JTable printThreadTable;

    public static MainWindow mainWindow;
    private static Log logger = LogFactory.get();

    public static JFrame frame;


    public static void main(String[] args) {

        // 初始化主题
        Init.initTheme();
        // 统一设置字体
        Init.initGlobalFont();
        // Windows系统状态栏图标
        frame = new JFrame(ConstantsUI.APP_NAME);
        frame.setIconImage(ConstantsUI.IMAGE_ICON);
        // Mac系统Dock图标
        if (SystemUtil.isMacOs()) {
            Application application = Application.getApplication();
            application.setDockIconImage(ConstantsUI.IMAGE_ICON);
        }

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); //得到屏幕的尺寸
        frame.setBounds((int) (screenSize.width * 0.1), (int) (screenSize.height * 0.08), (int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));

        Dimension preferSize = new Dimension((int) (screenSize.width * 0.8),
                (int) (screenSize.height * 0.8));
        frame.setPreferredSize(preferSize);

        mainWindow = new MainWindow();

        frame.setContentPane(mainWindow.mainPanel);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

        Init.initOthers();
        Init.initAllTab();

        // 添加事件监听
        AboutListener.addListeners();
//        HelpListener.addListeners();
//        PushHisListener.addListeners();
        SettingListener.addListeners();
//        MsgListener.addListeners();
//        MemberListener.addListeners();
//        PushListener.addListeners();
//        ScheduleListener.addListeners();
        TabListener.addListeners();
//        FramListener.addListeners();
        LogUitl.addElement("应用程序启动成功...");

    }


    //=====================================get&&set=====================================


    public JList getLogList() {
        return logList;
    }

    public void setLogList(JList logList) {
        this.logList = logList;
    }

    public JProgressBar getPrintTotalProgressBar() {
        return printTotalProgressBar;
    }

    public void setPrintTotalProgressBar(JProgressBar printTotalProgressBar) {
        this.printTotalProgressBar = printTotalProgressBar;
    }

    public JTable getPrintThreadTable() {
        return printThreadTable;
    }

    public void setPrintThreadTable(JTable printThreadTable) {
        this.printThreadTable = printThreadTable;
    }

    public JTextField getCustomDataPath() {
        return customDataPath;
    }

    public void setCustomDataPath(JTextField customDataPath) {
        this.customDataPath = customDataPath;
    }

    public JButton getCustomDataPathButton() {
        return customDataPathButton;
    }

    public void setCustomDataPathButton(JButton customDataPathButton) {
        this.customDataPathButton = customDataPathButton;
    }

    public JEditorPane getEditorPane1() {
        return editorPane1;
    }

    public void setEditorPane1(JEditorPane editorPane1) {
        this.editorPane1 = editorPane1;
    }


    public JPanel getSettingPanel() {
        return settingPanel;
    }

    public void setSettingPanel(JPanel settingPanel) {
        this.settingPanel = settingPanel;
    }

    public JPanel getSetPrintPanel() {
        return setPrintPanel;
    }

    public void setSetPrintPanel(JPanel setPrintPanel) {
        this.setPrintPanel = setPrintPanel;
    }

    public JPanel getCustomRightPanel() {
        return customRightPanel;
    }

    public void setCustomRightPanel(JPanel customRightPanel) {
        this.customRightPanel = customRightPanel;
    }

    public JTextField getSntextField() {
        return sntextField;
    }

    public void setSntextField(JTextField sntextField) {
        this.sntextField = sntextField;
    }

    public JTextField getSncodeField() {
        return sncodeField;
    }

    public void setSncodeField(JTextField sncodeField) {
        this.sncodeField = sncodeField;
    }

    public JTextField getStandardField() {
        return standardField;
    }

    public void setStandardField(JTextField standardField) {
        this.standardField = standardField;
    }

    public JComboBox getPrintservercomboBox() {
        return printservercomboBox;
    }

    public void setPrintservercomboBox(JComboBox printservercomboBox) {
        this.printservercomboBox = printservercomboBox;
    }

    public JButton getCustomTjButton() {
        return customTjButton;
    }

    public void setCustomTjButton(JButton customTjButton) {
        this.customTjButton = customTjButton;
    }

    public JButton getToShowSncodeButton() {
        return toShowSncodeButton;
    }

    public void setToShowSncodeButton(JButton toShowSncodeButton) {
        this.toShowSncodeButton = toShowSncodeButton;
    }

    public JTextField getTemplateFilePathField() {
        return templateFilePathField;
    }

    public void setTemplateFilePathField(JTextField templateFilePathField) {
        this.templateFilePathField = templateFilePathField;
    }

    public JButton getTemplateButton() {
        return templateButton;
    }

    public void setTemplateButton(JButton templateButton) {
        this.templateButton = templateButton;
    }

    public JComboBox getSettingPrintServercomboBox() {
        return settingPrintServercomboBox;
    }

    public void setSettingPrintServercomboBox(JComboBox settingPrintServercomboBox) {
        this.settingPrintServercomboBox = settingPrintServercomboBox;
    }

    public JTextField getDataPathField() {
        return dataPathField;
    }

    public void setDataPathField(JTextField dataPathField) {
        this.dataPathField = dataPathField;
    }

//    public JButton getDataPathButton() {
//        return dataPathButton;
//    }
//
//    public void setDataPathButton(JButton dataPathButton) {
//        this.dataPathButton = dataPathButton;
//    }

    public JButton getStartButton() {
        return startButton;
    }

    public void setStartButton(JButton startButton) {
        this.startButton = startButton;
    }

    public JButton getStopButton() {
        return stopButton;
    }

    public void setStopButton(JButton stopButton) {
        this.stopButton = stopButton;
    }

    public JPanel getCustomLeftPanel() {
        return customLeftPanel;
    }

    public void setCustomLeftPanel(JPanel customLeftPanel) {
        this.customLeftPanel = customLeftPanel;
    }

    public JTextField getCustomTemplateFilePathField() {
        return customTemplateFilePathField;
    }

    public void setCustomTemplateFilePathField(JTextField customTemplateFilePathField) {
        this.customTemplateFilePathField = customTemplateFilePathField;
    }

    public JButton getCustomTemplateButton() {
        return customTemplateButton;
    }

    public void setCustomTemplateButton(JButton customTemplateButton) {
        this.customTemplateButton = customTemplateButton;
    }

    public JLabel getShowSncode() {
        return showSncode;
    }

    public void setShowSncode(JLabel showSncode) {
        this.showSncode = showSncode;
    }

    public JLabel getLogoLable() {
        return logoLable;
    }

    public void setLogoLable(JLabel logoLable) {
        this.logoLable = logoLable;
    }

    public JLabel getCompanyLable() {
        return companyLable;
    }

    public void setCompanyLable(JLabel companyLable) {
        this.companyLable = companyLable;
    }

    public JLabel getVersionLable() {
        return versionLable;
    }

    public void setVersionLable(JLabel versionLable) {
        this.versionLable = versionLable;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setMainPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }

    public void setTabbedPane(JTabbedPane tabbedPane) {
        this.tabbedPane = tabbedPane;
    }

    public JPanel getIndexPanel() {
        return indexPanel;
    }

    public void setIndexPanel(JPanel indexPanel) {
        this.indexPanel = indexPanel;
    }

    public JPanel getLogPanel() {
        return logPanel;
    }

    public void setLogPanel(JPanel logPanel) {
        this.logPanel = logPanel;
    }

    public JTable getLogTable() {
        return logTable;
    }

    public void setLogTable(JTable logTable) {
        this.logTable = logTable;
    }

    public static MainWindow getMainWindow() {
        return mainWindow;
    }

    public static void setMainWindow(MainWindow mainWindow) {
        MainWindow.mainWindow = mainWindow;
    }

    public static Log getLogger() {
        return logger;
    }

    public static void setLogger(Log logger) {
        MainWindow.logger = logger;
    }

    public static JFrame getFrame() {
        return frame;
    }

    public static void setFrame(JFrame frame) {
        MainWindow.frame = frame;
    }


    public JScrollPane getLogScrollPane() {
        return logScrollPane;
    }

    public void setLogScrollPane(JScrollPane logScrollPane) {
        this.logScrollPane = logScrollPane;
    }


    {
// GUI initializer generated by IntelliJ IDEA GUI Designer
// >>> IMPORTANT!! <<<
// DO NOT EDIT OR ADD ANY CODE HERE!
        $$$setupUI$$$();
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayoutManager(1, 1, new Insets(8, 0, 0, 0), -1, -1));
        tabbedPane = new JTabbedPane();
        mainPanel.add(tabbedPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, new Dimension(200, 200), null, 0, false));
        indexPanel = new JPanel();
        indexPanel.setLayout(new GridLayoutManager(4, 1, new Insets(10, 10, 10, 10), -1, -1));
        indexPanel.setToolTipText("");
        tabbedPane.addTab("首页", indexPanel);
        logoLable = new JLabel();
        logoLable.setIcon(new ImageIcon(getClass().getResource("/icon/logo.jpg")));
        logoLable.setText("");
        indexPanel.add(logoLable, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        versionLable = new JLabel();
        versionLable.setText("Label");
        indexPanel.add(versionLable, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        companyLable = new JLabel();
        companyLable.setText("https://www.baidu.com");
        indexPanel.add(companyLable, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_SOUTH, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        wxLable = new JLabel();
        wxLable.setIcon(new ImageIcon(getClass().getResource("/icon/wx.jpg")));
        wxLable.setText("");
        indexPanel.add(wxLable, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        settingPanel = new JPanel();
        settingPanel.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        settingPanel.setEnabled(true);
        tabbedPane.addTab("设置", settingPanel);
        setPrintPanel = new JPanel();
        setPrintPanel.setLayout(new GridLayoutManager(5, 5, new Insets(0, 0, 0, 0), -1, -1));
        settingPanel.add(setPrintPanel, new GridConstraints(1, 0, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        setPrintPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "打印设置"));
        final JLabel label1 = new JLabel();
        label1.setText("报表编译路径");
        setPrintPanel.add(label1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateFilePathField = new JTextField();
        setPrintPanel.add(templateFilePathField, new GridConstraints(0, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label2 = new JLabel();
        label2.setText("设置默认打印机");
        setPrintPanel.add(label2, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        startButton = new JButton();
        startButton.setIcon(new ImageIcon(getClass().getResource("/icon/run@2x.png")));
        startButton.setText("开始");
        setPrintPanel.add(startButton, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        stopButton = new JButton();
        stopButton.setIcon(new ImageIcon(getClass().getResource("/icon/suspend.png")));
        stopButton.setText("停止");
        setPrintPanel.add(stopButton, new GridConstraints(4, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        templateButton = new JButton();
        templateButton.setText("浏览");
        setPrintPanel.add(templateButton, new GridConstraints(0, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        setPrintPanel.add(spacer1, new GridConstraints(3, 3, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        setPrintPanel.add(spacer2, new GridConstraints(4, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        settingPrintServercomboBox = new JComboBox();
        setPrintPanel.add(settingPrintServercomboBox, new GridConstraints(1, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label3 = new JLabel();
        label3.setText("总进度：");
        setPrintPanel.add(label3, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        printTotalProgressBar = new JProgressBar();
        setPrintPanel.add(printTotalProgressBar, new GridConstraints(2, 1, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customRightPanel = new JPanel();
        customRightPanel.setLayout(new GridLayoutManager(6, 5, new Insets(0, 0, 0, 0), -1, -1));
        settingPanel.add(customRightPanel, new GridConstraints(0, 1, 1, 2, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        customRightPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "自定义设置"));
        final JLabel label4 = new JLabel();
        label4.setText("物料名称");
        customRightPanel.add(label4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sntextField = new JTextField();
        customRightPanel.add(sntextField, new GridConstraints(0, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        standardField = new JTextField();
        customRightPanel.add(standardField, new GridConstraints(2, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label5 = new JLabel();
        label5.setText("物料规格");
        customRightPanel.add(label5, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        printservercomboBox = new JComboBox();
        customRightPanel.add(printservercomboBox, new GridConstraints(3, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JLabel label6 = new JLabel();
        label6.setText("设置打印机");
        customRightPanel.add(label6, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(2, 3, new Insets(0, 0, 0, 0), -1, -1));
        customRightPanel.add(panel1, new GridConstraints(5, 2, 1, 3, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        customTjButton = new JButton();
        customTjButton.setText("提交打印队列");
        panel1.add(customTjButton, new GridConstraints(0, 2, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel1.add(spacer3, new GridConstraints(1, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        toShowSncodeButton = new JButton();
        toShowSncodeButton.setText("打印预览");
        panel1.add(toShowSncodeButton, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel1.add(spacer4, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JLabel label7 = new JLabel();
        label7.setText("报表编译路径");
        customRightPanel.add(label7, new GridConstraints(4, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        sncodeField = new JTextField();
        customRightPanel.add(sncodeField, new GridConstraints(1, 1, 1, 4, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        final JLabel label8 = new JLabel();
        label8.setText("物料编码");
        customRightPanel.add(label8, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customTemplateButton = new JButton();
        customTemplateButton.setText("选择");
        customRightPanel.add(customTemplateButton, new GridConstraints(4, 4, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        customTemplateFilePathField = new JTextField();
        customRightPanel.add(customTemplateFilePathField, new GridConstraints(4, 1, 1, 3, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_FIXED, null, new Dimension(150, -1), null, 0, false));
        customLeftPanel = new JPanel();
        customLeftPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        settingPanel.add(customLeftPanel, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        customLeftPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "条码预览"));
        showSncode = new JLabel();
        showSncode.setIcon(new ImageIcon(getClass().getResource("/icon/cnaidc.png")));
        showSncode.setText("");
        customLeftPanel.add(showSncode, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        logPanel = new JPanel();
        logPanel.setLayout(new GridLayoutManager(1, 1, new Insets(0, 0, 0, 0), -1, -1));
        tabbedPane.addTab("日志", logPanel);
        logScrollPane = new JScrollPane();
        logPanel.add(logScrollPane, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        logList = new JList();
        logScrollPane.setViewportView(logList);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return mainPanel;
    }
}

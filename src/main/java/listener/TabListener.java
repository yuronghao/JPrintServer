package listener;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;

/**
 * tab事件监听
 * Created by rememberber(https://github.com/rememberber) on 2017/6/21.
 */
public class TabListener {

    private static final Log logger = LogFactory.get();

    public static void addListeners() {
        // 暂时停止使用，仅留作demo，日后需要时再使用
//        MainWindow.mainWindow.getTabbedPane().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                super.mouseClicked(e);
//                int index = MainWindow.mainWindow.getTabbedPane().getSelectedIndex();
//                switch (index) {
//                    case 6:
//                        Init.initPushHisTab();
//                        break;
//                    case 4:
//                        MainWindow.mainWindow.setPushMsgName(MainWindow.mainWindow.getMsgNameField().getText());
//
//                        if (PushData.allUser != null && PushData.allUser.size() > 0) {
//                            PushListener.refreshPushInfo();
//                        }
//
//                    default:
//                        break;
//                }
//            }
//        });
    }
}

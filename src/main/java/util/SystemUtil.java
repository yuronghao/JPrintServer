package util;

import java.io.File;

/**
 * 系统工具
 */
public class SystemUtil {
    public static  volatile boolean pushexit = false;
    public static  volatile boolean printexit = false;
    public static String osName = System.getProperty("os.name");
    public static String configHome = System.getProperty("user.home") + File.separator + ".jprintserver"
            + File.separator;

    public static boolean isMacOs() {
        if (osName.contains("Mac")) {
            return true;
        } else {
            return false;
        }
    }



    public static void main(String[] args){
            System.out.println(SystemUtil.configHome);

    }
}




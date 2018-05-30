package util;

import bean.Printset;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static cn.hutool.db.DbUtil.close;

public class MysqlConn {
    public static  void insert(List<Printset> list){
        Connection conn = getConn();
        int i = 0;
        System.out.println(1111);
        StringBuffer sb = new StringBuffer(" INSERT into printset(sntext,sncode,printserver,standard,templatePath) VALUES ");
        if(list != null && list.size()>0){
            for(int j = 0 ;j<list.size();j++){
                Printset printset = list.get(j);
                if( j == list.size() -1){
                    sb.append(" ('"+printset.getSntext()+"','"+printset.getSncode()+"','"+printset.getPrintserver()+"','"+printset.getStandard()+"','"+printset.getTemplatePath()+"') ");
                }else{
                    sb.append(" ('"+printset.getSntext()+"','"+printset.getSncode()+"','"+printset.getPrintserver()+"','"+printset.getStandard()+"','"+printset.getTemplatePath()+"') ");
                    sb.append(" , ");
                }
            }

        }

        String sql = sb.toString();
        System.out.println(sql);
        PreparedStatement pstmt;
        try {
            pstmt = conn.prepareStatement(sql);
            i = pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(i);

    }

    public static <T> List<T> executeList(Class<T> cls, String sql) {
              List<T> list = new ArrayList<T>();
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                 try {
                      con = getConn();
                       ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
                        while (rs.next()) {
                                 T obj = executeResultSet(cls, rs);
                                 list.add(obj);
                             }
                   } catch (Exception e) {
                         e.printStackTrace();
                     } finally {
                         close(con, ps, rs);
                     }
                 return list;
            }
    //万能更新
    public static boolean update(Object ob){
        boolean b = false;
        Connection conn = getConn();
        PreparedStatement ps = null;
        Class c1 = ob.getClass();
        Field[] fi = c1.getDeclaredFields();
        StringBuffer sb = new StringBuffer();
        sb.append("update ").append(c1.getSimpleName()).append(" set ");
        for(int i = 1; i<fi.length;i++){
            sb.append(fi[i].getName());
            sb.append(" = ? ");
            if(i!= fi.length -1){
                sb.append(" , ");
            }
        }
        sb.append(" where ");
        sb.append(fi[0].getName()).append(" =?");
        try{
            System.out.println(sb.toString());
            ps = conn.prepareStatement(sb.toString());
            for(int i=1;i<fi.length ; i++){
                fi[i].setAccessible(true);
                ps.setObject(i, fi[i].get(ob));
            }
            fi[0].setAccessible(true);
            ps.setObject(fi.length, fi[0].get(ob));
            int a = ps.executeUpdate();
            if(a>0){
                b=true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return b;
    }




    private static <T> T executeResultSet(Class<T> cls, ResultSet rs)
            throws InstantiationException, IllegalAccessException, SQLException {
               T obj = cls.newInstance();
                ResultSetMetaData rsm = rs.getMetaData();
                 int columnCount = rsm.getColumnCount();
                 // Field[] fields = cls.getFields();
                 Field[] fields = cls.getDeclaredFields();
                 for (int i = 0; i < fields.length; i++) {
                        Field field = fields[i];
                         String fieldName = field.getName();
                        for (int j = 1; j <= columnCount; j++) {
                            String columnName = rsm.getColumnName(j);
                               if (fieldName.equalsIgnoreCase(columnName)) {
                                         Object value = rs.getObject(j);
                                         field.setAccessible(true);
                                        field.set(obj, value);
                                        break;
                                     }
                            }
                     }
                 return obj;
             }



    public static Connection getConn() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/printserver?useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&serverTimezone=Asia/Shanghai";
        String username = "root";
        String password = "";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}

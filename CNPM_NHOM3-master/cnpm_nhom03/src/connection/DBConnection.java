package connection;

import java.sql.*;

public class DBConnection {

    static Connection con;
    static Connection connection = null;


    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cnpm_03?useUnicode=true&characterEncoding=utf-8", "root", "rootuser");
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            return connection;
        }
    }
}

//    static String host = "db4free.net:3306";
//    private static final String db = "cnpm_nhom03";
//    //    private static final String url = "jdbc:mysql://" + host + "/" + db + "?useUnicode=true&characterEncoding=utf-8";
//    private static final String url = "jdbc:mysql://" + host + "/" + db + "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false";
//    private static final String user = "nhom03";
//    private static final String password = "Hai2711@";
//
//    public static Connection getConnection() {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//            connection = DriverManager.getConnection(url, user, password);
//        } catch (ClassNotFoundException | SQLException e) {
//            System.out.println("ero");
//            return connection;
//        }
//        System.out.println("=======");
//        return connection;
//    }
//
//
//    public static void main(String[] args) {
//        DBConnection.getConnection();
//    }
//}

//localhost
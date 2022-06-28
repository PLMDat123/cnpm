package dao;

import connection.DBConnection;
import model.Accounts;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountDao {

    // Đăng nhâp
    public boolean checkAccount(String username, String password) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM account WHERE username = '" + username + "' AND password ='" + password + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    public static boolean checkIDFB(String ID) {
        Connection con = DBConnection.getConnection();
        String sql = "SELECT * FROM acc WHERE  facebookID = ? ";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }
    public static boolean checkIDGG(String ID) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM acc WHERE  googleID = ? ";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, ID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }

    public Accounts checkLogin(String username, String password) {
        Connection connection = DBConnection.getConnection();
        String sql = "SELECT * FROM account WHERE username = '" + username + "' AND password ='" + password + "'";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Accounts accountsBean = new Accounts();
                accountsBean.setId(rs.getInt("id"));
                accountsBean.setUserName(rs.getString("username"));
                accountsBean.setPassword(rs.getString("password"));
                accountsBean.setNumberPhone(rs.getString("numberphone"));
                accountsBean.setEmail(rs.getString("email"));
                accountsBean.setAddress(rs.getString("address"));
                return accountsBean;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean InsertFBToDB(String ID, String name) {
        PreparedStatement stm = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "INSERT INTO acc (facebookID,username) VALUES (?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, ID);
            stm.setString(2, name);
            int row = stm.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return false;
    }
    public static boolean InsertGGToDB(String email ,String googleID) {
        PreparedStatement stm = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "INSERT INTO acc (email, googleID) VALUES (?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, email);
            stm.setString(2, googleID);
            int row = stm.executeUpdate();
            if (row > 0) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stm != null) {
                    stm.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

        }
        return false;
    }

    // Đăng ký
    // Kiểm tra chuỗi hợp lệ:
    public boolean kiemTraChuoi(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }

    //Mã hóa mật khẩu MD5:
//    public static String maHoaMD5(String str) {
//        byte[] defaultBytes = str.getBytes();
//        try {
//            MessageDigest algorithm = MessageDigest.getInstance("MD5");
//            algorithm.reset();
//            algorithm.update(defaultBytes);
//            byte messageDigest[] = algorithm.digest();
//            StringBuffer hexString = new StringBuffer();
//            for (int i = 0; i < messageDigest.length; i++) {
//                String hex = Integer.toHexString(0xFF & messageDigest[i]);
//                if (hex.length() == 1) {
//                    hexString.append('0');
//                }
//                hexString.append(hex);
//            }
//            str = hexString + "";
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
//        return str;
//    }

    // 5.2.2. Kiểm tra tài khoản đã tồn tại hay chưa thông qua Username
    public boolean kiemTraTonTai_UN(String userName) {
        String sql = "SELECT * FROM account WHERE username = '" + userName + "'";
//        String sql = "SELECT * FROM account WHERE username = '" + userName + "' or email = '" + email + "'";
        try {
            Connection cons = DBConnection.getConnection();
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            cons.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean kiemTraTonTai_EM(String email) {
        String sql = "SELECT * FROM account WHERE email = '" + email + "'";
        try {
            Connection cons = DBConnection.getConnection();
            PreparedStatement ps = cons.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return true;
            }
            cons.close();
        } catch (SQLException ex) {
            Logger.getLogger(AccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    // 5.2.3. Thêm tài khoản vào Database
    public void themTaiKhoan(Accounts taiKhoan) {
        String sql = "INSERT INTO account VALUE (?,?,?,?,?,?,?)";
        try {
            Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, taiKhoan.getId());
            pst.setString(2, taiKhoan.getUserName());
            pst.setString(3, taiKhoan.getEmail());
            pst.setString(4, taiKhoan.getNumberPhone());
            pst.setString(5, taiKhoan.getAddress());
            pst.setString(6, taiKhoan.getPassword());
            pst.setInt(7, taiKhoan.getActive());
            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Cập nhật, chỉnh sửa thông tin cá nhân
    // Chỉnh sửa tên đăng nhập, email, số điện thoại, địa chỉ
    public void updateUser(Accounts accounts) {
        PreparedStatement stm;
        Connection con;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE `account` SET  `username` = ?, `email` = ?, `numberphone` = ?, `address` = ? WHERE `id` = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, accounts.getUserName());
            stm.setString(2, accounts.getEmail());
            stm.setString(3, accounts.getNumberPhone());
            stm.setString(4, accounts.getAddress());
            stm.setInt(5, accounts.getId());

            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Đổi mật khẩu
    public void updatePassword(Accounts accounts) {
        PreparedStatement stm = null;
        Connection con = null;
        try {
            con = DBConnection.getConnection();
            String sql = "UPDATE `account` SET  `password` = ? WHERE `id` = ?";
            stm = con.prepareStatement(sql);
            stm.setString(1, accounts.getPassword());
            stm.setInt(2, accounts.getId());
            stm.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

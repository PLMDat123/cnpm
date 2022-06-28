package controller;

import dao.AccountDao;
import model.Accounts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UpdateInfoUser")
public class UpdateInfoUser extends HttpServlet {
    private AccountDao accountDao = new AccountDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // Lấy thông tin người dùng nhập vào
        String uname = request.getParameter("user_name");
        String email = request.getParameter("user_email");
        String phone = request.getParameter("user_phone");
        String address = request.getParameter("user_address");

        // Nhận thông tin vào phiên người dùng
        HttpSession session = request.getSession();
        Accounts account = (Accounts) session.getAttribute("account");
        account.setUserName(uname);
        account.setEmail(email);
        account.setNumberPhone(phone);
        account.setAddress(address);

        // Kiểm tra thông tin nhập vào
        checkInfoInput(account, request, response);


    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void checkInfoInput(Accounts account, HttpServletRequest request, HttpServletResponse response) {
        String userName = account.getUserName();
        String email = account.getEmail();
        String phone = account.getNumberPhone();
        String regex_email = "^[A-Za-z0-9]+([_\\.\\-]?[A-Za-z0-9])*@[A-Za-z0-9]+([\\.\\-]?[A-Za-z0-9]+)*(\\.[A-Za-z]+)+$";
        String regex_phone = "^[0-9]+$";
        String userName_error = "";
        String email_error = "";
        String phone_error = "";

        String success = "✔ Cập nhật thông tin thành công!";

        // Kiểm tra tên đăng nhập nhập vào
        if (userName.equals("")) {
            userName_error = "✖ Tên đăng nhập không được bỏ trống!";
        } else if (accountDao.kiemTraTonTai_UN(userName) == true) {
            userName_error = "✖ Tên đăng nhập đã tồn tại!";
        }
        if (userName_error.length() > 0) {
            request.setAttribute("userName_error", userName_error);
        }

        // Kiểm tra email nhập vào
        if (email.equals("")) {
            email_error = "✖ Địa chỉ Email không được bỏ trống!";
        } else if (accountDao.kiemTraChuoi(regex_email, email) == false) {
            email_error = "✖ Sai định dạng Email!";
        }
        if (email_error.length() > 0) {
            request.setAttribute("email_error", email_error);
        }

        // Kiểm tra số điện thoại nhập vào
//        if (!phone.equals("")) {
//            if (!accountDao.kiemTraChuoi(regex_phone, phone)) {
//                phone_error = "✖ Chỉ được nhập số!";
//            } else if (phone.length() > 15 || phone.length() < 8) {
//                phone_error = "✖ Độ dài số điện thoại không hợp lệ!";
//            }
//        }
//        if (phone_error.length() > 0) {
//            request.setAttribute("phone_error", phone_error);
//        }

        // Cập nhật database
        try {
            if (userName_error.length() == 0 && email_error.length() == 0) {
                accountDao.updateUser(account);
                request.setAttribute("success", success);
                request.getRequestDispatcher("/infoUser.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("/infoUser.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
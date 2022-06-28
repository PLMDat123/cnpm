package controller;

import dao.AccountDao;
import model.Accounts;
import utils.UtilsPath;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ktMail")
public class CheckMail extends HttpServlet {
    AccountDao accountDao = new AccountDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
        String url = "";
        HttpSession session = request.getSession();
        // 6.3 Gọi lại mã xác nhận được tạo random đã gửi mail
        String maXN = (String) session.getAttribute("maxn");
        // 6.2 Lấy mã người dùng nhập vào để xác nhận ở xacnha.jsp
        String maxacnhan = request.getParameter("maxacnhan");
        // Account là users đã được người dùng đã nhập ở trang đăng ký
        Accounts user = (Accounts) session.getAttribute("users");
        // 7. Lấy mã được tạo random gửi tới người dùng so sánh với mã người dùng nhập vào nếu bằng thì thêm
        //    thì thêm user đó vào database
        if (maXN.equals(maxacnhan)) {
            // user phải khác null
            if (user != null) {
                // 7.2 thực hiện thêm tài khoản
                accountDao.themTaiKhoan(user);
                // Và đi đến trang Trang chủ
                response.sendRedirect(UtilsPath.getPath("index.jsp"));
            } else {
                // quay lại trang đăng ký nếu không có dữ liệu user
                response.sendRedirect(UtilsPath.getPath("register.jsp"));
            }

        } else {
            // 7.1 Ở lại trang xacnhan nếu 2 mã không giống nhau
            url = "/xacnhan.jsp";
            RequestDispatcher rd = getServletContext().getRequestDispatcher(url);
            rd.forward(request, response);

        }

    }


}

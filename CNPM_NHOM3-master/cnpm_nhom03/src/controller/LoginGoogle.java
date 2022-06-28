package controller;

import dao.AccountDao;
import model.GooglePojo;
import utils.GoogleUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login_google")
public class LoginGoogle extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginGoogle() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // lấy code từ trang login
        String code = request.getParameter("code");
        // kiểm tra code có bị null hoặc rỗng ?
        if (code == null || code.isEmpty()) {
            // quay lại trang login
            RequestDispatcher dis = request.getRequestDispatcher("login.jsp");
            dis.forward(request, response);
        } else {
            // lấy mã code sau đó đổi thành access Token
            // Để truy cập lấy thông tin trong các tài khoản gg
            String accessToken = GoogleUtils.getToken(code);
            GooglePojo googlePojo = GoogleUtils.getUserInfo(accessToken);
            request.setAttribute("id", googlePojo.getId());
            request.setAttribute("name", googlePojo.getName());
            request.setAttribute("email", googlePojo.getEmail());

            // kiểm tra đã có thông tin trong database
            if(AccountDao.checkIDGG(googlePojo.getId()) == true) {

            }else {
                // Nếu chưa thì add vô database
               AccountDao.InsertGGToDB(googlePojo.getEmail(),googlePojo.getId());
            }

            HttpSession session = request.getSession();
            session.setAttribute("userGG" , googlePojo);
            response.sendRedirect(request.getContextPath() +"/HomePage");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
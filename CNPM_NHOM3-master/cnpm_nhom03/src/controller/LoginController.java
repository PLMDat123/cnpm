package controller;

import dao.AccountDao;
import model.Accounts;
import utils.VerifyUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    AccountDao accountDao = new AccountDao();

    protected void requestProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        // lấy thông tin user name và password
        String email = request.getParameter("email_1");
        String pass = request.getParameter("password");

        boolean valid = true;
        String errorString = "";

        // Thông báo lỗi
        String err = "";
        if (email == "" || pass == "") {
            err = "✖ Chưa nhập tài khoản hoặc mật khẩu!";
        } else {
            if(accountDao.checkAccount(email, pass) == false) {
                err = "✖ Sai tên tài khoản hoặc mật khẩu!";
            }
        }
        if (err.length() > 0) {
            request.setAttribute("err", err);
        }
        request.setAttribute("email", email);
        request.setAttribute("pass", pass);

        String path = "/login.jsp";

        // Kiểm tra tên đăng nhập và mật khẩu
        Accounts accounts = new Accounts();
        accounts = accountDao.checkLogin(email, pass);

        Accounts accountAdmin = new Accounts();
        accountAdmin = accountDao.checkLogin(email, pass);
        try {
            if(valid){
                // lấy thông tin captcha
                String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
                System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
                // Verify CAPTCHA.
                valid = VerifyUtils.verify(gRecaptchaResponse);
                if (!valid) {
                    errorString = "✖ CaptCha không hợp lệ!";
                }
            }
            // kiểm tra đã check captcha ?
            if (!valid) {
                // Chưa check
                request.setAttribute("errorString", errorString);
                // trả lại trang login.jsp
                request.getRequestDispatcher("login.jsp").forward(request,response);
                return;
            } else {
                // kiểm tra có đăng nhập hay chưa
                if(accounts == null && err.length() > 0) {
                    request.getRequestDispatcher("login.jsp").forward(request,response);

                }
                // kiểm tra quyền quản trị
                if (accounts != null   ) { //&& accounts.getType().equals("user")
                    HttpSession session = request.getSession();
                    session.setAttribute("account", accounts);
                    session.setAttribute("nodisplay","display:none;");
                    response.sendRedirect(request.getContextPath() +"/HomePage");
                } else if (accountAdmin != null && accountAdmin.getActive() == 2) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accountAdmin", accountAdmin);
                    session.setAttribute("nodisplay","display:none;");
                    response.sendRedirect(request.getContextPath()+"/Admin");
//                response.sendRedirect(request.getContextPath() +"/Admin?id=0&page=page1");
                }
            }
            }
        catch (Exception e) {
            e.printStackTrace();
        }

        //captcha

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // log out ra khỏi ra trang web
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute("account");
            session.removeAttribute("userFB");
            session.removeAttribute("userGG");
            session.removeAttribute("accountAdmin");
            session.setAttribute("nodisplay","display:block;");
            response.sendRedirect(request.getContextPath() +"/HomePage");
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestProcess(request, response);
    }

}
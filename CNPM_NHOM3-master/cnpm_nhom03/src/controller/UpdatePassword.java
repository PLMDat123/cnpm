package controller;

import dao.AccountDao;
import dao.SendMail;
import model.Accounts;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/UpdatePassword")
public class UpdatePassword extends HttpServlet {
    private AccountDao accountDao = new AccountDao();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        Accounts account = (Accounts) session.getAttribute("account");

        checkPWInput(account, request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    private void checkPWInput(Accounts account, HttpServletRequest request, HttpServletResponse response) {
        String regex_password = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#\\$%\\^&\\*]).{8,}$";
        String error = "";
        String success = "✔ Đổi mật khẩu thành công!";
        String explain = "✖ Mật khẩu phải có ít nhất 8 kí tự, 1 kí tự thường(a-z), 1 kí tự in hoa(A-Z), 1 kí tự số(0-9) và 1 kí tự đặc biệt(!@#$%^&*).";

        // Lấy dữ liệu nhập vào
        String currentPW_input = request.getParameter("user_currentPassword");
        String newPW_input = request.getParameter("user_newPassword");
        String confirmPW_input = request.getParameter("user_confirmPassword");

        // Lấy thông tin phiên người dùng hiện tại
        String currentPW = account.getPassword();
        String uname = account.getUserName();
        String email = account.getEmail();

        // Xử lý các trường hợp nhập vào
        try {
            // Nếu cả 3 trường đều không null
            if (!currentPW_input.equals("") && !newPW_input.equals("") && !confirmPW_input.equals("")) {
                if (currentPW_input.equals(currentPW)) {  // Nếu mật khẩu hiện tại nhập vào chính xác
                    if (newPW_input.equals(currentPW_input)) {  // Nếu mật khẩu mới nhập vào giống mật khẩu hiện tại
                        error = "✖ Mật khẩu mới giống mật khẩu hiện tại!";
                        request.setAttribute("error", error);
                        request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                    } else {  // Nếu mật khẩu mới nhập vào khác mật khẩu hiện tại
                        if (confirmPW_input.equals(newPW_input)) {  // nếu xác nhận mật khẩu đúng
                            // Kiểm tra độ mạnh của mật khẩu mới
                            // Nếu mật khẩu mới không đủ mạnh
                            if (accountDao.kiemTraChuoi(regex_password, newPW_input) == false) {
                                error = "✖ Mật khẩu mới không đủ mạnh!";
                                request.setAttribute("error", error);
                                request.setAttribute("explain", explain);
                                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                            } else {  // Nếu mật khẩu mới đủ mạnh thì lưu và cập nhật database
                                account.setPassword(newPW_input);
                                accountDao.updatePassword(account);
                                SendMail.sendMail(email, "Coffee Blend", "Chào " + uname + "! Tài khoản của bạn đã đổi mật khẩu thành công!");
                                request.setAttribute("success", success);
                                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                            }
                        } else {  // Nếu xác nhận mật khẩu không đúng
                            error = "✖ Xác nhận mật khẩu không đúng!";
                            request.setAttribute("error", error);
                            request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                        }
                    }
                } else {  // Nếu mật khẩu hiện tại nhập vào không chính xác
                    error = "✖ Mật khẩu hiện tại không đúng!";
                    request.setAttribute("error", error);
                    request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                }
            } else { // Nếu có ít nhất 1 trường null
                error = "✖ Các trường không được bỏ trống!";
                request.setAttribute("error", error);
                request.getRequestDispatcher("changePassword.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

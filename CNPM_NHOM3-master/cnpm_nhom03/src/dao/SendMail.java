package dao;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {

    public static boolean sendMail(String to, String subject, String text) {
        Properties props = new Properties();
//        props.put("mail.smtp.auth", "true");
//        props.put("mail.smtp.starttls.enable", "true");
//        props.put("mail.smtp.host", "smtp.gmail.com");
//        props.put("mail.smtp.port", "465");

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); //TLS

        Session session = Session.getInstance(props, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication("phuongdung9504@gmail.com", "manhlacuccutheo");
                return new PasswordAuthentication("nhunhu8202@gmail.com", "nhuanh30195");
            }
        });
        try {
            Message message = new MimeMessage(session);
            message.setHeader("Content-Type", "text/plain; charset=UTF-8");
            message.setFrom(new InternetAddress("nhunhu8202@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(text);
            Transport.send(message);
        } catch (MessagingException e) {
            System.out.println("error");
            return false;
        }
        System.out.println("=======");
        return true;

    }
//    public static void main(String[] args) {
//        SendMail sm = new SendMail();
//        sm.sendMail("jennyvu8202@gmail.com","Coffee Blend","Test send mail");
////        sm.sendMail("vqhuy08071999@gmail.com","Coffee Blend","Test send mail");
////        sm.sendMail("vhdkhoa08071999@gmail.com","Coffee Blend","Test send mail");
//    }
}

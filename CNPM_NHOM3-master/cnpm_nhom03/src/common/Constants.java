package common;

public class Constants {

    // fb
    public static String FACEBOOK_APP_ID = "2720474321608029";
    public static String FACEBOOK_APP_SECRET = "74adf389e4e8720a2593a183d274c4ae";
//        public static String FACEBOOK_REDIRECT_URL = "https://coffee.j.layershift.co.uk/login_facebook";
    public static String FACEBOOK_REDIRECT_URL = "http://localhost:8080/cnpm_nhom03/login_facebook";
    public static String FACEBOOK_LINK_GET_TOKEN = "https://graph.facebook.com/oauth/access_token?client_id=%s&client_secret=%s&redirect_uri=%s&code=%s";

    //gg
    public static String GOOGLE_CLIENT_ID = "508591481116-tna9n07ahv5il3j14gnguvk01m3bd55g.apps.googleusercontent.com";
    public static String GOOGLE_CLIENT_SECRET = "JCZCVX1lHSPPeh42I2DEObuQ";
    public static String GOOGLE_REDIRECT_URL = "http://localhost:8080/cnpm_nhom03/login_google";
//        public static String GOOGLE_REDIRECT_URL = "https://coffee.j.layershift.co.uk/login_google";
    public static String GOOGLE_LINK_GET_TOKEN = "https://accounts.google.com/o/oauth2/token";
    public static String GOOGLE_LINK_GET_USER_INFO = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=";
    public static String GOOGLE_GRANT_TYPE = "authorization_code";

    //captcha
    public static final String SITE_KEY = "6LdqcrkZAAAAAHqVmA7iVJBmubB_i3y0-HphLIdO";

    public static final String SECRET_KEY = "6LdqcrkZAAAAADYlzI9JmO7IqDTkN9PIT0-1XKTz";

}

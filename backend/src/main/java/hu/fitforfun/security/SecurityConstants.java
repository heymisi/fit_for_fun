package hu.fitforfun.security;

public class SecurityConstants {
    public static final Long EXPIRATION_TIME = 86400000L; // 1 day
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/users";
    public static final String VERIFICATION_EMAIL_URL = "/users/email-verification";
    public static final String TOKEN_SECRET = "jf9i4jgu83nfa2";
}

package br.com.projectBackAnd.Utili;

public class SecurityConstants {
    public static final String SECRET = "NilOneCorp";
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/authenticate";
    public static final Long  EXPIRATION_TOKEN = 86400000L;
}

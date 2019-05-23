package com.xrlj.utils.authenticate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xrlj.utils.security.Base64Utils;

import java.io.UnsupportedEncodingException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

/**
 * https://github.com/auth0/java-jwt
 */
public final class JwtUtils {

    private JwtUtils(){}

    public static String genAnSignToken(long userId,String username,String userType, String secret, long time) throws Exception {
        long expTime = System.currentTimeMillis() + time; //到期时间，当前时间延后time时长
        Algorithm algorithm = Algorithm.HMAC256("secret");
        String token = JWT.create()
                .withIssuer("xrlj")
                .withExpiresAt(new Date(expTime))
                .withClaim("userId", userId)
                .withClaim("username",username)
                .withClaim("userType", userType)
                .sign(algorithm);
        return token;
    }

    public static boolean verifyToken() {

    }

    public static void main(String[] args) throws Exception {
//        String token = createAnSignToken();
//        System.out.println(token);
        verifyToken();
    }
}

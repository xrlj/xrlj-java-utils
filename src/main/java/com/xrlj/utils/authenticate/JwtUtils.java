package com.xrlj.utils.authenticate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xrlj.utils.PrintUtil;
import com.xrlj.utils.security.Base64Utils;

import java.time.Instant;
import java.util.Date;

/**
 * https://github.com/auth0/java-jwt
 */
public final class JwtUtils {

    //token过期时间，默认5分钟
    private static final long EXPIRE_TIME_DEFAULT = 5 * 60 * 1000; //毫秒

    private JwtUtils() {
    }

    /**
     * 生成签名的token。
     *
     * @param userId     用户id
     * @param username   用户名
     * @param userType   用户类型
     * @param secret     签名秘钥，保存在服务端
     * @param millisTime 过期时间，毫秒
     * @return 返回签名的jwt，未加密。否则返回空字符串。
     * @throws Exception
     */
    public static String genAnSignToken(long userId, String username, String userType, String secret, long millisTime) {
        try {
            if (millisTime <= 0) {
                millisTime = EXPIRE_TIME_DEFAULT;
            }
            //到期时间，当前时间延后time时长,单位毫秒
            Instant expTime = Instant.now().plusMillis(millisTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = JWT.create()
                    .withIssuer("xrlj")
                    .withExpiresAt(new Date(expTime.toEpochMilli()))
                    .withClaim("userId", userId)
                    .withClaim("username", username)
                    .withClaim("userType", userType)
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            exception.printStackTrace();
        }
        return "";
    }

    public static boolean verifyToken(long userId, String username, String userType, String secret,String token) throws Exception {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("xrlj")
                    .withClaim("userId", userId)
                    .withClaim("username", username)
                    .withClaim("userType", userType)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            return true;
        } catch (JWTVerificationException e) {
            throw e;
        }
    }

    public static void main(String[] args) throws Exception {
//        String token = genAnSignToken(111, "zmt","a","abc",3 *60 * 1000);
//        PrintUtil.println(token);

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ4cmxqIiwidXNlclR5cGUiOiJhIiwiZXhwIjoxNTU4NzE1NDE3LCJ1c2VySWQiOjExMSwidXNlcm5hbWUiOiJ6bXQifQ.Twwsp0OAnwL-tHv_wSkVryEVQwU2-uTGfqT97S9HCvs";
//        boolean a = verifyToken(111,"zmt","a","abc",token);
//        PrintUtil.println(a);

        try {
            DecodedJWT jwt = JWT.decode(token);
            PrintUtil.println(Base64Utils.base64Decode(jwt.getPayload()));
        } catch (JWTDecodeException exception){
            //Invalid token
        }
    }
}

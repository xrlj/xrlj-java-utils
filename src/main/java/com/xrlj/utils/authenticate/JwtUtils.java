package com.xrlj.utils.authenticate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xrlj.utils.PrintUtil;

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

    /**
     * 校验token
     * @param userId
     * @param username
     * @param userType
     * @param secret
     * @param token
     * @return
     */
    public static VerifyTokenResult verifyToken(long userId, String username, String userType, String secret,String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("xrlj")
                    .withClaim("userId", userId)
                    .withClaim("username", username)
                    .withClaim("userType", userType)
                    .build();
            verifier.verify(token);
            return VerifyTokenResult.VERIFY_OK;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            if (e instanceof InvalidClaimException) {
                return VerifyTokenResult.INVALID_CLAIM_ERROR;
            }
            if (e instanceof SignatureVerificationException) {
                return VerifyTokenResult.SIGNATURE_ERROR;
            }
            if (e instanceof TokenExpiredException) {
                return VerifyTokenResult.TOKEN_EXPIRED_ERROR;
            }
        }
        return VerifyTokenResult.VERIFY_ERROR_UNKNOW;
    }

    /**
     * 校验token
     * @param secret
     * @param token
     * @return
     */
    public static VerifyTokenResult verifyToken(String secret,String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("xrlj")
                    .build();
            verifier.verify(token);
            return VerifyTokenResult.VERIFY_OK;
        } catch (JWTVerificationException e) {
            e.printStackTrace();
            if (e instanceof SignatureVerificationException) {
                return VerifyTokenResult.SIGNATURE_ERROR;
            }
            if (e instanceof InvalidClaimException) {
                return VerifyTokenResult.INVALID_CLAIM_ERROR;
            }
            if (e instanceof TokenExpiredException) {
                return VerifyTokenResult.TOKEN_EXPIRED_ERROR;
            }
        }
        return VerifyTokenResult.VERIFY_ERROR_UNKNOW;
    }

    /**
     * token是否过期
     * @return true：过期
     */
    public static boolean isTokenExpired(String token) {
        Instant now = Instant.now();
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getExpiresAt().toInstant().isBefore(now);
    }

    public static <T> T getPubClaimValue(String token, String keyName, Class<T> clazz) {
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim(keyName).as(clazz);
    }

    public static enum VerifyTokenResult {
        VERIFY_OK("校验token成功"),
        SIGNATURE_ERROR("签名信息错误，可能被篡改"),
        INVALID_CLAIM_ERROR("包含无效主体信息"),
        TOKEN_EXPIRED_ERROR("token已过期"),
        VERIFY_ERROR_UNKNOW("校验token失败");

        private final String value;

        VerifyTokenResult(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }

//    public static void main(String[] args) throws Exception {
//        String token = genAnSignToken(111, "zmt","a","abc",2 *60 * 1000);
//        PrintUtil.println(token);
//
//        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJ4cmxqIiwidXNlclR5cGUiOiJhIiwiZXhwIjoxNTU4NzcxMDUxLCJ1c2VySWQiOjExMSwidXNlcm5hbWUiOiJ6bXQifQ.7LFGu4PDW-YRqWw4fI4pP_nPJQGvXHwC1FQXhlJIrEE";
//        PrintUtil.println(isTokenExpired(token));
//
//        try {
//            DecodedJWT jwt = JWT.decode(token);
//            PrintUtil.println(Base64Utils.base64Decode(jwt.getPayload()));
//            PrintUtil.println(jwt.getClaim("username").asString());
//            Date aaa = jwt.getExpiresAt();
//            PrintUtil.println(DateUtil.dateToString(aaa));
//        } catch (JWTDecodeException exception){
//            //Invalid token
//        }
//    }
}

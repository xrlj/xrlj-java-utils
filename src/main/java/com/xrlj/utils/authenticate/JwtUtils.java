package com.xrlj.utils.authenticate;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xrlj.utils.security.KeyTools;

import java.time.Instant;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * https://github.com/auth0/java-jwt
 */
public final class JwtUtils {

    //token过期时间，默认30分钟
    private static final long EXPIRE_TIME_DEFAULT = 30 * 60 * 1000; //毫秒

    private JwtUtils() {
    }

    /**
     * 生成token。通用方法。
     * @param issuer 发布者
     * @param secret 签名秘钥
     * @param millisTime 过期时间
     * @param claims  附件信息。
     * @return
     */
    public static String genAnSignToken(String issuer, String secret, Long millisTime, Map<String,Object> claims) {
        try {
            if (millisTime == null || millisTime <= 0) {
                millisTime = EXPIRE_TIME_DEFAULT;
            }
            //到期时间，当前时间延后time时长,单位毫秒
            Instant expTime = Instant.now().plusMillis(millisTime);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            String token = "";
            JWTCreator.Builder jwt = JWT.create()
                    .withIssuer(issuer)
                    .withExpiresAt(new Date(expTime.toEpochMilli()));
            if (claims != null) {
                Set<String> keys = claims.keySet();
                for (String key: keys) {
                    Object value = claims.get(key);
                    if (value instanceof Boolean) {
                        jwt.withClaim(key, (Boolean) value);
                    } else if (value instanceof Integer) {
                        jwt.withClaim(key, (Integer) value);
                    } else if (value instanceof String) {
                        jwt.withClaim(key, (String) value);
                    } else if (value instanceof Long) {
                        jwt.withClaim(key, (Long) value);
                    } else if (value instanceof Date) {
                        jwt.withClaim(key, (Date) value);
                    } else if (value instanceof Double) {
                        jwt.withClaim(key, (Double) value);
                    } else {
                        throw  new IllegalArgumentException();
                    }
                }
            }
            token = jwt.sign(algorithm);
            return token;
        } catch (JWTCreationException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 校验token
     * @param secret
     * @param token
     * @return
     */
    public static VerifyTokenResult verifyToken(String issuer, String secret,String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(issuer)
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

    /**
     * 返回过期时间。秒时间戳
     * @param token
     * @return 返回秒时间戳
     */
    public static long  getExpires(String token) {
        DecodedJWT jwt = JWT.decode(token);
        Date expiresTime = jwt.getExpiresAt();
        return expiresTime.toInstant().toEpochMilli()/1000;
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

    /**
     * 生成随机盐,长度32位
     * @return
     */
    public static String generateSalt(){
        String salt = KeyTools.generateSalt(16);
        return salt;
    }

//    public static void main(String[] args) throws Exception {
////        Map<String, Object> claims = new HashMap<>();
////        claims.put("username", "zmt");
////        claims.put("userType", 1);
////        claims.put("clientId","abcd");
////        String token = genAnSignToken("xrlj", "sdfsdfadsaf", null, claims);
////        PrintUtil.println(token);
//
////        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJjbGllbnRJZCI6ImFiY2QiLCJpc3MiOiJ4cmxqIiwidXNlclR5cGUiOjEsImV4cCI6MTU2NDE5NDE2MCwidXNlcm5hbWUiOiJ6bXQifQ.XVr0jiw7Gu0ve6lUfik2Y9fdXtii1JqzhCkD_DZg2pc";
////        PrintUtil.println(isTokenExpired(token));
////        PrintUtil.println(verifyToken("xrlj", "dd", token));
////
////        try {
////            DecodedJWT jwt = JWT.decode(token);
////            PrintUtil.println(jwt.getPayload());
////            PrintUtil.println(Base64Utils.base64Decode(jwt.getPayload()));
////            PrintUtil.println(jwt.getClaim("username").asString());
////            Date aaa = jwt.getExpiresAt();
////            PrintUtil.println(DateUtil.dateToString(aaa));
////        } catch (JWTDecodeException exception){
////            //Invalid token
////        }
//
////        String s = Base64Utils.base64Encode("{\"clientId\":\"abcd\",\"iss\":\"xrlj\",\"userType\":1,\"exp\":1564194160,\"username\":\"zmtt\"}");
////        PrintUtil.println(s);
//    }

}

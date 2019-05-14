package com.example.ssoprovider.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.*;

/**
 * @author 17611
 * @version 1.0
 * @className JavaWebTokenUtils
 * @description jwt工具类
 * @date 2019/4/4 12:56
 **/
public class TokenUtils {

    public static String createToken() {
        try {
            //token 密钥
            String secret = "secret";
            Algorithm algorithm = Algorithm.HMAC256("secret");
            //头部信息
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("alg", "HS256");
            map.put("typ", "JWT");

            Date nowDate = new Date();
            //2小过期
            Date expireDate = getAfterDate(nowDate, 0, 0, 0, 2, 0, 0);

            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    /*设置 载荷 Payload*/
                    //签名是有谁生成 例如 服务器
                    .withIssuer("SERVICE")
                    //签名的主题
                    .withSubject("this is test token")
                    //定义在什么时间之前，该jwt都是不可用的.
                    //.withNotBefore(new Date())
                    //签名的观众 也可以理解谁接受签名的
                    .withAudience("APP")
                    //生成签名的时间
                    .withIssuedAt(nowDate)
                    //签名过期的时间
                    .withExpiresAt(expireDate)
                    /*签名 Signature */
                    .sign(algorithm);

            return token;
        } catch (
                JWTCreationException exception) {
            exception.printStackTrace();
        }
        return null;
    }
    public static String createTokenWithClaim(String userName) {

        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            Map<String, Object> map = new HashMap<String, Object>();
            Date nowDate = new Date();
            //2小过期
            Date expireDate = getAfterDate(nowDate,0,0,0,2,0,0);
            map.put("alg", "HS256");
            map.put("typ", "JWT");
            String token = JWT.create()
                    /*设置头部信息 Header*/
                    .withHeader(map)
                    /*设置 载荷 Payload*/
                    .withClaim("loginName", userName)
                    //签名是有谁生成 例如 服务器
                    .withIssuer("SERVICE")
                    //签名的主题
                    .withSubject("this is test token")
                    //定义在什么时间之前，该jwt都是不可用的.
                    //.withNotBefore(new Date())
                    //签名的观众 也可以理解谁接受签名的
                    .withAudience("APP")
                    //生成签名的时间
                    .withIssuedAt(nowDate)
                    //签名过期的时间
                    .withExpiresAt(expireDate)
                    /*签名 Signature */
                    .sign(algorithm);
            return token;
        } catch (JWTCreationException exception){
            exception.printStackTrace();
        }
        return null;
    }


    private static Date getAfterDate(Date date, int year, int month, int day, int hour, int minute, int second) {
        if (date == null) {
            date = new Date();
        }

        Calendar cal = new GregorianCalendar();

        cal.setTime(date);
        if (year != 0) {
            cal.add(Calendar.YEAR, year);
        }
        if (month != 0) {
            cal.add(Calendar.MONTH, month);
        }
        if (day != 0) {
            cal.add(Calendar.DATE, day);
        }
        if (hour != 0) {
            cal.add(Calendar.HOUR_OF_DAY, hour);
        }
        if (minute != 0) {
            cal.add(Calendar.MINUTE, minute);
        }
        if (second != 0) {
            cal.add(Calendar.SECOND, second);
        }
        return cal.getTime();
    }

    public static String  verifyToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("SERVICE")
                    .build(); //Reusable verifier instance
            DecodedJWT jwt = verifier.verify(token);
            String subject = jwt.getSubject();

            Map<String, Claim> claims = jwt.getClaims();
            Claim claim = claims.get("loginName");
            if(claim.asString()==null ||"".equals(claim.asString())){

                return "";
            }
            System.out.println();
            List<String> audience = jwt.getAudience();

            System.out.println(subject);
            System.out.println(audience.get(0));
            return claim.asString();
        } catch (JWTVerificationException exception){
            exception.printStackTrace();
            return "";
        }
    }
}


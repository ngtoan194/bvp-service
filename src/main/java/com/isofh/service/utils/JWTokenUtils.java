/*
 * *******************************************************************
 * Copyright (c) 2018 Isofh.com to present.
 * All rights reserved.
 *
 * Author: tuanld
 * ******************************************************************
 *
 */

package com.isofh.service.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.isofh.service.constant.JWTConst;
import com.isofh.service.enums.ValidateTokenResult;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTokenUtils {

    public static String generateToken() {
        return generateToken((Map<String, Object>) null);
    }


    /**
     * Tao ra Token tu userId
     *
     * @param userId
     * @return
     */
    public static String generateToken(String userId) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        return generateToken(map);
    }

    public static String generateToken(Long id) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", id);
        return generateToken(map);
    }

    /**
     * Tao token tu Map param truyen vao
     *
     * @param map
     * @return
     */
    public static String generateToken(Map<String, Object> map) {
        String token = "";
        try {

            String secrete = JWTConst.JWT_KEY;
            Algorithm algorithm = Algorithm.HMAC256(secrete);
            Builder builder = JWT.create();
            Date currentDate = DateTimeUtils.getCurrentDate();
            builder.withIssuedAt(currentDate);
            builder.withExpiresAt(DateTimeUtils.addDays(currentDate, 365));

            if (map != null) {
                map.forEach((key, value) -> {

                    if (value instanceof String) {
                        builder.withClaim(key, (String) value);
                    } else if (value instanceof Boolean) {
                        builder.withClaim(key, (Boolean) value);
                    } else if (value instanceof Integer) {
                        builder.withClaim(key, (Integer) value);
                    } else if (value instanceof Long) {
                        builder.withClaim(key, (Long) value);
                    } else if (value instanceof Double) {
                        builder.withClaim(key, (Double) value);
                    } else if (value instanceof Date) {
                        builder.withClaim(key, (Date) value);
                    } else if (value instanceof String[]) {
                        builder.withArrayClaim(key, (String[]) value);
                    } else if (value instanceof Long[]) {
                        builder.withArrayClaim(key, (Long[]) value);
                    } else if (value instanceof Integer[]) {
                        builder.withArrayClaim(key, (Integer[]) value);
                    }
                });
            }

            token = builder.sign(algorithm);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return token;

    }

    /**
     * Get Decoded JWT from string token
     *
     * @param token
     * @return
     */
    public static DecodedJWT getDecodedJWT(String token) {
        try {
            String secrete = JWTConst.JWT_KEY;
            Algorithm algorithm = Algorithm.HMAC256(secrete);
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT jwt = verifier.verify(token);
            if (jwt != null)
                return jwt;
            else
                return null;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Lay danh sach claim trong token
     *
     * @param token
     * @return
     */
    public static Map<String, Claim> getClaims(String token) {
        try {
            DecodedJWT decoded = getDecodedJWT(token);
            if (decoded != null)
                return decoded.getClaims();
            else
                return null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Lay chi tiet key tu token
     *
     * @param token
     * @param key
     * @return
     */
    public static Claim getClaimsByKey(String token, String key) {
        Map<String, Claim> claim = getClaims(token);
        if (claim != null)
            return claim.get(key);
        else
            return null;
    }

    public static Long getUserIdFromToken(String token) {
//        String userId = getClaimsByKey(token, "userId").asString();
        Claim claimByKey = getClaimsByKey(token, "userId");
        if (claimByKey != null) {
            Long userId = claimByKey.asLong();
            return userId;
        } else {
            return null;
        }
    }

    /**
     * Check token status
     *
     * @param token
     * @return
     */
    public static ValidateTokenResult validateToken(String token) {
        try {
            DecodedJWT jwt = getDecodedJWT(token);
            if (jwt != null) {
                if (jwt.getExpiresAt().getTime() < DateTimeUtils.getCurrentTimeInMilis()) {
                    return ValidateTokenResult.EXPIRATED;
                } else {
                    return null;
                }
            }
            return ValidateTokenResult.SUCCESS;

        } catch (Exception ex) {
            ex.printStackTrace();
            return ValidateTokenResult.INVALID;
        }
    }
}

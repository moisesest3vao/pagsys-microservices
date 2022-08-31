package br.com.pagsys.msusers.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {
    public static boolean isTokenFormatValid(String token){
        return token != null && token.startsWith("Bearer ") && token.length() > 15;
    }
}

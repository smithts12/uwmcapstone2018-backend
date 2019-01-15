package edu.uwm.capstone.helper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Map;

public class AuthHelper {
    private AuthHelper() {

    }
    public static String getEmailFromAccessToken(String token) {
        String email = "";
        try {
            DecodedJWT jwt = JWT.decode(token);
            Map<String, Claim> claims = jwt.getClaims();
            email = claims.get("https://capstone.com/email").asString();
        } catch (JWTDecodeException ex) {
            System.out.println("Invalid token: " + token);
        }
        return email;
    }
}

package com.cclogic.security;

/**
 * Created by Nishant on 9/18/2017.
 */

import com.cclogic.exceptions.ResourceNotFoundException;
import com.cclogic.user.UserHeaderTokenData;
import com.cclogic.user.Users;
import com.cclogic.user.UsersService;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import static java.util.Collections.emptyList;

@Component
public class TokenAuthenticationService {
    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 18_00_000; // 30 Minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 12_960_000_000L; // 6 Months
    private static final String ACCESS_TOKEN_SECRET = "ThisIsASecret";
    private static final String REFRESH_TOKEN_SECRET = "ThisIsASecretForRefreshToken";
    public static final String HEADER_STRING = "Authorization";


    private static UsersService instance;

    @Autowired
    private UsersService userService;

    @PostConstruct
    public void init() {
        TokenAuthenticationService.instance = userService;
    }

    public static void addAuthentication(HttpServletResponse res, String username) {

        System.out.println("UserName at addAuthentication : " + username);

        Users users = instance.getUserByEmailId(username);

        if (users == null) {
            throw new ResourceNotFoundException("An unexpected exception. Try to login again");
        }

        Gson gson = new Gson();

        HashMap<String, Object> params = new HashMap<>();
        params.put("sub", username);
        params.put("userid", users.getUserId().toString());
        params.put("role", users.getRole());

        String accessJWT = getJWT(params, ACCESS_TOKEN_EXPIRATION_TIME, ACCESS_TOKEN_SECRET);
        String refreshJWT = getJWT(params, REFRESH_TOKEN_EXPIRATION_TIME, REFRESH_TOKEN_SECRET);

        res.addHeader(HEADER_STRING, accessJWT);

        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("message", "Login Successful");
        responseData.put("accessToken", accessJWT);
        responseData.put("refreshToken", refreshJWT);


        try {
            res.getOutputStream().println(gson.toJson(responseData));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getJWT(HashMap<String, Object> params, long expiryTime, String secret) {
        return Jwts.builder()
                .setClaims(params)
                .setExpiration(new Date(System.currentTimeMillis() + expiryTime))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            Jws jws = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET)
                    .parseClaimsJws(token);

            Claims claims = (Claims) jws.getBody();

            String user = claims.getSubject();

            //System.out.println("user Data from token: "+jws.toString());

            return user != null ?
                    new UsernamePasswordAuthenticationToken(user, null, emptyList()) :
                    null;
        }
        return null;
    }

    public static UserHeaderTokenData getUserData(String token) {
        if (token != null) {
            // parse the token.
            Jws jws = Jwts.parser()
                    .setSigningKey(ACCESS_TOKEN_SECRET)
                    .parseClaimsJws(token);

            Claims claims = (Claims) jws.getBody();

            String user = claims.getSubject();

            int userId = Integer.parseInt("" + claims.get("userid"));
            String role = "" + claims.get("role");

            System.out.println("user Data -- from token: " + jws.toString());
            System.out.println("user Id : " + userId);
            System.out.println("Role : " + role);
            System.out.println("username : " + user);

            UserHeaderTokenData userHeaderTokenData = new UserHeaderTokenData();
            userHeaderTokenData.setId(userId);
            userHeaderTokenData.setRole(role);

            return userHeaderTokenData;
        }
        return null;
    }
}

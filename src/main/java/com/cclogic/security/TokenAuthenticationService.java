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
import java.util.List;

import static java.util.Collections.emptyList;

@Component
public class TokenAuthenticationService {
    private static final long EXPIRATION_TIME = 864_000_000; // 10 days
    private static final String SECRET = "ThisIsASecret";
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

        String JWT = Jwts.builder()
                .setClaims(params)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        res.addHeader(HEADER_STRING, JWT);

        HashMap<String, String> responseData = new HashMap<>();
        responseData.put("message", "Login Successful");


        try {
            res.getOutputStream().println(gson.toJson(responseData));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static Authentication getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            // parse the token.
            Jws jws = Jwts.parser()
                    .setSigningKey(SECRET)
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
                    .setSigningKey(SECRET)
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

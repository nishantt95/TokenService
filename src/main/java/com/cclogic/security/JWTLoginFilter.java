package com.cclogic.security;

import com.cclogic.exceptions.InvalidDataException;
import com.cclogic.exceptions.UnAuthorizedAccessException;
import com.cclogic.user.Users;
import com.cclogic.user.UsersRepository;
import com.cclogic.user.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.Collections;

/**
 * Created by Nishant on 9/18/2017.
 */

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private UsersService usersService;

    @Autowired
    public JWTLoginFilter(String url, AuthenticationManager authManager, UsersService usersService) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
        this.usersService = usersService;
    }

    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest req, HttpServletResponse res)
            throws AuthenticationException, IOException, ServletException {

        AccountCredentials credentials = getCredentialsFromHeader(req);
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        credentials.getUsername(),
                        credentials.getPassword(),
                        Collections.emptyList()
                )
        );
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest req,
            HttpServletResponse res, FilterChain chain,
            Authentication auth) throws IOException, ServletException {

        TokenAuthenticationService.addAuthentication(res, auth.getName());
    }

    private AccountCredentials getCredentialsFromHeader(HttpServletRequest request) {
        String authorization = request.getHeader(TokenAuthenticationService.HEADER_STRING);

        if (authorization != null && authorization.startsWith("Basic")) {
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8"));
            String values[] = credentials.split(":", 2);

            System.out.println("Test Log [Login Credentials] : " + credentials);

            if (values.length < 2) {
                throw new InvalidDataException("Invalid parameters for login");
            }

            AccountCredentials accountCredentials = new AccountCredentials();
            accountCredentials.setUsername(values[0]);
            accountCredentials.setPassword(values[1]);


            if (usersService == null) {
                System.out.println("usersRepo is null");
            }

            Users users = usersService.getUserByEmailId(accountCredentials.getUsername());

            if (users == null) {
                System.out.println("users is null");
            }

            if (SecurityUtils.validatePassword(
                    accountCredentials.getPassword(),
                    users.getPassword(),
                    users.getSalt(),
                    users.getIterations())) {
                accountCredentials.setPassword(users.getPassword());
            } else {

                throw new UnAuthorizedAccessException("Invalid username or password used");

            }


            return accountCredentials;

        } else {
            throw new InvalidDataException("Invalid parameters for login");
        }
    }
}

package com.cclogic.security;

import com.cclogic.exceptions.InvalidDataException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

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

    public JWTLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
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

            return accountCredentials;

        } else {
            throw new InvalidDataException("Invalid parameters for login");
        }
    }
}

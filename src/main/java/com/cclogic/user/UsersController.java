package com.cclogic.user;

import com.cclogic.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.cclogic.security.TokenAuthenticationService.HEADER_STRING;
@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

    @RequestMapping(value = "/token/refresh", method = RequestMethod.GET)
    public String generateAccessToken(@RequestHeader HttpHeaders headers) {

        String token = headers.getFirst(HEADER_STRING);

        return TokenAuthenticationService.generateAccessTokenFromRefreshToken(token);
    }

}

package com.cclogic.user;

import com.cclogic.security.TokenAuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.cclogic.security.TokenAuthenticationService.HEADER_STRING;

/**
 * Created by Nishant on 9/16/2017.
 */

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    public String test(@RequestHeader HttpHeaders headers) {
        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));
        System.out.println("Active Id : " + userHeaderTokenData.getId());
        return new CustomResponse("Test Successful!").getResponse();
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public String addUser(@RequestBody OpenUser openUser) {
        userService.addUser(openUser);
        return new CustomResponse("user Registered Successfully!").getResponse();
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/users")
    public String updateUser(@RequestBody OpenUser openUser,
                             @RequestParam(value = "id", required = true) int Id,
                             @RequestHeader HttpHeaders headers) {
        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));
        userService.updateUser(openUser, userHeaderTokenData.getId(), Id);
        return new CustomResponse("user updated successfully!").getResponse();
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/users")
    public String deleteUser(@RequestParam(value = "id", required = true) int Id,
                             @RequestHeader HttpHeaders headers) {
        UserHeaderTokenData userHeaderTokenData = TokenAuthenticationService.getUserData(headers.getFirst(HEADER_STRING));
        userService.deleteUser(Id, userHeaderTokenData.getId());
        return new CustomResponse("user deleted successfully!").getResponse();
    }

    @RequestMapping(value = "/users/search", method = RequestMethod.GET)
    public List<User> getUsersByField(@RequestParam(value = "by", required = true) String field,
                                      @RequestParam(value = "val", required = true) String emailId) {
        return userService.getUserByField(field, emailId);
    }

}

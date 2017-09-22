package com.cclogic.user;

import com.cclogic.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
public class UsersController {

    @Autowired
    UsersService usersService;

    @RequestMapping(value = "/allUsers", method = RequestMethod.GET)
    public List<Users> getUsers() {
        return usersService.getUsers();
    }

    @RequestMapping(value = "/testing", method = RequestMethod.GET)
    public HashMap<String, Object> testing() {

        //1c6bbae4b680b2b52072b39a213f231ceafc6f9daec44118d35f12ae0e0607772b7bd20b9cc876f31ac16f8bdd8727bc4c4a35338fed4297237693012a4097db
        //74d93d401c36108aef62463be7feb95aec7ce1520de11868a4dff458c63049833ac1543688c8bfc59c696a20f657a150ceac231d37e8ba821fc644b0e4a093a5
        HashMap<String, Object> map = new HashMap<>();


        int iterations = 20000;
        String password = "password";
        String salt = SecurityUtils.toHex(SecurityUtils.getSalt().getBytes());

        String generatedHash = SecurityUtils.generateHash(password, salt, iterations);

        map.put("generated hash", generatedHash);
        map.put("generated salt", salt);

        System.out.println("salt : "+salt);

        boolean isCorrectPassword = SecurityUtils.validatePassword(password, generatedHash, salt, iterations);

        map.put("login success", isCorrectPassword);

        return map;
    }

}

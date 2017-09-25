package com.cclogic.user;

import com.cclogic.security.SecurityUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.*;

public class UsersControllerTest {
    @Test
    public void validatingPassword() throws Exception {
        String password = "webastra";
        String storedPassword = "1c6bbae4b680b2b52072b39a213f231ceafc6f9daec44118d35f12ae0e0607772b7bd20b9cc876f31ac16f8bdd8727bc4c4a35338fed4297237693012a4097db";
        String salt = "5b42403462393335343035";
        int iterations = 20000;

        boolean isCorrectPassword = SecurityUtils.validatePassword(password, storedPassword, salt, iterations);

        Assert.assertTrue(isCorrectPassword);

    }

}
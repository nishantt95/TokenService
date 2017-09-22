package com.cclogic.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Nishant on 9/20/2017.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenUser {

    private Integer id;
    private String phoneNumber;
    private String userName;
    private String emailId;
    private String password;
    private String userType;
}

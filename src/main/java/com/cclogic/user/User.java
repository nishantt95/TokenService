package com.cclogic.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.annotations.VisibleForTesting;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by Nishant on 9/16/2017.
 */

@Entity
@Table(name="user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String phoneNumber;
    private String userName;
    private String emailId;
    @JsonIgnore
    private String password;
    private String userType;

    @VisibleForTesting
    public User(String phoneNumber, String userName, String emailId, String password, String userType){
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.emailId = emailId;
        this.password = password;
        this.userType = userType;
    }
}

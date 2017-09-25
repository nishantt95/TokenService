package com.cclogic.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "3c_users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "userID")
    private Integer userId;

    @Column(name = "fullname")
    private String fullName;

    @Column(name = "username")
    private String userName;

    @Column(name = "email")
    private String emailId;

    @Column(name = "pass")
    @JsonIgnore
    private String password;

    private String salt;
    private Integer iterations;


    private enum Role {
        ROLE_ADMINISTRATOR,
        ROLE_OWNER,
        ROLE_AGENT,
        ROLE_SUPERVISOR,
        ROLE_SERVICE_NODE
    }

    @Enumerated(EnumType.STRING)
    private Role role;

    private enum Status {
        INACTIVE,
        ACTIVE,
        DELETED
    }

    @Enumerated(EnumType.STRING)
    private Status status;

    private Integer contact_center;
    private Integer customer;

}

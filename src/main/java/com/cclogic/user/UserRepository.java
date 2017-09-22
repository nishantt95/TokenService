package com.cclogic.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Nishant on 9/17/2017.
 */
public interface UserRepository extends JpaRepository<User,Integer> {

    public List<User> findByEmailId(String emailId);
    public List<User> findById(int Id);
    public List<User> findByPhoneNumber(String phoneNumber);
    public List<User> findByUserName(String userName);
    public List<User> findByUserType(String userType);

}

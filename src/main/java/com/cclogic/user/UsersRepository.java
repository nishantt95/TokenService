package com.cclogic.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Users findByEmailId(String emailId);
    public List<Users> findByUserId(int userId);
    public List<Users> findByRole(String role);
    public List<Users> findByStatus(String status);

}

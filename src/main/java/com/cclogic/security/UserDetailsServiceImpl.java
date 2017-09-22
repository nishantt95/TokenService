package com.cclogic.security;

/**
 * Created by Nishant on 9/19/2017.
 */

import com.cclogic.user.Users;
import com.cclogic.user.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Collections.emptyList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users users = userRepository.findByEmailId(username);

        if (users == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(users.getEmailId(), users.getPassword(), emptyList());
    }
}

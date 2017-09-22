package com.cclogic.security;

/**
 * Created by Nishant on 9/19/2017.
 */

import com.cclogic.user.UserRepository;
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
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<com.cclogic.user.User> users = userRepository.findByEmailId(username);

        com.cclogic.user.User user = null;
        if(users.size()>0) {
            user = userRepository.findByEmailId(username).get(0);
        }

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getEmailId(), user.getPassword(), emptyList());
    }
}

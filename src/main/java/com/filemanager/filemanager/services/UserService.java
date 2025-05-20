package com.filemanager.filemanager.services;

import com.filemanager.filemanager.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
    User save(User user);
    User findByUsername(String username);
}

package com.filemanager.filemanager.controller;

import com.filemanager.filemanager.model.File;
import com.filemanager.filemanager.model.User;
import com.filemanager.filemanager.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.filemanager.filemanager.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/file")
public class fileController {

    @Autowired
    private final AuthenticationManager authenticationManager;

    @Autowired
    private final FileService fileService;

    @Autowired
    private final UserService userService

    public fileController(AuthenticationManager authenticationManager, FileService fileService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.fileService = fileService;
        this.userService = userService;
    }

    // buraya bir daha bak filemeta dto ayarla
    @GetMapping("/files")
    public ResponseEntity<?> getFiles(Authentication authentication) {
        int userId;
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            userId = user.getId();
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
        List<File> files = fileService.findAllByUserId(userId);
        if (files.isEmpty()) {
            return ResponseEntity.ok("No files found for user with ID: " + userId);
        } else {
            return ResponseEntity.ok(files);
        }
    }

    // @PostMapping("/upload")
    // public ResponseEntity<?> uploadFile(Authentication authentication, File file) {
    // }

}

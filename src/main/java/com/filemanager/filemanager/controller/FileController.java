package com.filemanager.filemanager.controller;

import com.filemanager.filemanager.dtos.FileDto;
import com.filemanager.filemanager.model.File;
import com.filemanager.filemanager.model.User;
import com.filemanager.filemanager.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import com.filemanager.filemanager.services.UserService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private FileService fileService;

    @Autowired
    private final UserService userService;

    public FileController(AuthenticationManager authenticationManager, FileService fileService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.fileService = fileService;
        this.userService = userService;
    }

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
        List<FileDto> files = fileService.getFileInfo(userId);
        if (files.isEmpty()) {
            return ResponseEntity.ok("No files found for user with ID: " + userId);
        } else {
            return ResponseEntity.ok(files);
        }
    }

    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<?> uploadFile(Authentication authentication, @RequestParam("file") MultipartFile file1) {
        User user;
        try {
            String username = authentication.getName();
            user = userService.findByUsername(username);
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        File file = new File();

        try {
            file.setFileName(file1.getOriginalFilename());
            file.setFileType(file1.getContentType());
            file.setFileData(file1.getBytes());
            file.setUser(user);

            fileService.save(file);
        }
        catch (Exception e) {
            return ResponseEntity.status(500).body("Error saving file: " + e.getMessage());
        }
        return ResponseEntity.ok("File uploaded successfully");
    }

    @GetMapping("/getfile/{fileId}")
    public ResponseEntity<byte[]> download(
            @PathVariable int fileId,
            @RequestParam(value = "view", required = false, defaultValue = "false") boolean view,
            Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(401).body(null);
            }

            File file = fileService.findById(fileId).orElse(null);
            if (file == null) {
                return ResponseEntity.status(404).body(null);
            }

            if (file.getUser().getId() != user.getId()) {
                return ResponseEntity.status(403).body(null);
            }

            String dispositionType = view ? "inline" : "attachment";
            return ResponseEntity.ok()
                    .header("Content-Disposition", dispositionType + "; filename=\"" + file.getFileName() + "\"")
                    .contentType(MediaType.parseMediaType(file.getFileType()))
                    .body(file.getFileData());
        } catch(Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }


    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<?> deleteFile(@PathVariable int fileId, Authentication authentication) {
        try {
            String username = authentication.getName();
            User user = userService.findByUsername(username);
            if (user == null) {
                return ResponseEntity.status(401).body("Invalid user.");
            }

            File file = fileService.findById(fileId).orElse(null);
            if (file == null) {
                return ResponseEntity.status(404).body("File not found.");
            }

            if (file.getUser().getId() != user.getId()) {
                return ResponseEntity.status(403).body("You are not allowed to delete this file.");
            }

            fileService.deleteById(fileId);
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error deleting file: " + e.getMessage());
        }
    }


}

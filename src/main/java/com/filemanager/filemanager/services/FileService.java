package com.filemanager.filemanager.services;

import com.filemanager.filemanager.dtos.FileDto;
import com.filemanager.filemanager.model.File;
import com.filemanager.filemanager.model.User;

import java.util.List;
import java.util.Optional;

public interface FileService {
    File save(File file);
    List<File> findAllByUserId(int userID);
    Optional<File> findByUserId(int userId);
    List<FileDto> getFileInfo(int userId);
    Optional<File> findById(int fileId);
    void deleteById(int fileId);
}

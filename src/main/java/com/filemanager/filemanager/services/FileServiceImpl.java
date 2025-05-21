package com.filemanager.filemanager.services;

import com.filemanager.filemanager.repository.FileRepository;
import com.filemanager.filemanager.model.File;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.filemanager.filemanager.model.User;

@Service
public class FileServiceImpl implements FileService {
    private final FileRepository fileRepository;

    public FileServiceImpl(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    @Override
    public File save(File file) {
        return fileRepository.save(file);
    }

    @Override
    public List<File> findAllByUserId(int userID) {
        return fileRepository.findAllByUserId(userID);
    }

    @Override
    public Optional<File> findByUserId(int userId) {
        return fileRepository.findById(userId);
    }

}

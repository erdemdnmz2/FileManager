package com.filemanager.filemanager.repository;

import com.filemanager.filemanager.model.File;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FileRepository extends JpaRepository<File, Integer> {
    List<File> findAllByUserId(Integer userId);
}

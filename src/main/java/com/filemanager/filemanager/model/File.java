package com.filemanager.filemanager.model;

import jakarta.persistence.Lob;

public class File {
    private int id;
    private String fileName;
    private int userId;

    @Lob
    private byte[] fileData;
}

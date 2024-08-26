package com.project.file.services;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public String uploadImageToFileSystem(MultipartFile file);

    public byte[] downloadImageFromFileSystem(String fileId);

    public void deleteImageFromFileSystem(String fileId);
}

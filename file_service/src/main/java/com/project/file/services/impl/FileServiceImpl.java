package com.project.file.services.impl;

import com.project.file.entities.File;
import com.project.file.exceptions.GenericErrorResponse;
import com.project.file.repository.FileRepository;
import com.project.file.services.FileService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    private FileRepository fileRepository;
    private String FOLDER_PATH;

    @Value("${folder.path}")
    private String path;


    @PostConstruct
    public void init(){
        String currentWorkingDirectory = System.getProperty("user.dir");
        FOLDER_PATH = currentWorkingDirectory + path;

        java.io.File targetFolder = new java.io.File(FOLDER_PATH);

        if(!targetFolder.exists()){
            boolean directoriesCreated = targetFolder.mkdirs();
            if(!directoriesCreated){
                throw GenericErrorResponse.builder()
                        .message("Unable to create directories")
                        .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                        .build();
            }
        }
    }


    @Override
    public String uploadImageToFileSystem(MultipartFile file) {
        String uuid = UUID.randomUUID().toString();
        String filePath = FOLDER_PATH +"/" + uuid;

        try {
            file.transferTo(new java.io.File(filePath));
        }
        catch (Exception exception){
            throw GenericErrorResponse.builder()
                    .message("Unable to save file to storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }
        fileRepository.save(File.builder()
                .fileId(uuid)
                .type(file.getContentType())
                .filePath(filePath)
                .build()
        );
        return uuid;
    }

    @Override
    public byte[] downloadImageFromFileSystem(String fileId) {
       try{
        return Files.readAllBytes(new java.io.File(findById(fileId).getFilePath()).toPath());
       }
       catch (IOException exception){
           throw GenericErrorResponse.builder()
                   .message("Unable to read file from storage")
                   .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                   .build();
       }
    }

    @Override
    public void deleteImageFromFileSystem(String fileId) {
        java.io.File file = new java.io.File(findById(fileId).getFilePath());

        if(file.delete())
            this.fileRepository.deleteById(fileId);
        else
            throw GenericErrorResponse.builder()
                    .message("Unable to delete file from storage")
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
    }


    private File findById(String fileId){
        return fileRepository.findById(fileId)
                .orElseThrow(()->GenericErrorResponse.builder()
                        .message("File not found")
                        .httpStatus(HttpStatus.NOT_FOUND)
                        .build()
                );
    }
}

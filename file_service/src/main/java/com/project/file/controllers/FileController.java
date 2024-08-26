package com.project.file.controllers;

import com.project.file.services.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/v1/file-storage")
public class FileController {
    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadImageToFileSystem(@RequestParam(name = "image")MultipartFile file){
        return ResponseEntity.ok().body(this.fileService.uploadImageToFileSystem(file));
    }

    @GetMapping("/download/{fileId}")
    public ResponseEntity<?> downloadFileFromFileSystem(@PathVariable("fileId") String fileId) throws IOException {
        byte[] fileData = this.fileService.downloadImageFromFileSystem(fileId);

        // Use the fileId to determine the file type (e.g., file extension)
        String mimeType = Files.probeContentType(Paths.get(fileId));
        if (mimeType == null) {
            mimeType = "application/octet-stream"; // Default MIME type if unknown
        }

        return ResponseEntity.accepted().contentType(MediaType.valueOf(mimeType)).build();
    }


    @DeleteMapping("/delete/{fileId}")
    public ResponseEntity<Void> deleteFileFromFileSystem(@PathVariable String fileId){
        this.fileService.deleteImageFromFileSystem(fileId);
        return ResponseEntity.noContent().build();
    }



}

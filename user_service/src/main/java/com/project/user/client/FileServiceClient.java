package com.project.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "file-service",path = "/api/v1/file-storage")
public interface FileServiceClient {

    @PostMapping(value = "/upload",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> uploadImageToFileSystem(@RequestParam("image")MultipartFile file);

    @DeleteMapping("/delete/{fileId}")
    ResponseEntity<Void> deleteImageFromFileSystem(@PathVariable String fileId);

}

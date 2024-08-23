package com.project.movie.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {

	// method to get image/resources from user
		String uploadImage(String path, MultipartFile file) throws IOException;

		// method to get resources from database
		InputStream getResource(String path, String fileName) throws FileNotFoundException;
}

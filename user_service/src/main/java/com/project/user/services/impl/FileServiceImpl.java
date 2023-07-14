package com.project.user.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.project.user.auth.AuthStorage;
import com.project.user.exceptions.ForbiddenRequestException;
import com.project.user.services.FileService;

@Service
public class FileServiceImpl implements FileService {
	
	Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);
	
	@Autowired
	private UserServiceImpl userService;

	@Override
	public String uploadImage(String path, MultipartFile file,Long userId) throws IOException, ForbiddenRequestException {

		if(!AuthStorage.isUserLoggedIn(userId)) {
//			System.out.println(AuthStorage.LOGGED_IN_USER_DATA.get(AuthStorage.USER).getUserId());
			logger.warn("User is not allowed to change image of user with userId {}",userId);
			throw new ForbiddenRequestException();
		}
		// get file name
		String name = file.getOriginalFilename();
		// hello.png

		// random name generate file
		String randomId = UUID.randomUUID().toString();
		
		String fileName1 = randomId.concat(name.substring(name.lastIndexOf(".")));
		// 9823587fkrwkjkrrjw_8724.png

		// Generate full path
		String filePath = path + File.separator + fileName1;

		// if folder is not present create one
		File f = new File(path);
		if (!f.exists()) {
			f.mkdir();
		}

		// copy file
		Files.copy(file.getInputStream(), Paths.get(filePath));

		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath = path + File.separator + fileName;

		InputStream is = new FileInputStream(fullPath);

		return is;
	}

}

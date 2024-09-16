package com.project.review.utils;

import com.project.review.client.MovieServiceClient;
import com.project.review.client.UserServiceClient;
import com.project.review.exceptions.GenericException;
import com.project.review.payloads.MovieDTO;
import com.project.review.payloads.UserDTO;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@AllArgsConstructor
public class AccessUtils {

    private UserServiceClient userServiceClient;


    private MovieServiceClient movieServiceClient;

    // Private method to validate user access
    public UserDTO validateUserAccess(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = String.valueOf(authentication.getPrincipal());
        UserDTO userDTO = userServiceClient.getUserByUserEmail(email).getBody();

        if (userDTO == null) {
            throw new GenericException("User not found", HttpStatus.NOT_FOUND);
        }

        if (!Objects.equals(userDTO.getUserId(), userId)) {
            throw new GenericException("Request not permitted", HttpStatus.UNAUTHORIZED);
        }

        return userDTO;
    }

    // Private method to validate movie existence
    public MovieDTO validateMovieExistence(Long movieId) {
        MovieDTO movieDTO = movieServiceClient.getMovieById(movieId).getBody();

        if (movieDTO == null) {
            throw new GenericException("Movie not found", HttpStatus.NOT_FOUND);
        }

        return movieDTO;
    }
}

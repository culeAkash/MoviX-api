package com.project.auth.services;


import com.project.auth.client.UserClient;
import com.project.auth.exceptions.WrongCredentialsException;
import com.project.auth.payloads.RegisterDTO;
import com.project.auth.payloads.TokenDTO;
import com.project.auth.requests.LoginRequest;
import com.project.auth.requests.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserClient userClient;
    private final JwtService jwtService;

    public TokenDTO login(LoginRequest request) throws WrongCredentialsException {
        try {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                return TokenDTO
                        .builder()
                        .token(jwtService.generateToken(request.getEmail()))
                        .build();
            } else throw new WrongCredentialsException("Wrong Credentials");
        }
        catch(BadCredentialsException ex){
            throw new WrongCredentialsException("Wrong email or password");
        }
        catch (InternalAuthenticationServiceException ex) {
            throw new WrongCredentialsException(ex.getMessage());
        }
    }

    public RegisterDTO register(RegisterRequest request) {
        return userClient.saveUser(request).getBody();
    }
}

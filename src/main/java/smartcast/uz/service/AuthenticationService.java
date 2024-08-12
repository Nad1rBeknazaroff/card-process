package smartcast.uz.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import smartcast.uz.payload.request.AuthenticationRequest;
import smartcast.uz.payload.request.RegisterRequest;
import smartcast.uz.payload.response.AuthenticationResponse;

import java.io.IOException;

public interface AuthenticationService {
    AuthenticationResponse register(RegisterRequest request);

    AuthenticationResponse authenticate(AuthenticationRequest request);

    void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException;
}

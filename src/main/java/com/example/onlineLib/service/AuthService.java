package com.example.onlineLib.service;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.SignDTO;
import com.example.onlineLib.payload.TokenDTO;
import org.springframework.security.core.userdetails.UserDetails;

public interface AuthService {

    UserDetails loadUserByUsername(String username);

    ApiResult<?> signUp(SignDTO signDTO);

    ApiResult<?> verificationEmail(String email);

    ApiResult<TokenDTO> signIn(SignDTO signDTO);

    ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken);
}

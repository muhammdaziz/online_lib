package com.example.onlineLib.controller;

import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.SignDTO;
import com.example.onlineLib.payload.TokenDTO;
import com.example.onlineLib.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class AuthControllerImpl implements AuthController {

    private final AuthService authService;

    public ApiResult<?> signUp(SignDTO signDTO){
        return authService.signUp(signDTO);
    }

    public ApiResult<?> verificationEmail(String email){
        return authService.verificationEmail(email);
    }


    public ApiResult<TokenDTO> signIn(SignDTO signDTO){
        return authService.signIn(signDTO);
    }


    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        return authService.refreshToken(accessToken,refreshToken);
    }


}

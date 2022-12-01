package com.example.onlineLib.controller;


import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.SignDTO;
import com.example.onlineLib.payload.TokenDTO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RequestMapping("/api/auth")
public interface AuthController {

    @PostMapping("/sign-up")
    ApiResult<?> signUp(@Valid @RequestBody SignDTO signDTO);

    @PostMapping("/verification-email/{email}")
    ApiResult<?> verificationEmail(@PathVariable String email);

    @PostMapping("/sign-in")
    ApiResult<TokenDTO> signIn(@Valid @RequestBody SignDTO signDTO);


    @GetMapping("/refresh-token")
    public ApiResult<TokenDTO> refreshToken(@RequestHeader(value = "Authorization") String accessToken,
                                            @RequestHeader(value = "RefreshToken") String refreshToken);

}

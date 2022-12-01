package com.example.onlineLib.service;

import com.example.onlineLib.entity.User;
import com.example.onlineLib.exceptions.RestException;
import com.example.onlineLib.payload.ApiResult;
import com.example.onlineLib.payload.SignDTO;
import com.example.onlineLib.payload.TokenDTO;
import com.example.onlineLib.repository.RoleRepository;
import com.example.onlineLib.repository.UserRepository;
import com.example.onlineLib.utils.MessageLang;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements UserDetailsService, AuthService {

    @Value("${jwt.access.key}")
    private String ACCESS_TOKEN_KEY;

    @Value("${jwt.refresh.key}")
    private String REFRESH_TOKEN_KEY;

    @Value("${jwt.access.expiration-time}")
    private long ACCESS_TOKEN_EXPIRATION_TIME;

    @Value("${jwt.refresh.expiration-time}")
    private long REFRESH_TOKEN_EXPIRATION_TIME;
    @Value("${server.port}")
    private String API_PORT;
    private final String API = " http://54.209.172.146:";
    @Value("${spring.mail.username}")
    private String sender;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender javaMailSender;
    private final RoleRepository roleRepository;

    private final AuthenticationManager authenticationManager;

    public AuthServiceImpl(UserRepository userRepository,
                           @Lazy PasswordEncoder passwordEncoder,
                           JavaMailSender javaMailSender,
                           @Lazy AuthenticationManager authenticationManager,
                           RoleRepository roleRepository
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.javaMailSender = javaMailSender;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(username)
                .orElseThrow(
                        () -> RestException.restThrow(String.format("%s email not found", username), HttpStatus.UNAUTHORIZED));
    }


    public ApiResult<?> signUp(SignDTO signDTO) {

        if (userRepository.existsByEmail(signDTO.getEmail()))
            throw RestException.restThrow(
                    MessageLang.getMessageSource("EMAIL_ALREADY_EXIST"),
                    HttpStatus.CONFLICT);

        String encode = passwordEncoder.encode(signDTO.getPassword());
        User user = new User(signDTO.getEmail(), encode);

        userRepository.save(user);
        return ApiResult.successResponse(MessageLang.getMessageSource("SUCCESSFULLY_ADDED"));

    }


    public ApiResult<?> verificationEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        RestException.restThrow(MessageLang.getMessageSource("EMAIL_NOT_EXIST"),
                                HttpStatus.NOT_FOUND));

        if (user.isEnabled())
            return ApiResult.successResponse("ALREADY_VERIFIED");

        user.setEnabled(true);
        userRepository.save(user);
        return ApiResult.successResponse("SUCCESSFULLY_VERIFIED");
    }



    public ApiResult<TokenDTO> signIn(SignDTO signDTO) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signDTO.getEmail(),
                        signDTO.getPassword()
                ));

        User user = (User) authentication.getPrincipal();

        String accessToken = generateToken(user.getEmail(), true);
        String refreshToken = generateToken(user.getEmail(), false);


        TokenDTO tokenDTO = TokenDTO
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return ApiResult.successResponse(
                MessageLang.getMessageSource("SUCCESSFULLY_TOKEN_GENERATED"),
                tokenDTO);
    }


    public String generateToken(String email, boolean accessToken) {

        Date expiredDate = new Date(new Date().getTime() +
                (accessToken ? ACCESS_TOKEN_EXPIRATION_TIME : REFRESH_TOKEN_EXPIRATION_TIME));

        return Jwts
                .builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, (
                        accessToken ? ACCESS_TOKEN_KEY : REFRESH_TOKEN_KEY
                ))
                .compact();
    }



    private void sendNotificationMsgToUserMail(User user){

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(sender);
        simpleMailMessage.setTo(user.getEmail());
        simpleMailMessage.setSubject("Verification Mail");
        simpleMailMessage.setText(MessageLang.getMessageSource("CLICK_LINK") + API +API_PORT+"/api/auth/verification-email/" + user.getEmail());

        javaMailSender.send(simpleMailMessage);
    }


    public ApiResult<TokenDTO> refreshToken(String accessToken, String refreshToken) {
        accessToken = accessToken.substring(accessToken.indexOf("Bearer") + 6).trim();
        try {
            Jwts
                    .parser()
                    .setSigningKey(ACCESS_TOKEN_KEY)
                    .parseClaimsJws(accessToken)
                    .getBody()
                    .getSubject();
        } catch (ExpiredJwtException ex) {
            try {
                String email = Jwts
                        .parser()
                        .setSigningKey(REFRESH_TOKEN_KEY)
                        .parseClaimsJws(refreshToken)
                        .getBody()
                        .getSubject();
                User user = userRepository.findByEmail(email).orElseThrow(() ->
                        RestException.restThrow(MessageLang.getMessageSource("EMAIL_NOT_EXIST"), HttpStatus.NOT_FOUND));

                if (!user.isEnabled()
                        || !user.isAccountNonExpired()
                        || !user.isAccountNonLocked()
                        || !user.isCredentialsNonExpired())
                    throw RestException.restThrow(MessageLang.getMessageSource("USER_PERMISSION_RESTRICTION"), HttpStatus.UNAUTHORIZED);

                String newAccessToken = generateToken(email, true);
                String newRefreshToken = generateToken(email, false);
                TokenDTO tokenDTO = TokenDTO.builder()
                        .accessToken(newAccessToken)
                        .refreshToken(newRefreshToken)
                        .build();
                return ApiResult.successResponse(tokenDTO);
            } catch (Exception e) {
                throw RestException.restThrow(MessageLang.getMessageSource("REFRESH_TOKEN_EXPIRED"), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception ex) {
            throw RestException.restThrow(MessageLang.getMessageSource("WRONG_ACCESS_TOKEN"), HttpStatus.UNAUTHORIZED);
        }

        throw RestException.restThrow(MessageLang.getMessageSource("ACCESS_TOKEN_NOT_EXPIRED"), HttpStatus.UNAUTHORIZED);
    }
}

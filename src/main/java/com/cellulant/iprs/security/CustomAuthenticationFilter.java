package com.cellulant.iprs.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cellulant.iprs.exception.ResourceNotFoundException;
import com.cellulant.iprs.dto.AuthenticationResponseDTO;
import com.cellulant.iprs.entity.LoginLog;
import com.cellulant.iprs.entity.User;
import com.cellulant.iprs.repository.UserRepository;
import com.cellulant.iprs.service.ILoginLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /*
     * class used to convert json string to pojo
     */
    private ObjectMapper objectMapper = new ObjectMapper();

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final ILoginLogService loginLogService;

    AtomicInteger loginAttempt = new AtomicInteger(1);

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager,
                                      UserRepository userRepository,
                                      ILoginLogService loginLogService) {
        this.authenticationManager = authenticationManager; // used to authenticate user
        this.userRepository = userRepository;
        this.loginLogService = loginLogService;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("the request {}", request);
        try {
            log.info("the request2 {}", request.getReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        AuthenticationDTO authenticationDTO = getAuthenticationDTO(request);
        String email = authenticationDTO.getEmail().trim();
        String password = authenticationDTO.getPassword().trim();
        int loginAttempt = authenticationDTO.getLoginAttempt();
        this.loginAttempt.set(loginAttempt);
        log.info("Email is: {}", email);
        log.info("Password is: {}", password);
        log.info("Login attempt is: {}", loginAttempt);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken); // authenticate user
    }

    // user authenticated successful
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User)authentication.getPrincipal(); // returns user successfully authenticated
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes()); // get algorithm to sign access and refresh token; in prod encrypt and decrypt secret
        String access_token = JWT.create()
                .withSubject(user.getUsername()) // unique item about user so that you can identify user i.e id, email
                .withExpiresAt(new Date(System.currentTimeMillis() + 20000 * 60 * 1000)) // get current time of system and add 10 minutes
                //.withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 100)) // get current time of system and add 1 minute
                .withIssuer(request.getRequestURL().toString()) // url of our application
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList())) // list of roles
                .sign(algorithm);

        String refresh_token = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + 30000 * 60 * 1000)) // time for refresh token depends on security concerns
                //.withExpiresAt(new Date(System.currentTimeMillis() + 120000)) // 2 minutes
                .withIssuer(request.getRequestURL().toString())
                .sign(algorithm);
        /*response.setHeader("access_token", access_token);
        response.setHeader("refresh_token", refresh_token);*/



        Map<String, String> tokens = new HashMap<>();
        tokens.put("access_token", access_token);
        tokens.put("refresh_token", refresh_token);
        response.setContentType(APPLICATION_JSON_VALUE);

        log.info("authResponse101 {}", user.getUsername());
        User user1 = userRepository.findByEmailAddressIgnoreCase(user.getUsername()).orElseThrow(() -> new ResourceNotFoundException("User not found "));;
        Date date = new Date();
        Timestamp timestamp = new Timestamp(date.getTime());
        LoginLog loginLog = LoginLog.builder()
                .userID(user1.getUserID())
                .attemptsBeforeLogin(this.loginAttempt.get())
                .loginIP(request.getRemoteAddr())
                .loginTime(timestamp)
                .logoutTime(timestamp)
                //.dateModified(timestamp)
                //.insertedBy(1)
               // .updatedBy(1)
                //.sessionID("123")
                //.token("123")
                .build();
        loginLogService.create(loginLog);

        AuthenticationResponseDTO authenticationResponseDTO = AuthenticationResponseDTO.builder().
                user(user1)
                .accessToken(access_token)
                .refreshToken(refresh_token)
                .build();

        log.info("authResponse {}", authenticationResponseDTO);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), authenticationResponseDTO);
    }

    private AuthenticationDTO getAuthenticationDTO(HttpServletRequest request)  {
        String body = null;
        AuthenticationDTO authenticationDTO = null;
        try {
            body = request.getReader().lines().collect(Collectors.joining());
            authenticationDTO = objectMapper.readValue(body, AuthenticationDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return authenticationDTO;
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.error("failed");
        Map<String, String> res = new HashMap<>();
        res.put("error_message", "failed authentication");
        response.setContentType(APPLICATION_JSON_VALUE);
        response.setStatus(UNAUTHORIZED.value());
        new ObjectMapper().writeValue(response.getOutputStream(), res);
    }
}

@Data
class AuthenticationDTO {
    private String email;
    private String password;
    private int loginAttempt;
}

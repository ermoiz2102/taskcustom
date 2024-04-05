package com.example.customer.authJwt.Service;

import com.example.customer.authJwt.DTO.ReqRes;
import com.example.customer.authJwt.Entity.User;
import com.example.customer.authJwt.Repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class AuthService {
    @Autowired
    private userRepository userRepository;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ReqRes signUp(ReqRes registrationRequest){
        ReqRes resp=new ReqRes();
        try{
           User user= new User();
            user.setEmail(registrationRequest.getEmail());
            user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            user.setRole(registrationRequest.getRole());

            User newUser= userRepository.save(user);
            if(newUser!=null&&newUser.getId()>0){
                resp.setUsers(newUser);
                resp.setMessage("User save  successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
    public ReqRes signIn(ReqRes signInRequest){
        ReqRes resp=new ReqRes();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
            var user=userRepository.findByEmail(signInRequest.getEmail()).orElseThrow();
            var jwt=jwtUtils.generateToken(user);
            var refreshToken =jwtUtils.generatRefreshToken(new HashMap<>(),user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("24Hr");
            resp.setMessage("Successfully signed In");

        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }
    public ReqRes refreshtoken(ReqRes refreshToken){
        ReqRes resp=new ReqRes();
        String email=jwtUtils.extractUsername(refreshToken.getToken());
        User user=userRepository.findByEmail(email).orElseThrow();
        if(jwtUtils.isTokenValid(refreshToken.getToken(),user)){
            var jwt=jwtUtils.generateToken(user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken.getToken());
            resp.setExpirationTime("24Hr");
            resp.setMessage("Successfully Resfresh Token");
        }
        resp.setStatusCode(500);
        return resp;
    }

}

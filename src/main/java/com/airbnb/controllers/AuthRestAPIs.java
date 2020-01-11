package com.airbnb.controllers;

import com.airbnb.messages.request.LoginForm;
import com.airbnb.messages.request.SignUpForm;
import com.airbnb.messages.response.JwtResponse;
import com.airbnb.messages.response.ResponseMessage;
import com.airbnb.models.Role;
import com.airbnb.models.RoleName;
import com.airbnb.models.User;
import com.airbnb.repositories.RoleRepository;
import com.airbnb.repositories.UserRepository;
import com.airbnb.security.jwt.JwtProvider;
import com.airbnb.security.sevice.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthRestAPIs {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtProvider jwtProvider;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateJwtToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            UserPrinciple user = (UserPrinciple) userDetails;
            Long id = user.getId();

            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(true, "success" + loginRequest.getPassword(), new JwtResponse(id, jwt, userDetails.getUsername(),
                            userDetails.getAuthorities())),
                    HttpStatus.OK);
        } catch (DisabledException e) {
            e.printStackTrace();
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    //signup with host
    @RequestMapping(value = "/host/signup", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> registerHost(@Valid @RequestBody SignUpForm signUpRequest) throws
            Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Fail -> Username is already token!", null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Fail -> Email is already in use!", null),
                    HttpStatus.BAD_REQUEST);
        }

        //creating user's acc

        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role adminRole = roleRepository.findByName(RoleName.ROLE_HOST)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(adminRole);
//        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, "User registered with ROLE_HOST successfully!", null),
                HttpStatus.OK);
    }

    //signup with guest
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    public ResponseEntity<ResponseMessage> registerUser(@Valid @RequestBody SignUpForm signUpRequest) throws Exception {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Fail -> Username already exists!", null),
                    HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<ResponseMessage>(
                    new ResponseMessage(false, "Fail -> Email already uses!", null),
                    HttpStatus.BAD_REQUEST);
        }

        // Creating user's account
        User user = new User(signUpRequest.getName(), signUpRequest.getUsername(), signUpRequest.getEmail(),
                passwordEncoder.encode(signUpRequest.getPassword()));

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
        roles.add(userRole);
//        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);

        return new ResponseEntity<ResponseMessage>(
                new ResponseMessage(true, "User registered with ROLE_GUEST successfully!", null),
                HttpStatus.OK);
    }
}

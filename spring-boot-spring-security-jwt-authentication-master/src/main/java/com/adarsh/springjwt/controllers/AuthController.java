package com.adarsh.springjwt.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.adarsh.springjwt.clientResponses.EmsDtoClient;
import com.adarsh.springjwt.clientResponses.Leavetypedtoclient;
import com.adarsh.springjwt.clients.Ems_lms_client;
import com.adarsh.springjwt.models.Role;
import com.adarsh.springjwt.models.User;
import com.adarsh.springjwt.payload.request.LoginRequest;
import com.adarsh.springjwt.payload.request.SignupRequest;
import com.adarsh.springjwt.payload.response.JwtResponse;
import com.adarsh.springjwt.payload.response.MessageResponse;
import com.adarsh.springjwt.payload.response.SessionResponse;
import com.adarsh.springjwt.repository.RoleRepository;
import com.adarsh.springjwt.repository.UserRepository;
import com.adarsh.springjwt.security.jwt.JwtUtils;
import com.adarsh.springjwt.security.services.UserDetailsImpl;

@CrossOrigin(origins = "http://localhost:4200")  // Enable CORS for this controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    Ems_lms_client ems_lms_client;

    @Transactional
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, HttpSession session) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        System.out.println("login doneeee");

        // Store user details in session
        session.setAttribute("userId", userDetails.getId());
        session.setAttribute("username", userDetails.getUsername());
        session.setAttribute("roles", roles);
System.out.println("heyyyy i am signed in method from springboot");
        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));

    }

    @GetMapping("/checkSession")
    public ResponseEntity<?> checkSession(HttpSession session) {
        // Check if userId is present in session
        Long userId = (Long) session.getAttribute("userId");
        String username = (String) session.getAttribute("username");
        @SuppressWarnings("unchecked")
        List<String> roles = (List<String>) session.getAttribute("roles");

        if (userId != null && username != null && roles != null) {
            return ResponseEntity.ok(new SessionResponse(username, roles));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No active session found or Login First.");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        // Check if username is already taken
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Check if email is already in use
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Ensure roles are provided
        if (signUpRequest.getRole() == null || signUpRequest.getRole().isEmpty()) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: At least one role ID must be specified"));
        }

        // Create new user
        User user = new User(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        // Fetch roles using role IDs
        Set<Role> roles = new HashSet<>();
        for (Long roleId : signUpRequest.getRole()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new RuntimeException("Error: Role with ID " + roleId + " not found."));
            roles.add(role);
        }

        user.setRoles(roles);
        userRepository.save(user);

        // Save user ID in external EMS + LMS services
        EmsDtoClient emsDtoClient = new EmsDtoClient();
        emsDtoClient.setEmpid(user.getId());
        ResponseEntity<String> response = ems_lms_client.createemployee(emsDtoClient);

        Leavetypedtoclient leaveTypeDtoClient = new Leavetypedtoclient();
        leaveTypeDtoClient.setEmpid(user.getId());
        ResponseEntity<String> response1 = ems_lms_client.createleaverowafterregister(leaveTypeDtoClient);

        if (response.getStatusCode().is2xxSuccessful() && response1.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .ok(new MessageResponse("User registered successfully! Employee can enter remaining data now."));
        } else {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error: User registered but failed to save in another system."));
        }
    }

}

package spring_boot_react_auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spring_boot_react_auth.dto.JwtRequest;
import spring_boot_react_auth.dto.JwtResponse;
import spring_boot_react_auth.entity.User;
import spring_boot_react_auth.repository.UserRepository;
import spring_boot_react_auth.security.JwtUtil;
import spring_boot_react_auth.service.UserDetailsServiceImpl;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final UserRepository userRepository;   // <-- new

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        final String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(
                new JwtResponse(token, user.getFirstName(), user.getLastName(), user.getEmail())
        );
    }
}

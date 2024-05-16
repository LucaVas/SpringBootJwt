package dev.lucavassos.springbootjwt.services;

import dev.lucavassos.springbootjwt.entities.Role;
import dev.lucavassos.springbootjwt.entities.RoleName;
import dev.lucavassos.springbootjwt.entities.User;
import dev.lucavassos.springbootjwt.dtos.UserLoginDto;
import dev.lucavassos.springbootjwt.dtos.UserSignupDto;
import dev.lucavassos.springbootjwt.repositories.RoleRepository;
import dev.lucavassos.springbootjwt.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            RoleRepository roleRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User signup(UserSignupDto input) {

        Optional<Role> optionalRole = roleRepository.findByName(RoleName.USER);

        if (optionalRole.isEmpty()) {
            return null;
        }

        User user = new User();
        user.setFullName(input.fullName());
        user.setEmail(input.email());
        user.setPassword(passwordEncoder.encode(input.password()));
        user.setRole(optionalRole.get());

        return userRepository.save(user);
    }

    public User authenticate(UserLoginDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.email(),
                        input.password()
                )
        );

        return userRepository.findByEmail(input.email())
                .orElseThrow();
    }
}

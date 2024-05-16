package dev.lucavassos.springbootjwt.services;

import dev.lucavassos.springbootjwt.dtos.UserSignupDto;
import dev.lucavassos.springbootjwt.entities.Role;
import dev.lucavassos.springbootjwt.entities.RoleName;
import dev.lucavassos.springbootjwt.entities.User;
import dev.lucavassos.springbootjwt.repositories.RoleRepository;
import dev.lucavassos.springbootjwt.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> allUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(users::add);
        return users;
    }

    public User createAdministrator(UserSignupDto input) {
        Optional<Role> optionalRole = roleRepository.findByName(RoleName.ADMIN);

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
}

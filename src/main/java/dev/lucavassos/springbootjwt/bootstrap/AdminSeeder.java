package dev.lucavassos.springbootjwt.bootstrap;

import dev.lucavassos.springbootjwt.dtos.UserSignupDto;
import dev.lucavassos.springbootjwt.entities.Role;
import dev.lucavassos.springbootjwt.entities.RoleName;
import dev.lucavassos.springbootjwt.entities.User;
import dev.lucavassos.springbootjwt.repositories.RoleRepository;
import dev.lucavassos.springbootjwt.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminSeeder implements ApplicationListener<ContextRefreshedEvent> {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public AdminSeeder(
        RoleRepository roleRepository,
        UserRepository  userRepository,
        PasswordEncoder passwordEncoder
    ) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        this.createAdministrator();
    }

    private void createAdministrator() {
        UserSignupDto userDto = new UserSignupDto(
                "Admin",
                "admin@mail.com",
                "123456"
        );

        Optional<Role> optionalRole = roleRepository.findByName(RoleName.ADMIN);
        Optional<User> optionalUser = userRepository.findByEmail(userDto.email());

        if (optionalRole.isEmpty() || optionalUser.isPresent()) {
            return;
        }

        User user = new User();
        user.setFullName(userDto.fullName());
        user.setEmail(userDto.email());
        user.setPassword(passwordEncoder.encode(userDto.password()));
        user.setRole(optionalRole.get());

        userRepository.save(user);
    }
}

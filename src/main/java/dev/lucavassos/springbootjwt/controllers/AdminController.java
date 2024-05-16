package dev.lucavassos.springbootjwt.controllers;

import dev.lucavassos.springbootjwt.dtos.UserSignupDto;
import dev.lucavassos.springbootjwt.entities.User;
import dev.lucavassos.springbootjwt.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/admins")
@RestController
public class AdminController {
    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> createAdministrator(@RequestBody UserSignupDto userSignupDto) {
        User createdAdmin = userService.createAdministrator(userSignupDto);

        return ResponseEntity.ok(createdAdmin);
    }
}

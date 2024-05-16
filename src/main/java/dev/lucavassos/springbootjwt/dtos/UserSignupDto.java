package dev.lucavassos.springbootjwt.dtos;

public record UserSignupDto(
        String fullName,
        String email,
        String password
) {}

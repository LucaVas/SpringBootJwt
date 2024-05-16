package dev.lucavassos.springbootjwt.dtos;

public record UserLoginDto(
        String email,
        String password
) {}

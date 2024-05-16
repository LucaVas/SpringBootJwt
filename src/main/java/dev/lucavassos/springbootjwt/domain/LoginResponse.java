package dev.lucavassos.springbootjwt.domain;

public record LoginResponse (
        String token,
        Long expiresIn
) {}

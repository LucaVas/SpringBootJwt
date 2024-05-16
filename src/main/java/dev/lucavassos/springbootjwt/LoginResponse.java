package dev.lucavassos.springbootjwt;

public record LoginResponse (
        String token,
        Long expiresIn
) {}

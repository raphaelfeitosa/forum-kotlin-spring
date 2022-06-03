package br.com.alura.forum.dto

import io.swagger.v3.oas.annotations.media.Schema

data class TokenResponse(
    @field:Schema(
        description = "Token retornado para autorização de acesso aos endpoints",
        example = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhbmFAZW1haWwuY29tIiwicm9sZSI6W3siYXV0aG9yaXR5IjoiTEVJVFVSQV9FU0NSSVRBIn1dLCJleHAiOjE2NDUzNzM3NjR9.K3UlVXdN3GEYqOaHmV7l8OkV-vC54I3xxfyFwN2RxJgaiALaoCIYk9JhZORILRN5QCMGHt8WnlMbf901D3JwjQ"
    )
    val token: String,
    @field:Schema(description = "Tipo do token", example = "Bearer")
    val tipo: String
)
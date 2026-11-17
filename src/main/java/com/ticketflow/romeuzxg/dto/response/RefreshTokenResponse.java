package com.ticketflow.romeuzxg.dto.response;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}

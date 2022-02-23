package com.cellulant.iprs.dto;

import com.cellulant.iprs.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthenticationResponseDTO {
    private User user;
    private String accessToken;
    private String refreshToken;
}

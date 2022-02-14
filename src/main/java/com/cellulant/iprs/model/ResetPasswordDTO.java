package com.cellulant.iprs.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ResetPasswordDTO {
    private String username;
    private String password;
}

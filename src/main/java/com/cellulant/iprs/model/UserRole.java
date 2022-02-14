package com.cellulant.iprs.model;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Builder
public class UserRole {
    private long userID;
    private String userName;
    private int active;
    private long roleID;
    private String roleAlias;
    private String roleName;
    private String permissions;

    public UserRole(long userID, String userName, int active, long roleID, String roleAlias, String roleName, String permissions) {
        this.userID = userID;
        this.userName = userName;
        this.active = active;
        this.roleID = roleID;
        this.roleAlias = roleAlias;
        this.roleName = roleName;
        this.permissions = permissions;
    }
}

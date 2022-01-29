package com.cellulant.iprs.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class UserRole {
    private long userID;
    private String userName;
    private String active;
    private int roleID;
    private String roleName;

    public UserRole(long userID, String userName, String active, int roleID, String roleName) {
        this.userID = userID;
        this.userName = userName;
        this.active = active;
        this.roleID = roleID;
        this.roleName = roleName;
    }
}

package com.etip.app.entity;

import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.etip.app.model.ERole;

@Document(collection = "roles")
public class Role {
	@NotBlank
	@Id
    private String roleId;
    @NotBlank
    private ERole roleName;

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public ERole getRoleName() {
        return roleName;
    }

    public void setRoleName(ERole roleName) {
        this.roleName = roleName;
    }

}

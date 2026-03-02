package com.ozean.ku.enums;

import lombok.Getter;

@Getter
public enum RoleEnum {
    USER("USER", "用户"),
    ADMIN("ADMIN", "管理员"),
    SUPER_ADMIN("SUPER_ADMIN", "超级管理员");

    private final String value;
    private final String desc;

    RoleEnum(String value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static RoleEnum fromValue(String value) {
        for (RoleEnum role : RoleEnum.values()) {
            if (role.value.equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("无效的角色值：" + value);
    }

}

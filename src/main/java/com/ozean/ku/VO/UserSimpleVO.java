package com.ozean.ku.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSimpleVO {

    private Long userId;

    private String username;

    private String description;

    private Integer gender;

    private String avatarUrl;
}

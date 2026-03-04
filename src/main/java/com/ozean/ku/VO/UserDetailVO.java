package com.ozean.ku.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailVO {

    private Long userId;

    private String username;

    private String description;

    private Integer gender;

    private String avatarUrl;

    private Long post_id;

    private Long comment_id;
}

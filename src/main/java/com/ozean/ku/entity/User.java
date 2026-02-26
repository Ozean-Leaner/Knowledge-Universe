package com.ozean.ku.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {

    @Id
    private Integer user_id;

    @Length(min = 3, max = 10)
    private String username;

    @Length(min = 5, max = 15)
    private String password;

    @Email
    private String email;

    @Length(max = 50)
    private String description;

    private Integer gender;

    private String avatarUrl;

    private Integer post_id;

    private Integer comment_id;
}

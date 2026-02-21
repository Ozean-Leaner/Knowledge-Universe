package com.ozean.ku.entity;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Length(min = 3, max = 10)
    public String username;

    @Length(min = 5, max = 15)
    public String password;

    @Email
    public String email;
}

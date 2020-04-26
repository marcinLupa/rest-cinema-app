package com.cinema.application.dto;

import com.cinema.domain.model.role.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {

    private Long id;
    private String name;
    private String surname;
    private Integer age;

    private String username;
    private String password;
    private String passwordConfirmation;
    private String email;
    private Set<String> roles;

    @Override
    public String toString() {
        return "UserDTO id=" + id + "\n" +
                "   name='" + name + "\n" +
                "   surname='" + surname + "\n" +
                "   age=" + age + "\n" +
                "   roles=" + roles + "\n" +
                "   email='" + email + "\n" +
                "   username='" + username + "\n" +
                "   password='" + password + "\n" +
                "   passwordConfirmation='" + passwordConfirmation + "\n";
    }
}

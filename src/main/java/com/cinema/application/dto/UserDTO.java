package com.cinema.application.dto;

import com.cinema.domain.model.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private Role role;
    private String email;

    @Override
    public String toString() {
        return "UserDTO id=" + id  +"\n"+
                "   name='" + name  +"\n"+
                "   surname='" + surname  +"\n"+
                "   age=" + age  +"\n"+
                "   role=" + role  +"\n"+
                "   email='" + email  +"\n";
    }
}

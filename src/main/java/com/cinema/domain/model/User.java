package com.cinema.domain.model;

import com.cinema.domain.model.role.Role;
import com.cinema.domain.model.generic.BaseEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String username;
    private String password;
    private String email;

    private String name;
    private String surname;
    private Integer age;

    @OneToMany(mappedBy = "user")
    private Set<Ticket> tickets;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "user", fetch = FetchType.EAGER)
    private Set<Movie> favoriteMovies;

//    public void addMovies(Movie... movies) {
//        if (movies != null) {
//            for (Movie m : movies) {
//                m.setUser(this);
//                favoriteMovies.add(m);
//            }
//        }
//    }

    @ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles;

}

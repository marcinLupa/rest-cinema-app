package com.cinema.domain.model;

import com.cinema.domain.model.enums.Role;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Setter
@Getter

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String surname;
    private Integer age;
    private String email;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    private Set<Ticket> tickets;

    @OneToMany(cascade = CascadeType.PERSIST,mappedBy = "user",fetch = FetchType.EAGER)
    private Set<Movie> favoriteMovies;

    public void addMovies(Movie ... movies){
        if(movies!=null){
            for (Movie m : movies){
                m.setUser(this);
                favoriteMovies.add(m);
            }
        }
    }
}

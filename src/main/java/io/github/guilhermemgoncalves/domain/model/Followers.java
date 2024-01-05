package io.github.guilhermemgoncalves.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Followers {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name= "users_id")
    private User user;

    @ManyToOne
    @JoinColumn(name= "followers_id")
    private User follower;

}

package io.github.guilhermemgoncalves.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    @Column(name= "post_text")
    private String text;
    private LocalDateTime dateTime;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private User user;

    @PrePersist
    private  void prePersist(){
        if(this.dateTime == null){
            this.setDateTime(LocalDateTime.now());
        }
    }
}

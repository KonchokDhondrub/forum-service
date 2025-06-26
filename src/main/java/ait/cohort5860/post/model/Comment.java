package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String username;
    private String message;
    private LocalDateTime dateCreated = LocalDateTime.now();
    private long likes = 0;
    @ManyToOne
    private Post post;

    public Comment(String user, String message) {
        this.username = user;
        this.message = message;
    }

    public void addLike() {
        likes++;
    }
}

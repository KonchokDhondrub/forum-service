package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Column(name = "username")
    private String username;
    @Column(name = "message", columnDefinition = "TEXT")
    private String message;
    @Column(name = "date_created")
    private LocalDateTime dateCreated = LocalDateTime.now();
    @Column(name = "likes")
    private long likes = 0;

    @ManyToOne
    @Setter
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public Comment(String user, String message, Post post) {
        this.username = user;
        this.message = message;
        this.post = post;
    }

    public void addLike() {
        likes++;
    }
}

package ait.cohort5860.post.model;

import ait.cohort5860.fileTransporter.model.FileEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.*;

@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "posts")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;
    @Setter
    @Column(name = "title")
    private String title;
    @Setter
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    @Setter
    @Column(name = "author")
    private String author;
    @Column(name = "date_created")
    private LocalDateTime dateCreated = LocalDateTime.now();
    @Column(name = "likes")
    private long likes = 0;

    @Setter
    @ManyToMany
    @JoinTable(name = "posts_tags",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "tag"))
    private Set<Tag> tags = new HashSet<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<FileEntity> files = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    public Post(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Post(String title, String content, String author, Set<String> tags) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.tags.addAll(tags.stream().map(Tag::new).toList());
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addLike() {
        likes++;
    }

    public boolean addTag(Tag tag) {
        return tags.add(tag);
    }

    public void addFile(FileEntity file) {
        files.add(file);
    }

}

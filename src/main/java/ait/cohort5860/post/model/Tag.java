package ait.cohort5860.post.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = "tag")
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    private String tag;
    @ManyToMany(mappedBy = "tags")
    private Set<Post> posts = new HashSet<>();

    public Tag(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}

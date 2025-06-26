package ait.cohort5860.post.dao;

import ait.cohort5860.post.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public interface PostRepository extends JpaRepository<Post, Long> {

//    Post findByAuthorIgnoreCase(String author);

    List<Post> findAllByAuthorIgnoreCase(String author);

    List<Post> findDistinctByTagsTagIn(Set<String> tagNames);

    List<Post> findAllByDateCreatedBetween(LocalDateTime from, LocalDateTime to);

}

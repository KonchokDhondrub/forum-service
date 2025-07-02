package ait.cohort5860.security;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomWebSecurity {
    private final PostRepository postRepository;

    public boolean checkPostAuthor(Long postId, String username) {
        try {
            Post post = postRepository.findById(postId).orElse(null);
            return post != null && post.getAuthor().equalsIgnoreCase(username);
        } catch (Exception e) {
            return false;
        }
    }
}

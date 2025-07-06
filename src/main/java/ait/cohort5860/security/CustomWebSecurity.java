package ait.cohort5860.security;

import ait.cohort5860.fileTransporter.dao.FileRepository;
import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomWebSecurity {
    private final PostRepository postRepository;
    private final FileRepository fileRepository;

    public boolean checkPostAuthor(String postId, String username) {
        try {
            Long id = Long.parseLong(postId);
            Post post = postRepository.findById(id).orElse(null);
            return post != null && post.getAuthor().equalsIgnoreCase(username);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean checkFilePostAuthor(String fileId, String username) {
        try {
            Long id = Long.parseLong(fileId);
            Long postId = fileRepository.findById(id).orElse(null).getPost().getId();
            Post post = postRepository.findById(postId).orElse(null);
            return post != null && post.getAuthor().equalsIgnoreCase(username);
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
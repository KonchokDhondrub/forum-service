package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exceptions.PostNotFoundException;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);

        // Handle tags
        Set<String> tags = newPostDto.getTags();
        if (tags != null) {
            for (String tagName: tags) {
                Tag tag = tagRepository.findById(tagName)
                        .orElseGet(() -> tagRepository.save(new Tag(tagName)));
                post.addTag(tag);
            }
        }

        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    
    public void addLike(Long postId) {

    }

    
    public PostDto updatePost(Long postId, NewPostDto newPostDto) {
        return null;
    }

    
    public PostDto deletePost(Long postId) {
        return null;
    }

    
    public PostDto addComment(Long postId, String author, NewCommentDto newCommentDto) {
        return null;
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    
    public Iterable<PostDto> findPostByAuthor(String author) {
        return null;
    }

    
    public Iterable<PostDto> findPostsByTags(Set<String> tags) {
        return null;
    }

    
    public Iterable<PostDto> findPostsByTimePeriod(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}

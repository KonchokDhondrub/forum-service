package ait.cohort5860.post.service;

import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exceptions.PostNotFoundException;
import ait.cohort5860.post.model.Comment;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public PostDto addPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);

        // Handle tags
        handleTags(newPostDto.getTags(), post);

        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void addLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.addLike();
        postRepository.save(post);
    }

    @Override
    public PostDto updatePost(Long postId, NewPostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.setTitle(newPostDto.getTitle());
        post.getTags().clear();
        handleTags(newPostDto.getTags(), post);
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postRepository.delete(post);
        return postDto;
    }

    @Override
    public PostDto addComment(Long postId, String author, NewCommentDto newCommentDto) {
        if (newCommentDto == null) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(author, newCommentDto.getMessage());
        comment.setPost(post);
        post.addComment(comment);
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Iterable<PostDto> findPostByAuthor(String author) {
        List<Post> posts = postRepository.findAllByAuthorIgnoreCase(author);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByTags(Set<String> tags) {
        if (tags == null || tags.isEmpty()) {
            return Collections.emptyList();
        }
        List<Post> posts = postRepository.findDistinctByTagsTagIn(tags);
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByTimePeriod(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime from = dateFrom.atStartOfDay();
        LocalDateTime to = dateTo.atTime(23, 59, 59);

        if (from.isAfter(to)) {
            throw new IllegalArgumentException("dateFrom must not be after dateTo");
        }

        List<Post> posts = postRepository.findAllByDateCreatedBetween(from, to);

        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    private void handleTags(Set<String> tagNames, Post post) {
        if (tagNames != null && !tagNames.isEmpty()) {
            Map<String, Tag> existingTags = tagRepository.findAllById(tagNames).stream()
                    .collect(Collectors.toMap(Tag::getTag, tag -> tag));

            for (String tagName : tagNames) {
                Tag tag = existingTags.getOrDefault(tagName, tagRepository.save(new Tag(tagName)));
                post.addTag(tag);
            }
        }
    }
}

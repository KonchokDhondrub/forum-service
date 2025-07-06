package ait.cohort5860.post.service;

import ait.cohort5860.fileTransporter.dto.FileResponseDto;
import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dao.TagRepository;
import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.dto.exceptions.PostNotFoundException;
import ait.cohort5860.post.model.Comment;
import ait.cohort5860.post.model.Post;
import ait.cohort5860.post.model.Tag;
import ait.cohort5860.post.service.logging.PostLogger;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Collections;
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
    @PostLogger
    public PostDto addPost(String author, NewPostDto newPostDto) {
        Post post = new Post(newPostDto.getTitle(), newPostDto.getContent(), author);

        // Handle tags
        handleTags(newPostDto.getTags(), post);

        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    @PostLogger
    public void addLike(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        post.addLike();
    }

    @Override
    @Transactional
    @PostLogger
    public PostDto updatePost(Long postId, NewPostDto newPostDto) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if (newPostDto.getTitle() != null) {
            post.setTitle(newPostDto.getTitle());
        }
        if (newPostDto.getContent() != null) {
            post.setContent(newPostDto.getContent());
        }
        if (newPostDto.getTags() != null) {
            post.getTags().clear();
            handleTags(newPostDto.getTags(), post); // Tags rewriter
        }
        postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    @PostLogger
    public PostDto deletePost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        postRepository.delete(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    @PostLogger
    public PostDto addComment(Long postId, String author, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        if (newCommentDto.getMessage() == null) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        post.addComment(new Comment(author, newCommentDto.getMessage(), post));
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    @Transactional
    @PostLogger
    public PostDto findPostById(Long postId) {
        return modelMapper.map(postRepository.findById(postId).orElseThrow(PostNotFoundException::new), PostDto.class);
    }

    @Override
    @Transactional
    public Iterable<PostDto> findPostsByAuthor(String author) {
        if (author == null) {
            return Collections.emptyList();
        }
        return postRepository.findByAuthorIgnoreCase(author)
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    @Transactional
    public Iterable<PostDto> findPostsByTags(Set<String> tags) {
        return postRepository.findDistinctByTagsTagInIgnoreCase(tags)
                .map(post -> modelMapper.map(post, PostDto.class))
                .collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Iterable<PostDto> findPostsByTimePeriod(LocalDate dateFrom, LocalDate dateTo) {
        if (dateFrom == null || dateTo == null) {
            throw new IllegalArgumentException("dateFrom and dateTo must be not empty");
        }
        if (dateFrom.isAfter(dateTo)) {
            throw new IllegalArgumentException("dateFrom must not be after dateTo");
        }

        return postRepository.findAllByDateCreatedBetween(dateFrom.atStartOfDay(), dateTo.atTime(23, 59))
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

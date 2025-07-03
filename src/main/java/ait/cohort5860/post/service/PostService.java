package ait.cohort5860.post.service;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;

import java.time.LocalDate;
import java.util.Set;

public interface PostService {
    PostDto addPost(String author, NewPostDto newPostDto);

    void addLike(Long postId);

    PostDto updatePost(Long postId, NewPostDto newPostDto);

    PostDto deletePost(Long postId);

    PostDto addComment(Long postId, String author, NewCommentDto newCommentDto);

    PostDto findPostById(Long postId);

    Iterable<PostDto> findPostsByAuthor(String author);

    Iterable<PostDto> findPostsByTags(Set<String> tags);

    Iterable<PostDto> findPostsByTimePeriod(LocalDate dateFrom, LocalDate dateTo);


}
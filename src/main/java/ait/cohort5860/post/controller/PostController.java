package ait.cohort5860.post.controller;

import ait.cohort5860.post.dto.NewCommentDto;
import ait.cohort5860.post.dto.NewPostDto;
import ait.cohort5860.post.dto.PostDto;
import ait.cohort5860.post.service.PostService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/forum")
public class PostController {
    private final PostService postService;

    @PostMapping("/post/{author}")
    @ResponseStatus(HttpStatus.CREATED)
    public PostDto addPost(@PathVariable String author, @RequestBody @Valid NewPostDto newPostDto) {
        return postService.addPost(author, newPostDto);
    }

    @PatchMapping("/post/{postId}/like")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLike(@PathVariable Long postId) {
        postService.addLike(postId);
    }

    @PatchMapping("/post/{postId}")
    public PostDto updatePost(@PathVariable Long postId, @RequestBody NewPostDto newPostDto) {
        return postService.updatePost(postId, newPostDto);
    }

    @DeleteMapping("/post/{postId}")
    public PostDto deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @PatchMapping("/post/{postId}/comment/{commenter}")
    public PostDto addComment(@PathVariable Long postId, @PathVariable String commenter, @RequestBody @Valid NewCommentDto newCommentDto) {
        return postService.addComment(postId, commenter, newCommentDto);
    }

    @GetMapping("/post/{postId}")
    public PostDto findPostById(@PathVariable Long postId) {
        return postService.findPostById(postId);
    }

    @GetMapping("/posts/author/{user}")
    public Iterable<PostDto> findPostByAuthor(@PathVariable String user) {
        return postService.findPostsByAuthor(user);
    }

    @GetMapping("/posts/tags")
    public Iterable<PostDto> findPostsByTags(@RequestParam("values") Set<String> tags) {
        return postService.findPostsByTags(tags);
    }

    @GetMapping("/posts/period")
    public Iterable<PostDto> findPostsByTimePeriod(
            @RequestParam(defaultValue = "0001-01-01") @NotNull(message = "Date 'to' required") LocalDate dateFrom,
            @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}") @NotNull(message = "Date 'to' required") LocalDate dateTo) {
        return postService.findPostsByTimePeriod(dateFrom, dateTo);
    }

}

package ait.cohort5860.post.dto;

import ait.cohort5860.fileTransporter.dto.FileResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder

//@ResponseStatus(HttpStatus.CREATED)
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String author;
    private LocalDateTime dateCreated;
    @Singular
    private Set<String> tags;
    private Long likes;
    @Singular
    private List<CommentDto> comments;
    @Singular
    private List<FileResponseDto> files;
}

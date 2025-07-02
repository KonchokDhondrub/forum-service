package ait.cohort5860.post.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewPostDto {
    @NotBlank(message = "Title is required")
    @Size(min = 4, max = 20, message = "Title must be between 4 and 20 characters")
    private String title;
    @NotBlank(message = "Content is required")
    @Size(min = 10, max = 1000, message = "Content must be between 10 and 1000 characters")
    private String content;
    @NotNull(message = "Tags are required")
    @Size(min = 1, max = 20, message = "Tags must contain between 1 and 20 items")
    private Set<@NotBlank(message = "Tag must not be blank") String> tags;

}

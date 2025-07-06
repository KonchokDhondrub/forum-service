package ait.cohort5860.fileTransporter.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileResponseDto {
    private String filename;
    private String downloadUrl;
    private String contentType;
    private long size;

}


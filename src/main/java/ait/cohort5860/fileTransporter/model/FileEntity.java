package ait.cohort5860.fileTransporter.model;

import ait.cohort5860.post.model.Post;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String contentType;

    @Lob
    @Column(nullable = false)
    private byte[] data;

    @Setter
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    public FileEntity(String fileName, String contentType, byte[] data, Post post) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.data = data;
        this.post = post;
    }

    @Transient
    public String getFilename() {
        return fileName;
    }

    @Transient
    public String getDownloadUrl() {
        return "/files/download/" + id;
    }

    @Transient
    public long getSize() {
        return data != null ? data.length : 0;
    }

}


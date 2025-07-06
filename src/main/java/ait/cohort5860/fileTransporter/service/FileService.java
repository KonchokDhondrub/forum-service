package ait.cohort5860.fileTransporter.service;

import ait.cohort5860.fileTransporter.dao.FileRepository;
import ait.cohort5860.fileTransporter.dto.FileResponseDto;
import ait.cohort5860.fileTransporter.model.FileEntity;
import ait.cohort5860.post.dao.PostRepository;
import ait.cohort5860.post.dto.exceptions.PostNotFoundException;
import ait.cohort5860.post.model.Post;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Transactional
    public FileResponseDto storeFile(Long postId, MultipartFile multipartFile) {
        Post post = postRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        try {
            FileEntity file = new FileEntity(
                    multipartFile.getOriginalFilename(),
                    multipartFile.getContentType(),
                    multipartFile.getBytes(),
                    post
            );
            FileEntity saved = fileRepository.save(file);

            post.addFile(saved);

            return new FileResponseDto(
                    saved.getFilename(),
                    saved.getDownloadUrl(),
                    saved.getContentType(),
                    saved.getSize()
            );
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Не удалось сохранить файл", e);
        }
    }


    @Transactional
    public FileEntity getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Файл не найден"));
    }

    @Transactional
    public FileResponseDto deleteFile(Long fileId) {
        FileEntity file = fileRepository.findById(fileId)
                .orElseThrow(PostNotFoundException::new);

        fileRepository.delete(file);

        return new FileResponseDto(
                file.getFilename(),
                file.getDownloadUrl(),
                file.getContentType(),
                file.getSize()
        );
    }

}


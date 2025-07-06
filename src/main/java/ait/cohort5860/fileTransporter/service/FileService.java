package ait.cohort5860.fileTransporter.service;

import ait.cohort5860.fileTransporter.dao.FileRepository;
import ait.cohort5860.fileTransporter.dto.FileResponseDto;
import ait.cohort5860.fileTransporter.model.FileEntity;
import lombok.RequiredArgsConstructor;
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

    @Transactional
    public FileResponseDto storeFile(MultipartFile multipartFile) {
        try {
            FileEntity file = new FileEntity(multipartFile.getOriginalFilename(),multipartFile.getContentType(),multipartFile.getBytes());

            FileEntity saved = fileRepository.save(file);

            return new FileResponseDto(saved.getFileName(), "/files/download/" + saved.getId());
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Не удалось сохранить файл", e);
        }
    }

    @Transactional
    public FileEntity getFile(Long id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Файл не найден"));
    }
}


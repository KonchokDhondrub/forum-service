package ait.cohort5860.fileTransporter.controller;

import ait.cohort5860.fileTransporter.dto.FileResponseDto;
import ait.cohort5860.fileTransporter.model.FileEntity;
import ait.cohort5860.fileTransporter.service.FileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileResponseDto> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Файл не должен быть пустым");
        }
        long maxFileSize = 5 * 1024 * 1024; // 5 МБ
        if (file.getSize() > maxFileSize) {
            throw new ResponseStatusException(HttpStatus.PAYLOAD_TOO_LARGE, "Файл слишком большой");
        }

        String filename = file.getOriginalFilename();
        if (filename == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Имя файла не может быть пустым");
        }

        if (!filename.toLowerCase().matches(".*\\.(txt|pdf|jpg|png|jpeg|docx?|xlsx?|pptx?)$")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недопустимый формат файла");
        }

        FileResponseDto dto = fileService.storeFile(file);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable Long id) {
        FileEntity file = fileService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(file.getData());
    }
}


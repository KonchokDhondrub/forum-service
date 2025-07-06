package ait.cohort5860.fileTransporter.dao;

import ait.cohort5860.fileTransporter.model.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends JpaRepository<FileEntity, Long> {
}

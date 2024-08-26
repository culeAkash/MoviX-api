package com.project.file.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Entity(name = "files")
@Builder
@Data
public class File {
    @Id
    private String fileId;
    private String type;
    private String filePath;
}

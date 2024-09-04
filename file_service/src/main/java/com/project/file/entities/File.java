package com.project.file.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity(name = "files")
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class File {
    @Id
    private String fileId;
    private String type;
    private String filePath;
}

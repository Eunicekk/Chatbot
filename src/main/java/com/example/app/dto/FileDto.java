package com.example.app.dto;

import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class FileDto {
    private Long fileNumber;
    private String fileName;
    private String fileUploadPath;
    private String fileUuid;
    private Long boardNumber;
}

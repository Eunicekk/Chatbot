package com.example.app.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@Data
@Component
public class BoardVo {
    private Long boardNumber;
    private String boardTitle;
    private String boardContent;
    private String boardRegisterDate;
    private String boardUpdateDate;
    private Long userNumber;
    private String userId;
    private String fileName;
    private String fileUploadPath;
    private String fileUuid;
}

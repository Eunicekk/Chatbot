package com.example.app.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@RequiredArgsConstructor @NoArgsConstructor
public class ReplyDto {
    private Long replyNumber;
    @NonNull
    private String replyContent;
    @NonNull
    private String replyRegisterDate;
    @NonNull
    private String replyUpdateDate;
    @NonNull
    private Long boardNumber;
    @NonNull
    private Long userNumber;
}

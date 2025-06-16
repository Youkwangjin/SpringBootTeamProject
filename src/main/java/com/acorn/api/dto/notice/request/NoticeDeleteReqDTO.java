package com.acorn.api.dto.notice.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class NoticeDeleteReqDTO {

    @NotNull
    @Positive
    private Integer noticeId;
}
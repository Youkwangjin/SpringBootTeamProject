package com.acorn.api.code.contact;

import lombok.Getter;

@Getter
public enum ContactStatus {

    CONTACT_STATUS_PENDING(0, "문의 대기"),
    CONTACT_STATUS_PROGRESS(1, "문의 처리중"),
    CONTACT_STATUS_COMPLETED(2, "문의 처리완료"),
    CONTACT_STATUS_CANCELLED(3, "문의 취소"),

    USER(0, "사용자"),
    OWNER(1, "공급자");

    private final Integer code;
    private final String description;

    ContactStatus(Integer code, String description) {
        this.code = code;
        this.description = description;
    }
}
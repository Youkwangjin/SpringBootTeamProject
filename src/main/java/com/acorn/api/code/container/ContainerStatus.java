package com.acorn.api.code.container;

import lombok.Getter;

@Getter
public enum ContainerStatus {

    CONTAINER_STATUS_PENDING(0, "사용 대기"),
    CONTAINER_STATUS_AVAILABLE(1, "사용 가능"),
    CONTAINER_STATUS_USE(2, "사용 중"),
    CONTAINER_STATUS_UNAVAILABLE(3, "사용 불가능"),
    CONTAINER_APPROVAL_STATUS_PENDING(0, "승인 대기"),
    CONTAINER_APPROVAL_STATUS_APPROVED(1, "승인 완료"),
    CONTAINER_APPROVAL_STATUS_REJECTED(2, "승인 거절");

    private final int code;
    private final String description;

    ContainerStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
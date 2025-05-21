package com.acorn.api.code.container;

import com.acorn.api.code.common.ApiHttpErrorCode;
import com.acorn.api.exception.global.AcontainerException;
import lombok.Getter;

import java.util.Objects;

@Getter
public enum ContainerStatus {

    CONTAINER_STATUS_PENDING(0, "사용 대기", Type.USE),
    CONTAINER_STATUS_AVAILABLE(1, "사용 가능", Type.USE),
    CONTAINER_STATUS_USE(2, "사용 중", Type.USE),
    CONTAINER_STATUS_UNAVAILABLE(3, "사용 불가", Type.USE),
    CONTAINER_APPROVAL_STATUS_PENDING(0, "승인 대기", Type.APPROVAL),
    CONTAINER_APPROVAL_STATUS_IN_REVIEW(1, "검토 중", Type.APPROVAL),
    CONTAINER_APPROVAL_STATUS_APPROVED(2, "승인 완료", Type.APPROVAL),
    CONTAINER_APPROVAL_STATUS_REJECTED(3, "승인 거절", Type.APPROVAL);

    public enum Type { USE, APPROVAL }

    private final Integer code;
    private final String description;
    private final Type type;

    ContainerStatus(final Integer code, final String description, final Type type) {
        this.code = code;
        this.description = description;
        this.type = type;
    }

    public static ContainerStatus fromUseCode(Integer code) {
        for (ContainerStatus status : values()) {
            if (status.type == Type.USE
                    && Objects.equals(status.code, code)) {
                return status;
            }
        }
        throw new AcontainerException(ApiHttpErrorCode.BAD_REQUEST_ERROR);
    }

    public static ContainerStatus fromApprovalCode(Integer code) {
        for (ContainerStatus status : values()) {
            if (status.type == Type.APPROVAL
                    && Objects.equals(status.code, code)) {
                return status;
            }
        }
        throw new AcontainerException(ApiHttpErrorCode.BAD_REQUEST_ERROR);
    }
}
package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {
    USER_FOUND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "UFE", "존재하지 않는 사용자 입니다."),
    OWNER_FOUND_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "OFE", "존재하지 않는 공급자 입니다."),
    RESERVATION_STATUS_ACTIVE(HttpStatus.FORBIDDEN, "RSA", "예약 중인 사용자는 정보수정이 불가능 합니다."),

    BOARD_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "BNF", "게시글이 존재하지 않습니다."),

    CONTAINER_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "CNF", "창고가 존재하지 않습니다."),
    CONTAINER_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "CUF", "창고 정보 수정에 실패하였습니다."),
    CONTAINER_OWNER_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, "COM", "해당 창고의 소유자 정보가 일치하지 않습니다."),

    CONTAINER_REVIEW_NOT_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "CRA", "사용 불가 상태에서만 검토할 수 있습니다."),

    CONTAINER_APPROVAL_NOT_PENDING(HttpStatus.INTERNAL_SERVER_ERROR, "CAP", "승인 대기 상태에서만 수정 및 삭제가 가능합니다."),
    CONTAINER_STATUS_NOT_PENDING(HttpStatus.INTERNAL_SERVER_ERROR, "CSP", "사용 대기 상태에서만 수정 및 삭제가 가능합니다."),
    CONTAINER_STATUS_NOT_UNAVAILABLE_FOR_APPROVAL(HttpStatus.INTERNAL_SERVER_ERROR, "CNA", "사용 불가 상태에서만 승인 처리가 가능합니다."),
    CONTAINER_STATUS_NOT_UNAVAILABLE_FOR_REJECT(HttpStatus.INTERNAL_SERVER_ERROR, "CNA", "사용 불가 상태에서만 승인 거부 처리가 가능합니다."),
    CONTAINER_STATUS_NOT_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "CSU", "사용 불가 상태에서만 원복이 가능합니다."),
    CONTAINER_NOT_AVAILABLE_CANCEL(HttpStatus.INTERNAL_SERVER_ERROR, "CNC", "승인 취소는 사용 가능 상태에서만 가능합니다."),

    CONTAINER_ALREADY_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CAR", "해당 창고는 이미 검토 처리된 상태입니다."),
    CONTAINER_ALREADY_APPROVED(HttpStatus.INTERNAL_SERVER_ERROR, "CAA", "해당 창고는 이미 승인 처리된 상태입니다."),
    CONTAINER_ALREADY_REJECT(HttpStatus.INTERNAL_SERVER_ERROR, "CAJ", "해당 창고는 이미 승인거부 처리된 상태입니다."),

    CONTAINER_APPROVAL_NOT_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CAR", "검토중 상태의 창고만 승인 처리할 수 있습니다."),
    CONTAINER_REJECT_NOT_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CJR", "검토중 상태의 창고만 승인거부 처리할 수 있습니다."),
    CONTAINER_APPROVAL_NOT_APPROVED(HttpStatus.INTERNAL_SERVER_ERROR, "CNA", "승인 완료 상태의 창고만 승인 취소가 가능합니다."),
    CONTAINER_APPROVAL_NOT_REJECT(HttpStatus.INTERNAL_SERVER_ERROR, "CNR", "승인 거절 상태에서만 취소가 가능합니다."),

    FILE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "FNF", "파일 데이터가 존재하지 않습니다."),
    FILE_PATH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FPE", "파일 업로드 디렉토리가 존재하지 않습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FUE", "파일 업로드에 실패하였습니다."),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FDE", "파일 삭제 중 문제가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}
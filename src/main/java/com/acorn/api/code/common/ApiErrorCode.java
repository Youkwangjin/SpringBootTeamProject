package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiErrorCode {
    USER_FOUND_ERROR(HttpStatus.NOT_FOUND, "UFE", "존재하지 않는 사용자 입니다."),
    OWNER_FOUND_ERROR(HttpStatus.NOT_FOUND, "OFE", "존재하지 않는 공급자 입니다."),

    RESERVATION_STATUS_ACTIVE(HttpStatus.FORBIDDEN, "RSA", "예약 중인 사용자는 회원수정 및 탈퇴가 불가능 합니다."),
    PAYMENT_HISTORY_ACTIVE(HttpStatus.FORBIDDEN, "PHA", "이미 결제한 내역이 존재하여 회원수정 및 탈퇴가 불가능합니다."),

    BOARD_NOT_FOUND(HttpStatus.NOT_FOUND, "BNF", "게시글이 존재하지 않습니다."),
    NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NNF", "공지사항이 존재하지 않습니다."),
    CONTACT_NOT_FOUND(HttpStatus.NOT_FOUND, "CNF","문의내역이 존재하지 않습니다."),
    FAQ_NOT_FOUND(HttpStatus.NOT_FOUND, "FNF","FAQ가 존재하지 않습니다."),

    CONTACT_NOT_FILE_DATA(HttpStatus.NOT_FOUND, "CNF","문의내역에 첨부된 파일이 존재하지 않습니다."),
    CONTACT_NOT_WAITING(HttpStatus.BAD_REQUEST, "CNW", "문의대기 상태에서만 수정, 삭제, 취소가 가능합니다."),
    CONTACT_STATUS_NOT_PENDING(HttpStatus.BAD_REQUEST, "CSP", "현재 문의가 대기 상태일 때만 검토로 변경할 수 있습니다."),
    CONTACT_STATUS_NOT_PROCESSING(HttpStatus.BAD_REQUEST, "CNP", "현재 문의가 처리중 상태일 때만 답변을 등록할 수 있습니다."),

    CONTAINER_NOT_FOUND(HttpStatus.NOT_FOUND, "CNF", "창고가 존재하지 않습니다."),
    CONTAINER_UPDATE_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "CUF", "창고 정보 수정에 실패하였습니다."),
    CONTAINER_OWNER_MISMATCH(HttpStatus.INTERNAL_SERVER_ERROR, "COM", "해당 창고의 소유자 정보가 일치하지 않습니다."),

    CONTAINER_REVIEW_NOT_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "CRA", "사용 불가 상태에서만 검토할 수 있습니다."),

    CONTAINER_APPROVAL_NOT_PENDING(HttpStatus.INTERNAL_SERVER_ERROR, "CAP", "승인 대기 상태에서만 수정 및 삭제가 가능합니다."),
    CONTAINER_MODIFY_DELETE_NOT_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "CMD", "사용 불가 상태에서만 수정 및 삭제가 가능합니다."),
    CONTAINER_STATUS_NOT_UNAVAILABLE_FOR_APPROVAL(HttpStatus.INTERNAL_SERVER_ERROR, "CNA", "사용 불가 상태에서만 승인 처리가 가능합니다."),
    CONTAINER_STATUS_NOT_UNAVAILABLE_FOR_REJECT(HttpStatus.INTERNAL_SERVER_ERROR, "CNA", "사용 불가 상태에서만 승인 거부 처리가 가능합니다."),
    CONTAINER_STATUS_NOT_UNAVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "CSU", "사용 불가 상태에서만 원복이 가능합니다."),
    CONTAINER_NOT_AVAILABLE_CANCEL(HttpStatus.INTERNAL_SERVER_ERROR, "CNC", "승인 취소는 사용 가능 상태에서만 가능합니다."),

    CONTAINER_ALREADY_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CAR", "해당 창고는 이미 검토 처리된 상태입니다."),
    CONTAINER_ALREADY_APPROVED(HttpStatus.INTERNAL_SERVER_ERROR, "CAA", "해당 창고는 이미 승인 처리된 상태입니다."),
    CONTAINER_ALREADY_REVIEW_CANCEL(HttpStatus.INTERNAL_SERVER_ERROR, "CAC", "해당 창고는 이미 검토 취소 처리된 상태입니다."),
    CONTAINER_ALREADY_REJECT(HttpStatus.INTERNAL_SERVER_ERROR, "CAJ", "해당 창고는 이미 승인 거부 처리된 상태입니다."),

    CONTAINER_REVIEW_CANCEL_NOT_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CRC", "검토중 상태의 창고만 검토 취소 처리 할 수 있습니다."),
    CONTAINER_APPROVAL_NOT_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CAR", "검토중 상태의 창고만 승인 처리할 수 있습니다."),
    CONTAINER_REJECT_NOT_REVIEW(HttpStatus.INTERNAL_SERVER_ERROR, "CJR", "검토중 상태의 창고만 승인거부 처리할 수 있습니다."),
    CONTAINER_APPROVAL_NOT_APPROVED(HttpStatus.INTERNAL_SERVER_ERROR, "CNA", "승인 완료 상태의 창고만 승인 취소가 가능합니다."),
    CONTAINER_APPROVAL_NOT_REJECT(HttpStatus.INTERNAL_SERVER_ERROR, "CNR", "승인 거절 상태에서만 취소가 가능합니다."),

    RESERVE_CONTAINER_NOT_FOUND(HttpStatus.NOT_FOUND, "CRN", "창고 예약 정보가 존재하지 않습니다."),
    RESERVE_CONTAINERNOT_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "CRV", "사용 가능 상태에서만 예약이 가능합니다."),
    RESERVE_CONTAINER_NOT_APPROVED(HttpStatus.INTERNAL_SERVER_ERROR, "CRP", "승인 완료 상태에서만 예약이 가능합니다."),
    RESERVE_CONTAINER_ALREADY(HttpStatus.INTERNAL_SERVER_ERROR, "CRD", "이미 예약이 완료된 창고입니다."),
    RESERVE_CONTAINER_NOT_PENDING(HttpStatus.INTERNAL_SERVER_ERROR, "CRW", "예약 대기 상태에서만 예약 취소가 가능합니다."),

    PAYMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "PNF", "결제 내역이 존재하지 않습니다."),
    PAYMENT_DATA_INCONSISTENCY(HttpStatus.INTERNAL_SERVER_ERROR, "PDI", "결제 데이터에 이상이 있습니다. 관리자에게 문의하세요."),
    PAYMENT_ALREADY_COMPLETED(HttpStatus.INTERNAL_SERVER_ERROR, "PAC", "이미 결제가 완료된 예약입니다."),
    PAYMENT_DENIED_ON_CANCELED(HttpStatus.INTERNAL_SERVER_ERROR, "PDC", "예약 취소건은 결제를 진행할 수 없습니다."),
    PAYMENT_CANCEL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "PCE", "결제가 완료된 건만 취소가 가능합니다."),
    PAYMENT_CANCEL_EXPIRED(HttpStatus.BAD_REQUEST, "PCE", "결제 취소 가능 기간이 지났습니다."),

    FILE_NOT_FOUND(HttpStatus.INTERNAL_SERVER_ERROR, "FNF", "파일 데이터가 존재하지 않습니다."),
    FILE_PATH_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FPE", "파일 업로드 디렉토리가 존재하지 않습니다."),
    FILE_UPLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FUE", "파일 업로드에 실패하였습니다."),
    FILE_DELETE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FDE", "파일 삭제 중 문제가 발생했습니다."),
    FILE_DOWNLOAD_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "FDE", "파일 다운로드 중 문제가 발생했습니다."),

    NAVER_API_CALL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "NAC", "네이버 API 호출 중 오류가 발생했습니다."),
    NAVER_API_INVALID_RESPONSE(HttpStatus.INTERNAL_SERVER_ERROR, "NIR", "네이버 API 응답이 유효하지 않습니다."),

    KAKAOPAY_API_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "KPF", "카카오페이 결제 요청 중 오류가 발생했습니다."),
    KAKAOPAY_CANCEL_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "KCF", "카카오페이 결제 취소 응답 중 오류가 발생했습니다.");

    private final HttpStatus httpStatus;

    private final String errorDivisionCode;

    private final String errorMsg;

    ApiErrorCode(final HttpStatus httpStatus, final String errorDivisionCode, final String errorMsg) {
        this.httpStatus = httpStatus;
        this.errorDivisionCode = errorDivisionCode;
        this.errorMsg = errorMsg;
    }
}
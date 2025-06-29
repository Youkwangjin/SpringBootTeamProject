package com.acorn.api.code.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApiSuccessCode {
    REGISTER_SUCCESS(HttpStatus.CREATED,      "RIS", "회원가입이 정상적으로 완료되었습니다."),
    INFO_UPDATE_SUCCESS(HttpStatus.OK,        "IUS", "회원정보가 성공적으로 수정되었습니다."),
    INFO_DELETE_SUCCESS(HttpStatus.OK,        "IDS", "그동안 이용해 주셔서 감사합니다."),
    ADMIN_DELETE_SUCCESS(HttpStatus.OK,       "ADS", "회원정보가 성공적으로 삭제되었습니다."),

    LOGIN_SUCCESS(HttpStatus.OK,              "LIS", "로그인이 정상적으로 완료되었습니다."),
    ADMIN_PASSWORD_SUCCESS(HttpStatus.OK,     "APS", "비밀번호가 검증되었습니다."),

    BOARD_SAVE_SUCCESS(HttpStatus.OK,         "BSS", "게시글이 성공적으로 등록되었습니다."),
    BOARD_UPDATE_SUCCESS(HttpStatus.OK,       "BUS", "게시글이 성공적으로 수정되었습니다."),
    BOARD_DELETE_SUCCESS(HttpStatus.OK,       "BDS", "게시글이 성공적으로 삭제되었습니다."),

    NOTICE_SAVE_SUCCESS(HttpStatus.OK,        "NSS", "공지사항이 성공적으로 등록되었습니다."),
    NOTICE_UPDATE_SUCCESS(HttpStatus.OK,      "NUS", "공지사항이 성공적으로 수정되었습니다."),
    NOTICE_DELETE_SUCCESS(HttpStatus.OK,      "NDS", "공지사항이 성공적으로 삭제되었습니다."),

    CONTACT_SAVE_SUCCESS(HttpStatus.OK,       "CSS", "1:1 문의가 성공적으로 등록되었습니다."),
    CONTACT_UPDATE_SUCCESS(HttpStatus.OK,     "CUS", "1:1 문의가 성공적으로 수정되었습니다."),
    CONTACT_DELETE_SUCCESS(HttpStatus.OK,     "CDS", "1:1 문의가 성공적으로 삭제되었습니다."),
    CONTACT_CANCEL_SUCCESS(HttpStatus.OK,     "CCS", "1:1 문의가 성공적으로 취소되었습니다."),
    CONTACT_REVIEW_SUCCESS(HttpStatus.OK,     "CRS", "해당 문의내역이 검토 상태로 변경되었습니다."),
    CONTACT_ANSWER_SUCCESS(HttpStatus.OK,     "CAS", "답변이 성공적으로 등록되었습니다."),

    FAQ_READ_OK(HttpStatus.OK,               "FCO", "FAQ 내용이 조회되었습니다."),
    FAQ_SAVE_SUCCESS(HttpStatus.CREATED,     "FCS", "FAQ가 성공적으로 등록되었습니다."),
    FAQ_UPDATE_SUCCESS(HttpStatus.OK,        "FUS", "FAQ가 성공적으로 수정되었습니다."),
    FAQ_DELETE_SUCCESS(HttpStatus.OK,        "FDS", "FAQ가 성공적으로 삭제되었습니다."),

    CONTAINER_REGISTER_SUCCESS(HttpStatus.OK, "CRS", "창고정보가 성공적으로 등록되었습니다."),
    CONTAINER_SELECT_SUCCESS(HttpStatus.OK,   "CSS", "창고정보가 성공적으로 조회되었습니다."),
    CONTAINER_UPDATE_SUCCESS(HttpStatus.OK,   "CUS", "창고정보가 성공적으로 수정되었습니다."),
    CONTAINER_DELETE_SUCCESS(HttpStatus.OK,   "CDS", "창고정보가 성공적으로 삭제되었습니다."),

    CONTAINER_PENDING_SUCCESS(HttpStatus.OK,  "CPS", "해당 창고가 승인 대기 상태로 변경되었습니다."),
    CONTAINER_REVIEW_SUCCESS(HttpStatus.OK,   "CRS", "해당 창고가 검토 상태로 변경되었습니다."),
    CONTAINER_APPROVAL_SUCCESS(HttpStatus.OK, "CAS", "해당 창고가 승인 상태로 변경되었습니다."),
    CONTAINER_REJECT_SUCCESS(HttpStatus.OK,   "CJS", "해당 창고가 승인거부 상태로 변경되었습니다."),

    RESERVE_CONTAINER_SUCCESS(HttpStatus.OK,  "RCS", "창고 예약이 성공적으로 완료되었습니다."),
    RESERVE_CONTAINER_CANCEL(HttpStatus.OK,   "RCC", "창고 예약이 취소되었습니다."),

    KAKAOPAY_READY_SUCCESS(HttpStatus.OK,     "KRS", "카카오페이 결제 준비가 완료되었습니다."),
    KAKAOPAY_APPROVE_SUCCESS(HttpStatus.OK,   "KAS", "카카오페이 결제가 완료되었습니다."),
    PAYMENT_CANCEL_SUCCESS(HttpStatus.OK,     "PCS", "결제가 성공적으로 취소되었습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    ApiSuccessCode(final HttpStatus httpStatus, final String code, final String message) {
        this.httpStatus = httpStatus;
        this.code = code;
        this.message = message;
    }
}
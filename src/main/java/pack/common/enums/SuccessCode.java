package pack.common.enums;


import lombok.Getter;

@Getter
public enum SuccessCode {

    /**
     * ******************************* Success CodeList ***************************************
     */

    // 조회 성공 코드 (HTTP Response: 200 OK)
    SELECT_SUCCESS(200, "200", "SELECT SUCCESS"),

    // 삭제 성공 코드 (HTTP Response: 200 OK)
    DELETE_SUCCESS(200, "200", "DELETE SUCCESS"),

    // 삽입 성공 코드 (HTTP Response: 201 Created)
    INSERT_SUCCESS(201, "201", "INSERT SUCCESS"),

    // 수정 성공 코드 (HTTP Response: 201 Created)
    UPDATE_SUCCESS(200, "204", "UPDATE SUCCESS");

    // 성공 코드의 '코드 상태'를 반환
    private final int status;

    // 성공 코드의 '코드 값'을 반환
    private final String code;

    // 성공 코드의 '코드 메시지'를 반환
    private final String message;

    SuccessCode(final int status, final String code, final String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

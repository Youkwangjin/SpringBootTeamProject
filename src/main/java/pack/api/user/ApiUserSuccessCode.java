package pack.api.user;


import lombok.Getter;

@Getter
public enum ApiUserSuccessCode {

    /**
     * ******************************* Success CodeList ***************************************
     */

    WAREHOUSE_RESERVATION_CREATED(201, "WRC", "창고 예약이 성공적으로 완료되었습니다."),

    WAREHOUSE_RESERVATION_UPDATED(200, "WRU", "창고 예약 정보가 수정되었습니다."),

    WAREHOUSE_RESERVATION_DELETE(200, "WRD", "창고 예약이 취소되었습니다.");


    // user API 성공 코드의 '코드 상태'를 반환
    private final int userApiStatus;

    //  user API 성공 코드의 '코드 값'을 반환
    private final String userApiCode;

    //  user API 성공 코드의 '코드 메시지'를 반환
    private final String userApiMessage;

    ApiUserSuccessCode(final int userApiStatus, final String userApiCode, final String userApiMessage) {
        this.userApiStatus = userApiStatus;
        this.userApiCode = userApiCode;
        this.userApiMessage = userApiMessage;
    }
}

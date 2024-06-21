package pack.common.response;


import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import pack.common.enums.code.ErrorCode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global Exception Handler 에서 발생한 에러에 대한 응답 처리를 관리
 */

@Getter
public class ErrorResponse {

    // 에러 상태 코드
    // private final int errorStatus;

    // 에러 구분 코드
    // private final String errorDivisionCode;

    // 에러 메시지
    // private final String errorMsg;

    // 상세 에러 메시지
    // private final List<FieldError> errors;

    // 에러 이유
    // private final String errorReason;


}

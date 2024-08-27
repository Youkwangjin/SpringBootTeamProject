package pack.controller.owner;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pack.api.code.common.ApiErrorCode;
import pack.api.code.common.ApiSuccessCode;
import pack.api.code.owner.ApiOwnerErrorCode;
import pack.api.code.owner.ApiOwnerSuccessCode;
import pack.api.response.ApiErrorResponse;
import pack.api.response.ApiSuccessResponse;
import pack.service.owner.OwnerService;

@RestController
@RequiredArgsConstructor
public class OwnerValidationController {

    private static final Logger log = LoggerFactory.getLogger(OwnerValidationController.class);
    private final OwnerService ownerService;

    @GetMapping("/api/auth/owner/emailCheck")
    public ResponseEntity<Object> emailCheck(@RequestParam String ownerEmail) {

        log.info(" *****************************    emailCheck START    ***************************** ");

        boolean ownerEmailDuplicate = ownerService.isOwnerEmailDuplicate(ownerEmail);

        if (ownerEmailDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiErrorCode.EMAIL_DUPLICATED.getErrorStatus())
                    .errorDivisionCode(ApiErrorCode.EMAIL_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiErrorCode.EMAIL_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiErrorCode.EMAIL_DUPLICATED.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiSuccessCode.EMAIL_AVAILABLE.getStatus())
                    .resultMsg(ApiSuccessCode.EMAIL_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiSuccessCode.EMAIL_AVAILABLE.getStatus()).body(emailCheckResponse);
        }

    }

    @GetMapping("/api/auth/owner/businessNumCheck")
    public ResponseEntity<Object> telBusinessNumCheck(@RequestParam String ownerBusinessNum) {
        log.info(" *****************************    telBusinessNumCheck START    ***************************** ");

        boolean ownerBusinessNumDuplicate = ownerService.isBusinessNumDuplicate(ownerBusinessNum);

        if (ownerBusinessNumDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiOwnerErrorCode.OWNER_AUTHENTICATION_FAILED.getOwnerErrorStatus())
                    .errorDivisionCode(ApiOwnerErrorCode.OWNER_AUTHENTICATION_FAILED.getOwnerErrorDivisionCode())
                    .errorMsg(ApiOwnerErrorCode.OWNER_AUTHENTICATION_FAILED.getOwnerErrorMsg())
                    .build();
            return ResponseEntity.status(ApiOwnerErrorCode.OWNER_AUTHENTICATION_FAILED.getOwnerErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> businessNumCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiOwnerSuccessCode.BUSINESS_NUMBER_AVAILABLE.getOwnerApiStatus())
                    .resultMsg(ApiOwnerSuccessCode.BUSINESS_NUMBER_AVAILABLE.getOwnerApiMessage())
                    .build();
            return ResponseEntity.status(ApiOwnerSuccessCode.BUSINESS_NUMBER_AVAILABLE.getOwnerApiStatus()).body(businessNumCheckResponse);
        }
    }



    @GetMapping("/api/auth/owner/ownerTelCheck")
    public ResponseEntity<Object> telPhoneCheck(@RequestParam String ownerTel) {
        log.info(" *****************************    telPhoneCheck START    ***************************** ");

        boolean ownerTelDuplicate = ownerService.isTelPhoneDuplicate(ownerTel);

        if (ownerTelDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorStatus())
                    .errorDivisionCode(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiErrorCode.TELEPHONE_DUPLICATED.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> telPhoneCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiSuccessCode.TELEPHONE_AVAILABLE.getStatus())
                    .resultMsg(ApiSuccessCode.TELEPHONE_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiSuccessCode.TELEPHONE_AVAILABLE.getStatus()).body(telPhoneCheckResponse);
        }
    }
}

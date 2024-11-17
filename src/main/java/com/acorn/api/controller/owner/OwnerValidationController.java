package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.common.ApiValidationSuccessCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.service.owner.OwnerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
                    .httpStatus(ApiValidationErrorCode.EMAIL_DUPLICATED.getHttpStatus())
                    .errorDivisionCode(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiValidationErrorCode.EMAIL_DUPLICATED.getHttpStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .httpStatus(ApiValidationSuccessCode.EMAIL_AVAILABLE.getHttpStatus())
                    .resultMsg(ApiValidationSuccessCode.EMAIL_AVAILABLE.getMessage())
                    .build();

            return ResponseEntity.status(ApiValidationSuccessCode.EMAIL_AVAILABLE.getHttpStatus()).body(emailCheckResponse);
        }
    }

    @GetMapping("/api/auth/owner/businessNumCheck")
    public ResponseEntity<Object> telBusinessNumCheck(@RequestParam String ownerBusinessNum) {
        log.info(" *****************************    telBusinessNumCheck START    ***************************** ");

        boolean ownerBusinessNumDuplicate = ownerService.isBusinessNumDuplicate(ownerBusinessNum);

        if (ownerBusinessNumDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .httpStatus(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getHttpStatus())
                    .errorDivisionCode(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getErrorMsg())
                    .build();

            return ResponseEntity.status(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getHttpStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> businessNumCheckResponse = ApiSuccessResponse.builder()
                    .httpStatus(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE.getHttpStatus())
                    .resultMsg(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE.getMessage())
                    .build();

            return ResponseEntity.status(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE.getHttpStatus()).body(businessNumCheckResponse);
        }
    }

    @GetMapping("/api/auth/owner/ownerTelCheck")
    public ResponseEntity<Object> telPhoneCheck(@RequestParam String ownerTel) {
        log.info(" *****************************    telPhoneCheck START    ***************************** ");

        boolean ownerTelDuplicate = ownerService.isTelPhoneDuplicate(ownerTel);

        if (ownerTelDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .httpStatus(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getHttpStatus())
                    .errorDivisionCode(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorMsg())
                    .build();

            return ResponseEntity.status(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getHttpStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> telPhoneCheckResponse = ApiSuccessResponse.builder()
                    .httpStatus(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getHttpStatus())
                    .resultMsg(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getMessage())
                    .build();

            return ResponseEntity.status(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getHttpStatus()).body(telPhoneCheckResponse);
        }
    }

    @GetMapping("/api/auth/owner/companyNameCheck")
    public ResponseEntity<Object> companyNameCheck(@RequestParam String ownerCompanyName) {
        log.info(" *****************************    companyNameCheck START    ***************************** ");

        boolean ownerCompanyNameDuplicate = ownerService.isCompanyNameDuplicate(ownerCompanyName);

        if (ownerCompanyNameDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .httpStatus(ApiValidationErrorCode.COMPANY_NAME_ERROR.getHttpStatus())
                    .errorDivisionCode(ApiValidationErrorCode.COMPANY_NAME_ERROR.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.COMPANY_NAME_ERROR.getErrorMsg())
                    .build();

            return ResponseEntity.status(ApiValidationErrorCode.COMPANY_NAME_ERROR.getHttpStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> companyNameResponse = ApiSuccessResponse.builder()
                    .httpStatus(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE.getHttpStatus())
                    .resultMsg(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE.getMessage())
                    .build();

            return ResponseEntity.status(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE.getHttpStatus()).body(companyNameResponse);
        }
    }
}
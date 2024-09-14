package com.acorn.api.controller.owner;

import com.acorn.api.com.acorn.api.code.code.common.ApiValidationErrorCode;
import com.acorn.api.com.acorn.api.code.code.common.ApiValidationSuccessCode;
import com.acorn.api.com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.com.acorn.api.code.response.ApiSuccessResponse;
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
                    .errorStatus(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorStatus())
                    .errorDivisionCode(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiValidationErrorCode.EMAIL_DUPLICATED.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> emailCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiValidationSuccessCode.EMAIL_AVAILABLE.getStatus())
                    .resultMsg(ApiValidationSuccessCode.EMAIL_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiValidationSuccessCode.EMAIL_AVAILABLE.getStatus()).body(emailCheckResponse);
        }

    }

    @GetMapping("/api/auth/owner/businessNumCheck")
    public ResponseEntity<Object> telBusinessNumCheck(@RequestParam String ownerBusinessNum) {
        log.info(" *****************************    telBusinessNumCheck START    ***************************** ");

        boolean ownerBusinessNumDuplicate = ownerService.isBusinessNumDuplicate(ownerBusinessNum);

        if (ownerBusinessNumDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getErrorStatus())
                    .errorDivisionCode(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiValidationErrorCode.BUSINESS_NUMBER_CONFLICT.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> businessNumCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE.getStatus())
                    .resultMsg(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE.getStatus()).body(businessNumCheckResponse);
        }
    }

    @GetMapping("/api/auth/owner/ownerTelCheck")
    public ResponseEntity<Object> telPhoneCheck(@RequestParam String ownerTel) {
        log.info(" *****************************    telPhoneCheck START    ***************************** ");

        boolean ownerTelDuplicate = ownerService.isTelPhoneDuplicate(ownerTel);

        if (ownerTelDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorStatus())
                    .errorDivisionCode(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiValidationErrorCode.TELEPHONE_DUPLICATED.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> telPhoneCheckResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getStatus())
                    .resultMsg(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiValidationSuccessCode.TELEPHONE_AVAILABLE.getStatus()).body(telPhoneCheckResponse);
        }
    }

    @GetMapping("/api/auth/owner/companyNameCheck")
    public ResponseEntity<Object> companyNameCheck(@RequestParam String ownerCompanyName) {
        log.info(" *****************************    companyNameCheck START    ***************************** ");

        boolean ownerCompanyNameDuplicate = ownerService.isCompanyNameDuplicate(ownerCompanyName);

        if (ownerCompanyNameDuplicate) {
            ApiErrorResponse errorResponse = ApiErrorResponse.builder()
                    .errorStatus(ApiValidationErrorCode.COMPANY_NAME_ERROR.getErrorStatus())
                    .errorDivisionCode(ApiValidationErrorCode.COMPANY_NAME_ERROR.getErrorDivisionCode())
                    .errorMsg(ApiValidationErrorCode.COMPANY_NAME_ERROR.getErrorMsg())
                    .build();
            return ResponseEntity.status(ApiValidationErrorCode.COMPANY_NAME_ERROR.getErrorStatus()).body(errorResponse);
        } else {
            ApiSuccessResponse<Object> companyNameResponse = ApiSuccessResponse.builder()
                    .resultCode(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE.getStatus())
                    .resultMsg(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE.getMessage())
                    .build();
            return ResponseEntity.status(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE.getStatus()).body(companyNameResponse);
        }
    }
}

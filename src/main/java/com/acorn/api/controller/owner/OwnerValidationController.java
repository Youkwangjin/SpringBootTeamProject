package com.acorn.api.controller.owner;

import com.acorn.api.code.common.ApiValidationSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
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
    public ResponseEntity<ApiSuccessResponse<Object>> emailCheck(@RequestParam String ownerEmail) {
        log.info(" *****************************    emailCheck START    ***************************** ");

        ownerService.isOwnerEmailDuplicate(ownerEmail);

        return ApiResponseBuilder.success(ApiValidationSuccessCode.EMAIL_AVAILABLE);
    }

    @GetMapping("/api/auth/owner/businessNumCheck")
    public ResponseEntity<ApiSuccessResponse<Object>> telBusinessNumCheck(@RequestParam String ownerBusinessNum) {
        log.info(" *****************************    telBusinessNumCheck START    ***************************** ");

        ownerService.isBusinessNumDuplicate(ownerBusinessNum);

        return ApiResponseBuilder.success(ApiValidationSuccessCode.BUSINESS_NUMBER_AVAILABLE);
    }

    @GetMapping("/api/auth/owner/ownerTelCheck")
    public ResponseEntity<ApiSuccessResponse<Object>> telPhoneCheck(@RequestParam String ownerTel) {
        log.info(" *****************************    telPhoneCheck START    ***************************** ");

        ownerService.isTelPhoneDuplicate(ownerTel);

        return ApiResponseBuilder.success(ApiValidationSuccessCode.TELEPHONE_AVAILABLE);
    }

    @GetMapping("/api/auth/owner/companyNameCheck")
    public ResponseEntity<ApiSuccessResponse<Object>> companyNameCheck(@RequestParam String ownerCompanyName) {
        log.info(" *****************************    companyNameCheck START    ***************************** ");

        ownerService.isCompanyNameDuplicate(ownerCompanyName);

        return ApiResponseBuilder.success(ApiValidationSuccessCode.COMPANY_NAME_AVAILABLE);
    }
}
package com.acorn.api.exception.container;

import com.acorn.api.code.common.ApiValidationErrorCode;
import com.acorn.api.code.response.ApiErrorResponse;
import com.acorn.api.controller.admin.AdminContainerManagementController;
import com.acorn.api.controller.container.ContainerDeleteController;
import com.acorn.api.controller.container.ContainerRegisterController;
import com.acorn.api.controller.container.ContainerUpdateController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice (assignableTypes = {ContainerRegisterController.class, ContainerUpdateController.class, ContainerDeleteController.class, AdminContainerManagementController.class})
public class ContainerExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("  ========================  ExceptionHandler started  ========================  ");

        StringBuilder errorContainerMsg = new StringBuilder();
        ApiValidationErrorCode containerErrorCode = ApiValidationErrorCode.VALIDATION_ERROR;

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            if (!errorContainerMsg.isEmpty()) {
                errorContainerMsg.append(", ");
            }
            errorContainerMsg.append(fieldError.getField()).append(": ").append(fieldError.getDefaultMessage());

            containerErrorCode = switch (fieldError.getField()) {
                case "containerId" -> ApiValidationErrorCode.ID_VAULE_ERROR;
                case "containerName" -> ApiValidationErrorCode.CONTAINER_NAME_ERROR;
                case "containerAddr" -> ApiValidationErrorCode.ADDRESS_FORMAT_ERROR;
                case "containerLatitude" -> ApiValidationErrorCode.LATITUDE_FORMAT_ERROR;
                case "containerLongitude" -> ApiValidationErrorCode.LONGITUDE_FORMAT_ERROR;
                case "containerSize" -> ApiValidationErrorCode.SIZE_FORMAT_ERROR;
                case "containerPrice" -> ApiValidationErrorCode.PRICE_FORMAT_ERROR;
                case "containerFiles" -> ApiValidationErrorCode.FILE_SIZE_ERROR;
                default -> ApiValidationErrorCode.VALIDATION_ERROR;
            };
        }

        log.info("  ========================  Validation errors  ========================  : {}", errorContainerMsg);

        ApiErrorResponse response = new ApiErrorResponse(
                containerErrorCode.getHttpStatus(),
                containerErrorCode.getErrorDivisionCode(),
                containerErrorCode.getErrorMsg()
        );

        return ResponseEntity.status(containerErrorCode.getHttpStatus()).body(response);
    }
}

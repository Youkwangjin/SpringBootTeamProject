package com.acorn.api.controller.container;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.container.request.ContainerRegisterReqDTO;
import com.acorn.api.service.container.ContainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContainerRegisterController {

    private final ContainerService containerService;

    @PostMapping("/api/container/register")
    public ResponseEntity<ApiSuccessResponse<Object>> containerRegister(@Valid ContainerRegisterReqDTO registerData) {
        containerService.containerRegister(registerData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_REGISTER_SUCCESS);
    }
}
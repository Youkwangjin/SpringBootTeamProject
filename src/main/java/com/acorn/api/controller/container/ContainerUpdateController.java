package com.acorn.api.controller.container;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.container.request.ContainerUpdateReqDTO;
import com.acorn.api.service.container.ContainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContainerUpdateController {

    private final ContainerService containerService;

    @PatchMapping("/api/container/update/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> containerUpdate(@Valid ContainerUpdateReqDTO updateData) {
        containerService.containerUpdate(updateData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_UPDATE_SUCCESS);
    }
}
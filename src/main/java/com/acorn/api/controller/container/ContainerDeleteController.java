package com.acorn.api.controller.container;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.container.request.ContainerDeleteReqDTO;
import com.acorn.api.service.container.ContainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ContainerDeleteController {

    private final ContainerService containerService;

    @PostMapping("/api/container/delete/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> containerDelete(@Valid @RequestBody ContainerDeleteReqDTO deleteData) {
        containerService.containerDelete(deleteData);

        return ApiResponseBuilder.success(ApiSuccessCode.CONTAINER_DELETE_SUCCESS);
    }
}
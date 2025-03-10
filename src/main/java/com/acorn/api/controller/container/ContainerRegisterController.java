package com.acorn.api.controller.container;

import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.container.ContainerRegisterDTO;
import com.acorn.api.service.container.ContainerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ContainerRegisterController {

    private final ContainerService containerService;

    @PostMapping("/api/container/register")
    public ResponseEntity<ApiSuccessResponse<Object>> containerRegister(@Valid ContainerRegisterDTO registerData) {
        log.info(" *****************************    Container Register START    *****************************");

        containerService.containerRegister(registerData);

        return null;
    }
}
package com.acorn.api.controller.reservation;

import com.acorn.api.code.common.ApiSuccessCode;
import com.acorn.api.code.response.ApiResponseBuilder;
import com.acorn.api.code.response.ApiSuccessResponse;
import com.acorn.api.dto.reservation.request.ReservationCancelReqDTO;
import com.acorn.api.service.reservation.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/api/user/reservations/{containerId}")
    public ResponseEntity<ApiSuccessResponse<Object>> reserveContainer(@PathVariable("containerId") Integer containerId) {
        reservationService.reserveContainer(containerId);

        return ApiResponseBuilder.success(ApiSuccessCode.RESERVE_CONTAINER_SUCCESS);
    }

    @PostMapping("/api/user/reservations/cancel/{reservationId}")
    public ResponseEntity<ApiSuccessResponse<Object>> reserveCancelContainer(@Valid @RequestBody ReservationCancelReqDTO requestData) {
        reservationService.reserveCancelContainer(requestData);

        return ApiResponseBuilder.success(ApiSuccessCode.RESERVE_CONTAINER_CANCEL);
    }
}
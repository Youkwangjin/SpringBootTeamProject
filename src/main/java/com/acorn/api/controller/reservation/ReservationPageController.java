package com.acorn.api.controller.reservation;

import com.acorn.api.dto.reservation.request.ReservationContainerListReqDTO;
import com.acorn.api.dto.reservation.response.ReservationContainerDetailResDTO;
import com.acorn.api.dto.reservation.response.ReservationContainerListResDTO;
import com.acorn.api.dto.reservation.response.ReservationDetailResDTO;
import com.acorn.api.dto.reservation.request.ReservationListReqDTO;
import com.acorn.api.dto.reservation.response.ReservationListResDTO;
import com.acorn.api.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationPageController {

    private final ReservationService reservationService;

    @GetMapping("/user/reservation/list")
    public String reservationListPage(ReservationListReqDTO listData, Model model) {
        List<ReservationListResDTO> reservationListData = reservationService.getReservationListData(listData);
        model.addAttribute("reservationListData", reservationListData);
        model.addAttribute("request", listData);
        return "reservation/reservation-list";
    }

    @GetMapping("/reservation/container/list")
    public String reservationPage(ReservationContainerListReqDTO listData, Model model) {
        List<ReservationContainerListResDTO> reservationContainerListData = reservationService.getReservationContainerListData(listData);
        model.addAttribute("reservationContainerListData", reservationContainerListData);
        model.addAttribute("request", listData);
        return "reservation/reservation-container-list";
    }

    @GetMapping("/user/reservation/detail/{reservationId}")
    public String reservationDetailPage(@PathVariable("reservationId") Integer reservationId, Model model) {
        ReservationDetailResDTO reservationDetailData = reservationService.getReservationData(reservationId);
        model.addAttribute("reservationDetailData", reservationDetailData);
        return "reservation/reservation-detail";
    }

    @GetMapping("/reservation/container/detail/{reservationId}")
    public String reservationContainerDetailPage(@PathVariable("reservationId") Integer reservationId, Model model) {
        ReservationContainerDetailResDTO reservationContainerDetailData = reservationService.getReservationContainerDetailData(reservationId);
        model.addAttribute("reservationContainerDetailData", reservationContainerDetailData);
        return "reservation/reservation-container-detail";
    }
}
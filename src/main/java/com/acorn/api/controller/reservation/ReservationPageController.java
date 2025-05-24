package com.acorn.api.controller.reservation;

import com.acorn.api.dto.reservation.ReservationListDTO;
import com.acorn.api.service.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationPageController {

    private final ReservationService reservationService;

    @GetMapping("/user/reservation/list")
    public String reservationListPage(ReservationListDTO listData, Model model) {
        List<ReservationListDTO> reservationListData = reservationService.getReservationListData(listData);
        model.addAttribute("reservationListData", reservationListData);
        model.addAttribute("request", listData);
        return "reservation/reservation-list";
    }
}
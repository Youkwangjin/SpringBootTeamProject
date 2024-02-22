package pack.controller.booking;

import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Collections;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pack.dto.booking.BookingDTO;
import pack.dto.admin.AdminDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pack.response.BookingResponse;
import pack.service.booking.BookingService;

@Controller
@RequestMapping("/booking")
@AllArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

    @GetMapping("/booking")
    public String booking() {
        return "booking/booking";
    }

    @GetMapping("/bookingInfo")
    public String bookingProcess(HttpSession session, Model model) {
        try {
            String user_id = (String) session.getAttribute("user_id");
            if (user_id != null) {
                ArrayList<BookingDTO> bookingDto = bookingService.bookingList(user_id);
                model.addAttribute("bList", bookingDto);
                return "booking/booking-info";
            } else {
                logger.error("유저 세션 없다!");
                return "redirect:/";
            }
        } catch (Exception e) {
            logger.error("Error bookingProcess", e);
            return "redirect:/container/container-error";
        }
    }

    @PostMapping("/bookingDo")
    public String bookingDo(BookingDTO bookingdto, AdminDTO adminDTO) {
        try {
            boolean bookingInsertData = bookingService.bookingInsert(bookingdto);
            boolean contStatusData = bookingService.contStatusUpdate(adminDTO);
            if (bookingInsertData && contStatusData) {
                return "redirect:/booking/bookingInfo";
            } else {
                return "booking/booking";
            }
        } catch (Exception e) {
            logger.error("Error bookingDo", e);
            return "booking/booking";
        }
    }

    @ResponseBody
    @PutMapping("/updateStatus/{bookingId}/{status}")
    public ResponseEntity<?> updateBookingStatus(@PathVariable int bookingId, @PathVariable String status) {
        try {
            BookingResponse response = bookingService.updateBookingStatus(bookingId, status);
            if (response.isSuccess()) {
                return ResponseEntity.ok(Collections.singletonMap("message", response.getMessage()));
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Collections.singletonMap("error", response.getMessage()));
            }
        } catch (Exception e) {
            logger.error("창고예약 상태 에러", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "서버 내부 오류가 발생했습니다."));
        }
    }
}
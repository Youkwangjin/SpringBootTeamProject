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
import pack.dto.booking.bookingDTO;
import pack.dto.admin.AdminDTO;
import pack.dao.booking.BookingDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/booking")
@AllArgsConstructor
public class bookingController {

	private final BookingDAO dao;
	private static final Logger logger = LoggerFactory.getLogger(bookingController.class);

	@GetMapping("/booking")
	public String booking() {
		return "booking/booking";
	}

	@PostMapping("/bookingDo")
	public String bookingDo(bookingDTO bookingdto, AdminDTO adminDTO) {
		try {
			boolean b = dao.bookingInsert(bookingdto);
			boolean a = dao.contStatusUpdate(adminDTO);
			if (b && a) {
				return "redirect:/booking/bookingInfo";
			} else {
				return "/booking/booking";
			}
		} catch (Exception e) {
			logger.error("Error bookingDo", e);
			return "/booking/booking";
		}
	}

	@GetMapping("/bookingInfo")
	public String bookingProcess(HttpSession session, Model model) {
		try {
			String user_id = (String) session.getAttribute("user_id");
			if (user_id != null) {
				ArrayList<bookingDTO> bookingDto = dao.bookingList(user_id);
				model.addAttribute("bList", bookingDto);
				return "/booking/booking-info";
			} else {
				logger.error("유저 세션 없다!");
				return "redirect:/";
			}
		} catch (Exception e) {
			logger.error("Error bookingProcess", e);
			return "redirect:/container/container-error";
		}
	}


	@ResponseBody
	@DeleteMapping("/bookDelete")
	public ResponseEntity<?> bookDelete(@RequestBody bookingDTO bookingDto) {
		try {
			boolean b = dao.bookingDelete(bookingDto);
			if (b) {
				return ResponseEntity.ok(Collections.singletonMap("message", "예약이 취소되었습니다."));
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.singletonMap("error", "예약을 찾을 수 없습니다."));
			}
		} catch (Exception e) {
			logger.error("Error bookDelete", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("error", "서버 내부 오류가 발생했습니다."));
		}
	}
}
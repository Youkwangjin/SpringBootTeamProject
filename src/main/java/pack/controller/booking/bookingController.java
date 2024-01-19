package pack.controller.booking;



import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pack.dto.booking.bookingDTO;
import pack.dto.admin.AdminDTO;
import pack.dao.booking.BookingDAO;


@Controller
@RequestMapping("booking")
@AllArgsConstructor
public class bookingController {

	private final BookingDAO dao;

	@GetMapping("/booking")
	public String booking() {
		return "booking/booking";
	}

		@PostMapping("/bookingDo")
		public String bookingDo(bookingDTO bookingdto, AdminDTO adminDTO) {
			boolean b = dao.bookingInsert(bookingdto);
			boolean a = dao.contStatusUpdate(adminDTO);
			if(b && a) {
				return "redirect:/booking/bookingInfo";			
			} else {
				return "/booking/booking";
			}	
		}

	@GetMapping("/bookingInfo")
	public String bookingProcess(HttpSession session, Model model) {
		String user_id = (String)session.getAttribute("user_id");
		ArrayList<bookingDTO> bookingDto = dao.bookingListAll(user_id);
		session.setAttribute("bookList", bookingDto);
		model.addAttribute("bList", bookingDto);
		return "/booking/booking-info";
	}
	
	//예약삭제
	@GetMapping("/bookDelete")
	public String bookDelete(bookingDTO bookingDto){
		boolean b = dao.bookingDelete(bookingDto);
		if(b) {
			return "booking/booking";
		}
		return "/booking/booking-info";
	}

}
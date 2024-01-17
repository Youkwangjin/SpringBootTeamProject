package pack.controller.booking;



import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
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
public class bookingController {
	@Autowired
	private BookingDAO dao;

	@GetMapping("booking")
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
		
		System.out.println("리스트 메소드 시작");
		
		String user_id = (String)session.getAttribute("user_id");
		
		System.out.println(user_id);
		
		ArrayList<bookingDTO> bookingdto = dao.bookingListAll(user_id);
		System.out.println(bookingdto);
		
		session.setAttribute("bookList", bookingdto);
		
		model.addAttribute("bList", bookingdto);
		return "booking-Info";
	}
	
	//예약삭제
	@GetMapping("bookDelete")
	public String bookDelete(bookingDTO bookingDto){
		boolean b = dao.bookingDelete(bookingDto);
		if(b) {
			return "booking/booking";
		}
		return "redirect:bookingInfo";
	}

}
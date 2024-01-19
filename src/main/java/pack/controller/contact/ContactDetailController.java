package pack.controller.contact;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
@AllArgsConstructor
public class ContactDetailController {

	@Autowired
	private ContactDAO contactDao;
	
	@GetMapping("/contactDetail")
	public String detail(@RequestParam("contact_no")String contact_no, Model model) {
		ContactDTO detail = contactDao.detailContact(contact_no);
		model.addAttribute("detail", detail);
		return "contact/contact-detail";
	}
}

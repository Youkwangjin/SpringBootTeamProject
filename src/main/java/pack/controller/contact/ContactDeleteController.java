package pack.controller.contact;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pack.service.contact.ContactDeleteService;

@Controller
@AllArgsConstructor
public class ContactDeleteController {

	private final ContactDeleteService contactDeleteService;

	@GetMapping("/contactDelete")
	public String contactDelete(@RequestParam("contact_no") String contact_no) {
		boolean insertContactData = contactDeleteService.deleteContact(contact_no);
		if(insertContactData)
			return "redirect:/contactAdmin?page=1";
		else
			return "/container/container-error";
	}
}

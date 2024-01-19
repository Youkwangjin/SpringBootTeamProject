package pack.controller.contact;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
@AllArgsConstructor
public class ContactUpdateController {

	private final ContactDAO contactDao;
	
	@PostMapping("/contactUpdate")
	public String update(ContactDTO contactDTO) {
	boolean b = contactDao.updateContact(contactDTO);
	if(b)
		return "redirect:/contactAdmin?page=1";
	else
		return "/container/container-error";
}
}

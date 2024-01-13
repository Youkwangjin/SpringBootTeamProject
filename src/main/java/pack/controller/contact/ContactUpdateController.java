package pack.controller.contact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
public class ContactUpdateController {

	@Autowired
	private ContactDAO contactDao;
	
	@PostMapping("contactupdate")
	public String update(ContactDTO contactDTO) {
	boolean b = contactDao.updateContact(contactDTO);
	if(b)
		return "redirect:/contactadmin?page=1";
	else
		return "error";
}
}

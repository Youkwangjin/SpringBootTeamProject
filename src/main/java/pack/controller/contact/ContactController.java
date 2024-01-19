package pack.controller.contact;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
@AllArgsConstructor
public class ContactController {

	private final ContactDAO contactDao;
	
	@RequestMapping("contactAdmin")
	public String adminList(Model model) {
		ArrayList<ContactDTO> list = (ArrayList<ContactDTO>)contactDao.listContact();
		model.addAttribute("list", list);
		return "contact/contact-admin";
	}
	
	@RequestMapping("/contactUser")
	public String mainListUser(Model model) {
		ArrayList<ContactDTO> list = (ArrayList<ContactDTO>)contactDao.listContact();
		model.addAttribute("list", list);
		return "contact/contact-user";
	}
	
	@RequestMapping("contactOwner")
	public String mainListOwner(Model model) {
		ArrayList<ContactDTO> list = (ArrayList<ContactDTO>)contactDao.listContact();
		model.addAttribute("list", list);
		return "contact/contact-owner";
	}
}

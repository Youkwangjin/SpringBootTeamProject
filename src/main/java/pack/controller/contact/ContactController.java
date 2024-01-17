package pack.controller.contact;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.RequestMapping;


import pack.dao.contact.ContactDAO;
import pack.dto.contact.ContactDTO;

@Controller
public class ContactController {

	@Autowired
	private ContactDAO contactDao;
	
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
